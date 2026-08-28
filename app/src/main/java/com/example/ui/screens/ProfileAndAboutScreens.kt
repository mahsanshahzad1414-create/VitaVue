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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AuthUiState
import com.example.data.model.SyncUiState
import com.example.data.model.UserProfile
import com.example.ui.components.DietaryTagChip
import com.example.ui.components.VitaVueLogo
import com.example.ui.theme.Amber300
import com.example.ui.theme.Amber400
import com.example.ui.theme.Emerald400
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
import com.example.ui.theme.Teal300
import com.example.ui.theme.Teal400
import com.example.ui.theme.Teal500
import com.example.ui.viewmodel.VitaVueViewModel

@Composable
fun ProfileScreen(
    viewModel: VitaVueViewModel,
    modifier: Modifier = Modifier
) {
    val currentProfile by viewModel.userProfile.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val authUiState by viewModel.authUiState.collectAsState()
    val syncUiState by viewModel.syncUiState.collectAsState()

    var name by remember(currentProfile.name) { mutableStateOf(currentProfile.name) }
    var goal by remember(currentProfile.goal) { mutableStateOf(currentProfile.goal) }
    var pattern by remember(currentProfile.dietaryPattern) { mutableStateOf(currentProfile.dietaryPattern) }
    var calorieTarget by remember(currentProfile.dailyCalorieTarget) { mutableStateOf(currentProfile.dailyCalorieTarget.toString()) }
    var useMetric by remember(currentProfile.isMetric) { mutableStateOf(currentProfile.isMetric) }
    var savedFeedback by remember { mutableStateOf(false) }

    // Auth form state
    var authTab by remember { mutableStateOf(0) } // 0: Sign In, 1: Register, 2: Reset Password
    var authEmail by remember { mutableStateOf("") }
    var authPassword by remember { mutableStateOf("") }
    var authDisplayName by remember { mutableStateOf("") }

    val goalsList = listOf("Balanced Health", "Muscle Hypertrophy", "Longevity & Heart", "Metabolic Health", "Athletic Endurance")
    val patternsList = listOf("Mediterranean", "Vegetarian", "Vegan", "Halal", "High-Protein", "Whole Food Plant-Based")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Navy950)
            .testTag("profile_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column {
                Text(
                    text = "Profile & Cloud Account",
                    color = Neutral50,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Tailor VitaVue's vision interpretations, nutrient targets, and synchronize your private data across devices.",
                    color = Neutral300,
                    fontSize = 13.sp
                )
            }
        }

        // --- AUTHENTICATION & CLOUD SYNC CARD ---
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, if (currentUser != null) Teal500.copy(alpha = 0.4f) else Navy700, RoundedCornerShape(16.dp))
                    .testTag("auth_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (currentUser != null) {
                        // Logged in View
                        val user = currentUser!!
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    modifier = Modifier.size(44.dp),
                                    shape = CircleShape,
                                    color = Teal500.copy(alpha = 0.2f),
                                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Teal400)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = (user.displayName?.take(1) ?: user.email.take(1)).uppercase(),
                                            color = Teal300,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = user.displayName ?: "VitaVue Member",
                                        color = Neutral50,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = user.email,
                                        color = Neutral400,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        text = "UID: ${user.uid.take(8)}...",
                                        color = Neutral400.copy(alpha = 0.7f),
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            IconButton(
                                onClick = { viewModel.signOut() },
                                modifier = Modifier.testTag("sign_out_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Logout,
                                    contentDescription = "Sign Out",
                                    tint = Neutral400
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Cloud Sync Status
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            color = Navy850,
                            border = androidx.compose.foundation.BorderStroke(1.dp, Navy700)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = when (syncUiState) {
                                            is SyncUiState.Syncing -> Icons.Default.Sync
                                            is SyncUiState.Error -> Icons.Default.ErrorOutline
                                            else -> Icons.Default.CloudDone
                                        },
                                        contentDescription = "Cloud Status",
                                        tint = when (syncUiState) {
                                            is SyncUiState.Syncing -> Amber400
                                            is SyncUiState.Error -> Color(0xFFEF4444)
                                            else -> Emerald400
                                        },
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = when (syncUiState) {
                                                is SyncUiState.Syncing -> "Synchronizing cloud records..."
                                                is SyncUiState.Synced -> "Cloud Database Synchronized"
                                                is SyncUiState.Error -> "Sync Error"
                                                else -> "Cloud Database Connected"
                                            },
                                            color = Neutral100,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(
                                            text = "Encrypted private collection: users/${user.uid.take(6)}...",
                                            color = Neutral400,
                                            fontSize = 10.sp
                                        )
                                    }
                                }

                                OutlinedButton(
                                    onClick = { viewModel.syncWithCloud() },
                                    modifier = Modifier
                                        .height(32.dp)
                                        .testTag("sync_now_button"),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Teal400),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Teal500.copy(alpha = 0.5f))
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Sync", fontSize = 11.sp)
                                }
                            }
                        }
                    } else {
                        // Logged out / Auth Tabs View
                        Text(
                            text = "VITAVUE CLOUD ACCOUNT",
                            color = Neutral400,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Sign in to securely back up meal analyses, bookmarks, and diet plans to Cloud Firestore.",
                            color = Neutral300,
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        TabRow(
                            selectedTabIndex = authTab,
                            containerColor = Navy850,
                            contentColor = Teal400,
                            indicator = { tabPositions ->
                                TabRowDefaults.SecondaryIndicator(
                                    modifier = Modifier.tabIndicatorOffset(tabPositions[authTab]),
                                    color = Teal400
                                )
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, Navy700, RoundedCornerShape(8.dp))
                        ) {
                            Tab(
                                selected = authTab == 0,
                                onClick = { authTab = 0; viewModel.clearAuthError() },
                                text = { Text("Sign In", fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
                            )
                            Tab(
                                selected = authTab == 1,
                                onClick = { authTab = 1; viewModel.clearAuthError() },
                                text = { Text("Create Account", fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
                            )
                            Tab(
                                selected = authTab == 2,
                                onClick = { authTab = 2; viewModel.clearAuthError() },
                                text = { Text("Reset", fontSize = 12.sp, fontWeight = FontWeight.SemiBold) }
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        if (authTab == 1) {
                            OutlinedTextField(
                                value = authDisplayName,
                                onValueChange = { authDisplayName = it },
                                label = { Text("Full Name") },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Neutral400) },
                                modifier = Modifier.fillMaxWidth().testTag("auth_name_input"),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Teal400,
                                    unfocusedBorderColor = Navy700,
                                    focusedContainerColor = Navy850,
                                    unfocusedContainerColor = Navy850,
                                    focusedTextColor = Neutral50,
                                    unfocusedTextColor = Neutral50
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        OutlinedTextField(
                            value = authEmail,
                            onValueChange = { authEmail = it },
                            label = { Text("Email Address") },
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Neutral400) },
                            modifier = Modifier.fillMaxWidth().testTag("auth_email_input"),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Teal400,
                                unfocusedBorderColor = Navy700,
                                focusedContainerColor = Navy850,
                                unfocusedContainerColor = Navy850,
                                focusedTextColor = Neutral50,
                                unfocusedTextColor = Neutral50
                            )
                        )

                        if (authTab != 2) {
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = authPassword,
                                onValueChange = { authPassword = it },
                                label = { Text("Password (min 6 chars)") },
                                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Neutral400) },
                                visualTransformation = PasswordVisualTransformation(),
                                modifier = Modifier.fillMaxWidth().testTag("auth_password_input"),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Teal400,
                                    unfocusedBorderColor = Navy700,
                                    focusedContainerColor = Navy850,
                                    unfocusedContainerColor = Navy850,
                                    focusedTextColor = Neutral50,
                                    unfocusedTextColor = Neutral50
                                )
                            )
                        }

                        // Auth Error / Success feedback
                        when (val state = authUiState) {
                            is AuthUiState.Error -> {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0x22EF4444), RoundedCornerShape(8.dp))
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = Color(0xFFEF4444), modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(state.message, color = Color(0xFFFCA5A5), fontSize = 11.sp)
                                }
                            }
                            is AuthUiState.PasswordResetSent -> {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Emerald400.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Emerald400, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Reset link sent to ${state.email}. Check your inbox.", color = Emerald400, fontSize = 11.sp)
                                }
                            }
                            else -> {}
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                when (authTab) {
                                    0 -> viewModel.signInWithEmail(authEmail, authPassword)
                                    1 -> viewModel.registerWithEmail(authEmail, authPassword, authDisplayName)
                                    2 -> viewModel.sendPasswordReset(authEmail)
                                }
                            },
                            enabled = authUiState !is AuthUiState.Loading,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .testTag("auth_submit_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = Teal500),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            if (authUiState is AuthUiState.Loading) {
                                CircularProgressIndicator(modifier = Modifier.size(18.dp), color = Navy950, strokeWidth = 2.dp)
                            } else {
                                Icon(
                                    imageVector = when (authTab) {
                                        0 -> Icons.Default.VpnKey
                                        1 -> Icons.Default.Person
                                        else -> Icons.Default.Email
                                    },
                                    contentDescription = null,
                                    tint = Navy950,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = when (authTab) {
                                        0 -> "Sign In"
                                        1 -> "Create VitaVue Account"
                                        else -> "Send Reset Link"
                                    },
                                    color = Navy950,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "🔒 Zero Image Upload: Food images are processed on-device and never sent or stored in Firestore. Only verified nutrition metrics synchronize.",
                            color = Neutral400,
                            fontSize = 10.sp,
                            lineHeight = 14.sp
                        )
                    }
                }
            }
        }

        // Dietary Targets Card
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
                        text = "PRIMARY NUTRITION GOAL",
                        color = Neutral400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(goalsList) { g ->
                            DietaryTagChip(
                                tag = g,
                                isSelected = goal == g,
                                onClick = { goal = g }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "DIETARY PATTERN",
                        color = Neutral400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(patternsList) { p ->
                            DietaryTagChip(
                                tag = p,
                                isSelected = pattern == p,
                                onClick = { pattern = p }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = calorieTarget,
                        onValueChange = { calorieTarget = it },
                        label = { Text("Daily Calorie Target (kcal)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Teal400,
                            unfocusedBorderColor = Navy700,
                            focusedContainerColor = Navy850,
                            unfocusedContainerColor = Navy850,
                            focusedTextColor = Neutral50,
                            unfocusedTextColor = Neutral50
                        )
                    )
                }
            }
        }

        // Units Toggle
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Navy700, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Metric Measurement System",
                            color = Neutral50,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Use grams (g), milliliters (ml), and kilocalories (kcal)",
                            color = Neutral400,
                            fontSize = 11.sp
                        )
                    }

                    Switch(
                        checked = useMetric,
                        onCheckedChange = { useMetric = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Teal400,
                            checkedTrackColor = Teal500.copy(alpha = 0.3f),
                            uncheckedThumbColor = Neutral400,
                            uncheckedTrackColor = Navy800
                        )
                    )
                }
            }
        }

        // Save Button
        item {
            Button(
                onClick = {
                    val cal = calorieTarget.toIntOrNull() ?: 2100
                    viewModel.updateUserProfile(
                        currentProfile.copy(
                            name = name,
                            goal = goal,
                            dietaryPattern = pattern,
                            dailyCalorieTarget = cal,
                            isMetric = useMetric
                        )
                    )
                    savedFeedback = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("save_profile_button"),
                colors = ButtonDefaults.buttonColors(containerColor = if (savedFeedback) Emerald400 else Teal500),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = if (savedFeedback) Icons.Default.Check else Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Navy950,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (savedFeedback) "Preferences Saved" else "Save Profile Preferences",
                    color = Navy950,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        // Responsible AI & Disclosure Links
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Navy700, RoundedCornerShape(12.dp))
                        .clickable { viewModel.navigateTo("responsible_ai") },
                    color = Navy900,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = "Ethics",
                            tint = Emerald400,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Responsible AI",
                            color = Neutral100,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "Non-Medical Ethics & Disclaimers",
                            color = Neutral400,
                            fontSize = 10.sp
                        )
                    }
                }

                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Navy700, RoundedCornerShape(12.dp))
                        .clickable { viewModel.navigateTo("about") },
                    color = Navy900,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "About",
                            tint = Teal400,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "About VitaVue",
                            color = Neutral100,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "System Architecture & Citations",
                            color = Neutral400,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ResponsibleAIScreen(
    viewModel: VitaVueViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Navy950)
            .testTag("responsible_ai_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            IconButton(
                onClick = { viewModel.navigateTo("home") },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Neutral100
                )
            }
        }

        item {
            Column {
                Text(
                    text = "Scientific & Non-Medical AI Disclosure",
                    color = Neutral50,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "VitaVue is engineered with strict ethical AI guardrails, nutritional science transparency, and non-diagnostic boundaries.",
                    color = Neutral300,
                    fontSize = 13.sp
                )
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Emerald400.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = Emerald400,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "1. Non-Medical, Educational Nature",
                            color = Emerald400,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "VitaVue is an educational nutrition intelligence tool, not a certified medical device or clinical diagnostic service. It does not provide medical diagnoses, treatment plans, or prescription dosage advice. Users with acute metabolic conditions, eating disorders, or pregnancy should consult a registered dietitian or licensed medical professional.",
                        color = Neutral200,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Amber400.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Science,
                            contentDescription = null,
                            tint = Amber400,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "2. Visual Portion Estimation & Uncertainty",
                            color = Amber400,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Visual estimation from 2D images cannot capture hidden fats, cooking oils, specific sodium concentrations, or internal recipe ratios with 100% precision. VitaVue explicitly indicates confidence ratings and transparent uncertainty notes on every analysis.",
                        color = Neutral200,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Teal400.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = Teal400,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "3. Privacy & On-Device Security",
                            color = Teal400,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Personal meal logs and preferences are stored locally on the device through Room Database. API keys and credentials are encrypted and never exposed in client bundles.",
                        color = Neutral200,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AboutScreen(
    viewModel: VitaVueViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Navy950)
            .testTag("about_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            IconButton(
                onClick = { viewModel.navigateTo("home") },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Neutral100
                )
            }
        }

        // Title Header
        item {
            Column {
                VitaVueLogo()
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "About VitaVue",
                    color = Neutral50,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "“Where Nutrition Meets Intelligence.”",
                    color = Teal400,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "A multimodal AI nutrition intelligence platform built to make dietary awareness intuitive, evidence-based, and actionable for everyday life.",
                    color = Neutral300,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }

        // Executive Summary & Core Mission Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Teal500.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "CORE POSITIONING & MISSION",
                        color = Teal400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "VitaVue is a multimodal AI nutrition intelligence application designed to help people better understand their food, learn practical nutrition, make informed everyday decisions, and improve their overall well-being.",
                        color = Neutral50,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Rather than simply tracking numbers or calories, VitaVue focuses on nutritional awareness, human-centered technology, evidence-based learning, and the practical application of nutrition knowledge to foster continuous self-improvement.",
                        color = Neutral300,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Core Philosophy Card: SEE -> ANALYZE -> UNDERSTAND -> LEARN -> PLAN -> PRACTICE -> IMPROVE
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Amber400.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "THE VITAVUE PHILOSOPHY",
                        color = Amber400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Navy850,
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Amber400.copy(alpha = 0.3f))
                    ) {
                        Text(
                            text = "SEE → ANALYZE → UNDERSTAND → LEARN → PLAN → PRACTICE → IMPROVE",
                            color = Amber300,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(10.dp),
                            lineHeight = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "VitaVue connects visual food understanding, nutrition science, AI reasoning, education, planning, and practical improvement into one cohesive, supportive experience.",
                        color = Neutral200,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Creator & Development Attribution Card
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
                        text = "CREATOR & DEVELOPMENT ATTRIBUTION",
                        color = Neutral400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Lead Developer: Muhammad Ahsan Shahzad",
                        color = Neutral50,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "An independent university student from Pakistan building VitaVue to explore how multimodal AI can make nutrition intelligence more accessible, understandable, and personalized for diverse global cultures.",
                        color = Neutral300,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Core Capabilities Card
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
                        text = "CORE CAPABILITIES",
                        color = Neutral400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        CapabilityItem(
                            title = "1. AI Vision Nutrition Analysis",
                            desc = "Multimodal meal analysis estimating components, portions, calories, macros, micronutrients, and culinary suggestions with transparent confidence reporting."
                        )
                        CapabilityItem(
                            title = "2. International Food Explorer",
                            desc = "64 curated global foods and regional dishes with macronutrient ratios, glycemic context, and culinary preparation science."
                        )
                        CapabilityItem(
                            title = "3. Evidence-Grounded Nutrition Hub",
                            desc = "8 foundational nutrition science categories, myth-vs-fact analyses, and cited metabolic insights."
                        )
                        CapabilityItem(
                            title = "4. Personalized Diet Planner",
                            desc = "Dynamic 3-day and weekly planning tailored to health targets, dietary patterns, and cultural cuisine preferences."
                        )
                        CapabilityItem(
                            title = "5. My Nutrition Intelligence",
                            desc = "Private on-device persistence using Room Database for meal logs, favorite foods, bookmarked articles, custom diet plans, and user preferences."
                        )
                    }
                }
            }
        }

        // Scientific References & Data Sources
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
                        text = "DATA SOURCES & NUTRITION REFERENCES",
                        color = Neutral400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• USDA FoodData Central (Nutritional Composition Data)\n• World Health Organization (WHO Nutritional Guidance)\n• European Food Safety Authority (EFSA Reference Values)",
                        color = Neutral200,
                        fontSize = 12.sp,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Reference datasets provide foundational nutrition science baselines. Multimodal AI estimations are informed by these standards and clearly identified as visual approximations.",
                        color = Neutral400,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Technology Architecture
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
                        text = "TECHNICAL ARCHITECTURE",
                        color = Neutral400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Platform: Android Native (Kotlin DSL)\n• UI System: Jetpack Compose & Material Design 3\n• Architecture: Clean MVVM + Repository Pattern\n• AI Core: Google Gemini Multimodal Vision & Intelligence\n• Local Persistence: Android Room Database with SQLite & KSP\n• Asynchronous: Kotlin Coroutines & StateFlow\n• Networking: OkHttp REST Client\n• Testing: Local JVM Robolectric & Roborazzi Suite",
                        color = Neutral200,
                        fontSize = 12.sp,
                        lineHeight = 20.sp
                    )
                }
            }
        }

        // Lead Developer & Project Attribution
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Teal500.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "LEAD DEVELOPER & SYSTEM ARCHITECT",
                        color = Teal400,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Muhammad Ahsan Shahzad",
                        color = Neutral50,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "VitaVue — Multimodal AI Nutrition Intelligence Platform\nProof of Possible 2026",
                        color = Neutral300,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }
        }

        // Responsible AI & Educational Scope
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, Emerald400.copy(alpha = 0.4f), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Navy900)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = Emerald400,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "RESPONSIBLE AI & SAFETY POSITIONING",
                            color = Emerald400,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "VitaVue is an educational and nutritional awareness tool. It provides informational estimates and reasoning to support everyday well-being. It does not provide medical diagnosis, clinical treatment plans, or replace consultations with licensed dietitians or medical professionals.",
                        color = Neutral200,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun CapabilityItem(
    title: String,
    desc: String
) {
    Column {
        Text(
            text = title,
            color = Teal300,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = desc,
            color = Neutral300,
            fontSize = 12.sp,
            lineHeight = 17.sp
        )
    }
}
