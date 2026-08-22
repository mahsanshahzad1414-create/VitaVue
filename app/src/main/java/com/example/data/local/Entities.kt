package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_analyses")
data class AnalysisEntity(
    @PrimaryKey val id: String,
    val mealTitle: String,
    val mealDescription: String,
    val totalCalories: Int,
    val totalProteinGrams: Float,
    val totalCarbsGrams: Float,
    val totalFatGrams: Float,
    val totalFiberGrams: Float,
    val detectedItemsJson: String,
    val micronutrientsJson: String,
    val highlightsJson: String,
    val suggestionsJson: String,
    val confidenceRating: String,
    val uncertaintyNote: String,
    val imageUri: String?,
    val timestamp: Long
)

@Entity(tableName = "saved_foods")
data class SavedFoodEntity(
    @PrimaryKey val foodId: String,
    val savedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "article_bookmarks")
data class BookmarkEntity(
    @PrimaryKey val articleSlug: String,
    val bookmarkedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_meal_plans")
data class MealPlanEntity(
    @PrimaryKey val id: String,
    val title: String,
    val planJson: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: String = "user_me",
    val name: String,
    val goal: String,
    val dietaryPattern: String,
    val allergiesCsv: String,
    val foodsToAvoidCsv: String,
    val cuisinePreferencesCsv: String,
    val dailyCalorieTarget: Int,
    val isMetric: Boolean,
    val isDarkMode: Boolean
)
