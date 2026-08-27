package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AgentAction
import com.example.data.model.Micronutrient
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
import com.example.ui.theme.Neutral700
import com.example.ui.theme.ProteinColor
import com.example.ui.theme.Teal300
import com.example.ui.theme.Teal400
import com.example.ui.theme.Teal500

@Composable
fun VitaVueLogo(
    modifier: Modifier = Modifier,
    showTagline: Boolean = true,
    compact: Boolean = false
) {
    Row(
        modifier = modifier.testTag("app_logo"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // High-tech circular badge
        Box(
            modifier = Modifier
                .size(if (compact) 32.dp else 40.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(Navy800, Navy950)
                    )
                )
                .border(1.5.dp, Brush.linearGradient(listOf(Teal400, Amber400)), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "V",
                color = Teal400,
                fontWeight = FontWeight.Black,
                fontSize = if (compact) 16.sp else 20.sp
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "VITA",
                    color = Neutral100,
                    fontWeight = FontWeight.Black,
                    fontSize = if (compact) 16.sp else 20.sp,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "VUE",
                    color = Teal400,
                    fontWeight = FontWeight.Black,
                    fontSize = if (compact) 16.sp else 20.sp,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(Amber400)
                )
            }
            if (showTagline && !compact) {
                Text(
                    text = "WHERE NUTRITION MEETS INTELLIGENCE",
                    color = Neutral400,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 8.5.sp,
                    letterSpacing = 0.8.sp
                )
            }
        }
    }
}

@Composable
fun MacroDistributionBar(
    proteinGrams: Float,
    carbsGrams: Float,
    fatGrams: Float,
    fiberGrams: Float,
    modifier: Modifier = Modifier
) {
    val totalGrams = (proteinGrams + carbsGrams + fatGrams).coerceAtLeast(1f)
    val pWeight = (proteinGrams / totalGrams).coerceIn(0.05f, 0.9f)
    val cWeight = (carbsGrams / totalGrams).coerceIn(0.05f, 0.9f)
    val fWeight = (fatGrams / totalGrams).coerceIn(0.05f, 0.9f)

    Column(modifier = modifier.fillMaxWidth()) {
        // Multi-segment horizontal bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Navy800)
        ) {
            Box(
                modifier = Modifier
                    .weight(pWeight)
                    .height(8.dp)
                    .background(ProteinColor)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Box(
                modifier = Modifier
                    .weight(cWeight)
                    .height(8.dp)
                    .background(CarbsColor)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Box(
                modifier = Modifier
                    .weight(fWeight)
                    .height(8.dp)
                    .background(FatColor)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Legend row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MacroMiniBadge(label = "Protein", grams = proteinGrams, color = ProteinColor)
            MacroMiniBadge(label = "Carbs", grams = carbsGrams, color = CarbsColor)
            MacroMiniBadge(label = "Fats", grams = fatGrams, color = FatColor)
            MacroMiniBadge(label = "Fiber", grams = fiberGrams, color = FiberColor)
        }
    }
}

@Composable
fun MacroMiniBadge(
    label: String,
    grams: Float,
    color: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(
                text = "${grams.toInt()}g",
                color = Neutral100,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                color = Neutral400,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun DietaryTagChip(
    tag: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val bg = if (isSelected) Teal500.copy(alpha = 0.2f) else Navy800.copy(alpha = 0.6f)
    val borderCol = if (isSelected) Teal400 else Navy700
    val textCol = if (isSelected) Teal300 else Neutral300

    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, borderCol, RoundedCornerShape(12.dp))
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        color = bg,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = tag,
            color = textCol,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
    }
}

@Composable
fun UncertaintyDisclaimerCard(
    note: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Amber500.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
        color = Navy900.copy(alpha = 0.85f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "Uncertainty Note",
                tint = Amber400,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "AI Estimation Transparency",
                    color = Amber400,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = note,
                    color = Neutral300,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }
        }
    }
}

@Composable
fun ResponsibleAIBadge(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Emerald500.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .clickable { onClick() },
        color = Navy900.copy(alpha = 0.9f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = "Responsible AI",
                tint = Emerald400,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Scientific & Non-Medical AI Disclosure",
                color = Emerald400,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun AgentActionPill(
    action: AgentAction,
    onActionClick: (AgentAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Teal400.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
            .clickable { onActionClick(action) },
        color = Teal500.copy(alpha = 0.15f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "⚡ " + action.title,
                color = Teal300,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
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
