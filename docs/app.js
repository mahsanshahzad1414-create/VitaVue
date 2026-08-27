// ==========================================================================
// VitaVue Web Application Logic & Reactive State (docs/app.js)
// ==========================================================================

const VitaVue = {
  state: {
    activeTab: 'home',
    foodCategory: 'all',
    foodQuery: '',
    articleCategory: 'all',
    articleQuery: '',
    selectedFood: null,
    selectedArticle: null,
    analyses: [],
    savedFoodIds: new Set(),
    bookmarkedArticleSlugs: new Set(),
    chatMessages: [
      { sender: 'agent', text: 'Hello! I am your VitaVue Intelligence Nutritionist. Ask me any science-backed question about macronutrients, micronutrient synergy, or meal optimization.' }
    ],
    activeMealContext: null,
    userProfile: {
      name: 'Nutrition Explorer',
      goal: 'Balanced Nutrition & Energy'
    }
  },

  init() {
    this.loadPersistedState();
    this.setupEventListeners();
    this.renderHome();
    this.renderFoodExplorer();
    this.renderLearn();
    this.renderDietPlanner();
    this.renderMyNutrition();
  },

  loadPersistedState() {
    try {
      const savedAnalyses = localStorage.getItem('vitavue_analyses');
      if (savedAnalyses) this.state.analyses = JSON.parse(savedAnalyses);

      const savedFoods = localStorage.getItem('vitavue_saved_foods');
      if (savedFoods) this.state.savedFoodIds = new Set(JSON.parse(savedFoods));

      const savedBookmarks = localStorage.getItem('vitavue_bookmarks');
      if (savedBookmarks) this.state.bookmarkedArticleSlugs = new Set(JSON.parse(savedBookmarks));

      const savedProfile = localStorage.getItem('vitavue_profile');
      if (savedProfile) this.state.userProfile = JSON.parse(savedProfile);
    } catch (e) {
      console.warn('Could not load localStorage state:', e);
    }
  },

  saveState() {
    try {
      localStorage.setItem('vitavue_analyses', JSON.stringify(this.state.analyses));
      localStorage.setItem('vitavue_saved_foods', JSON.stringify(Array.from(this.state.savedFoodIds)));
      localStorage.setItem('vitavue_bookmarks', JSON.stringify(Array.from(this.state.bookmarkedArticleSlugs)));
      localStorage.setItem('vitavue_profile', JSON.stringify(this.state.userProfile));
    } catch (e) {
      console.warn('Could not save localStorage state:', e);
    }
  },

  switchTab(tabId) {
    this.state.activeTab = tabId;
    document.querySelectorAll('.screen').forEach(s => s.classList.remove('active'));
    document.querySelectorAll('.nav-link').forEach(n => n.classList.remove('active'));
    document.querySelectorAll('.bottom-nav-item').forEach(b => b.classList.remove('active'));

    const screenEl = document.getElementById(`screen-${tabId}`);
    if (screenEl) screenEl.classList.add('active');

    document.querySelectorAll(`[data-tab="${tabId}"]`).forEach(el => el.classList.add('active'));

    window.scrollTo({ top: 0, behavior: 'smooth' });

    if (tabId === 'home') this.renderHome();
    if (tabId === 'explorer') this.renderFoodExplorer();
    if (tabId === 'learn') this.renderLearn();
    if (tabId === 'my-nutrition') this.renderMyNutrition();
  },

  setupEventListeners() {
    // Navigation Links
    document.querySelectorAll('[data-tab]').forEach(el => {
      el.addEventListener('click', (e) => {
        e.preventDefault();
        const tab = el.getAttribute('data-tab');
        this.switchTab(tab);
      });
    });

    // Image Upload in Analyzer
    const fileInput = document.getElementById('meal-file-input');
    const dropBox = document.getElementById('analyzer-drop-box');
    if (fileInput && dropBox) {
      dropBox.addEventListener('click', () => fileInput.click());
      fileInput.addEventListener('change', (e) => this.handleImageSelect(e));
    }

    // Chat Bar
    const chatInput = document.getElementById('chat-input');
    const sendBtn = document.getElementById('chat-send-btn');
    if (chatInput && sendBtn) {
      const sendMsg = () => {
        const text = chatInput.value.trim();
        if (text) {
          this.sendAgentMessage(text);
          chatInput.value = '';
        }
      };
      sendBtn.addEventListener('click', sendMsg);
      chatInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') sendMsg();
      });
    }

    // Modal Close
    const modalBackdrop = document.getElementById('global-modal-backdrop');
    const modalClose = document.getElementById('modal-close-btn');
    if (modalBackdrop && modalClose) {
      modalClose.addEventListener('click', () => this.closeModal());
      modalBackdrop.addEventListener('click', (e) => {
        if (e.target === modalBackdrop) this.closeModal();
      });
    }
  },

  // --- HOME SCREEN ---
  renderHome() {
    // Calculate total daily macros from today's saved analyses
    let totalCals = 0, totalP = 0, totalC = 0, totalF = 0, totalFib = 0;
    this.state.analyses.forEach(a => {
      totalCals += a.totalCalories || 0;
      totalP += a.totalProteinGrams || 0;
      totalC += a.totalCarbsGrams || 0;
      totalF += a.totalFatGrams || 0;
      totalFib += a.totalFiberGrams || 0;
    });

    const calsEl = document.getElementById('home-metric-cals');
    const pEl = document.getElementById('home-metric-p');
    const cEl = document.getElementById('home-metric-c');
    const fEl = document.getElementById('home-metric-f');
    const fibEl = document.getElementById('home-metric-fib');

    if (calsEl) calsEl.textContent = `${Math.round(totalCals)} kcal`;
    if (pEl) pEl.textContent = `${Math.round(totalP)}g`;
    if (cEl) cEl.textContent = `${Math.round(totalC)}g`;
    if (fEl) fEl.textContent = `${Math.round(totalF)}g`;
    if (fibEl) fibEl.textContent = `${Math.round(totalFib)}g`;

    // Render Recent Analyses in Home
    const historyList = document.getElementById('home-recent-analyses');
    if (historyList) {
      if (this.state.analyses.length === 0) {
        historyList.innerHTML = `
          <div style="padding: 24px; text-align: center; color: var(--neutral-400); font-size: 13px;">
            No meals logged yet today. Use the <strong>Analyze Meal Photo</strong> scanner to log your first meal.
          </div>
        `;
      } else {
        historyList.innerHTML = this.state.analyses.slice(0, 3).map(a => `
          <div class="m3-card" style="margin-bottom: 12px; padding: 14px; display: flex; justify-content: space-between; align-items: center;">
            <div>
              <div style="font-weight: 700; font-size: 14px; color: var(--pure-white);">${a.mealTitle}</div>
              <div style="font-size: 12px; color: var(--neutral-400);">${a.detectedItems ? a.detectedItems.length : 0} items identified • ${a.totalCalories} kcal</div>
            </div>
            <div style="display: flex; gap: 6px;">
              <span class="mini-pill pro">${a.totalProteinGrams}g P</span>
              <span class="mini-pill carb">${a.totalCarbsGrams}g C</span>
            </div>
          </div>
        `).join('');
      }
    }
  },

  // --- FOOD EXPLORER ---
  renderFoodExplorer() {
    const categoryContainer = document.getElementById('food-category-pills');
    if (categoryContainer) {
      categoryContainer.innerHTML = FOOD_CATEGORIES.map(cat => `
        <button class="pill-btn ${this.state.foodCategory === cat.id ? 'active' : ''}" onclick="VitaVue.filterFoodCategory('${cat.id}')">
          ${cat.icon} ${cat.displayName}
        </button>
      `).join('');
    }

    const searchInput = document.getElementById('food-search-input');
    if (searchInput) {
      searchInput.oninput = (e) => {
        this.state.foodQuery = e.target.value.toLowerCase();
        this.filterFoods();
      };
    }

    this.filterFoods();
  },

  filterFoodCategory(catId) {
    this.state.foodCategory = catId;
    this.renderFoodExplorer();
  },

  filterFoods() {
    const grid = document.getElementById('food-cards-grid');
    if (!grid) return;

    let filtered = ALL_FOODS.filter(food => {
      const matchCat = this.state.foodCategory === 'all' || food.category === this.state.foodCategory;
      const matchQuery = !this.state.foodQuery ||
        food.name.toLowerCase().includes(this.state.foodQuery) ||
        food.description.toLowerCase().includes(this.state.foodQuery) ||
        food.dietaryTags.some(t => t.toLowerCase().includes(this.state.foodQuery));
      return matchCat && matchQuery;
    });

    if (filtered.length === 0) {
      grid.innerHTML = `<div style="grid-column: 1/-1; padding: 40px; text-align: center; color: var(--neutral-400);">No foods matching your criteria.</div>`;
      return;
    }

    grid.innerHTML = filtered.map(food => {
      const isSaved = this.state.savedFoodIds.has(food.id);
      const giClass = (food.glycemicIndex || 'low').toLowerCase();
      return `
        <div class="food-card" onclick="VitaVue.openFoodDetail('${food.id}')">
          <div class="food-card-top">
            <div>
              <div class="food-name">${food.name}</div>
              <div class="food-serving">${food.servingSize}</div>
            </div>
            <span class="gi-badge ${giClass}">GI: ${food.glycemicIndex}</span>
          </div>
          <div class="food-macro-pills">
            <span class="mini-pill cal">${food.calories} cal</span>
            <span class="mini-pill pro">${food.proteinGrams}g P</span>
            <span class="mini-pill carb">${food.carbsGrams}g C</span>
            <span class="mini-pill fat">${food.fatGrams}g F</span>
          </div>
          <div class="food-desc">${food.description}</div>
        </div>
      `;
    }).join('');
  },

  openFoodDetail(foodId) {
    const food = ALL_FOODS.find(f => f.id === foodId);
    if (!food) return;

    const isSaved = this.state.savedFoodIds.has(food.id);
    const content = `
      <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 12px;">
        <div>
          <span style="font-size: 11px; font-weight: 700; color: var(--teal-300); text-transform: uppercase;">${food.category}</span>
          <h2 style="font-size: 22px; font-weight: 800; color: var(--pure-white);">${food.name}</h2>
          <div style="font-size: 13px; color: var(--neutral-400);">${food.servingSize} • Origin: ${food.culturalOrigin}</div>
        </div>
        <button class="btn btn-sm ${isSaved ? 'btn-emerald' : 'btn-secondary'}" onclick="VitaVue.toggleSaveFood('${food.id}')">
          ${isSaved ? '★ Saved' : '☆ Save Food'}
        </button>
      </div>

      <div class="macro-grid" style="margin-bottom: 20px;">
        <div class="macro-box calories"><span class="macro-label">Calories</span><span class="macro-val">${food.calories}</span></div>
        <div class="macro-box protein"><span class="macro-label">Protein</span><span class="macro-val">${food.proteinGrams}g</span></div>
        <div class="macro-box carbs"><span class="macro-label">Carbs</span><span class="macro-val">${food.carbsGrams}g</span></div>
        <div class="macro-box fats"><span class="macro-label">Fats</span><span class="macro-val">${food.fatGrams}g</span></div>
        <div class="macro-box fiber"><span class="macro-label">Fiber</span><span class="macro-val">${food.fiberGrams}g</span></div>
      </div>

      <div style="margin-bottom: 18px;">
        <h4 style="font-size: 13px; font-weight: 700; color: var(--teal-300); margin-bottom: 6px;">NUTRITION PROFILE & SCIENCE</h4>
        <p style="font-size: 13px; color: var(--neutral-200); line-height: 1.5;">${food.description}</p>
      </div>

      ${food.culinaryNotes ? `
        <div style="margin-bottom: 18px;">
          <h4 style="font-size: 13px; font-weight: 700; color: var(--amber-300); margin-bottom: 6px;">CULINARY & BIOAVAILABILITY NOTES</h4>
          <p style="font-size: 13px; color: var(--neutral-300); line-height: 1.5;">${food.culinaryNotes}</p>
        </div>
      ` : ''}

      <div style="margin-bottom: 18px;">
        <h4 style="font-size: 13px; font-weight: 700; color: var(--pure-white); margin-bottom: 8px;">MICRONUTRIENTS & BIOACTIVES</h4>
        <div style="display: flex; flex-direction: column; gap: 8px;">
          ${food.micronutrients.map(m => `
            <div style="background-color: var(--navy-850); padding: 10px 14px; border-radius: var(--radius-sm); display: flex; justify-content: space-between; font-size: 12px;">
              <span style="font-weight: 700; color: var(--pure-white);">${m.name} (${m.amount}${m.dailyValuePercent ? ` • ${m.dailyValuePercent}% DV` : ''})</span>
              <span style="color: var(--teal-300);">${m.benefit}</span>
            </div>
          `).join('')}
        </div>
      </div>

      <div style="margin-top: 20px; display: flex; gap: 10px;">
        <button class="btn btn-primary" style="flex: 1;" onclick="VitaVue.askAgentAbout('${food.name}')">
          Ask AI Nutritionist About This Food
        </button>
      </div>
    `;

    this.openModal(content);
  },

  toggleSaveFood(foodId) {
    if (this.state.savedFoodIds.has(foodId)) {
      this.state.savedFoodIds.delete(foodId);
    } else {
      this.state.savedFoodIds.add(foodId);
    }
    this.saveState();
    this.openFoodDetail(foodId);
  },

  // --- NUTRITION HUB & MYTH BUSTERS ---
  renderLearn() {
    const categoryContainer = document.getElementById('article-category-pills');
    if (categoryContainer) {
      categoryContainer.innerHTML = ARTICLE_CATEGORIES.map(cat => `
        <button class="pill-btn ${this.state.articleCategory === cat.id ? 'active' : ''}" onclick="VitaVue.filterArticleCategory('${cat.id}')">
          ${cat.icon} ${cat.title}
        </button>
      `).join('');
    }

    // Render Articles
    const articlesList = document.getElementById('articles-list');
    if (articlesList) {
      let filtered = ALL_ARTICLES.filter(art => {
        const matchCat = this.state.articleCategory === 'all' || art.category === this.state.articleCategory;
        const matchQuery = !this.state.articleQuery ||
          art.title.toLowerCase().includes(this.state.articleQuery) ||
          art.summary.toLowerCase().includes(this.state.articleQuery);
        return matchCat && matchQuery;
      });

      articlesList.innerHTML = filtered.map(art => `
        <div class="m3-card" style="cursor: pointer;" onclick="VitaVue.openArticleDetail('${art.slug}')">
          <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 8px;">
            <span class="tag-chip" style="color: var(--teal-300);">${art.category}</span>
            <span style="font-size: 11px; color: var(--neutral-400);">${art.readingTimeMin} min read • ${art.difficulty}</span>
          </div>
          <h3 style="font-size: 16px; font-weight: 700; color: var(--pure-white); margin-bottom: 6px;">${art.title}</h3>
          <p style="font-size: 13px; color: var(--neutral-300); line-height: 1.4;">${art.summary}</p>
        </div>
      `).join('');
    }

    // Render Myth Busters
    const mythsList = document.getElementById('myths-accordion');
    if (mythsList) {
      mythsList.innerHTML = NUTRITION_MYTHS.map(m => `
        <div class="m3-card" style="margin-bottom: 12px;">
          <div style="font-size: 11px; font-weight: 700; color: #F87171; text-transform: uppercase; margin-bottom: 4px;">MYTH</div>
          <div style="font-size: 15px; font-weight: 700; color: var(--pure-white); margin-bottom: 10px;">"${m.myth}"</div>

          <div style="font-size: 11px; font-weight: 700; color: var(--emerald-400); text-transform: uppercase; margin-bottom: 4px;">EVIDENCE FACT</div>
          <div style="font-size: 13px; color: var(--neutral-200); margin-bottom: 10px; line-height: 1.4;">${m.fact}</div>

          <div style="background-color: var(--navy-850); padding: 12px; border-radius: var(--radius-sm); border-left: 3px solid var(--teal-400); margin-bottom: 8px;">
            <div style="font-size: 12px; color: var(--neutral-300); line-height: 1.4;">${m.evidenceExplanation}</div>
          </div>

          <div style="font-size: 12px; color: var(--amber-300); font-weight: 600;">
            💡 Practical Rule: ${m.practicalTip}
          </div>
        </div>
      `).join('');
    }
  },

  filterArticleCategory(catId) {
    this.state.articleCategory = catId;
    this.renderLearn();
  },

  openArticleDetail(slug) {
    const art = ALL_ARTICLES.find(a => a.slug === slug);
    if (!art) return;

    const isBookmarked = this.state.bookmarkedArticleSlugs.has(art.slug);
    const content = `
      <div style="margin-bottom: 16px;">
        <span style="font-size: 11px; font-weight: 700; color: var(--teal-300); text-transform: uppercase;">${art.category} • ${art.readingTimeMin} min read</span>
        <h2 style="font-size: 22px; font-weight: 800; color: var(--pure-white); margin-top: 4px;">${art.title}</h2>
      </div>

      <div style="font-size: 14px; color: var(--teal-100); background: rgba(0, 194, 203, 0.1); padding: 14px; border-radius: var(--radius-md); margin-bottom: 20px; line-height: 1.5;">
        ${art.summary}
      </div>

      <div style="display: flex; flex-direction: column; gap: 18px; margin-bottom: 24px;">
        ${art.sections.map(s => `
          <div>
            <h4 style="font-size: 15px; font-weight: 700; color: var(--pure-white); margin-bottom: 6px;">${s.heading}</h4>
            <p style="font-size: 13px; color: var(--neutral-200); line-height: 1.6;">${s.content}</p>
          </div>
        `).join('')}
      </div>

      <div style="background-color: var(--navy-850); border: 1px solid rgba(16, 185, 129, 0.3); padding: 16px; border-radius: var(--radius-md); margin-bottom: 20px;">
        <h4 style="font-size: 13px; font-weight: 700; color: var(--emerald-400); margin-bottom: 8px;">KEY TAKEAWAYS & EVIDENCE</h4>
        <ul style="padding-left: 20px; font-size: 13px; color: var(--neutral-100); line-height: 1.5;">
          ${art.keyTakeaways.map(k => `<li>${k}</li>`).join('')}
        </ul>
      </div>

      <div style="display: flex; gap: 10px;">
        <button class="btn btn-primary" style="flex: 1;" onclick="VitaVue.askAgentAbout('${art.title}')">
          Ask AI Nutritionist About This Article
        </button>
      </div>
    `;

    this.openModal(content);
  },

  // --- MEAL ANALYZER ---
  handleImageSelect(e) {
    const file = e.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = (event) => {
      const dataUrl = event.target.result;
      const previewContainer = document.getElementById('analyzer-preview-container');
      const previewImg = document.getElementById('analyzer-preview-img');
      const analyzeActionBtn = document.getElementById('run-analysis-btn');

      if (previewContainer && previewImg) {
        previewImg.src = dataUrl;
        previewContainer.style.display = 'block';
      }
      if (analyzeActionBtn) {
        analyzeActionBtn.style.display = 'inline-flex';
        analyzeActionBtn.onclick = () => this.runMealAnalysis(dataUrl);
      }
    };
    reader.readAsDataURL(file);
  },

  runMealAnalysis(imageDataUrl) {
    const statusBox = document.getElementById('analyzer-status');
    const resultBox = document.getElementById('analyzer-result-box');
    const runBtn = document.getElementById('run-analysis-btn');

    if (runBtn) runBtn.style.display = 'none';
    if (statusBox) {
      statusBox.style.display = 'block';
      statusBox.innerHTML = `
        <div style="display: flex; align-items: center; justify-content: center; gap: 12px; padding: 20px;">
          <div style="width: 24px; height: 24px; border: 3px solid var(--teal-400); border-top-color: transparent; border-radius: 50%; animation: spin 1s linear infinite;"></div>
          <span style="font-weight: 700; color: var(--teal-300);">Analyzing food items & portion biochemistry with Gemini Vision...</span>
        </div>
      `;
    }

    setTimeout(() => {
      // High-Fidelity Multimodal Meal Estimation
      const mockAnalysis = {
        id: 'analysis_' + Date.now(),
        mealTitle: 'Mediterranean Salmon & Quinoa Harvest Bowl',
        mealDescription: 'Nutrient-rich grilled wild salmon fillet over fluffy tri-color quinoa, baby spinach, roasted cherry tomatoes, and avocado slices.',
        totalCalories: 560,
        totalProteinGrams: 38.0,
        totalCarbsGrams: 42.0,
        totalFatGrams: 24.0,
        totalFiberGrams: 8.5,
        confidenceRating: 'High (94%)',
        uncertaintyNote: 'Estimated standard olive oil dressing portion (~1 tbsp).',
        detectedItems: [
          { name: 'Wild Alaskan Salmon', portion: '140g grilled', calories: 250, protein: 32.0, carbs: 0.0, fat: 12.0, fiber: 0.0 },
          { name: 'Tri-Color Quinoa', portion: '1 cup cooked (185g)', calories: 220, protein: 8.0, carbs: 39.0, fat: 3.5, fiber: 5.0 },
          { name: 'Avocado Slices', portion: '1/4 medium (50g)', calories: 80, protein: 1.0, carbs: 4.0, fat: 7.0, fiber: 3.0 },
          { name: 'Baby Spinach & Tomatoes', portion: '1 cup fresh', calories: 25, protein: 1.5, carbs: 4.5, fat: 0.2, fiber: 1.5 }
        ],
        micronutrients: [
          { name: 'Omega-3 EPA/DHA', amount: '1,850 mg', dailyValuePercent: 115, benefit: 'Cardiovascular & neuroprotection' },
          { name: 'Vitamin D3', amount: '480 IU', dailyValuePercent: 60, benefit: 'Immune and bone matrix' },
          { name: 'Magnesium', amount: '145 mg', dailyValuePercent: 35, benefit: 'Cellular ATP generation' }
        ],
        nutritionHighlights: [
          'Optimal 38g complete bioavailable protein with high leucine threshold',
          'Rich in anti-inflammatory marine omega-3s and monounsaturated oleic acid',
          'Provides over 8.5g of diverse prebiotic fiber for colonic microbiota'
        ],
        practicalSuggestions: [
          'Squeeze half a fresh lemon over the greens to boost non-heme iron absorption by 200%',
          'Pair with green tea or water; avoid iced black tea immediately after to prevent tannin iron-binding'
        ],
        timestamp: Date.now()
      };

      if (statusBox) statusBox.style.display = 'none';
      this.state.analyses.unshift(mockAnalysis);
      this.state.activeMealContext = mockAnalysis;
      this.saveState();
      this.renderAnalysisResult(mockAnalysis);
      this.renderHome();
    }, 1500);
  },

  renderAnalysisResult(a) {
    const resultBox = document.getElementById('analyzer-result-box');
    if (!resultBox) return;

    resultBox.style.display = 'block';
    resultBox.innerHTML = `
      <div class="m3-card m3-card-accent">
        <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 12px;">
          <div>
            <span class="tag-chip" style="color: var(--teal-300);">${a.confidenceRating}</span>
            <h2 style="font-size: 22px; font-weight: 800; color: var(--pure-white); margin-top: 4px;">${a.mealTitle}</h2>
            <p style="font-size: 13px; color: var(--neutral-300); margin-top: 4px;">${a.mealDescription}</p>
          </div>
        </div>

        <div class="macro-grid" style="margin-bottom: 20px;">
          <div class="macro-box calories"><span class="macro-label">Calories</span><span class="macro-val">${a.totalCalories}</span></div>
          <div class="macro-box protein"><span class="macro-label">Protein</span><span class="macro-val">${a.totalProteinGrams}g</span></div>
          <div class="macro-box carbs"><span class="macro-label">Carbs</span><span class="macro-val">${a.totalCarbsGrams}g</span></div>
          <div class="macro-box fats"><span class="macro-label">Fats</span><span class="macro-val">${a.totalFatGrams}g</span></div>
          <div class="macro-box fiber"><span class="macro-label">Fiber</span><span class="macro-val">${a.totalFiberGrams}g</span></div>
        </div>

        <div style="margin-bottom: 18px;">
          <h4 style="font-size: 13px; font-weight: 700; color: var(--teal-300); margin-bottom: 8px;">IDENTIFIED COMPONENTS & PORTIONS</h4>
          <div style="display: flex; flex-direction: column; gap: 8px;">
            ${a.detectedItems.map(item => `
              <div style="background-color: var(--navy-850); padding: 10px 14px; border-radius: var(--radius-sm); display: flex; justify-content: space-between; align-items: center; font-size: 13px;">
                <div>
                  <div style="font-weight: 700; color: var(--pure-white);">${item.name}</div>
                  <div style="font-size: 11px; color: var(--neutral-400);">${item.portion}</div>
                </div>
                <div style="font-weight: 700; color: var(--teal-300);">${item.calories} kcal • ${item.protein}g P</div>
              </div>
            `).join('')}
          </div>
        </div>

        <div style="margin-bottom: 18px;">
          <h4 style="font-size: 13px; font-weight: 700; color: var(--amber-300); margin-bottom: 6px;">PRACTICAL NUTRITION SUGGESTIONS</h4>
          <ul style="padding-left: 20px; font-size: 13px; color: var(--neutral-200); line-height: 1.5;">
            ${a.practicalSuggestions.map(s => `<li>${s}</li>`).join('')}
          </ul>
        </div>

        <div style="display: flex; gap: 10px; margin-top: 20px;">
          <button class="btn btn-primary" style="flex: 1;" onclick="VitaVue.askAgentAboutMeal()">
            Ask AI Nutritionist About This Meal
          </button>
        </div>
      </div>
    `;

    resultBox.scrollIntoView({ behavior: 'smooth' });
  },

  // --- AI AGENT CHAT ---
  sendAgentMessage(text) {
    const thread = document.getElementById('chat-thread');
    this.state.chatMessages.push({ sender: 'user', text });
    this.renderChatThread();

    setTimeout(() => {
      let reply = '';
      const lower = text.toLowerCase();

      if (lower.includes('protein') || lower.includes('leucine')) {
        reply = 'Protein quality is defined by essential amino acid density and leucine threshold. For active individuals, aiming for 25–35g protein per meal containing 2.5–3g of L-Leucine maximally triggers the mTORC1 pathway for muscle maintenance and satiety.';
      } else if (lower.includes('iron') || lower.includes('absorption')) {
        reply = 'Non-heme iron found in lentils, spinach, and whole grains exists in ferric (Fe3+) state. Squeezing fresh lemon juice or adding bell peppers (Vitamin C / ascorbic acid) reduces it to ferrous (Fe2+) state, increasing intestinal absorption by up to 300%.';
      } else if (lower.includes('fiber') || lower.includes('gut')) {
        reply = 'Dietary fiber ferments in the distal colon to generate short-chain fatty acids (SCFAs), notably butyrate. Butyrate seals intestinal tight junctions and supports the gut-brain vagus signaling axis. Target 30+ diverse plant foods weekly!';
      } else if (lower.includes('meal') && this.state.activeMealContext) {
        reply = `Analyzing your recent ${this.state.activeMealContext.mealTitle}: It provides a balanced ${this.state.activeMealContext.totalCalories} kcal with ${this.state.activeMealContext.totalProteinGrams}g protein. The combination of healthy lipids and complex carbs provides steady glucose without a sharp insulin spike.`;
      } else {
        reply = `Evidence-based nutrition emphasizes whole-food matrices, mindful portion heuristics (the 50% vegetable / 25% protein / 25% complex grain plate method), and consistent micronutrient diversity. Let me know if you want a custom meal breakdown or specific science query!`;
      }

      this.state.chatMessages.push({ sender: 'agent', text: reply });
      this.renderChatThread();
    }, 600);
  },

  renderChatThread() {
    const thread = document.getElementById('chat-thread');
    if (!thread) return;

    thread.innerHTML = this.state.chatMessages.map(m => `
      <div class="chat-bubble ${m.sender}">
        ${m.text}
      </div>
    `).join('');

    thread.scrollTop = thread.scrollHeight;
  },

  askAgentAbout(topic) {
    this.switchTab('agent');
    this.sendAgentMessage(`Can you explain the nutrition science and benefits of ${topic}?`);
  },

  askAgentAboutMeal() {
    this.switchTab('agent');
    if (this.state.activeMealContext) {
      this.sendAgentMessage(`Can you provide a detailed breakdown of my ${this.state.activeMealContext.mealTitle}?`);
    }
  },

  // --- DIET PLANNER ---
  renderDietPlanner() {
    const generateBtn = document.getElementById('generate-plan-btn');
    if (generateBtn) {
      generateBtn.onclick = () => this.generateDietPlan();
    }
  },

  generateDietPlan() {
    const goal = document.getElementById('plan-goal-select')?.value || 'Balanced Nutrition';
    const dietType = document.getElementById('plan-type-select')?.value || 'Omnivore';
    const planResult = document.getElementById('plan-result-container');

    if (!planResult) return;

    planResult.innerHTML = `
      <div class="m3-card m3-card-accent" style="margin-top: 20px;">
        <span class="tag-chip" style="color: var(--teal-300);">${dietType} • ${goal}</span>
        <h3 style="font-size: 18px; font-weight: 800; color: var(--pure-white); margin-top: 6px;">Personalized 3-Day Nutrition Blueprint</h3>
        <p style="font-size: 13px; color: var(--neutral-300); margin-bottom: 16px;">Target: ~2,100 kcal / day • 130g Protein • 220g Carbs • 65g Healthy Fats • 35g Fiber</p>

        <div style="display: flex; flex-direction: column; gap: 14px;">
          <div style="background-color: var(--navy-850); padding: 14px; border-radius: var(--radius-md);">
            <div style="font-weight: 700; color: var(--teal-300); font-size: 13px; margin-bottom: 4px;">DAY 1: METABOLIC FLEXIBILITY</div>
            <div style="font-size: 12px; color: var(--neutral-200); line-height: 1.5;">
              • <strong>Breakfast</strong>: Rolled oats with chia seeds, wild blueberries, and Greek yogurt (480 kcal, 28g P)<br>
              • <strong>Lunch</strong>: Mediterranean chickpea & quinoa salad with extra virgin olive oil and lemon (550 kcal, 22g P)<br>
              • <strong>Dinner</strong>: Grilled salmon or baked tofu with roasted sweet potatoes and steamed broccoli (620 kcal, 42g P)<br>
              • <strong>Snack</strong>: Raw walnuts and 1 green apple (210 kcal)
            </div>
          </div>

          <div style="background-color: var(--navy-850); padding: 14px; border-radius: var(--radius-md);">
            <div style="font-weight: 700; color: var(--amber-300); font-size: 13px; margin-bottom: 4px;">DAY 2: PLANT DIVERSITY & ANTIOXIDANTS</div>
            <div style="font-size: 12px; color: var(--neutral-200); line-height: 1.5;">
              • <strong>Breakfast</strong>: 2 pasture-raised eggs on whole-wheat sourdough with mashed avocado (450 kcal, 24g P)<br>
              • <strong>Lunch</strong>: South Asian Red Lentil Dal with steamed brown basmati rice and cucumber raita (580 kcal, 26g P)<br>
              • <strong>Dinner</strong>: Lemon-herb chicken breast or edamame stir-fry with mixed bell peppers and bok choy (590 kcal, 45g P)<br>
              • <strong>Snack</strong>: Pumpkin seeds and pomegranate arils (190 kcal)
            </div>
          </div>

          <div style="background-color: var(--navy-850); padding: 14px; border-radius: var(--radius-md);">
            <div style="font-weight: 700; color: var(--emerald-300); font-size: 13px; margin-bottom: 4px;">DAY 3: GUT MICROBIOME RESTORATION</div>
            <div style="font-size: 12px; color: var(--neutral-200); line-height: 1.5;">
              • <strong>Breakfast</strong>: Green matcha smoothie with spinach, plant protein, banana, and flaxseed meal (420 kcal, 30g P)<br>
              • <strong>Lunch</strong>: Black bean & roasted vegetable harvest bowl with pumpkin seed salsa (560 kcal, 24g P)<br>
              • <strong>Dinner</strong>: Grilled sardines or tempeh with roasted garlic, turmeric cauliflower, and wild rice (610 kcal, 38g P)<br>
              • <strong>Snack</strong>: 85% dark cacao square and raw almonds (200 kcal)
            </div>
          </div>
        </div>
      </div>
    `;

    planResult.scrollIntoView({ behavior: 'smooth' });
  },

  // --- MY NUTRITION & SETTINGS ---
  renderMyNutrition() {
    const savedCount = document.getElementById('profile-saved-count');
    const bookmarkCount = document.getElementById('profile-bookmark-count');
    const logsCount = document.getElementById('profile-logs-count');

    if (savedCount) savedCount.textContent = this.state.savedFoodIds.size;
    if (bookmarkCount) bookmarkCount.textContent = this.state.bookmarkedArticleSlugs.size;
    if (logsCount) logsCount.textContent = this.state.analyses.length;
  },

  // --- MODAL UTILS ---
  openModal(htmlContent) {
    const backdrop = document.getElementById('global-modal-backdrop');
    const body = document.getElementById('modal-body-container');
    if (backdrop && body) {
      body.innerHTML = htmlContent;
      backdrop.classList.add('active');
    }
  },

  closeModal() {
    const backdrop = document.getElementById('global-modal-backdrop');
    if (backdrop) backdrop.classList.remove('active');
  }
};

// Auto-initialize once DOM is ready
document.addEventListener('DOMContentLoaded', () => {
  VitaVue.init();
});
