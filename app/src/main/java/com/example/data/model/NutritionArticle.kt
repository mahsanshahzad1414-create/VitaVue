package com.example.data.model

enum class ArticleCategory(val title: String) {
    MACRONUTRIENTS("Macronutrients"),
    VITAMINS("Vitamins"),
    MINERALS("Minerals"),
    FUNDAMENTALS("Fundamentals"),
    PRACTICAL("Practical Nutrition"),
    PATTERNS("Dietary Patterns"),
    ACTIVE("Active Lifestyle"),
    LIFE_STAGES("Life-Stage Nutrition")
}

data class ArticleSection(
    val heading: String,
    val content: String
)

data class NutritionArticle(
    val id: String,
    val slug: String,
    val title: String,
    val summary: String,
    val category: ArticleCategory,
    val readingTimeMin: Int,
    val difficulty: String = "Beginner", // Beginner, Intermediate, In-Depth
    val sections: List<ArticleSection> = emptyList(),
    val keyTakeaways: List<String> = emptyList(),
    val relatedTopicSlugs: List<String> = emptyList(),
    val isBookmarked: Boolean = false
)

data class NutritionMyth(
    val id: String,
    val myth: String,
    val fact: String,
    val evidenceExplanation: String,
    val practicalTip: String,
    val category: String = "General Nutrition"
)
