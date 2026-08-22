package com.example.data.model

data class FoodComponent(
    val name: String,
    val portionEstimate: String,
    val calories: Int,
    val proteinGrams: Float,
    val carbsGrams: Float,
    val fatGrams: Float,
    val fiberGrams: Float
)

data class Micronutrient(
    val name: String,
    val amount: String,
    val dailyValuePercent: Int? = null,
    val benefit: String = ""
)

data class FoodAnalysisResult(
    val id: String = System.currentTimeMillis().toString(),
    val mealTitle: String,
    val mealDescription: String,
    val detectedItems: List<FoodComponent> = emptyList(),
    val totalCalories: Int,
    val totalProteinGrams: Float,
    val totalCarbsGrams: Float,
    val totalFatGrams: Float,
    val totalFiberGrams: Float,
    val micronutrients: List<Micronutrient> = emptyList(),
    val nutritionHighlights: List<String> = emptyList(),
    val practicalSuggestions: List<String> = emptyList(),
    val confidenceRating: String = "High", // High, Medium, Approximate
    val uncertaintyNote: String = "Estimated nutrition based on visual representation. Actual cooking oils, portion depth, and ingredients may vary.",
    val imageUri: String? = null,
    val presetImageRes: Int? = null,
    val timestamp: Long = System.currentTimeMillis()
)
