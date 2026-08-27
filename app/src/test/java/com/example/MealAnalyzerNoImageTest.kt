package com.example

import android.app.Application
import android.graphics.Bitmap
import androidx.test.core.app.ApplicationProvider
import com.example.data.remote.GeminiService
import com.example.data.repository.VitaVueRepository
import com.example.ui.viewmodel.AnalysisUiState
import com.example.ui.viewmodel.VitaVueViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
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
@Config(sdk = [34])
class MealAnalyzerNoImageTest {

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

    @Test
    fun testInitialState_NoImageAndNoAnalysis() {
        // Test A: Initially, there is no selected image and no analysis result
        assertNull("Selected image must initially be null", viewModel.selectedImageBitmap.value)
        assertNull("Current analysis must initially be null", viewModel.currentAnalysis.value)
        assertTrue("Analysis state must initially be Idle", viewModel.analysisState.value is AnalysisUiState.Idle)
    }

    @Test
    fun testAnalyzeWithNullImage_RejectsAndSetsErrorWithoutAnalysis() = runTest(testDispatcher) {
        // Test B: Calling analyzeMeal with null must NOT generate analysis
        viewModel.analyzeMeal(null)
        advanceUntilIdle()

        assertNull("Current analysis must remain null when analyzed with no image", viewModel.currentAnalysis.value)
        assertNull("Selected bitmap must remain null", viewModel.selectedImageBitmap.value)
        assertTrue("Analysis state must be Error when analyzing with null image", viewModel.analysisState.value is AnalysisUiState.Error)
        val errorState = viewModel.analysisState.value as AnalysisUiState.Error
        assertTrue(errorState.message.contains("No meal image selected", ignoreCase = true))
    }

    @Test
    fun testRemoveImage_ClearsImageAndResetsAnalysisState() {
        // Test C: Setting image and removing it clears state
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        viewModel.setSelectedBitmap(bitmap)
        assertEquals(bitmap, viewModel.selectedImageBitmap.value)

        viewModel.removeSelectedImage()
        assertNull("Selected image must be null after removeSelectedImage", viewModel.selectedImageBitmap.value)
        assertNull("Current analysis must be null after removeSelectedImage", viewModel.currentAnalysis.value)
        assertTrue("Analysis state must be Idle after removeSelectedImage", viewModel.analysisState.value is AnalysisUiState.Idle)
    }

    @Test
    fun testReplacingImage_ClearsPreviousAnalysis() {
        // Test D: Changing image invalidates any previous analysis
        val bitmapA = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val bitmapB = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888)

        viewModel.setSelectedBitmap(bitmapA)
        assertEquals(bitmapA, viewModel.selectedImageBitmap.value)

        // Setting a new bitmap B
        viewModel.setSelectedBitmap(bitmapB)
        assertEquals(bitmapB, viewModel.selectedImageBitmap.value)
        assertNull("Analysis must be cleared when image is replaced", viewModel.currentAnalysis.value)
        assertTrue("Analysis state must reset to Idle", viewModel.analysisState.value is AnalysisUiState.Idle)
    }

    @Test
    fun testRepositoryRejectsNullBitmap() = runTest(testDispatcher) {
        // Test E: Repository strictly throws exception if bitmap is null
        var exceptionThrown = false
        try {
            repository.analyzeFood(null)
        } catch (e: IllegalArgumentException) {
            exceptionThrown = true
        }
        assertTrue("Repository analyzeFood(null) must throw IllegalArgumentException", exceptionThrown)
    }

    @Test
    fun testGeminiServiceRejectsNullBitmap() = runTest(testDispatcher) {
        // Test F: GeminiService strictly throws exception if bitmap is null
        val service = GeminiService()
        var exceptionThrown = false
        try {
            service.analyzeFoodImage(null)
        } catch (e: IllegalArgumentException) {
            exceptionThrown = true
        }
        assertTrue("GeminiService analyzeFoodImage(null) must throw IllegalArgumentException", exceptionThrown)
    }
}
