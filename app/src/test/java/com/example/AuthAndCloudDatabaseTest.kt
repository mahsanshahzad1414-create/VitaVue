package com.example

import android.app.Application
import android.graphics.Bitmap
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.AuthUiState
import com.example.data.model.AuthUser
import com.example.data.model.FoodAnalysisResult
import com.example.data.model.FoodComponent
import com.example.data.model.Micronutrient
import com.example.data.model.SyncUiState
import com.example.data.model.UserDietPlan
import com.example.data.model.UserProfile
import com.example.data.remote.FirebaseAuthService
import com.example.data.remote.FirestoreSyncService
import com.example.data.repository.VitaVueRepository
import com.example.ui.viewmodel.AnalysisUiState
import com.example.ui.viewmodel.VitaVueViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class AuthAndCloudDatabaseTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var application: Application
    private lateinit var repository: VitaVueRepository
    private lateinit var viewModel: VitaVueViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        application = ApplicationProvider.getApplicationContext()
        repository = VitaVueRepository(application)
        viewModel = VitaVueViewModel(application)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ==========================================
    // 1. AUTHENTICATION FLOW TESTS
    // ==========================================

    @Test
    fun testAuth_InitialStateIsUnauthenticatedOrIdle() {
        assertNull("Current user should initially be null without session", viewModel.currentUser.value)
        assertTrue("Auth state should initially be Idle", viewModel.authUiState.value is AuthUiState.Idle)
    }

    @Test
    fun testAuth_RegisterValidation_RejectsInvalidEmail() = runTest(testDispatcher) {
        viewModel.registerWithEmail("invalid-email", "password123", "Chef Alex")
        advanceUntilIdle()

        assertTrue("Auth state must be Error on invalid email", viewModel.authUiState.value is AuthUiState.Error)
        val err = (viewModel.authUiState.value as AuthUiState.Error).message
        assertTrue(err.contains("valid email", ignoreCase = true))
    }

    @Test
    fun testAuth_RegisterValidation_RejectsShortPassword() = runTest(testDispatcher) {
        viewModel.registerWithEmail("alex@example.com", "123", "Chef Alex")
        advanceUntilIdle()

        assertTrue("Auth state must be Error on short password", viewModel.authUiState.value is AuthUiState.Error)
        val err = (viewModel.authUiState.value as AuthUiState.Error).message
        assertTrue(err.contains("6 characters", ignoreCase = true))
    }

    @Test
    fun testAuth_LoginValidation_RejectsEmptyPassword() = runTest(testDispatcher) {
        viewModel.signInWithEmail("alex@example.com", "")
        advanceUntilIdle()

        assertTrue("Auth state must be Error on empty password", viewModel.authUiState.value is AuthUiState.Error)
        val err = (viewModel.authUiState.value as AuthUiState.Error).message
        assertTrue(err.contains("password", ignoreCase = true))
    }

    @Test
    fun testAuth_PasswordResetValidation_RejectsInvalidEmail() = runTest(testDispatcher) {
        viewModel.sendPasswordReset("not-an-email")
        advanceUntilIdle()

        assertTrue("Auth state must be Error on invalid reset email", viewModel.authUiState.value is AuthUiState.Error)
    }

    @Test
    fun testAuth_SignOut_ResetsStateToUnauthenticated() = runTest(testDispatcher) {
        viewModel.signOut()
        advanceUntilIdle()

        assertNull("Current user must be null after sign out", viewModel.currentUser.value)
        assertTrue("Auth state must be Unauthenticated", viewModel.authUiState.value is AuthUiState.Unauthenticated)
        assertTrue("Sync state should reset to Idle", viewModel.syncUiState.value is SyncUiState.Idle)
    }

    // ==========================================
    // 2. CLOUD DATABASE & PERSISTENCE TESTS
    // ==========================================

    @Test
    fun testPersistence_LocalRoomOperatesWhenUnauthenticated() = runTest(testDispatcher) {
        // Room works seamlessly offline/logged-out
        val testFoodId = "superfood_matcha"
        repository.toggleSaveFood(testFoodId)
        advanceUntilIdle()

        val saved = repository.getSavedFoodIdsFlow().first()
        assertTrue("Food should be saved in local Room even when logged out", saved.contains(testFoodId))

        repository.toggleSaveFood(testFoodId)
        advanceUntilIdle()

        val savedAfter = repository.getSavedFoodIdsFlow().first()
        assertFalse("Food should be removed on toggle", savedAfter.contains(testFoodId))
    }

    @Test
    fun testPersistence_BookmarksInLocalRoom() = runTest(testDispatcher) {
        val testSlug = "mediterranean-longevity-dietary-pattern"
        repository.toggleBookmarkArticle(testSlug)
        advanceUntilIdle()

        val bookmarks = repository.getBookmarkedArticleSlugsFlow().first()
        assertTrue("Article slug should be in bookmarks", bookmarks.contains(testSlug))
    }

    @Test
    fun testPersistence_UserProfileSaving() = runTest(testDispatcher) {
        val updatedProfile = UserProfile(
            name = "Dr. Elena Rostova",
            goal = "Metabolic Health",
            dietaryPattern = "Mediterranean",
            dailyCalorieTarget = 2300,
            isMetric = true
        )
        repository.saveUserProfile(updatedProfile)
        advanceUntilIdle()

        val loaded = repository.getUserProfileFlow().first()
        assertEquals("Dr. Elena Rostova", loaded.name)
        assertEquals("Metabolic Health", loaded.goal)
        assertEquals(2300, loaded.dailyCalorieTarget)
    }

    @Test
    fun testCloudSync_RequiresAuthentication() = runTest(testDispatcher) {
        // Calling syncWithCloud when logged out should gracefully set an error state without crashing
        viewModel.syncWithCloud()
        advanceUntilIdle()

        assertTrue("Sync state should be Error when unauthenticated", viewModel.syncUiState.value is SyncUiState.Error)
    }

    // ==========================================
    // 3. MEAL ANALYZER SAFETY INTEGRITY
    // ==========================================

    @Test
    fun testAnalyzer_NullImageNeverPersistsAnalysisRecord() = runTest(testDispatcher) {
        viewModel.analyzeMeal(null)
        advanceUntilIdle()

        assertNull("Analysis result must be null", viewModel.currentAnalysis.value)
        assertTrue("Analysis state must be Error", viewModel.analysisState.value is AnalysisUiState.Error)

        val history = repository.getAllAnalysesFlow().first()
        // No fake record created
        assertTrue("No record should be created in history for null image", history.isEmpty())
    }

    @Test
    fun testAnalyzer_ValidResultCanBeSavedAndDeleted() = runTest(testDispatcher) {
        val testResult = FoodAnalysisResult(
            id = "test_analysis_cuj_1",
            mealTitle = "Grilled Salmon Bowl",
            mealDescription = "Wild salmon with quinoa and steamed broccoli.",
            totalCalories = 540,
            totalProteinGrams = 42f,
            totalCarbsGrams = 45f,
            totalFatGrams = 18f,
            totalFiberGrams = 7f,
            detectedItems = listOf(
                FoodComponent("Wild Salmon", "150g", 280, 34f, 0f, 15f, 0f),
                FoodComponent("Quinoa", "1 cup", 220, 8f, 39f, 3f, 5f)
            ),
            micronutrients = listOf(
                Micronutrient("Omega-3 EPA/DHA", "1800 mg", 100, "Cardiovascular and cognitive health")
            ),
            nutritionHighlights = listOf("High protein density"),
            practicalSuggestions = listOf("Add lemon juice for iron absorption"),
            confidenceRating = "HIGH",
            uncertaintyNote = "Portion estimated from visual geometry"
        )

        repository.saveAnalysisToHistory(testResult)
        advanceUntilIdle()

        val history = repository.getAllAnalysesFlow().first()
        assertTrue("History should contain saved analysis", history.any { it.id == "test_analysis_cuj_1" })

        repository.deleteAnalysis("test_analysis_cuj_1")
        advanceUntilIdle()

        val historyAfter = repository.getAllAnalysesFlow().first()
        assertFalse("Analysis should be deleted from history", historyAfter.any { it.id == "test_analysis_cuj_1" })
    }
}
