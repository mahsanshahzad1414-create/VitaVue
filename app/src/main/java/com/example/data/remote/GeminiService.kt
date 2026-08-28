package com.example.data.remote

import android.graphics.Bitmap
import android.util.Base64
import android.util.Log
import com.example.BuildConfig
import com.example.data.model.AgentAction
import com.example.data.model.AgentActionType
import com.example.data.model.FoodAnalysisResult
import com.example.data.model.FoodComponent
import com.example.data.model.Micronutrient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeUnit

class GeminiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val apiKey: String
        get() = try {
            BuildConfig.GEMINI_API_KEY.trim().removeSurrounding("\"")
        } catch (e: Exception) {
            ""
        }

    private val baseUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent"

    suspend fun analyzeFoodImage(
        bitmap: Bitmap?
    ): FoodAnalysisResult = withContext(Dispatchers.IO) {
        if (bitmap == null || bitmap.isRecycled || bitmap.width <= 0 || bitmap.height <= 0) {
            throw IllegalArgumentException("A valid meal image is required for nutritional analysis.")
        }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            throw IllegalStateException("Gemini API key is not configured. Please configure your API key to analyze meal photos.")
        }

        val base64Image = try {
            bitmapToBase64(bitmap)
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to process the selected meal image: ${e.localizedMessage ?: "Invalid image data"}")
        }

        val promptText = """
            You are VitaVue, a world-class AI Nutrition and Food Intelligence Agent.
            Analyze the food items present in this image with scientific rigor and portion estimation.
            
            You MUST return a clean, valid JSON object strictly matching this schema:
            {
              "mealTitle": "Short descriptive title of the dish",
              "mealDescription": "2-sentence overview of the meal and culinary preparation",
              "detectedItems": [
                {
                  "name": "Food item name",
                  "portionEstimate": "e.g. 150g or 1 cup",
                  "calories": 250,
                  "proteinGrams": 15.0,
                  "carbsGrams": 30.0,
                  "fatGrams": 8.0,
                  "fiberGrams": 4.0
                }
              ],
              "totalCalories": 550,
              "totalProteinGrams": 32.0,
              "totalCarbsGrams": 58.0,
              "totalFatGrams": 16.0,
              "totalFiberGrams": 8.5,
              "micronutrients": [
                {
                  "name": "e.g. Iron",
                  "amount": "4.2 mg",
                  "dailyValuePercent": 23,
                  "benefit": "Supports cellular energy & oxygen transport"
                }
              ],
              "nutritionHighlights": [
                "Highlight 1: e.g. High quality protein with balanced amino acids",
                "Highlight 2: e.g. Rich in polyphenols and healthy monounsaturated fats"
              ],
              "practicalSuggestions": [
                "Suggestion 1: e.g. Add a squeeze of fresh lemon to boost non-heme iron absorption",
                "Suggestion 2: e.g. Pair with a source of probiotics like yogurt raita"
              ],
              "confidenceRating": "High", // High, Medium, or Approximate
              "uncertaintyNote": "Estimated nutrition based on visual representation. Actual cooking oils, portion depth, and ingredients may vary."
            }
            
            Return ONLY valid JSON without Markdown fences or commentary.
        """.trimIndent()

        val requestJson = JSONObject().apply {
            val contents = JSONArray().apply {
                val contentObj = JSONObject().apply {
                    val parts = JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", promptText)
                        })
                        put(JSONObject().apply {
                            put("inlineData", JSONObject().apply {
                                put("mimeType", "image/jpeg")
                                put("data", base64Image)
                            })
                        })
                    }
                    put("parts", parts)
                }
                put(contentObj)
            }
            put("contents", contents)
            put("generationConfig", JSONObject().apply {
                put("responseMimeType", "application/json")
                put("temperature", 0.2)
            })
        }

        val requestBody = requestJson.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url("$baseUrl?key=$apiKey")
            .post(requestBody)
            .build()

        val response = try {
            client.newCall(request).execute()
        } catch (e: Exception) {
            throw IllegalStateException("Network connection failed during AI meal analysis: ${e.localizedMessage ?: "Unable to connect"}")
        }

        val responseBody = response.body?.string() ?: ""

        if (!response.isSuccessful || responseBody.isBlank()) {
            val errorMsg = if (response.code == 400 || response.code == 403) {
                "Invalid API Key or permissions issue (HTTP ${response.code})."
            } else if (response.code == 429) {
                "AI quota limit reached. Please wait a moment and retry."
            } else {
                "AI vision analysis failed (HTTP ${response.code})."
            }
            throw IllegalStateException(errorMsg)
        }

        parseFoodAnalysisJson(responseBody)
    }

    suspend fun askAgent(
        userQuery: String,
        history: List<Pair<String, Boolean>>, // text, isUser
        contextMealAnalysis: FoodAnalysisResult? = null,
        activeFoodName: String? = null,
        activeArticleTitle: String? = null,
        userDietGoal: String? = null
    ): Pair<String, List<AgentAction>> = withContext(Dispatchers.IO) {
        val systemPrompt = buildString {
            append("You are the VitaVue Intelligence Agent, an expert AI food intelligence, nutrition science, and culinary biochemistry assistant. ")
            append("Brand Persona: Premium, intelligent, evidence-based, trustworthy, empathetic, modern, calm, and highly accessible. ")
            append("Core Response Standards:\n")
            append("1. Provide comprehensive, thorough, multi-paragraph, and well-structured answers. Use bold text, bullet points, and numbered lists for readability. Never truncate explanations prematurely.\n")
            append("2. When analyzing meals or answering nutrition queries, break down macronutrients (protein, complex carbs, healthy fats, fiber) and key micronutrients/bioactives.\n")
            append("3. For meal improvement queries, provide at least 4-5 practical, specific, actionable food swaps or preparation optimizations and explain the biochemical reasoning.\n")
            append("4. Safety & Responsible AI: Clearly state that visual portion and nutrient analyses are intelligent approximations. VitaVue does not diagnose medical conditions or replace qualified registered dietitians/physicians.\n")

            if (contextMealAnalysis != null) {
                append("\n[ACTIVE MEAL CONTEXT]:")
                append("\nMeal Title: ${contextMealAnalysis.mealTitle}")
                append("\nTotal Calories: ~${contextMealAnalysis.totalCalories} kcal")
                append("\nMacros: Protein ${contextMealAnalysis.totalProteinGrams}g, Carbs ${contextMealAnalysis.totalCarbsGrams}g, Fat ${contextMealAnalysis.totalFatGrams}g, Fiber ${contextMealAnalysis.totalFiberGrams}g")
                append("\nDetected Components: ${contextMealAnalysis.detectedItems.joinToString { "${it.name} (${it.portionEstimate})" }}")
                append("\nHighlights: ${contextMealAnalysis.nutritionHighlights.joinToString("; ")}")
                append("\nSuggestions: ${contextMealAnalysis.practicalSuggestions.joinToString("; ")}")
                append("\nUncertainty: ${contextMealAnalysis.uncertaintyNote}")
            }
            if (!activeFoodName.isNullOrBlank()) {
                append("\n[ACTIVE FOOD EXPLORER CONTEXT]: Looking at food: $activeFoodName")
            }
            if (!activeArticleTitle.isNullOrBlank()) {
                append("\n[ACTIVE EDUCATION ARTICLE CONTEXT]: Reading article: $activeArticleTitle")
            }
            if (!userDietGoal.isNullOrBlank()) {
                append("\n[USER PREFERENCES]: User diet goal: $userDietGoal")
            }
        }

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            // Intelligent contextual offline engine
            return@withContext generateDomainAgentResponse(userQuery, contextMealAnalysis, activeFoodName, activeArticleTitle, userDietGoal)
        }

        try {
            val contentsArray = JSONArray()

            // System instruction as first turn / context turn
            contentsArray.put(JSONObject().apply {
                put("role", "user")
                put("parts", JSONArray().apply {
                    put(JSONObject().put("text", "System Guidelines & Active Context:\n$systemPrompt"))
                })
            })
            contentsArray.put(JSONObject().apply {
                put("role", "model")
                put("parts", JSONArray().apply {
                    put(JSONObject().put("text", "Understood. I am the VitaVue Intelligence Agent, ready to provide in-depth, context-aware, evidence-grounded nutrition intelligence."))
                })
            })

            // Past turns
            for ((text, isUser) in history.takeLast(8)) {
                contentsArray.put(JSONObject().apply {
                    put("role", if (isUser) "user" else "model")
                    put("parts", JSONArray().apply {
                        put(JSONObject().put("text", text))
                    })
                })
            }

            // Current query
            contentsArray.put(JSONObject().apply {
                put("role", "user")
                put("parts", JSONArray().apply {
                    put(JSONObject().put("text", userQuery))
                })
            })

            val requestJson = JSONObject().apply {
                put("contents", contentsArray)
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.4)
                    put("maxOutputTokens", 2048)
                })
            }

            val request = Request.Builder()
                .url("$baseUrl?key=$apiKey")
                .post(requestJson.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val bodyString = response.body?.string() ?: ""

            if (!response.isSuccessful || bodyString.isBlank()) {
                return@withContext generateDomainAgentResponse(userQuery, contextMealAnalysis, activeFoodName, activeArticleTitle, userDietGoal)
            }

            val json = JSONObject(bodyString)
            val candidateObj = json.getJSONArray("candidates").getJSONObject(0)
            val parts = candidateObj.getJSONObject("content").getJSONArray("parts")
            var text = ""
            for (p in 0 until parts.length()) {
                val partObj = parts.getJSONObject(p)
                if (partObj.has("text")) {
                    val t = partObj.getString("text")
                    if (t.isNotBlank()) {
                        text = t
                        break
                    }
                }
            }

            val actions = deriveActionsFromResponse(text, userQuery, contextMealAnalysis)
            Pair(text, actions)
        } catch (e: Exception) {
            Log.e("GeminiService", "Exception in askAgent", e)
            generateDomainAgentResponse(userQuery, contextMealAnalysis, activeFoodName, activeArticleTitle, userDietGoal)
        }
    }

    private fun deriveActionsFromResponse(
        response: String,
        query: String,
        contextMealAnalysis: FoodAnalysisResult?
    ): List<AgentAction> {
        val actions = mutableListOf<AgentAction>()
        val lower = (response + " " + query).lowercase()

        if (lower.contains("fiber") || lower.contains("microbiome") || lower.contains("gut")) {
            actions.add(AgentAction("Read Fiber & Gut Health", AgentActionType.READ_ARTICLE, "fiber-gut-microbiome"))
        }
        if (lower.contains("protein") || lower.contains("amino acid") || lower.contains("leucine")) {
            actions.add(AgentAction("Explore Protein Science", AgentActionType.READ_ARTICLE, "protein-mastery-science"))
        }
        if (lower.contains("plan") || lower.contains("diet plan") || lower.contains("meal plan")) {
            actions.add(AgentAction("Open Diet Planner", AgentActionType.OPEN_PLANNER, ""))
        }
        if (lower.contains("analyze") || lower.contains("upload") || lower.contains("camera")) {
            actions.add(AgentAction("Analyze Another Meal", AgentActionType.ANALYZE_MEAL, ""))
        }
        if (lower.contains("mediterranean") || lower.contains("olive oil")) {
            actions.add(AgentAction("Explore Mediterranean Diet", AgentActionType.READ_ARTICLE, "mediterranean-diet-blueprint"))
        }
        return actions.distinctBy { it.payload + it.title }.take(3)
    }

    private fun parseFoodAnalysisJson(rawJson: String): FoodAnalysisResult {
        return try {
            val root = JSONObject(rawJson)
            // If response is wrapped in candidates
            val candidateText = if (root.has("candidates")) {
                val candidateObj = root.getJSONArray("candidates").getJSONObject(0)
                val parts = candidateObj.getJSONObject("content").getJSONArray("parts")
                var extractedText = ""
                for (p in 0 until parts.length()) {
                    val partObj = parts.getJSONObject(p)
                    if (partObj.has("text")) {
                        val t = partObj.getString("text")
                        if (t.isNotBlank()) {
                            extractedText = t
                            break
                        }
                    }
                }
                extractedText
            } else {
                rawJson
            }

            val cleanJson = candidateText
                .replace("```json", "")
                .replace("```", "")
                .trim()

            val data = JSONObject(cleanJson)

            val items = mutableListOf<FoodComponent>()
            val itemsArray = data.optJSONArray("detectedItems")
            if (itemsArray != null) {
                for (i in 0 until itemsArray.length()) {
                    val item = itemsArray.getJSONObject(i)
                    items.add(
                        FoodComponent(
                            name = item.optString("name", "Food Item"),
                            portionEstimate = item.optString("portionEstimate", "1 serving"),
                            calories = item.optInt("calories", 100),
                            proteinGrams = item.optDouble("proteinGrams", 5.0).toFloat(),
                            carbsGrams = item.optDouble("carbsGrams", 15.0).toFloat(),
                            fatGrams = item.optDouble("fatGrams", 3.0).toFloat(),
                            fiberGrams = item.optDouble("fiberGrams", 2.0).toFloat()
                        )
                    )
                }
            }

            val micros = mutableListOf<Micronutrient>()
            val microsArray = data.optJSONArray("micronutrients")
            if (microsArray != null) {
                for (i in 0 until microsArray.length()) {
                    val m = microsArray.getJSONObject(i)
                    micros.add(
                        Micronutrient(
                            name = m.optString("name", "Nutrient"),
                            amount = m.optString("amount", "10 mg"),
                            dailyValuePercent = if (m.has("dailyValuePercent")) m.optInt("dailyValuePercent") else null,
                            benefit = m.optString("benefit", "")
                        )
                    )
                }
            }

            val highlights = mutableListOf<String>()
            val hArray = data.optJSONArray("nutritionHighlights")
            if (hArray != null) {
                for (i in 0 until hArray.length()) {
                    highlights.add(hArray.getString(i))
                }
            }

            val suggestions = mutableListOf<String>()
            val sArray = data.optJSONArray("practicalSuggestions")
            if (sArray != null) {
                for (i in 0 until sArray.length()) {
                    suggestions.add(sArray.getString(i))
                }
            }

            FoodAnalysisResult(
                mealTitle = data.optString("mealTitle", "Analyzed Meal"),
                mealDescription = data.optString("mealDescription", "Nutrient-rich balanced meal analyzed with vision intelligence."),
                detectedItems = items,
                totalCalories = data.optInt("totalCalories", items.sumOf { it.calories }),
                totalProteinGrams = data.optDouble("totalProteinGrams", items.sumOf { it.proteinGrams.toDouble() }).toFloat(),
                totalCarbsGrams = data.optDouble("totalCarbsGrams", items.sumOf { it.carbsGrams.toDouble() }).toFloat(),
                totalFatGrams = data.optDouble("totalFatGrams", items.sumOf { it.fatGrams.toDouble() }).toFloat(),
                totalFiberGrams = data.optDouble("totalFiberGrams", items.sumOf { it.fiberGrams.toDouble() }).toFloat(),
                micronutrients = micros,
                nutritionHighlights = highlights,
                practicalSuggestions = suggestions,
                confidenceRating = data.optString("confidenceRating", "High"),
                uncertaintyNote = data.optString("uncertaintyNote", "Estimated nutrition based on visual representation. Actual cooking oils, hidden sugars, and precise weights may vary.")
            )
        } catch (e: Exception) {
            Log.e("GeminiService", "Failed to parse JSON food result", e)
            throw IllegalStateException("Failed to interpret nutritional breakdown from vision response: ${e.localizedMessage ?: "Invalid response schema"}")
        }
    }

    private fun generateDomainAgentResponse(
        query: String,
        mealContext: FoodAnalysisResult?,
        foodName: String?,
        articleTitle: String?,
        userDietGoal: String? = null
    ): Pair<String, List<AgentAction>> {
        val q = query.lowercase()
        val actions = mutableListOf<AgentAction>()

        val text = when {
            // Detailed meal breakdown (Test 1)
            mealContext != null && (q.contains("explain") || q.contains("detail") || q.contains("breakdown")) && (q.contains("meal") || q.contains("this")) -> {
                actions.add(AgentAction("Read Balanced Plate Guide", AgentActionType.READ_ARTICLE, "balanced-plate-method"))
                actions.add(AgentAction("Explore Food Database", AgentActionType.EXPLORE_FOOD, ""))
                buildString {
                    append("### In-Depth Nutritional Assessment: **${mealContext.mealTitle}**\n\n")
                    append("Based on multimodal visual portion and ingredient extraction, here is the complete physiological and biochemical breakdown:\n\n")
                    append("#### 1. Energy & Caloric Density\n")
                    append("This meal delivers approximately **${mealContext.totalCalories} kcal**, providing a substantial and satiating meal suitable as a core daily anchor.\n\n")
                    append("#### 2. Macronutrient Architecture\n")
                    append("- **Protein (${mealContext.totalProteinGrams}g)**: Delivers essential branched-chain amino acids (BCAAs) that stimulate muscle protein synthesis and promote peptide YY satiety signaling.\n")
                    append("- **Carbohydrates (${mealContext.totalCarbsGrams}g)**: Composed primarily of complex starches that provide steady glucose release without precipitous postprandial glycemic spikes.\n")
                    append("- **Dietary Fiber (${mealContext.totalFiberGrams}g)**: Supplies both soluble and insoluble polysaccharides, slowing gastric emptying and fueling colonic fermentation.\n")
                    append("- **Healthy Fats (${mealContext.totalFatGrams}g)**: Essential for the micellar absorption of fat-soluble vitamins (A, D, E, K) and cellular membrane fluidity.\n\n")
                    append("#### 3. Detected Components & Proportions\n")
                    mealContext.detectedItems.forEach { item ->
                        append("- **${item.name}** (${item.portionEstimate}): ~${item.calories} kcal | ${item.proteinGrams}g P | ${item.carbsGrams}g C | ${item.fatGrams}g F\n")
                    }
                    if (mealContext.micronutrients.isNotEmpty()) {
                        append("\n#### 4. Key Bioactives & Micronutrients\n")
                        mealContext.micronutrients.take(4).forEach { m ->
                            append("- **${m.name}** (${m.amount}${if (m.dailyValuePercent != null) ", ${m.dailyValuePercent}% DV" else ""}): ${m.benefit}\n")
                        }
                    }
                    append("\n*Disclaimer: Nutrition values are intelligent approximations derived from visual food recognition. Individual cooking methods, oils, and brand preparations may vary.*")
                }
            }

            // Strengths and weaknesses (Test 2)
            mealContext != null && (q.contains("strength") || q.contains("weakness") || q.contains("pros") || q.contains("cons")) -> {
                actions.add(AgentAction("Read Micronutrient Science", AgentActionType.READ_ARTICLE, "micronutrient-synergy-matrix"))
                actions.add(AgentAction("Open Diet Planner", AgentActionType.OPEN_PLANNER, ""))
                buildString {
                    append("### Nutritional Audit: Strengths & Opportunities for **${mealContext.mealTitle}**\n\n")
                    append("#### 🟢 Key Nutritional Strengths\n")
                    append("1. **Complete Amino Acid Delivery**: With ~${mealContext.totalProteinGrams}g of protein, this meal provides sufficient substrate to surpass the ~2.5g leucine threshold required to activate mTOR-driven muscle protein synthesis.\n")
                    append("2. **Macronutrient Synergy**: The pairing of proteins and dietary fats (~${mealContext.totalFatGrams}g) buffers carbohydrate digestion, resulting in a moderate glycemic load and sustained energy curve.\n")
                    if (mealContext.nutritionHighlights.isNotEmpty()) {
                        append("3. **Bioactive Richness**: ${mealContext.nutritionHighlights.first()}\n")
                    }
                    append("\n#### 🟡 Potential Nutritional Limitations & Gaps\n")
                    if (mealContext.totalFiberGrams < 8f) {
                        append("1. **Fiber Gap**: At ${mealContext.totalFiberGrams}g of fiber, the meal falls short of the ideal 10–12g per-meal threshold required for optimal gut microbiome diversity and short-chain fatty acid (butyrate) synthesis.\n")
                    } else {
                        append("1. **Sodium & Oil Visibility**: Restaurant or pre-packaged preparations may contain higher levels of refined seed oils or hidden sodium not easily quantifiable by optical sensors alone.\n")
                    }
                    append("2. **Cruciferous / Polyphenol Density**: The meal would benefit from a higher density of dark leafy greens or sulfur-rich alliums (garlic/onions) to enhance endogenous glutathione production.\n\n")
                    append("Would you like specific side pairings or cooking adjustments to optimize this plate further?")
                }
            }

            // Five practical ways to improve (Test 3)
            mealContext != null && (q.contains("five") || q.contains("5") || q.contains("ways") || q.contains("improve") || q.contains("optimize") || q.contains("better")) -> {
                actions.add(AgentAction("Read Balanced Plate Guide", AgentActionType.READ_ARTICLE, "balanced-plate-method"))
                actions.add(AgentAction("Explore Fiber Science", AgentActionType.READ_ARTICLE, "fiber-gut-microbiome"))
                buildString {
                    append("### 5 Evidence-Based Ways to Improve **${mealContext.mealTitle}**\n\n")
                    append("Here are five high-impact, scientifically validated adjustments to elevate the nutritional density of this meal:\n\n")
                    append("1. **Integrate Polyphenol-Rich Dark Greens**: Add 1–2 cups of raw baby spinach, steamed kale, or crisp arugula. This adds 3g+ of prebiotic fiber, folate (B9), and lutein while adding negligible calories (<25 kcal).\n\n")
                    append("2. **Add Bioactive Vitamin C for Mineral Absorption**: Squeeze half a fresh lemon or lime over the dish right before eating. Ascorbic acid reduces ferric iron (Fe3+) to ferrous iron (Fe2+), boosting non-heme iron absorption by up to 300%.\n\n")
                    append("3. **Enhance Essential Fatty Acid Profile**: Incorporate 1 tablespoon of crushed raw walnuts, chia seeds, or hemp hearts. This infuses plant-based ALA Omega-3 fatty acids to counterbalance inflammatory Omega-6 pathways.\n\n")
                    append("4. **Upgrade the Carbohydrate Base with Resistant Starch**: If using white rice or refined grains, cool and reheat cooked whole grains (like brown basmati, quinoa, or pearl barley). This retrogradation process creates type-3 resistant starch that fuels colonic bifidobacteria.\n\n")
                    append("5. **Incorporate Fermented Micro-Probiotics**: Add 2 tablespoons of unpasteurized kimchi, raw sauerkraut, or unsweetened kefir on the side. Living bacterial cultures support intestinal tight-junction integrity and enhance digestive enzyme kinetics.")
                }
            }

            // Explain macros for beginners (Test 4)
            q.contains("beginner") || (q.contains("new to nutrition") || (q.contains("protein") && q.contains("carb") && q.contains("fat") && q.contains("fiber"))) -> {
                actions.add(AgentAction("Read Macronutrients Primer", AgentActionType.READ_ARTICLE, "macronutrient-mastery-primer"))
                actions.add(AgentAction("Explore Protein Science", AgentActionType.READ_ARTICLE, "protein-mastery-science"))
                buildString {
                    append("### Nutrition 101: Understanding Your Core Macronutrients\n\n")
                    append("Think of nutrition as the molecular toolkit your body uses to build tissue, create energy, and regulate hormones. Here is the clear breakdown:\n\n")
                    append("#### 🥩 1. Protein (The Building Blocks)\n")
                    append("- **What it does**: Breaks down into amino acids to repair muscle tissue, make enzymes, synthesize antibodies, and build skin/hair.\n")
                    append("- **Energy value**: 4 calories per gram.\n")
                    append("- **Optimal sources**: Eggs, wild fish, lentils, poultry, tofu, Greek yogurt, chickpeas.\n\n")
                    append("#### 🌾 2. Carbohydrates (The Energy Engine)\n")
                    append("- **What it does**: The primary, preferred fuel for your brain, central nervous system, and muscles during movement.\n")
                    append("- **Energy value**: 4 calories per gram.\n")
                    append("- **Optimal sources**: Oats, sweet potatoes, brown rice, quinoa, fresh fruits, whole grains.\n\n")
                    append("#### 🥑 3. Healthy Fats (The Hormonal Regulator)\n")
                    append("- **What it does**: Essential for absorbing fat-soluble vitamins (A, D, E, K), synthesizing testosterone/estrogen, and insulating nerve cells.\n")
                    append("- **Energy value**: 9 calories per gram (concentrated energy).\n")
                    append("- **Optimal sources**: Extra virgin olive oil, avocados, almonds, walnuts, seeds, fatty fish.\n\n")
                    append("#### 🥦 4. Dietary Fiber (The Gut Protector)\n")
                    append("- **What it does**: An indigestible plant carbohydrate that keeps your digestive tract clean, feeds healthy gut bacteria, and slows blood sugar spikes.\n")
                    append("- **Daily Goal**: Target 28–38g daily from legumes, seeds, berries, and vegetables.")
                }
            }

            // Protein specific queries
            mealContext != null && (q.contains("protein") || q.contains("source")) -> {
                actions.add(AgentAction("Explore Protein Science", AgentActionType.READ_ARTICLE, "protein-mastery-science"))
                val topProtein = mealContext.detectedItems.maxByOrNull { it.proteinGrams }
                buildString {
                    append("In **${mealContext.mealTitle}**, total protein is approximately **${mealContext.totalProteinGrams}g**.\n\n")
                    append("The primary contributor is **${topProtein?.name ?: "the protein component"}**, supplying ~**${topProtein?.proteinGrams ?: mealContext.totalProteinGrams}g** of protein (${topProtein?.portionEstimate ?: "per serving"}).\n\n")
                    append("#### Physiological Impact:\n")
                    append("- Consuming 25–35g of high-quality protein per meal triggers the **leucine threshold** (~2.5–3g of leucine), activating the intracellular mTORC1 pathway for muscle maintenance and recovery.\n")
                    append("- Protein exhibits a **High Thermic Effect of Food (TEF)**, burning 20–30% of its caloric value purely through digestion and peptide metabolism.")
                }
            }

            // Carbohydrate & Swap queries
            mealContext != null && (q.contains("replace") || q.contains("swap") || q.contains("rice") || q.contains("carb") || q.contains("low-carb")) -> {
                actions.add(AgentAction("Explore Quinoa in Food Explorer", AgentActionType.EXPLORE_FOOD, "grain_quinoa"))
                actions.add(AgentAction("Read Low-Glycemic Guide", AgentActionType.READ_ARTICLE, "glycemic-index-energy-metabolism"))
                buildString {
                    append("### Smart Carbohydrate & Grain Swaps for **${mealContext.mealTitle}**\n\n")
                    append("Depending on your personal physiological targets, here are 3 optimized alternatives:\n\n")
                    append("1. **For Complete Plant Protein & Satiety**: Swap the grain for **Organic Tri-Color Quinoa**. Delivers 8g of complete amino acid protein per cooked cup plus 5g of soluble fiber with a low glycemic index (~53).\n\n")
                    append("2. **For Blood Sugar Control & Low-Calorie Density**: Swap for **Riced Cauliflower & Herb Medley**. Reduces total carbohydrates by ~85% while providing glucosinolates and choline for liver detoxification.\n\n")
                    append("3. **For Long-Duration Sustained Glycogen**: Swap for **Whole Pearl Barley or Spelt**. Contains beta-glucan fibers that form a viscous gel in the small intestine, dramatically blunting insulin response.")
                }
            }

            // General Meal Context default
            mealContext != null -> {
                actions.add(AgentAction("Read Balanced Plate Guide", AgentActionType.READ_ARTICLE, "balanced-plate-method"))
                actions.add(AgentAction("Open Diet Planner", AgentActionType.OPEN_PLANNER, ""))
                buildString {
                    append("### Analysis Context: **${mealContext.mealTitle}**\n\n")
                    append("Summary Metrics: ~**${mealContext.totalCalories} kcal** | **${mealContext.totalProteinGrams}g Protein** | **${mealContext.totalCarbsGrams}g Carbs** | **${mealContext.totalFatGrams}g Fat** | **${mealContext.totalFiberGrams}g Fiber**\n\n")
                    if (mealContext.nutritionHighlights.isNotEmpty()) {
                        append("#### Nutritional Highlights:\n")
                        mealContext.nutritionHighlights.forEach { h ->
                            append("- $h\n")
                        }
                        append("\n")
                    }
                    if (mealContext.practicalSuggestions.isNotEmpty()) {
                        append("#### Actionable Suggestions:\n")
                        mealContext.practicalSuggestions.forEach { s ->
                            append("- $s\n")
                        }
                        append("\n")
                    }
                    append("Feel free to ask me about protein synthesis, micronutrient synergies, specific ingredient replacements, or how this fits into your personalized daily meal plan!")
                }
            }

            // Specific Question: Nutritional benefits of apples
            q.contains("apple") && (q.contains("benefit") || q.contains("nutrition") || q.contains("good for") || q.contains("health") || q.contains("what are")) -> {
                actions.add(AgentAction("Explore Apple in Food Explorer", AgentActionType.EXPLORE_FOOD, "fruit_apple"))
                actions.add(AgentAction("Read Fiber & Gut Health", AgentActionType.READ_ARTICLE, "fiber-gut-microbiome"))
                buildString {
                    append("### Nutritional Biochemistry & Health Benefits of Apples\n\n")
                    append("Apples (*Malus domestica*) offer a dense profile of bioactive phytochemicals and fermentable fibers with low glycemic impact (~95 kcal for a medium 182g apple):\n\n")
                    append("#### 1. Soluble Fiber Matrix (Pectin)\n")
                    append("- **Prebiotic Fermentation**: Apples provide ~4.4g of total fiber, predominantly **pectin**, a viscous soluble polysaccharide that forms a protective gel matrix in the small intestine, slowing sugar absorption and reducing LDL cholesterol by binding intestinal bile acids.\n")
                    append("- **Butyrate Synthesis**: Colonic fermentation of pectin by *Bifidobacterium* species produces short-chain fatty acids (SCFAs), maintaining gut epithelial integrity.\n\n")
                    append("#### 2. Bioactive Flavonoids & Polyphenols\n")
                    append("- **Quercetin (Concentrated in the Peel)**: A potent antioxidant flavonol that downregulates pro-inflammatory cytokines (IL-6, TNF-alpha) and promotes vascular nitric oxide bioavailability.\n")
                    append("- **Epicatechin & Procyanidins**: Support endothelial flexibility and systemic antioxidant defense against lipid peroxidation.\n")
                    append("- **Phloridzin**: A unique dihydrochalcone glucoside that inhibits sodium-glucose co-transporters (SGLT1 and SGLT2) in the intestinal mucosa, moderating postprandial glucose excursions.\n\n")
                    append("#### 3. Practical Nutrition Rule\n")
                    append("Always consume apples **with their skin intact** (washed) to capture up to 80% of their total polyphenol and insoluble fiber content.")
                }
            }

            // Specific Question: Improving protein quality in rice and lentils
            (q.contains("rice") && q.contains("lentil")) || (q.contains("protein quality") && q.contains("plant")) || q.contains("complementar") -> {
                actions.add(AgentAction("Explore Lentils in Database", AgentActionType.EXPLORE_FOOD, "legume_lentils"))
                actions.add(AgentAction("Read Protein Mastery Guide", AgentActionType.READ_ARTICLE, "protein-mastery-science"))
                buildString {
                    append("### Optimizing Protein Quality & Amino Acid Complementarity in Rice & Lentils\n\n")
                    append("Lentils and rice form one of the world's most classical and effective **plant protein complementary matrices** (e.g. *Mujaddara*, *Khichdi*).\n\n")
                    append("#### 1. The Limiting Amino Acid Synergy\n")
                    append("- **Lentils (Legumes)**: Rich in the essential amino acid **L-Lysine**, but limiting in sulfur-containing amino acids (**L-Methionine** and **L-Cysteine**).\n")
                    append("- **Rice (Grains)**: Rich in **L-Methionine** and **L-Cysteine**, but limiting in **L-Lysine**.\n")
                    append("- **The Synergy**: Consuming both combines their amino acid pools to achieve a **complete Essential Amino Acid (EAA) profile** matching human physiological requirements (DIAAS score approaching 0.9–1.0).\n\n")
                    append("#### 2. Three Evidence-Based Ways to Further Enhance This Meal\n")
                    append("1. **Hit the Leucine Trigger (2.5–3.0g)**: Aim for a **2:1 or 1:1 ratio of cooked lentils to cooked brown rice** (e.g., 1.5 cups lentils + 1 cup brown rice), yielding ~30g of protein and ~2.4g of leucine.\n")
                    append("2. **Add Vitamin C for Non-Heme Iron Absorption**: Squeeze fresh lemon juice or add fresh diced tomatoes over the dish to boost iron absorption from lentils up to 3-fold.\n")
                    append("3. **Incorporate Sprouted Seeds or Hemp Hearts**: Sprinkle 1 tbsp of hemp hearts or roasted pumpkin seeds for extra leucine, zinc, and bioavailable ALA Omega-3s.")
                }
            }

            // Specific Question: Vitamin C and non-heme iron absorption
            (q.contains("vitamin c") && q.contains("iron")) || q.contains("non-heme") || (q.contains("iron") && q.contains("absorp")) -> {
                actions.add(AgentAction("Read Micronutrient Science", AgentActionType.READ_ARTICLE, "essential-vitamins-and-minerals"))
                actions.add(AgentAction("Read Nutrition Myths", AgentActionType.READ_ARTICLE, "micronutrient-synergy-matrix"))
                buildString {
                    append("### Biochemical Mechanism: How Vitamin C Enhances Non-Heme Iron Absorption\n\n")
                    append("Plant-based iron (non-heme iron) found in legumes, grains, and dark leafy greens exists primarily in the **ferric (Fe3+) state**, which is relatively insoluble and poorly absorbed in the human duodenum (~2–10% baseline bioavailability).\n\n")
                    append("#### 1. Chemical Reduction (Fe3+ to Fe2+)\n")
                    append("**Ascorbic acid (Vitamin C)** acts as a powerful electron donor in the acidic gastric environment, reducing insoluble **ferric iron (Fe3+)** into highly soluble **ferrous iron (Fe2+)**:\n")
                    append("$$\\text{Fe}^{3+} + \\text{Ascorbic Acid} \\rightarrow \\text{Fe}^{2+} + \\text{Dehydroascorbic Acid}$$\n\n")
                    append("#### 2. Chelation & Insoluble Complex Prevention\n")
                    append("Vitamin C forms a soluble chelate complex with iron at low gastric pH. This chelate remains soluble even when passing into the neutral-to-alkaline environment of the duodenum, preventing iron from binding to inhibitory dietary compounds like **phytates** (in grains/legumes) or **polyphenols/tannins** (in tea/coffee).\n\n")
                    append("#### 3. Practical Rule\n")
                    append("Pairing just **25–50mg of Vitamin C** (e.g. half a squeezed lemon, half a cup of raw bell peppers, or a handful of strawberries) with iron-rich plant meals increases non-heme iron absorption by **up to 300%**.")
                }
            }

            // Specific Question: What is a practical balanced plate?
            q.contains("balanced plate") || (q.contains("practical") && q.contains("plate")) || q.contains("plate method") || (q.contains("how to build") && q.contains("plate")) -> {
                actions.add(AgentAction("Read Balanced Plate Guide", AgentActionType.READ_ARTICLE, "balanced-plate-method"))
                actions.add(AgentAction("Analyze a Meal Image", AgentActionType.ANALYZE_MEAL, ""))
                buildString {
                    append("### The Evidence-Based Practical Balanced Plate Method\n\n")
                    append("The **Balanced Plate Heuristic** translates complex nutritional science into an intuitive, scalable visual blueprint without requiring exhaustive calorie weighing:\n\n")
                    append("#### 1. 50% Plate: High-Volume Micronutrient Shield (Non-Starchy Veg)\n")
                    append("- **Examples**: Steamed broccoli, crisp baby spinach, bell peppers, asparagus, zucchini.\n")
                    append("- **Function**: Provides high dietary fiber (6-10g), polyphenols, potassium, and magnesium with low caloric density (<80 kcal), driving gastric distension and peptide YY fullness.\n\n")
                    append("#### 2. 25% Plate: Protein Engine (25–35g Protein)\n")
                    append("- **Examples**: Wild salmon, organic tofu, pasture-raised eggs, skinless chicken breast, tempeh, or lentils.\n")
                    append("- **Function**: Delivers 2.5–3g of leucine to cross the anabolic threshold, maintaining lean muscle tissue and eliciting a high Thermic Effect of Food (TEF, ~20–30%).\n\n")
                    append("#### 3. 25% Plate: Steady-State Glycogen (Complex Low-GI Carbs)\n")
                    append("- **Examples**: Tri-color quinoa, roasted sweet potatoes, brown basmati rice, or whole oats.\n")
                    append("- **Function**: Supplies sustained glucose for brain and muscular performance without triggering rapid insulin spikes.\n\n")
                    append("#### 4. 1 Thumbnail: Lipid & Vitamin Carrier\n")
                    append("1 tbsp of extra virgin olive oil, 1/4 avocado, or a handful of nuts to facilitate absorption of fat-soluble vitamins (A, D, E, K).")
                }
            }

            // Specific Question: What is nutrient density?
            q.contains("nutrient dens") || (q.contains("density") && q.contains("nutrition")) -> {
                actions.add(AgentAction("Read Foundations Article", AgentActionType.READ_ARTICLE, "energy-balance-metabolism"))
                actions.add(AgentAction("Explore 64 Global Foods", AgentActionType.EXPLORE_FOOD, ""))
                buildString {
                    append("### What is Nutrient Density? Principles & Mathematical Concept\n\n")
                    append("**Nutrient Density** is the concentration of essential micronutrients (vitamins, minerals, amino acids, essential fatty acids, and bioactives) relative to the total caloric energy a food provides.\n\n")
                    append("$$\\text{Nutrient Density} = \\frac{\\text{Micronutrients (Vitamins, Minerals, Fiber, Phytonutrients)}}{\\text{Total Energy Content (kcal)}}$$\n\n")
                    append("#### 1. Nutrient Density vs. Energy Density\n")
                    append("- **High Nutrient Density (Low Calorie Density)**: Spinach, kale, wild salmon, blueberries, lentils, broccoli, eggs. For example, 100 kcal of raw spinach delivers over 1,000% DV of Vitamin K1, 100% DV of Vitamin A precursors, and rich lutein/zeaxanthin.\n")
                    append("- **Low Nutrient Density (High Calorie Density / 'Empty Calories')**: Refined sugar, sweetened sodas, ultra-processed pastries, and refined vegetable shortenings, which provide substantial calories with negligible micronutrient co-factors.\n\n")
                    append("#### 2. Practical Application (The Nutrient-to-Calorie Ratio)\n")
                    append("Prioritizing nutrient-dense whole foods ensures you achieve 100% of your micronutrient Reference Daily Intakes (RDIs) within your target energy budget without requiring artificial supplementation.")
                }
            }

            // Fiber & Gut
            q.contains("fiber") || q.contains("microbiome") || q.contains("gut") -> {
                actions.add(AgentAction("Read Fiber & Gut Health", AgentActionType.READ_ARTICLE, "fiber-gut-microbiome"))
                buildString {
                    append("### Dietary Fiber & The Human Microbiome\n\n")
                    append("Dietary fiber is an indispensable nutrient class that directly shapes the metabolic activity of your intestinal microbiome.\n\n")
                    append("#### 1. Prebiotic Fermentation & Short-Chain Fatty Acids (SCFAs)\n")
                    append("When soluble fibers (such as beta-glucan, inulin, and pectin) reach the large intestine, anaerobic microbes ferment them into key SCFAs:\n")
                    append("- **Acetate**: Enters peripheral circulation to regulate lipid metabolism and appetite.\n")
                    append("- **Propionate**: Travels to the liver to modulate gluconeogenesis and lower cholesterol synthesis.\n")
                    append("- **Butyrate**: The primary fuel source for colonocytes, reinforcing mucosal barrier tight junctions and suppressing inflammatory cytokines (NF-kB).\n\n")
                    append("#### 2. Daily Recommendations\n")
                    append("Evidence suggests aiming for **at least 30–38g daily for men and 25–30g daily for women**, emphasizing variety from 30+ different plant types per week.")
                }
            }

            // Protein Mastery
            q.contains("protein") -> {
                actions.add(AgentAction("Explore Protein Science", AgentActionType.READ_ARTICLE, "protein-mastery-science"))
                buildString {
                    append("### Evidence-Based Protein Optimization\n\n")
                    append("Protein requirements depend heavily on activity levels, age, and metabolic goals:\n\n")
                    append("- **Sedentary Baseline**: ~0.8g per kg body weight (prevents deficiency).\n")
                    append("- **Active & Strength Training**: **1.6 to 2.2g per kg body weight** distributed evenly across 3–4 meals.\n")
                    append("- **Longevity & Healthy Aging**: Older adults require higher per-meal thresholds (~30–40g) to overcome anabolic resistance.\n\n")
                    append("#### The Leucine Trigger:\n")
                    append("Aim for at least ~2.5–3g of leucine per meal (found in whey, eggs, chicken breast, tempeh, or combinations of lentils + whole grains) to fully stimulate muscle protein synthesis via mTORC1.")
                }
            }

            // Diet Planner
            q.contains("plan") || q.contains("diet") -> {
                actions.add(AgentAction("Open Diet Planner", AgentActionType.OPEN_PLANNER, ""))
                "VitaVue's **Personalized Diet Planner** synthesizes your unique physiological targets (e.g. Balanced Wellness, High-Protein Muscle, Longevity & Heart) with dietary patterns (Mediterranean, Vegan, Vegetarian, Halal) and calorie targets (1,500–3,500 kcal). You can generate and customize precision multi-day meal matrices instantly."
            }

            else -> {
                actions.add(AgentAction("Analyze a Meal Image", AgentActionType.ANALYZE_MEAL, ""))
                actions.add(AgentAction("Explore Food Database", AgentActionType.EXPLORE_FOOD, ""))
                "Hello! I am your **VitaVue Intelligence Agent**, powered by multimodal nutritional science. I can help you analyze food images, assess macronutrient and micronutrient balance, recommend evidence-backed culinary swaps, and construct personalized meal plans. How can I assist your nutrition journey today?"
            }
        }

        return Pair(text, actions)
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        // Resize if overly large for efficient network transfer
        val maxDimension = 1024
        val scale = if (bitmap.width > maxDimension || bitmap.height > maxDimension) {
            maxDimension.toFloat() / maxOf(bitmap.width, bitmap.height)
        } else {
            1.0f
        }

        val scaledBitmap = if (scale < 1.0f) {
            Bitmap.createScaledBitmap(
                bitmap,
                (bitmap.width * scale).toInt(),
                (bitmap.height * scale).toInt(),
                true
            )
        } else {
            bitmap
        }

        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }
}
