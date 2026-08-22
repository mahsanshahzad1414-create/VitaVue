package com.example.data.repository

import android.content.Context
import android.graphics.Bitmap
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
import com.example.data.remote.GeminiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class VitaVueRepository(private val context: Context) {

    private val db = VitaVueDatabase.getInstance(context)
    private val dao: NutritionDao = db.nutritionDao()
    private val geminiService = GeminiService()

    // --- AI ANALYSIS ---
    suspend fun analyzeFood(bitmap: Bitmap?, presetTitle: String? = null): FoodAnalysisResult {
        val result = geminiService.analyzeFoodImage(bitmap, presetTitle)
        // Automatically save analysis to history in Room database
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

    // --- HISTORY IN ROOM ---
    suspend fun saveAnalysisToHistory(analysis: FoodAnalysisResult) = withContext(Dispatchers.IO) {
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
        if (dao.isFoodSaved(foodId)) {
            dao.deleteSavedFood(foodId)
        } else {
            dao.insertSavedFood(SavedFoodEntity(foodId))
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
        if (dao.isArticleBookmarked(slug)) {
            dao.deleteBookmark(slug)
        } else {
            dao.insertBookmark(BookmarkEntity(slug))
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

        for (i in 0 until daysCount) {
            val dayName = daysOfWeek.getOrElse(i) { "Day ${i + 1}" }
            val meals = when (i % 3) {
                0 -> listOf(
                    PlannedMealItem(
                        mealType = "Breakfast",
                        recipeTitle = "Chia Berry Power Pudding with Walnuts",
                        description = "Overnight soaked chia seeds in almond milk topped with wild blueberries, crushed walnuts, and cinnamon.",
                        ingredients = listOf("3 tbsp Chia seeds", "1 cup Unsweetened almond milk", "1/2 cup Blueberries", "15g Crushed walnuts", "1 tsp Ceylon cinnamon"),
                        prepTimeMin = 5,
                        calories = 380,
                        proteinGrams = 12f,
                        carbsGrams = 32f,
                        fatGrams = 22f,
                        fiberGrams = 14f
                    ),
                    PlannedMealItem(
                        mealType = "Lunch",
                        recipeTitle = "Mediterranean Quinoa Bowl with Baked Chickpeas",
                        description = "Fluffy tri-color quinoa, spiced roasted chickpeas, cherry tomatoes, kalamata olives, and lemon-tahini dressing.",
                        ingredients = listOf("1 cup Cooked quinoa", "1 cup Spiced chickpeas", "1 cup Chopped cucumber & tomatoes", "2 tbsp Lemon tahini dressing", "1 tbsp Pumpkin seeds"),
                        prepTimeMin = 15,
                        calories = 540,
                        proteinGrams = 22f,
                        carbsGrams = 72f,
                        fatGrams = 18f,
                        fiberGrams = 16f
                    ),
                    PlannedMealItem(
                        mealType = "Dinner",
                        recipeTitle = "Grilled Herb Salmon with Steamed Bok Choy & Brown Basmati",
                        description = "Wild salmon fillet pan-seared with fresh dill, served alongside sesame-steamed bok choy and brown basmati rice.",
                        ingredients = listOf("140g Wild Salmon fillet", "3/4 cup Cooked Brown Basmati", "2 cups Steamed bok choy", "1 tsp Toasted sesame oil", "Fresh dill & lemon"),
                        prepTimeMin = 20,
                        calories = 580,
                        proteinGrams = 38f,
                        carbsGrams = 42f,
                        fatGrams = 24f,
                        fiberGrams = 6f
                    ),
                    PlannedMealItem(
                        mealType = "Smart Snack",
                        recipeTitle = "Greek Yogurt Parfait with Pumpkin Seeds",
                        description = "Plain strained 2% Greek yogurt with a sprinkle of raw pepitas and a drizzle of raw honey.",
                        ingredients = listOf("3/4 cup Greek yogurt", "1 tbsp Raw pumpkin seeds", "1 tsp Raw honey"),
                        prepTimeMin = 3,
                        calories = 190,
                        proteinGrams = 18f,
                        carbsGrams = 12f,
                        fatGrams = 6f,
                        fiberGrams = 2f
                    )
                )
                1 -> listOf(
                    PlannedMealItem(
                        mealType = "Breakfast",
                        recipeTitle = "Avocado & Poached Egg Toast with Microgreens",
                        description = "Whole-grain sourdough toast layered with crushed Hass avocado, two soft poached pasture-raised eggs, and red pepper flakes.",
                        ingredients = listOf("1 slice Whole-grain sourdough", "1/2 Avocado", "2 Pasture-raised eggs", "Microgreens", "Chili flakes"),
                        prepTimeMin = 10,
                        calories = 420,
                        proteinGrams = 19f,
                        carbsGrams = 30f,
                        fatGrams = 24f,
                        fiberGrams = 7f
                    ),
                    PlannedMealItem(
                        mealType = "Lunch",
                        recipeTitle = "Hearty South Asian Yellow Dal Tadka & Spiced Sabzi",
                        description = "Comforting yellow dal with cumin-garlic tarka, sautéed turmeric cauliflower & peas, and a warm whole wheat chapati.",
                        ingredients = listOf("1.5 cups Yellow Moong/Masoor Dal", "1 cup Spiced Aloo Gobi sabzi", "1 Handmade Whole Wheat Roti", "Fresh cilantro"),
                        prepTimeMin = 20,
                        calories = 510,
                        proteinGrams = 24f,
                        carbsGrams = 74f,
                        fatGrams = 12f,
                        fiberGrams = 15f
                    ),
                    PlannedMealItem(
                        mealType = "Dinner",
                        recipeTitle = "Grilled Chicken Breast with Charred Sweet Potato & Asparagus",
                        description = "Lemon-herb grilled chicken breast paired with roasted cinnamon sweet potato wedges and garlic asparagus.",
                        ingredients = listOf("150g Free-range chicken breast", "1 medium Sweet potato", "1 cup Asparagus spears", "1 tbsp Extra virgin olive oil"),
                        prepTimeMin = 25,
                        calories = 560,
                        proteinGrams = 42f,
                        carbsGrams = 48f,
                        fatGrams = 16f,
                        fiberGrams = 8f
                    ),
                    PlannedMealItem(
                        mealType = "Smart Snack",
                        recipeTitle = "Crisp Apple Slices with Almond Butter",
                        description = "Crisp Gala apple sliced and paired with stone-ground raw almond butter.",
                        ingredients = listOf("1 Medium organic apple", "1.5 tbsp Stone-ground almond butter"),
                        prepTimeMin = 2,
                        calories = 210,
                        proteinGrams = 5f,
                        carbsGrams = 26f,
                        fatGrams = 12f,
                        fiberGrams = 6f
                    )
                )
                else -> listOf(
                    PlannedMealItem(
                        mealType = "Breakfast",
                        recipeTitle = "Spiced Golden Oatmeal with Flax & Banana",
                        description = "Rolled oats simmered with turmeric, ground ginger, cinnamon, ground flaxseeds, and sliced banana.",
                        ingredients = listOf("1/2 cup Rolled oats", "1 cup Oat milk", "1 tbsp Ground flaxseed", "1/2 Banana", "Turmeric & cinnamon"),
                        prepTimeMin = 8,
                        calories = 360,
                        proteinGrams = 11f,
                        carbsGrams = 58f,
                        fatGrams = 9f,
                        fiberGrams = 9f
                    ),
                    PlannedMealItem(
                        mealType = "Lunch",
                        recipeTitle = "Crisp Falafel Salad with Tahini & Kalamata Olives",
                        description = "Baked herb falafels over crisp romaine, diced Persian cucumbers, sumac onions, and lemon tahini dressing.",
                        ingredients = listOf("4 Baked chickpea falafels", "2 cups Romaine lettuce", "1/2 cup Cucumbers & tomatoes", "2 tbsp Tahini dressing", "6 Kalamata olives"),
                        prepTimeMin = 15,
                        calories = 490,
                        proteinGrams = 18f,
                        carbsGrams = 52f,
                        fatGrams = 23f,
                        fiberGrams = 12f
                    ),
                    PlannedMealItem(
                        mealType = "Dinner",
                        recipeTitle = "Tofu & Edamame Veggie Stir-Fry with Brown Rice",
                        description = "Crispy pan-fried firm tofu, sweet edamame pods, broccoli florets, and red bell peppers in a ginger-garlic tamari glaze.",
                        ingredients = listOf("150g Organic firm tofu", "1/2 cup Shelled edamame", "1.5 cups Broccoli & peppers", "3/4 cup Cooked brown rice", "Ginger-tamari sauce"),
                        prepTimeMin = 20,
                        calories = 520,
                        proteinGrams = 32f,
                        carbsGrams = 56f,
                        fatGrams = 17f,
                        fiberGrams = 11f
                    ),
                    PlannedMealItem(
                        mealType = "Smart Snack",
                        recipeTitle = "Medjool Date Stuffed with Walnut & Cacao",
                        description = "Soft Medjool date stuffed with raw walnut half and dusted with pure unsweetened raw cacao.",
                        ingredients = listOf("1 Large Medjool date", "1 Walnut half", "1/2 tsp Raw cacao powder"),
                        prepTimeMin = 2,
                        calories = 110,
                        proteinGrams = 2f,
                        carbsGrams = 20f,
                        fatGrams = 4f,
                        fiberGrams = 3f
                    )
                )
            }
            days.add(DayMealPlan(i + 1, dayName, meals))
        }

        val totalDailyCal = days.firstOrNull()?.meals?.sumOf { it.calories } ?: targetCalories
        val totalDailyProt = days.firstOrNull()?.meals?.sumOf { it.proteinGrams.toDouble() }?.toInt() ?: 110
        val totalDailyCarb = days.firstOrNull()?.meals?.sumOf { it.carbsGrams.toDouble() }?.toInt() ?: 210
        val totalDailyFat = days.firstOrNull()?.meals?.sumOf { it.fatGrams.toDouble() }?.toInt() ?: 65

        return UserDietPlan(
            title = "$goal Precision Meal Plan ($cuisine)",
            primaryGoal = goal,
            dietaryPattern = dietaryPattern,
            targetCaloriesPerDay = totalDailyCal,
            targetProteinGrams = totalDailyProt,
            targetCarbsGrams = totalDailyCarb,
            targetFatGrams = totalDailyFat,
            days = days
        )
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
