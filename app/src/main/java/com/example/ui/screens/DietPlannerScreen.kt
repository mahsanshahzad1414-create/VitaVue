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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DayMealPlan
import com.example.data.model.PlannedMealItem
import com.example.ui.components.DietaryTagChip
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
fun DietPlannerScreen(
    viewModel: VitaVueViewModel,
    modifier: Modifier = Modifier
) {
    val currentPlan by viewModel.currentDietPlan.collectAsState()
    val isGenerating by viewModel.isGeneratingPlan.collectAsState()

    var selectedGoal by remember { mutableStateOf("Balanced Wellness") }
    var selectedPattern by remember { mutableStateOf("Mediterranean / Flexible") }
    var selectedCuisine by remember { mutableStateOf("Mediterranean") }
    var calorieTarget by remember { mutableStateOf(2150f) }
    var planSavedSuccess by remember { mutableStateOf(false) }

    val goals = listOf("Balanced Wellness", "High-Protein Muscle", "Longevity & Heart", "Low-Glycemic Energy")
    val patterns = listOf("Mediterranean / Flexible", "Vegetarian", "Vegan", "Halal", "Plant-Forward")
    val cuisines = listOf("Mediterranean", "South Asian", "East Asian", "Global Whole Foods")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Navy950)
            .testTag("diet_planner_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Header
        item {
            Column {
                Text(
                    text = "Personalized Diet Planner",
                    color = Neutral50,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Construct evidence-aligned daily and weekly meal matrices matched to your physiological goals and culinary preferences.",
                    color = Neutral300,
                    fontSize = 13.sp
                )
            }
        }

        // Configuration Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .border(1.dp, Navy700, RoundedCornerShape(18.dp)),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Goal Selection
                    Text(
                        text = "PRIMARY NUTRITIONAL GOAL",
                        color = Neutral400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(goals) { g ->
                            DietaryTagChip(
                                tag = g,
                                isSelected = selectedGoal == g,
                                onClick = { selectedGoal = g }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Dietary Pattern
                    Text(
                        text = "DIETARY PATTERN",
                        color = Neutral400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(patterns) { p ->
                            DietaryTagChip(
                                tag = p,
                                isSelected = selectedPattern == p,
                                onClick = { selectedPattern = p }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Cuisine
                    Text(
                        text = "CUISINE INSPIRATION",
                        color = Neutral400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(cuisines) { c ->
                            DietaryTagChip(
                                tag = c,
                                isSelected = selectedCuisine == c,
                                onClick = { selectedCuisine = c }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Calorie Target Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "DAILY CALORIE TARGET",
                            color = Neutral400,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "${calorieTarget.toInt()} kcal",
                            color = Amber400,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Slider(
                        value = calorieTarget,
                        onValueChange = { calorieTarget = it },
                        valueRange = 1500f..3500f,
                        steps = 20,
                        colors = SliderDefaults.colors(
                            thumbColor = Teal400,
                            activeTrackColor = Teal400,
                            inactiveTrackColor = Navy800
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            planSavedSuccess = false
                            viewModel.generateNewMealPlan(
                                goal = selectedGoal,
                                dietaryPattern = selectedPattern,
                                targetCalories = calorieTarget.toInt(),
                                cuisine = selectedCuisine
                            )
                        },
                        enabled = !isGenerating,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("generate_meal_plan_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = Teal500),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        if (isGenerating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = Navy950,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generating Matrix...", color = Navy950, fontWeight = FontWeight.Bold)
                        } else {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Generate",
                                tint = Navy950,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate Precision Plan", color = Navy950, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Display Generated Plan
        if (currentPlan != null) {
            val plan = currentPlan!!
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .border(1.dp, Teal500.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
                        .testTag("active_plan_overview_card"),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Navy900)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = plan.title,
                                    color = Neutral50,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Target: ~${plan.targetCaloriesPerDay} kcal/day • ${plan.dietaryPattern}",
                                    color = Neutral400,
                                    fontSize = 12.sp
                                )
                            }

                            Button(
                                onClick = {
                                    viewModel.saveCurrentMealPlan()
                                    planSavedSuccess = true
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = if (planSavedSuccess) Emerald400 else Teal500),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(36.dp)
                            ) {
                                Icon(
                                    imageVector = if (planSavedSuccess) Icons.Default.Check else Icons.Default.BookmarkAdd,
                                    contentDescription = "Save",
                                    tint = Navy950,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (planSavedSuccess) "Saved" else "Save Plan",
                                    color = Navy950,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        MacroDistributionBar(
                            proteinGrams = plan.targetProteinGrams.toFloat(),
                            carbsGrams = plan.targetCarbsGrams.toFloat(),
                            fatGrams = plan.targetFatGrams.toFloat(),
                            fiberGrams = 32f
                        )
                    }
                }
            }

            // Days Breakdown
            items(plan.days) { day ->
                DayPlanCard(day = day)
            }
        }
    }
}

@Composable
fun DayPlanCard(day: DayMealPlan) {
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
                Text(
                    text = day.dayName.uppercase(),
                    color = Teal300,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "${day.meals.sumOf { it.calories }} kcal total",
                    color = Amber400,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            day.meals.forEachIndexed { index, meal ->
                PlannedMealRow(meal = meal)
                if (index < day.meals.size - 1) {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
fun PlannedMealRow(meal: PlannedMealItem) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        color = Navy850,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Teal500.copy(alpha = 0.15f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = meal.mealType.uppercase(),
                        color = Teal300,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Prep time",
                        tint = Neutral400,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${meal.prepTimeMin} min prep",
                        color = Neutral400,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${meal.calories} kcal",
                        color = Amber400,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = meal.recipeTitle,
                color = Neutral50,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = meal.description,
                color = Neutral300,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Key Ingredients: " + meal.ingredients.joinToString(", "),
                color = Neutral400,
                fontSize = 10.sp
            )
        }
    }
}
