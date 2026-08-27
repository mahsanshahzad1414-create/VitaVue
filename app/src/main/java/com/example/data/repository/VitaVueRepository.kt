package com.example.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.example.data.datasource.FoodDatabaseSource
import com.example.data.datasource.NutritionKnowledgeSource
import com.example.data.local.AnalysisEntity
import com.example.data.local.BookmarkEntity
import com.example.data.local.MealPlanEntity
import com.example.data.local.NutritionDao
import com.example.data.local.SavedFoodEntity
import com.example.data.local.UserProfileEntity
import com.example.data.local.VitaVueDatabase
import com.example.data.model.AgentAction
import com.example.data.model.ArticleCategory
import com.example.data.model.AuthUser
import com.example.data.model.DayMealPlan
import com.example.data.model.FoodAnalysisResult
import com.example.data.model.FoodCategory
import com.example.data.model.FoodComponent
import com.example.data.model.FoodItem
import com.example.data.model.Micronutrient
import com.example.data.model.NutritionArticle
import com.example.data.model.NutritionMyth
import com.example.data.model.PlannedMealItem
import com.example.data.model.UserDietPlan
import com.example.data.model.UserProfile
import com.example.data.remote.FirebaseAuthService
import com.example.data.remote.FirestoreSyncService
import com.example.data.remote.GeminiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class VitaVueRepository(
    private val context: Context,
    val authService: FirebaseAuthService = FirebaseAuthService(),
    val firestoreSyncService: FirestoreSyncService = FirestoreSyncService()
) {

    private val db = VitaVueDatabase.getInstance(context)
    private val dao: NutritionDao = db.nutritionDao()
    private val geminiService = GeminiService()

    val currentUserFlow: StateFlow<AuthUser?> = authService.currentUserFlow

    val currentUserId: String?
        get() = authService.currentUserId

    // --- CLOUD SYNCHRONIZATION ---
    suspend fun synchronizeWithCloud(): Int = withContext(Dispatchers.IO) {
        val uid = authService.currentUserId ?: return@withContext 0
        var syncedCount = 0

        try {
            // 1. Sync User Profile
            val cloudProfile = firestoreSyncService.fetchUserProfileFromCloud(uid)
            if (cloudProfile != null) {
                saveUserProfileToLocal(cloudProfile)
                syncedCount++
            } else {
                // Upload local profile if not in cloud
                val localEntity = dao.getUserProfileFlow()
                val currentProfile = UserProfile(
                    name = "Nutrition Explorer",
                    goal = "Balanced Nutrition"
                )
                firestoreSyncService.saveUserProfileToCloud(uid, currentProfile, authService.getCurrentUser()?.email)
            }

            // 2. Sync Meal Analyses (Merge without duplicates)
            val cloudAnalyses = firestoreSyncService.fetchAllMealAnalysesFromCloud(uid)
            cloudAnalyses.forEach { analysis ->
                val existing = dao.getAnalysisById(analysis.id)
                if (existing == null) {
                    saveAnalysisToLocalOnly(analysis)
                    syncedCount++
                }
            }

            // 3. Sync Saved Foods
            val cloudSavedFoodIds = firestoreSyncService.fetchSavedFoodIdsFromCloud(uid)
            cloudSavedFoodIds.forEach { foodId ->
                if (!dao.isFoodSaved(foodId)) {
                    dao.insertSavedFood(SavedFoodEntity(foodId))
                    syncedCount++
                }
            }

            // 4. Sync Bookmarks
            val cloudBookmarks = firestoreSyncService.fetchBookmarkSlugsFromCloud(uid)
            cloudBookmarks.forEach { slug ->
                if (!dao.isArticleBookmarked(slug)) {
                    dao.insertBookmark(BookmarkEntity(slug))
                    syncedCount++
                }
            }

            // 5. Sync Diet Plans
            val cloudPlans = firestoreSyncService.fetchDietPlansFromCloud(uid)
            cloudPlans.forEach { (planId, planJson) ->
                val parsed = parseMealPlanJson(planJson)
                if (parsed != null) {
                    val entity = MealPlanEntity(
                        id = planId,
                        title = parsed.title,
                        planJson = planJson
                    )
                    dao.insertMealPlan(entity)
                    syncedCount++
                }
            }
        } catch (e: Exception) {
            Log.w("VitaVueRepository", "Sync failed or partially completed: ${e.message}")
        }

        syncedCount
    }

    // --- AI ANALYSIS ---
    suspend fun analyzeFood(bitmap: Bitmap?): FoodAnalysisResult {
        if (bitmap == null || bitmap.isRecycled || bitmap.width <= 0 || bitmap.height <= 0) {
            throw IllegalArgumentException("A valid meal image is required for nutritional analysis.")
        }
        val result = geminiService.analyzeFoodImage(bitmap)
        // Automatically save analysis to history in Room and Cloud on success
        saveAnalysisToHistory(result)
        return result
    }

    suspend fun askAgent(
        query: String,
        history: List<Pair<String, Boolean>>,
        mealContext: FoodAnalysisResult? = null,
        activeFoodName: String? = null,
        activeArticleTitle: String? = null,
        userDietGoal: String? = null
    ): Pair<String, List<AgentAction>> {
        return geminiService.askAgent(
            userQuery = query,
            history = history,
            contextMealAnalysis = mealContext,
            activeFoodName = activeFoodName,
            activeArticleTitle = activeArticleTitle,
            userDietGoal = userDietGoal
        )
    }

    // --- HISTORY IN ROOM & CLOUD ---
    suspend fun saveAnalysisToHistory(analysis: FoodAnalysisResult) = withContext(Dispatchers.IO) {
        saveAnalysisToLocalOnly(analysis)
        val uid = authService.currentUserId
        if (uid != null) {
            try {
                firestoreSyncService.saveMealAnalysisToCloud(uid, analysis)
            } catch (e: Exception) {
                Log.w("VitaVueRepository", "Failed to sync analysis to cloud: ${e.message}")
            }
        }
    }

    suspend fun saveAnalysisToLocalOnly(analysis: FoodAnalysisResult) = withContext(Dispatchers.IO) {
        val detectedJson = JSONArray().apply {
            analysis.detectedItems.forEach { item ->
                put(JSONObject().apply {
                    put("name", item.name)
                    put("portion", item.portionEstimate)
                    put("cal", item.calories)
                    put("p", item.proteinGrams)
                    put("c", item.carbsGrams)
                    put("f", item.fatGrams)
                    put("fib", item.fiberGrams)
                })
            }
        }.toString()

        val microJson = JSONArray().apply {
            analysis.micronutrients.forEach { m ->
                put(JSONObject().apply {
                    put("name", m.name)
                    put("amount", m.amount)
                    put("dv", m.dailyValuePercent ?: -1)
                    put("benefit", m.benefit)
                })
            }
        }.toString()

        val highlightsJson = JSONArray(analysis.nutritionHighlights).toString()
        val suggestionsJson = JSONArray(analysis.practicalSuggestions).toString()

        val entity = AnalysisEntity(
            id = analysis.id,
            mealTitle = analysis.mealTitle,
            mealDescription = analysis.mealDescription,
            totalCalories = analysis.totalCalories,
            totalProteinGrams = analysis.totalProteinGrams,
            totalCarbsGrams = analysis.totalCarbsGrams,
            totalFatGrams = analysis.totalFatGrams,
            totalFiberGrams = analysis.totalFiberGrams,
            detectedItemsJson = detectedJson,
            micronutrientsJson = microJson,
            highlightsJson = highlightsJson,
            suggestionsJson = suggestionsJson,
            confidenceRating = analysis.confidenceRating,
            uncertaintyNote = analysis.uncertaintyNote,
            imageUri = analysis.imageUri,
            timestamp = analysis.timestamp
        )
        dao.insertAnalysis(entity)
    }

    fun getAllAnalysesFlow(): Flow<List<FoodAnalysisResult>> {
        return dao.getAllAnalyses().map { list ->
            list.map { entity ->
                entityToAnalysisResult(entity)
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteAnalysis(id: String) = withContext(Dispatchers.IO) {
        dao.deleteAnalysisById(id)
        val uid = authService.currentUserId
        if (uid != null) {
            try {
                firestoreSyncService.deleteMealAnalysisFromCloud(uid, id)
            } catch (e: Exception) {
                Log.w("VitaVueRepository", "Failed to delete cloud analysis: ${e.message}")
            }
        }
    }

    // --- FOOD EXPLORER ---
    fun getAllFoods(): List<FoodItem> = FoodDatabaseSource.foods

    fun searchFoods(query: String, category: FoodCategory?, tag: String?): List<FoodItem> {
        return FoodDatabaseSource.searchFoods(query, category, tag)
    }

    fun getFoodById(id: String): FoodItem? {
        return FoodDatabaseSource.getFoodById(id)
    }

    fun getSavedFoodIdsFlow(): Flow<Set<String>> {
        return dao.getAllSavedFoods().map { list ->
            list.map { it.foodId }.toSet()
        }.flowOn(Dispatchers.IO)
    }

    suspend fun toggleSaveFood(foodId: String) = withContext(Dispatchers.IO) {
        val uid = authService.currentUserId
        if (dao.isFoodSaved(foodId)) {
            dao.deleteSavedFood(foodId)
            if (uid != null) {
                try {
                    firestoreSyncService.deleteSavedFoodFromCloud(uid, foodId)
                } catch (e: Exception) {
                    Log.w("VitaVueRepository", "Failed to remove cloud saved food: ${e.message}")
                }
            }
        } else {
            dao.insertSavedFood(SavedFoodEntity(foodId))
            if (uid != null) {
                try {
                    firestoreSyncService.saveSavedFoodToCloud(uid, foodId)
                } catch (e: Exception) {
                    Log.w("VitaVueRepository", "Failed to save cloud saved food: ${e.message}")
                }
            }
        }
    }

    // --- KNOWLEDGE & MYTHS ---
    fun getAllArticles(): List<NutritionArticle> = NutritionKnowledgeSource.articles

    fun searchArticles(query: String, category: ArticleCategory?): List<NutritionArticle> {
        return NutritionKnowledgeSource.searchArticles(query, category)
    }

    fun getArticleBySlug(slug: String): NutritionArticle? {
        return NutritionKnowledgeSource.getArticleBySlug(slug)
    }

    fun getAllMyths(): List<NutritionMyth> = NutritionKnowledgeSource.myths

    fun getBookmarkedArticleSlugsFlow(): Flow<Set<String>> {
        return dao.getAllBookmarks().map { list ->
            list.map { it.articleSlug }.toSet()
        }.flowOn(Dispatchers.IO)
    }

    suspend fun toggleBookmarkArticle(slug: String) = withContext(Dispatchers.IO) {
        val uid = authService.currentUserId
        if (dao.isArticleBookmarked(slug)) {
            dao.deleteBookmark(slug)
            if (uid != null) {
                try {
                    firestoreSyncService.deleteBookmarkFromCloud(uid, slug)
                } catch (e: Exception) {
                    Log.w("VitaVueRepository", "Failed to delete cloud bookmark: ${e.message}")
                }
            }
        } else {
            dao.insertBookmark(BookmarkEntity(slug))
            if (uid != null) {
                try {
                    firestoreSyncService.saveBookmarkToCloud(uid, slug)
                } catch (e: Exception) {
                    Log.w("VitaVueRepository", "Failed to save cloud bookmark: ${e.message}")
                }
            }
        }
    }

    // --- DIET PLANNER ---
    fun generateMealPlan(
        goal: String,
        dietaryPattern: String,
        targetCalories: Int,
        cuisine: String,
        daysCount: Int = 3
    ): UserDietPlan {
        val days = mutableListOf<DayMealPlan>()
        val daysOfWeek = listOf("Day 1 - Foundation", "Day 2 - Vitality", "Day 3 - Longevity", "Day 4 - Strength", "Day 5 - Balance", "Day 6 - Energy", "Day 7 - Recovery")

        val calorieScaleFactor = (targetCalories.toFloat() / 2000f).coerceIn(0.7f, 1.8f)

        // Recipe Matrices tailored by Cuisine and Dietary Pattern
        for (dayIndex in 0 until daysCount) {
            val dayName = daysOfWeek.getOrElse(dayIndex) { "Day ${dayIndex + 1}" }

            val breakfast = createDynamicMeal(
                mealType = "Breakfast",
                cuisine = cuisine,
                dietaryPattern = dietaryPattern,
                goal = goal,
                dayIndex = dayIndex,
                scale = calorieScaleFactor,
                targetPortion = 0.25f
            )

            val lunch = createDynamicMeal(
                mealType = "Lunch",
                cuisine = cuisine,
                dietaryPattern = dietaryPattern,
                goal = goal,
                dayIndex = dayIndex,
                scale = calorieScaleFactor,
                targetPortion = 0.35f
            )

            val dinner = createDynamicMeal(
                mealType = "Dinner",
                cuisine = cuisine,
                dietaryPattern = dietaryPattern,
                goal = goal,
                dayIndex = dayIndex,
                scale = calorieScaleFactor,
                targetPortion = 0.30f
            )

            val snack = createDynamicMeal(
                mealType = "Smart Snack",
                cuisine = cuisine,
                dietaryPattern = dietaryPattern,
                goal = goal,
                dayIndex = dayIndex,
                scale = calorieScaleFactor,
                targetPortion = 0.10f
            )

            days.add(DayMealPlan(dayIndex + 1, dayName, listOf(breakfast, lunch, dinner, snack)))
        }

        val totalDailyCal = days.firstOrNull()?.meals?.sumOf { it.calories } ?: targetCalories
        val totalDailyProt = days.firstOrNull()?.meals?.sumOf { it.proteinGrams.toDouble() }?.toInt() ?: 120
        val totalDailyCarb = days.firstOrNull()?.meals?.sumOf { it.carbsGrams.toDouble() }?.toInt() ?: 200
        val totalDailyFat = days.firstOrNull()?.meals?.sumOf { it.fatGrams.toDouble() }?.toInt() ?: 65

        return UserDietPlan(
            title = "$goal Precision Plan • $cuisine ($dietaryPattern)",
            primaryGoal = goal,
            dietaryPattern = dietaryPattern,
            targetCaloriesPerDay = totalDailyCal,
            targetProteinGrams = totalDailyProt,
            targetCarbsGrams = totalDailyCarb,
            targetFatGrams = totalDailyFat,
            days = days
        )
    }

    private fun createDynamicMeal(
        mealType: String,
        cuisine: String,
        dietaryPattern: String,
        goal: String,
        dayIndex: Int,
        scale: Float,
        targetPortion: Float
    ): PlannedMealItem {
        val isVegan = dietaryPattern.contains("Vegan", ignoreCase = true)
        val isVegetarian = isVegan || dietaryPattern.contains("Vegetarian", ignoreCase = true) || dietaryPattern.contains("Plant", ignoreCase = true)
        val isLowCarb = dietaryPattern.contains("Low-Carb", ignoreCase = true) || dietaryPattern.contains("Keto", ignoreCase = true)
        val isHighProtein = goal.contains("Protein", ignoreCase = true) || goal.contains("Muscle", ignoreCase = true)

        val item = when (mealType) {
            "Breakfast" -> {
                when {
                    cuisine.contains("South Asian", ignoreCase = true) -> {
                        if (isVegan) {
                            if (dayIndex % 2 == 0) {
                                PlannedMealItem(
                                    mealType = "Breakfast",
                                    recipeTitle = "Spiced Moong Dal Chilla with Mint Chutney",
                                    description = "Savory yellow lentil crepes stuffed with grated ginger, cilantro, and crushed cumin, served with probiotic mint coriander chutney.",
                                    ingredients = listOf("1 cup Soaked yellow moong dal", "1 tsp Grated ginger", "Fresh mint-coriander chutney", "1 tsp Cold-pressed mustard oil", "Himalayan pink salt"),
                                    prepTimeMin = 12,
                                    calories = (360 * scale).toInt(),
                                    proteinGrams = (18f * scale),
                                    carbsGrams = (48f * scale),
                                    fatGrams = (8f * scale),
                                    fiberGrams = (9f * scale)
                                )
                            } else {
                                PlannedMealItem(
                                    mealType = "Breakfast",
                                    recipeTitle = "Spiced Tofu Bhurji with Multigrain Toast",
                                    description = "Crumbled organic tofu tossed with turmeric, sweet onions, diced tomatoes, and freshly cracked green chilies.",
                                    ingredients = listOf("150g Crumbled firm tofu", "1 slice Sprouted grain toast", "1/2 cup Diced onions & tomatoes", "1/2 tsp Turmeric & cumin"),
                                    prepTimeMin = 10,
                                    calories = (380 * scale).toInt(),
                                    proteinGrams = (22f * scale),
                                    carbsGrams = (32f * scale),
                                    fatGrams = (16f * scale),
                                    fiberGrams = (7f * scale)
                                )
                            }
                        } else {
                            PlannedMealItem(
                                mealType = "Breakfast",
                                recipeTitle = "Paneer & Vegetable Egg Scramble with Jowar Roti",
                                description = "Pasture-raised egg scramble folded with diced paneer cubes, baby spinach, and roasted cumin seeds alongside sorghum flatbread.",
                                ingredients = listOf("2 Pasture-raised eggs", "50g Fresh artisanal paneer", "1 cup Baby spinach", "1 Handmade Jowar roti", "1 tsp Desi ghee"),
                                prepTimeMin = 12,
                                calories = (420 * scale).toInt(),
                                proteinGrams = (28f * scale),
                                carbsGrams = (34f * scale),
                                fatGrams = (18f * scale),
                                fiberGrams = (6f * scale)
                            )
                        }
                    }
                    cuisine.contains("East Asian", ignoreCase = true) -> {
                        PlannedMealItem(
                            mealType = "Breakfast",
                            recipeTitle = if (isVegetarian) "Silken Tofu Miso Broth with Shiitake & Soba" else "Poached Salmon Miso Bowl with Wakame",
                            description = "Warm probiotic fermented white miso broth with steamed greens, toasted sesame seeds, and delicate ginger.",
                            ingredients = listOf("1 block Silken tofu / Wild salmon", "1 tbsp White organic miso", "1 cup Sliced shiitake mushrooms", "Wakame sea kelp", "Scallions"),
                            prepTimeMin = 10,
                            calories = (350 * scale).toInt(),
                            proteinGrams = (24f * scale),
                            carbsGrams = (38f * scale),
                            fatGrams = (9f * scale),
                            fiberGrams = (5f * scale)
                        )
                    }
                    cuisine.contains("Middle Eastern", ignoreCase = true) -> {
                        PlannedMealItem(
                            mealType = "Breakfast",
                            recipeTitle = if (isVegan) "Spiced Chickpea Shakshuka with Tahini Drizzle" else "Classic Shakshuka with Free-Range Eggs & Za'atar",
                            description = "Rich tomato, sweet bell pepper, and smoked paprika skillet simmered to perfection with fresh herbs and toasted sesame za'atar.",
                            ingredients = listOf(if (isVegan) "1 cup Chickpeas" else "2 Large eggs", "1.5 cups Stewed heirloom tomatoes", "1 tbsp Cold-pressed olive oil", "Za'atar blend", "Fresh parsley"),
                            prepTimeMin = 15,
                            calories = (390 * scale).toInt(),
                            proteinGrams = (20f * scale),
                            carbsGrams = (36f * scale),
                            fatGrams = (18f * scale),
                            fiberGrams = (8f * scale)
                        )
                    }
                    else -> {
                        // Mediterranean / Global default
                        if (dayIndex % 2 == 0) {
                            PlannedMealItem(
                                mealType = "Breakfast",
                                recipeTitle = "Chia Berry Power Pudding with Toasted Walnuts",
                                description = "Overnight soaked organic chia seeds in almond milk topped with wild polyphenolic blueberries and omega-3 crushed walnuts.",
                                ingredients = listOf("3 tbsp Chia seeds", "1 cup Unsweetened almond milk", "1/2 cup Wild blueberries", "15g Crushed raw walnuts", "1 tsp Ceylon cinnamon"),
                                prepTimeMin = 5,
                                calories = (380 * scale).toInt(),
                                proteinGrams = (14f * scale),
                                carbsGrams = (34f * scale),
                                fatGrams = (20f * scale),
                                fiberGrams = (14f * scale)
                            )
                        } else {
                            PlannedMealItem(
                                mealType = "Breakfast",
                                recipeTitle = "Avocado & Poached Egg Whole Grain Sourdough",
                                description = "Slow-fermented sourdough toast topped with rich Hass avocado, soft pasture eggs, and antioxidant microgreens.",
                                ingredients = listOf("1 slice Artisanal sourdough", "1/2 Hass avocado", "2 Pasture-raised eggs", "Microgreens", "Chili flakes"),
                                prepTimeMin = 10,
                                calories = (410 * scale).toInt(),
                                proteinGrams = (19f * scale),
                                carbsGrams = (32f * scale),
                                fatGrams = (22f * scale),
                                fiberGrams = (7f * scale)
                            )
                        }
                    }
                }
            }

            "Lunch" -> {
                when {
                    cuisine.contains("South Asian", ignoreCase = true) -> {
                        PlannedMealItem(
                            mealType = "Lunch",
                            recipeTitle = "Yellow Moong Dal Tadka with Steamed Brown Basmati & Kachumber",
                            description = "Slow-simmered yellow lentils tempered with cumin, roasted garlic, and turmeric, served with crisp cucumber-tomato salad and brown basmati.",
                            ingredients = listOf("1.5 cups Yellow Moong Dal", "3/4 cup Cooked Brown Basmati", "1 cup Diced kachumber salad", "1 tsp Cumin & cold-pressed ghee", "Lemon wedge"),
                            prepTimeMin = 18,
                            calories = (540 * scale).toInt(),
                            proteinGrams = (26f * scale),
                            carbsGrams = (78f * scale),
                            fatGrams = (12f * scale),
                            fiberGrams = (15f * scale)
                        )
                    }
                    cuisine.contains("East Asian", ignoreCase = true) -> {
                        PlannedMealItem(
                            mealType = "Lunch",
                            recipeTitle = if (isVegetarian) "Crispy Tempeh & Edamame Poke Bowl" else "Wild Sesame Salmon Poke Bowl with Edamame",
                            description = "Nutrient-dense bowl featuring organic protein over brown rice, cucumber ribbons, pickled ginger, and toasted nori.",
                            ingredients = listOf(if (isVegetarian) "140g Organic tempeh" else "130g Wild Alaskan salmon", "3/4 cup Brown rice", "1/2 cup Shelled edamame", "1/2 Avocado", "Tamari glaze"),
                            prepTimeMin = 15,
                            calories = (560 * scale).toInt(),
                            proteinGrams = (36f * scale),
                            carbsGrams = (58f * scale),
                            fatGrams = (18f * scale),
                            fiberGrams = (10f * scale)
                        )
                    }
                    cuisine.contains("Middle Eastern", ignoreCase = true) -> {
                        PlannedMealItem(
                            mealType = "Lunch",
                            recipeTitle = "Baked Herb Falafel Power Salad with Tahini",
                            description = "Crispy baked chickpea-parsley falafels nestled over crisp romaine, sumac pickled onions, Persian cucumbers, and lemon tahini dressing.",
                            ingredients = listOf("4 Baked falafels", "2 cups Romaine lettuce", "1/2 cup Cucumber & cherry tomatoes", "2 tbsp Tahini dressing", "6 Kalamata olives"),
                            prepTimeMin = 15,
                            calories = (520 * scale).toInt(),
                            proteinGrams = (21f * scale),
                            carbsGrams = (62f * scale),
                            fatGrams = (22f * scale),
                            fiberGrams = (14f * scale)
                        )
                    }
                    else -> {
                        PlannedMealItem(
                            mealType = "Lunch",
                            recipeTitle = "Mediterranean Tri-Color Quinoa Bowl with Chickpeas",
                            description = "Fluffy tri-color quinoa, spiced roasted chickpeas, cherry tomatoes, kalamata olives, diced cucumbers, and lemon-tahini dressing.",
                            ingredients = listOf("1 cup Cooked quinoa", "1 cup Spiced roasted chickpeas", "1 cup Chopped cucumber & tomatoes", "2 tbsp Lemon tahini dressing", "1 tbsp Pumpkin seeds"),
                            prepTimeMin = 15,
                            calories = (550 * scale).toInt(),
                            proteinGrams = (24f * scale),
                            carbsGrams = (72f * scale),
                            fatGrams = (18f * scale),
                            fiberGrams = (16f * scale)
                        )
                    }
                }
            }

            "Dinner" -> {
                when {
                    cuisine.contains("South Asian", ignoreCase = true) -> {
                        PlannedMealItem(
                            mealType = "Dinner",
                            recipeTitle = if (isVegetarian) "Palak Tofu/Paneer with Roasted Turmeric Cauliflower" else "Tandoori Spiced Chicken Breast with Methi Saag & Roti",
                            description = "Vibrant iron-rich spinach gravy infused with garlic and garam masala paired with spiced vegetables and warm flatbread.",
                            ingredients = listOf(if (isVegetarian) "140g Paneer/Tofu" else "150g Free-range chicken", "2 cups Fresh spinach puree", "1 cup Spiced gobhi", "1 Whole wheat chapati", "1 tsp Olive oil"),
                            prepTimeMin = 22,
                            calories = (570 * scale).toInt(),
                            proteinGrams = (if (isHighProtein) 44f else 32f) * scale,
                            carbsGrams = (48f * scale),
                            fatGrams = (20f * scale),
                            fiberGrams = (11f * scale)
                        )
                    }
                    cuisine.contains("East Asian", ignoreCase = true) -> {
                        PlannedMealItem(
                            mealType = "Dinner",
                            recipeTitle = "Ginger Garlic Tofu & Baby Bok Choy Stir-Fry",
                            description = "Crisp pan-seared firm tofu and tender baby bok choy tossed in a fragrant ginger, garlic, and toasted sesame oil glaze over pearl barley.",
                            ingredients = listOf("160g Firm tofu", "2 cups Baby bok choy", "1/2 cup Sliced shiitake", "3/4 cup Cooked pearl barley", "1 tbsp Sesame ginger glaze"),
                            prepTimeMin = 20,
                            calories = (530 * scale).toInt(),
                            proteinGrams = (30f * scale),
                            carbsGrams = (54f * scale),
                            fatGrams = (18f * scale),
                            fiberGrams = (12f * scale)
                        )
                    }
                    else -> {
                        PlannedMealItem(
                            mealType = "Dinner",
                            recipeTitle = if (isVegetarian) "Lentil Shepherd's Skillet with Sweet Potato Crust" else "Grilled Herb-Crusted Wild Salmon with Roasted Asparagus",
                            description = if (isVegetarian) "Rich brown lentil and vegetable stew baked with a velvety mashed sweet potato top." else "Wild salmon fillet pan-seared with fresh dill, served alongside charred asparagus and roasted sweet potato wedges.",
                            ingredients = listOf(if (isVegetarian) "1.5 cups French green lentils" else "150g Wild Salmon fillet", "1 medium Roasted sweet potato", "1.5 cups Asparagus spears", "1 tbsp Extra virgin olive oil"),
                            prepTimeMin = 25,
                            calories = (590 * scale).toInt(),
                            proteinGrams = (if (isVegetarian) 28f else 40f) * scale,
                            carbsGrams = (46f * scale),
                            fatGrams = (22f * scale),
                            fiberGrams = (9f * scale)
                        )
                    }
                }
            }

            else -> {
                // Smart Snack
                when (dayIndex % 3) {
                    0 -> PlannedMealItem(
                        mealType = "Smart Snack",
                        recipeTitle = "Strained Greek Yogurt Parfait with Pepitas",
                        description = "High-protein unsweetened Greek yogurt topped with raw pumpkin seeds and a touch of raw organic honey.",
                        ingredients = listOf("3/4 cup Plain Greek yogurt (or Coconut yogurt)", "1 tbsp Raw pumpkin seeds", "1 tsp Raw honey"),
                        prepTimeMin = 3,
                        calories = (190 * scale).toInt(),
                        proteinGrams = (16f * scale),
                        carbsGrams = (14f * scale),
                        fatGrams = (7f * scale),
                        fiberGrams = (3f * scale)
                    )
                    1 -> PlannedMealItem(
                        mealType = "Smart Snack",
                        recipeTitle = "Crisp Apple Slices with Raw Almond Butter",
                        description = "Enzyme-rich Gala apple slices paired with stone-ground raw almond butter.",
                        ingredients = listOf("1 Organic apple", "1.5 tbsp Stone-ground almond butter"),
                        prepTimeMin = 2,
                        calories = (210 * scale).toInt(),
                        proteinGrams = (5f * scale),
                        carbsGrams = (26f * scale),
                        fatGrams = (12f * scale),
                        fiberGrams = (6f * scale)
                    )
                    else -> PlannedMealItem(
                        mealType = "Smart Snack",
                        recipeTitle = "Medjool Date Stuffed with Walnut & Raw Cacao",
                        description = "Caramel-sweet Medjool date stuffed with raw English walnut and dusted with pure polyphenolic cacao.",
                        ingredients = listOf("1 Large Medjool date", "1 Walnut half", "1/2 tsp Raw cacao powder"),
                        prepTimeMin = 2,
                        calories = (120 * scale).toInt(),
                        proteinGrams = (3f * scale),
                        carbsGrams = (22f * scale),
                        fatGrams = (5f * scale),
                        fiberGrams = (3f * scale)
                    )
                }
            }
        }

        return item
    }

    suspend fun saveMealPlan(plan: UserDietPlan) = withContext(Dispatchers.IO) {
        val json = JSONObject().apply {
            put("id", plan.id)
            put("title", plan.title)
            put("goal", plan.primaryGoal)
            put("dietaryPattern", plan.dietaryPattern)
            put("cal", plan.targetCaloriesPerDay)
            put("prot", plan.targetProteinGrams)
            put("carb", plan.targetCarbsGrams)
            put("fat", plan.targetFatGrams)
            val daysArray = JSONArray()
            plan.days.forEach { day ->
                val dayObj = JSONObject().apply {
                    put("dayNumber", day.dayNumber)
                    put("dayName", day.dayName)
                    val mealsArray = JSONArray()
                    day.meals.forEach { meal ->
                        val mealObj = JSONObject().apply {
                            put("mealType", meal.mealType)
                            put("recipeTitle", meal.recipeTitle)
                            put("description", meal.description)
                            put("prepTimeMin", meal.prepTimeMin)
                            put("cal", meal.calories)
                            put("p", meal.proteinGrams)
                            put("c", meal.carbsGrams)
                            put("f", meal.fatGrams)
                            put("fib", meal.fiberGrams)
                            put("ingredients", JSONArray(meal.ingredients))
                        }
                        mealsArray.put(mealObj)
                    }
                    put("meals", mealsArray)
                }
                daysArray.put(dayObj)
            }
            put("days", daysArray)
        }.toString()

        val entity = MealPlanEntity(
            id = plan.id,
            title = plan.title,
            planJson = json
        )
        dao.insertMealPlan(entity)

        val uid = authService.currentUserId
        if (uid != null) {
            try {
                firestoreSyncService.saveDietPlanToCloud(uid, plan, json)
            } catch (e: Exception) {
                Log.w("VitaVueRepository", "Failed to save cloud diet plan: ${e.message}")
            }
        }
    }

    fun getAllSavedMealPlansFlow(): Flow<List<UserDietPlan>> {
        return dao.getAllMealPlans().map { list ->
            list.mapNotNull { entity ->
                parseMealPlanJson(entity.planJson)
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteMealPlan(id: String) = withContext(Dispatchers.IO) {
        dao.deleteMealPlanById(id)
        val uid = authService.currentUserId
        if (uid != null) {
            try {
                firestoreSyncService.deleteDietPlanFromCloud(uid, id)
            } catch (e: Exception) {
                Log.w("VitaVueRepository", "Failed to delete cloud diet plan: ${e.message}")
            }
        }
    }

    // --- USER PROFILE ---
    fun getUserProfileFlow(): Flow<UserProfile> {
        return dao.getUserProfileFlow().map { entity ->
            if (entity == null) {
                UserProfile()
            } else {
                UserProfile(
                    id = entity.id,
                    name = entity.name,
                    goal = entity.goal,
                    dietaryPattern = entity.dietaryPattern,
                    allergies = entity.allergiesCsv.split(",").filter { it.isNotBlank() },
                    foodsToAvoid = entity.foodsToAvoidCsv.split(",").filter { it.isNotBlank() },
                    cuisinePreferences = entity.cuisinePreferencesCsv.split(",").filter { it.isNotBlank() },
                    dailyCalorieTarget = entity.dailyCalorieTarget,
                    isMetric = entity.isMetric,
                    isDarkMode = entity.isDarkMode
                )
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun saveUserProfile(profile: UserProfile) = withContext(Dispatchers.IO) {
        saveUserProfileToLocal(profile)
        val uid = authService.currentUserId
        if (uid != null) {
            try {
                firestoreSyncService.saveUserProfileToCloud(uid, profile, authService.getCurrentUser()?.email)
            } catch (e: Exception) {
                Log.w("VitaVueRepository", "Failed to save cloud user profile: ${e.message}")
            }
        }
    }

    suspend fun saveUserProfileToLocal(profile: UserProfile) = withContext(Dispatchers.IO) {
        val entity = UserProfileEntity(
            id = "user_me",
            name = profile.name,
            goal = profile.goal,
            dietaryPattern = profile.dietaryPattern,
            allergiesCsv = profile.allergies.joinToString(","),
            foodsToAvoidCsv = profile.foodsToAvoid.joinToString(","),
            cuisinePreferencesCsv = profile.cuisinePreferences.joinToString(","),
            dailyCalorieTarget = profile.dailyCalorieTarget,
            isMetric = profile.isMetric,
            isDarkMode = profile.isDarkMode
        )
        dao.insertUserProfile(entity)
    }

    private fun entityToAnalysisResult(entity: AnalysisEntity): FoodAnalysisResult {
        val items = mutableListOf<FoodComponent>()
        try {
            val array = JSONArray(entity.detectedItemsJson)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                items.add(
                    FoodComponent(
                        name = obj.optString("name", "Food Item"),
                        portionEstimate = obj.optString("portion", "1 portion"),
                        calories = obj.optInt("cal", 100),
                        proteinGrams = obj.optDouble("p", 5.0).toFloat(),
                        carbsGrams = obj.optDouble("c", 15.0).toFloat(),
                        fatGrams = obj.optDouble("f", 3.0).toFloat(),
                        fiberGrams = obj.optDouble("fib", 2.0).toFloat()
                    )
                )
            }
        } catch (_: Exception) {}

        val micros = mutableListOf<Micronutrient>()
        try {
            val array = JSONArray(entity.micronutrientsJson)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                val dv = obj.optInt("dv", -1)
                micros.add(
                    Micronutrient(
                        name = obj.optString("name", "Nutrient"),
                        amount = obj.optString("amount", "10 mg"),
                        dailyValuePercent = if (dv >= 0) dv else null,
                        benefit = obj.optString("benefit", "")
                    )
                )
            }
        } catch (_: Exception) {}

        val highlights = mutableListOf<String>()
        try {
            val array = JSONArray(entity.highlightsJson)
            for (i in 0 until array.length()) {
                highlights.add(array.getString(i))
            }
        } catch (_: Exception) {}

        val suggestions = mutableListOf<String>()
        try {
            val array = JSONArray(entity.suggestionsJson)
            for (i in 0 until array.length()) {
                suggestions.add(array.getString(i))
            }
        } catch (_: Exception) {}

        return FoodAnalysisResult(
            id = entity.id,
            mealTitle = entity.mealTitle,
            mealDescription = entity.mealDescription,
            detectedItems = items,
            totalCalories = entity.totalCalories,
            totalProteinGrams = entity.totalProteinGrams,
            totalCarbsGrams = entity.totalCarbsGrams,
            totalFatGrams = entity.totalFatGrams,
            totalFiberGrams = entity.totalFiberGrams,
            micronutrients = micros,
            nutritionHighlights = highlights,
            practicalSuggestions = suggestions,
            confidenceRating = entity.confidenceRating,
            uncertaintyNote = entity.uncertaintyNote,
            imageUri = entity.imageUri,
            timestamp = entity.timestamp
        )
    }

    private fun parseMealPlanJson(jsonString: String): UserDietPlan? {
        return try {
            val root = JSONObject(jsonString)
            val daysList = mutableListOf<DayMealPlan>()
            val daysArray = root.getJSONArray("days")
            for (i in 0 until daysArray.length()) {
                val dayObj = daysArray.getJSONObject(i)
                val mealsList = mutableListOf<PlannedMealItem>()
                val mealsArray = dayObj.getJSONArray("meals")
                for (j in 0 until mealsArray.length()) {
                    val m = mealsArray.getJSONObject(j)
                    val ingArray = m.optJSONArray("ingredients")
                    val ingredients = mutableListOf<String>()
                    if (ingArray != null) {
                        for (k in 0 until ingArray.length()) {
                            ingredients.add(ingArray.getString(k))
                        }
                    }
                    mealsList.add(
                        PlannedMealItem(
                            mealType = m.optString("mealType", "Meal"),
                            recipeTitle = m.optString("recipeTitle", "Recipe"),
                            description = m.optString("description", ""),
                            ingredients = ingredients,
                            prepTimeMin = m.optInt("prepTimeMin", 15),
                            calories = m.optInt("cal", 350),
                            proteinGrams = m.optDouble("p", 20.0).toFloat(),
                            carbsGrams = m.optDouble("c", 40.0).toFloat(),
                            fatGrams = m.optDouble("f", 12.0).toFloat(),
                            fiberGrams = m.optDouble("fib", 5.0).toFloat()
                        )
                    )
                }
                daysList.add(
                    DayMealPlan(
                        dayNumber = dayObj.optInt("dayNumber", i + 1),
                        dayName = dayObj.optString("dayName", "Day ${i + 1}"),
                        meals = mealsList
                    )
                )
            }

            UserDietPlan(
                id = root.optString("id", System.currentTimeMillis().toString()),
                title = root.optString("title", "Saved Meal Plan"),
                primaryGoal = root.optString("goal", "Balanced Nutrition"),
                dietaryPattern = root.optString("dietaryPattern", "Flexible"),
                targetCaloriesPerDay = root.optInt("cal", 2000),
                targetProteinGrams = root.optInt("prot", 100),
                targetCarbsGrams = root.optInt("carb", 200),
                targetFatGrams = root.optInt("fat", 60),
                days = daysList
            )
        } catch (_: Exception) {
            null
        }
    }
}
