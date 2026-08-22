package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.AgentScreen
import com.example.ui.screens.AnalyzeScreen
import com.example.ui.screens.ArticleDetailScreen
import com.example.ui.screens.DietPlannerScreen
import com.example.ui.screens.FoodDetailScreen
import com.example.ui.screens.FoodExplorerScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LearnScreen
import com.example.ui.screens.MyNutritionScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ResponsibleAIScreen
import com.example.ui.theme.Amber400
import com.example.ui.theme.Navy800
import com.example.ui.theme.Navy900
import com.example.ui.theme.Navy950
import com.example.ui.theme.Neutral400
import com.example.ui.theme.Neutral50
import com.example.ui.theme.Teal300
import com.example.ui.theme.Teal400
import com.example.ui.theme.Teal500
import com.example.ui.theme.VitaVueTheme
import com.example.ui.viewmodel.VitaVueViewModel

data class NavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val isHighlighted: Boolean = false
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VitaVueTheme {
                VitaVueApp()
            }
        }
    }
}

@Composable
fun VitaVueApp(
    viewModel: VitaVueViewModel = viewModel()
) {
    val currentDestination by viewModel.currentDestination.collectAsState()

    val navItems = listOf(
        NavItem("home", "Home", Icons.Default.Home),
        NavItem("analyze", "Analyze", Icons.Default.CameraAlt, isHighlighted = true),
        NavItem("agent", "Agent", Icons.Default.AutoAwesome),
        NavItem("food_explorer", "Foods", Icons.Default.Restaurant),
        NavItem("diet_planner", "Planner", Icons.Default.Schedule),
        NavItem("my_nutrition", "My Log", Icons.Default.Person)
    )

    val showBottomBar = currentDestination in listOf(
        "home", "analyze", "agent", "food_explorer", "diet_planner", "my_nutrition"
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy950)
            .testTag("vitavue_scaffold"),
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Navy900,
                    tonalElevation = 8.dp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .border(1.dp, Navy800, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .testTag("bottom_navigation_bar")
                ) {
                    navItems.forEach { item ->
                        val selected = currentDestination == item.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = { viewModel.navigateTo(item.route) },
                            icon = {
                                if (item.isHighlighted) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(if (selected) Teal400 else Teal500.copy(alpha = 0.2f)),
                                        contentAlignment = androidx.compose.ui.Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = item.label,
                                            tint = if (selected) Navy950 else Teal300,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                } else {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.label,
                                        tint = if (selected) Teal400 else Neutral400,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    fontSize = 10.sp,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (selected) Teal300 else Neutral400
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Navy800.copy(alpha = 0.6f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        AnimatedContent(
            targetState = currentDestination,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "screen_navigation",
            modifier = Modifier.padding(innerPadding)
        ) { target ->
            when (target) {
                "home" -> HomeScreen(viewModel = viewModel)
                "analyze" -> AnalyzeScreen(viewModel = viewModel)
                "agent" -> AgentScreen(viewModel = viewModel)
                "food_explorer" -> FoodExplorerScreen(viewModel = viewModel)
                "food_detail" -> FoodDetailScreen(viewModel = viewModel)
                "learn" -> LearnScreen(viewModel = viewModel)
                "article_detail" -> ArticleDetailScreen(viewModel = viewModel)
                "diet_planner" -> DietPlannerScreen(viewModel = viewModel)
                "my_nutrition" -> MyNutritionScreen(viewModel = viewModel)
                "profile" -> ProfileScreen(viewModel = viewModel)
                "responsible_ai" -> ResponsibleAIScreen(viewModel = viewModel)
                "about" -> AboutScreen(viewModel = viewModel)
                else -> HomeScreen(viewModel = viewModel)
            }
        }
    }
}
