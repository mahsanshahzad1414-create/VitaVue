// ==========================================================================
// VitaVue International Nutrition & Evidence-Based Knowledge Base
// Replicated with exact fidelity from the Android Kotlin DataSource (64 Foods)
// ==========================================================================

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
  {
    "id": "fruit_apple",
    "name": "Apple",
    "category": "Fruits",
    "servingSize": "1 medium (182g)",
    "calories": 95,
    "proteinGrams": 0.5,
    "carbsGrams": 25,
    "fatGrams": 0.3,
    "fiberGrams": 4.4,
    "micronutrients": [
      {
        "name": "Vitamin C",
        "amount": "8.4 mg",
        "dailyValuePercent": 9,
        "benefit": "Antioxidant & immunity"
      },
      {
        "name": "Potassium",
        "amount": "195 mg",
        "dailyValuePercent": 4,
        "benefit": "Electrolyte & heart health"
      },
      {
        "name": "Quercetin",
        "amount": "4.4 mg",
        "dailyValuePercent": null,
        "benefit": "Flavonoid antioxidant"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Low-Fat",
      "High-Fiber"
    ],
    "description": "Crisp, sweet pome fruit rich in soluble pectin fiber that supports gut microbiome diversity and steady blood glucose.",
    "culinaryNotes": "Best eaten raw with peel intact to preserve insoluble fiber and polyphenol concentration.",
    "culturalOrigin": "Central Asia / Global",
    "glycemicIndex": "Low"
  },
  {
    "id": "fruit_banana",
    "name": "Banana",
    "category": "Fruits",
    "servingSize": "1 medium (118g)",
    "calories": 105,
    "proteinGrams": 1.3,
    "carbsGrams": 27,
    "fatGrams": 0.4,
    "fiberGrams": 3.1,
    "micronutrients": [
      {
        "name": "Potassium",
        "amount": "422 mg",
        "dailyValuePercent": 9,
        "benefit": "Supports neuromuscular signaling"
      },
      {
        "name": "Vitamin B6",
        "amount": "0.4 mg",
        "dailyValuePercent": 25,
        "benefit": "Neurotransmitter synthesis"
      },
      {
        "name": "Vitamin C",
        "amount": "10.3 mg",
        "dailyValuePercent": 11,
        "benefit": "Cellular defense"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Low-Fat"
    ],
    "description": "Natural energy source rich in potassium and prebiotic resistant starch (especially when slightly green).",
    "culinaryNotes": "Convenient pre-workout or breakfast carb source.",
    "culturalOrigin": "Southeast Asia / Tropical",
    "glycemicIndex": "Medium"
  },
  {
    "id": "fruit_orange",
    "name": "Orange",
    "category": "Fruits",
    "servingSize": "1 medium (131g)",
    "calories": 62,
    "proteinGrams": 1.2,
    "carbsGrams": 15.4,
    "fatGrams": 0.2,
    "fiberGrams": 3.1,
    "micronutrients": [
      {
        "name": "Vitamin C",
        "amount": "69.7 mg",
        "dailyValuePercent": 77,
        "benefit": "Immune defense & collagen"
      },
      {
        "name": "Hesperidin",
        "amount": "24 mg",
        "dailyValuePercent": null,
        "benefit": "Cardiovascular flavonoid"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Low-Fat",
      "High-Vitamin-C"
    ],
    "description": "Citrus staple renowned for high ascorbic acid (vitamin C) and bioavailable hesperidin flavonoids.",
    "culinaryNotes": "Eating whole slices provides dietary fiber that moderates natural fructose absorption.",
    "culturalOrigin": "Southern China / Mediterranean",
    "glycemicIndex": "Low"
  },
  {
    "id": "fruit_mango",
    "name": "Mango",
    "category": "Fruits",
    "servingSize": "1 cup sliced (165g)",
    "calories": 99,
    "proteinGrams": 1.4,
    "carbsGrams": 24.7,
    "fatGrams": 0.6,
    "fiberGrams": 2.6,
    "micronutrients": [
      {
        "name": "Vitamin C",
        "amount": "60 mg",
        "dailyValuePercent": 67,
        "benefit": "Antioxidant protection"
      },
      {
        "name": "Vitamin A",
        "amount": "89 mcg",
        "dailyValuePercent": 10,
        "benefit": "Eye & skin epithelial health"
      },
      {
        "name": "Mangiferin",
        "amount": "3.2 mg",
        "dailyValuePercent": null,
        "benefit": "Bioactive polyphenol"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Tropical"
    ],
    "description": "Aromatic stone fruit bursting with beta-carotene, vitamin C, and unique digestive enzymes (amylases).",
    "culinaryNotes": "Beloved across South Asia, Southeast Asia, and Latin America. Excellent in salsas and salads.",
    "culturalOrigin": "South Asia",
    "glycemicIndex": "Medium"
  },
  {
    "id": "fruit_avocado",
    "name": "Avocado",
    "category": "Fruits",
    "servingSize": "1/2 medium (100g)",
    "calories": 160,
    "proteinGrams": 2,
    "carbsGrams": 8.5,
    "fatGrams": 14.7,
    "fiberGrams": 6.7,
    "micronutrients": [
      {
        "name": "Potassium",
        "amount": "485 mg",
        "dailyValuePercent": 10,
        "benefit": "Electrolyte balance"
      },
      {
        "name": "Vitamin E",
        "amount": "2.1 mg",
        "dailyValuePercent": 14,
        "benefit": "Lipophilic antioxidant"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Keto-Friendly",
      "Heart-Healthy",
      "High-Fiber"
    ],
    "description": "Nutrient-dense unique fruit packed with monounsaturated oleic acid, lutein, and prebiotic fiber.",
    "culinaryNotes": "Enhances carotenoid absorption from accompanying vegetables when added to meals.",
    "culturalOrigin": "Mesoamerica",
    "glycemicIndex": "Low"
  },
  {
    "id": "fruit_blueberry",
    "name": "Blueberry",
    "category": "Fruits",
    "servingSize": "1 cup (148g)",
    "calories": 84,
    "proteinGrams": 1.1,
    "carbsGrams": 21.4,
    "fatGrams": 0.5,
    "fiberGrams": 3.6,
    "micronutrients": [
      {
        "name": "Anthocyanins",
        "amount": "163 mg",
        "dailyValuePercent": null,
        "benefit": "Potent neurological antioxidant"
      },
      {
        "name": "Vitamin K1",
        "amount": "28.6 mcg",
        "dailyValuePercent": 24,
        "benefit": "Bone & coagulation"
      },
      {
        "name": "Manganese",
        "amount": "0.5 mg",
        "dailyValuePercent": 22,
        "benefit": "Enzymatic cofactor"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Low-GI",
      "High-Antioxidant"
    ],
    "description": "Superfood berry with top-tier ORAC antioxidant capacity from anthocyanin pigments.",
    "culinaryNotes": "Retains beneficial polyphenol profiles whether fresh or flash-frozen.",
    "culturalOrigin": "North America",
    "glycemicIndex": "Low"
  },
  {
    "id": "fruit_strawberry",
    "name": "Strawberry",
    "category": "Fruits",
    "servingSize": "1 cup sliced (166g)",
    "calories": 53,
    "proteinGrams": 1.1,
    "carbsGrams": 12.7,
    "fatGrams": 0.5,
    "fiberGrams": 3.3,
    "micronutrients": [
      {
        "name": "Vitamin C",
        "amount": "97.6 mg",
        "dailyValuePercent": 108,
        "benefit": "Exceptional antioxidant"
      },
      {
        "name": "Manganese",
        "amount": "0.6 mg",
        "dailyValuePercent": 26,
        "benefit": "Metabolism"
      },
      {
        "name": "Ellagic Acid",
        "amount": "2.5 mg",
        "dailyValuePercent": null,
        "benefit": "Cellular protection"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Low-Calorie",
      "Low-Sugar"
    ],
    "description": "Hydrating berry providing over 100% daily vitamin C in one cup with minimal glycemic load.",
    "culinaryNotes": "Pairs naturally with Greek yogurt or chia seeds for balanced macro distribution.",
    "culturalOrigin": "Europe / Americas",
    "glycemicIndex": "Low"
  },
  {
    "id": "fruit_watermelon",
    "name": "Watermelon",
    "category": "Fruits",
    "servingSize": "1 wedge (286g)",
    "calories": 86,
    "proteinGrams": 1.7,
    "carbsGrams": 21.6,
    "fatGrams": 0.4,
    "fiberGrams": 1.1,
    "micronutrients": [
      {
        "name": "Lycopene",
        "amount": "12.7 mg",
        "dailyValuePercent": null,
        "benefit": "Cardioprotective carotenoid"
      },
      {
        "name": "L-Citrulline",
        "amount": "1.1 g",
        "dailyValuePercent": null,
        "benefit": "Nitric oxide precursor"
      },
      {
        "name": "Vitamin A",
        "amount": "80 mcg",
        "dailyValuePercent": 9,
        "benefit": "Vision & immunity"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Hydrating"
    ],
    "description": "Over 92% water content with high natural L-citrulline and lycopene for cellular hydration.",
    "culinaryNotes": "Excellent post-exercise refreshment.",
    "culturalOrigin": "Northeast Africa",
    "glycemicIndex": "High"
  },
  {
    "id": "fruit_pomegranate",
    "name": "Pomegranate Arils",
    "category": "Fruits",
    "servingSize": "1/2 cup (87g)",
    "calories": 72,
    "proteinGrams": 1.5,
    "carbsGrams": 16.3,
    "fatGrams": 1,
    "fiberGrams": 3.5,
    "micronutrients": [
      {
        "name": "Punicalagins",
        "amount": "120 mg",
        "dailyValuePercent": null,
        "benefit": "Potent vascular antioxidant"
      },
      {
        "name": "Vitamin K",
        "amount": "14.3 mcg",
        "dailyValuePercent": 12,
        "benefit": "Vascular integrity"
      },
      {
        "name": "Folate",
        "amount": "33 mcg",
        "dailyValuePercent": 8,
        "benefit": "DNA maintenance"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Heart-Healthy"
    ],
    "description": "Jewel-toned seed arils containing punicalagins that support arterial elasticity and gut microbiome.",
    "culinaryNotes": "Traditional Middle Eastern & South Asian garnish for savory rice, salads, and raita.",
    "culturalOrigin": "Persia / Mediterranean",
    "glycemicIndex": "Low"
  },
  {
    "id": "fruit_guava",
    "name": "Guava",
    "category": "Fruits",
    "servingSize": "1 fruit (55g)",
    "calories": 37,
    "proteinGrams": 1.4,
    "carbsGrams": 7.9,
    "fatGrams": 0.5,
    "fiberGrams": 3,
    "micronutrients": [
      {
        "name": "Vitamin C",
        "amount": "126 mg",
        "dailyValuePercent": 140,
        "benefit": "Extreme vitamin C density"
      },
      {
        "name": "Lycopene",
        "amount": "2.9 mg",
        "dailyValuePercent": null,
        "benefit": "Cellular defense"
      },
      {
        "name": "Potassium",
        "amount": "229 mg",
        "dailyValuePercent": 5,
        "benefit": "Blood pressure support"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "High-Fiber",
      "High-Vitamin-C"
    ],
    "description": "Tropical nutritional powerhouse packing quadruple the vitamin C concentration of standard oranges.",
    "culinaryNotes": "Can be eaten whole including edible seeds and skin.",
    "culturalOrigin": "Central & South America",
    "glycemicIndex": "Low"
  },
  {
    "id": "fruit_papaya",
    "name": "Papaya",
    "category": "Fruits",
    "servingSize": "1 cup chunks (145g)",
    "calories": 62,
    "proteinGrams": 0.7,
    "carbsGrams": 15.7,
    "fatGrams": 0.4,
    "fiberGrams": 2.5,
    "micronutrients": [
      {
        "name": "Papain",
        "amount": "25 mg",
        "dailyValuePercent": null,
        "benefit": "Proteolytic digestive enzyme"
      },
      {
        "name": "Vitamin C",
        "amount": "88.3 mg",
        "dailyValuePercent": 98,
        "benefit": "Immune and tissue support"
      },
      {
        "name": "Beta-Carotene",
        "amount": "397 mcg",
        "dailyValuePercent": null,
        "benefit": "Provitamin A"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Digestive-Aid"
    ],
    "description": "Tropical fruit with soothing papain enzymes that aid protein digestion and reduce bloating.",
    "culinaryNotes": "Popular in tropical breakfasts and green papaya savory salads across Southeast Asia.",
    "culturalOrigin": "Mesoamerica",
    "glycemicIndex": "Medium"
  },
  {
    "id": "fruit_pineapple",
    "name": "Pineapple",
    "category": "Fruits",
    "servingSize": "1 cup chunks (165g)",
    "calories": 82,
    "proteinGrams": 0.9,
    "carbsGrams": 21.6,
    "fatGrams": 0.2,
    "fiberGrams": 2.3,
    "micronutrients": [
      {
        "name": "Bromelain",
        "amount": "40 mg",
        "dailyValuePercent": null,
        "benefit": "Anti-inflammatory enzyme"
      },
      {
        "name": "Manganese",
        "amount": "1.5 mg",
        "dailyValuePercent": 65,
        "benefit": "Metabolic enzyme cofactor"
      },
      {
        "name": "Vitamin C",
        "amount": "78.9 mg",
        "dailyValuePercent": 88,
        "benefit": "Antioxidant"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Anti-Inflammatory"
    ],
    "description": "Sweet tropical fruit containing bromelain, a proteolytic enzyme studied for anti-inflammatory benefits.",
    "culinaryNotes": "Great natural meat tenderizer in marinades.",
    "culturalOrigin": "South America",
    "glycemicIndex": "High"
  },
  {
    "id": "fruit_kiwi",
    "name": "Kiwi",
    "category": "Fruits",
    "servingSize": "1 medium (69g)",
    "calories": 42,
    "proteinGrams": 0.8,
    "carbsGrams": 10.1,
    "fatGrams": 0.4,
    "fiberGrams": 2.1,
    "micronutrients": [
      {
        "name": "Actinidin",
        "amount": "18 mg",
        "dailyValuePercent": null,
        "benefit": "Digestive enzyme"
      },
      {
        "name": "Vitamin C",
        "amount": "64 mg",
        "dailyValuePercent": 71,
        "benefit": "Immune strength"
      },
      {
        "name": "Serotonin Precursors",
        "amount": "12 mcg",
        "dailyValuePercent": null,
        "benefit": "Sleep quality support"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Low-Calorie"
    ],
    "description": "Emerald fruit loaded with actinidin and natural compounds that promote restful sleep and gastrointestinal motility.",
    "culinaryNotes": "The fuzzy skin is fully edible and triples the total fiber content.",
    "culturalOrigin": "China / New Zealand",
    "glycemicIndex": "Low"
  },
  {
    "id": "fruit_dates",
    "name": "Medjool Dates",
    "category": "Fruits",
    "servingSize": "2 dates (48g)",
    "calories": 133,
    "proteinGrams": 0.9,
    "carbsGrams": 36,
    "fatGrams": 0.1,
    "fiberGrams": 3.2,
    "micronutrients": [
      {
        "name": "Potassium",
        "amount": "334 mg",
        "dailyValuePercent": 7,
        "benefit": "Quick electrolyte replenish"
      },
      {
        "name": "Copper",
        "amount": "0.2 mg",
        "dailyValuePercent": 22,
        "benefit": "Energy metabolism"
      },
      {
        "name": "Polyphenols",
        "amount": "85 mg",
        "dailyValuePercent": null,
        "benefit": "Cellular defense"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Natural-Sweetener",
      "High-Energy"
    ],
    "description": "Ancient Middle Eastern date palm fruit offering rapid natural energy, potassium, and rich caramel flavor.",
    "culinaryNotes": "Traditional food to break fasts in Islamic traditions (Iftar) due to gentle digestive assimilation.",
    "culturalOrigin": "Middle East / North Africa",
    "glycemicIndex": "High"
  },
  {
    "id": "fruit_figs",
    "name": "Fresh Figs",
    "category": "Fruits",
    "servingSize": "2 medium (100g)",
    "calories": 74,
    "proteinGrams": 0.8,
    "carbsGrams": 19.2,
    "fatGrams": 0.3,
    "fiberGrams": 2.9,
    "micronutrients": [
      {
        "name": "Calcium",
        "amount": "35 mg",
        "dailyValuePercent": 3,
        "benefit": "Bone health"
      },
      {
        "name": "Magnesium",
        "amount": "17 mg",
        "dailyValuePercent": 4,
        "benefit": "Muscular function"
      },
      {
        "name": "Ficin",
        "amount": "15 mg",
        "dailyValuePercent": null,
        "benefit": "Natural digestive enzyme"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Mediterranean"
    ],
    "description": "Delicate Mediterranean fruit with edible crunchy seeds, soluble pectin, and mild natural laxative properties.",
    "culinaryNotes": "Pairs exceptionally with walnuts, goat cheese, and balsamic reduction.",
    "culturalOrigin": "Mediterranean / Levant",
    "glycemicIndex": "Medium"
  },
  {
    "id": "fruit_dragonfruit",
    "name": "Dragon Fruit (Pitaya)",
    "category": "Fruits",
    "servingSize": "1 cup cubed (227g)",
    "calories": 136,
    "proteinGrams": 3,
    "carbsGrams": 29,
    "fatGrams": 0,
    "fiberGrams": 7,
    "micronutrients": [
      {
        "name": "Iron",
        "amount": "1.9 mg",
        "dailyValuePercent": 11,
        "benefit": "Oxygen transport"
      },
      {
        "name": "Magnesium",
        "amount": "41 mg",
        "dailyValuePercent": 10,
        "benefit": "Energy production"
      },
      {
        "name": "Betalains",
        "amount": "45 mg",
        "dailyValuePercent": null,
        "benefit": "Antioxidant pigment"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "High-Fiber"
    ],
    "description": "Striking cactus fruit packed with 7g of prebiotic fiber, magnesium, and plant-based iron.",
    "culinaryNotes": "Mild, slightly sweet flavor similar to a cross between kiwi and pear.",
    "culturalOrigin": "Central America / Southeast Asia",
    "glycemicIndex": "Low"
  },
  {
    "id": "veg_spinach",
    "name": "Spinach (Fresh / Cooked)",
    "category": "Vegetables",
    "servingSize": "1 cup cooked (180g)",
    "calories": 41,
    "proteinGrams": 5.3,
    "carbsGrams": 6.7,
    "fatGrams": 0.5,
    "fiberGrams": 4.3,
    "micronutrients": [
      {
        "name": "Vitamin K",
        "amount": "888 mcg",
        "dailyValuePercent": 740,
        "benefit": "Coagulation & bone matrix"
      },
      {
        "name": "Iron",
        "amount": "6.4 mg",
        "dailyValuePercent": 36,
        "benefit": "Hemoglobin synthesis"
      },
      {
        "name": "Lutein & Zeaxanthin",
        "amount": "20.4 mg",
        "dailyValuePercent": null,
        "benefit": "Macular protection"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Keto-Friendly",
      "Iron-Rich",
      "Superfood"
    ],
    "description": "Premier dark leafy green offering concentrated non-heme iron, lutein, and chlorophyll.",
    "culinaryNotes": "Cooking reduces oxalate binding and drastically boosts iron and calcium bioavailability. Fundamental in Palak Paneer and Mediterranean sautés.",
    "culturalOrigin": "Ancient Persia / Global",
    "glycemicIndex": "Low"
  },
  {
    "id": "veg_broccoli",
    "name": "Broccoli",
    "category": "Vegetables",
    "servingSize": "1 cup chopped (91g)",
    "calories": 31,
    "proteinGrams": 2.6,
    "carbsGrams": 6,
    "fatGrams": 0.3,
    "fiberGrams": 2.4,
    "micronutrients": [
      {
        "name": "Vitamin C",
        "amount": "81 mg",
        "dailyValuePercent": 90,
        "benefit": "Collagen & immune defense"
      },
      {
        "name": "Chromium",
        "amount": "18 mcg",
        "dailyValuePercent": 51,
        "benefit": "Glucose tolerance factor"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Low-Carb",
      "Detox-Support"
    ],
    "description": "Cruciferous staple famous for sulforaphane, an organosulfur compound that triggers cellular antioxidant enzymes.",
    "culinaryNotes": "Lightly steaming for 3-4 minutes preserves myrosinase enzyme activity needed to form sulforaphane.",
    "culturalOrigin": "Mediterranean / Italy",
    "glycemicIndex": "Low"
  },
  {
    "id": "veg_sweet_potato",
    "name": "Sweet Potato",
    "category": "Vegetables",
    "servingSize": "1 medium baked (114g)",
    "calories": 103,
    "proteinGrams": 2.3,
    "carbsGrams": 23.6,
    "fatGrams": 0.2,
    "fiberGrams": 3.8,
    "micronutrients": [
      {
        "name": "Vitamin A",
        "amount": "1096 mcg",
        "dailyValuePercent": 122,
        "benefit": "Skin & visual cycle"
      },
      {
        "name": "Potassium",
        "amount": "542 mg",
        "dailyValuePercent": 12,
        "benefit": "Blood pressure regulation"
      },
      {
        "name": "Manganese",
        "amount": "0.5 mg",
        "dailyValuePercent": 22,
        "benefit": "Antioxidant enzyme SOD"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Complex-Carb",
      "High-Fiber"
    ],
    "description": "Complex carbohydrate tuber loaded with carotenoids and resistant starch with a low glycemic index when boiled or baked.",
    "culinaryNotes": "Pairing with healthy fats (like olive oil) enhances beta-carotene assimilation by over 300%.",
    "culturalOrigin": "Central & South America",
    "glycemicIndex": "Medium"
  },
  {
    "id": "veg_carrot",
    "name": "Carrot",
    "category": "Vegetables",
    "servingSize": "1 medium (61g)",
    "calories": 25,
    "proteinGrams": 0.6,
    "carbsGrams": 5.8,
    "fatGrams": 0.1,
    "fiberGrams": 1.7,
    "micronutrients": [
      {
        "name": "Beta-Carotene",
        "amount": "5059 mcg",
        "dailyValuePercent": null,
        "benefit": "Retinol precursor"
      },
      {
        "name": "Lutein",
        "amount": "256 mcg",
        "dailyValuePercent": null,
        "benefit": "Eye protection"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Low-Calorie"
    ],
    "description": "Root vegetable revered for eye health from provitamin A carotenoids and versatile crunchy texture.",
    "culinaryNotes": "Grated raw in salads or simmered in stews and South Asian halwas.",
    "culturalOrigin": "Persia / Afghanistan",
    "glycemicIndex": "Low"
  },
  {
    "id": "veg_garlic",
    "name": "Garlic",
    "category": "Vegetables",
    "servingSize": "3 cloves (9g)",
    "calories": 13,
    "proteinGrams": 0.6,
    "carbsGrams": 3,
    "fatGrams": 0,
    "fiberGrams": 0.2,
    "micronutrients": [
      {
        "name": "Allicin",
        "amount": "12 mg",
        "dailyValuePercent": null,
        "benefit": "Cardiovascular and antimicrobial sulfur compound"
      },
      {
        "name": "Manganese",
        "amount": "0.15 mg",
        "dailyValuePercent": 7,
        "benefit": "Connective tissue"
      },
      {
        "name": "Vitamin B6",
        "amount": "0.11 mg",
        "dailyValuePercent": 6,
        "benefit": "Metabolism"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Heart-Healthy",
      "Aromatic"
    ],
    "description": "Aromatic culinary cornerstone with active organosulfur allicin proven to support vascular nitric oxide production.",
    "culinaryNotes": "Crush or chop and allow to rest for 10 minutes before cooking to activate alliinase enzyme synthesis.",
    "culturalOrigin": "Central Asia",
    "glycemicIndex": "Low"
  },
  {
    "id": "veg_onion",
    "name": "Red / Yellow Onion",
    "category": "Vegetables",
    "servingSize": "1 medium (110g)",
    "calories": 44,
    "proteinGrams": 1.2,
    "carbsGrams": 10.3,
    "fatGrams": 0.1,
    "fiberGrams": 1.9,
    "micronutrients": [
      {
        "name": "Quercetin",
        "amount": "32 mg",
        "dailyValuePercent": null,
        "benefit": "Antihistamine & antioxidant flavonoid"
      },
      {
        "name": "Inulin",
        "amount": "1.5 g",
        "dailyValuePercent": null,
        "benefit": "Prebiotic fructooligosaccharide"
      },
      {
        "name": "Chromium",
        "amount": "12 mcg",
        "dailyValuePercent": 34,
        "benefit": "Insulin sensitivity"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Prebiotic",
      "Aromatic"
    ],
    "description": "Global culinary base delivering prebiotic inulin fiber to feed beneficial bifidobacteria in the gut.",
    "culinaryNotes": "The outer colored layers possess the highest quercetin concentration.",
    "culturalOrigin": "Central Asia / Global",
    "glycemicIndex": "Low"
  },
  {
    "id": "veg_bell_pepper",
    "name": "Red Bell Pepper",
    "category": "Vegetables",
    "servingSize": "1 medium (119g)",
    "calories": 37,
    "proteinGrams": 1.2,
    "carbsGrams": 7.2,
    "fatGrams": 0.4,
    "fiberGrams": 2.5,
    "micronutrients": [
      {
        "name": "Vitamin C",
        "amount": "152 mg",
        "dailyValuePercent": 169,
        "benefit": "Highest amongst common vegetables"
      },
      {
        "name": "Capsanthin",
        "amount": "38 mg",
        "dailyValuePercent": null,
        "benefit": "Potent red antioxidant"
      },
      {
        "name": "Vitamin B6",
        "amount": "0.34 mg",
        "dailyValuePercent": 20,
        "benefit": "Neurochemistry"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "High-Vitamin-C",
      "Low-Calorie"
    ],
    "description": "Sweet, vibrant pepper offering more than 1.5x daily vitamin C requirement in a single pepper.",
    "culinaryNotes": "Essential in Mediterranean ratatouille, fajitas, stir-fries, and raw snacking.",
    "culturalOrigin": "Mesoamerica",
    "glycemicIndex": "Low"
  },
  {
    "id": "veg_beetroot",
    "name": "Beetroot",
    "category": "Vegetables",
    "servingSize": "1 cup cooked (170g)",
    "calories": 75,
    "proteinGrams": 2.9,
    "carbsGrams": 16.9,
    "fatGrams": 0.3,
    "fiberGrams": 3.4,
    "micronutrients": [
      {
        "name": "Dietary Inorganic Nitrates",
        "amount": "250 mg",
        "dailyValuePercent": null,
        "benefit": "Vasodilation & stamina"
      },
      {
        "name": "Betalains",
        "amount": "42 mg",
        "dailyValuePercent": null,
        "benefit": "Liver support & antioxidant"
      },
      {
        "name": "Folate",
        "amount": "136 mcg",
        "dailyValuePercent": 34,
        "benefit": "Erythrocyte production"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Athletic-Performance",
      "Heart-Healthy"
    ],
    "description": "Deep ruby root prized by athletes for inorganic nitrates that boost endothelial nitric oxide and exercise efficiency.",
    "culinaryNotes": "Roasting or juicing yields optimal bioavailable nitrates.",
    "culturalOrigin": "Mediterranean coast",
    "glycemicIndex": "Medium"
  },
  {
    "id": "veg_cauliflower",
    "name": "Cauliflower",
    "category": "Vegetables",
    "servingSize": "1 cup chopped (107g)",
    "calories": 27,
    "proteinGrams": 2.1,
    "carbsGrams": 5.3,
    "fatGrams": 0.3,
    "fiberGrams": 2.1,
    "micronutrients": [
      {
        "name": "Choline",
        "amount": "47 mg",
        "dailyValuePercent": 9,
        "benefit": "Cell membrane & brain health"
      },
      {
        "name": "Indole-3-Carbinol",
        "amount": "18 mg",
        "dailyValuePercent": null,
        "benefit": "Hormone metabolism support"
      },
      {
        "name": "Vitamin C",
        "amount": "51.6 mg",
        "dailyValuePercent": 57,
        "benefit": "Antioxidant"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Low-Carb",
      "Keto-Friendly"
    ],
    "description": "Versatile brassica rich in choline, used widely as a nutrient-dense low-carb alternative for rice or dough.",
    "culinaryNotes": "Featured globally in Aloo Gobi (South Asian spiced cauliflower) and modern roasted florets.",
    "culturalOrigin": "Mediterranean / Asia Minor",
    "glycemicIndex": "Low"
  },
  {
    "id": "veg_mushroom",
    "name": "Cremini / Portobello Mushrooms",
    "category": "Vegetables",
    "servingSize": "1 cup sliced (70g)",
    "calories": 15,
    "proteinGrams": 2.2,
    "carbsGrams": 2.3,
    "fatGrams": 0.2,
    "fiberGrams": 0.7,
    "micronutrients": [
      {
        "name": "Selenium",
        "amount": "18.2 mcg",
        "dailyValuePercent": 33,
        "benefit": "Thyroid and antioxidant enzymes"
      },
      {
        "name": "Beta-Glucans",
        "amount": "1.2 g",
        "dailyValuePercent": null,
        "benefit": "Immune modulating polysaccharide"
      },
      {
        "name": "Ergothioneine",
        "amount": "4.8 mg",
        "dailyValuePercent": null,
        "benefit": "Longevity antioxidant"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Low-Calorie",
      "Umami-Rich"
    ],
    "description": "Fungal superfood providing unique ergothioneine and selenium, adding savory umami without sodium.",
    "culinaryNotes": "Sautéing with garlic and herbs unlocks deep savory glutamates.",
    "culturalOrigin": "Global",
    "glycemicIndex": "Low"
  },
  {
    "id": "grain_quinoa",
    "name": "Quinoa (Cooked)",
    "category": "Grains & Breads",
    "servingSize": "1 cup cooked (185g)",
    "calories": 222,
    "proteinGrams": 8.1,
    "carbsGrams": 39.4,
    "fatGrams": 3.6,
    "fiberGrams": 5.2,
    "micronutrients": [
      {
        "name": "Complete Amino Acid Score",
        "amount": "100%",
        "dailyValuePercent": null,
        "benefit": "All 9 essential amino acids"
      },
      {
        "name": "Magnesium",
        "amount": "118 mg",
        "dailyValuePercent": 28,
        "benefit": "Muscle & nerve homeostasis"
      },
      {
        "name": "Iron",
        "amount": "2.8 mg",
        "dailyValuePercent": 15,
        "benefit": "Cellular oxygen transport"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Complete-Protein",
      "High-Fiber"
    ],
    "description": "Ancient Andean pseudo-grain containing all 9 essential amino acids in optimal human proportion.",
    "culinaryNotes": "Rinse before boiling to remove natural bitter saponins on outer coating.",
    "culturalOrigin": "Andean Region of South America",
    "glycemicIndex": "Low"
  },
  {
    "id": "grain_oats",
    "name": "Rolled Oats (Dry)",
    "category": "Grains & Breads",
    "servingSize": "1/2 cup dry (40g)",
    "calories": 150,
    "proteinGrams": 5,
    "carbsGrams": 27,
    "fatGrams": 2.5,
    "fiberGrams": 4,
    "micronutrients": [
      {
        "name": "Beta-Glucan",
        "amount": "2.0 g",
        "dailyValuePercent": null,
        "benefit": "Reduces LDL cholesterol & stabilizes glucose"
      },
      {
        "name": "Avenanthramides",
        "amount": "14 mg",
        "dailyValuePercent": null,
        "benefit": "Anti-inflammatory avenanthramides"
      },
      {
        "name": "Manganese",
        "amount": "1.4 mg",
        "dailyValuePercent": 61,
        "benefit": "Metabolic enzyme activity"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free-Certified",
      "Heart-Healthy",
      "High-Fiber"
    ],
    "description": "Gold-standard breakfast grain with clinically proven beta-glucan soluble fiber that binds digestive bile acids.",
    "culinaryNotes": "Overnight soaking improves starch digestibility and phytate breakdown.",
    "culturalOrigin": "Fertile Crescent / Europe",
    "glycemicIndex": "Low"
  },
  {
    "id": "grain_basmati_rice",
    "name": "Brown Basmati Rice (Cooked)",
    "category": "Grains & Breads",
    "servingSize": "1 cup cooked (195g)",
    "calories": 216,
    "proteinGrams": 5,
    "carbsGrams": 45,
    "fatGrams": 1.8,
    "fiberGrams": 3.5,
    "micronutrients": [
      {
        "name": "Manganese",
        "amount": "1.8 mg",
        "dailyValuePercent": 78,
        "benefit": "Antioxidant defense"
      },
      {
        "name": "Selenium",
        "amount": "19 mcg",
        "dailyValuePercent": 35,
        "benefit": "Thyroid hormone activation"
      },
      {
        "name": "Magnesium",
        "amount": "84 mg",
        "dailyValuePercent": 20,
        "benefit": "Glycemic control"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Whole-Grain"
    ],
    "description": "Aromatic long-grain rice with intact bran layer providing sustained complex carbohydrates and nutty fragrance.",
    "culinaryNotes": "Standard wholesome carbohydrate backbone across South Asian, Persian, and Middle Eastern gastronomy.",
    "culturalOrigin": "Himalayan Foothills / South Asia",
    "glycemicIndex": "Medium"
  },
  {
    "id": "grain_roti_chapati",
    "name": "Whole Wheat Roti / Chapati",
    "category": "Grains & Breads",
    "servingSize": "1 medium roti (40g)",
    "calories": 110,
    "proteinGrams": 3.8,
    "carbsGrams": 22,
    "fatGrams": 0.8,
    "fiberGrams": 3.2,
    "micronutrients": [
      {
        "name": "Complex Carbs",
        "amount": "18 g",
        "dailyValuePercent": null,
        "benefit": "Slow release energy"
      },
      {
        "name": "B-Complex Vitamins",
        "amount": "0.2 mg",
        "dailyValuePercent": 15,
        "benefit": "Energy metabolism"
      },
      {
        "name": "Iron",
        "amount": "1.2 mg",
        "dailyValuePercent": 7,
        "benefit": "Oxygenation"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Whole-Grain",
      "Low-Fat",
      "Traditional"
    ],
    "description": "Traditional unleavened stone-ground flatbread (atta) cooked on a dry tawa pan without saturated fats.",
    "culinaryNotes": "Fundamental daily staple across Pakistan and Northern India, ideally paired with dals and sabzis.",
    "culturalOrigin": "South Asia",
    "glycemicIndex": "Medium"
  },
  {
    "id": "grain_barley",
    "name": "Pearl / Hulled Barley",
    "category": "Grains & Breads",
    "servingSize": "1 cup cooked (157g)",
    "calories": 193,
    "proteinGrams": 3.6,
    "carbsGrams": 44.3,
    "fatGrams": 0.7,
    "fiberGrams": 6,
    "micronutrients": [
      {
        "name": "Soluble Fiber",
        "amount": "3.0 g",
        "dailyValuePercent": null,
        "benefit": "Cholesterol reduction"
      },
      {
        "name": "Selenium",
        "amount": "13.5 mcg",
        "dailyValuePercent": 25,
        "benefit": "Antioxidant protection"
      },
      {
        "name": "Molybdenum",
        "amount": "12 mcg",
        "dailyValuePercent": 27,
        "benefit": "Sulfur amino acid breakdown"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "High-Fiber",
      "Low-GI"
    ],
    "description": "Ancient hearty grain with the lowest glycemic index among cereal grains (GI ~28).",
    "culinaryNotes": "Superb in Middle Eastern soups, stews, and grain salads.",
    "culturalOrigin": "Fertile Crescent",
    "glycemicIndex": "Low"
  },
  {
    "id": "legume_chickpeas",
    "name": "Chickpeas (Garbanzo Beans)",
    "category": "Legumes & Pulses",
    "servingSize": "1 cup cooked (164g)",
    "calories": 269,
    "proteinGrams": 14.5,
    "carbsGrams": 45,
    "fatGrams": 4.2,
    "fiberGrams": 12.5,
    "micronutrients": [
      {
        "name": "Iron",
        "amount": "4.7 mg",
        "dailyValuePercent": 26,
        "benefit": "Oxygen carriage"
      },
      {
        "name": "Manganese",
        "amount": "1.7 mg",
        "dailyValuePercent": 74,
        "benefit": "Mitochondrial antioxidant SOD2"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "High-Protein",
      "High-Fiber",
      "Heart-Healthy"
    ],
    "description": "Nutty legume providing 12.5g of fiber and 14.5g of protein, backing iconic dishes like hummus, falafel, and chana masala.",
    "culinaryNotes": "Aquafaba (chickpea soaking water) serves as an effective vegan egg white substitute.",
    "culturalOrigin": "Middle East / Mediterranean",
    "glycemicIndex": "Low"
  },
  {
    "id": "legume_red_lentils",
    "name": "Red Lentils (Masoor Dal)",
    "category": "Legumes & Pulses",
    "servingSize": "1 cup cooked (198g)",
    "calories": 230,
    "proteinGrams": 17.9,
    "carbsGrams": 39.9,
    "fatGrams": 0.8,
    "fiberGrams": 15.6,
    "micronutrients": [
      {
        "name": "Folate",
        "amount": "358 mcg",
        "dailyValuePercent": 90,
        "benefit": "Cell proliferation"
      },
      {
        "name": "Iron",
        "amount": "6.6 mg",
        "dailyValuePercent": 37,
        "benefit": "Plant-based heme precursor"
      },
      {
        "name": "Zinc",
        "amount": "2.5 mg",
        "dailyValuePercent": 23,
        "benefit": "Immunity & protein synthesis"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "High-Protein",
      "High-Fiber",
      "Low-Fat"
    ],
    "description": "Quick-cooking hulled lentils that break down into a creamy, protein-dense stew without soaking.",
    "culinaryNotes": "Cornerstone of South Asian comforting Dal Tadka, seasoned with cumin, turmeric, and garlic.",
    "culturalOrigin": "South Asia / Levant",
    "glycemicIndex": "Low"
  },
  {
    "id": "legume_black_beans",
    "name": "Black Beans",
    "category": "Legumes & Pulses",
    "servingSize": "1 cup cooked (172g)",
    "calories": 227,
    "proteinGrams": 15.2,
    "carbsGrams": 40.8,
    "fatGrams": 0.9,
    "fiberGrams": 15,
    "micronutrients": [
      {
        "name": "Anthocyanin Bioflavonoids",
        "amount": "45 mg",
        "dailyValuePercent": null,
        "benefit": "Dark seed coat antioxidant"
      },
      {
        "name": "Magnesium",
        "amount": "120 mg",
        "dailyValuePercent": 29,
        "benefit": "Metabolic regulation"
      },
      {
        "name": "Folate",
        "amount": "256 mcg",
        "dailyValuePercent": 64,
        "benefit": "Cardiovascular homocysteine control"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "High-Protein",
      "High-Fiber"
    ],
    "description": "Latin American staple loaded with resistant starch and polyphenols that promote gut short-chain fatty acids (SCFAs).",
    "culinaryNotes": "Pairs naturally with brown rice to form a complete dietary protein with balanced leucine.",
    "culturalOrigin": "Central & South America",
    "glycemicIndex": "Low"
  },
  {
    "id": "legume_mung_beans",
    "name": "Green Mung Beans (Moong)",
    "category": "Legumes & Pulses",
    "servingSize": "1 cup cooked (202g)",
    "calories": 212,
    "proteinGrams": 14.2,
    "carbsGrams": 38.7,
    "fatGrams": 0.8,
    "fiberGrams": 15.4,
    "micronutrients": [
      {
        "name": "Potassium",
        "amount": "537 mg",
        "dailyValuePercent": 11,
        "benefit": "Cardiovascular health"
      },
      {
        "name": "Magnesium",
        "amount": "97 mg",
        "dailyValuePercent": 23,
        "benefit": "Nerve conduction"
      },
      {
        "name": "Vitexin & Isovitexin",
        "amount": "22 mg",
        "dailyValuePercent": null,
        "benefit": "Heat stroke & oxidative defense"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Easy-Digest",
      "High-Fiber"
    ],
    "description": "Easiest-to-digest legume widely prescribed in Ayurvedic traditions (as Kitchari) for gentle gut restoration.",
    "culinaryNotes": "Can be sprouted in 48 hours to multiply vitamin C and enzyme availability.",
    "culturalOrigin": "South Asia",
    "glycemicIndex": "Low"
  },
  {
    "id": "legume_kidney_beans",
    "name": "Kidney Beans (Rajma)",
    "category": "Legumes & Pulses",
    "servingSize": "1 cup cooked (177g)",
    "calories": 225,
    "proteinGrams": 15.3,
    "carbsGrams": 40.4,
    "fatGrams": 0.9,
    "fiberGrams": 13.1,
    "micronutrients": [
      {
        "name": "Molybdenum",
        "amount": "130 mcg",
        "dailyValuePercent": 289,
        "benefit": "Detoxification of sulfites"
      },
      {
        "name": "Iron",
        "amount": "3.9 mg",
        "dailyValuePercent": 22,
        "benefit": "Energy levels"
      },
      {
        "name": "Folate",
        "amount": "230 mcg",
        "dailyValuePercent": 58,
        "benefit": "Cellular division"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "High-Protein",
      "High-Fiber"
    ],
    "description": "Robust red bean essential in Rajma Masala, chili con carne, and Mediterranean minestrone.",
    "culinaryNotes": "Must be boiled thoroughly (10+ minutes) to deactivate phytohaemagglutinin lectins.",
    "culturalOrigin": "Central America / South Asia",
    "glycemicIndex": "Low"
  },
  {
    "id": "nut_almonds",
    "name": "Raw / Roasted Almonds",
    "category": "Nuts & Seeds",
    "servingSize": "1 oz (28g / ~23 nuts)",
    "calories": 164,
    "proteinGrams": 6,
    "carbsGrams": 6.1,
    "fatGrams": 14.2,
    "fiberGrams": 3.5,
    "micronutrients": [
      {
        "name": "Magnesium",
        "amount": "77 mg",
        "dailyValuePercent": 18,
        "benefit": "Muscle relaxation"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Keto-Friendly",
      "Heart-Healthy",
      "Brain-Food"
    ],
    "description": "Nutrient-dense tree nut providing 50% daily Vitamin E per ounce to shield cell membranes from lipid peroxidation.",
    "culinaryNotes": "Soaking overnight softens texture and reduces enzyme inhibitor phytic acid.",
    "culturalOrigin": "Middle East / Mediterranean",
    "glycemicIndex": "Low"
  },
  {
    "id": "nut_walnuts",
    "name": "Walnuts",
    "category": "Nuts & Seeds",
    "servingSize": "1 oz (28g / 14 halves)",
    "calories": 185,
    "proteinGrams": 4.3,
    "carbsGrams": 3.9,
    "fatGrams": 18.5,
    "fiberGrams": 1.9,
    "micronutrients": [
      {
        "name": "Polyphenols",
        "amount": "180 mg",
        "dailyValuePercent": null,
        "benefit": "Cognitive and endothelial protection"
      },
      {
        "name": "Copper",
        "amount": "0.45 mg",
        "dailyValuePercent": 50,
        "benefit": "Collagen & iron metabolism"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Omega-3-Rich",
      "Keto-Friendly",
      "Brain-Food"
    ],
    "description": "The premier tree nut source of plant-based ALA Omega-3 fatty acids, shaped like human cerebral hemispheres.",
    "culinaryNotes": "Store in refrigeration to prevent delicate polyunsaturated fats from oxidizing.",
    "culturalOrigin": "Persia / Mediterranean",
    "glycemicIndex": "Low"
  },
  {
    "id": "nut_chia_seeds",
    "name": "Chia Seeds",
    "category": "Nuts & Seeds",
    "servingSize": "2 tbsp (28g)",
    "calories": 138,
    "proteinGrams": 4.7,
    "carbsGrams": 12,
    "fatGrams": 8.7,
    "fiberGrams": 9.8,
    "micronutrients": [
      {
        "name": "ALA Omega-3",
        "amount": "5.0 g",
        "dailyValuePercent": 312,
        "benefit": "Unprecedented plant omega-3 density"
      },
      {
        "name": "Calcium",
        "amount": "179 mg",
        "dailyValuePercent": 14,
        "benefit": "Non-dairy bone mineral"
      },
      {
        "name": "Soluble Mucilage Fiber",
        "amount": "5.2 g",
        "dailyValuePercent": null,
        "benefit": "Forms hydrophilic gel"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "High-Fiber",
      "Omega-3-Rich",
      "Superfood"
    ],
    "description": "Aztec super-seed capable of absorbing 12x its weight in liquid, creating a prebiotic hydrophilic gel.",
    "culinaryNotes": "Stir into almond milk with cocoa or berries to make nutritious Chia Pudding.",
    "culturalOrigin": "Mesoamerica",
    "glycemicIndex": "Low"
  },
  {
    "id": "nut_flaxseeds",
    "name": "Ground Flaxseeds (Linseed)",
    "category": "Nuts & Seeds",
    "servingSize": "2 tbsp ground (14g)",
    "calories": 75,
    "proteinGrams": 2.6,
    "carbsGrams": 4,
    "fatGrams": 6,
    "fiberGrams": 3.8,
    "micronutrients": [
      {
        "name": "ALA Omega-3",
        "amount": "3.2 g",
        "dailyValuePercent": 200,
        "benefit": "Vascular health"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Hormone-Balance",
      "Omega-3"
    ],
    "description": "Richest dietary source of lignan phytoestrogens that support healthy estrogen metabolism and cardiovascular elasticity.",
    "culinaryNotes": "Must be ground (milled) because human enzymes cannot breach the tough outer whole seed hull.",
    "culturalOrigin": "Fertile Crescent",
    "glycemicIndex": "Low"
  },
  {
    "id": "nut_pumpkin_seeds",
    "name": "Pumpkin Seeds (Pepitas)",
    "category": "Nuts & Seeds",
    "servingSize": "1 oz (28g)",
    "calories": 158,
    "proteinGrams": 8.6,
    "carbsGrams": 3,
    "fatGrams": 13.9,
    "fiberGrams": 1.7,
    "micronutrients": [
      {
        "name": "Magnesium",
        "amount": "156 mg",
        "dailyValuePercent": 37,
        "benefit": "Deep sleep & neuromuscular function"
      },
      {
        "name": "Zinc",
        "amount": "2.2 mg",
        "dailyValuePercent": 20,
        "benefit": "Immune and testosterone synthesis"
      },
      {
        "name": "L-Tryptophan",
        "amount": "160 mg",
        "dailyValuePercent": null,
        "benefit": "Serotonin and melatonin precursor"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "High-Protein",
      "High-Zinc",
      "Sleep-Support"
    ],
    "description": "Dark green seed delivering nearly 9g of plant protein per ounce and unparalleled natural magnesium and zinc.",
    "culinaryNotes": "Toasted lightly with sea salt and smoked paprika as a nutrient-packed crunchy snack.",
    "culturalOrigin": "North America / Mexico",
    "glycemicIndex": "Low"
  },
  {
    "id": "dairy_greek_yogurt",
    "name": "Plain Greek Yogurt (0-2% Fat)",
    "category": "Dairy & Alternatives",
    "servingSize": "1 cup (200g)",
    "calories": 146,
    "proteinGrams": 20,
    "carbsGrams": 7,
    "fatGrams": 3.8,
    "fiberGrams": 0,
    "micronutrients": [
      {
        "name": "Live Probiotics",
        "amount": "10 Billion CFU",
        "dailyValuePercent": null,
        "benefit": "L. bulgaricus & S. thermophilus"
      },
      {
        "name": "Calcium",
        "amount": "200 mg",
        "dailyValuePercent": 15,
        "benefit": "Bone & muscle contraction"
      },
      {
        "name": "Vitamin B12",
        "amount": "1.0 mcg",
        "dailyValuePercent": 42,
        "benefit": "Nerve myelination"
      }
    ],
    "dietaryTags": [
      "Vegetarian",
      "Gluten-Free",
      "High-Protein",
      "Probiotic",
      "Low-Sugar"
    ],
    "description": "Strained cultured yogurt with double the protein concentration of regular yogurt and live probiotic colonies.",
    "culinaryNotes": "Traditional foundation of Mediterranean Tzatziki and high-protein breakfast parfaits.",
    "culturalOrigin": "Eastern Mediterranean / Balkans",
    "glycemicIndex": "Low"
  },
  {
    "id": "dairy_cottage_cheese",
    "name": "Low-Fat Cottage Cheese / Paneer",
    "category": "Dairy & Alternatives",
    "servingSize": "1 cup (226g)",
    "calories": 163,
    "proteinGrams": 28,
    "carbsGrams": 6.2,
    "fatGrams": 2.3,
    "fiberGrams": 0,
    "micronutrients": [
      {
        "name": "Micellar Casein Protein",
        "amount": "24 g",
        "dailyValuePercent": null,
        "benefit": "Slow continuous amino acid release"
      },
      {
        "name": "Phosphorus",
        "amount": "303 mg",
        "dailyValuePercent": 24,
        "benefit": "Bone mineralization"
      },
      {
        "name": "Selenium",
        "amount": "20 mcg",
        "dailyValuePercent": 36,
        "benefit": "Antioxidant enzyme glutathione peroxidase"
      }
    ],
    "dietaryTags": [
      "Vegetarian",
      "Gluten-Free",
      "High-Protein",
      "Slow-Release"
    ],
    "description": "Concentrated casein curd protein that digests slowly over 6-8 hours, providing sustained muscular amino acid delivery.",
    "culinaryNotes": "Paneer is the unsalted South Asian variant essential in Saag Paneer and Matar Paneer.",
    "culturalOrigin": "Central Europe / South Asia",
    "glycemicIndex": "Low"
  },
  {
    "id": "dairy_soy_milk",
    "name": "Fortified Organic Soy Milk",
    "category": "Dairy & Alternatives",
    "servingSize": "1 cup (240ml)",
    "calories": 100,
    "proteinGrams": 7.5,
    "carbsGrams": 4,
    "fatGrams": 4,
    "fiberGrams": 1.5,
    "micronutrients": [
      {
        "name": "Vitamin D2/D3",
        "amount": "2.5 mcg",
        "dailyValuePercent": 13,
        "benefit": "Calcium absorption"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Dairy-Free",
      "High-Protein"
    ],
    "description": "The only plant-based milk alternative matching dairy milk's complete protein profile gram-for-gram.",
    "culinaryNotes": "Foams exceptionally well in coffee drinks due to natural soybean lecithin.",
    "culturalOrigin": "China / East Asia",
    "glycemicIndex": "Low"
  },
  {
    "id": "dairy_kefir",
    "name": "Kefir (Fermented Milk)",
    "category": "Dairy & Alternatives",
    "servingSize": "1 cup (243g)",
    "calories": 110,
    "proteinGrams": 9,
    "carbsGrams": 12,
    "fatGrams": 2.5,
    "fiberGrams": 0,
    "micronutrients": [
      {
        "name": "Probiotic Strains",
        "amount": "30+ Bacterial/Yeast Strains",
        "dailyValuePercent": null,
        "benefit": "Diverse microflora colonizer"
      },
      {
        "name": "Kefiran",
        "amount": "1.5 g",
        "dailyValuePercent": null,
        "benefit": "Antimicrobial exopolysaccharide"
      }
    ],
    "dietaryTags": [
      "Vegetarian",
      "Gluten-Free",
      "Probiotic-Superfood",
      "99%-Lactose-Free"
    ],
    "description": "Centuries-old fermented probiotic drink with 3x the microbial diversity of standard yogurt.",
    "culinaryNotes": "Naturally effervescent with a refreshing tart flavor.",
    "culturalOrigin": "Caucasus Mountains",
    "glycemicIndex": "Low"
  },
  {
    "id": "protein_salmon",
    "name": "Wild Atlantic / Sockeye Salmon",
    "category": "Proteins & Seafood",
    "servingSize": "3.5 oz cooked (100g)",
    "calories": 208,
    "proteinGrams": 22,
    "carbsGrams": 0,
    "fatGrams": 12.5,
    "fiberGrams": 0,
    "micronutrients": [
      {
        "name": "EPA & DHA Omega-3",
        "amount": "2.2 g",
        "dailyValuePercent": null,
        "benefit": "Cardiovascular and neuronal membranes"
      },
      {
        "name": "Astaxanthin",
        "amount": "3.0 mg",
        "dailyValuePercent": null,
        "benefit": "Potent carotenoid marine antioxidant"
      },
      {
        "name": "Vitamin D3",
        "amount": "14.2 mcg",
        "dailyValuePercent": 71,
        "benefit": "Immunity & hormone regulation"
      },
      {
        "name": "Vitamin B12",
        "amount": "3.2 mcg",
        "dailyValuePercent": 133,
        "benefit": "DNA & nerve health"
      }
    ],
    "dietaryTags": [
      "Pescatarian",
      "Gluten-Free",
      "Keto-Friendly",
      "Omega-3-Rich",
      "High-Protein"
    ],
    "description": "Premier fatty fish loaded with marine EPA/DHA omega-3s, natural astaxanthin, and bioavailable Vitamin D3.",
    "culinaryNotes": "Pan-searing skin-down locks in moisture and crisps omega-rich skin.",
    "culturalOrigin": "North Pacific / Atlantic",
    "glycemicIndex": "Low"
  },
  {
    "id": "protein_chicken_breast",
    "name": "Skinless Chicken Breast",
    "category": "Proteins & Seafood",
    "servingSize": "3.5 oz cooked (100g)",
    "calories": 165,
    "proteinGrams": 31,
    "carbsGrams": 0,
    "fatGrams": 3.6,
    "fiberGrams": 0,
    "micronutrients": [
      {
        "name": "Phosphorus",
        "amount": "228 mg",
        "dailyValuePercent": 18,
        "benefit": "ATP structure"
      },
      {
        "name": "Selenium",
        "amount": "27.6 mcg",
        "dailyValuePercent": 50,
        "benefit": "Thyroid protection"
      }
    ],
    "dietaryTags": [
      "Halal",
      "Gluten-Free",
      "High-Protein",
      "Low-Fat",
      "Lean-Meat"
    ],
    "description": "Gold-standard lean animal protein offering 31g of high biological value protein with minimal saturated fat.",
    "culinaryNotes": "Brining in saltwater with herbs before grilling prevents dry texture.",
    "culturalOrigin": "Global",
    "glycemicIndex": "Low"
  },
  {
    "id": "protein_egg",
    "name": "Whole Pasture-Raised Egg",
    "category": "Proteins & Seafood",
    "servingSize": "1 large egg (50g)",
    "calories": 72,
    "proteinGrams": 6.3,
    "carbsGrams": 0.4,
    "fatGrams": 4.8,
    "fiberGrams": 0,
    "micronutrients": [
      {
        "name": "Choline",
        "amount": "147 mg",
        "dailyValuePercent": 27,
        "benefit": "Acetylcholine neurotransmitter & liver"
      },
      {
        "name": "Lutein & Zeaxanthin",
        "amount": "252 mcg",
        "dailyValuePercent": null,
        "benefit": "Retinal macula protection"
      },
      {
        "name": "Biotin",
        "amount": "10 mcg",
        "dailyValuePercent": 33,
        "benefit": "Cell growth & metabolic enzyme"
      }
    ],
    "dietaryTags": [
      "Vegetarian-Friendly",
      "Gluten-Free",
      "Keto-Friendly",
      "Complete-Protein"
    ],
    "description": "Nature's nutrient multivitamin encapsulating all essential vitamins (except C) and vital brain-boosting choline.",
    "culinaryNotes": "Poaching or soft-boiling keeps yolk lipids unoxidized.",
    "culturalOrigin": "Global",
    "glycemicIndex": "Low"
  },
  {
    "id": "protein_tofu",
    "name": "Organic Firm Tofu",
    "category": "Proteins & Seafood",
    "servingSize": "1/2 cup (126g)",
    "calories": 117,
    "proteinGrams": 15,
    "carbsGrams": 2.8,
    "fatGrams": 7,
    "fiberGrams": 2,
    "micronutrients": [
      {
        "name": "Iron",
        "amount": "3.4 mg",
        "dailyValuePercent": 19,
        "benefit": "Oxygen carriage"
      },
      {
        "name": "Isoflavones",
        "amount": "35 mg",
        "dailyValuePercent": null,
        "benefit": "Antioxidant & lipid balance"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "High-Protein",
      "Plant-Based"
    ],
    "description": "Soy curd staple delivering complete plant protein, calcium, and iron with zero cholesterol.",
    "culinaryNotes": "Press out excess water and marinate in soy sauce, ginger, and garlic before pan-crisping.",
    "culturalOrigin": "Ancient China",
    "glycemicIndex": "Low"
  },
  {
    "id": "protein_tempeh",
    "name": "Fermented Soy Tempeh",
    "category": "Proteins & Seafood",
    "servingSize": "3 oz (85g)",
    "calories": 160,
    "proteinGrams": 18,
    "carbsGrams": 8,
    "fatGrams": 9,
    "fiberGrams": 5,
    "micronutrients": [
      {
        "name": "Prebiotic Isoflavones",
        "amount": "45 mg",
        "dailyValuePercent": null,
        "benefit": "Fermentation-enhanced bioactivity"
      },
      {
        "name": "Magnesium",
        "amount": "65 mg",
        "dailyValuePercent": 15,
        "benefit": "Cellular ATP"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "High-Protein",
      "High-Fiber",
      "Fermented"
    ],
    "description": "Traditional Indonesian whole fermented soybean cake with 18g protein and 5g fiber per serving.",
    "culinaryNotes": "Nutty, firm texture that absorbs rich spices, curries, and peanut sauces.",
    "culturalOrigin": "Java, Indonesia",
    "glycemicIndex": "Low"
  },
  {
    "id": "oil_olive",
    "name": "Extra Virgin Olive Oil (EVOO)",
    "category": "Oils & Healthy Fats",
    "servingSize": "1 tbsp (15ml / 14g)",
    "calories": 119,
    "proteinGrams": 0,
    "carbsGrams": 0,
    "fatGrams": 13.5,
    "fiberGrams": 0,
    "micronutrients": [
      {
        "name": "Oleocanthal & Oleuropein",
        "amount": "30 mg",
        "dailyValuePercent": null,
        "benefit": "COX-1/COX-2 inhibiting polyphenols"
      },
      {
        "name": "Vitamin E",
        "amount": "1.9 mg",
        "dailyValuePercent": 13,
        "benefit": "Antioxidant"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Keto-Friendly",
      "Mediterranean",
      "Heart-Healthy"
    ],
    "description": "Crown jewel of the Mediterranean Diet, cold-pressed with natural oleocanthal that mimics gentle anti-inflammatory pathways.",
    "culinaryNotes": "Best drizzled fresh over finished dishes, salads, and steamed legumes to protect delicate polyphenols.",
    "culturalOrigin": "Mediterranean Basin",
    "glycemicIndex": "Low"
  },
  {
    "id": "oil_ghee",
    "name": "Grass-Fed Desi Ghee (Clarified Butter)",
    "category": "Oils & Healthy Fats",
    "servingSize": "1 tbsp (14g)",
    "calories": 120,
    "proteinGrams": 0,
    "carbsGrams": 0,
    "fatGrams": 14,
    "fiberGrams": 0,
    "micronutrients": [
      {
        "name": "Vitamin A",
        "amount": "120 mcg",
        "dailyValuePercent": 13,
        "benefit": "Fat-soluble vitamin"
      }
    ],
    "dietaryTags": [
      "Vegetarian",
      "Gluten-Free",
      "Keto-Friendly",
      "Lactose-Free",
      "Ayurvedic"
    ],
    "description": "Slow-simmered clarified butter with milk solids removed, packed with short-chain butyrate for colon mucosal lining.",
    "culinaryNotes": "High smoke point (485°F / 250°C), iconic aroma in South Asian curries, dals, and khichdi.",
    "culturalOrigin": "Ancient South Asia",
    "glycemicIndex": "Low"
  },
  {
    "id": "bev_green_tea",
    "name": "Matcha / Green Tea",
    "category": "Beverages",
    "servingSize": "1 cup (240ml)",
    "calories": 2,
    "proteinGrams": 0.2,
    "carbsGrams": 0.4,
    "fatGrams": 0,
    "fiberGrams": 0,
    "micronutrients": [
      {
        "name": "L-Theanine",
        "amount": "25 mg",
        "dailyValuePercent": null,
        "benefit": "Promotes alpha brain waves and calm focus"
      },
      {
        "name": "Caffeine",
        "amount": "35 mg",
        "dailyValuePercent": null,
        "benefit": "Gentle metabolic stimulation"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Zero-Calorie",
      "Nootropic",
      "Antioxidant"
    ],
    "description": "Steeped Camellia sinensis leaves loaded with EGCG and L-Theanine for calm, sustained cognitive alertness without jitters.",
    "culinaryNotes": "Brew at 175°F (80°C) rather than boiling water to avoid scorching delicate tea catechins.",
    "culturalOrigin": "China / Japan",
    "glycemicIndex": "Low"
  },
  {
    "id": "bev_coconut_water",
    "name": "Fresh Coconut Water",
    "category": "Beverages",
    "servingSize": "1 cup (240ml)",
    "calories": 45,
    "proteinGrams": 1.7,
    "carbsGrams": 8.9,
    "fatGrams": 0.5,
    "fiberGrams": 2.6,
    "micronutrients": [
      {
        "name": "Potassium",
        "amount": "600 mg",
        "dailyValuePercent": 13,
        "benefit": "Natural isotonic electrolyte"
      },
      {
        "name": "Sodium",
        "amount": "252 mg",
        "dailyValuePercent": 11,
        "benefit": "Hydration balance"
      },
      {
        "name": "Magnesium",
        "amount": "60 mg",
        "dailyValuePercent": 14,
        "benefit": "Electrolyte homeostasis"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Isotonic",
      "Hydration"
    ],
    "description": "Natural isotonic fluid containing the 5 essential human electrolytes: potassium, sodium, magnesium, calcium, and phosphorus.",
    "culinaryNotes": "Ideal natural post-workout rehydration beverage without artificial colorings or refined syrups.",
    "culturalOrigin": "Tropical Regions / South & Southeast Asia",
    "glycemicIndex": "Low"
  },
  {
    "id": "dish_chicken_biryani",
    "name": "Chicken Biryani with Mint Raita",
    "category": "Prepared Cultural Dishes",
    "servingSize": "1 plate (350g)",
    "calories": 520,
    "proteinGrams": 32,
    "carbsGrams": 68,
    "fatGrams": 14,
    "fiberGrams": 4.2,
    "micronutrients": [
      {
        "name": "Saffron Crocin",
        "amount": "8 mg",
        "dailyValuePercent": null,
        "benefit": "Mood and neurological support"
      },
      {
        "name": "Iron",
        "amount": "3.2 mg",
        "dailyValuePercent": 18,
        "benefit": "Oxygenation"
      }
    ],
    "dietaryTags": [
      "Halal",
      "High-Protein",
      "South-Asian",
      "Aromatic-Spices"
    ],
    "description": "Celebrated South Asian fragrant basmati rice layered with tender spiced marinated chicken, saffron, mint, and cooling yogurt raita.",
    "culinaryNotes": "Pairing with cucumber-mint raita cools capsaicin and adds live probiotic enzymes that ease grain digestion.",
    "culturalOrigin": "South Asia (Mughlai / Hyderabadi / Pakistani)",
    "glycemicIndex": "Low"
  },
  {
    "id": "dish_chana_masala",
    "name": "Chana Masala with Whole Wheat Roti",
    "category": "Prepared Cultural Dishes",
    "servingSize": "1 bowl chana + 1 roti (300g)",
    "calories": 380,
    "proteinGrams": 16.5,
    "carbsGrams": 62,
    "fatGrams": 7.5,
    "fiberGrams": 14,
    "micronutrients": [
      {
        "name": "Dietary Fiber",
        "amount": "14 g",
        "dailyValuePercent": 50,
        "benefit": "Half of daily fiber requirement"
      },
      {
        "name": "Folate",
        "amount": "220 mcg",
        "dailyValuePercent": 55,
        "benefit": "Cellular division"
      },
      {
        "name": "Gingerol & Piperine",
        "amount": "15 mg",
        "dailyValuePercent": null,
        "benefit": "Digestive enzyme stimulants"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "High-Fiber",
      "South-Asian",
      "Plant-Protein"
    ],
    "description": "Tender chickpeas simmered in a spiced tomato, ginger, garlic, and amchur (dry mango) gravy, served with handmade atta roti.",
    "culinaryNotes": "A complete nutritional synergy: legumes supply lysine while wheat provides methionine for balanced amino acid intake.",
    "culturalOrigin": "Northern South Asia (Punjab)",
    "glycemicIndex": "Low"
  },
  {
    "id": "dish_dal_tadka",
    "name": "Yellow Lentil Dal Tadka with Steamed Rice & Salad",
    "category": "Prepared Cultural Dishes",
    "servingSize": "1 serving (320g)",
    "calories": 340,
    "proteinGrams": 15,
    "carbsGrams": 56,
    "fatGrams": 6,
    "fiberGrams": 9.5,
    "micronutrients": [
      {
        "name": "Iron",
        "amount": "4.8 mg",
        "dailyValuePercent": 27,
        "benefit": "Energy and hemoglobin"
      },
      {
        "name": "Turmeric Curcumin",
        "amount": "35 mg",
        "dailyValuePercent": null,
        "benefit": "Cellular protection"
      },
      {
        "name": "Potassium",
        "amount": "580 mg",
        "dailyValuePercent": 12,
        "benefit": "Blood pressure balance"
      }
    ],
    "dietaryTags": [
      "Vegetarian",
      "Gluten-Free-Option",
      "South-Asian",
      "Heart-Healthy"
    ],
    "description": "Comforting golden lentils tempered with aromatic cumin seeds, garlic, ghee, and fresh cilantro, served with crisp cucumber-tomato kachumber.",
    "culinaryNotes": "A daily balanced meal in millions of South Asian homes, rich in polyphenols and gentle prebiotic fiber.",
    "culturalOrigin": "South Asia",
    "glycemicIndex": "Low"
  },
  {
    "id": "dish_chicken_karahi",
    "name": "Chicken Karahi (Pakistani Style)",
    "category": "Prepared Cultural Dishes",
    "servingSize": "1 portion (250g)",
    "calories": 360,
    "proteinGrams": 34,
    "carbsGrams": 8,
    "fatGrams": 21,
    "fiberGrams": 2.4,
    "micronutrients": [
      {
        "name": "Gingerol",
        "amount": "18 mg",
        "dailyValuePercent": null,
        "benefit": "Digestive stimulant"
      }
    ],
    "dietaryTags": [
      "Halal",
      "High-Protein",
      "Low-Carb",
      "South-Asian"
    ],
    "description": "Tender chicken wok-cooked with ripe tomatoes, ginger juliennes, green chilies, and black pepper without heavy onions or flour thickeners.",
    "culinaryNotes": "High in bioavailable lean protein and lycopene synthesized by cooking tomatoes in healthy oil.",
    "culturalOrigin": "Pakistan (Khyber Pakhtunkhwa / Lahore)",
    "glycemicIndex": "Low"
  },
  {
    "id": "dish_hummus_falafel",
    "name": "Mediterranean Hummus & Baked Falafel Bowl",
    "category": "Prepared Cultural Dishes",
    "servingSize": "1 bowl (320g)",
    "calories": 410,
    "proteinGrams": 18,
    "carbsGrams": 48,
    "fatGrams": 17,
    "fiberGrams": 13,
    "micronutrients": [
      {
        "name": "Folate",
        "amount": "240 mcg",
        "dailyValuePercent": 60,
        "benefit": "DNA repair"
      },
      {
        "name": "Calcium",
        "amount": "165 mg",
        "dailyValuePercent": 13,
        "benefit": "Bone health"
      }
    ],
    "dietaryTags": [
      "Vegan",
      "Gluten-Free",
      "Mediterranean",
      "High-Fiber"
    ],
    "description": "Crispy herb-infused chickpea falafels served over creamy tahini hummus, kalamata olives, cucumber, and fresh parsley.",
    "culinaryNotes": "Tahini (sesame paste) adds essential minerals like calcium, zinc, and healthy unsaturated fatty acids.",
    "culturalOrigin": "Levant / Middle East",
    "glycemicIndex": "Low"
  },
  {
    "id": "dish_shawarma_plate",
    "name": "Grilled Chicken Shawarma Plate with Fattoush",
    "category": "Prepared Cultural Dishes",
    "servingSize": "1 plate (340g)",
    "calories": 460,
    "proteinGrams": 36,
    "carbsGrams": 32,
    "fatGrams": 20,
    "fiberGrams": 5.5,
    "micronutrients": [
      {
        "name": "Sumac Polyphenols",
        "amount": "32 mg",
        "dailyValuePercent": null,
        "benefit": "Antioxidant spice profile"
      },
      {
        "name": "Vitamin C",
        "amount": "45 mg",
        "dailyValuePercent": 50,
        "benefit": "Tissue synthesis"
      },
      {
        "name": "Zinc",
        "amount": "3.4 mg",
        "dailyValuePercent": 31,
        "benefit": "Immune resilience"
      }
    ],
    "dietaryTags": [
      "Halal",
      "High-Protein",
      "Middle-Eastern",
      "Balanced"
    ],
    "description": "Cardamom and sumac spiced chicken breast paired with garlic toum, pickled turnip, and sumac-dressed crunchy fattoush salad.",
    "culinaryNotes": "Sumac is one of the highest ORAC antioxidant spices in the world, providing natural tartness.",
    "culturalOrigin": "Levant / Middle East",
    "glycemicIndex": "Low"
  },
  {
    "id": "dish_ramen_chashu",
    "name": "Japanese Ramen with Soft-Boiled Egg & Bok Choy",
    "category": "Prepared Cultural Dishes",
    "servingSize": "1 large bowl (450g)",
    "calories": 490,
    "proteinGrams": 24,
    "carbsGrams": 62,
    "fatGrams": 16,
    "fiberGrams": 4,
    "micronutrients": [
      {
        "name": "Glutamate Umami",
        "amount": "1.2 g",
        "dailyValuePercent": null,
        "benefit": "Natural savory satiety signaling"
      }
    ],
    "dietaryTags": [
      "East-Asian",
      "Satiating",
      "Umami-Rich"
    ],
    "description": "Wheat ramen noodles in rich savory broth topped with soft ajitsuke tamago egg, tender protein, nori seaweed, and steamed bok choy.",
    "culinaryNotes": "Adding nutrient-dense nori and bok choy supplies trace marine iodine and carotenoids.",
    "culturalOrigin": "Japan",
    "glycemicIndex": "Low"
  },
  {
    "id": "dish_salmon_sushi",
    "name": "Salmon Nigiri & Avocado Roll Set",
    "category": "Prepared Cultural Dishes",
    "servingSize": "8 pieces (220g)",
    "calories": 360,
    "proteinGrams": 19,
    "carbsGrams": 46,
    "fatGrams": 11,
    "fiberGrams": 3.5,
    "micronutrients": [
      {
        "name": "Vitamin B12",
        "amount": "2.1 mcg",
        "dailyValuePercent": 88,
        "benefit": "Nerve conduction"
      }
    ],
    "dietaryTags": [
      "Pescatarian",
      "High-Omega-3",
      "East-Asian",
      "Clean-Eating"
    ],
    "description": "Fresh raw sashimi-grade salmon over seasoned sushi rice with avocado slices and mineral-rich nori sheets.",
    "culinaryNotes": "Wasabi provides natural isothiocyanates with antimicrobial and anti-inflammatory properties.",
    "culturalOrigin": "Japan",
    "glycemicIndex": "Low"
  },
  {
    "id": "dish_greek_salad_tzatziki",
    "name": "Mediterranean Greek Salad with Kalamata & Feta",
    "category": "Prepared Cultural Dishes",
    "servingSize": "1 large bowl (280g)",
    "calories": 290,
    "proteinGrams": 9,
    "carbsGrams": 12,
    "fatGrams": 23,
    "fiberGrams": 4.5,
    "micronutrients": [
      {
        "name": "EVOO Polyphenols",
        "amount": "40 mg",
        "dailyValuePercent": null,
        "benefit": "Vascular health"
      },
      {
        "name": "Lycopene",
        "amount": "8.5 mg",
        "dailyValuePercent": null,
        "benefit": "Antioxidant protection"
      },
      {
        "name": "Calcium",
        "amount": "240 mg",
        "dailyValuePercent": 18,
        "benefit": "Bone mineral"
      }
    ],
    "dietaryTags": [
      "Vegetarian",
      "Gluten-Free",
      "Keto-Friendly",
      "Mediterranean",
      "Low-Carb"
    ],
    "description": "Crisp cucumbers, vine tomatoes, red onions, kalamata olives, and sheep's milk feta tossed in cold-pressed extra virgin olive oil and wild oregano.",
    "culinaryNotes": "Classic representation of raw vegetable polyphenol absorption mediated by monounsaturated fats.",
    "culturalOrigin": "Greece / Mediterranean",
    "glycemicIndex": "Low"
  },
  {
    "id": "dish_burrito_bowl",
    "name": "Chipotle-Style Chicken & Black Bean Quinoa Bowl",
    "category": "Prepared Cultural Dishes",
    "servingSize": "1 bowl (360g)",
    "calories": 480,
    "proteinGrams": 35,
    "carbsGrams": 52,
    "fatGrams": 14,
    "fiberGrams": 11,
    "micronutrients": [
      {
        "name": "Plant & Animal Protein Matrix",
        "amount": "35 g",
        "dailyValuePercent": null,
        "benefit": "Optimal leucine and branched-chain aminos"
      },
      {
        "name": "Dietary Fiber",
        "amount": "11 g",
        "dailyValuePercent": 39,
        "benefit": "Microbiome fuel"
      },
      {
        "name": "Potassium",
        "amount": "720 mg",
        "dailyValuePercent": 15,
        "benefit": "Electrolyte balance"
      }
    ],
    "dietaryTags": [
      "Gluten-Free",
      "High-Protein",
      "High-Fiber",
      "Latin-American"
    ],
    "description": "Grilled adobo chicken served over tri-color quinoa, black beans, charred corn salsa, avocado chunks, and cilantro lime dressing.",
    "culinaryNotes": "Balancing high fiber beans with lean chicken and avocado creates hours of steady glycemic satiety.",
    "culturalOrigin": "Mexico / Americas",
    "glycemicIndex": "Low"
  }
];

const ARTICLE_CATEGORIES = [
  { id: "all", displayName: "All Topics" },
  { id: "MACRONUTRIENTS", displayName: "Macronutrient Mechanics" },
  { id: "MINERALS", displayName: "Vitamins & Minerals" },
  { id: "FUNDAMENTALS", displayName: "Foundations" },
  { id: "PRACTICAL", displayName: "Practical Habits" },
  { id: "PATTERNS", displayName: "Dietary Patterns" },
  { id: "ACTIVE", displayName: "Active Nutrition" },
  { id: "LIFE_STAGES", displayName: "Life-Stage" }
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
  },
  {
    id: "art_energy_balance_metabolism",
    slug: "energy-balance-metabolism",
    title: "Energy Balance & Basal Metabolic Rate: The Thermodynamics of Nutrition",
    summary: "Explore total daily energy expenditure (TDEE), basal metabolic rate (BMR), the thermic effect of food (TEF), and non-exercise activity thermogenesis (NEAT).",
    category: "FUNDAMENTALS",
    readingTimeMin: 5,
    difficulty: "Beginner",
    sections: [
      {
        heading: "The Components of Daily Energy Expenditure",
        content: "Total Daily Energy Expenditure (TDEE) comprises four distinct components: Basal Metabolic Rate (BMR, ~60-70%), the Thermic Effect of Food (TEF, ~10%), Exercise Activity Thermogenesis (EAT, ~5-15%), and Non-Exercise Activity Thermogenesis (NEAT, ~15-30%)."
      },
      {
        heading: "The Thermic Effect of Food (TEF)",
        content: "Digestive metabolism burns energy: dietary protein exhibits the highest TEF at 20–30% of its caloric value, compared to 5–10% for carbohydrates and 0–3% for fats. A high-protein diet slightly elevates resting metabolic rate purely through the metabolic cost of peptide cleavage and urea synthesis."
      },
      {
        heading: "NEAT: The Variable Multiplier",
        content: "Spontaneous daily movement (walking, standing, posture, fidgeting) accounts for substantial variance in daily calorie burn. Elevating daily step count from 4,000 to 10,000 steps increases NEAT by 300–500 kcal per day without taxing recovery capacity."
      }
    ],
    keyTakeaways: [
      "BMR represents the baseline energy required to sustain vital organ function.",
      "Dietary protein requires 20-30% of its energy simply to be digested and metabolized.",
      "Daily walking and spontaneous movement (NEAT) are powerful metabolic levers for long-term health."
    ]
  },
  {
    id: "art_life_stage_nutrition",
    slug: "life-stage-nutrition",
    title: "Life-Stage Nutrition: Supporting Growth, Adulthood & Healthy Aging",
    summary: "How nutritional demands shift across decades: optimizing bone density in youth, metabolic health in midlife, and anabolic resistance mitigation in older adulthood.",
    category: "LIFE_STAGES",
    readingTimeMin: 6,
    difficulty: "Intermediate",
    sections: [
      {
        heading: "Peak Bone Mass & Early Adulthood",
        content: "The human skeleton achieves 90% of peak bone mass by age 20. Ensuring adequate calcium, vitamin D3, vitamin K2, and resistance training during adolescence and early adulthood lays the structural mineral foundation for osteoporosis prevention decades later."
      },
      {
        heading: "Midlife Metabolic Resilience",
        content: "During middle adulthood, basal metabolic rate gradually decreases unless preserved by lean skeletal muscle mass. Prioritizing dietary fiber (30g+), antioxidant-dense polyphenols, and omega-3 lipids preserves vascular endothelial flexibility and insulin receptor sensitivity."
      },
      {
        heading: "Overcoming Anabolic Resistance in Aging",
        content: "Older adults experience anabolic resistance, meaning skeletal muscle requires a higher per-meal leucine threshold (~3.0-3.5g) to initiate muscle protein synthesis. Higher daily protein targets (1.2-1.6g/kg) and bioavailable B12/Vitamin D supplementation help preserve muscle mass (sarcopenia prevention) and functional independence."
      }
    ],
    keyTakeaways: [
      "Early life focuses on building peak bone mineral density through calcium, vitamin D3, and weight-bearing exercise.",
      "Midlife demands insulin sensitivity preservation through fiber, polyphenols, and whole-food matrices.",
      "Older adulthood requires higher per-meal protein (30-40g) to overcome anabolic resistance and preserve muscle strength."
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

const DIET_PLANNER_PRESETS = {
  "muscle_synthesis": {
    name: "Muscle Synthesis & Athletic Recovery",
    tagline: "High-protein distribution hitting the leucine threshold across all meals.",
    dailyTargets: { calories: 2350, protein: 165, carbs: 250, fats: 75, fiber: 38 },
    days: [
      {
        dayNumber: 1,
        meals: [
          { type: "Breakfast", title: "3-Egg Omelet with Spinach, Feta & Sourdough", calories: 480, p: 32, c: 38, f: 22, fib: 5, notes: "Rich in choline, bioavailable leucine, and lutein." },
          { type: "Lunch", title: "Grilled Salmon, Quinoa & Steamed Broccoli Bowl", calories: 620, p: 44, c: 52, f: 24, fib: 9, notes: "Provides 1800mg EPA/DHA marine omega-3s for myofibrillar recovery." },
          { type: "Snack", title: "Greek Yogurt with Berries & Raw Pumpkin Seeds", calories: 280, p: 24, c: 22, f: 10, fib: 4, notes: "Delivers slow-digesting micellar casein and zinc." },
          { type: "Dinner", title: "Herb-Roasted Chicken Breast with Sweet Potato & Asparagus", calories: 650, p: 52, c: 58, f: 18, fib: 8, notes: "Replenishes muscle glycogen and provides sustained amino acid release." }
        ]
      },
      {
        dayNumber: 2,
        meals: [
          { type: "Breakfast", title: "Overnight Rolled Oats with Whey Isolate, Chia & Blueberries", calories: 510, p: 36, c: 58, f: 14, fib: 11, notes: "Beta-glucan soluble fiber with fast and slow protein kinetics." },
          { type: "Lunch", title: "Mediterranean Turkey & Chickpea Power Bowl", calories: 590, p: 46, c: 48, f: 22, fib: 10, notes: "Paired with tahini dressing and fresh parsley for non-heme iron absorption." },
          { type: "Snack", title: "Cottage Cheese with Sliced Kiwi & Walnuts", calories: 260, p: 22, c: 16, f: 11, fib: 3, notes: "High in tryptophan and potassium for evening nervous system recovery." },
          { type: "Dinner", title: "Grilled Sirloin Steak, Roasted Baby Potatoes & Sauteed Greens", calories: 680, p: 50, c: 54, f: 26, fib: 7, notes: "Natural creatine, heme iron, and B12 for energetic recharge." }
        ]
      },
      {
        dayNumber: 3,
        meals: [
          { type: "Breakfast", title: "Tofu & Egg White Scramble with Avocado Toast", calories: 460, p: 30, c: 36, f: 20, fib: 8, notes: "Balanced isoflavones, monounsaturated fats, and potassium." },
          { type: "Lunch", title: "Tuna Niçoise Salad with Boiled Eggs, Olives & Green Beans", calories: 570, p: 48, c: 28, f: 28, fib: 7, notes: "Clean lean marine protein with healthy olive oil polyphenols." },
          { type: "Snack", title: "Edamame Pods with Sea Salt & Apple Slices", calories: 230, p: 18, c: 26, f: 6, fib: 8, notes: "Whole soy protein providing all 9 essential amino acids." },
          { type: "Dinner", title: "Chipotle-Style Chicken & Black Bean Quinoa Bowl", calories: 660, p: 48, c: 68, f: 20, fib: 12, notes: "Legume-grain synergy with antioxidant salsa and lime juice." }
        ]
      }
    ]
  },
  "metabolic_health": {
    name: "Metabolic Health & Glucose Stability",
    tagline: "Low glycemic index, rich in prebiotic fiber, polyphenols, and healthy fats.",
    dailyTargets: { calories: 1850, protein: 115, carbs: 145, fats: 85, fiber: 42 },
    days: [
      {
        dayNumber: 1,
        meals: [
          { type: "Breakfast", title: "Chia Seed Pudding with Unsweetened Almond Milk & Raspberries", calories: 340, p: 12, c: 28, f: 18, fib: 14, notes: "Extremely low glycemic load with high mucilage soluble fiber." },
          { type: "Lunch", title: "Mediterranean Lentil Salad with Feta, Cucumber & Olive Oil", calories: 520, p: 26, c: 48, f: 24, fib: 15, notes: "Slow-fermenting resistant starch stabilizing afternoon glucose." },
          { type: "Snack", title: "Raw Walnuts & Celery Sticks with Almond Butter", calories: 240, p: 6, c: 8, f: 21, fib: 4, notes: "Zero sugar spike with cardioprotective alpha-linolenic acid." },
          { type: "Dinner", title: "Pan-Seared Wild Salmon with Garlic Sautéed Spinach & Cauliflower Mash", calories: 580, p: 44, c: 18, f: 36, fib: 8, notes: "Rich in potassium, magnesium, and anti-inflammatory EPA/DHA." }
        ]
      },
      {
        dayNumber: 2,
        meals: [
          { type: "Breakfast", title: "Poached Eggs over Wilted Kale & Half Avocado", calories: 380, p: 18, c: 12, f: 28, fib: 7, notes: "Zero refined carbs to kickstart insulin sensitivity." },
          { type: "Lunch", title: "Grilled Herb Chicken Breast with Greek Salad & Extra Virgin Olive Oil", calories: 540, p: 42, c: 16, f: 34, fib: 6, notes: "Polyphenol-rich kalamata olives and oregano." },
          { type: "Snack", title: "Roasted Pumpkin Seeds with Green Tea", calories: 200, p: 10, c: 6, f: 16, fib: 3, notes: "High magnesium (37% DV) promoting insulin signaling pathways." },
          { type: "Dinner", title: "Spiced Chickpea & Spinach Stew (Chana Palak) with Side Raita", calories: 560, p: 24, c: 64, f: 20, fib: 14, notes: "Turmeric curcumin and probiotic lactic acid bacteria." }
        ]
      },
      {
        dayNumber: 3,
        meals: [
          { type: "Breakfast", title: "Steel-Cut Oats with Cinnamon, Flaxseed & Crushed Pecans", calories: 370, p: 12, c: 42, f: 18, fib: 9, notes: "Cinnamaldehyde moderates postprandial glucose excursions." },
          { type: "Lunch", title: "Baked Tempeh Buddha Bowl with Roasted Broccoli & Tahini", calories: 530, p: 32, c: 34, f: 28, fib: 12, notes: "Fermented prebiotic substrate with complete plant protein." },
          { type: "Snack", title: "Dark Chocolate (85% Cacao) with Handful of Raw Almonds", calories: 220, p: 6, c: 12, f: 18, fib: 4, notes: "Flavanols supporting endothelial nitric oxide production." },
          { type: "Dinner", title: "Grilled Cod Loin with Lemon, Asparagus & Herbed Wild Rice", calories: 540, p: 40, c: 42, f: 18, fib: 7, notes: "Lean marine protein paired with low-glycemic intact grains." }
        ]
      }
    ]
  },
  "plant_vitality": {
    name: "Plant-Based Vitality & Diverse Ecology",
    tagline: "30+ plant species weekly, optimizing microbiome diversity and short-chain fatty acids.",
    dailyTargets: { calories: 2050, protein: 105, carbs: 285, fats: 65, fiber: 52 },
    days: [
      {
        dayNumber: 1,
        meals: [
          { type: "Breakfast", title: "Superfood Oatmeal with Hemp Seeds, Ground Flax & Mixed Berries", calories: 440, p: 18, c: 62, f: 14, fib: 14, notes: "Delivers 5 distinct plant species in a single bowl." },
          { type: "Lunch", title: "Moroccan Spiced Chickpea & Butternut Squash Tagine", calories: 580, p: 22, c: 84, f: 16, fib: 18, notes: "Rich in beta-carotene, cumin, ginger, and prebiotic fiber." },
          { type: "Snack", title: "Edamame & Carrot Sticks with Creamy Garlic Hummus", calories: 260, p: 14, c: 26, f: 11, fib: 8, notes: "Combines legumes, alliums, and root vegetables." },
          { type: "Dinner", title: "Crispy Baked Tofu with Quinoa, Bok Choy & Ginger Tamari Glaze", calories: 620, p: 34, c: 68, f: 22, fib: 11, notes: "Complete essential amino acid score with brassica glucosinolates." }
        ]
      },
      {
        dayNumber: 2,
        meals: [
          { type: "Breakfast", title: "Spiced Golden Tofu Scramble with Sautéed Peppers & Sourdough", calories: 420, p: 26, c: 42, f: 16, fib: 7, notes: "Turmeric, nutritional yeast (B-complex), and lycopene." },
          { type: "Lunch", title: "South Asian Yellow Dal Tadka with Brown Basmati & Tomato Kachumber", calories: 590, p: 24, c: 88, f: 14, fib: 16, notes: "Classic legume-grain pairing with fresh lemon for iron synergy." },
          { type: "Snack", title: "Chia Berry Pudding with Toasted Almonds", calories: 250, p: 8, c: 22, f: 14, fib: 9, notes: "Soluble mucilage fibers soothing gut epithelial linings." },
          { type: "Dinner", title: "Black Bean & Roasted Corn Enchilada Casserole with Guacamole", calories: 640, p: 26, c: 82, f: 22, fib: 17, notes: "High resistant starch yielding colonic butyrate production." }
        ]
      },
      {
        dayNumber: 3,
        meals: [
          { type: "Breakfast", title: "Green Smoothie Bowl with Spinach, Spirulina, Banana & Hemp Hearts", calories: 410, p: 18, c: 56, f: 12, fib: 10, notes: "Chlorophyll, bioavailable lutein, and magnesium." },
          { type: "Lunch", title: "Tempeh Reuben Bowl with Warm Sauerkraut & Rye Croutons", calories: 560, p: 32, c: 48, f: 24, fib: 12, notes: "Living probiotic lactic acid bacteria supporting gut microbiota." },
          { type: "Snack", title: "Roasted Spiced Chickpeas (Crispy Garbanzos)", calories: 220, p: 10, c: 30, f: 6, fib: 8, notes: "High-satiety crunchy snack rich in folate and manganese." },
          { type: "Dinner", title: "Red Lentil Coconut Curry with Steamed Kale & Wild Rice", calories: 660, p: 28, c: 84, f: 22, fib: 14, notes: "Comforting, anti-inflammatory whole food plant matrix." }
        ]
      }
    ]
  },
  "mediterranean_longevity": {
    name: "Mediterranean Heart-Healthy Longevity",
    tagline: "Extra virgin olive oil foundation, wild seafood, fresh herbs, and colorful produce.",
    dailyTargets: { calories: 2100, protein: 125, carbs: 210, fats: 90, fiber: 40 },
    days: [
      {
        dayNumber: 1,
        meals: [
          { type: "Breakfast", title: "Greek Yogurt with Fig Slices, Walnuts & Drizzle of Wild Honey", calories: 420, p: 24, c: 38, f: 18, fib: 5, notes: "Probiotic Lactobacillus paired with prebiotic polyphenols." },
          { type: "Lunch", title: "Classic Greek Village Salad with Grilled Sardines & Sourdough", calories: 580, p: 38, c: 36, f: 30, fib: 7, notes: "Calcium from edible fish bones and oleic acid from Kalamata olives." },
          { type: "Snack", title: "Fresh Orange Slices with Handful of Raw Pistachios", calories: 220, p: 6, c: 22, f: 13, fib: 5, notes: "Hesperidin citrus bioflavonoid supporting blood vessel elasticity." },
          { type: "Dinner", title: "Mediterranean Baked Cod with Tomatoes, Capers, Garlic & Rosemary Potatoes", calories: 640, p: 44, c: 54, f: 26, fib: 8, notes: "Rich in allicin, rosemary rosmarinic acid, and clean lean protein." }
        ]
      },
      {
        dayNumber: 2,
        meals: [
          { type: "Breakfast", title: "Spanish Pan con Tomate with Soft-Boiled Eggs & Jamón / Olive Oil", calories: 450, p: 22, c: 34, f: 24, fib: 4, notes: "Lycopene bioavailability amplified by raw extra virgin olive oil." },
          { type: "Lunch", title: "White Bean & Rosemary Stew (Ribollita) with Crusty Whole Grain Bread", calories: 560, p: 24, c: 72, f: 18, fib: 16, notes: "Tuscan longevity classic rich in slow-digesting complex carbs." },
          { type: "Snack", title: "Pomegranate Arils with Crumbled Feta Cheese", calories: 190, p: 6, c: 20, f: 9, fib: 4, notes: "Punicalagin tannins promoting cardiovascular arterial flexibility." },
          { type: "Dinner", title: "Grilled Herb Lamb Skewers with Tzatziki, Cucumber Salad & Bulgur Pilaf", calories: 670, p: 48, c: 46, f: 32, fib: 9, notes: "Natural zinc, carnosine, and cooling mint-garlic probiotics." }
        ]
      },
      {
        dayNumber: 3,
        meals: [
          { type: "Breakfast", title: "Herb Frittata with Leeks, Feta & Sliced Avocado", calories: 430, p: 24, c: 14, f: 30, fib: 6, notes: "High lutein/zeaxanthin eye protection with steady morning energy." },
          { type: "Lunch", title: "Falafel & Hummus Plate with Tabbouleh (Parsley & Bulgur)", calories: 600, p: 22, c: 68, f: 28, fib: 14, notes: "Massive vitamin C dose from parsley enhancing chickpea iron." },
          { type: "Snack", title: "Marinated Artichoke Hearts with Roasted Almonds", calories: 210, p: 6, c: 12, f: 16, fib: 6, notes: "Cynarin from artichokes stimulating healthy bile production." },
          { type: "Dinner", title: "Seafood Paella with Mussels, Shrimp, Saffron & Sweet Peppers", calories: 650, p: 46, c: 62, f: 22, fib: 6, notes: "Saffron carotenoids (crocin) and dense marine iodine/selenium." }
        ]
      }
    ]
  },
  "healthy_maintenance": {
    name: "Healthy Maintenance & Longevity",
    tagline: "Equally balanced whole foods, steady glycemic index, and antioxidant defense.",
    dailyTargets: { calories: 2000, protein: 120, carbs: 225, fats: 70, fiber: 38 },
    days: [
      {
        dayNumber: 1,
        meals: [
          { type: "Breakfast", title: "Steel-Cut Oats with Chia Seeds, Sliced Banana & Crushed Walnuts", calories: 420, p: 14, c: 62, f: 16, fib: 11, notes: "Beta-glucan soluble fibers supporting healthy lipid profiles." },
          { type: "Lunch", title: "Grilled Salmon Quinoa Bowl with Sautéed Baby Spinach & Avocado", calories: 580, p: 42, c: 48, f: 22, fib: 9.5, notes: "Marine EPA/DHA paired with carotenoids and folate." },
          { type: "Snack", title: "Fresh Apple Slices with Creamy Almond Butter", calories: 210, p: 5, c: 24, f: 12, fib: 5, notes: "Pectin fiber and vitamin E for sustained afternoon satiety." },
          { type: "Dinner", title: "Herb-Roasted Chicken Breast with Sweet Potato Mash & Steamed Broccoli", calories: 590, p: 48, c: 54, f: 16, fib: 9, notes: "Balanced complete amino acids, beta-carotene, and sulforaphane." }
        ]
      },
      {
        dayNumber: 2,
        meals: [
          { type: "Breakfast", title: "Greek Yogurt Parfait with Wild Blueberries, Pumpkin Seeds & Honey", calories: 380, p: 24, c: 36, f: 14, fib: 6, notes: "Probiotics paired with anthocyanin bioflavonoids." },
          { type: "Lunch", title: "Mediterranean Lentil Salad with Feta, Vine Tomatoes & Extra Virgin Olive Oil", calories: 540, p: 26, c: 56, f: 22, fib: 14, notes: "Slow-fermenting prebiotic fiber with oleic acid synergy." },
          { type: "Snack", title: "Handful of Raw Mixed Nuts (Almonds, Walnuts, Pistachios)", calories: 200, p: 6, c: 8, f: 18, fib: 4, notes: "Cardioprotective plant sterols and magnesium." },
          { type: "Dinner", title: "Pan-Seared White Cod Loin with Wild Brown Rice & Grilled Asparagus", calories: 560, p: 44, c: 52, f: 14, fib: 7, notes: "Clean lean marine protein and B-vitamins." }
        ]
      },
      {
        dayNumber: 3,
        meals: [
          { type: "Breakfast", title: "2-Egg Veggie Scramble with Whole Grain Sourdough & Sliced Tomatoes", calories: 400, p: 22, c: 32, f: 20, fib: 5, notes: "Choline and lutein for neurological and ocular support." },
          { type: "Lunch", title: "Warm Tempeh & Roasted Vegetable Bowl with Lemon Tahini Dressing", calories: 560, p: 30, c: 46, f: 26, fib: 11, notes: "Fermented prebiotic plant protein with bioavailable sesame calcium." },
          { type: "Snack", title: "Roasted Spiced Chickpeas (Crispy Garbanzos)", calories: 190, p: 8, c: 26, f: 5, fib: 7, notes: "Crunchy high-satiety legume snack." },
          { type: "Dinner", title: "Lean Turkey Meatballs with Marinara Sauce over Whole Wheat Spaghetti", calories: 610, p: 46, c: 64, f: 18, fib: 9, notes: "Rich in cooked tomato lycopene and complex carbohydrates." }
        ]
      }
    ]
  },
  "active_performance": {
    name: "Active Performance & High Energy",
    tagline: "Carbohydrate replenishment, electrolyte hydration, and rapid recovery timing.",
    dailyTargets: { calories: 2600, protein: 155, carbs: 340, fats: 72, fiber: 44 },
    days: [
      {
        dayNumber: 1,
        meals: [
          { type: "Breakfast", title: "Power Porridge with Rolled Oats, Whey Protein, Banana & Peanut Butter", calories: 560, p: 38, c: 74, f: 16, fib: 10, notes: "High glycogen loading potential with rapid and sustained amino release." },
          { type: "Lunch", title: "Teriyaki Grilled Chicken Breast with Jasmine Rice & Steamed Edamame", calories: 680, p: 52, c: 84, f: 14, fib: 8, notes: "Fast-absorbing liver and muscle glycogen restoration." },
          { type: "Snack", title: "Whole Wheat Pita with Garlic Hummus & Orange Slices", calories: 280, p: 10, c: 44, f: 8, fib: 7, notes: "Hydrating electrolytes with complex carbohydrates." },
          { type: "Dinner", title: "Wild Alaskan Salmon with Roasted Sweet Potatoes & Green Beans", calories: 720, p: 48, c: 72, f: 26, fib: 11, notes: "Potassium, anti-inflammatory omega-3s, and slow-burning starches." }
        ]
      },
      {
        dayNumber: 2,
        meals: [
          { type: "Breakfast", title: "Fluffy Whole Grain Protein Pancakes with Pure Maple Syrup & Strawberries", calories: 540, p: 36, c: 78, f: 12, fib: 8, notes: "Optimal pre-training carbohydrate matrix." },
          { type: "Lunch", title: "Bison & Brown Rice Burrito Bowl with Black Beans, Salsa & Guacamole", calories: 710, p: 48, c: 82, f: 20, fib: 14, notes: "Natural creatine, heme iron, and fiber for sustained athletic output." },
          { type: "Snack", title: "Greek Yogurt Smoothie with Spinach, Mango & Chia Seeds", calories: 290, p: 20, c: 38, f: 6, fib: 6, notes: "Electrolytes, calcium, and bioavailable lutein." },
          { type: "Dinner", title: "Herb-Crusted Flank Steak with Roasted Fingerling Potatoes & Grilled Zucchini", calories: 730, p: 52, c: 66, f: 24, fib: 8, notes: "Zinc and branched-chain aminos for overnight myofibrillar repair." }
        ]
      },
      {
        dayNumber: 3,
        meals: [
          { type: "Breakfast", title: "Avocado Sourdough Toast with 3 Soft-Boiled Eggs & Microgreens", calories: 520, p: 28, c: 42, f: 28, fib: 7, notes: "Healthy monounsaturated fats and essential choline." },
          { type: "Lunch", title: "Mediterranean Tuna Pasta Salad with Cherry Tomatoes, Capers & Olive Oil", calories: 690, p: 50, c: 78, f: 18, fib: 9, notes: "High protein efficiency ratio with easy post-workout digestibility." },
          { type: "Snack", title: "Medjool Dates Stuffed with Almond Butter & Sea Salt", calories: 270, p: 6, c: 46, f: 10, fib: 5, notes: "Rapid natural glucose and fructose for immediate energy recharge." },
          { type: "Dinner", title: "Chicken Tikka Masala with Brown Basmati Rice & Cucumber Raita", calories: 740, p: 50, c: 80, f: 22, fib: 9, notes: "Turmeric curcumin for exercise-induced inflammatory recovery." }
        ]
      }
    ]
  }
};
