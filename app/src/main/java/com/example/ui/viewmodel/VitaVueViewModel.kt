package com.example.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.AgentAction
import com.example.data.model.AgentActionType
import com.example.data.model.ArticleCategory
import com.example.data.model.ChatMessage
import com.example.data.model.FoodAnalysisResult
import com.example.data.model.FoodCategory
import com.example.data.model.FoodItem
import com.example.data.model.NutritionArticle
import com.example.data.model.NutritionMyth
import com.example.data.model.UserDietPlan
import com.example.data.model.UserProfile
import com.example.data.repository.VitaVueRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class AnalysisUiState {
    object Idle : AnalysisUiState()
    object Analyzing : AnalysisUiState()
    data class Success(val result: FoodAnalysisResult) : AnalysisUiState()
    data class Error(val message: String) : AnalysisUiState()
}

class VitaVueViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = VitaVueRepository(application)

    // Navigation state
    private val _currentDestination = MutableStateFlow("home")
    val currentDestination: StateFlow<String> = _currentDestination.asStateFlow()

    // Analysis State
    private val _analysisState = MutableStateFlow<AnalysisUiState>(AnalysisUiState.Idle)
    val analysisState: StateFlow<AnalysisUiState> = _analysisState.asStateFlow()

    private val _currentAnalysis = MutableStateFlow<FoodAnalysisResult?>(null)
    val currentAnalysis: StateFlow<FoodAnalysisResult?> = _currentAnalysis.asStateFlow()

    private val _selectedImageBitmap = MutableStateFlow<Bitmap?>(null)
    val selectedImageBitmap: StateFlow<Bitmap?> = _selectedImageBitmap.asStateFlow()

    val analysisHistory: StateFlow<List<FoodAnalysisResult>> = repository.getAllAnalysesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Agent State
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                isUser = false,
                message = "Hello! I am your **VitaVue Intelligence Agent**. I provide real-time nutritional science, visual food interpretation, portion intelligence, and tailored dietary advice.\n\nYou can ask me questions about any meal you scan, explore food items, or build custom diet plans.",
                quickPrompts = listOf(
                    "How much protein do I need daily?",
                    "What makes the Mediterranean diet so healthy?",
                    "How does dietary fiber affect my gut microbiome?",
                    "Can you suggest a high-protein vegetarian meal?"
                )
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isAgentTyping = MutableStateFlow(false)
    val isAgentTyping: StateFlow<Boolean> = _isAgentTyping.asStateFlow()

    private val _activeMealContext = MutableStateFlow<FoodAnalysisResult?>(null)
    val activeMealContext: StateFlow<FoodAnalysisResult?> = _activeMealContext.asStateFlow()

    // Food Explorer State
    private val _foodSearchQuery = MutableStateFlow("")
    val foodSearchQuery: StateFlow<String> = _foodSearchQuery.asStateFlow()

    private val _selectedFoodCategory = MutableStateFlow<FoodCategory?>(null)
    val selectedFoodCategory: StateFlow<FoodCategory?> = _selectedFoodCategory.asStateFlow()

    private val _selectedDietaryTag = MutableStateFlow<String?>("All")
    val selectedDietaryTag: StateFlow<String?> = _selectedDietaryTag.asStateFlow()

    private val _selectedFoodDetail = MutableStateFlow<FoodItem?>(null)
    val selectedFoodDetail: StateFlow<FoodItem?> = _selectedFoodDetail.asStateFlow()

    // Comparison Mode
    private val _compareFoodA = MutableStateFlow<FoodItem?>(null)
    val compareFoodA: StateFlow<FoodItem?> = _compareFoodA.asStateFlow()

    private val _compareFoodB = MutableStateFlow<FoodItem?>(null)
    val compareFoodB: StateFlow<FoodItem?> = _compareFoodB.asStateFlow()

    val savedFoodIds: StateFlow<Set<String>> = repository.getSavedFoodIdsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    // Knowledge Hub State
    private val _learnSearchQuery = MutableStateFlow("")
    val learnSearchQuery: StateFlow<String> = _learnSearchQuery.asStateFlow()

    private val _selectedArticleCategory = MutableStateFlow<ArticleCategory?>(null)
    val selectedArticleCategory: StateFlow<ArticleCategory?> = _selectedArticleCategory.asStateFlow()

    private val _selectedArticle = MutableStateFlow<NutritionArticle?>(null)
    val selectedArticle: StateFlow<NutritionArticle?> = _selectedArticle.asStateFlow()

    val bookmarkedArticleSlugs: StateFlow<Set<String>> = repository.getBookmarkedArticleSlugsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    val allMyths: List<NutritionMyth> = repository.getAllMyths()

    // Diet Planner State
    private val _currentDietPlan = MutableStateFlow<UserDietPlan?>(null)
    val currentDietPlan: StateFlow<UserDietPlan?> = _currentDietPlan.asStateFlow()

    private val _isGeneratingPlan = MutableStateFlow(false)
    val isGeneratingPlan: StateFlow<Boolean> = _isGeneratingPlan.asStateFlow()

    val savedMealPlans: StateFlow<List<UserDietPlan>> = repository.getAllSavedMealPlansFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // User Profile State
    val userProfile: StateFlow<UserProfile> = repository.getUserProfileFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfile())

    init {
        // Generate an initial personalized diet plan for instant usability
        generateNewMealPlan("Balanced Wellness", "Mediterranean / Flexible", 2150, "Mediterranean")
    }

    // --- NAVIGATION ---
    fun navigateTo(route: String) {
        _currentDestination.value = route
    }

    // --- FOOD ANALYSIS ACTIONS ---
    fun setSelectedBitmap(bitmap: Bitmap?) {
        _selectedImageBitmap.value = bitmap
    }

    fun analyzeMeal(bitmap: Bitmap?, presetTitle: String? = null) {
        _analysisState.value = AnalysisUiState.Analyzing
        viewModelScope.launch {
            try {
                val result = repository.analyzeFood(bitmap, presetTitle)
                _currentAnalysis.value = result
                _activeMealContext.value = result
                _analysisState.value = AnalysisUiState.Success(result)
            } catch (e: Exception) {
                _analysisState.value = AnalysisUiState.Error(e.localizedMessage ?: "Analysis failed")
            }
        }
    }

    fun setAnalysisContextForAgent(analysis: FoodAnalysisResult) {
        _activeMealContext.value = analysis
        _currentDestination.value = "agent"
        sendAgentMessage("I have attached my analyzed meal: **${analysis.mealTitle}** (~${analysis.totalCalories} kcal, ${analysis.totalProteinGrams}g protein, ${analysis.totalFiberGrams}g fiber). Can you break down its key nutritional pros and how I can balance it further?")
    }

    fun clearActiveMealContext() {
        _activeMealContext.value = null
    }

    fun deleteAnalysisHistory(id: String) {
        viewModelScope.launch {
            repository.deleteAnalysis(id)
        }
    }

    // --- AGENT CHAT ACTIONS ---
    fun sendAgentMessage(userText: String) {
        if (userText.isBlank()) return

        val userMessage = ChatMessage(
            isUser = true,
            message = userText.trim(),
            attachedMealContext = _activeMealContext.value?.mealTitle
        )

        val updated = _chatMessages.value + userMessage
        _chatMessages.value = updated
        _isAgentTyping.value = true

        viewModelScope.launch {
            try {
                val history = updated.map { Pair(it.message, it.isUser) }
                val (reply, actions) = repository.askAgent(
                    query = userText,
                    history = history,
                    mealContext = _activeMealContext.value,
                    activeFoodName = _selectedFoodDetail.value?.name,
                    activeArticleTitle = _selectedArticle.value?.title,
                    userDietGoal = userProfile.value.goal
                )

                val quickPrompts = when {
                    _activeMealContext.value != null -> listOf(
                        "What is the main protein source in this meal?",
                        "How can I increase the fiber here?",
                        "Is this meal suitable for a low-carb diet?",
                        "What healthy sides would pair well?"
                    )
                    else -> listOf(
                        "How do I balance non-heme iron absorption?",
                        "What are the best plant-based protein sources?",
                        "Explain the 50/25/25 visual plate method",
                        "Tell me about Omega-3 vs Omega-6 balance"
                    )
                }

                val agentMessage = ChatMessage(
                    isUser = false,
                    message = reply,
                    quickPrompts = quickPrompts,
                    actions = actions
                )

                _chatMessages.value = _chatMessages.value + agentMessage
            } catch (e: Exception) {
                _chatMessages.value = _chatMessages.value + ChatMessage(
                    isUser = false,
                    message = "I encountered a transient network issue. Please feel free to ask your nutrition question again."
                )
            } finally {
                _isAgentTyping.value = false
            }
        }
    }

    fun handleAgentAction(action: AgentAction) {
        when (action.type) {
            AgentActionType.ANALYZE_MEAL -> {
                _currentDestination.value = "analyze"
            }
            AgentActionType.EXPLORE_FOOD -> {
                if (action.payload.isNotBlank()) {
                    val food = repository.getFoodById(action.payload)
                    if (food != null) {
                        _selectedFoodDetail.value = food
                        _currentDestination.value = "food_detail"
                        return
                    }
                }
                _currentDestination.value = "food_explorer"
            }
            AgentActionType.READ_ARTICLE -> {
                if (action.payload.isNotBlank()) {
                    val article = repository.getArticleBySlug(action.payload)
                    if (article != null) {
                        _selectedArticle.value = article
                        _currentDestination.value = "article_detail"
                        return
                    }
                }
                _currentDestination.value = "learn"
            }
            AgentActionType.OPEN_PLANNER -> {
                _currentDestination.value = "diet_planner"
            }
        }
    }

    // --- FOOD EXPLORER ACTIONS ---
    fun setFoodSearchQuery(query: String) {
        _foodSearchQuery.value = query
    }

    fun setFoodCategory(category: FoodCategory?) {
        _selectedFoodCategory.value = category
    }

    fun setDietaryTag(tag: String?) {
        _selectedDietaryTag.value = tag
    }

    fun openFoodDetail(food: FoodItem) {
        _selectedFoodDetail.value = food
        _currentDestination.value = "food_detail"
    }

    fun toggleSaveFood(foodId: String) {
        viewModelScope.launch {
            repository.toggleSaveFood(foodId)
        }
    }

    fun setCompareFoodA(food: FoodItem?) {
        _compareFoodA.value = food
    }

    fun setCompareFoodB(food: FoodItem?) {
        _compareFoodB.value = food
    }

    fun startFoodComparison(food1: FoodItem, food2: FoodItem) {
        _compareFoodA.value = food1
        _compareFoodB.value = food2
        _currentDestination.value = "food_compare"
    }

    // --- KNOWLEDGE HUB ACTIONS ---
    fun setLearnSearchQuery(query: String) {
        _learnSearchQuery.value = query
    }

    fun setArticleCategory(category: ArticleCategory?) {
        _selectedArticleCategory.value = category
    }

    fun openArticleDetail(article: NutritionArticle) {
        _selectedArticle.value = article
        _currentDestination.value = "article_detail"
    }

    fun toggleBookmarkArticle(slug: String) {
        viewModelScope.launch {
            repository.toggleBookmarkArticle(slug)
        }
    }

    // --- DIET PLANNER ACTIONS ---
    fun generateNewMealPlan(
        goal: String,
        dietaryPattern: String,
        targetCalories: Int,
        cuisine: String,
        daysCount: Int = 3
    ) {
        _isGeneratingPlan.value = true
        viewModelScope.launch {
            val plan = repository.generateMealPlan(goal, dietaryPattern, targetCalories, cuisine, daysCount)
            _currentDietPlan.value = plan
            _isGeneratingPlan.value = false
        }
    }

    fun saveCurrentMealPlan() {
        val plan = _currentDietPlan.value ?: return
        viewModelScope.launch {
            repository.saveMealPlan(plan)
        }
    }

    fun deleteSavedMealPlan(id: String) {
        viewModelScope.launch {
            repository.deleteMealPlan(id)
        }
    }

    // --- USER PROFILE ACTIONS ---
    fun updateUserProfile(profile: UserProfile) {
        viewModelScope.launch {
            repository.saveUserProfile(profile)
        }
    }

    // --- DATA QUERY HELPERS ---
    fun getAllFoods(): List<FoodItem> = repository.getAllFoods()

    fun searchFoods(query: String, category: FoodCategory?, tag: String?): List<FoodItem> =
        repository.searchFoods(query, category, tag)

    fun getAllArticles(): List<NutritionArticle> = repository.getAllArticles()

    fun searchArticles(query: String, category: ArticleCategory?): List<NutritionArticle> =
        repository.searchArticles(query, category)
}
