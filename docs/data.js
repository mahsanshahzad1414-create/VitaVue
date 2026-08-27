// VitaVue International Nutrition & Evidence-Based Knowledge Base
// Replicated with exact fidelity from the Android Kotlin DataSource

const FOOD_CATEGORIES = [
  { id: "all", displayName: "All Foods", icon: "🍱" },
  { id: "Fruits", displayName: "Fruits", icon: "🍎" },
  { id: "Vegetables", displayName: "Vegetables", icon: "🥦" },
  { id: "Grains & Breads", displayName: "Grains & Breads", icon: "🌾" },
  { id: "Legumes & Pulses", displayName: "Legumes & Pulses", icon: "🫘" },
  { id: "Nuts & Seeds", displayName: "Nuts & Seeds", icon: "🥜" },
  { id: "Dairy & Alternatives", displayName: "Dairy & Alternatives", icon: "🥛" },
  { id: "Proteins & Seafood", displayName: "Proteins & Seafood", icon: "🐟" },
  { id: "Oils & Healthy Fats", displayName: "Oils & Healthy Fats", icon: "🫒" },
  { id: "Beverages", displayName: "Beverages", icon: "🍵" },
  { id: "Prepared Cultural Dishes", displayName: "Cultural Dishes", icon: "🍛" }
];

const ALL_FOODS = [
  // --- FRUITS ---
  {
    id: "fruit_apple", name: "Apple", category: "Fruits", servingSize: "1 medium (182g)",
    calories: 95, proteinGrams: 0.5, carbsGrams: 25.0, fatGrams: 0.3, fiberGrams: 4.4,
    micronutrients: [
      { name: "Vitamin C", amount: "8.4 mg", dailyValuePercent: 9, benefit: "Antioxidant & immunity" },
      { name: "Potassium", amount: "195 mg", dailyValuePercent: 4, benefit: "Electrolyte balance" },
      { name: "Quercetin", amount: "4.4 mg", dailyValuePercent: null, benefit: "Flavonoid antioxidant" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Low-Fat", "High-Fiber"],
    description: "Crisp pome fruit rich in soluble pectin fiber supporting gut microbiome diversity and steady postprandial glucose.",
    culinaryNotes: "Best eaten raw with peel intact to preserve insoluble cellulose and bioactive polyphenols.",
    culturalOrigin: "Central Asia / Global", glycemicIndex: "Low"
  },
  {
    id: "fruit_banana", name: "Banana", category: "Fruits", servingSize: "1 medium (118g)",
    calories: 105, proteinGrams: 1.3, carbsGrams: 27.0, fatGrams: 0.4, fiberGrams: 3.1,
    micronutrients: [
      { name: "Potassium", amount: "422 mg", dailyValuePercent: 9, benefit: "Neuromuscular signaling" },
      { name: "Vitamin B6", amount: "0.4 mg", dailyValuePercent: 25, benefit: "Neurotransmitter synthesis" },
      { name: "Vitamin C", amount: "10.3 mg", dailyValuePercent: 11, benefit: "Cellular defense" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Low-Fat", "Energy-Dense"],
    description: "Natural energy source rich in potassium and prebiotic resistant starch (especially when slightly green).",
    culinaryNotes: "Convenient pre-workout or breakfast carb fuel; blends smoothly in smoothies and oats.",
    culturalOrigin: "Southeast Asia / Tropical", glycemicIndex: "Medium"
  },
  {
    id: "fruit_orange", name: "Orange", category: "Fruits", servingSize: "1 medium (131g)",
    calories: 62, proteinGrams: 1.2, carbsGrams: 15.4, fatGrams: 0.2, fiberGrams: 3.1,
    micronutrients: [
      { name: "Vitamin C", amount: "69.7 mg", dailyValuePercent: 77, benefit: "Immune defense & collagen" },
      { name: "Folate (B9)", amount: "40 mcg", dailyValuePercent: 10, benefit: "Cell division" },
      { name: "Hesperidin", amount: "24 mg", dailyValuePercent: null, benefit: "Cardiovascular flavonoid" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Low-Fat", "High-Vitamin-C"],
    description: "Citrus staple renowned for ascorbic acid (vitamin C) and bioavailable hesperidin flavonoids.",
    culinaryNotes: "Eating whole slices provides dietary fiber that moderates natural fructose absorption.",
    culturalOrigin: "Southern China / Mediterranean", glycemicIndex: "Low"
  },
  {
    id: "fruit_mango", name: "Mango", category: "Fruits", servingSize: "1 cup sliced (165g)",
    calories: 99, proteinGrams: 1.4, carbsGrams: 24.7, fatGrams: 0.6, fiberGrams: 2.6,
    micronutrients: [
      { name: "Vitamin C", amount: "60 mg", dailyValuePercent: 67, benefit: "Antioxidant protection" },
      { name: "Vitamin A", amount: "89 mcg", dailyValuePercent: 10, benefit: "Eye & skin epithelial health" },
      { name: "Mangiferin", amount: "3.2 mg", dailyValuePercent: null, benefit: "Bioactive polyphenol" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Tropical"],
    description: "Aromatic stone fruit bursting with beta-carotene, vitamin C, and unique digestive amylase enzymes.",
    culinaryNotes: "Beloved across South Asia, Southeast Asia, and Latin America. Excellent in salsas and curries.",
    culturalOrigin: "South Asia", glycemicIndex: "Medium"
  },
  {
    id: "fruit_avocado", name: "Avocado", category: "Fruits", servingSize: "1/2 medium (100g)",
    calories: 160, proteinGrams: 2.0, carbsGrams: 8.5, fatGrams: 14.7, fiberGrams: 6.7,
    micronutrients: [
      { name: "Potassium", amount: "485 mg", dailyValuePercent: 10, benefit: "Blood pressure regulation" },
      { name: "Folate", amount: "81 mcg", dailyValuePercent: 20, benefit: "DNA methylation" },
      { name: "Vitamin E", amount: "2.1 mg", dailyValuePercent: 14, benefit: "Lipid membrane antioxidant" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Keto-Friendly", "High-Fiber", "Heart-Healthy"],
    description: "Botanical berry dense in monounsaturated oleic acid, lutein, and soluble/insoluble fiber.",
    culinaryNotes: "Enhances absorption of fat-soluble carotenoids (lycopene, beta-carotene) when added to salads.",
    culturalOrigin: "Mesoamerica", glycemicIndex: "Low"
  },
  {
    id: "fruit_blueberries", name: "Blueberries", category: "Fruits", servingSize: "1 cup (148g)",
    calories: 84, proteinGrams: 1.1, carbsGrams: 21.4, fatGrams: 0.5, fiberGrams: 3.6,
    micronutrients: [
      { name: "Anthocyanins", amount: "163 mg", dailyValuePercent: null, benefit: "Neuroprotective antioxidant" },
      { name: "Vitamin K1", amount: "28.6 mcg", dailyValuePercent: 24, benefit: "Coagulation & bone health" },
      { name: "Manganese", amount: "0.5 mg", dailyValuePercent: 22, benefit: "Enzymatic SOD cofactor" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Low-Glycemic", "Antioxidant-Rich"],
    description: "Wild or cultivated berry rich in anthocyanins shown in clinical trials to benefit vascular and cognitive functions.",
    culinaryNotes: "Add to morning porridge or enjoy fresh to maximize heat-sensitive polyphenol bioavailability.",
    culturalOrigin: "North America / Global", glycemicIndex: "Low"
  },
  {
    id: "fruit_strawberries", name: "Strawberries", category: "Fruits", servingSize: "1 cup (152g)",
    calories: 49, proteinGrams: 1.0, carbsGrams: 11.7, fatGrams: 0.5, fiberGrams: 3.0,
    micronutrients: [
      { name: "Vitamin C", amount: "89.4 mg", dailyValuePercent: 99, benefit: "Collagen synthesis" },
      { name: "Ellagic Acid", amount: "1.8 mg", dailyValuePercent: null, benefit: "Polyphenol protection" },
      { name: "Folate", amount: "36.5 mcg", dailyValuePercent: 9, benefit: "Cell regeneration" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Low-Calorie", "Low-Glycemic"],
    description: "Low-calorie berry providing complete daily vitamin C requirement in a single cup.",
    culinaryNotes: "Pair with dark cacao or Greek yogurt for improved satiety.",
    culturalOrigin: "Global / Europe", glycemicIndex: "Low"
  },
  {
    id: "fruit_pomegranate", name: "Pomegranate Arils", category: "Fruits", servingSize: "1/2 cup (87g)",
    calories: 72, proteinGrams: 1.5, carbsGrams: 16.3, fatGrams: 1.0, fiberGrams: 3.5,
    micronutrients: [
      { name: "Punicalagins", amount: "120 mg", dailyValuePercent: null, benefit: "Potent anti-inflammatory tannin" },
      { name: "Vitamin K", amount: "14.3 mcg", dailyValuePercent: 12, benefit: "Bone matrix mineralization" },
      { name: "Potassium", amount: "205 mg", dailyValuePercent: 4, benefit: "Vascular flexibility" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Heart-Healthy"],
    description: "Ruby jewel fruit rich in punicalagins and punicic acid that support nitric oxide endothelial dilation.",
    culinaryNotes: "Garnish for Middle Eastern pilafs, grain bowls, and crisp salads.",
    culturalOrigin: "Persia / Middle East", glycemicIndex: "Low"
  },
  {
    id: "fruit_papaya", name: "Papaya", category: "Fruits", servingSize: "1 cup cubed (145g)",
    calories: 62, proteinGrams: 0.7, carbsGrams: 15.7, fatGrams: 0.4, fiberGrams: 2.5,
    micronutrients: [
      { name: "Vitamin C", amount: "88.3 mg", dailyValuePercent: 98, benefit: "Immune booster" },
      { name: "Papain Enzyme", amount: "Active", dailyValuePercent: null, benefit: "Protein digestion aid" },
      { name: "Lycopene", amount: "2.6 mg", dailyValuePercent: null, benefit: "Carotenoid defense" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Digestive-Aid"],
    description: "Tropical digestive fruit providing papain enzymes that gently assist protein breakdown in the stomach.",
    culinaryNotes: "Squeeze fresh lime juice over ripe wedges for traditional Latin/South Asian preparation.",
    culturalOrigin: "Central America / Tropical", glycemicIndex: "Medium"
  },
  {
    id: "fruit_guava", name: "Guava", category: "Fruits", servingSize: "1 fruit (55g)",
    calories: 37, proteinGrams: 1.4, carbsGrams: 7.9, fatGrams: 0.5, fiberGrams: 3.0,
    micronutrients: [
      { name: "Vitamin C", amount: "125.6 mg", dailyValuePercent: 140, benefit: "Mega-dosed antioxidant" },
      { name: "Lycopene", amount: "2.9 mg", dailyValuePercent: null, benefit: "Prostate & skin health" },
      { name: "Potassium", amount: "229 mg", dailyValuePercent: 5, benefit: "Cell hydration" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "High-Fiber", "Superfood"],
    description: "Contains over 4x the vitamin C of an orange with high insoluble seed fiber and pink lycopene.",
    culinaryNotes: "Enjoy sliced with a pinch of pink rock salt and chili powder in South Asian and Caribbean styles.",
    culturalOrigin: "Tropical Americas / South Asia", glycemicIndex: "Low"
  },
  {
    id: "fruit_dates", name: "Medjool Dates", category: "Fruits", servingSize: "2 dates (48g)",
    calories: 133, proteinGrams: 0.9, carbsGrams: 36.0, fatGrams: 0.1, fiberGrams: 3.2,
    micronutrients: [
      { name: "Potassium", amount: "334 mg", dailyValuePercent: 7, benefit: "Endurance electrolyte" },
      { name: "Copper", amount: "0.18 mg", dailyValuePercent: 20, benefit: "Iron utilization" },
      { name: "Magnesium", amount: "26 mg", dailyValuePercent: 6, benefit: "Muscle relaxation" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Energy-Dense", "Whole-Food-Sweetener"],
    description: "Caramel-sweet desert fruit offering rapid glycogen replenishment without refined sugar additives.",
    culinaryNotes: "Stuff with walnuts or almond butter for a balanced pre-workout or post-fasting energy bite.",
    culturalOrigin: "Middle East / North Africa", glycemicIndex: "Medium"
  },
  {
    id: "fruit_watermelon", name: "Watermelon", category: "Fruits", servingSize: "1 cup diced (152g)",
    calories: 46, proteinGrams: 0.9, carbsGrams: 11.5, fatGrams: 0.2, fiberGrams: 0.6,
    micronutrients: [
      { name: "L-Citrulline", amount: "250 mg", dailyValuePercent: null, benefit: "Nitric oxide vasodilator" },
      { name: "Lycopene", amount: "6.9 mg", dailyValuePercent: null, benefit: "Cellular antioxidant" },
      { name: "Water Content", amount: "92%", dailyValuePercent: null, benefit: "Electrolyte hydration" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Hydrating", "Low-Calorie"],
    description: "Ultra-hydrating summer melon dense in L-citrulline which aids athletic recovery and circulation.",
    culinaryNotes: "Pair with fresh mint and feta in Mediterranean salads for electrolyte replenishment.",
    culturalOrigin: "North Africa", glycemicIndex: "Medium"
  },
  {
    id: "fruit_kiwi", name: "Kiwi Fruit", category: "Fruits", servingSize: "1 fruit (69g)",
    calories: 42, proteinGrams: 0.8, carbsGrams: 10.1, fatGrams: 0.4, fiberGrams: 2.1,
    micronutrients: [
      { name: "Vitamin C", amount: "64 mg", dailyValuePercent: 71, benefit: "Immunity & collagen" },
      { name: "Actinidin", amount: "Active", dailyValuePercent: null, benefit: "Proteolytic digestive enzyme" },
      { name: "Serotonin precursors", amount: "Trace", dailyValuePercent: null, benefit: "Sleep quality promotion" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Digestive-Aid"],
    description: "Emerald fruit rich in actinidin enzyme and natural antioxidants associated in clinical trials with improved sleep.",
    culinaryNotes: "Eating 2 kiwis in the evening supports circadian neurotransmitter synthesis.",
    culturalOrigin: "Eastern Asia / New Zealand", glycemicIndex: "Low"
  },
  {
    id: "fruit_grapes", name: "Red Grapes", category: "Fruits", servingSize: "1 cup (151g)",
    calories: 104, proteinGrams: 1.1, carbsGrams: 27.3, fatGrams: 0.2, fiberGrams: 1.4,
    micronutrients: [
      { name: "Resveratrol", amount: "0.3 mg", dailyValuePercent: null, benefit: "Sirtuin longevity activator" },
      { name: "Vitamin K", amount: "22 mcg", dailyValuePercent: 18, benefit: "Coagulation cascade" },
      { name: "Copper", amount: "0.19 mg", dailyValuePercent: 21, benefit: "Connective tissue" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Heart-Healthy"],
    description: "Contains resveratrol concentrated in the red/purple skin that supports microvascular circulation.",
    culinaryNotes: "Freeze for a crisp, refreshing, whole-food frozen summer treat.",
    culturalOrigin: "Near East / Mediterranean", glycemicIndex: "Low"
  },
  {
    id: "fruit_lemon", name: "Lemon & Lime Juice", category: "Fruits", servingSize: "1 fruit (48g)",
    calories: 17, proteinGrams: 0.4, carbsGrams: 5.4, fatGrams: 0.1, fiberGrams: 1.6,
    micronutrients: [
      { name: "Citric Acid", amount: "1.4 g", dailyValuePercent: null, benefit: "Kidney stone prevention" },
      { name: "Vitamin C", amount: "30.7 mg", dailyValuePercent: 34, benefit: "Plant iron absorption booster" },
      { name: "Eriocitrin", amount: "8 mg", dailyValuePercent: null, benefit: "Flavonoid antioxidant" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Alkalizing-Effect", "Low-Calorie"],
    description: "Citrus essential for increasing non-heme iron absorption from lentils and dark leafy greens by up to 300%.",
    culinaryNotes: "Squeeze over all cooked pulses, curries, and salads just before serving.",
    culturalOrigin: "Assam & Southeast Asia", glycemicIndex: "Low"
  },

  // --- VEGETABLES (15) ---
  {
    id: "veg_spinach", name: "Spinach (Palak)", category: "Vegetables", servingSize: "1 cup cooked (180g)",
    calories: 41, proteinGrams: 5.3, carbsGrams: 6.7, fatGrams: 0.5, fiberGrams: 4.3,
    micronutrients: [
      { name: "Non-Heme Iron", amount: "6.4 mg", dailyValuePercent: 36, benefit: "Oxygen transport" },
      { name: "Folate (B9)", amount: "263 mcg", dailyValuePercent: 66, benefit: "DNA synthesis & cellular growth" },
      { name: "Lutein & Zeaxanthin", amount: "20.4 mg", dailyValuePercent: null, benefit: "Retinal macular pigment" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Low-Calorie", "Iron-Rich", "Keto-Friendly"],
    description: "Nutrient-dense dark leafy green revered worldwide from Palak Paneer to Mediterranean Spanakopita.",
    culinaryNotes: "Lightly steaming or sautéing reduces insoluble oxalates and enhances lutein bioavailability.",
    culturalOrigin: "Ancient Persia / Global", glycemicIndex: "Low"
  },
  {
    id: "veg_broccoli", name: "Broccoli Florets", category: "Vegetables", servingSize: "1 cup chopped (91g)",
    calories: 31, proteinGrams: 2.6, carbsGrams: 6.0, fatGrams: 0.3, fiberGrams: 2.4,
    micronutrients: [
      { name: "Sulforaphane Precursors", amount: "Glucoraphanin", dailyValuePercent: null, benefit: "Nrf2 cellular defense" },
      { name: "Vitamin C", amount: "81.2 mg", dailyValuePercent: 90, benefit: "Antioxidant & collagen synthesis" },
      { name: "Vitamin K1", amount: "92.5 mcg", dailyValuePercent: 77, benefit: "Bone matrix regulation" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Cruciferous", "Cancer-Protective"],
    description: "Cruciferous vegetable celebrated for enzymatic conversion to sulforaphane, a master Nrf2 pathway activator.",
    culinaryNotes: "Chop 30 minutes before gentle steaming to allow myrosinase enzyme to synthesize sulforaphane.",
    culturalOrigin: "Mediterranean / Italy", glycemicIndex: "Low"
  },
  {
    id: "veg_kale", name: "Curly Kale", category: "Vegetables", servingSize: "1 cup chopped (67g)",
    calories: 33, proteinGrams: 2.9, carbsGrams: 6.0, fatGrams: 0.6, fiberGrams: 1.3,
    micronutrients: [
      { name: "Vitamin K1", amount: "547 mcg", dailyValuePercent: 450, benefit: "Coagulation & bone health" },
      { name: "Vitamin A (Carotenoids)", amount: "500 mcg", dailyValuePercent: 56, benefit: "Immune defense" },
      { name: "Calcium", amount: "100 mg", dailyValuePercent: 8, benefit: "Bioavailable plant calcium" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Superfood", "Keto-Friendly"],
    description: "Hardy brassica vegetable containing highly bioavailable non-dairy calcium with low oxalate interference.",
    culinaryNotes: "Massage raw leaves with extra virgin olive oil and lemon juice for 2 minutes to tenderize cellulose.",
    culturalOrigin: "Eastern Mediterranean / Europe", glycemicIndex: "Low"
  },
  {
    id: "veg_garlic", name: "Garlic", category: "Vegetables", servingSize: "3 cloves (9g)",
    calories: 13, proteinGrams: 0.6, carbsGrams: 3.0, fatGrams: 0.0, fiberGrams: 0.2,
    micronutrients: [
      { name: "Allicin", amount: "Bioactive", dailyValuePercent: null, benefit: "Broad-spectrum antimicrobial" },
      { name: "S-Allyl Cysteine", amount: "Active", dailyValuePercent: null, benefit: "Cardiovascular arterial elasticity" },
      { name: "Manganese", amount: "0.15 mg", dailyValuePercent: 7, benefit: "Antioxidant enzyme cofactor" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Prebiotic", "Heart-Healthy"],
    description: "Medicinal allium bulb forming allicin upon crushing, documented to support blood pressure and immunity.",
    culinaryNotes: "Crush or mince and let rest for 10 minutes before cooking to maximize allicin synthesis.",
    culturalOrigin: "Central Asia / Global", glycemicIndex: "Low"
  },
  {
    id: "veg_ginger", name: "Fresh Ginger Root", category: "Vegetables", servingSize: "1 tbsp minced (6g)",
    calories: 5, proteinGrams: 0.1, carbsGrams: 1.1, fatGrams: 0.0, fiberGrams: 0.1,
    micronutrients: [
      { name: "Gingerols (6-gingerol)", amount: "Bioactive", dailyValuePercent: null, benefit: "Anti-nausea & anti-inflammatory" },
      { name: "Shogaols", amount: "Formed upon heat", dailyValuePercent: null, benefit: "Analgesic signaling modulation" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Digestive-Aid", "Anti-Inflammatory"],
    description: "Rhizome prized in Ayurvedic, East Asian, and global traditional systems for calming gastric motility and easing joint stiffness.",
    culinaryNotes: "Simmer in hot water with lemon or use as base aromatic for curries and stir-fries.",
    culturalOrigin: "Maritime Southeast Asia / South Asia", glycemicIndex: "Low"
  },
  {
    id: "veg_bell_pepper", name: "Red Bell Pepper", category: "Vegetables", servingSize: "1 medium (119g)",
    calories: 37, proteinGrams: 1.2, carbsGrams: 7.2, fatGrams: 0.4, fiberGrams: 2.5,
    micronutrients: [
      { name: "Vitamin C", amount: "152 mg", dailyValuePercent: 169, benefit: "Supercharged antioxidant" },
      { name: "Capsanthin", amount: "Carotenoid", dailyValuePercent: null, benefit: "Lipid peroxidation inhibitor" },
      { name: "Vitamin B6", amount: "0.3 mg", dailyValuePercent: 18, benefit: "Energy metabolism" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Low-Calorie", "High-Vitamin-C"],
    description: "Contains nearly three times more ascorbic acid (vitamin C) per weight than typical citrus oranges.",
    culinaryNotes: "Stir-fry lightly with olive oil to preserve heat-sensitive vitamin C while releasing fat-soluble capsanthin.",
    culturalOrigin: "Mesoamerica / Global", glycemicIndex: "Low"
  },
  {
    id: "veg_sweet_potato", name: "Sweet Potato (Shakarkandi)", category: "Vegetables", servingSize: "1 medium baked (114g)",
    calories: 103, proteinGrams: 2.3, carbsGrams: 23.6, fatGrams: 0.2, fiberGrams: 3.8,
    micronutrients: [
      { name: "Beta-Carotene (Vitamin A)", amount: "1096 mcg", dailyValuePercent: 122, benefit: "Epithelial integrity & night vision" },
      { name: "Potassium", amount: "542 mg", dailyValuePercent: 12, benefit: "Vascular balance" },
      { name: "Manganese", amount: "0.5 mg", dailyValuePercent: 22, benefit: "Bone matrix formation" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Complex-Carb", "Okinawa-Staple"],
    description: "Slow-burning complex tuber that served as the primary caloric cornerstone of traditional centenarians in Okinawa.",
    culinaryNotes: "Bake whole with skin on and serve with a dash of cinnamon or extra virgin olive oil.",
    culturalOrigin: "Central and South America", glycemicIndex: "Medium"
  },
  {
    id: "veg_carrots", name: "Carrots", category: "Vegetables", servingSize: "1 cup chopped (128g)",
    calories: 52, proteinGrams: 1.2, carbsGrams: 12.3, fatGrams: 0.3, fiberGrams: 3.6,
    micronutrients: [
      { name: "Beta-Carotene", amount: "10,605 mcg", dailyValuePercent: 118, benefit: "Pro-vitamin A retinal synthesis" },
      { name: "Lutein", amount: "330 mcg", dailyValuePercent: null, benefit: "Ocular lens protection" },
      { name: "Biotin", amount: "6 mcg", dailyValuePercent: 20, benefit: "Keratin structural support" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Low-Calorie"],
    description: "Crunchy root vegetable providing abundant carotenoids that convert into active retinol in the liver.",
    culinaryNotes: "Light cooking and pairing with healthy lipids increases carotenoid absorption by 400%.",
    culturalOrigin: "Persia (Afghanistan / Iran)", glycemicIndex: "Low"
  },
  {
    id: "veg_tomato", name: "Tomato", category: "Vegetables", servingSize: "1 medium (123g)",
    calories: 22, proteinGrams: 1.1, carbsGrams: 4.8, fatGrams: 0.2, fiberGrams: 1.5,
    micronutrients: [
      { name: "Lycopene", amount: "3.0 mg", dailyValuePercent: null, benefit: "Prostate & vascular antioxidant" },
      { name: "Vitamin C", amount: "17 mg", dailyValuePercent: 19, benefit: "Cell defense" },
      { name: "Potassium", amount: "292 mg", dailyValuePercent: 6, benefit: "Cardiovascular health" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Low-Calorie", "Heart-Healthy"],
    description: "Botanical fruit consumed as vegetable, celebrated for lycopene which becomes significantly more bioavailable when cooked.",
    culinaryNotes: "Cooking with olive oil in sauces (like Italian Marinara or South Asian Curry gravy) maximizes trans-to-cis lycopene isomer conversion.",
    culturalOrigin: "South America / Andes", glycemicIndex: "Low"
  },
  {
    id: "veg_cauliflower", name: "Cauliflower", category: "Vegetables", servingSize: "1 cup chopped (107g)",
    calories: 27, proteinGrams: 2.1, carbsGrams: 5.3, fatGrams: 0.3, fiberGrams: 2.1,
    micronutrients: [
      { name: "Choline", amount: "47.4 mg", dailyValuePercent: 9, benefit: "Cell membrane & acetylcholine neurotransmitter" },
      { name: "Vitamin C", amount: "51.6 mg", dailyValuePercent: 57, benefit: "Immune defense" },
      { name: "Glucosinolates", amount: "Active", dailyValuePercent: null, benefit: "Hepatic Phase II detoxification" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Keto-Friendly", "Low-Carb"],
    description: "Versatile brassica flower head providing choline for cognitive function; excellent low-carb grain substitute.",
    culinaryNotes: "Roast with turmeric and cumin (Aloo Gobi style) or pulse into cauliflower rice.",
    culturalOrigin: "Cyprus / Mediterranean", glycemicIndex: "Low"
  },
  {
    id: "veg_cucumber", name: "Cucumber (Kheera)", category: "Vegetables", servingSize: "1 cup sliced (104g)",
    calories: 16, proteinGrams: 0.7, carbsGrams: 3.8, fatGrams: 0.1, fiberGrams: 0.5,
    micronutrients: [
      { name: "Silica", amount: "Bioavailable", dailyValuePercent: null, benefit: "Collagen connective tissue" },
      { name: "Cucurbitacins", amount: "Active", dailyValuePercent: null, benefit: "Anti-inflammatory signaling" },
      { name: "Water Content", amount: "95%", dailyValuePercent: null, benefit: "Intracellular hydration" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Low-Calorie", "Hydrating"],
    description: "Cooling cucurbit containing 95% structured water, silica for skin hydration, and soothing electrolytes.",
    culinaryNotes: "Core ingredient in South Asian Raita, Greek Tzatziki, and Middle Eastern Shirazi salad.",
    culturalOrigin: "Ancient India / South Asia", glycemicIndex: "Low"
  },
  {
    id: "veg_mushrooms", name: "Shiitake & Button Mushrooms", category: "Vegetables", servingSize: "1 cup sliced (70g)",
    calories: 22, proteinGrams: 2.2, carbsGrams: 2.3, fatGrams: 0.2, fiberGrams: 1.4,
    micronutrients: [
      { name: "Beta-Glucans (Lentinan)", amount: "Active", dailyValuePercent: null, benefit: "Macrophage immune priming" },
      { name: "Ergothioneine", amount: "1.2 mg", dailyValuePercent: null, benefit: "Mitochondrial longevity antioxidant" },
      { name: "Selenium", amount: "6.5 mcg", dailyValuePercent: 12, benefit: "Thyroid deiodinase enzyme" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Immune-Support", "Umami-Rich"],
    description: "Culinary fungi packed with ergothioneine ('the longevity vitamin') and fungal polysaccharides that train immune cells.",
    culinaryNotes: "Expose mushrooms to sunlight for 30 minutes prior to cooking to naturally generate dietary Vitamin D2.",
    culturalOrigin: "East Asia / Global", glycemicIndex: "Low"
  },
  {
    id: "veg_onion", name: "Red & Yellow Onion (Piyaz)", category: "Vegetables", servingSize: "1 medium (110g)",
    calories: 44, proteinGrams: 1.2, carbsGrams: 10.3, fatGrams: 0.1, fiberGrams: 1.9,
    micronutrients: [
      { name: "Quercetin", amount: "22 mg", dailyValuePercent: null, benefit: "Mast cell stabilization & anti-allergy" },
      { name: "Inulin (Prebiotic)", amount: "1.8 g", dailyValuePercent: null, benefit: "Bifidobacteria microbiome fuel" },
      { name: "Chromium", amount: "2.1 mcg", dailyValuePercent: 6, benefit: "Insulin receptor sensitization" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Prebiotic", "Aromatic"],
    description: "Fundamental aromatic vegetable providing rich stores of inulin prebiotics and quercetin bioflavonoids.",
    culinaryNotes: "Outer reddish layers contain the highest concentration of quercetin; avoid peeling too deeply.",
    culturalOrigin: "Central Asia / Global", glycemicIndex: "Low"
  },
  {
    id: "veg_bitter_gourd", name: "Bitter Gourd (Karela)", category: "Vegetables", servingSize: "1 cup cooked (124g)",
    calories: 24, proteinGrams: 1.0, carbsGrams: 5.4, fatGrams: 0.2, fiberGrams: 2.5,
    micronutrients: [
      { name: "Charantin", amount: "Bioactive", dailyValuePercent: null, benefit: "AMPK glucose uptake stimulator" },
      { name: "Polypeptide-p", amount: "Plant insulin analog", dailyValuePercent: null, benefit: "Glycemic moderation" },
      { name: "Momordicin", amount: "Bitter principle", dailyValuePercent: null, benefit: "Digestive bile secretion" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Glycemic-Control", "Ayurvedic"],
    description: "Celebrated in Ayurvedic and traditional Chinese systems for active charantin and polypeptide-p which activate cellular glucose transporters.",
    culinaryNotes: "Sauté with onions, mustard seeds, and amchur (dry mango powder) to balance intense medicinal bitterness.",
    culturalOrigin: "South Asia / Tropical", glycemicIndex: "Low"
  },
  {
    id: "veg_cabbage", name: "Green & Red Cabbage (Band Gobi)", category: "Vegetables", servingSize: "1 cup shredded (89g)",
    calories: 22, proteinGrams: 1.1, carbsGrams: 5.2, fatGrams: 0.1, fiberGrams: 2.2,
    micronutrients: [
      { name: "Glutamine", amount: "Bioactive", dailyValuePercent: null, benefit: "Intestinal mucosal barrier repair" },
      { name: "Vitamin C", amount: "32.6 mg", dailyValuePercent: 36, benefit: "Collagen integrity" },
      { name: "Vitamin K", amount: "67.6 mcg", dailyValuePercent: 56, benefit: "Vascular homeostasis" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Gut-Health", "Low-Calorie"],
    description: "Economical brassica powerhouse containing L-glutamine which fuels the rapid renewal of intestinal epithelial cells.",
    culinaryNotes: "Ferment into Sauerkraut or Kimchi to introduce billions of live probiotic lactic acid bacteria.",
    culturalOrigin: "European & Mediterranean", glycemicIndex: "Low"
  },

  // --- GRAINS & BREADS (10) ---
  {
    id: "grain_quinoa", name: "Quinoa", category: "Grains & Breads", servingSize: "1 cup cooked (185g)",
    calories: 222, proteinGrams: 8.1, carbsGrams: 39.4, fatGrams: 3.6, fiberGrams: 5.2,
    micronutrients: [
      { name: "Magnesium", amount: "118 mg", dailyValuePercent: 28, benefit: "ATP cellular energy production" },
      { name: "Complete EAAs", amount: "All 9 amino acids", dailyValuePercent: null, benefit: "Full protein spectrum" },
      { name: "Iron", amount: "2.8 mg", dailyValuePercent: 15, benefit: "Hemoglobin synthesis" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Complete-Protein", "Ancient-Grain"],
    description: "Andean pseudo-cereal containing all 9 essential amino acids in optimal human physiological ratios.",
    culinaryNotes: "Rinse thoroughly to remove bitter outer saponin layer; cook 1:2 in water for fluffy nutty grains.",
    culturalOrigin: "Andes Mountains (Bolivia/Peru)", glycemicIndex: "Low"
  },
  {
    id: "grain_oats", name: "Rolled Oats (Jowar/Dalia)", category: "Grains & Breads", servingSize: "1/2 cup dry (40g)",
    calories: 150, proteinGrams: 5.0, carbsGrams: 27.0, fatGrams: 3.0, fiberGrams: 4.0,
    micronutrients: [
      { name: "Beta-Glucan", amount: "2.0 g", dailyValuePercent: null, benefit: "Lowers LDL blood cholesterol" },
      { name: "Avenanthramides", amount: "Polyphenol", dailyValuePercent: null, benefit: "Endothelial anti-itch and vascular calm" },
      { name: "Manganese", amount: "1.4 mg", dailyValuePercent: 61, benefit: "Antioxidant enzyme defenses" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Heart-Healthy", "High-Fiber"],
    description: "Whole grain dense in viscous beta-glucan soluble fiber that binds bile acids and stabilizes morning glucose.",
    culinaryNotes: "Prepare as overnight oats with chia and berries or cook into savory spiced South Asian Khichdi-style oats.",
    culturalOrigin: "Fertile Crescent / Northern Europe", glycemicIndex: "Low"
  },
  {
    id: "grain_brown_rice", name: "Brown Basmati Rice", category: "Grains & Breads", servingSize: "1 cup cooked (195g)",
    calories: 216, proteinGrams: 5.0, carbsGrams: 44.8, fatGrams: 1.8, fiberGrams: 3.5,
    micronutrients: [
      { name: "Manganese", amount: "1.8 mg", dailyValuePercent: 78, benefit: "Mitochondrial superoxide dismutase" },
      { name: "Selenium", amount: "19 mcg", dailyValuePercent: 35, benefit: "Thyroid hormone activation" },
      { name: "Magnesium", amount: "84 mg", dailyValuePercent: 20, benefit: "Neuromuscular function" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Whole-Grain"],
    description: "Long-grain aromatic rice retaining the nutritious bran germ and aleurone layer for sustained glycemic release.",
    culinaryNotes: "Soak for 30 minutes before cooking with green cardamom, cloves, and bay leaf for authentic royal aroma.",
    culturalOrigin: "Indian Subcontinent (Indus Valley)", glycemicIndex: "Medium"
  },
  {
    id: "grain_whole_wheat_roti", name: "Whole Wheat Roti / Chapati (Chakki Atta)", category: "Grains & Breads", servingSize: "1 roti (40g)",
    calories: 110, proteinGrams: 3.8, carbsGrams: 22.0, fatGrams: 0.6, fiberGrams: 3.5,
    micronutrients: [
      { name: "Insoluble Fiber", amount: "3.2 g", dailyValuePercent: 12, benefit: "Colonic motility and fullness" },
      { name: "Thiamine (B1)", amount: "0.15 mg", dailyValuePercent: 13, benefit: "Carbohydrate energy utilization" },
      { name: "Phosphorus", amount: "98 mg", dailyValuePercent: 8, benefit: "Cellular membrane phospholipids" }
    ],
    dietaryTags: ["Vegan", "Whole-Grain", "Traditional-Staple"],
    description: "Stone-ground (100% Chakki) unrefined flatbread containing natural bran fiber that slows digestion.",
    culinaryNotes: "Cook on a heavy cast-iron tawa and puff over flame without oil; optionally brush with 1/2 tsp desi ghee.",
    culturalOrigin: "South Asia / Ancient Harappa", glycemicIndex: "Medium"
  },
  {
    id: "grain_sourdough", name: "Artisan Sourdough Bread", category: "Grains & Breads", servingSize: "1 slice (50g)",
    calories: 120, proteinGrams: 4.5, carbsGrams: 24.0, fatGrams: 0.8, fiberGrams: 1.8,
    micronutrients: [
      { name: "Lactic Acid", amount: "Fermentation product", dailyValuePercent: null, benefit: "Reduces glycemic peak" },
      { name: "Degraded Phytates", amount: "70% broken down", dailyValuePercent: null, benefit: "Increases zinc & magnesium absorption" }
    ],
    dietaryTags: ["Vegan", "Slow-Fermented", "Gut-Friendly"],
    description: "Slow-fermented wild yeast loaf whose lactic acid bacteria pre-digest gluten proteins and neutralize mineral-binding phytic acid.",
    culinaryNotes: "Toast and top with smashed avocado, poached eggs, or extra virgin olive oil and za'atar.",
    culturalOrigin: "Ancient Egypt / Mediterranean", glycemicIndex: "Medium"
  },
  {
    id: "grain_barley", name: "Pearl & Hulled Barley (Jau)", category: "Grains & Breads", servingSize: "1 cup cooked (157g)",
    calories: 193, proteinGrams: 3.6, carbsGrams: 44.3, fatGrams: 0.7, fiberGrams: 6.0,
    micronutrients: [
      { name: "Beta-Glucan", amount: "2.5 g", dailyValuePercent: null, benefit: "Metabolic and lipid regulator" },
      { name: "Selenium", amount: "13.5 mcg", dailyValuePercent: 25, benefit: "Antioxidant enzyme defenses" },
      { name: "Niacin (B3)", amount: "3.2 mg", dailyValuePercent: 20, benefit: "DNA repair & mitochondrial NAD+" }
    ],
    dietaryTags: ["Vegan", "High-Fiber", "Ancient-Grain"],
    description: "Ancient grain boasting the lowest glycemic index among common cereals and rich in soluble beta-glucan.",
    culinaryNotes: "Add to hearty vegetable soups, winter stews, or brew as cooling South Asian Jau Sattu water.",
    culturalOrigin: "Fertile Crescent", glycemicIndex: "Low"
  },
  {
    id: "grain_buckwheat", name: "Buckwheat (Kuttu)", category: "Grains & Breads", servingSize: "1 cup cooked (168g)",
    calories: 155, proteinGrams: 5.7, carbsGrams: 33.5, fatGrams: 1.0, fiberGrams: 4.5,
    micronutrients: [
      { name: "Rutin", amount: "Bioactive", dailyValuePercent: null, benefit: "Strengthens capillary walls" },
      { name: "D-Chiro-Inositol", amount: "Bioactive", dailyValuePercent: null, benefit: "Improves insulin sensitivity in PCOS" },
      { name: "Magnesium", amount: "86 mg", dailyValuePercent: 20, benefit: "Glycemic cofactor" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Low-Glycemic", "Heart-Healthy"],
    description: "Gluten-free seed grain dense in rutin and D-chiro-inositol, traditionally consumed during South Asian Navratri fasts.",
    culinaryNotes: "Use buckwheat flour for galettes, soba noodles, or make hearty porridge.",
    culturalOrigin: "Southeast Asia / Central Asia", glycemicIndex: "Low"
  },
  {
    id: "grain_millet", name: "Foxtail & Pearl Millet (Bajra/Kangni)", category: "Grains & Breads", servingSize: "1 cup cooked (174g)",
    calories: 207, proteinGrams: 6.1, carbsGrams: 41.2, fatGrams: 1.7, fiberGrams: 2.3,
    micronutrients: [
      { name: "Iron", amount: "3.0 mg", dailyValuePercent: 17, benefit: "Oxygen transport" },
      { name: "Zinc", amount: "1.7 mg", dailyValuePercent: 15, benefit: "Immune defense & protein synthesis" },
      { name: "Phosphorus", amount: "174 mg", dailyValuePercent: 14, benefit: "Bone matrix formation" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Climate-Resilient", "Ancient-Grain"],
    description: "Drought-hardy super-grain high in resistant starch and iron, celebrated by the UN International Year of Millets.",
    culinaryNotes: "Bajra roti paired with sarson ka saag or spiced lentil soup is a winter nutritional powerhouse.",
    culturalOrigin: "African Sahel & Indus Valley", glycemicIndex: "Low"
  },
  {
    id: "grain_chia_seeds", name: "Chia Seeds", category: "Nuts & Seeds", servingSize: "2 tbsp (28g)",
    calories: 138, proteinGrams: 4.7, carbsGrams: 12.0, fatGrams: 8.7, fiberGrams: 9.8,
    micronutrients: [
      { name: "ALA Omega-3", amount: "5.0 g", dailyValuePercent: 312, benefit: "Anti-inflammatory plant lipid" },
      { name: "Calcium", amount: "179 mg", dailyValuePercent: 14, benefit: "Plant-based bone mineral" },
      { name: "Phosphorus", amount: "244 mg", dailyValuePercent: 20, benefit: "Cellular phosphorylation" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "High-Fiber", "Omega-3", "Keto-Friendly"],
    description: "Hydrophilic seeds that absorb up to 12x their weight in water, forming a soothing digestive mucilage gel.",
    culinaryNotes: "Soak in almond or oat milk with pure vanilla for a slow-digesting chia pudding.",
    culturalOrigin: "Mesoamerica (Aztec/Mayan)", glycemicIndex: "Low"
  },
  {
    id: "grain_flaxseeds", name: "Ground Flaxseeds (Alsi)", category: "Nuts & Seeds", servingSize: "2 tbsp (14g)",
    calories: 75, proteinGrams: 2.6, carbsGrams: 4.0, fatGrams: 6.0, fiberGrams: 3.8,
    micronutrients: [
      { name: "Lignans (SDG)", amount: "40 mg", dailyValuePercent: null, benefit: "Phytoestrogen hormone balancer" },
      { name: "ALA Omega-3", amount: "3.2 g", dailyValuePercent: 200, benefit: "Cardiovascular inflammation defense" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Hormone-Health", "Omega-3"],
    description: "Richest known dietary source of plant lignans (SDG), which support estrogen metabolism and vascular elasticity.",
    culinaryNotes: "Must be consumed ground (flaxseed meal) rather than whole to enable intestinal absorption of internal lipids.",
    culturalOrigin: "Fertile Crescent / Ancient Egypt", glycemicIndex: "Low"
  },

  // --- LEGUMES & PULSES (10) ---
  {
    id: "legume_lentils", name: "Red & Brown Lentils (Masoor/Moong Dal)", category: "Legumes & Pulses", servingSize: "1 cup cooked (198g)",
    calories: 230, proteinGrams: 17.9, carbsGrams: 39.9, fatGrams: 0.8, fiberGrams: 15.6,
    micronutrients: [
      { name: "Folate (B9)", amount: "358 mcg", dailyValuePercent: 90, benefit: "Neural development & DNA synthesis" },
      { name: "Non-Heme Iron", amount: "6.6 mg", dailyValuePercent: 37, benefit: "Red blood cell production" },
      { name: "Potassium", amount: "731 mg", dailyValuePercent: 16, benefit: "Blood pressure regulation" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "High-Protein", "High-Fiber", "Heart-Healthy"],
    description: "Foundational pulse of global civilization providing nearly 18g protein and 16g fiber per cooked cup.",
    culinaryNotes: "Cook with turmeric and cumin tadka, finish with fresh lemon juice to amplify iron bioavailability.",
    culturalOrigin: "Fertile Crescent / South Asia", glycemicIndex: "Low"
  },
  {
    id: "legume_chickpeas", name: "Chickpeas / Garbanzo Beans (Chana)", category: "Legumes & Pulses", servingSize: "1 cup cooked (164g)",
    calories: 269, proteinGrams: 14.5, carbsGrams: 45.0, fatGrams: 4.2, fiberGrams: 12.5,
    micronutrients: [
      { name: "Manganese", amount: "1.7 mg", dailyValuePercent: 74, benefit: "Antioxidant enzyme defenses" },
      { name: "Folate", amount: "282 mcg", dailyValuePercent: 71, benefit: "Cellular regeneration" },
      { name: "Tryptophan", amount: "150 mg", dailyValuePercent: null, benefit: "Serotonin and melatonin precursor" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "High-Protein", "High-Fiber"],
    description: "Versatile legume cornerstone of Mediterranean Hummus, Middle Eastern Falafel, and South Asian Chana Masala.",
    culinaryNotes: "Simmer with bay leaves and garlic; save chickpea cooking liquid (aquafaba) as vegan egg-white alternative.",
    culturalOrigin: "Middle East / Turkey", glycemicIndex: "Low"
  },
  {
    id: "legume_black_beans", name: "Black Turtle Beans", category: "Legumes & Pulses", servingSize: "1 cup cooked (172g)",
    calories: 227, proteinGrams: 15.2, carbsGrams: 40.8, fatGrams: 0.9, fiberGrams: 15.0,
    micronutrients: [
      { name: "Anthocyanins", amount: "Pigments", dailyValuePercent: null, benefit: "Vascular endothelial defense" },
      { name: "Molybdenum", amount: "130 mcg", dailyValuePercent: 289, benefit: "Sulfur amino acid detoxification" },
      { name: "Magnesium", amount: "120 mg", dailyValuePercent: 29, benefit: "Glycemic and nerve regulator" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "High-Fiber", "Antioxidant-Rich"],
    description: "Dark-pigmented legume revered in Latin American cuisines for anthocyanin content matching blackberries.",
    culinaryNotes: "Simmer with cumin, oregano, and sweet bell peppers; serve with brown rice for a complete amino acid profile.",
    culturalOrigin: "Mesoamerica / South America", glycemicIndex: "Low"
  },
  {
    id: "legume_kidney_beans", name: "Kidney Beans (Rajma)", category: "Legumes & Pulses", servingSize: "1 cup cooked (177g)",
    calories: 225, proteinGrams: 15.3, carbsGrams: 40.4, fatGrams: 0.9, fiberGrams: 13.1,
    micronutrients: [
      { name: "Iron", amount: "5.2 mg", dailyValuePercent: 29, benefit: "Cellular energy synthesis" },
      { name: "Copper", amount: "0.4 mg", dailyValuePercent: 44, benefit: "Superoxide dismutase cofactor" },
      { name: "Resistant Starch", amount: "4.8 g", dailyValuePercent: null, benefit: "Colonic butyrate generation" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "High-Protein", "High-Fiber"],
    description: "Hearty red bean renowned in South Asian Rajma Chawal and American Creole red beans and rice.",
    culinaryNotes: "Must be soaked and boiled vigorously for at least 10 minutes to completely deactivate phytohaemagglutinin lectins.",
    culturalOrigin: "Central & South America", glycemicIndex: "Low"
  },
  {
    id: "legume_edamame", name: "Edamame & Organic Tofu", category: "Legumes & Pulses", servingSize: "1 cup shelled (155g)",
    calories: 188, proteinGrams: 18.5, carbsGrams: 13.8, fatGrams: 8.1, fiberGrams: 8.1,
    micronutrients: [
      { name: "Soy Isoflavones (Genistein)", amount: "35 mg", dailyValuePercent: null, benefit: "Cardiometabolic & lipid modulator" },
      { name: "Complete EAAs", amount: "Full score", dailyValuePercent: null, benefit: "Complete muscle protein synthesis" },
      { name: "Folate", amount: "482 mcg", dailyValuePercent: 120, benefit: "Cell division and methylation" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Complete-Protein", "High-Protein"],
    description: "Young green soybeans providing a complete plant protein spectrum with high digestibility and beneficial isoflavones.",
    culinaryNotes: "Steam in pods with sea salt for a snack or press firm tofu and sear with ginger-soy glaze.",
    culturalOrigin: "East Asia (China/Japan)", glycemicIndex: "Low"
  },

  // --- PROTEINS & SEAFOOD (8) ---
  {
    id: "protein_salmon", name: "Wild Alaskan Salmon", category: "Proteins & Seafood", servingSize: "3.5 oz / 100g cooked",
    calories: 182, proteinGrams: 25.4, carbsGrams: 0.0, fatGrams: 8.1, fiberGrams: 0.0,
    micronutrients: [
      { name: "EPA & DHA Omega-3", amount: "2,260 mg", dailyValuePercent: 141, benefit: "Brain lipid structure & anti-inflammation" },
      { name: "Vitamin D3", amount: "570 IU", dailyValuePercent: 71, benefit: "Bone matrix & immune modulation" },
      { name: "Astaxanthin", amount: "3.5 mg", dailyValuePercent: null, benefit: "Marine carotenoid antioxidant" },
      { name: "Vitamin B12", amount: "3.2 mcg", dailyValuePercent: 133, benefit: "Nerve myelin sheath integrity" }
    ],
    dietaryTags: ["Pescatarian", "Gluten-Free", "Keto-Friendly", "Omega-3", "High-Protein"],
    description: "Cold-water fatty fish prized for marine omega-3 lipids (EPA/DHA) and pink astaxanthin carotenoid.",
    culinaryNotes: "Pan-sear with skin-on in a touch of olive oil, seasoned with sea salt, lemon, and fresh dill.",
    culturalOrigin: "North Pacific / Scandinavia", glycemicIndex: "Low"
  },
  {
    id: "protein_eggs", name: "Pasture-Raised Eggs", category: "Proteins & Seafood", servingSize: "2 large eggs (100g)",
    calories: 144, proteinGrams: 12.6, carbsGrams: 0.8, fatGrams: 9.9, fiberGrams: 0.0,
    micronutrients: [
      { name: "Choline", amount: "294 mg", dailyValuePercent: 53, benefit: "Acetylcholine neurotransmitter & liver export" },
      { name: "Lutein & Zeaxanthin", amount: "500 mcg", dailyValuePercent: null, benefit: "Highly bioavailable eye protection" },
      { name: "Vitamin B12", amount: "1.8 mcg", dailyValuePercent: 75, benefit: "Red cell maturation" },
      { name: "Leucine", amount: "1.1 g", dailyValuePercent: null, benefit: "Muscle synthesis trigger" }
    ],
    dietaryTags: ["Vegetarian", "Gluten-Free", "Keto-Friendly", "High-Protein", "Complete-Protein"],
    description: "Nature's nutrient multivitamin providing the gold standard reference protein score (DIAAS > 1.1) and essential brain choline.",
    culinaryNotes: "Soft-boiling or poaching preserves delicate yolk lipids and prevents cholesterol oxidation.",
    culturalOrigin: "Global / Southeast Asia", glycemicIndex: "Low"
  },
  {
    id: "protein_chicken_breast", name: "Skinless Chicken Breast", category: "Proteins & Seafood", servingSize: "3.5 oz / 100g cooked",
    calories: 165, proteinGrams: 31.0, carbsGrams: 0.0, fatGrams: 3.6, fiberGrams: 0.0,
    micronutrients: [
      { name: "Niacin (B3)", amount: "13.7 mg", dailyValuePercent: 86, benefit: "Cellular energy conversion" },
      { name: "Vitamin B6", amount: "0.6 mg", dailyValuePercent: 35, benefit: "Amino acid transamination" },
      { name: "Phosphorus", amount: "228 mg", dailyValuePercent: 23, benefit: "Bone structure & ATP" }
    ],
    dietaryTags: ["High-Protein", "Low-Fat", "Keto-Friendly", "Gluten-Free"],
    description: "Ultra-lean high-density animal protein delivering 31g of bioavailable amino acids per 100g serving.",
    culinaryNotes: "Marinate in yogurt, lemon, garlic, and ginger (Tandoori/Tikka style) to keep moisture without adding saturated fats.",
    culturalOrigin: "Global / Indus Valley", glycemicIndex: "Low"
  },
  {
    id: "protein_greek_yogurt", name: "Plain Greek Yogurt (0% or 2%)", category: "Dairy & Alternatives", servingSize: "1 cup (200g)",
    calories: 146, proteinGrams: 20.0, carbsGrams: 7.8, fatGrams: 3.8, fiberGrams: 0.0,
    micronutrients: [
      { name: "Calcium", amount: "230 mg", dailyValuePercent: 18, benefit: "Bone mineral density" },
      { name: "Probiotic Cultures", amount: "Billions CFU", dailyValuePercent: null, benefit: "L. bulgaricus & S. thermophilus" },
      { name: "Vitamin B12", amount: "1.0 mcg", dailyValuePercent: 42, benefit: "Neurological health" }
    ],
    dietaryTags: ["Vegetarian", "Gluten-Free", "High-Protein", "Probiotic", "Keto-Friendly"],
    description: "Strained cultured dairy with whey removed, concentrating casein and bioactive peptides with twice the protein of normal yogurt.",
    culinaryNotes: "Top with raw walnuts, blueberries, and a drizzle of raw honey for an optimal protein-antioxidant bowl.",
    culturalOrigin: "Eastern Mediterranean / Greece", glycemicIndex: "Low"
  },
  {
    id: "protein_sardines", name: "Sardines in Olive Oil", category: "Proteins & Seafood", servingSize: "1 can drained (85g)",
    calories: 177, proteinGrams: 21.0, carbsGrams: 0.0, fatGrams: 9.8, fiberGrams: 0.0,
    micronutrients: [
      { name: "Calcium (Soft Bones)", amount: "325 mg", dailyValuePercent: 25, benefit: "Skeletal scaffolding" },
      { name: "Omega-3 (EPA/DHA)", amount: "1,500 mg", dailyValuePercent: 94, benefit: "Cardiovascular health" },
      { name: "Vitamin B12", amount: "7.6 mcg", dailyValuePercent: 317, benefit: "Nerve myelin integrity" }
    ],
    dietaryTags: ["Pescatarian", "Gluten-Free", "Keto-Friendly", "High-Protein", "Omega-3", "Sustainable"],
    description: "Small sustainable cold-water fish at the bottom of the marine trophic pyramid, virtually free of heavy metal accumulation.",
    culinaryNotes: "Mash on whole grain sourdough toast with lemon juice, cracked black pepper, and capers.",
    culturalOrigin: "Mediterranean / Atlantic", glycemicIndex: "Low"
  },

  // --- NUTS, SEEDS & OILS (8) ---
  {
    id: "nut_walnuts", name: "English Walnuts", category: "Nuts & Seeds", servingSize: "1 oz / 14 halves (28g)",
    calories: 185, proteinGrams: 4.3, carbsGrams: 3.9, fatGrams: 18.5, fiberGrams: 1.9,
    micronutrients: [
      { name: "ALA Omega-3", amount: "2.5 g", dailyValuePercent: 156, benefit: "Brain lipid structure" },
      { name: "Polyphenols (Pedunculagin)", amount: "Bioactive", dailyValuePercent: null, benefit: "Microvascular elasticity" },
      { name: "Copper", amount: "0.45 mg", dailyValuePercent: 50, benefit: "Collagen & elastin cross-linking" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Keto-Friendly", "Omega-3", "Brain-Health"],
    description: "Brain-shaped nut uniquely high in alpha-linolenic acid (ALA) and ellagitannin polyphenols that support vascular tone.",
    culinaryNotes: "Store in refrigerator to prevent polyunsaturated fats from oxidizing; toss into salads or morning porridge.",
    culturalOrigin: "Persia / Central Asia", glycemicIndex: "Low"
  },
  {
    id: "nut_almonds", name: "Raw Almonds (Badam)", category: "Nuts & Seeds", servingSize: "1 oz / 23 almonds (28g)",
    calories: 164, proteinGrams: 6.0, carbsGrams: 6.1, fatGrams: 14.2, fiberGrams: 3.5,
    micronutrients: [
      { name: "Vitamin E (Alpha-tocopherol)", amount: "7.3 mg", dailyValuePercent: 49, benefit: "Master lipid antioxidant" },
      { name: "Magnesium", amount: "76 mg", dailyValuePercent: 18, benefit: "Muscle & nerve homeostasis" },
      { name: "Riboflavin (B2)", amount: "0.3 mg", dailyValuePercent: 23, benefit: "Mitochondrial energy" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Keto-Friendly", "Heart-Healthy"],
    description: "Dense in alpha-tocopherol (vitamin E) that protects LDL particles and cell membranes from oxidative stress.",
    culinaryNotes: "Ayurvedic tradition recommends soaking in water overnight and peeling outer skins for enhanced digestive ease.",
    culturalOrigin: "Levant & South Asia", glycemicIndex: "Low"
  },
  {
    id: "oil_evoo", name: "Extra Virgin Olive Oil (EVOO)", category: "Oils & Healthy Fats", servingSize: "1 tbsp (15 ml)",
    calories: 119, proteinGrams: 0.0, carbsGrams: 0.0, fatGrams: 13.5, fiberGrams: 0.0,
    micronutrients: [
      { name: "Oleocanthal", amount: "Polyphenol", dailyValuePercent: null, benefit: "Natural COX-1/COX-2 enzyme inhibitor" },
      { name: "Oleic Acid (Omega-9)", amount: "10 g", dailyValuePercent: null, benefit: "Endothelial nitric oxide enhancement" },
      { name: "Vitamin E", amount: "1.9 mg", dailyValuePercent: 13, benefit: "Membrane defense" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Keto-Friendly", "Heart-Healthy", "Anti-Inflammatory"],
    description: "Cold-extracted olive fruit juice rich in oleocanthal, an anti-inflammatory polyphenol providing the hallmark peppery throat sensation.",
    culinaryNotes: "Use generously as a raw dressing or finishing oil over warm dishes to preserve volatile aroma compounds.",
    culturalOrigin: "Mediterranean Basin", glycemicIndex: "Low"
  },
  {
    id: "nut_pumpkin_seeds", name: "Pumpkin Seeds (Pepitas)", category: "Nuts & Seeds", servingSize: "1 oz (28g)",
    calories: 151, proteinGrams: 7.0, carbsGrams: 5.0, fatGrams: 13.0, fiberGrams: 1.7,
    micronutrients: [
      { name: "Magnesium", amount: "150 mg", dailyValuePercent: 37, benefit: "Over 300 biochemical enzymes" },
      { name: "Zinc", amount: "2.2 mg", dailyValuePercent: 20, benefit: "Immunity and testosterone pathways" },
      { name: "Phytosterols", amount: "75 mg", dailyValuePercent: null, benefit: "Cholesterol absorption inhibition" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Keto-Friendly", "Mineral-Dense"],
    description: "One of the most magnesium- and zinc-dense plant foods on the planet, crucial for cellular energy and hormonal balance.",
    culinaryNotes: "Toast lightly on dry skillet with sea salt, smoked paprika, and lime juice.",
    culturalOrigin: "Mesoamerica (Olmec/Mayan)", glycemicIndex: "Low"
  },

  // --- BEVERAGES & HEALTHY HERBALS (6) ---
  {
    id: "bev_green_tea", name: "Matcha & Sencha Green Tea", category: "Beverages", servingSize: "1 cup brewed (240 ml)",
    calories: 2, proteinGrams: 0.2, carbsGrams: 0.4, fatGrams: 0.0, fiberGrams: 0.0,
    micronutrients: [
      { name: "EGCG (Epigallocatechin Gallate)", amount: "150-300 mg", dailyValuePercent: null, benefit: "Potent metabolic & cellular antioxidant" },
      { name: "L-Theanine", amount: "25 mg", dailyValuePercent: null, benefit: "Promotes alpha brain waves and calm focus" },
      { name: "Natural Caffeine", amount: "30-50 mg", dailyValuePercent: null, benefit: "Sustained alertness without jitters" }
    ],
    dietaryTags: ["Vegan", "Gluten-Free", "Zero-Calorie", "Nootropic", "Longevity"],
    description: "Rich in EGCG catechins and L-theanine which synergize to promote relaxed yet acute cognitive focus.",
    culinaryNotes: "Brew water at 70-80°C (not boiling) for 2-3 minutes to extract catechins without harsh bitter tannins.",
    culturalOrigin: "China & Japan", glycemicIndex: "Low"
  },
  {
    id: "bev_turmeric_latte", name: "Turmeric Golden Milk (Haldi Doodh)", category: "Beverages", servingSize: "1 cup (240 ml)",
    calories: 120, proteinGrams: 4.0, carbsGrams: 10.0, fatGrams: 6.0, fiberGrams: 1.0,
    micronutrients: [
      { name: "Curcuminoids", amount: "200 mg", dailyValuePercent: null, benefit: "Downregulates NF-kB inflammatory cascade" },
      { name: "Piperine (Black Pepper)", amount: "Active", dailyValuePercent: null, benefit: "Boosts curcumin absorption by 2000%" },
      { name: "Calcium", amount: "250 mg", dailyValuePercent: 20, benefit: "Skeletal support" }
    ],
    dietaryTags: ["Vegetarian", "Gluten-Free", "Ayurvedic", "Anti-Inflammatory"],
    description: "Ancient Indian restorative elixir combining turmeric, black pepper (piperine), and lipids for maximum curcumin bioavailability.",
    culinaryNotes: "Warm gently with grass-fed dairy or coconut milk, a pinch of black pepper, and cinnamon.",
    culturalOrigin: "Vedic India / South Asia", glycemicIndex: "Low"
  }
];

const ARTICLE_CATEGORIES = [
  { id: "all", title: "All Disciplines", icon: "📚" },
  { id: "FUNDAMENTALS", title: "Fundamentals", icon: "🧬" },
  { id: "MACRONUTRIENTS", title: "Macronutrients", icon: "🥩" },
  { id: "VITAMINS", title: "Vitamins", icon: "🍊" },
  { id: "MINERALS", title: "Minerals", icon: "⚡" },
  { id: "PRACTICAL", title: "Practical Nutrition", icon: "🍽️" },
  { id: "PATTERNS", title: "Dietary Patterns", icon: "🥗" },
  { id: "ACTIVE", title: "Active Lifestyle", icon: "🏃" },
  { id: "LIFE_STAGES", title: "Life-Stage Nutrition", icon: "🌱" }
];

const ALL_ARTICLES = [
  {
    id: "art_protein_mastery",
    slug: "protein-mastery-science",
    title: "Protein Fundamentals: Muscle Synthesis, Satiety & Bioavailability",
    summary: "Explore the biochemistry of amino acids, the leucine trigger threshold, and how to optimize daily protein distribution across diverse cuisines.",
    category: "MACRONUTRIENTS",
    readingTimeMin: 6,
    difficulty: "Intermediate",
    sections: [
      {
        heading: "The Role of Essential Amino Acids",
        content: "Proteins are polymer chains constructed from 20 amino acids, 9 of which are essential (EAAs) because the human body lacks the enzymatic machinery to synthesize them de novo. These amino acids serve not only as muscular building blocks but as structural collagen, immune immunoglobulins, enzymatic catalysts, and peptide hormones."
      },
      {
        heading: "The Leucine Trigger & Muscle Protein Synthesis",
        content: "Among the branched-chain amino acids, L-Leucine acts as the primary molecular trigger for the mTORC1 (mechanistic target of rapamycin) kinase pathway. To initiate peak muscle protein synthesis in adults, a single meal should generally deliver between 2.2g and 3.0g of leucine, typically found in 25–35g of high-quality animal protein or 35–45g of complementary plant protein blends."
      },
      {
        heading: "Plant Protein Complementarity in Practice",
        content: "Contrary to outdated theories, plant-based eaters do not need to combine specific proteins in every single bite. The human body maintains a dynamic circulating amino acid pool. Pairing legumes (rich in lysine, low in methionine) with cereal grains (rich in methionine, low in lysine)—such as South Asian Dal with Roti, Mexican Rice with Black Beans, or Middle Eastern Hummus with Pita—across the day provides a complete amino acid score."
      }
    ],
    keyTakeaways: [
      "Aim for 1.2 to 1.8 grams of protein per kilogram of body weight for active wellness.",
      "Distribute protein intake evenly across 3 to 4 meals (25-35g per meal) to maximize synthesis.",
      "Combine diverse grains, legumes, seeds, and nuts to achieve complete essential amino acid profiles."
    ]
  },
  {
    id: "art_fiber_gut_microbiome",
    slug: "fiber-gut-microbiome",
    title: "Dietary Fiber & The Gut Microbiome: Beyond Regularity",
    summary: "Understand soluble, insoluble, and prebiotic resistant starches, and how short-chain fatty acids (SCFAs) regulate immunity and brain chemistry.",
    category: "MACRONUTRIENTS",
    readingTimeMin: 7,
    difficulty: "Intermediate",
    sections: [
      {
        heading: "The Fiber Spectrum: Soluble vs. Insoluble",
        content: "Dietary fiber represents non-digestible carbohydrates that transit through the upper gastrointestinal tract intact. Insoluble fiber (cellulose, lignin in wheat bran and vegetable skins) adds mechanical bulk and accelerates transit. Soluble fiber (pectin in apples, beta-glucan in oats, mucilage in chia) dissolves in water to form a viscous gel that traps cholesterol and moderates postprandial blood glucose spikes."
      },
      {
        heading: "Fermentation & Short-Chain Fatty Acids (SCFAs)",
        content: "When prebiotic fiber reaches the distal colon, anaerobic commensal microbes ferment it into powerful short-chain fatty acids: acetate, propionate, and butyrate. Butyrate serves as the primary metabolic fuel for colonic epithelial cells, sealing tight junctions to prevent gut permeability and suppressing systemic inflammation."
      },
      {
        heading: "The Microbiota-Gut-Brain Axis",
        content: "Over 90% of the body's serotonin receptors and significant vagal nerve signaling pathways reside in the enteric nervous system. By supporting a diverse microbial ecosystem with 30+ distinct plant species per week, dietary fiber directly influences mood, stress resilience, and neurocognitive clarity."
      }
    ],
    keyTakeaways: [
      "Target at least 28–38 grams of total dietary fiber per day from whole foods.",
      "Aim for 'Plant Diversity': consuming 30 distinct plant types per week optimizes microbial richness.",
      "Increase fiber intake gradually while increasing water consumption to avoid transient digestive gas."
    ]
  },
  {
    id: "art_healthy_fats_lipids",
    slug: "healthy-fats-lipids",
    title: "The Lipid Blueprint: Omega-3s, Monounsaturates & Cellular Fluidity",
    summary: "Demystifying saturated, monounsaturated, and polyunsaturated fats, with an emphasis on EPA/DHA marine lipids and anti-inflammatory pathways.",
    category: "MACRONUTRIENTS",
    readingTimeMin: 5,
    difficulty: "Beginner",
    sections: [
      {
        heading: "Lipid Structure & Biological Function",
        content: "Fats are concentrated energy sources yielding 9 kcal/gram, but their most vital role is architectural: forming the lipid bilayer of trillions of human cell membranes, insulating myelin nerve sheaths, and acting as precursors for prostaglandins and steroid hormones."
      },
      {
        heading: "The Omega-6 to Omega-3 Balance",
        content: "Modern diets frequently exhibit an inflammatory Omega-6 to Omega-3 ratio of 16:1 or higher, primarily driven by ultra-refined seed oils. Restoring a ratio closer to 3:1 through cold-water fatty fish (salmon, sardines, mackerel), chia seeds, flaxseeds, and walnuts dampens systemic inflammatory cytokines."
      },
      {
        heading: "Monounsaturated Oleic Acid in Culinary Traditions",
        content: "Extra virgin olive oil and avocados are dense in oleic acid (Omega-9), which is resistant to thermal oxidation and enhances endothelial nitric oxide synthesis, keeping arteries flexible and healthy."
      }
    ],
    keyTakeaways: [
      "Replace highly refined industrial seed oils with extra virgin olive oil, avocado, and nuts.",
      "Consume 2 servings of fatty cold-water fish weekly or take an algae-derived EPA/DHA supplement.",
      "Store delicate omega-3 rich oils and ground flaxseeds in airtight, refrigerated containers."
    ]
  },
  {
    id: "art_micronutrient_powerhouses",
    slug: "essential-vitamins-and-minerals",
    title: "Micronutrient Synergy: Iron, Vitamin C, Calcium & Vitamin D",
    summary: "How vitamins and minerals interact biochemically, how to maximize plant-based iron absorption, and preventing common global deficiencies.",
    category: "MINERALS",
    readingTimeMin: 6,
    difficulty: "Beginner",
    sections: [
      {
        heading: "Bioavailability & The Vitamin C + Iron Pair",
        content: "Non-heme iron (found in lentils, spinach, beans, and seeds) exists in the ferric (Fe3+) state, which is poorly absorbed. Pairing plant-based iron with ascorbic acid (Vitamin C from citrus, tomatoes, or bell peppers) reduces ferric iron to the highly absorbable ferrous (Fe2+) state, boosting absorption by up to 300%."
      },
      {
        heading: "The Calcium, Vitamin D3 & Vitamin K2 Triad",
        content: "Bone mineral density is governed by an exquisite triad: Vitamin D3 facilitates intestinal calcium absorption; Calcium provides mineral scaffold density; and Vitamin K2 (MK-7) activates osteocalcin to direct calcium directly into bones while preventing vascular calcification."
      },
      {
        heading: "Magnesium: The 300-Enzyme Regulator",
        content: "Magnesium is a vital enzymatic cofactor involved in over 300 cellular biochemical reactions, including ATP energy production, DNA synthesis, glucose homeostasis, and parasympathetic nervous system tone."
      }
    ],
    keyTakeaways: [
      "Always squeeze lemon juice or add fresh tomatoes/peppers over cooked lentils and leafy greens.",
      "Include magnesium-rich pumpkin seeds, dark chocolate, spinach, and black beans in your diet.",
      "Pair calcium foods with sunshine exposure or vitamin D3 for optimal skeletal and muscular health."
    ]
  },
  {
    id: "art_balanced_plate_method",
    slug: "balanced-plate-method",
    title: "The Visual Plate Method: Effortless Portion Control Without Obsessive Counting",
    summary: "A practical, evidence-based visual heuristic for constructing nutritious, satisfying meals anywhere in the world.",
    category: "PRACTICAL",
    readingTimeMin: 4,
    difficulty: "Beginner",
    sections: [
      {
        heading: "The 50 / 25 / 25 Division",
        content: "Rather than carrying food scales everywhere, visualize a standard 9-inch dinner plate divided into three intuitive zones: 1/2 of the plate filled with colorful non-starchy vegetables and greens; 1/4 of the plate allocated to quality lean or plant protein; and 1/4 of the plate reserved for high-fiber complex carbohydrates."
      },
      {
        heading: "The Healthy Fat Thumbnail Rule",
        content: "Incorporate a thumb-sized portion of healthy fats (such as a drizzle of extra virgin olive oil, 1/4 avocado, a sprinkle of toasted seeds, or a teaspoon of desi ghee) to ensure hormonal health and complete fat-soluble vitamin absorption."
      },
      {
        heading: "Adaptable Across Global Cuisines",
        content: "This framework translates effortlessly: for South Asian dining, fill half the plate with cucumber-tomato salad and sauteed spinach (palak), one quarter with chicken tikka or dal, and one quarter with brown basmati rice or one whole wheat roti."
      }
    ],
    keyTakeaways: [
      "50% colorful vegetables + 25% protein + 25% complex whole grains + 1 thumb of healthy fat.",
      "Focus on color variety across the week to ensure comprehensive polyphenol intake.",
      "Eat slowly, giving gastric stretch receptors and satiety hormones 15-20 minutes to signal fullness."
    ]
  },
  {
    id: "art_mediterranean_blueprint",
    slug: "mediterranean-diet-blueprint",
    title: "The Mediterranean Dietary Pattern: Longevity & Metabolic Health",
    summary: "Examining the scientific evidence behind the world's most rigorously researched dietary pattern.",
    category: "PATTERNS",
    readingTimeMin: 5,
    difficulty: "Intermediate",
    sections: [
      {
        heading: "The Core Tenets of Mediterranean Eating",
        content: "The traditional Mediterranean diet is characterized by an abundance of minimally processed plant foods: vegetables, fruits, legumes, whole grains, nuts, and seeds; extra virgin olive oil as the principal source of added fat; moderate consumption of fish and poultry; and minimal red meat and refined sugars."
      },
      {
        heading: "Cardiovascular & Cognitive Outcomes",
        content: "Randomized clinical trials (such as the landmark PREDIMED study) demonstrated a 30% reduction in major cardiovascular events among participants adhering to an EVOO or nut-enriched Mediterranean diet compared to standard low-fat control groups."
      },
      {
        heading: "Lifestyle & Mindful Eating",
        content: "Crucially, the Mediterranean paradigm encompasses more than food chemistry: it embraces shared communal meals, physical activity integrated into daily life, and unhurried culinary enjoyment."
      }
    ],
    keyTakeaways: [
      "Make extra virgin olive oil your primary daily cooking and finishing fat.",
      "Eat legumes at least 3-4 times per week as hearty meal foundations.",
      "Emphasize whole seasonal produce, seafood twice weekly, and mindful communal eating."
    ]
  },
  {
    id: "art_student_budget_nutrition",
    slug: "student-budget-nutrition",
    title: "High-Nutrition on a Budget: Smart Staples & Meal Prep",
    summary: "Practical strategies for maximizing nutritional density per dollar spent using global wholesome pantry items.",
    category: "PRACTICAL",
    readingTimeMin: 4,
    difficulty: "Beginner",
    sections: [
      {
        heading: "High-Value Nutrient Powerhouses",
        content: "The most nutrient-dense foods on Earth are often the least expensive per serving. Dried lentils, canned chickpeas, frozen vegetables (which match or exceed fresh produce nutrient retention), rolled oats, eggs, and peanut butter offer world-class nutrition for pennies per portion."
      },
      {
        heading: "Batch Cooking & Smart Freezing",
        content: "Cooking a large pot of Chana Masala, Black Bean Chili, or Lentil Dal on Sunday yields 4-5 ready-to-eat lunches throughout the week, eliminating the temptation of expensive, hyper-processed convenience fast food."
      }
    ],
    keyTakeaways: [
      "Stock up on dry legumes, brown rice, whole oats, eggs, and frozen spinach/berries.",
      "Batch cook nutrient-dense stews and grain bases once or twice a week.",
      "Season with bulk spices (turmeric, cumin, garlic, chili) for antioxidant benefits on a budget."
    ]
  },
  {
    id: "art_active_lifestyle_hydration",
    slug: "active-lifestyle-hydration-glycogen",
    title: "Performance Fueling: Glycogen Storage, Electrolytes & Recovery Timing",
    summary: "How to balance macronutrient timing, muscle glycogen resynthesis, and intracellular hydration for active individuals.",
    category: "ACTIVE",
    readingTimeMin: 5,
    difficulty: "Intermediate",
    sections: [
      {
        heading: "Pre-Workout Energy Substrates",
        content: "Consuming easily digestible complex carbohydrates 1 to 2 hours before strenuous activity replenishes liver and muscle glycogen stores without triggering gastrointestinal distress."
      },
      {
        heading: "Electrolytes & Fluid Balance",
        content: "Sweat consists not only of water but sodium, chloride, potassium, and magnesium. Replenishing sodium during prolonged workouts preserves plasma volume and prevents hyponatremia."
      },
      {
        heading: "The Anabolic Recovery Window",
        content: "Pairing 20-30g of protein with 40-60g of carbohydrates within 1-2 hours post-exercise maximizes both muscle protein synthesis (mTORC1) and glycogen synthase activity."
      }
    ],
    keyTakeaways: [
      "Hydrate before, during, and after activity with water plus pinch of electrolytes.",
      "Consume a 3:1 or 4:1 carb-to-protein ratio following intense endurance or resistance exercise.",
      "Adequate sleep is the non-negotiable foundation for muscular and nervous system repair."
    ]
  }
];

const NUTRITION_MYTHS = [
  {
    id: "myth_carbs_bad",
    myth: "Carbohydrates are inherently fattening and should be eliminated.",
    fact: "Carbohydrates are the body and brain's primary energy substrate. Quality and dietary fiber matter far more than total carb quantity.",
    evidenceExplanation: "Unrefined complex carbohydrates (oats, legumes, whole grains, root vegetables) come packaged with viscous soluble fiber, resistant starch, and polyphenols that slow glucose absorption, nourish the colonic microbiome, and elevate satiety hormones (GLP-1, PYY). Weight gain is driven by hyper-palatable processed energy density, not wholesome carbohydrate molecules.",
    practicalTip: "Focus on intact grains (quinoa, brown basmati, oats) and legumes rather than refined flours and isolated syrups.",
    category: "Macronutrients"
  },
  {
    id: "myth_all_fats_unhealthy",
    myth: "Eating dietary fat makes you gain body fat and clogs arteries.",
    fact: "Essential fatty acids (Omega-3 and Omega-6) and monounsaturated fats are critical for hormone synthesis, cell membranes, and nutrient absorption.",
    evidenceExplanation: "Decades of randomized clinical trials (such as the PREDIMED trial) show that diets rich in extra virgin olive oil, avocados, and nuts significantly reduce cardiovascular mortality without inducing unwanted weight gain. Fats also facilitate the absorption of fat-soluble vitamins (A, D, E, K).",
    practicalTip: "Include extra virgin olive oil, chia seeds, walnuts, and wild fatty fish regularly in your weekly meal rotation.",
    category: "Fats & Lipids"
  },
  {
    id: "myth_fruit_sugar",
    myth: "Fruit sugar (fructose) is just as harmful as high-fructose corn syrup.",
    fact: "Whole fruits contain fructose encased within cellular water, insoluble cellulose, and soluble pectin matrices that prevent rapid hepatic fructose flooding.",
    evidenceExplanation: "Consuming whole fruit is consistently correlated with lower risks of Type 2 diabetes and cardiovascular disease in large epidemiological cohorts. Intact fiber slows gastric emptying and ensures gradual digestive transit, unlike isolated crystalline syrups in beverages.",
    practicalTip: "Eat whole fresh fruits with peel intact when possible, rather than strained juices or fruit concentrates.",
    category: "Carbohydrates"
  },
  {
    id: "myth_detox_diets",
    myth: "Juice cleanses and detox teas are necessary to remove bodily toxins.",
    fact: "The human liver, kidneys, lungs, skin, and gastrointestinal tract maintain continuous, highly sophisticated endogenous detoxification pathways.",
    evidenceExplanation: "Phase I (Cytochrome P450 enzymes) and Phase II (glucuronidation, sulfation, glutathione conjugation) hepatic detox pathways require amino acids, sulfur compounds (from cruciferous vegetables and alliums), and B-vitamins—not calorie deprivation or laxative teas.",
    practicalTip: "Support liver function naturally with adequate protein, cruciferous vegetables (broccoli, cabbage), garlic, and 2-3 liters of water daily.",
    category: "Metabolism"
  },
  {
    id: "myth_protein_excess",
    myth: "More protein is always better; there is no ceiling to its benefits.",
    fact: "While adequate protein (1.2–2.0g/kg for active individuals) is vital, muscle protein synthesis (MPS) plateaus per meal, and excess calories are stored as fat or oxidized.",
    evidenceExplanation: "The 'muscle-full' effect shows that ~20-40g of high-quality protein (containing ~2.5-3g leucine) maximally stimulates the mTORC1 pathway in a single sitting. Distributing protein evenly across 3-4 meals produces superior muscle retention compared to a single massive bolus.",
    practicalTip: "Target 25-35g of bioavailable protein per meal rather than consuming the bulk of your protein in one giant dinner.",
    category: "Protein"
  },
  {
    id: "myth_eating_late_fat",
    myth: "Eating after 8:00 PM automatically turns food directly into body fat.",
    fact: "Total 24-hour energy balance and nutrient composition determine weight change, not an arbitrary clock hour.",
    evidenceExplanation: "While circadian alignment and eating during daylight hours optimize insulin sensitivity and digestive enzyme release, calories consumed late at night are subject to the identical biochemical laws of thermodynamics. Late-night eating often leads to mindless overconsumption of calorie-dense snacks rather than metabolic malfunction.",
    practicalTip: "Leave a 2-hour window between your last meal and sleep to enhance sleep architecture and prevent acid reflux.",
    category: "Energy Balance"
  },
  {
    id: "myth_supplements_essential",
    myth: "You must take a cabinet full of multivitamins and supplements to be healthy.",
    fact: "Whole food matrices contain thousands of synergistic co-factors, flavonoids, and fiber that synthetic pills cannot replicate.",
    evidenceExplanation: "With specific exceptions (e.g., Vitamin D in low-sun regions, B12 for strict vegans, prenatal folate, or clinically diagnosed deficiencies), randomized trials repeatedly demonstrate that general multivitamin supplementation in healthy populations does not reduce chronic disease incidence compared to a colorful whole-food dietary pattern.",
    practicalTip: "Prioritize whole diverse foods first; supplement strategically only based on bloodwork or specific dietary restrictions.",
    category: "Vitamins & Minerals"
  },
  {
    id: "myth_natural_always_healthy",
    myth: "'Natural' or 'organic' stamped on a package guarantees the food is healthy.",
    fact: "'Natural' is an unstandardized marketing term; organic cane sugar and deep-fried chips carry the exact same caloric and glycemic load.",
    evidenceExplanation: "Processing level (NOVA classification) is a far stronger predictor of metabolic health outcomes than marketing labels. Ultra-processed foods engineered with salt-sugar-fat bliss points override satiety neurochemistry regardless of whether their raw ingredients were labeled organic.",
    practicalTip: "Read the ingredients list and nutrient facts panel rather than relying on front-of-package marketing slogans.",
    category: "Food Literacy"
  }
];
