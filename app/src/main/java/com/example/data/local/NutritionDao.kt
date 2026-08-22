package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NutritionDao {
    // Food Analyses
    @Query("SELECT * FROM food_analyses ORDER BY timestamp DESC")
    fun getAllAnalyses(): Flow<List<AnalysisEntity>>

    @Query("SELECT * FROM food_analyses WHERE id = :id LIMIT 1")
    suspend fun getAnalysisById(id: String): AnalysisEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnalysis(analysis: AnalysisEntity)

    @Query("DELETE FROM food_analyses WHERE id = :id")
    suspend fun deleteAnalysisById(id: String)

    @Query("DELETE FROM food_analyses")
    suspend fun clearAllAnalyses()

    // Saved Foods
    @Query("SELECT * FROM saved_foods ORDER BY savedAt DESC")
    fun getAllSavedFoods(): Flow<List<SavedFoodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedFood(savedFood: SavedFoodEntity)

    @Query("DELETE FROM saved_foods WHERE foodId = :foodId")
    suspend fun deleteSavedFood(foodId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_foods WHERE foodId = :foodId)")
    suspend fun isFoodSaved(foodId: String): Boolean

    // Article Bookmarks
    @Query("SELECT * FROM article_bookmarks ORDER BY bookmarkedAt DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM article_bookmarks WHERE articleSlug = :slug")
    suspend fun deleteBookmark(slug: String)

    @Query("SELECT EXISTS(SELECT 1 FROM article_bookmarks WHERE articleSlug = :slug)")
    suspend fun isArticleBookmarked(slug: String): Boolean

    // Meal Plans
    @Query("SELECT * FROM saved_meal_plans ORDER BY createdAt DESC")
    fun getAllMealPlans(): Flow<List<MealPlanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealPlan(mealPlan: MealPlanEntity)

    @Query("DELETE FROM saved_meal_plans WHERE id = :id")
    suspend fun deleteMealPlanById(id: String)

    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 'user_me' LIMIT 1")
    fun getUserProfileFlow(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(profile: UserProfileEntity)
}
