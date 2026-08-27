package com.example.data.remote

import android.util.Log
import com.example.data.model.DayMealPlan
import com.example.data.model.FoodAnalysisResult
import com.example.data.model.FoodComponent
import com.example.data.model.Micronutrient
import com.example.data.model.PlannedMealItem
import com.example.data.model.UserDietPlan
import com.example.data.model.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class FirestoreSyncService {

    private val firestore: FirebaseFirestore? by lazy {
        try {
            FirebaseFirestore.getInstance()
        } catch (e: Exception) {
            Log.w("FirestoreSyncService", "Firestore initialization unavailable: ${e.message}")
            null
        }
    }

    // --- USER PROFILE ---
    suspend fun saveUserProfileToCloud(userId: String, profile: UserProfile, email: String?) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        val userDoc = db.collection("users").document(userId)

        val profileData = hashMapOf(
            "uid" to userId,
            "email" to (email ?: ""),
            "displayName" to profile.name,
            "goal" to profile.goal,
            "dietaryPattern" to profile.dietaryPattern,
            "dailyCalorieTarget" to profile.dailyCalorieTarget,
            "isMetric" to profile.isMetric,
            "isDarkMode" to profile.isDarkMode,
            "allergies" to profile.allergies,
            "foodsToAvoid" to profile.foodsToAvoid,
            "cuisinePreferences" to profile.cuisinePreferences,
            "updatedAt" to System.currentTimeMillis()
        )

        userDoc.set(profileData, SetOptions.merge()).await()
    }

    suspend fun fetchUserProfileFromCloud(userId: String): UserProfile? = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext null
        try {
            val doc = db.collection("users").document(userId).get().await()
            if (!doc.exists()) return@withContext null

            @Suppress("UNCHECKED_CAST")
            val allergiesList = (doc.get("allergies") as? List<String>) ?: emptyList()
            @Suppress("UNCHECKED_CAST")
            val foodsToAvoidList = (doc.get("foodsToAvoid") as? List<String>) ?: emptyList()
            @Suppress("UNCHECKED_CAST")
            val cuisinePrefList = (doc.get("cuisinePreferences") as? List<String>) ?: emptyList()

            UserProfile(
                id = doc.getString("uid") ?: userId,
                name = doc.getString("displayName") ?: "Nutrition Explorer",
                goal = doc.getString("goal") ?: "Balanced Nutrition",
                dietaryPattern = doc.getString("dietaryPattern") ?: "Mediterranean",
                dailyCalorieTarget = doc.getLong("dailyCalorieTarget")?.toInt() ?: 2100,
                isMetric = doc.getBoolean("isMetric") ?: true,
                isDarkMode = doc.getBoolean("isDarkMode") ?: true,
                allergies = allergiesList,
                foodsToAvoid = foodsToAvoidList,
                cuisinePreferences = cuisinePrefList
            )
        } catch (e: Exception) {
            Log.w("FirestoreSyncService", "Failed to fetch cloud profile: ${e.message}")
            null
        }
    }

    // --- MEAL ANALYSES ---
    suspend fun saveMealAnalysisToCloud(userId: String, analysis: FoodAnalysisResult) = withContext(Dispatchers.IO) {
        if (analysis.detectedItems.isEmpty() && analysis.totalCalories <= 0) {
            throw IllegalArgumentException("Cannot save invalid or empty meal analysis to cloud.")
        }
        val db = firestore ?: return@withContext

        val detectedList = analysis.detectedItems.map {
            mapOf(
                "name" to it.name,
                "portion" to it.portionEstimate,
                "calories" to it.calories,
                "proteinGrams" to it.proteinGrams.toDouble(),
                "carbsGrams" to it.carbsGrams.toDouble(),
                "fatGrams" to it.fatGrams.toDouble(),
                "fiberGrams" to it.fiberGrams.toDouble()
            )
        }

        val microList = analysis.micronutrients.map {
            mapOf(
                "name" to it.name,
                "amount" to it.amount,
                "dailyValuePercent" to (it.dailyValuePercent ?: -1),
                "benefit" to it.benefit
            )
        }

        val analysisData = hashMapOf(
            "analysisId" to analysis.id,
            "userId" to userId,
            "timestamp" to analysis.timestamp,
            "mealTitle" to analysis.mealTitle,
            "mealDescription" to analysis.mealDescription,
            "totalCalories" to analysis.totalCalories,
            "totalProteinGrams" to analysis.totalProteinGrams.toDouble(),
            "totalCarbsGrams" to analysis.totalCarbsGrams.toDouble(),
            "totalFatGrams" to analysis.totalFatGrams.toDouble(),
            "totalFiberGrams" to analysis.totalFiberGrams.toDouble(),
            "confidenceRating" to analysis.confidenceRating,
            "uncertaintyNote" to analysis.uncertaintyNote,
            "detectedItems" to detectedList,
            "micronutrients" to microList,
            "nutritionHighlights" to analysis.nutritionHighlights,
            "practicalSuggestions" to analysis.practicalSuggestions,
            "imageProvenanceId" to "img_prov_${analysis.timestamp}_${analysis.id.takeLast(6)}"
        )

        db.collection("users")
            .document(userId)
            .collection("mealAnalyses")
            .document(analysis.id)
            .set(analysisData, SetOptions.merge())
            .await()
    }

    suspend fun fetchAllMealAnalysesFromCloud(userId: String): List<FoodAnalysisResult> = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext emptyList()
        try {
            val snapshot = db.collection("users")
                .document(userId)
                .collection("mealAnalyses")
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                try {
                    val id = doc.getString("analysisId") ?: doc.id
                    val title = doc.getString("mealTitle") ?: "Meal Analysis"
                    val desc = doc.getString("mealDescription") ?: ""
                    val calories = doc.getLong("totalCalories")?.toInt() ?: 0
                    val protein = doc.getDouble("totalProteinGrams")?.toFloat() ?: 0f
                    val carbs = doc.getDouble("totalCarbsGrams")?.toFloat() ?: 0f
                    val fat = doc.getDouble("totalFatGrams")?.toFloat() ?: 0f
                    val fiber = doc.getDouble("totalFiberGrams")?.toFloat() ?: 0f
                    val confidence = doc.getString("confidenceRating") ?: "MODERATE"
                    val uncertainty = doc.getString("uncertaintyNote") ?: ""
                    val timestamp = doc.getLong("timestamp") ?: System.currentTimeMillis()

                    @Suppress("UNCHECKED_CAST")
                    val itemsRaw = doc.get("detectedItems") as? List<Map<String, Any>> ?: emptyList()
                    val detectedItems = itemsRaw.map {
                        FoodComponent(
                            name = it["name"] as? String ?: "Food Item",
                            portionEstimate = it["portion"] as? String ?: "1 serving",
                            calories = (it["calories"] as? Number)?.toInt() ?: 0,
                            proteinGrams = (it["proteinGrams"] as? Number)?.toFloat() ?: 0f,
                            carbsGrams = (it["carbsGrams"] as? Number)?.toFloat() ?: 0f,
                            fatGrams = (it["fatGrams"] as? Number)?.toFloat() ?: 0f,
                            fiberGrams = (it["fiberGrams"] as? Number)?.toFloat() ?: 0f
                        )
                    }

                    @Suppress("UNCHECKED_CAST")
                    val microsRaw = doc.get("micronutrients") as? List<Map<String, Any>> ?: emptyList()
                    val micronutrients = microsRaw.map {
                        val dv = (it["dailyValuePercent"] as? Number)?.toInt()
                        Micronutrient(
                            name = it["name"] as? String ?: "Nutrient",
                            amount = it["amount"] as? String ?: "",
                            dailyValuePercent = if (dv != null && dv >= 0) dv else null,
                            benefit = it["benefit"] as? String ?: ""
                        )
                    }

                    @Suppress("UNCHECKED_CAST")
                    val highlights = (doc.get("nutritionHighlights") as? List<String>) ?: emptyList()
                    @Suppress("UNCHECKED_CAST")
                    val suggestions = (doc.get("practicalSuggestions") as? List<String>) ?: emptyList()

                    FoodAnalysisResult(
                        id = id,
                        mealTitle = title,
                        mealDescription = desc,
                        detectedItems = detectedItems,
                        totalCalories = calories,
                        totalProteinGrams = protein,
                        totalCarbsGrams = carbs,
                        totalFatGrams = fat,
                        totalFiberGrams = fiber,
                        micronutrients = micronutrients,
                        nutritionHighlights = highlights,
                        practicalSuggestions = suggestions,
                        confidenceRating = confidence,
                        uncertaintyNote = uncertainty,
                        imageUri = null,
                        timestamp = timestamp
                    )
                } catch (e: Exception) {
                    null
                }
            }
        } catch (e: Exception) {
            Log.w("FirestoreSyncService", "Failed to fetch cloud analyses: ${e.message}")
            emptyList()
        }
    }

    suspend fun deleteMealAnalysisFromCloud(userId: String, analysisId: String) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        db.collection("users")
            .document(userId)
            .collection("mealAnalyses")
            .document(analysisId)
            .delete()
            .await()
    }

    // --- SAVED FOODS ---
    suspend fun saveSavedFoodToCloud(userId: String, foodId: String) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        val data = hashMapOf(
            "foodId" to foodId,
            "userId" to userId,
            "savedAt" to System.currentTimeMillis()
        )
        db.collection("users")
            .document(userId)
            .collection("savedFoods")
            .document(foodId)
            .set(data, SetOptions.merge())
            .await()
    }

    suspend fun deleteSavedFoodFromCloud(userId: String, foodId: String) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        db.collection("users")
            .document(userId)
            .collection("savedFoods")
            .document(foodId)
            .delete()
            .await()
    }

    suspend fun fetchSavedFoodIdsFromCloud(userId: String): Set<String> = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext emptySet()
        try {
            val snapshot = db.collection("users")
                .document(userId)
                .collection("savedFoods")
                .get()
                .await()
            snapshot.documents.mapNotNull { it.getString("foodId") ?: it.id }.toSet()
        } catch (e: Exception) {
            Log.w("FirestoreSyncService", "Failed to fetch saved foods from cloud: ${e.message}")
            emptySet()
        }
    }

    // --- BOOKMARKS ---
    suspend fun saveBookmarkToCloud(userId: String, articleSlug: String) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        val data = hashMapOf(
            "articleSlug" to articleSlug,
            "bookmarkId" to articleSlug,
            "userId" to userId,
            "bookmarkedAt" to System.currentTimeMillis()
        )
        db.collection("users")
            .document(userId)
            .collection("bookmarks")
            .document(articleSlug)
            .set(data, SetOptions.merge())
            .await()
    }

    suspend fun deleteBookmarkFromCloud(userId: String, articleSlug: String) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        db.collection("users")
            .document(userId)
            .collection("bookmarks")
            .document(articleSlug)
            .delete()
            .await()
    }

    suspend fun fetchBookmarkSlugsFromCloud(userId: String): Set<String> = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext emptySet()
        try {
            val snapshot = db.collection("users")
                .document(userId)
                .collection("bookmarks")
                .get()
                .await()
            snapshot.documents.mapNotNull { it.getString("articleSlug") ?: it.id }.toSet()
        } catch (e: Exception) {
            Log.w("FirestoreSyncService", "Failed to fetch bookmarks from cloud: ${e.message}")
            emptySet()
        }
    }

    // --- DIET PLANS ---
    suspend fun saveDietPlanToCloud(userId: String, plan: UserDietPlan, planJson: String) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        val data = hashMapOf(
            "planId" to plan.id,
            "userId" to userId,
            "title" to plan.title,
            "primaryGoal" to plan.primaryGoal,
            "dietaryPattern" to plan.dietaryPattern,
            "targetCaloriesPerDay" to plan.targetCaloriesPerDay,
            "targetProteinGrams" to plan.targetProteinGrams,
            "targetCarbsGrams" to plan.targetCarbsGrams,
            "targetFatGrams" to plan.targetFatGrams,
            "planJson" to planJson,
            "createdAt" to System.currentTimeMillis()
        )
        db.collection("users")
            .document(userId)
            .collection("dietPlans")
            .document(plan.id)
            .set(data, SetOptions.merge())
            .await()
    }

    suspend fun deleteDietPlanFromCloud(userId: String, planId: String) = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext
        db.collection("users")
            .document(userId)
            .collection("dietPlans")
            .document(planId)
            .delete()
            .await()
    }

    suspend fun fetchDietPlansFromCloud(userId: String): List<Pair<String, String>> = withContext(Dispatchers.IO) {
        val db = firestore ?: return@withContext emptyList()
        try {
            val snapshot = db.collection("users")
                .document(userId)
                .collection("dietPlans")
                .get()
                .await()
            snapshot.documents.mapNotNull { doc ->
                val planId = doc.getString("planId") ?: doc.id
                val json = doc.getString("planJson")
                if (json != null) Pair(planId, json) else null
            }
        } catch (e: Exception) {
            Log.w("FirestoreSyncService", "Failed to fetch diet plans from cloud: ${e.message}")
            emptyList()
        }
    }
}
