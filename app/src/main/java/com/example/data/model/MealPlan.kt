package com.example.data.model

data class PlannedMealItem(
    val mealType: String, // Breakfast, Lunch, Dinner, Snack
    val recipeTitle: String,
    val description: String,
    val ingredients: List<String>,
    val prepTimeMin: Int,
    val calories: Int,
    val proteinGrams: Float,
    val carbsGrams: Float,
    val fatGrams: Float,
    val fiberGrams: Float
)

data class DayMealPlan(
    val dayNumber: Int,
    val dayName: String,
    val meals: List<PlannedMealItem>
)

data class UserDietPlan(
    val id: String = System.currentTimeMillis().toString(),
    val title: String,
    val primaryGoal: String,
    val dietaryPattern: String,
    val targetCaloriesPerDay: Int,
    val targetProteinGrams: Int,
    val targetCarbsGrams: Int,
    val targetFatGrams: Int,
    val days: List<DayMealPlan>,
    val createdAt: Long = System.currentTimeMillis()
)

data class UserProfile(
    val id: String = "default_user",
    val name: String = "Health Explorer",
    val goal: String = "Balanced Nutrition & Meal Intelligence",
    val dietaryPattern: String = "Omnivore / Flexible", // Mediterranean, Vegetarian, Vegan, Halal, Plant-Forward, High-Protein
    val allergies: List<String> = emptyList(), // Peanuts, Tree nuts, Dairy, Gluten, Soy, Shellfish
    val foodsToAvoid: List<String> = emptyList(),
    val cuisinePreferences: List<String> = listOf("Mediterranean", "South Asian", "East Asian", "Whole Foods"),
    val dailyCalorieTarget: Int = 2100,
    val isMetric: Boolean = true,
    val isDarkMode: Boolean = true
)

enum class AgentActionType {
    ANALYZE_MEAL,
    EXPLORE_FOOD,
    READ_ARTICLE,
    OPEN_PLANNER
}

data class AgentAction(
    val title: String,
    val type: AgentActionType,
    val payload: String = "" // id or slug
)

data class ChatMessage(
    val id: String = System.currentTimeMillis().toString() + "_" + (0..999).random(),
    val isUser: Boolean,
    val message: String,
    val timestamp: Long = System.currentTimeMillis(),
    val quickPrompts: List<String> = emptyList(),
    val actions: List<AgentAction> = emptyList(),
    val attachedMealContext: String? = null
)
