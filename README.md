# VitaVue — AI Nutrition Intelligence Agent
> *“See your food differently.”*

Built for the Global AI / Web Hackathon by **Muhammad Ahsan Shahzad** (Lead AI Engineer & System Architect).

---

## 🌟 Executive Summary

**VitaVue** is an international-grade, multimodal AI food and nutrition intelligence application designed to bridge the gap between visual eating experiences and evidence-based nutrition science.

Unlike static trackers or generic chatbots that guess numbers without context, VitaVue pairs **multimodal vision intelligence** with a scientific nutrition knowledge base (100+ analyzed global foods, 8 comprehensive science modules, and peer-reviewed myth busters) and a conversational AI agent that can reason through portion sizes, bioavailability, glycemic impact, and cultural preparations.

---

## 🚀 Core Pillars & Capabilities

```
SEE → ANALYZE → UNDERSTAND → LEARN → PLAN → IMPROVE
```

1. **Multimodal Meal Analyzer**:
   - Accepts image input (camera / gallery / preset international meals like Chicken Biryani & Raita, Mediterranean Salmon Bowl, Avocado Sourdough, Chickpea Falafel Bowl).
   - Extracts structured macronutrient distribution (Protein, Carbs, Fats, Fiber) and calculates calories.
   - Dissects detected meal components with portion estimates.
   - Highlights key micronutrients & bioactive compounds (e.g. Curcumin, Omega-3s, Choline, Polyphenols) with Daily Value percentages.
   - Outlines practical culinary enhancement suggestions and explicit confidence ratings with uncertainty notes.

2. **VitaVue Conversational Intelligence Agent**:
   - Context-aware dialogue grounded in active meal scans, explored foods, or dietary goals.
   - Multi-turn nutritional reasoning, non-heme iron absorption tips, gut microbiome insights, and macro budgeting.
   - Integrated action triggers that seamlessly navigate to deep-dive articles, diet planner modules, or food detail breakdowns.

3. **International Food Explorer (100+ Items)**:
   - Covers 10 food categories: Fruits, Vegetables, Whole Grains, Legumes & Pulses, Nuts & Seeds, Dairy & Alternatives, Lean Proteins, Healthy Fats, Beverages, and Cultural Meals.
   - Granular dietary filtering: Vegan, High-Protein, Gluten-Free, Low-Carb, Heart-Healthy, Halal, Keto-Friendly.
   - Detailed micronutrient tables, glycemic index ratings, and culinary preparation science.

4. **Evidence-Based Nutrition Hub & Myth Busters**:
   - 8 disciplines: Macronutrient Mechanics, Vitamins & Minerals, Foundations, Practical Habits, Dietary Patterns, Active Nutrition, Life-Stage, and Evidence vs Myths.
   - Interactive Myth Busters dismantling pervasive misconceptions (e.g., late-night carbs, complete plant proteins, egg yolks, detox cleanses).

5. **Personalized Precision Diet Planner**:
   - Tailors 3-day and weekly meal matrices based on primary goals, dietary patterns, cuisine inspirations, and target calories.
   - Provides complete prep times, ingredient lists, and macro distributions.

6. **Local Persistence (Room Database)**:
   - Secure, on-device caching of meal logs, favorite foods, bookmarked articles, custom diet plans, and user preferences.

7. **Ethical & Responsible AI Guardrails**:
   - Clear non-medical disclaimer boundaries.
   - Transparent visual uncertainty reporting.

---

## 🛠️ Architecture & Tech Stack

- **Platform**: Android Native with Kotlin DSL
- **UI Framework**: Jetpack Compose with Material Design 3 (Midnight Navy, Luminous Teal, Warm Gold)
- **Architecture**: MVVM + Clean Architecture + Repository Pattern
- **AI Core**: Google Gemini Multimodal Vision & Language API (`gemini-2.5-flash`)
- **Local Persistence**: Room Database (SQLite with KSP code generation)
- **Concurrency & State**: Kotlin Coroutines, StateFlow, Flow
- **Network Layer**: OkHttpClient with JSON serialization
- **Testing**: Robolectric Local JVM & Roborazzi screenshot verification

---

## 🔒 Security & Secrets Management

- **API Keys**: Configured via AI Studio environment variables / BuildConfig. No private keys are hardcoded into source control.
- **Privacy**: All personal meal logs and preferences remain strictly on-device in Room storage.

---

## ⚖️ AI & Development Disclosure

- **AI Model**: Google Gemini API for visual meal component estimation and conversational dialogue.
- **Data Sources**: USDA FoodData Central, WHO Nutritional Guidelines, EFSA Dietary Reference Values.
- **Lead Developer**: Muhammad Ahsan Shahzad.
