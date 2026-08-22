package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FoodCategory
import com.example.data.model.FoodItem
import com.example.ui.components.DietaryTagChip
import com.example.ui.components.MacroDistributionBar
import com.example.ui.components.MacroMiniBadge
import com.example.ui.components.MicronutrientRow
import com.example.ui.theme.Amber400
import com.example.ui.theme.CarbsColor
import com.example.ui.theme.Emerald400
import com.example.ui.theme.FatColor
import com.example.ui.theme.FiberColor
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
import com.example.ui.theme.ProteinColor
import com.example.ui.theme.Teal300
import com.example.ui.theme.Teal400
import com.example.ui.theme.Teal500
import com.example.ui.viewmodel.VitaVueViewModel

@Composable
fun FoodExplorerScreen(
    viewModel: VitaVueViewModel,
    modifier: Modifier = Modifier
) {
    val searchQuery by viewModel.foodSearchQuery.collectAsState()
    val selectedCategory by viewModel.selectedFoodCategory.collectAsState()
    val selectedTag by viewModel.selectedDietaryTag.collectAsState()
    val savedFoodIds by viewModel.savedFoodIds.collectAsState()

    val filteredFoods = viewModel.searchFoods(
        query = searchQuery,
        category = selectedCategory,
        tag = if (selectedTag == "All") null else selectedTag
    )

    val dietaryFilters = listOf("All", "Vegan", "High-Protein", "Gluten-Free", "Low-Carb", "Heart-Healthy", "Halal", "Keto-Friendly", "Omega-3")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Navy950)
            .testTag("food_explorer_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Screen Header
        item {
            Column {
                Text(
                    text = "International Food Database",
                    color = Neutral50,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "100+ analyzed global foods with complete macronutrients, micronutrients, and culinary science.",
                    color = Neutral300,
                    fontSize = 13.sp
                )
            }
        }

        // Search Field
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setFoodSearchQuery(it) },
                placeholder = { Text("Search by food, nutrient, region (e.g. Biryani, Quinoa)...", color = Neutral400, fontSize = 13.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Teal400,
                        modifier = Modifier.size(20.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("food_search_input"),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Teal400,
                    unfocusedBorderColor = Navy700,
                    focusedContainerColor = Navy900,
                    unfocusedContainerColor = Navy900,
                    focusedTextColor = Neutral50,
                    unfocusedTextColor = Neutral50
                ),
                singleLine = true
            )
        }

        // Category Pills
        item {
            Column {
                Text(
                    text = "CATEGORIES",
                    color = Neutral400,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        DietaryTagChip(
                            tag = "All Categories",
                            isSelected = selectedCategory == null,
                            onClick = { viewModel.setFoodCategory(null) }
                        )
                    }
                    items(FoodCategory.values()) { cat ->
                        DietaryTagChip(
                            tag = cat.displayName,
                            isSelected = selectedCategory == cat,
                            onClick = { viewModel.setFoodCategory(cat) }
                        )
                    }
                }
            }
        }

        // Dietary Tag Filters
        item {
            Column {
                Text(
                    text = "DIETARY FILTERS",
                    color = Neutral400,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(dietaryFilters) { tag ->
                        DietaryTagChip(
                            tag = tag,
                            isSelected = selectedTag == tag,
                            onClick = { viewModel.setDietaryTag(tag) }
                        )
                    }
                }
            }
        }

        // Results Count Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredFoods.size} FOODS AVAILABLE",
                    color = Neutral400,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }

        // Food Items List
        items(filteredFoods) { food ->
            val isSaved = savedFoodIds.contains(food.id)
            FoodExplorerCard(
                food = food,
                isSaved = isSaved,
                onFavoriteToggle = { viewModel.toggleSaveFood(food.id) },
                onClick = { viewModel.openFoodDetail(food) }
            )
        }
    }
}

@Composable
fun FoodExplorerCard(
    food: FoodItem,
    isSaved: Boolean,
    onFavoriteToggle: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Navy700, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .testTag("food_card_${food.id}"),
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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = food.category.displayName.uppercase(),
                            color = Teal400,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "• ${food.culturalOrigin}",
                            color = Neutral400,
                            fontSize = 10.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = food.name,
                        color = Neutral50,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Serving: ${food.servingSize}",
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
                            text = "${food.calories} kcal",
                            color = Amber400,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    IconButton(
                        onClick = onFavoriteToggle,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isSaved) Color(0xFFEF4444) else Neutral400,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = food.description,
                color = Neutral300,
                fontSize = 12.sp,
                maxLines = 2,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Mini Macro row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MacroMiniBadge(label = "Protein", grams = food.proteinGrams, color = ProteinColor)
                MacroMiniBadge(label = "Carbs", grams = food.carbsGrams, color = CarbsColor)
                MacroMiniBadge(label = "Fats", grams = food.fatGrams, color = FatColor)
                MacroMiniBadge(label = "Fiber", grams = food.fiberGrams, color = FiberColor)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FoodDetailScreen(
    viewModel: VitaVueViewModel,
    modifier: Modifier = Modifier
) {
    val food = viewModel.selectedFoodDetail.collectAsState().value
    val savedFoods by viewModel.savedFoodIds.collectAsState()

    if (food == null) {
        Box(
            modifier = modifier.fillMaxSize().background(Navy950),
            contentAlignment = Alignment.Center
        ) {
            Text("Food item not found", color = Neutral300)
        }
        return
    }

    val isSaved = savedFoods.contains(food.id)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Navy950)
            .testTag("food_detail_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Navigation bar
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.navigateTo("food_explorer") },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Neutral100
                    )
                }

                Row {
                    IconButton(
                        onClick = { viewModel.toggleSaveFood(food.id) },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isSaved) Color(0xFFEF4444) else Neutral100
                        )
                    }
                }
            }
        }

        // Hero Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, Teal500.copy(alpha = 0.3f), RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Teal500.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = food.category.displayName.uppercase(),
                                color = Teal300,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Amber400.copy(alpha = 0.2f))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "${food.calories} kcal",
                                color = Amber400,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = food.name,
                        color = Neutral50,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Serving: ${food.servingSize} • Origin: ${food.culturalOrigin}",
                        color = Neutral400,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Macro distribution
                    Text(
                        text = "MACRONUTRIENTS",
                        color = Neutral400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    MacroDistributionBar(
                        proteinGrams = food.proteinGrams,
                        carbsGrams = food.carbsGrams,
                        fatGrams = food.fatGrams,
                        fiberGrams = food.fiberGrams
                    )
                }
            }
        }

        // Dietary Tags
        if (food.dietaryTags.isNotEmpty()) {
            item {
                Column {
                    Text(
                        text = "DIETARY ATTRIBUTES",
                        color = Neutral400,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        food.dietaryTags.forEach { tag ->
                            DietaryTagChip(tag = tag, isSelected = true)
                        }
                    }
                }
            }
        }

        // Description & Culinary Science
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Navy700, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "NUTRITIONAL PROFILE & SCIENCE",
                        color = Neutral400,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = food.description,
                        color = Neutral100,
                        fontSize = 13.sp,
                        lineHeight = 19.sp
                    )

                    if (food.culinaryNotes.isNotBlank()) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "CULINARY & PREPARATION TIPS",
                            color = Teal400,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = food.culinaryNotes,
                            color = Neutral200,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                    }
                }
            }
        }

        // Micronutrients Table
        if (food.micronutrients.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Navy700, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Navy900)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "KEY MICRONUTRIENTS & BIOACTIVE COMPOUNDS",
                            color = Neutral400,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        food.micronutrients.forEachIndexed { index, m ->
                            MicronutrientRow(micronutrient = m)
                            if (index < food.micronutrients.size - 1) {
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }

        // Ask Agent CTA
        item {
            Button(
                onClick = {
                    viewModel.navigateTo("agent")
                    viewModel.sendAgentMessage("Tell me more about the nutritional science and health benefits of **${food.name}** (${food.calories} kcal per ${food.servingSize}). How does it fit into a balanced diet?")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("ask_agent_about_food_button"),
                colors = ButtonDefaults.buttonColors(containerColor = Teal500),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "Ask Agent",
                    tint = Navy950,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ask VitaVue Agent About ${food.name}",
                    color = Navy950,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}
