package com.example.data.model

enum class FoodCategory(val displayName: String, val iconName: String) {
    FRUITS("Fruits", "eco"),
    VEGETABLES("Vegetables", "spa"),
    GRAINS("Grains & Breads", "grain"),
    LEGUMES("Legumes & Pulses", "scatter_plot"),
    NUTS_SEEDS("Nuts & Seeds", "circle"),
    DAIRY_ALTERNATIVES("Dairy & Alternatives", "local_drink"),
    PROTEINS_SEAFOOD("Proteins & Seafood", "restaurant"),
    OILS_FATS("Oils & Healthy Fats", "water_drop"),
    BEVERAGES("Beverages", "coffee"),
    CULTURAL_MEALS("Prepared Cultural Dishes", "dinner_dining")
}

data class FoodItem(
    val id: String,
    val name: String,
    val category: FoodCategory,
    val servingSize: String,
    val calories: Int,
    val proteinGrams: Float,
    val carbsGrams: Float,
    val fatGrams: Float,
    val fiberGrams: Float,
    val micronutrients: List<Micronutrient> = emptyList(),
    val dietaryTags: List<String> = emptyList(), // "Vegan", "High-Protein", "Gluten-Free", "Low-Carb", "Heart-Healthy", etc.
    val description: String,
    val culinaryNotes: String = "",
    val culturalOrigin: String = "Global",
    val glycemicIndex: String = "Low", // Low, Medium, High
    val isFavorite: Boolean = false
)
