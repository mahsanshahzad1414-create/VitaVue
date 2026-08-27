package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.FoodCategory
import com.example.data.model.FoodItem
import com.example.ui.components.MacroDistributionBar
import com.example.ui.components.ResponsibleAIBadge
import com.example.ui.components.VitaVueLogo
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber500
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Emerald500
import com.example.ui.theme.Navy700
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy850
import com.example.ui.theme.Navy900
import com.example.ui.theme.Navy950
import com.example.ui.theme.Neutral100
import com.example.ui.theme.Neutral200
import com.example.ui.theme.Neutral300
import com.example.ui.theme.Neutral400
import com.example.ui.theme.Neutral50
import com.example.ui.theme.Neutral700
import com.example.ui.theme.Teal300
import com.example.ui.theme.Teal400
import com.example.ui.theme.Teal500
import com.example.ui.viewmodel.VitaVueViewModel

@Composable
fun HomeScreen(
    viewModel: VitaVueViewModel,
    modifier: Modifier = Modifier
) {
    val analysisHistory by viewModel.analysisHistory.collectAsState()
    val savedFoods by viewModel.savedFoodIds.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val allFoods = viewModel.getAllFoods()

    val featuredFoods = allFoods.filter {
        it.id in listOf("dish_chicken_biryani", "protein_salmon", "fruit_avocado", "legume_chickpeas", "dish_hummus_falafel")
    }

    val dailyMyth = viewModel.allMyths.firstOrNull()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Navy950)
            .testTag("home_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // App Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                VitaVueLogo()
                ResponsibleAIBadge(
                    onClick = { viewModel.navigateTo("responsible_ai") }
                )
            }
        }

        // Hero Banner Card with CTA
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, Teal500.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                    .testTag("hero_banner_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900)
            ) {
                Column {
                    // Visual Banner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.vitavue_hero_banner_1787392797987),
                            contentDescription = "VitaVue Nutrition Intelligence Hero",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Navy900)
                                    )
                                )
                        )
                    }

                    // Card Content
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(Teal500.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "GEMINI MULTIMODAL INTELLIGENCE",
                                    color = Teal300,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "See Your Food Differently",
                            color = Neutral50,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Instant visual portion estimation, complete macro and micronutrient breakdown, and conversational diet intelligence.",
                            color = Neutral300,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = { viewModel.navigateTo("analyze") },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("home_scan_meal_button"),
                                colors = ButtonDefaults.buttonColors(containerColor = Teal500),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = "Scan",
                                    tint = Navy950,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Scan Meal",
                                    color = Navy950,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }

                            OutlinedButton(
                                onClick = { viewModel.navigateTo("agent") },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("home_ask_agent_button"),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Teal400),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "Agent",
                                    tint = Teal400,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Ask Agent",
                                    color = Teal400,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quick Intelligence Prompts
        item {
            Column {
                Text(
                    text = "EXPLORE NUTRITION INTELLIGENCE",
                    color = Neutral400,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

                val quickQuestions = listOf(
                    "How do I boost non-heme iron bioavailability?",
                    "What are the core principles of the Mediterranean pattern?",
                    "How does dietary fiber feed short-chain fatty acids?",
                    "What are high-protein options on a student budget?"
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(end = 8.dp)
                ) {
                    items(quickQuestions) { question ->
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .border(1.dp, Navy700, RoundedCornerShape(14.dp))
                                .clickable {
                                    viewModel.navigateTo("agent")
                                    viewModel.sendAgentMessage(question)
                                },
                            color = Navy900,
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = "Question",
                                    tint = Amber400,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = question,
                                    color = Neutral100,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // Latest Analysis or Quick Feature
        if (analysisHistory.isNotEmpty()) {
            val latest = analysisHistory.first()
            item {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "LATEST MEAL ANALYSIS",
                            color = Neutral400,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "View All",
                            color = Teal400,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { viewModel.navigateTo("my_nutrition") }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Navy700, RoundedCornerShape(16.dp))
                            .clickable {
                                viewModel.setAnalysisContextForAgent(latest)
                            },
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
                                        text = latest.mealTitle,
                                        color = Neutral50,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${latest.detectedItems.size} components detected • ${latest.confidenceRating} confidence",
                                        color = Neutral400,
                                        fontSize = 12.sp
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Amber500.copy(alpha = 0.15f))
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "~${latest.totalCalories} kcal",
                                        color = Amber400,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            MacroDistributionBar(
                                proteinGrams = latest.totalProteinGrams,
                                carbsGrams = latest.totalCarbsGrams,
                                fatGrams = latest.totalFatGrams,
                                fiberGrams = latest.totalFiberGrams
                            )
                        }
                    }
                }
            }
        }

        // Featured International Foods Discovery
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "INTERNATIONAL FOOD DATABASE (100+ ITEMS)",
                        color = Neutral400,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Explore",
                        color = Teal400,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { viewModel.navigateTo("food_explorer") }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(featuredFoods) { food ->
                        FeaturedFoodCard(
                            food = food,
                            onClick = { viewModel.openFoodDetail(food) }
                        )
                    }
                }
            }
        }

        // Daily Evidence Myth Buster
        if (dailyMyth != null) {
            item {
                Column {
                    Text(
                        text = "EVIDENCE VS MYTH",
                        color = Neutral400,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Amber500.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                            .clickable { viewModel.navigateTo("learn") },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Navy900)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(Color(0xFFEF4444).copy(alpha = 0.2f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "MYTH",
                                        color = Color(0xFFF87171),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = dailyMyth.myth,
                                    color = Neutral100,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(verticalAlignment = Alignment.Top) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Fact",
                                    tint = Emerald400,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = dailyMyth.fact,
                                    color = Emerald400,
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Navigation Quick Hub
        item {
            Column {
                Text(
                    text = "INTELLIGENCE MODULES",
                    color = Neutral400,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ModuleShortcutCard(
                        title = "Diet Planner",
                        subtitle = "Personalized Meals",
                        icon = Icons.Default.Schedule,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.navigateTo("diet_planner") }
                    )
                    ModuleShortcutCard(
                        title = "Knowledge Hub",
                        subtitle = "8 Science Categories",
                        icon = Icons.Default.MenuBook,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.navigateTo("learn") }
                    )
                }
            }
        }

        // Footer / About
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "VitaVue • Where Nutrition Meets Intelligence • About & Architecture",
                    color = Neutral400,
                    fontSize = 11.sp,
                    modifier = Modifier.clickable { viewModel.navigateTo("about") }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun FeaturedFoodCard(
    food: FoodItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .width(175.dp)
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, Navy700, RoundedCornerShape(14.dp))
            .clickable { onClick() },
        color = Navy900,
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = food.culturalOrigin,
                    color = Teal400,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = "${food.calories} kcal",
                    color = Amber400,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = food.name,
                color = Neutral100,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                minLines = 2,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "P: ${food.proteinGrams.toInt()}g • C: ${food.carbsGrams.toInt()}g • F: ${food.fatGrams.toInt()}g",
                color = Neutral400,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun ModuleShortcutCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, Navy700, RoundedCornerShape(14.dp))
            .clickable { onClick() },
        color = Navy900,
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Navy800),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Teal400,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    color = Neutral100,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = subtitle,
                    color = Neutral400,
                    fontSize = 11.sp
                )
            }
        }
    }
}
