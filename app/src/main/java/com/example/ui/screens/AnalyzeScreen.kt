package com.example.ui.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FoodAnalysisResult
import com.example.data.model.FoodComponent
import com.example.data.model.Micronutrient
import com.example.ui.components.MacroDistributionBar
import com.example.ui.components.UncertaintyDisclaimerCard
import com.example.ui.theme.Amber400
import com.example.ui.theme.Amber500
import com.example.ui.theme.CarbsColor
import com.example.ui.theme.Emerald400
import com.example.ui.theme.Emerald500
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
import com.example.ui.theme.Neutral500
import com.example.ui.theme.ProteinColor
import com.example.ui.theme.Teal300
import com.example.ui.theme.Teal400
import com.example.ui.theme.Teal500
import com.example.ui.viewmodel.AnalysisUiState
import com.example.ui.viewmodel.VitaVueViewModel

@Composable
fun AnalyzeScreen(
    viewModel: VitaVueViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val analysisState by viewModel.analysisState.collectAsState()
    val currentResult by viewModel.currentAnalysis.collectAsState()
    val selectedBitmap by viewModel.selectedImageBitmap.collectAsState()

    // Gallery Picker launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                val bitmap = if (Build.VERSION.SDK_INT < 28) {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }
                if (bitmap != null && bitmap.width > 0 && bitmap.height > 0) {
                    viewModel.setSelectedBitmap(bitmap)
                }
            } catch (e: Exception) {
                // Graceful failure - do not set bitmap or produce fake result
            }
        }
    }

    // Camera Capture launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null && bitmap.width > 0 && bitmap.height > 0) {
            viewModel.setSelectedBitmap(bitmap)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Navy950)
            .testTag("analyze_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Title Header
        item {
            Column {
                Text(
                    text = "AI Vision Nutrition Analysis",
                    color = Neutral50,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Upload or capture a meal photo to calculate portion estimates, calories, macronutrients, and bioactive compounds.",
                    color = Neutral300,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }

        // Image Input & Capture Card
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
                    if (selectedBitmap != null) {
                        // Image Selected State with Preview & Clear action
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, Teal500.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                        ) {
                            Image(
                                bitmap = selectedBitmap!!.asImageBitmap(),
                                contentDescription = "Selected Meal Photo",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            // Remove Image Overlay Button
                            IconButton(
                                onClick = { viewModel.removeSelectedImage() },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Navy950.copy(alpha = 0.8f))
                                    .testTag("remove_image_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Remove photo",
                                    tint = Neutral50,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    } else {
                        // Clear Empty State: NO IMAGE
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Navy850)
                                .border(1.dp, Navy700, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = "Camera icon",
                                    tint = Teal400,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "No meal image selected",
                                    color = Neutral50,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Upload or capture a meal photo to begin analysis.",
                                    color = Neutral300,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Media Capture / Selection Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = { cameraLauncher.launch(null) },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("camera_capture_button"),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Navy700),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Take Photo",
                                tint = Neutral100,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (selectedBitmap != null) "Retake" else "Camera",
                                color = Neutral100,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        OutlinedButton(
                            onClick = { galleryLauncher.launch("image/*") },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp)
                                .testTag("select_photo_button"),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Navy700),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Image,
                                contentDescription = "Select from Gallery",
                                tint = Neutral100,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (selectedBitmap != null) "Replace" else "Gallery",
                                color = Neutral100,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Primary Action: Analyze Meal Button (Strictly requires selectedBitmap != null)
                    val isImageReady = selectedBitmap != null
                    val isAnalyzing = analysisState is AnalysisUiState.Analyzing

                    Button(
                        onClick = {
                            if (selectedBitmap != null) {
                                viewModel.analyzeMeal(selectedBitmap)
                            }
                        },
                        enabled = isImageReady && !isAnalyzing,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("trigger_analyze_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Teal500,
                            disabledContainerColor = Navy800,
                            disabledContentColor = Neutral500
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        if (isAnalyzing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = Navy950,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Analyzing Meal...",
                                color = Navy950,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Analyze",
                                tint = if (isImageReady) Navy950 else Neutral500,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isImageReady) "Analyze Meal Photo" else "Select Photo to Analyze",
                                color = if (isImageReady) Navy950 else Neutral500,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Error State Card with Retry Action (Requires valid current image)
        if (analysisState is AnalysisUiState.Error) {
            val errorMsg = (analysisState as AnalysisUiState.Error).message
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, Amber500.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                        .testTag("analysis_error_card"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Navy900)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ErrorOutline,
                                contentDescription = "Error",
                                tint = Amber400,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Analysis Failed",
                                color = Amber400,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = errorMsg,
                            color = Neutral200,
                            fontSize = 13.sp,
                            lineHeight = 17.sp
                        )

                        if (selectedBitmap != null) {
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedButton(
                                onClick = { viewModel.analyzeMeal(selectedBitmap) },
                                modifier = Modifier.height(38.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Amber400),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Retry",
                                    tint = Amber400,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Retry Analysis",
                                    color = Amber400,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // ANALYSIS RESULTS: STRICT REQUIREMENT: Only render when a valid image exists AND analysis succeeded
        if (selectedBitmap != null && currentResult != null && analysisState is AnalysisUiState.Success) {
            val res = currentResult!!
            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Result Header Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(18.dp))
                            .border(1.dp, Teal500.copy(alpha = 0.4f), RoundedCornerShape(18.dp))
                            .testTag("analysis_result_card"),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Navy900)
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Emerald500.copy(alpha = 0.2f))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "${res.confidenceRating.uppercase()} CONFIDENCE ESTIMATE",
                                        color = Emerald400,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.8.sp
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Amber500.copy(alpha = 0.2f))
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "~${res.totalCalories} kcal",
                                        color = Amber400,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = res.mealTitle,
                                color = Neutral50,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = res.mealDescription,
                                color = Neutral300,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Macronutrients Bar
                            Text(
                                text = "MACRONUTRIENT DISTRIBUTION",
                                color = Neutral400,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            MacroDistributionBar(
                                proteinGrams = res.totalProteinGrams,
                                carbsGrams = res.totalCarbsGrams,
                                fatGrams = res.totalFatGrams,
                                fiberGrams = res.totalFiberGrams
                            )
                        }
                    }

                    // Detected Components Breakdown
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
                                text = "DETECTED FOOD COMPONENTS & PORTIONS",
                                color = Neutral400,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            res.detectedItems.forEachIndexed { index, item ->
                                FoodComponentRow(item = item)
                                if (index < res.detectedItems.size - 1) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
                        }
                    }

                    // Micronutrients & Bioactives Table
                    if (res.micronutrients.isNotEmpty()) {
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
                                    text = "MICRONUTRIENTS & BIOACTIVE COMPOUNDS",
                                    color = Neutral400,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                res.micronutrients.forEachIndexed { index, m ->
                                    MicronutrientRow(micronutrient = m)
                                    if (index < res.micronutrients.size - 1) {
                                        Spacer(modifier = Modifier.height(10.dp))
                                    }
                                }
                            }
                        }
                    }

                    // Nutrition Highlights & Practical Enhancements
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
                                text = "PRACTICAL ENHANCEMENTS & OBSERVATIONS",
                                color = Neutral400,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            res.nutritionHighlights.forEach { highlight ->
                                Row(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Highlight",
                                        tint = Teal400,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = highlight,
                                        color = Neutral100,
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }

                            res.practicalSuggestions.forEach { suggestion ->
                                Row(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lightbulb,
                                        contentDescription = "Suggestion",
                                        tint = Amber400,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = suggestion,
                                        color = Neutral200,
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }

                    // Uncertainty Notice
                    UncertaintyDisclaimerCard(note = res.uncertaintyNote)

                    // Agent CTA button
                    Button(
                        onClick = {
                            viewModel.setAnalysisContextForAgent(res)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("ask_agent_about_meal_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = Teal500),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = "Ask Agent",
                            tint = Navy950,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ask VitaVue Agent About This Meal",
                            color = Navy950,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FoodComponentRow(item: FoodComponent) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        color = Navy850,
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.name,
                    color = Neutral50,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
                Text(
                    text = "${item.calories} kcal",
                    color = Amber400,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Portion: ${item.portionEstimate}",
                color = Neutral400,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "P: ${item.proteinGrams.toInt()}g",
                    color = ProteinColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "C: ${item.carbsGrams.toInt()}g",
                    color = CarbsColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "F: ${item.fatGrams.toInt()}g",
                    color = FatColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Fib: ${item.fiberGrams.toInt()}g",
                    color = FiberColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun MicronutrientRow(micronutrient: Micronutrient) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        color = Navy850,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = micronutrient.name,
                        color = Neutral50,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = micronutrient.amount,
                        color = Teal300,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                if (micronutrient.benefit.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = micronutrient.benefit,
                        color = Neutral400,
                        fontSize = 11.sp
                    )
                }
            }

            if (micronutrient.dailyValuePercent != null && micronutrient.dailyValuePercent > 0) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(Teal500.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "${micronutrient.dailyValuePercent}% DV",
                        color = Teal400,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}
