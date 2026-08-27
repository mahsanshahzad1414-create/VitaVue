package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FoodAnalysisResult
import com.example.ui.components.MacroDistributionBar
import com.example.ui.theme.Amber400
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Navy700
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy850
import com.example.ui.theme.Navy900
import com.example.ui.theme.Navy950
import com.example.ui.theme.Neutral100
import com.example.ui.theme.Neutral300
import com.example.ui.theme.Neutral400
import com.example.ui.theme.Neutral50
import com.example.ui.theme.Teal300
import com.example.ui.theme.Teal400
import com.example.ui.theme.Teal500
import com.example.ui.viewmodel.VitaVueViewModel

@Composable
fun MyNutritionScreen(
    viewModel: VitaVueViewModel,
    modifier: Modifier = Modifier
) {
    val analysisHistory by viewModel.analysisHistory.collectAsState()
    val savedFoodIds by viewModel.savedFoodIds.collectAsState()
    val bookmarkedSlugs by viewModel.bookmarkedArticleSlugs.collectAsState()
    val savedPlans by viewModel.savedMealPlans.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }
    val tabTitles = listOf("Meal Analyses", "Saved Foods", "Bookmarks", "Saved Plans")

    val allFoods = viewModel.getAllFoods()
    val savedFoodsList = allFoods.filter { savedFoodIds.contains(it.id) }

    val allArticles = viewModel.getAllArticles()
    val bookmarkedArticlesList = allArticles.filter { bookmarkedSlugs.contains(it.slug) }

    val totalCalLogged = analysisHistory.sumOf { it.totalCalories }
    val totalProtLogged = analysisHistory.sumOf { it.totalProteinGrams.toDouble() }.toInt()
    val totalCarbLogged = analysisHistory.sumOf { it.totalCarbsGrams.toDouble() }.toInt()
    val totalFatLogged = analysisHistory.sumOf { it.totalFatGrams.toDouble() }.toInt()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Navy950)
            .testTag("my_nutrition_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Screen Header
        item {
            Column {
                Text(
                    text = "My Nutrition Intelligence",
                    color = Neutral50,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Your logged meals, saved superfoods, evidence bookmarks, and custom diet plans in local Room persistence.",
                    color = Neutral300,
                    fontSize = 13.sp
                )
            }
        }

        // Daily Aggregation Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .border(1.dp, Teal500.copy(alpha = 0.3f), RoundedCornerShape(18.dp)),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ACCUMULATED LOGGED NUTRITION",
                            color = Neutral400,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        Text(
                            text = "${analysisHistory.size} meals logged",
                            color = Teal400,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$totalCalLogged / ${userProfile.dailyCalorieTarget} kcal",
                            color = Amber400,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        val percent = if (userProfile.dailyCalorieTarget > 0) {
                            ((totalCalLogged.toFloat() / userProfile.dailyCalorieTarget) * 100).toInt()
                        } else 0

                        Text(
                            text = "$percent% of daily target",
                            color = Neutral300,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    MacroDistributionBar(
                        proteinGrams = totalProtLogged.toFloat(),
                        carbsGrams = totalCarbLogged.toFloat(),
                        fatGrams = totalFatLogged.toFloat(),
                        fiberGrams = 20f
                    )
                }
            }
        }

        // Tab Selector
        item {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Navy900,
                contentColor = Teal400,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Teal400
                    )
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Navy700, RoundedCornerShape(12.dp))
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                color = if (selectedTab == index) Teal300 else Neutral400,
                                fontSize = 11.sp,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    )
                }
            }
        }

        // Tab Content
        when (selectedTab) {
            0 -> {
                // Analysis History
                if (analysisHistory.isEmpty()) {
                    item {
                        EmptyStateCard(
                            message = "No meal analyses recorded yet. Scan a meal photo or select a sample dish in the Analyzer!",
                            actionText = "Open Analyzer",
                            onAction = { viewModel.navigateTo("analyze") }
                        )
                    }
                } else {
                    items(analysisHistory) { item ->
                        AnalysisHistoryCard(
                            analysis = item,
                            onAskAgent = { viewModel.setAnalysisContextForAgent(item) },
                            onDelete = { viewModel.deleteAnalysisHistory(item.id) }
                        )
                    }
                }
            }
            1 -> {
                // Saved Foods
                if (savedFoodsList.isEmpty()) {
                    item {
                        EmptyStateCard(
                            message = "No saved foods in your favorites yet. Explore the Food Database and tap the heart icon!",
                            actionText = "Explore 64 Foods",
                            onAction = { viewModel.navigateTo("food_explorer") }
                        )
                    }
                } else {
                    items(savedFoodsList) { food ->
                        FoodExplorerCard(
                            food = food,
                            isSaved = true,
                            onFavoriteToggle = { viewModel.toggleSaveFood(food.id) },
                            onClick = { viewModel.openFoodDetail(food) }
                        )
                    }
                }
            }
            2 -> {
                // Bookmarked Articles
                if (bookmarkedArticlesList.isEmpty()) {
                    item {
                        EmptyStateCard(
                            message = "No bookmarked articles yet. Browse the Knowledge Hub and bookmark topics of interest!",
                            actionText = "Open Knowledge Hub",
                            onAction = { viewModel.navigateTo("learn") }
                        )
                    }
                } else {
                    items(bookmarkedArticlesList) { article ->
                        ArticleCard(
                            article = article,
                            isBookmarked = true,
                            onBookmarkToggle = { viewModel.toggleBookmarkArticle(article.slug) },
                            onClick = { viewModel.openArticleDetail(article) }
                        )
                    }
                }
            }
            3 -> {
                // Saved Meal Plans
                if (savedPlans.isEmpty()) {
                    item {
                        EmptyStateCard(
                            message = "No custom meal plans saved yet. Generate a tailored meal plan in the Diet Planner!",
                            actionText = "Create Diet Plan",
                            onAction = { viewModel.navigateTo("diet_planner") }
                        )
                    }
                } else {
                    items(savedPlans) { plan ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .border(1.dp, Navy700, RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Navy900)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = plan.title,
                                            color = Neutral50,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "${plan.days.size} Days • ~${plan.targetCaloriesPerDay} kcal/day",
                                            color = Neutral400,
                                            fontSize = 12.sp
                                        )
                                    }

                                    IconButton(
                                        onClick = { viewModel.deleteSavedMealPlan(plan.id) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = Color(0xFFEF4444),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AnalysisHistoryCard(
    analysis: FoodAnalysisResult,
    onAskAgent: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Navy700, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Navy900)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = analysis.mealTitle,
                        color = Neutral50,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${analysis.detectedItems.size} components • ${analysis.confidenceRating} confidence",
                        color = Neutral400,
                        fontSize = 11.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Amber400.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "~${analysis.totalCalories} kcal",
                            color = Amber400,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Neutral400,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            MacroDistributionBar(
                proteinGrams = analysis.totalProteinGrams,
                carbsGrams = analysis.totalCarbsGrams,
                fatGrams = analysis.totalFatGrams,
                fiberGrams = analysis.totalFiberGrams
            )

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Teal500.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                    .clickable { onAskAgent() },
                color = Teal500.copy(alpha = 0.12f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "💬 Ask VitaVue Agent About This Meal",
                        color = Teal300,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyStateCard(
    message: String,
    actionText: String,
    onAction: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Navy700, RoundedCornerShape(16.dp)),
        color = Navy900,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                color = Neutral300,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(14.dp))
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onAction() },
                color = Teal500,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = actionText,
                    color = Navy950,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}
