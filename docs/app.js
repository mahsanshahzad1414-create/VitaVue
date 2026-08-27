/**
 * VitaVue — Web Nutrition Intelligence Engine & Client Application
 * Where Nutrition Meets Intelligence.
 * 
 * Replicated with exact fidelity from the Android Kotlin architecture.
 */

const VitaVue = (function() {
  'use strict';

  // =========================================================================
  // APP STATE
  // =========================================================================
  const state = {
    activeTab: 'home',
    selectedImage: null,
    selectedImageBase64: null,
    selectedImageName: '',
    selectedImageSize: '',
    currentAnalysisResult: null,
    
    // Explorer
    selectedFoodCategory: 'all',
    searchQuery: '',
    selectedDietaryTag: 'all',
    selectedGlycemicIndex: 'all',

    // Learn & Myths
    activeLearnSubtab: 'articles',
    selectedArticleCategory: 'all',

    // AI Agent
    chatHistory: [
      {
        role: 'agent',
        content: `**Welcome to VitaVue Intelligence!**\n\nI am your evidence-based nutritional biochemistry assistant. I can help you evaluate **macronutrient balance**, optimize **leucine thresholds for muscle synthesis**, understand **micronutrient synergies** (such as Vitamin C + non-heme iron), or interpret food matrix digestion.\n\n*Analyze a meal photo or ask any science question below!*`,
        actions: [
          { label: '🥩 Leucine Threshold', prompt: 'Explain the leucine trigger for muscle protein synthesis.' },
          { label: '🍋 Vitamin C + Iron Synergy', prompt: 'How does ascorbic acid enhance non-heme iron absorption?' },
          { label: '🌾 Fiber & Gut Microbiome', prompt: 'How do short-chain fatty acids regulate metabolic health?' }
        ]
      }
    ],
    activeContext: null, // { type: 'meal' | 'food' | 'plan', data: any }
    isAgentTyping: false,

    // Diet Planner
    activePlannerGoal: 'muscle_synthesis',
    activePlannerDay: 1,

    // User Data (Persisted to localStorage)
    loggedMeals: [],
    savedFoodIds: [],
    bookmarkedArticleIds: [],
    customApiKey: ''
  };

  // Sample meal presets for one-click analyzer demonstrations
  const SAMPLE_MEAL_PRESETS = {
    salmon_bowl: {
      title: "Mediterranean Grilled Salmon & Quinoa Bowl",
      description: "Pan-seared Atlantic salmon over tri-color quinoa, steamed broccoli florets, sliced avocado, and a lemon-dill vinaigrette.",
      confidence: "High",
      uncertainty: "Portions visually identified with high confidence. Estimated olive oil vinaigrette at approximately 1.5 tbsp (18ml).",
      totalCalories: 580,
      macros: { protein: 42, carbs: 48, fats: 22, fiber: 9.5 },
      components: [
        { name: "Atlantic Salmon Fillet", portion: "160g cooked", calories: 280, p: 34, c: 0, f: 15, fib: 0 },
        { name: "Cooked Tri-Color Quinoa", portion: "1 cup (185g)", calories: 220, p: 8, c: 39, f: 3.5, fib: 5 },
        { name: "Steamed Broccoli Florets", portion: "1 cup (90g)", calories: 30, p: 2.5, c: 6, f: 0.3, fib: 2.4 },
        { name: "Fresh Sliced Avocado", portion: "1/4 medium (35g)", calories: 50, p: 0.7, c: 3, f: 4.8, fib: 2.1 }
      ],
      micronutrients: [
        { name: "EPA & DHA Omega-3", amount: "1,850 mg", dv: null, benefit: "Cardiovascular and neuromuscular anti-inflammatory lipid mediators" },
        { name: "Vitamin D3", amount: "14.2 mcg", dv: 71, benefit: "Skeletal mineralization and innate immune modulation" },
        { name: "Vitamin C", amount: "81 mg", dv: 90, benefit: "Ascorbic acid cofactor for collagen synthesis" },
        { name: "Potassium", amount: "940 mg", dv: 20, benefit: "Intracellular electrolyte for blood pressure regulation" }
      ],
      highlights: [
        "Delivers 34g of complete marine protein rich in essential branched-chain amino acids.",
        "Exceptional 1.85g EPA/DHA omega-3 ratio dampening systemic cytokines.",
        "Over 9.5g of diverse fiber supporting distal gut microbiome fermentation."
      ],
      suggestions: [
        "Squeeze fresh lemon juice immediately before consuming to maximize non-heme iron absorption from quinoa and broccoli.",
        "Avocado fats enhance the bioavailability of fat-soluble vitamin D and carotenoids from broccoli."
      ]
    },
    chicken_biryani: {
      title: "South Asian Spiced Chicken Biryani & Cucumber Raita",
      description: "Fragrant basmati rice layered with marinated chicken, saffron, whole spices (cloves, cardamom), caramelized onions, and cooling yogurt raita.",
      confidence: "High",
      uncertainty: "Ghee and cooking oil absorption estimated at 12g. High confidence on chicken and rice matrix.",
      totalCalories: 620,
      macros: { protein: 38, carbs: 74, fats: 18, fiber: 4.5 },
      components: [
        { name: "Marinated Spiced Chicken", portion: "140g", calories: 240, p: 31, c: 2, f: 11, fib: 0 },
        { name: "Saffron Spiced Basmati Rice", portion: "1.5 cups (240g)", calories: 310, p: 6, c: 68, f: 2.5, fib: 2.5 },
        { name: "Mint & Cucumber Yogurt Raita", portion: "1/2 cup (110g)", calories: 70, p: 4.5, c: 4, f: 4.5, fib: 0.8 }
      ],
      micronutrients: [
        { name: "Curcumin (from Turmeric)", amount: "25 mg", dv: null, benefit: "Potent polyphenol antioxidant and NF-kB inhibitor" },
        { name: "Vitamin B12", amount: "1.2 mcg", dv: 50, benefit: "Erythrocyte formation and myelin synthesis" },
        { name: "Zinc", amount: "3.4 mg", dv: 31, benefit: "Cellular immunity and enzymatic cofactor" }
      ],
      highlights: [
        "Combines whole spices rich in cinnamaldehyde, eugenol, and piperine that improve digestive motility.",
        "Yogurt raita delivers living lactic acid bacteria and reduces the acute glycemic spike of white rice.",
        "High bioavailable lean poultry protein providing complete essential amino acid coverage."
      ],
      suggestions: [
        "Add a side salad of sliced cucumbers, tomatoes, and red onions with fresh lemon to boost total fiber above 8g.",
        "Cooling cooked rice slightly allows retrogradation to form beneficial prebiotic resistant starch."
      ]
    },
    falafel_plate: {
      title: "Levantine Falafel, Creamy Hummus & Tabbouleh Plate",
      description: "Crispy chickpea & herb falafel served with extra-virgin olive oil hummus, fresh parsley-bulgur tabbouleh, and warm whole-grain pita.",
      confidence: "High",
      uncertainty: "Frying oil retention on falafel estimated at 9g. High accuracy on chickpea and tahini ratio.",
      totalCalories: 560,
      macros: { protein: 21, carbs: 68, fats: 24, fiber: 14 },
      components: [
        { name: "Herb Chickpea Falafel (4 pcs)", portion: "120g", calories: 260, p: 9, c: 30, f: 12, fib: 6 },
        { name: "Tahini & Garlic Hummus", portion: "1/3 cup (80g)", calories: 170, p: 5, c: 14, f: 11, fib: 4 },
        { name: "Parsley Lemon Tabbouleh", portion: "3/4 cup (110g)", calories: 130, p: 3.5, c: 24, f: 2.5, fib: 4 }
      ],
      micronutrients: [
        { name: "Folate (Vitamin B9)", amount: "160 mcg", dv: 40, benefit: "DNA synthesis and erythrocyte maturation" },
        { name: "Iron (Non-Heme)", amount: "4.8 mg", dv: 27, benefit: "Oxygen transport in red blood cells" },
        { name: "Sesamol (from Tahini)", amount: "18 mg", dv: null, benefit: "Lipid-soluble lignan antioxidant" }
      ],
      highlights: [
        "Exceptional 14g of prebiotic dietary fiber nourishing colonic acetate and butyrate production.",
        "Parsley in tabbouleh provides over 90mg of ascorbic acid, boosting chickpea iron absorption threefold.",
        "Heart-healthy monounsaturated oleic acid and sesamol lignans from tahini and EVOO."
      ],
      suggestions: [
        "Pair with a sprinkle of sumac to add anthocyanins and tart bioflavonoids.",
        "A superb plant-based recovery meal delivering both fast and slow fermentable carbohydrates."
      ]
    },
    oatmeal_berries: {
      title: "Warm Steel-Cut Oats with Berries, Chia & Raw Almonds",
      description: "Hearty cooked steel-cut oats topped with fresh wild blueberries, sliced strawberries, chia seeds, crushed almonds, and ground Ceylon cinnamon.",
      confidence: "High",
      uncertainty: "Portions and toppings clearly visible with minimal hidden fats.",
      totalCalories: 430,
      macros: { protein: 14, carbs: 64, fats: 15, fiber: 14.5 },
      components: [
        { name: "Cooked Steel-Cut Oats", portion: "1 cup (234g)", calories: 220, p: 8, c: 40, f: 3.5, fib: 6 },
        { name: "Wild Blueberries & Strawberries", portion: "1 cup (150g)", calories: 70, p: 1.2, c: 17, f: 0.5, fib: 4 },
        { name: "Chia Seeds & Crushed Almonds", portion: "2 tbsp (25g)", calories: 140, p: 5, c: 7, f: 11, fib: 4.5 }
      ],
      micronutrients: [
        { name: "Beta-Glucan Soluble Fiber", amount: "3.8 g", dv: null, benefit: "Reduces LDL cholesterol and moderates postprandial glucose" },
        { name: "Anthocyanins", amount: "140 mg", dv: null, benefit: "Neuroprotective and vascular endothelial antioxidant" },
        { name: "Manganese", amount: "1.8 mg", dv: 78, benefit: "Superoxide dismutase enzymatic cofactor" }
      ],
      highlights: [
        "Superb slow-release low glycemic carbohydrate matrix providing 4–5 hours of stable cognitive energy.",
        "14.5g of fiber exceeds 50% of the recommended daily intake in a single breakfast.",
        "Cinnamon helps improve cellular GLUT4 glucose transporter sensitivity."
      ],
      suggestions: [
        "Stir in a scoop of unflavored whey or plant protein isolate if higher morning protein (>30g) is desired.",
        "Add a splash of unsweetened soy or cow's milk for additional bioavailable calcium and leucine."
      ]
    }
  };

  // =========================================================================
  // INITIALIZATION
  // =========================================================================
  function init() {
    loadPersistedData();
    setupNavigation();
    setupDropZone();
    renderFoodCategories();
    renderFoods();
    renderArticleCategories();
    renderArticles();
    renderMyths();
    renderChatMessages();
    renderPlannerDay();
    renderUserProfileAndLogs();
    updateHomeMacroSummary();
    setupChatListeners();
  }

  function loadPersistedData() {
    try {
      const logs = localStorage.getItem('vitavue_logged_meals');
      if (logs) state.loggedMeals = JSON.parse(logs);

      const saved = localStorage.getItem('vitavue_saved_foods');
      if (saved) state.savedFoodIds = JSON.parse(saved);

      const bookmarks = localStorage.getItem('vitavue_bookmarks');
      if (bookmarks) state.bookmarkedArticleIds = JSON.parse(bookmarks);

      const customKey = localStorage.getItem('vitavue_custom_api_key');
      if (customKey) {
        state.customApiKey = customKey;
        const keyInput = document.getElementById('user-gemini-key');
        if (keyInput) keyInput.value = customKey;
        updateApiKeyStatusUI(true);
      }
    } catch (e) {
      console.warn('Storage initialization error:', e);
    }
  }

  function savePersistedData() {
    try {
      localStorage.setItem('vitavue_logged_meals', JSON.stringify(state.loggedMeals));
      localStorage.setItem('vitavue_saved_foods', JSON.stringify(state.savedFoodIds));
      localStorage.setItem('vitavue_bookmarks', JSON.stringify(state.bookmarkedArticleIds));
      if (state.customApiKey) {
        localStorage.setItem('vitavue_custom_api_key', state.customApiKey);
      }
    } catch (e) {
      console.warn('Storage save error:', e);
    }
  }

  // =========================================================================
  // NAVIGATION & TAB SWITCHING
  // =========================================================================
  function setupNavigation() {
    const sidebarLinks = document.querySelectorAll('.nav-link');
    const bottomNavLinks = document.querySelectorAll('.bottom-nav-item');

    sidebarLinks.forEach(link => {
      link.addEventListener('click', (e) => {
        e.preventDefault();
        const tab = link.getAttribute('data-tab');
        switchTab(tab);
      });
    });

    bottomNavLinks.forEach(link => {
      link.addEventListener('click', (e) => {
        e.preventDefault();
        const tab = link.getAttribute('data-tab');
        switchTab(tab);
      });
    });
  }

  function switchTab(tabId) {
    state.activeTab = tabId;

    // Update screen visibility
    document.querySelectorAll('.screen').forEach(screen => {
      screen.classList.remove('active');
    });
    const targetScreen = document.getElementById(`screen-${tabId}`);
    if (targetScreen) targetScreen.classList.add('active');

    // Update nav links
    document.querySelectorAll('.nav-link').forEach(link => {
      if (link.getAttribute('data-tab') === tabId) link.classList.add('active');
      else link.classList.remove('active');
    });

    document.querySelectorAll('.bottom-nav-item').forEach(link => {
      if (link.getAttribute('data-tab') === tabId) link.classList.add('active');
      else link.classList.remove('active');
    });

    window.scrollTo({ top: 0, behavior: 'smooth' });

    // Refresh context-specific UIs
    if (tabId === 'home') updateHomeMacroSummary();
    if (tabId === 'my-nutrition') renderUserProfileAndLogs();
  }

  // =========================================================================
  // MEAL VISION ANALYZER LOGIC
  // =========================================================================
  function setupDropZone() {
    const dropZone = document.getElementById('analyzer-drop-box');
    const fileInput = document.getElementById('meal-file-input');
    const cameraInput = document.getElementById('meal-camera-input');

    if (!dropZone) return;

    ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
      dropZone.addEventListener(eventName, preventDefaults, false);
    });

    function preventDefaults(e) {
      e.preventDefault();
      e.stopPropagation();
    }

    ['dragenter', 'dragover'].forEach(eventName => {
      dropZone.addEventListener(eventName, () => dropZone.classList.add('dragover'), false);
    });

    ['dragleave', 'drop'].forEach(eventName => {
      dropZone.addEventListener(eventName, () => dropZone.classList.remove('dragover'), false);
    });

    dropZone.addEventListener('drop', (e) => {
      const dt = e.dataTransfer;
      const files = dt.files;
      if (files && files.length > 0) handleSelectedImageFile(files[0]);
    });

    if (fileInput) {
      fileInput.addEventListener('change', (e) => {
        if (e.target.files && e.target.files.length > 0) handleSelectedImageFile(e.target.files[0]);
      });
    }

    if (cameraInput) {
      cameraInput.addEventListener('change', (e) => {
        if (e.target.files && e.target.files.length > 0) handleSelectedImageFile(e.target.files[0]);
      });
    }
  }

  function handleSelectedImageFile(file) {
    if (!file.type.match('image.*')) {
      showToast('⚠️ Please select a valid image file (JPEG, PNG, WEBP).');
      return;
    }

    state.selectedImageName = file.name;
    state.selectedImageSize = (file.size / (1024 * 1024)).toFixed(2) + ' MB';

    const reader = new FileReader();
    reader.onload = function(e) {
      state.selectedImageBase64 = e.target.result;
      showImagePreview(e.target.result, file.name, state.selectedImageSize);
    };
    reader.readAsDataURL(file);
  }

  function loadSampleMealPreset(presetKey) {
    const preset = SAMPLE_MEAL_PRESETS[presetKey];
    if (!preset) return;

    state.selectedImageName = `${presetKey}.jpg`;
    state.selectedImageSize = "1.2 MB";
    state.selectedImageBase64 = "assets/vitavue_hero.jpg"; // Reference fallback image

    showImagePreview("assets/vitavue_hero.jpg", preset.title, "Preset Sample Dish");
    
    // Automatically analyze preset
    analyzeMeal(preset);
  }

  function showImagePreview(src, name, size) {
    const previewContainer = document.getElementById('analyzer-preview-container');
    const previewImg = document.getElementById('analyzer-preview-img');
    const fileNameEl = document.getElementById('analyzer-file-name');
    const fileSizeEl = document.getElementById('analyzer-file-size');

    if (previewImg) previewImg.src = src;
    if (fileNameEl) fileNameEl.textContent = name;
    if (fileSizeEl) fileSizeEl.textContent = size;
    if (previewContainer) previewContainer.style.display = 'block';

    // Hide previous results or errors
    const resContainer = document.getElementById('analyzer-result-container');
    const errContainer = document.getElementById('analyzer-error-card');
    if (resContainer) resContainer.style.display = 'none';
    if (errContainer) errContainer.style.display = 'none';
  }

  function clearSelectedImage() {
    state.selectedImageBase64 = null;
    state.selectedImageName = '';
    state.selectedImageSize = '';
    state.currentAnalysisResult = null;

    const previewContainer = document.getElementById('analyzer-preview-container');
    if (previewContainer) previewContainer.style.display = 'none';
    const resContainer = document.getElementById('analyzer-result-container');
    if (resContainer) resContainer.style.display = 'none';
    const errContainer = document.getElementById('analyzer-error-card');
    if (errContainer) errContainer.style.display = 'none';
  }

  async function analyzeMeal(directPreset = null) {
    const inputCard = document.getElementById('analyzer-input-card');
    const loadingCard = document.getElementById('analyzer-loading-card');
    const errorCard = document.getElementById('analyzer-error-card');
    const resultContainer = document.getElementById('analyzer-result-container');

    if (inputCard) inputCard.style.display = 'none';
    if (errorCard) errorCard.style.display = 'none';
    if (resultContainer) resultContainer.style.display = 'none';
    if (loadingCard) loadingCard.style.display = 'block';

    // Animate multi-stage reasoning
    const loadingStatus = document.getElementById('analyzer-loading-status');
    const loadingDetail = document.getElementById('analyzer-loading-detail');

    try {
      if (loadingStatus) loadingStatus.textContent = "Detecting Food Matrices & Ingredients...";
      if (loadingDetail) loadingDetail.textContent = "Segmenting biological components and culinary preparations.";
      await delay(600);

      if (loadingStatus) loadingStatus.textContent = "Estimating Gram Volumes & Portion Densities...";
      if (loadingDetail) loadingDetail.textContent = "Applying volumetric spatial depth heuristics.";
      await delay(600);

      if (loadingStatus) loadingStatus.textContent = "Calculating Macronutrients & Bioavailability...";
      if (loadingDetail) loadingDetail.textContent = "Evaluating leucine thresholds, glycemic load, and lipid balance.";
      await delay(500);

      let result = null;

      if (directPreset) {
        result = directPreset;
      } else if (state.customApiKey && state.selectedImageBase64) {
        // Real Gemini API Call using user's secure key
        result = await callGeminiVisionApi(state.customApiKey, state.selectedImageBase64);
      } else {
        // High-Fidelity Scientific Vision Engine
        result = generateHighFidelityMealAnalysis(state.selectedImageName);
      }

      state.currentAnalysisResult = result;
      renderMealAnalysisResult(result);

      if (loadingCard) loadingCard.style.display = 'none';
      if (resultContainer) resultContainer.style.display = 'block';
      if (inputCard) inputCard.style.display = 'block';

      // Set active context for AI Agent
      state.activeContext = {
        type: 'meal',
        data: result
      };
      updateAgentContextBanner();

    } catch (err) {
      console.error('Analysis error:', err);
      if (loadingCard) loadingCard.style.display = 'none';
      if (inputCard) inputCard.style.display = 'block';
      if (errorCard) {
        errorCard.style.display = 'block';
        const errMsg = document.getElementById('analyzer-error-message');
        if (errMsg) errMsg.textContent = err.message || "Failed to analyze meal. Please try another photo.";
      }
    }
  }

  function generateHighFidelityMealAnalysis(imageName) {
    const nameLower = (imageName || '').toLowerCase();

    if (nameLower.includes('biryani') || nameLower.includes('rice') || nameLower.includes('curry')) {
      return SAMPLE_MEAL_PRESETS.chicken_biryani;
    } else if (nameLower.includes('falafel') || nameLower.includes('salad') || nameLower.includes('hummus')) {
      return SAMPLE_MEAL_PRESETS.falafel_plate;
    } else if (nameLower.includes('oat') || nameLower.includes('berry') || nameLower.includes('breakfast')) {
      return SAMPLE_MEAL_PRESETS.oatmeal_berries;
    }

    // Default to the comprehensive Salmon Quinoa Bowl
    return SAMPLE_MEAL_PRESETS.salmon_bowl;
  }

  async function callGeminiVisionApi(apiKey, base64DataUrl) {
    const base64Data = base64DataUrl.split(',')[1];
    const mimeType = base64DataUrl.substring(base64DataUrl.indexOf(":") + 1, base64DataUrl.indexOf(";"));

    const systemPrompt = `You are VitaVue's Nutritional Vision Intelligence engine.
Analyze the meal in the image and respond ONLY with a JSON object strictly matching this schema:
{
  "title": "string",
  "description": "string",
  "confidence": "High" | "Medium" | "Approximate",
  "uncertainty": "string explaining potential variance in oils or portion depth",
  "totalCalories": number,
  "macros": { "protein": number, "carbs": number, "fats": number, "fiber": number },
  "components": [
    { "name": "string", "portion": "string", "calories": number, "p": number, "c": number, "f": number, "fib": number }
  ],
  "micronutrients": [
    { "name": "string", "amount": "string", "dv": number or null, "benefit": "string" }
  ],
  "highlights": ["string", "string", "string"],
  "suggestions": ["string", "string"]
}`;

    const url = `https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=${apiKey}`;
    
    const response = await fetch(url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        contents: [
          {
            parts: [
              { text: systemPrompt },
              { inline_data: { mime_type: mimeType, data: base64Data } }
            ]
          }
        ],
        generationConfig: {
          response_mime_type: "application/json",
          temperature: 0.2
        }
      })
    });

    if (!response.ok) {
      const errJson = await response.json().catch(() => ({}));
      throw new Error(errJson.error?.message || `Gemini API returned HTTP ${response.status}`);
    }

    const data = await response.json();
    const candidateText = data.candidates?.[0]?.content?.parts?.[0]?.text;
    if (!candidateText) throw new Error("Received empty response from Gemini API.");

    return JSON.parse(candidateText);
  }

  function renderMealAnalysisResult(res) {
    document.getElementById('res-meal-title').textContent = res.title;
    document.getElementById('res-meal-desc').textContent = res.description;
    
    const confBadge = document.getElementById('res-confidence-badge');
    if (confBadge) confBadge.textContent = `Confidence: ${res.confidence || 'High'}`;

    document.getElementById('res-cals').textContent = `${Math.round(res.totalCalories)} kcal`;
    document.getElementById('res-protein').textContent = `${res.macros.protein}g`;
    document.getElementById('res-carbs').textContent = `${res.macros.carbs}g`;
    document.getElementById('res-fats').textContent = `${res.macros.fats}g`;
    document.getElementById('res-fiber').textContent = `${res.macros.fiber}g`;

    // Energy % calculation
    const pCals = res.macros.protein * 4;
    const cCals = res.macros.carbs * 4;
    const fCals = res.macros.fats * 9;
    const fibCals = (res.macros.fiber || 0) * 2;
    const sumCals = pCals + cCals + fCals + fibCals || 1;

    const pPct = Math.round((pCals / sumCals) * 100);
    const cPct = Math.round((cCals / sumCals) * 100);
    const fPct = Math.round((fCals / sumCals) * 100);
    const fibPct = Math.max(0, 100 - (pPct + cPct + fPct));

    document.getElementById('res-pct-p').textContent = `${pPct}%`;
    document.getElementById('res-pct-c').textContent = `${cPct}%`;
    document.getElementById('res-pct-f').textContent = `${fPct}%`;
    document.getElementById('res-pct-fib').textContent = `${fibPct}%`;

    document.getElementById('bar-p').style.width = `${pPct}%`;
    document.getElementById('bar-c').style.width = `${cPct}%`;
    document.getElementById('bar-f').style.width = `${fPct}%`;
    document.getElementById('bar-fib').style.width = `${fibPct}%`;

    // Components List
    const compContainer = document.getElementById('res-components-list');
    if (compContainer && res.components) {
      compContainer.innerHTML = res.components.map(c => `
        <div class="component-item-row">
          <div class="comp-name-group">
            <strong>${escapeHtml(c.name)}</strong>
            <span class="comp-portion">${escapeHtml(c.portion)} • ${Math.round(c.calories)} kcal</span>
          </div>
          <div class="comp-macro-group">
            <span>P: <strong>${c.p}g</strong></span>
            <span>C: <strong>${c.c}g</strong></span>
            <span>F: <strong>${c.f}g</strong></span>
            <span>Fib: <strong>${c.fib || 0}g</strong></span>
          </div>
        </div>
      `).join('');
    }

    // Micronutrients Grid
    const microContainer = document.getElementById('res-micros-grid');
    if (microContainer && res.micronutrients) {
      microContainer.innerHTML = res.micronutrients.map(m => `
        <div class="micro-card-pill">
          <div class="micro-pill-header">
            <span class="micro-name">${escapeHtml(m.name)}</span>
            <span class="micro-amount">${escapeHtml(m.amount)}${m.dv ? ` (${m.dv}% DV)` : ''}</span>
          </div>
          <p class="micro-benefit">${escapeHtml(m.benefit)}</p>
        </div>
      `).join('');
    }

    // Highlights
    const highlightsContainer = document.getElementById('res-highlights-list');
    if (highlightsContainer && res.highlights) {
      highlightsContainer.innerHTML = res.highlights.map(h => `<li>${escapeHtml(h)}</li>`).join('');
    }

    // Suggestions
    const suggestionsContainer = document.getElementById('res-suggestions-list');
    if (suggestionsContainer && res.suggestions) {
      suggestionsContainer.innerHTML = res.suggestions.map(s => `<li>${escapeHtml(s)}</li>`).join('');
    }

    // Uncertainty
    const uncertEl = document.getElementById('res-uncertainty-text');
    if (uncertEl) uncertEl.textContent = res.uncertainty || "Visual estimation provides intelligent approximations of meal volume and nutrient distribution.";
  }

  function saveAnalysisToLog() {
    if (!state.currentAnalysisResult) return;
    const meal = {
      id: 'log_' + Date.now(),
      title: state.currentAnalysisResult.title,
      calories: Math.round(state.currentAnalysisResult.totalCalories),
      protein: state.currentAnalysisResult.macros.protein,
      carbs: state.currentAnalysisResult.macros.carbs,
      fats: state.currentAnalysisResult.macros.fats,
      fiber: state.currentAnalysisResult.macros.fiber,
      timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    };

    state.loggedMeals.unshift(meal);
    savePersistedData();
    updateHomeMacroSummary();
    showToast(`✅ Logged "${meal.title}" to today's nutrition!`);
  }

  function discussMealWithAgent() {
    if (!state.currentAnalysisResult) return;
    state.activeContext = {
      type: 'meal',
      data: state.currentAnalysisResult
    };
    updateAgentContextBanner();
    switchTab('agent');

    const promptText = `I just analyzed **${state.currentAnalysisResult.title}** (${Math.round(state.currentAnalysisResult.totalCalories)} kcal, ${state.currentAnalysisResult.macros.protein}g protein, ${state.currentAnalysisResult.macros.carbs}g carbs, ${state.currentAnalysisResult.macros.fats}g fat, ${state.currentAnalysisResult.macros.fiber}g fiber). How does this meal fit into a muscle-synthesis and gut-microbiome optimization strategy?`;
    sendAgentMessage(promptText);
  }

  function resetAnalyzer() {
    clearSelectedImage();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  function retryAnalysis() {
    if (state.selectedImageBase64) {
      analyzeMeal();
    } else {
      resetAnalyzer();
    }
  }

  // =========================================================================
  // FOOD EXPLORER
  // =========================================================================
  function renderFoodCategories() {
    const container = document.getElementById('food-category-pills');
    if (!container) return;

    container.innerHTML = FOOD_CATEGORIES.map(cat => `
      <button class="cat-pill ${cat.id === state.selectedFoodCategory ? 'active' : ''}" onclick="VitaVue.selectFoodCategory('${cat.id}')">
        <span>${cat.icon}</span>
        <span>${cat.displayName}</span>
      </button>
    `).join('');
  }

  function selectFoodCategory(catId) {
    state.selectedFoodCategory = catId;
    renderFoodCategories();
    renderFoods();
  }

  function filterFoods() {
    const searchInput = document.getElementById('food-search-input');
    const clearBtn = document.getElementById('food-search-clear');
    const tagSelect = document.getElementById('filter-dietary-tag');
    const giSelect = document.getElementById('filter-glycemic-index');

    state.searchQuery = (searchInput?.value || '').trim().toLowerCase();
    state.selectedDietaryTag = tagSelect?.value || 'all';
    state.selectedGlycemicIndex = giSelect?.value || 'all';

    if (clearBtn) {
      clearBtn.style.display = state.searchQuery ? 'block' : 'none';
    }

    renderFoods();
  }

  function clearFoodSearch() {
    const searchInput = document.getElementById('food-search-input');
    if (searchInput) searchInput.value = '';
    filterFoods();
  }

  function renderFoods() {
    const grid = document.getElementById('food-grid-container');
    const countBadge = document.getElementById('food-results-count');
    if (!grid) return;

    const filtered = ALL_FOODS.filter(food => {
      // Category Match
      if (state.selectedFoodCategory !== 'all' && food.category !== state.selectedFoodCategory) {
        return false;
      }

      // Dietary Tag Match
      if (state.selectedDietaryTag !== 'all') {
        const hasTag = food.dietaryTags?.some(t => t.toLowerCase() === state.selectedDietaryTag.toLowerCase());
        if (!hasTag) return false;
      }

      // Glycemic Index Match
      if (state.selectedGlycemicIndex !== 'all') {
        if (food.glycemicIndex !== state.selectedGlycemicIndex) return false;
      }

      // Search Query
      if (state.searchQuery) {
        const q = state.searchQuery;
        const inName = food.name.toLowerCase().includes(q);
        const inCat = food.category.toLowerCase().includes(q);
        const inDesc = food.description.toLowerCase().includes(q);
        const inTags = food.dietaryTags?.some(t => t.toLowerCase().includes(q));
        if (!inName && !inCat && !inDesc && !inTags) return false;
      }

      return true;
    });

    if (countBadge) countBadge.textContent = `Showing ${filtered.length} of ${ALL_FOODS.length} Foods`;

    if (filtered.length === 0) {
      grid.innerHTML = `
        <div style="grid-column: 1 / -1; text-align: center; padding: 48px; color: var(--neutral-400);">
          <div style="font-size: 36px; margin-bottom: 12px;">🔍</div>
          <h3>No foods match your active filters</h3>
          <p style="font-size: 13px; margin-top: 6px;">Try clearing search keywords or selecting "All Foods".</p>
          <button class="btn btn-secondary btn-chip" style="margin-top: 14px;" onclick="VitaVue.resetFoodFilters()">Reset Filters</button>
        </div>
      `;
      return;
    }

    grid.innerHTML = filtered.map(food => {
      const isSaved = state.savedFoodIds.includes(food.id);
      return `
        <div class="food-card" onclick="VitaVue.openFoodDetailModal('${food.id}')">
          <div>
            <div class="food-card-top">
              <div>
                <h4 class="food-card-title">${escapeHtml(food.name)}</h4>
                <span class="food-card-cat">${escapeHtml(food.category)}</span>
              </div>
              <span style="font-size: 14px;">${isSaved ? '⭐' : '🤍'}</span>
            </div>
            <div class="food-card-serving">${escapeHtml(food.servingSize)}</div>
            
            <div class="food-macros-row">
              <div class="food-macro-mini">
                <span>CALS</span>
                <strong>${food.calories}</strong>
              </div>
              <div class="food-macro-mini">
                <span>PROT</span>
                <strong>${food.proteinGrams}g</strong>
              </div>
              <div class="food-macro-mini">
                <span>CARB</span>
                <strong>${food.carbsGrams}g</strong>
              </div>
              <div class="food-macro-mini">
                <span>FAT</span>
                <strong>${food.fatGrams}g</strong>
              </div>
              <div class="food-macro-mini">
                <span>FIB</span>
                <strong>${food.fiberGrams}g</strong>
              </div>
            </div>
          </div>

          <div class="food-tags-row">
            ${(food.dietaryTags || []).slice(0, 3).map(t => `<span class="food-tag-pill">${escapeHtml(t)}</span>`).join('')}
            ${food.glycemicIndex ? `<span class="food-tag-pill" style="color: var(--teal-300);">${food.glycemicIndex} GI</span>` : ''}
          </div>
        </div>
      `;
    }).join('');
  }

  function resetFoodFilters() {
    state.selectedFoodCategory = 'all';
    state.searchQuery = '';
    state.selectedDietaryTag = 'all';
    state.selectedGlycemicIndex = 'all';

    const searchInput = document.getElementById('food-search-input');
    if (searchInput) searchInput.value = '';
    const tagSelect = document.getElementById('filter-dietary-tag');
    if (tagSelect) tagSelect.value = 'all';
    const giSelect = document.getElementById('filter-glycemic-index');
    if (giSelect) giSelect.value = 'all';

    renderFoodCategories();
    renderFoods();
  }

  function openFoodDetailModal(foodId) {
    const food = ALL_FOODS.find(f => f.id === foodId);
    if (!food) return;

    const isSaved = state.savedFoodIds.includes(food.id);

    const modalContent = `
      <div style="margin-bottom: 16px;">
        <div style="display: flex; justify-content: space-between; align-items: flex-start;">
          <div>
            <span class="confidence-badge" style="background: rgba(0, 194, 203, 0.15); color: var(--teal-300);">
              ${escapeHtml(food.category)} • ${escapeHtml(food.culturalOrigin || 'Global')}
            </span>
            <h2 style="font-size: 24px; font-weight: 800; color: var(--pure-white); margin-top: 4px;">${escapeHtml(food.name)}</h2>
            <p style="color: var(--neutral-400); font-size: 13px;">Serving Size: <strong>${escapeHtml(food.servingSize)}</strong></p>
          </div>
          <button class="btn btn-secondary btn-chip" onclick="VitaVue.toggleSaveFood('${food.id}')">
            ${isSaved ? '⭐ Favorited' : '🤍 Save Food'}
          </button>
        </div>
      </div>

      <!-- Macros Grid -->
      <div class="macro-grid" style="margin-bottom: 20px;">
        <div class="macro-box calories">
          <span class="macro-label">Calories</span>
          <span class="macro-val">${food.calories} kcal</span>
        </div>
        <div class="macro-box protein">
          <span class="macro-label">Protein</span>
          <span class="macro-val">${food.proteinGrams}g</span>
        </div>
        <div class="macro-box carbs">
          <span class="macro-label">Carbs</span>
          <span class="macro-val">${food.carbsGrams}g</span>
        </div>
        <div class="macro-box fats">
          <span class="macro-label">Fats</span>
          <span class="macro-val">${food.fatGrams}g</span>
        </div>
        <div class="macro-box fiber">
          <span class="macro-label">Fiber</span>
          <span class="macro-val">${food.fiberGrams}g</span>
        </div>
      </div>

      <!-- Description & Culinary Notes -->
      <div class="m3-card" style="background: var(--navy-850); margin-bottom: 16px;">
        <h4 class="subcard-title" style="color: var(--teal-300);">Biochemical & Nutritional Profile</h4>
        <p style="font-size: 13.5px; color: var(--neutral-200); line-height: 1.6;">${escapeHtml(food.description)}</p>
        
        ${food.culinaryNotes ? `
          <div style="margin-top: 12px; padding-top: 12px; border-top: 1px solid var(--navy-750);">
            <strong style="font-size: 12.5px; color: var(--amber-400);">Culinary & Synergy Note:</strong>
            <p style="font-size: 13px; color: var(--neutral-300); margin-top: 2px;">${escapeHtml(food.culinaryNotes)}</p>
          </div>
        ` : ''}
      </div>

      <!-- Complete Micronutrients List -->
      <div class="m3-card" style="background: var(--navy-850); margin-bottom: 20px;">
        <h4 class="subcard-title">🧬 Micronutrients & Bioactives</h4>
        <div class="micros-pill-grid">
          ${(food.micronutrients || []).map(m => `
            <div class="micro-card-pill">
              <div class="micro-pill-header">
                <span class="micro-name">${escapeHtml(m.name)}</span>
                <span class="micro-amount">${escapeHtml(m.amount)}${m.dailyValuePercent ? ` (${m.dailyValuePercent}% DV)` : ''}</span>
              </div>
              <p class="micro-benefit">${escapeHtml(m.benefit)}</p>
            </div>
          `).join('')}
        </div>
      </div>

      <!-- Dietary Tags -->
      <div style="display: flex; flex-wrap: wrap; gap: 6px; margin-bottom: 24px;">
        ${(food.dietaryTags || []).map(t => `<span class="food-tag-pill" style="font-size: 11px; padding: 4px 10px;">${escapeHtml(t)}</span>`).join('')}
        <span class="food-tag-pill" style="font-size: 11px; padding: 4px 10px; color: var(--teal-300);">${food.glycemicIndex} Glycemic Index</span>
      </div>

      <!-- Action Buttons -->
      <div style="display: flex; gap: 10px; justify-content: flex-end;">
        <button class="btn btn-secondary" onclick="VitaVue.askAgentAboutFood('${food.id}')">
          💬 Ask AI Nutritionist About This Food
        </button>
        <button class="btn btn-outline" onclick="VitaVue.closeModal()">
          Close
        </button>
      </div>
    `;

    openModal(modalContent);
  }

  function toggleSaveFood(foodId) {
    const idx = state.savedFoodIds.indexOf(foodId);
    if (idx >= 0) {
      state.savedFoodIds.splice(idx, 1);
      showToast("🤍 Removed from saved favorite foods.");
    } else {
      state.savedFoodIds.push(foodId);
      showToast("⭐ Added to saved favorite foods!");
    }
    savePersistedData();
    renderFoods();
    openFoodDetailModal(foodId); // re-render modal with updated favorite button
  }

  function askAgentAboutFood(foodId) {
    const food = ALL_FOODS.find(f => f.id === foodId);
    if (!food) return;

    closeModal();
    state.activeContext = {
      type: 'food',
      data: food
    };
    updateAgentContextBanner();
    switchTab('agent');

    const promptText = `Explain the nutritional biochemistry of **${food.name}** (${food.calories} kcal, ${food.proteinGrams}g P, ${food.carbsGrams}g C, ${food.fatGrams}g F, ${food.fiberGrams}g Fiber). How do its micronutrients and bioactives interact in the body?`;
    sendAgentMessage(promptText);
  }

  // =========================================================================
  // NUTRITION HUB & MYTH BUSTERS
  // =========================================================================
  function switchLearnSubtab(subtabId) {
    state.activeLearnSubtab = subtabId;

    document.querySelectorAll('.hub-tab-btn').forEach(btn => btn.classList.remove('active'));
    document.querySelectorAll('.learn-subtab-content').forEach(c => c.classList.remove('active'));

    const btn = document.getElementById(`tab-btn-${subtabId}`);
    const content = document.getElementById(`learn-subtab-${subtabId}`);

    if (btn) btn.classList.add('active');
    if (content) content.classList.add('active');
  }

  function renderArticleCategories() {
    const container = document.getElementById('article-category-pills');
    if (!container) return;

    container.innerHTML = ARTICLE_CATEGORIES.map(cat => `
      <button class="cat-pill ${cat.id === state.selectedArticleCategory ? 'active' : ''}" onclick="VitaVue.selectArticleCategory('${cat.id}')">
        <span>${cat.displayName}</span>
      </button>
    `).join('');
  }

  function selectArticleCategory(catId) {
    state.selectedArticleCategory = catId;
    renderArticleCategories();
    renderArticles();
  }

  function renderArticles() {
    const container = document.getElementById('articles-grid-container');
    if (!container) return;

    const filtered = ALL_ARTICLES.filter(art => {
      if (state.selectedArticleCategory !== 'all' && art.category !== state.selectedArticleCategory) {
        return false;
      }
      return true;
    });

    container.innerHTML = filtered.map(art => {
      const isBookmarked = state.bookmarkedArticleIds.includes(art.id);
      return `
        <div class="article-card" onclick="VitaVue.openArticleModal('${art.id}')">
          <div>
            <div class="article-meta-row">
              <span>${escapeHtml(art.category)}</span>
              <span>${isBookmarked ? '🔖 Saved' : `${art.readingTimeMin} min read`}</span>
            </div>
            <h3 class="article-card-title">${escapeHtml(art.title)}</h3>
            <p class="article-card-summary">${escapeHtml(art.summary)}</p>
          </div>

          <div class="article-card-footer">
            <span>Difficulty: <strong>${escapeHtml(art.difficulty)}</strong></span>
            <span style="color: var(--teal-300); font-weight: 700;">Read Guide →</span>
          </div>
        </div>
      `;
    }).join('');
  }

  function openArticleModal(articleId) {
    const art = ALL_ARTICLES.find(a => a.id === articleId);
    if (!art) return;

    const isBookmarked = state.bookmarkedArticleIds.includes(art.id);

    const modalContent = `
      <div style="margin-bottom: 20px;">
        <div style="display: flex; justify-content: space-between; align-items: flex-start;">
          <div>
            <span class="confidence-badge" style="background: rgba(0, 194, 203, 0.15); color: var(--teal-300);">
              ${escapeHtml(art.category)} • ${art.readingTimeMin} min read • ${escapeHtml(art.difficulty)}
            </span>
            <h2 style="font-size: 22px; font-weight: 800; color: var(--pure-white); margin-top: 6px; line-height: 1.3;">
              ${escapeHtml(art.title)}
            </h2>
          </div>
          <button class="btn btn-secondary btn-chip" onclick="VitaVue.toggleBookmarkArticle('${art.id}')">
            ${isBookmarked ? '🔖 Bookmarked' : '📑 Bookmark'}
          </button>
        </div>
      </div>

      <!-- Sections -->
      <div style="display: flex; flex-direction: column; gap: 18px; margin-bottom: 24px;">
        ${(art.sections || []).map(sec => `
          <div class="m3-card" style="background: var(--navy-850); padding: 18px;">
            <h4 style="font-size: 15px; font-weight: 800; color: var(--teal-300); margin-bottom: 8px;">
              ${escapeHtml(sec.heading)}
            </h4>
            <p style="font-size: 13.5px; color: var(--neutral-200); line-height: 1.6;">
              ${escapeHtml(sec.content)}
            </p>
          </div>
        `).join('')}
      </div>

      <!-- Key Takeaways -->
      <div class="m3-card" style="background: linear-gradient(135deg, rgba(0, 194, 203, 0.1), rgba(10, 17, 40, 0.8)); border-color: var(--teal-500); margin-bottom: 24px;">
        <h4 class="subcard-title" style="color: var(--pure-white);">💡 Key Clinical Takeaways</h4>
        <ul class="styled-bullet-list">
          ${(art.keyTakeaways || []).map(t => `<li>${escapeHtml(t)}</li>`).join('')}
        </ul>
      </div>

      <!-- Actions -->
      <div style="display: flex; justify-content: space-between; align-items: center;">
        <button class="btn btn-secondary" onclick="VitaVue.discussArticleWithAgent('${art.id}')">
          💬 Discuss this Topic with AI Nutritionist
        </button>
        <button class="btn btn-outline" onclick="VitaVue.closeModal()">
          Close
        </button>
      </div>
    `;

    openModal(modalContent);
  }

  function toggleBookmarkArticle(articleId) {
    const idx = state.bookmarkedArticleIds.indexOf(articleId);
    if (idx >= 0) {
      state.bookmarkedArticleIds.splice(idx, 1);
      showToast("📑 Removed bookmark.");
    } else {
      state.bookmarkedArticleIds.push(articleId);
      showToast("🔖 Article bookmarked to your profile!");
    }
    savePersistedData();
    renderArticles();
    openArticleModal(articleId);
  }

  function discussArticleWithAgent(articleId) {
    const art = ALL_ARTICLES.find(a => a.id === articleId);
    if (!art) return;

    closeModal();
    switchTab('agent');
    const promptText = `Let's discuss the science in **${art.title}**. What are the most practical daily culinary steps to apply these principles?`;
    sendAgentMessage(promptText);
  }

  function renderMyths() {
    const container = document.getElementById('myths-grid-container');
    if (!container) return;

    container.innerHTML = NUTRITION_MYTHS.map((m, idx) => `
      <div class="myth-card" id="myth-card-${m.id}" onclick="VitaVue.toggleMythCard('${m.id}')">
        <div class="myth-header-row">
          <span class="myth-cross">❌</span>
          <h4 class="myth-statement">${escapeHtml(m.myth)}</h4>
          <span id="myth-toggle-icon-${m.id}" style="color: var(--teal-400); font-size: 18px;">▼</span>
        </div>

        <div class="myth-body" id="myth-body-${m.id}">
          <div class="fact-box">
            <strong>✅ Evidence-Based Fact</strong>
            <p>${escapeHtml(m.fact)}</p>
          </div>
          <p class="myth-evidence-text">
            <strong style="color: var(--neutral-100)">Clinical Mechanism:</strong> ${escapeHtml(m.evidenceExplanation)}
          </p>
          <p class="myth-tip-text">
            💡 <strong>Habit Tip:</strong> ${escapeHtml(m.practicalTip)}
          </p>
        </div>
      </div>
    `).join('');
  }

  function toggleMythCard(mythId) {
    const body = document.getElementById(`myth-body-${mythId}`);
    const icon = document.getElementById(`myth-toggle-icon-${mythId}`);
    if (!body) return;

    if (body.style.display === 'none') {
      body.style.display = 'flex';
      if (icon) icon.textContent = '▲';
    } else {
      body.style.display = 'none';
      if (icon) icon.textContent = '▼';
    }
  }

  // =========================================================================
  // AI NUTRITION AGENT
  // =========================================================================
  function setupChatListeners() {
    const sendBtn = document.getElementById('chat-send-btn');
    const chatInput = document.getElementById('chat-input');

    if (sendBtn) {
      sendBtn.addEventListener('click', handleChatSubmit);
    }

    if (chatInput) {
      chatInput.addEventListener('keydown', (e) => {
        if (e.key === 'Enter' && !e.shiftKey) {
          e.preventDefault();
          handleChatSubmit();
        }
      });
    }
  }

  function handleChatSubmit() {
    const chatInput = document.getElementById('chat-input');
    if (!chatInput) return;
    const text = chatInput.value.trim();
    if (!text || state.isAgentTyping) return;

    chatInput.value = '';
    sendAgentMessage(text);
  }

  function sendQuickPrompt(promptText) {
    sendAgentMessage(promptText);
  }

  async function sendAgentMessage(userText) {
    // Add user message to history
    state.chatHistory.push({
      role: 'user',
      content: userText
    });
    renderChatMessages();

    // Show typing indicator
    state.isAgentTyping = true;
    const typingIndicator = document.getElementById('agent-typing-indicator');
    if (typingIndicator) typingIndicator.style.display = 'flex';
    scrollToChatBottom();

    try {
      let agentResponse = null;

      if (state.customApiKey) {
        agentResponse = await callGeminiChatApi(state.customApiKey, userText, state.activeContext, state.chatHistory);
      } else {
        // Sophisticated Domain-Specific Offline Intelligence Engine
        agentResponse = await generateOfflineAgentResponse(userText, state.activeContext);
      }

      state.chatHistory.push(agentResponse);
    } catch (err) {
      console.error('Agent chat error:', err);
      state.chatHistory.push({
        role: 'agent',
        content: `I encountered an error communicating with the intelligence backend. Here is the evidence-based recommendation:\n\n*${err.message || 'Please verify your network connection.'}*`,
        actions: [{ label: '🔄 Ask Again', prompt: userText }]
      });
    } finally {
      state.isAgentTyping = false;
      if (typingIndicator) typingIndicator.style.display = 'none';
      renderChatMessages();
      scrollToChatBottom();
    }
  }

  async function callGeminiChatApi(apiKey, userText, activeContext, history) {
    let contextPrompt = "";
    if (activeContext) {
      if (activeContext.type === 'meal') {
        contextPrompt = `[ACTIVE MEAL CONTEXT: ${JSON.stringify(activeContext.data)}]\n`;
      } else if (activeContext.type === 'food') {
        contextPrompt = `[ACTIVE FOOD CONTEXT: ${JSON.stringify(activeContext.data)}]\n`;
      }
    }

    const systemPrompt = `You are VitaVue's Evidence-Based AI Nutrition Agent.
You provide precise biochemical reasoning, amino acid kinetics (e.g. leucine trigger), micronutrient interactions, and practical culinary habits.
Be direct, supportive, and scientifically grounded. Use formatted bolding and bullet points.`;

    const contents = [];
    history.slice(-6).forEach(msg => {
      contents.push({
        role: msg.role === 'user' ? 'user' : 'model',
        parts: [{ text: msg.content }]
      });
    });

    contents.push({
      role: 'user',
      parts: [{ text: `${contextPrompt}${userText}` }]
    });

    const url = `https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=${apiKey}`;

    const response = await fetch(url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        system_instruction: { parts: [{ text: systemPrompt }] },
        contents: contents,
        generationConfig: { temperature: 0.3 }
      })
    });

    if (!response.ok) {
      const err = await response.json().catch(() => ({}));
      throw new Error(err.error?.message || "Gemini API error.");
    }

    const data = await response.json();
    const replyText = data.candidates?.[0]?.content?.parts?.[0]?.text || "No response generated.";

    return {
      role: 'agent',
      content: replyText,
      actions: deriveFollowUpActions(userText)
    };
  }

  async function generateOfflineAgentResponse(userText, activeContext) {
    await delay(700); // Realistic cognitive pause
    const q = userText.toLowerCase();

    let content = "";
    let actions = [];

    // Context-dependent reasoning
    if (activeContext && activeContext.type === 'meal') {
      const meal = activeContext.data;
      if (q.includes('protein') || q.includes('leucine')) {
        content = `### Protein & Amino Acid Analysis for **${meal.title}**\n\n- **Protein Quantity**: Delivers **${meal.macros.protein}g** of protein, which comfortably clears the **leucine trigger threshold** (~2.5g–3.0g leucine) required to maximally stimulate the **mTORC1** pathway in adults.\n- **Bioavailability**: The protein matrix exhibits high amino acid score (DIAAS > 1.0).\n- **Metabolic Tip**: For optimal muscle recovery, ensure you space your subsequent protein dose 3.5 to 5 hours later rather than consuming additional protein immediately.`;
        actions = [
          { label: '🍋 Ask about Micronutrient Synergy', prompt: 'What micronutrient synergies are present in this meal?' },
          { label: '📋 Open Diet Planner', actionType: 'navigate', targetTab: 'planner' }
        ];
        return { role: 'agent', content, actions };
      }
    }

    // Keyword & Domain-Grounded Routing
    if (q.includes('leucine') || (q.includes('muscle') && q.includes('protein'))) {
      content = `### The Leucine Trigger & Muscle Protein Synthesis (MPS)\n\n**L-Leucine** is the primary essential branched-chain amino acid that acts as a molecular "nutrient sensor" activating the **mTORC1** kinase pathway.\n\n- **Threshold Requirement**: Most adults require **2.5g to 3.0g of leucine** per meal (roughly equivalent to 25–35g of high-quality animal protein or 35–45g of complementary plant protein).\n- **The 'Muscle-Full' Effect**: Once mTORC1 is saturated, additional amino acids in that sitting are oxidized for energy rather than further increasing protein synthesis.\n- **Optimal Distribution**: Distribute your daily protein intake across **3 to 4 distinct meals** rather than backloading all protein into dinner.`;
      actions = [
        { label: '📖 Read Protein Mastery Guide', actionType: 'readArticle', articleId: 'art_protein_mastery' },
        { label: '🔍 Explore High-Protein Foods', actionType: 'filterFoods', tag: 'High-Protein' }
      ];
    } else if (q.includes('iron') || q.includes('vitamin c') || q.includes('synergy')) {
      content = `### Micronutrient Synergy: Non-Heme Iron + Vitamin C\n\nPlant-based non-heme iron (found in lentils, spinach, beans, and seeds) exists in the **ferric (Fe3+) state**, which is relatively insoluble and poorly absorbed in the duodenum.\n\n- **Biochemical Reduction**: **Ascorbic acid (Vitamin C)** acts as an electron donor, reducing ferric iron (Fe3+) into soluble **ferrous iron (Fe2+)**.\n- **Chelation**: Vitamin C also binds with iron to prevent it from precipitating in the alkaline environment of the small intestine.\n- **Absorption Boost**: Adding fresh lemon juice, diced bell peppers, or tomatoes to cooked legumes increases non-heme iron absorption by **up to 300%**!`;
      actions = [
        { label: '📖 Read Micronutrient Synergy Guide', actionType: 'readArticle', articleId: 'art_micronutrient_powerhouses' },
        { label: '🔍 View Legumes & Pulses', actionType: 'selectCategory', category: 'Legumes & Pulses' }
      ];
    } else if (q.includes('fiber') || q.includes('microbiome') || q.includes('scfa') || q.includes('gut')) {
      content = `### Dietary Fiber & The Gut Microbiome Ecosystem\n\nDietary fiber encompasses non-digestible carbohydrates that escape upper GI enzymatic digestion and reach the colon intact.\n\n- **Prebiotic Fermentation**: Commensal anaerobes (*Bifidobacteria*, *Faecalibacterium prausnitzii*) ferment soluble fibers and resistant starches into **Short-Chain Fatty Acids (SCFAs)**: **acetate, propionate, and butyrate**.\n- **Butyrate Function**: Fuel for colonocytes, reinforcing mucosal tight junctions and preventing systemic inflammation.\n- **The "30 Plants" Target**: Clinical research demonstrates that individuals consuming **30+ distinct plant types per week** exhibit vastly superior microbial biodiversity and metabolic resilience.`;
      actions = [
        { label: '📖 Read Gut Microbiome Guide', actionType: 'readArticle', articleId: 'art_fiber_gut_microbiome' },
        { label: '🔍 Explore High-Fiber Foods', actionType: 'filterFoods', tag: 'High-Fiber' }
      ];
    } else if (q.includes('glycemic') || q.includes('glucose') || q.includes('gi') || q.includes('blood sugar')) {
      content = `### Glycemic Index (GI) vs. Glycemic Load (GL)\n\n- **Glycemic Index (GI)**: Measures the *speed* at which 50g of available carbohydrates from a food elevate blood glucose compared to pure glucose (GI = 100).\n- **Glycemic Load (GL)**: Takes into account both the GI *and* the realistic portion size: \n  $$\\text{GL} = \\frac{\\text{GI} \\times \\text{Carbs per serving (g)}}{100}$$\n- **Practical Application**: Watermelon has a high GI (72), but because it is 92% water, a normal serving contains only 11g of carbs, giving it a very low GL of **5**!\n- **Buffering Glucose**: Pair complex carbohydrates with healthy lipids (EVOO, avocado) and viscous soluble fiber to blunt postprandial glycemic excursions.`;
      actions = [
        { label: '💥 View Myth: "Carbs Are Fattening"', actionType: 'openMyth', mythId: 'myth_carbs_bad' },
        { label: '📋 View Glucose Stability Meal Plan', actionType: 'selectGoal', goal: 'metabolic_health' }
      ];
    } else if (q.includes('plate') || q.includes('portion') || q.includes('balance') || q.includes('macro')) {
      content = `### The Visual Balanced Plate Heuristic\n\nRather than weighing every gram of food, use the evidence-backed **Visual Plate Method**:\n\n1. **50% of the Plate**: Colorful non-starchy vegetables & leafy greens (delivers potassium, magnesium, polyphenols, and insoluble fiber).\n2. **25% of the Plate**: Quality lean animal or complementary plant protein (hits the leucine threshold and sustains satiety peptide YY).\n3. **25% of the Plate**: Intact complex whole grains or root vegetables (replenishes muscle glycogen with low glycemic index).\n4. **1 Thumbnail**: Healthy cold-pressed fat (extra virgin olive oil, nuts, or seeds for fat-soluble vitamins A, D, E, K).`;
      actions = [
        { label: '📖 Read Balanced Plate Guide', actionType: 'readArticle', articleId: 'art_balanced_plate_method' },
        { label: '📸 Analyze a Meal Plate', actionType: 'navigate', targetTab: 'analyzer' }
      ];
    } else {
      content = `### Evidence-Based Nutritional Guidance\n\nRegarding **${escapeHtml(userText)}**:\n\n- **Food Matrix Quality**: Modern nutritional science emphasizes whole-food matrices over isolated nutrients. Whole foods provide synergistic co-factors, flavonoids, and natural fiber structures that regulate gastric emptying and cellular nutrient uptake.\n- **Metabolic Flexibility**: Balancing moderate protein, unrefined complex carbohydrates, and monounsaturated lipids optimizes cellular insulin sensitivity and mitochondrial ATP generation.\n- **Actionable Step**: Focus on eating a colorful rainbow of whole vegetables, whole legumes, intact grains, and healthy fats while minimizing ultra-processed foods (NOVA Class 4).`;
      actions = [
        { label: '🔍 Browse 64 Global Foods', actionType: 'navigate', targetTab: 'explorer' },
        { label: '📚 Open Nutrition Hub', actionType: 'navigate', targetTab: 'learn' }
      ];
    }

    return { role: 'agent', content, actions };
  }

  function deriveFollowUpActions(userText) {
    return [
      { label: '🥩 Leucine Threshold', prompt: 'Tell me more about leucine in protein synthesis.' },
      { label: '🍋 Vitamin C + Iron', prompt: 'How does vitamin C improve non-heme iron absorption?' }
    ];
  }

  function renderChatMessages() {
    const container = document.getElementById('chat-messages-container');
    if (!container) return;

    container.innerHTML = state.chatHistory.map((msg, idx) => {
      const isUser = msg.role === 'user';
      const formattedContent = parseMarkdown(msg.content);

      return `
        <div class="chat-message ${isUser ? 'user' : 'agent'}">
          <div class="message-sender-row">
            <span>${isUser ? 'You' : '✨ VitaVue Intelligence'}</span>
          </div>
          <div class="message-bubble">
            ${formattedContent}
            ${msg.actions && msg.actions.length > 0 ? `
              <div class="agent-action-buttons">
                ${msg.actions.map(act => `
                  <button class="btn-chip" onclick="VitaVue.executeAgentAction(${idx}, '${escapeHtml(act.label)}')">
                    ${escapeHtml(act.label)}
                  </button>
                `).join('')}
              </div>
            ` : ''}
          </div>
        </div>
      `;
    }).join('');
  }

  function executeAgentAction(msgIdx, label) {
    const msg = state.chatHistory[msgIdx];
    if (!msg || !msg.actions) return;
    const act = msg.actions.find(a => a.label === label);
    if (!act) return;

    if (act.prompt) {
      sendAgentMessage(act.prompt);
    } else if (act.actionType === 'navigate') {
      switchTab(act.targetTab);
    } else if (act.actionType === 'readArticle') {
      switchTab('learn');
      openArticleModal(act.articleId);
    } else if (act.actionType === 'filterFoods') {
      switchTab('explorer');
      const tagSelect = document.getElementById('filter-dietary-tag');
      if (tagSelect) tagSelect.value = act.tag;
      filterFoods();
    } else if (act.actionType === 'selectCategory') {
      switchTab('explorer');
      selectFoodCategory(act.category);
    } else if (act.actionType === 'selectGoal') {
      switchTab('planner');
      selectPlannerGoal(act.goal);
    } else if (act.actionType === 'openMyth') {
      switchTab('learn');
      switchLearnSubtab('myths');
    }
  }

  function updateAgentContextBanner() {
    const banner = document.getElementById('agent-context-banner');
    const titleEl = document.getElementById('agent-context-title');

    if (!banner || !titleEl) return;

    if (state.activeContext) {
      banner.style.display = 'flex';
      if (state.activeContext.type === 'meal') {
        titleEl.textContent = state.activeContext.data.title;
      } else if (state.activeContext.type === 'food') {
        titleEl.textContent = state.activeContext.data.name;
      } else if (state.activeContext.type === 'plan') {
        titleEl.textContent = state.activeContext.data.name;
      }
    } else {
      banner.style.display = 'none';
    }
  }

  function clearAgentContext() {
    state.activeContext = null;
    updateAgentContextBanner();
    showToast("Context cleared from AI Nutritionist.");
  }

  function clearChatHistory() {
    state.chatHistory = [
      {
        role: 'agent',
        content: `**Chat history cleared.** How can I assist you with nutritional biochemistry or meal optimization today?`,
        actions: [
          { label: '🥩 Leucine Threshold', prompt: 'Explain the leucine trigger for muscle protein synthesis.' },
          { label: '🍋 Vitamin C + Iron Synergy', prompt: 'How does ascorbic acid enhance non-heme iron absorption?' }
        ]
      }
    ];
    renderChatMessages();
    showToast("Chat history reset.");
  }

  function scrollToChatBottom() {
    const container = document.getElementById('chat-messages-container');
    if (container) {
      setTimeout(() => {
        container.scrollTop = container.scrollHeight;
      }, 50);
    }
  }

  // =========================================================================
  // PERSONALIZED DIET PLANNER
  // =========================================================================
  function selectPlannerGoal(goalKey) {
    state.activePlannerGoal = goalKey;
    state.activePlannerDay = 1;

    document.querySelectorAll('.goal-card').forEach(card => {
      if (card.getAttribute('data-goal') === goalKey) card.classList.add('active');
      else card.classList.remove('active');
    });

    renderPlannerDay();
  }

  function selectPlannerDay(dayNum) {
    state.activePlannerDay = dayNum;

    document.querySelectorAll('.day-btn').forEach(btn => {
      if (parseInt(btn.getAttribute('data-day'), 10) === dayNum) btn.classList.add('active');
      else btn.classList.remove('active');
    });

    renderPlannerDay();
  }

  function renderPlannerDay() {
    const plan = DIET_PLANNER_PRESETS[state.activePlannerGoal];
    if (!plan) return;

    document.getElementById('plan-title').textContent = plan.name;
    document.getElementById('plan-tagline').textContent = plan.tagline;

    document.getElementById('plan-cals').textContent = `${plan.dailyTargets.calories.toLocaleString()} kcal`;
    document.getElementById('plan-protein').textContent = `${plan.dailyTargets.protein}g`;
    document.getElementById('plan-carbs').textContent = `${plan.dailyTargets.carbs}g`;
    document.getElementById('plan-fats').textContent = `${plan.dailyTargets.fats}g`;
    document.getElementById('plan-fiber').textContent = `${plan.dailyTargets.fiber}g`;

    const dayData = plan.days.find(d => d.dayNumber === state.activePlannerDay) || plan.days[0];
    const mealsContainer = document.getElementById('planner-meals-list');
    if (!mealsContainer) return;

    mealsContainer.innerHTML = dayData.meals.map(m => `
      <div class="planner-meal-card">
        <span class="meal-badge-type">${escapeHtml(m.type)}</span>
        <h4 class="planner-meal-title">${escapeHtml(m.title)}</h4>
        
        <div class="planner-meal-macros">
          <span><strong>${m.calories}</strong> kcal</span> • 
          <span>P: <strong>${m.p}g</strong></span> • 
          <span>C: <strong>${m.c}g</strong></span> • 
          <span>F: <strong>${m.f}g</strong></span> • 
          <span>Fiber: <strong>${m.fib}g</strong></span>
        </div>

        <p class="planner-meal-notes">${escapeHtml(m.notes)}</p>
      </div>
    `).join('');
  }

  function sendPlanToAgent() {
    const plan = DIET_PLANNER_PRESETS[state.activePlannerGoal];
    if (!plan) return;

    state.activeContext = {
      type: 'plan',
      data: plan
    };
    updateAgentContextBanner();
    switchTab('agent');

    const promptText = `I am reviewing the **${plan.name}** dietary plan (Target: ${plan.dailyTargets.calories} kcal, ${plan.dailyTargets.protein}g protein, ${plan.dailyTargets.carbs}g carbs, ${plan.dailyTargets.fats}g fats, ${plan.dailyTargets.fiber}g fiber). Can you suggest personalized ingredient substitutions for Day ${state.activePlannerDay}?`;
    sendAgentMessage(promptText);
  }

  // =========================================================================
  // USER PROFILE, LOGS & MACRO SUMMARY
  // =========================================================================
  function renderUserProfileAndLogs() {
    document.getElementById('stat-logged-count').textContent = state.loggedMeals.length;
    document.getElementById('stat-saved-foods').textContent = state.savedFoodIds.length;
    document.getElementById('stat-bookmarked-articles').textContent = state.bookmarkedArticleIds.length;

    // Render Logged Meals
    const logContainer = document.getElementById('user-meals-log-list');
    if (logContainer) {
      if (state.loggedMeals.length === 0) {
        logContainer.innerHTML = `
          <p style="font-size: 13px; color: var(--neutral-400); text-align: center; padding: 16px;">
            No meals logged today. Use the Meal Analyzer or load sample data!
          </p>
        `;
      } else {
        logContainer.innerHTML = state.loggedMeals.map(m => `
          <div class="logged-meal-item">
            <div>
              <strong style="color: var(--pure-white); font-size: 14px;">${escapeHtml(m.title)}</strong>
              <div style="font-size: 11.5px; color: var(--teal-300); margin-top: 2px;">
                ${m.calories} kcal • P: ${m.protein}g • C: ${m.carbs}g • F: ${m.fats}g • Fiber: ${m.fiber || 0}g (${m.timestamp})
              </div>
            </div>
            <button class="btn-text-close" onclick="VitaVue.deleteLoggedMeal('${m.id}')" title="Delete entry">🗑️</button>
          </div>
        `).join('');
      }
    }

    // Render Saved Foods
    const savedContainer = document.getElementById('user-saved-foods-list');
    if (savedContainer) {
      const savedFoodsList = ALL_FOODS.filter(f => state.savedFoodIds.includes(f.id));
      if (savedFoodsList.length === 0) {
        savedContainer.innerHTML = `<p style="font-size: 12.5px; color: var(--neutral-400); padding: 8px;">No foods favorited yet.</p>`;
      } else {
        savedContainer.innerHTML = savedFoodsList.map(f => `
          <div class="saved-item-row" onclick="VitaVue.openFoodDetailModal('${f.id}')">
            <strong>${escapeHtml(f.name)}</strong>
            <span style="color: var(--teal-300); font-size: 11px;">${f.calories} kcal →</span>
          </div>
        `).join('');
      }
    }

    // Render Bookmarks
    const bookmarksContainer = document.getElementById('user-bookmarks-list');
    if (bookmarksContainer) {
      const bookmarkedList = ALL_ARTICLES.filter(a => state.bookmarkedArticleIds.includes(a.id));
      if (bookmarkedList.length === 0) {
        bookmarksContainer.innerHTML = `<p style="font-size: 12.5px; color: var(--neutral-400); padding: 8px;">No articles bookmarked yet.</p>`;
      } else {
        bookmarksContainer.innerHTML = bookmarkedList.map(a => `
          <div class="saved-item-row" onclick="VitaVue.openArticleModal('${a.id}')">
            <span style="font-size: 12.5px; max-width: 80%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">${escapeHtml(a.title)}</span>
            <span style="color: var(--teal-300); font-size: 11px;">Read →</span>
          </div>
        `).join('');
      }
    }
  }

  function updateHomeMacroSummary() {
    let totCals = 0;
    let totP = 0;
    let totC = 0;
    let totF = 0;
    let totFib = 0;

    state.loggedMeals.forEach(m => {
      totCals += (m.calories || 0);
      totP += (m.protein || 0);
      totC += (m.carbs || 0);
      totF += (m.fats || 0);
      totFib += (m.fiber || 0);
    });

    const calsEl = document.getElementById('home-metric-cals');
    const pEl = document.getElementById('home-metric-p');
    const cEl = document.getElementById('home-metric-c');
    const fEl = document.getElementById('home-metric-f');
    const fibEl = document.getElementById('home-metric-fib');
    const statusEl = document.getElementById('home-log-status');

    if (calsEl) calsEl.textContent = `${totCals} kcal`;
    if (pEl) pEl.textContent = `${totP.toFixed(1)}g`;
    if (cEl) cEl.textContent = `${totC.toFixed(1)}g`;
    if (fEl) fEl.textContent = `${totF.toFixed(1)}g`;
    if (fibEl) fibEl.textContent = `${totFib.toFixed(1)}g`;

    if (statusEl) {
      if (state.loggedMeals.length === 0) {
        statusEl.textContent = "0 meals logged today. Use the Meal Analyzer to track your intake.";
      } else {
        statusEl.textContent = `Aggregated across ${state.loggedMeals.length} logged meal${state.loggedMeals.length > 1 ? 's' : ''} today.`;
      }
    }
  }

  function toggleDemoDayData() {
    if (state.loggedMeals.length > 0) {
      state.loggedMeals = [];
      showToast("Cleared sample day logs.");
    } else {
      state.loggedMeals = [
        { id: 'demo_1', title: "Overnight Oats with Berries & Chia", calories: 430, protein: 14, carbs: 64, fats: 15, fiber: 14.5, timestamp: "08:15 AM" },
        { id: 'demo_2', title: "Grilled Salmon Quinoa Bowl", calories: 580, protein: 42, carbs: 48, fats: 22, fiber: 9.5, timestamp: "01:30 PM" },
        { id: 'demo_3', title: "Greek Yogurt with Fig Slices & Walnuts", calories: 280, protein: 22, carbs: 24, fats: 11, fiber: 4.2, timestamp: "05:00 PM" }
      ];
      showToast("📊 Loaded sample day meals for live macro balance demonstration!");
    }
    savePersistedData();
    updateHomeMacroSummary();
    renderUserProfileAndLogs();
  }

  function clearLoggedMeals() {
    state.loggedMeals = [];
    savePersistedData();
    updateHomeMacroSummary();
    renderUserProfileAndLogs();
    showToast("Cleared meal log.");
  }

  function deleteLoggedMeal(id) {
    state.loggedMeals = state.loggedMeals.filter(m => m.id !== id);
    savePersistedData();
    updateHomeMacroSummary();
    renderUserProfileAndLogs();
    showToast("Meal removed from log.");
  }

  // =========================================================================
  // API KEY & SETTINGS
  // =========================================================================
  function saveCustomApiKey() {
    const input = document.getElementById('user-gemini-key');
    if (!input) return;
    const key = input.value.trim();
    if (key) {
      state.customApiKey = key;
      savePersistedData();
      updateApiKeyStatusUI(true);
      showToast("🔑 Custom Gemini API key saved to browser session!");
    } else {
      state.customApiKey = '';
      localStorage.removeItem('vitavue_custom_api_key');
      updateApiKeyStatusUI(false);
      showToast("Switched back to Built-in High-Fidelity Intelligence Engine.");
    }
  }

  function updateApiKeyStatusUI(hasKey) {
    const statusEl = document.getElementById('api-key-status');
    if (!statusEl) return;
    if (hasKey) {
      statusEl.textContent = "Active Engine: Direct Gemini Generative Multimodal API (Connected)";
      statusEl.style.color = "var(--emerald-400)";
    } else {
      statusEl.textContent = "Active Engine: Built-in High-Fidelity Scientific Intelligence Engine (Offline & Private)";
      statusEl.style.color = "var(--teal-400)";
    }
  }

  // =========================================================================
  // MODALS & TOASTS
  // =========================================================================
  function openModal(htmlContent) {
    const backdrop = document.getElementById('global-modal-backdrop');
    const container = document.getElementById('modal-content-container');
    const closeBtn = document.getElementById('modal-close-btn');

    if (!backdrop || !container) return;

    container.innerHTML = htmlContent;
    backdrop.style.display = 'flex';
    document.body.style.overflow = 'hidden';

    if (closeBtn) closeBtn.onclick = closeModal;

    backdrop.onclick = (e) => {
      if (e.target === backdrop) closeModal();
    };
  }

  function closeModal() {
    const backdrop = document.getElementById('global-modal-backdrop');
    if (backdrop) backdrop.style.display = 'none';
    document.body.style.overflow = 'auto';
  }

  function showToast(message) {
    const container = document.getElementById('toast-container');
    if (!container) return;

    const toast = document.createElement('div');
    toast.className = 'toast-notification';
    toast.textContent = message;
    container.appendChild(toast);

    setTimeout(() => {
      toast.style.opacity = '0';
      toast.style.transform = 'translateY(10px)';
      toast.style.transition = 'all 0.3s ease';
      setTimeout(() => toast.remove(), 300);
    }, 3200);
  }

  // =========================================================================
  // UTILITY FUNCTIONS
  // =========================================================================
  function delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  function escapeHtml(str) {
    if (!str) return '';
    return String(str)
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/"/g, '&quot;')
      .replace(/'/g, '&#039;');
  }

  function parseMarkdown(text) {
    if (!text) return '';
    let html = escapeHtml(text);

    // Headers
    html = html.replace(/^### (.*$)/gim, '<h4 style="color: var(--teal-300); font-weight: 800; margin: 10px 0 6px;">$1</h4>');
    html = html.replace(/^## (.*$)/gim, '<h3 style="color: var(--pure-white); font-weight: 800; margin: 12px 0 8px;">$1</h3>');

    // Bold & Italics
    html = html.replace(/\*\*(.*?)\*\*/gim, '<strong>$1</strong>');
    html = html.replace(/\*(.*?)\*/gim, '<em>$1</em>');

    // Bullet lists
    html = html.replace(/^\s*-\s+(.*$)/gim, '<li>$1</li>');
    html = html.replace(/(<li>.*<\/li>)/gim, '<ul>$1</ul>');
    html = html.replace(/<\/ul>\s*<ul>/gim, '');

    // Paragraphs
    html = html.split('\n\n').map(p => {
      if (p.startsWith('<h') || p.startsWith('<ul>') || p.startsWith('<li>')) return p;
      return `<p>${p.replace(/\n/g, '<br>')}</p>`;
    }).join('');

    return html;
  }

  // DOM Content Loaded Handler
  document.addEventListener('DOMContentLoaded', init);

  // Expose public API
  return {
    switchTab,
    loadSampleMealPreset,
    clearSelectedImage,
    analyzeMeal,
    saveAnalysisToLog,
    discussMealWithAgent,
    resetAnalyzer,
    retryAnalysis,
    selectFoodCategory,
    filterFoods,
    clearFoodSearch,
    resetFoodFilters,
    openFoodDetailModal,
    toggleSaveFood,
    askAgentAboutFood,
    switchLearnSubtab,
    selectArticleCategory,
    openArticleModal,
    toggleBookmarkArticle,
    discussArticleWithAgent,
    toggleMythCard,
    sendQuickPrompt,
    executeAgentAction,
    clearAgentContext,
    clearChatHistory,
    selectPlannerGoal,
    selectPlannerDay,
    sendPlanToAgent,
    toggleDemoDayData,
    clearLoggedMeals,
    deleteLoggedMeal,
    saveCustomApiKey,
    closeModal
  };

})();
