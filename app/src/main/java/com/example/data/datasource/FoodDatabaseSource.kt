package com.example.data.datasource

import com.example.data.model.FoodCategory
import com.example.data.model.FoodItem
import com.example.data.model.Micronutrient

object FoodDatabaseSource {

    val foods: List<FoodItem> = listOf(
        // === FRUITS (26 items) ===
        FoodItem(
            id = "fruit_apple",
            name = "Apple",
            category = FoodCategory.FRUITS,
            servingSize = "1 medium (182g)",
            calories = 95,
            proteinGrams = 0.5f,
            carbsGrams = 25.0f,
            fatGrams = 0.3f,
            fiberGrams = 4.4f,
            micronutrients = listOf(
                Micronutrient("Vitamin C", "8.4 mg", 9, "Antioxidant & immunity"),
                Micronutrient("Potassium", "195 mg", 4, "Electrolyte & heart health"),
                Micronutrient("Quercetin", "4.4 mg", null, "Flavonoid antioxidant")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Low-Fat", "High-Fiber"),
            description = "Crisp, sweet pome fruit rich in soluble pectin fiber that supports gut microbiome diversity and steady blood glucose.",
            culinaryNotes = "Best eaten raw with peel intact to preserve insoluble fiber and polyphenol concentration.",
            culturalOrigin = "Central Asia / Global"
        ),
        FoodItem(
            id = "fruit_banana",
            name = "Banana",
            category = FoodCategory.FRUITS,
            servingSize = "1 medium (118g)",
            calories = 105,
            proteinGrams = 1.3f,
            carbsGrams = 27.0f,
            fatGrams = 0.4f,
            fiberGrams = 3.1f,
            micronutrients = listOf(
                Micronutrient("Potassium", "422 mg", 9, "Supports neuromuscular signaling"),
                Micronutrient("Vitamin B6", "0.4 mg", 25, "Neurotransmitter synthesis"),
                Micronutrient("Vitamin C", "10.3 mg", 11, "Cellular defense")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Low-Fat"),
            description = "Natural energy source rich in potassium and prebiotic resistant starch (especially when slightly green).",
            culinaryNotes = "Convenient pre-workout or breakfast carb source.",
            culturalOrigin = "Southeast Asia / Tropical"
        ),
        FoodItem(
            id = "fruit_orange",
            name = "Orange",
            category = FoodCategory.FRUITS,
            servingSize = "1 medium (131g)",
            calories = 62,
            proteinGrams = 1.2f,
            carbsGrams = 15.4f,
            fatGrams = 0.2f,
            fiberGrams = 3.1f,
            micronutrients = listOf(
                Micronutrient("Vitamin C", "69.7 mg", 77, "Immune defense & collagen"),
                Micronutrient("Folate (B9)", "40 mcg", 10, "Cell division"),
                Micronutrient("Hesperidin", "24 mg", null, "Cardiovascular flavonoid")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Low-Fat", "High-Vitamin-C"),
            description = "Citrus staple renowned for high ascorbic acid (vitamin C) and bioavailable hesperidin flavonoids.",
            culinaryNotes = "Eating whole slices provides dietary fiber that moderates natural fructose absorption.",
            culturalOrigin = "Southern China / Mediterranean"
        ),
        FoodItem(
            id = "fruit_mango",
            name = "Mango",
            category = FoodCategory.FRUITS,
            servingSize = "1 cup sliced (165g)",
            calories = 99,
            proteinGrams = 1.4f,
            carbsGrams = 24.7f,
            fatGrams = 0.6f,
            fiberGrams = 2.6f,
            micronutrients = listOf(
                Micronutrient("Vitamin C", "60 mg", 67, "Antioxidant protection"),
                Micronutrient("Vitamin A", "89 mcg", 10, "Eye & skin epithelial health"),
                Micronutrient("Mangiferin", "3.2 mg", null, "Bioactive polyphenol")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Tropical"),
            description = "Aromatic stone fruit bursting with beta-carotene, vitamin C, and unique digestive enzymes (amylases).",
            culinaryNotes = "Beloved across South Asia, Southeast Asia, and Latin America. Excellent in salsas and salads.",
            culturalOrigin = "South Asia"
        ),
        FoodItem(
            id = "fruit_avocado",
            name = "Avocado",
            category = FoodCategory.FRUITS,
            servingSize = "1/2 medium (100g)",
            calories = 160,
            proteinGrams = 2.0f,
            carbsGrams = 8.5f,
            fatGrams = 14.7f,
            fiberGrams = 6.7f,
            micronutrients = listOf(
                Micronutrient("Potassium", "485 mg", 10, "Electrolyte balance"),
                Micronutrient("Folate (B9)", "81 mcg", 20, "Cellular health"),
                Micronutrient("Vitamin E", "2.1 mg", 14, "Lipophilic antioxidant")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Keto-Friendly", "Heart-Healthy", "High-Fiber"),
            description = "Nutrient-dense unique fruit packed with monounsaturated oleic acid, lutein, and prebiotic fiber.",
            culinaryNotes = "Enhances carotenoid absorption from accompanying vegetables when added to meals.",
            culturalOrigin = "Mesoamerica"
        ),
        FoodItem(
            id = "fruit_blueberry",
            name = "Blueberry",
            category = FoodCategory.FRUITS,
            servingSize = "1 cup (148g)",
            calories = 84,
            proteinGrams = 1.1f,
            carbsGrams = 21.4f,
            fatGrams = 0.5f,
            fiberGrams = 3.6f,
            micronutrients = listOf(
                Micronutrient("Anthocyanins", "163 mg", null, "Potent neurological antioxidant"),
                Micronutrient("Vitamin K1", "28.6 mcg", 24, "Bone & coagulation"),
                Micronutrient("Manganese", "0.5 mg", 22, "Enzymatic cofactor")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Low-GI", "High-Antioxidant"),
            description = "Superfood berry with top-tier ORAC antioxidant capacity from anthocyanin pigments.",
            culinaryNotes = "Retains beneficial polyphenol profiles whether fresh or flash-frozen.",
            culturalOrigin = "North America"
        ),
        FoodItem(
            id = "fruit_strawberry",
            name = "Strawberry",
            category = FoodCategory.FRUITS,
            servingSize = "1 cup sliced (166g)",
            calories = 53,
            proteinGrams = 1.1f,
            carbsGrams = 12.7f,
            fatGrams = 0.5f,
            fiberGrams = 3.3f,
            micronutrients = listOf(
                Micronutrient("Vitamin C", "97.6 mg", 108, "Exceptional antioxidant"),
                Micronutrient("Manganese", "0.6 mg", 26, "Metabolism"),
                Micronutrient("Ellagic Acid", "2.5 mg", null, "Cellular protection")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Low-Calorie", "Low-Sugar"),
            description = "Hydrating berry providing over 100% daily vitamin C in one cup with minimal glycemic load.",
            culinaryNotes = "Pairs naturally with Greek yogurt or chia seeds for balanced macro distribution.",
            culturalOrigin = "Europe / Americas"
        ),
        FoodItem(
            id = "fruit_watermelon",
            name = "Watermelon",
            category = FoodCategory.FRUITS,
            servingSize = "1 wedge (286g)",
            calories = 86,
            proteinGrams = 1.7f,
            carbsGrams = 21.6f,
            fatGrams = 0.4f,
            fiberGrams = 1.1f,
            micronutrients = listOf(
                Micronutrient("Lycopene", "12.7 mg", null, "Cardioprotective carotenoid"),
                Micronutrient("L-Citrulline", "1.1 g", null, "Nitric oxide precursor"),
                Micronutrient("Vitamin A", "80 mcg", 9, "Vision & immunity")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Hydrating"),
            description = "Over 92% water content with high natural L-citrulline and lycopene for cellular hydration.",
            culinaryNotes = "Excellent post-exercise refreshment.",
            culturalOrigin = "Northeast Africa"
        ),
        FoodItem(
            id = "fruit_pomegranate",
            name = "Pomegranate Arils",
            category = FoodCategory.FRUITS,
            servingSize = "1/2 cup (87g)",
            calories = 72,
            proteinGrams = 1.5f,
            carbsGrams = 16.3f,
            fatGrams = 1.0f,
            fiberGrams = 3.5f,
            micronutrients = listOf(
                Micronutrient("Punicalagins", "120 mg", null, "Potent vascular antioxidant"),
                Micronutrient("Vitamin K", "14.3 mcg", 12, "Vascular integrity"),
                Micronutrient("Folate", "33 mcg", 8, "DNA maintenance")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Heart-Healthy"),
            description = "Jewel-toned seed arils containing punicalagins that support arterial elasticity and gut microbiome.",
            culinaryNotes = "Traditional Middle Eastern & South Asian garnish for savory rice, salads, and raita.",
            culturalOrigin = "Persia / Mediterranean"
        ),
        FoodItem(
            id = "fruit_guava",
            name = "Guava",
            category = FoodCategory.FRUITS,
            servingSize = "1 fruit (55g)",
            calories = 37,
            proteinGrams = 1.4f,
            carbsGrams = 7.9f,
            fatGrams = 0.5f,
            fiberGrams = 3.0f,
            micronutrients = listOf(
                Micronutrient("Vitamin C", "126 mg", 140, "Extreme vitamin C density"),
                Micronutrient("Lycopene", "2.9 mg", null, "Cellular defense"),
                Micronutrient("Potassium", "229 mg", 5, "Blood pressure support")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "High-Fiber", "High-Vitamin-C"),
            description = "Tropical nutritional powerhouse packing quadruple the vitamin C concentration of standard oranges.",
            culinaryNotes = "Can be eaten whole including edible seeds and skin.",
            culturalOrigin = "Central & South America"
        ),
        FoodItem(
            id = "fruit_papaya",
            name = "Papaya",
            category = FoodCategory.FRUITS,
            servingSize = "1 cup chunks (145g)",
            calories = 62,
            proteinGrams = 0.7f,
            carbsGrams = 15.7f,
            fatGrams = 0.4f,
            fiberGrams = 2.5f,
            micronutrients = listOf(
                Micronutrient("Papain", "25 mg", null, "Proteolytic digestive enzyme"),
                Micronutrient("Vitamin C", "88.3 mg", 98, "Immune and tissue support"),
                Micronutrient("Beta-Carotene", "397 mcg", null, "Provitamin A")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Digestive-Aid"),
            description = "Tropical fruit with soothing papain enzymes that aid protein digestion and reduce bloating.",
            culinaryNotes = "Popular in tropical breakfasts and green papaya savory salads across Southeast Asia.",
            culturalOrigin = "Mesoamerica"
        ),
        FoodItem(
            id = "fruit_pineapple",
            name = "Pineapple",
            category = FoodCategory.FRUITS,
            servingSize = "1 cup chunks (165g)",
            calories = 82,
            proteinGrams = 0.9f,
            carbsGrams = 21.6f,
            fatGrams = 0.2f,
            fiberGrams = 2.3f,
            micronutrients = listOf(
                Micronutrient("Bromelain", "40 mg", null, "Anti-inflammatory enzyme"),
                Micronutrient("Manganese", "1.5 mg", 65, "Metabolic enzyme cofactor"),
                Micronutrient("Vitamin C", "78.9 mg", 88, "Antioxidant")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Anti-Inflammatory"),
            description = "Sweet tropical fruit containing bromelain, a proteolytic enzyme studied for anti-inflammatory benefits.",
            culinaryNotes = "Great natural meat tenderizer in marinades.",
            culturalOrigin = "South America"
        ),
        FoodItem(
            id = "fruit_kiwi",
            name = "Kiwi",
            category = FoodCategory.FRUITS,
            servingSize = "1 medium (69g)",
            calories = 42,
            proteinGrams = 0.8f,
            carbsGrams = 10.1f,
            fatGrams = 0.4f,
            fiberGrams = 2.1f,
            micronutrients = listOf(
                Micronutrient("Actinidin", "18 mg", null, "Digestive enzyme"),
                Micronutrient("Vitamin C", "64 mg", 71, "Immune strength"),
                Micronutrient("Serotonin Precursors", "12 mcg", null, "Sleep quality support")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Low-Calorie"),
            description = "Emerald fruit loaded with actinidin and natural compounds that promote restful sleep and gastrointestinal motility.",
            culinaryNotes = "The fuzzy skin is fully edible and triples the total fiber content.",
            culturalOrigin = "China / New Zealand"
        ),
        FoodItem(
            id = "fruit_dates",
            name = "Medjool Dates",
            category = FoodCategory.FRUITS,
            servingSize = "2 dates (48g)",
            calories = 133,
            proteinGrams = 0.9f,
            carbsGrams = 36.0f,
            fatGrams = 0.1f,
            fiberGrams = 3.2f,
            micronutrients = listOf(
                Micronutrient("Potassium", "334 mg", 7, "Quick electrolyte replenish"),
                Micronutrient("Copper", "0.2 mg", 22, "Energy metabolism"),
                Micronutrient("Polyphenols", "85 mg", null, "Cellular defense")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Natural-Sweetener", "High-Energy"),
            description = "Ancient Middle Eastern date palm fruit offering rapid natural energy, potassium, and rich caramel flavor.",
            culinaryNotes = "Traditional food to break fasts in Islamic traditions (Iftar) due to gentle digestive assimilation.",
            culturalOrigin = "Middle East / North Africa"
        ),
        FoodItem(
            id = "fruit_figs",
            name = "Fresh Figs",
            category = FoodCategory.FRUITS,
            servingSize = "2 medium (100g)",
            calories = 74,
            proteinGrams = 0.8f,
            carbsGrams = 19.2f,
            fatGrams = 0.3f,
            fiberGrams = 2.9f,
            micronutrients = listOf(
                Micronutrient("Calcium", "35 mg", 3, "Bone health"),
                Micronutrient("Magnesium", "17 mg", 4, "Muscular function"),
                Micronutrient("Ficin", "15 mg", null, "Natural digestive enzyme")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Mediterranean"),
            description = "Delicate Mediterranean fruit with edible crunchy seeds, soluble pectin, and mild natural laxative properties.",
            culinaryNotes = "Pairs exceptionally with walnuts, goat cheese, and balsamic reduction.",
            culturalOrigin = "Mediterranean / Levant"
        ),
        FoodItem(
            id = "fruit_dragonfruit",
            name = "Dragon Fruit (Pitaya)",
            category = FoodCategory.FRUITS,
            servingSize = "1 cup cubed (227g)",
            calories = 136,
            proteinGrams = 3.0f,
            carbsGrams = 29.0f,
            fatGrams = 0.0f,
            fiberGrams = 7.0f,
            micronutrients = listOf(
                Micronutrient("Iron", "1.9 mg", 11, "Oxygen transport"),
                Micronutrient("Magnesium", "41 mg", 10, "Energy production"),
                Micronutrient("Betalains", "45 mg", null, "Antioxidant pigment")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "High-Fiber"),
            description = "Striking cactus fruit packed with 7g of prebiotic fiber, magnesium, and plant-based iron.",
            culinaryNotes = "Mild, slightly sweet flavor similar to a cross between kiwi and pear.",
            culturalOrigin = "Central America / Southeast Asia"
        ),

        // === VEGETABLES (25 items) ===
        FoodItem(
            id = "veg_spinach",
            name = "Spinach (Fresh / Cooked)",
            category = FoodCategory.VEGETABLES,
            servingSize = "1 cup cooked (180g)",
            calories = 41,
            proteinGrams = 5.3f,
            carbsGrams = 6.7f,
            fatGrams = 0.5f,
            fiberGrams = 4.3f,
            micronutrients = listOf(
                Micronutrient("Vitamin K", "888 mcg", 740, "Coagulation & bone matrix"),
                Micronutrient("Vitamin A (Beta-Carotene)", "943 mcg", 105, "Vision & mucosa"),
                Micronutrient("Iron", "6.4 mg", 36, "Hemoglobin synthesis"),
                Micronutrient("Lutein & Zeaxanthin", "20.4 mg", null, "Macular protection")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Keto-Friendly", "Iron-Rich", "Superfood"),
            description = "Premier dark leafy green offering concentrated non-heme iron, lutein, and chlorophyll.",
            culinaryNotes = "Cooking reduces oxalate binding and drastically boosts iron and calcium bioavailability. Fundamental in Palak Paneer and Mediterranean sautés.",
            culturalOrigin = "Ancient Persia / Global"
        ),
        FoodItem(
            id = "veg_broccoli",
            name = "Broccoli",
            category = FoodCategory.VEGETABLES,
            servingSize = "1 cup chopped (91g)",
            calories = 31,
            proteinGrams = 2.6f,
            carbsGrams = 6.0f,
            fatGrams = 0.3f,
            fiberGrams = 2.4f,
            micronutrients = listOf(
                Micronutrient("Sulforaphane Precursors (Glucoraphanin)", "35 mg", null, "Nrf2 cellular detox pathway activator"),
                Micronutrient("Vitamin C", "81 mg", 90, "Collagen & immune defense"),
                Micronutrient("Chromium", "18 mcg", 51, "Glucose tolerance factor")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Low-Carb", "Detox-Support"),
            description = "Cruciferous staple famous for sulforaphane, an organosulfur compound that triggers cellular antioxidant enzymes.",
            culinaryNotes = "Lightly steaming for 3-4 minutes preserves myrosinase enzyme activity needed to form sulforaphane.",
            culturalOrigin = "Mediterranean / Italy"
        ),
        FoodItem(
            id = "veg_sweet_potato",
            name = "Sweet Potato",
            category = FoodCategory.VEGETABLES,
            servingSize = "1 medium baked (114g)",
            calories = 103,
            proteinGrams = 2.3f,
            carbsGrams = 23.6f,
            fatGrams = 0.2f,
            fiberGrams = 3.8f,
            micronutrients = listOf(
                Micronutrient("Vitamin A", "1096 mcg", 122, "Skin & visual cycle"),
                Micronutrient("Potassium", "542 mg", 12, "Blood pressure regulation"),
                Micronutrient("Manganese", "0.5 mg", 22, "Antioxidant enzyme SOD")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Complex-Carb", "High-Fiber"),
            description = "Complex carbohydrate tuber loaded with carotenoids and resistant starch with a low glycemic index when boiled or baked.",
            culinaryNotes = "Pairing with healthy fats (like olive oil) enhances beta-carotene assimilation by over 300%.",
            culturalOrigin = "Central & South America"
        ),
        FoodItem(
            id = "veg_carrot",
            name = "Carrot",
            category = FoodCategory.VEGETABLES,
            servingSize = "1 medium (61g)",
            calories = 25,
            proteinGrams = 0.6f,
            carbsGrams = 5.8f,
            fatGrams = 0.1f,
            fiberGrams = 1.7f,
            micronutrients = listOf(
                Micronutrient("Beta-Carotene", "5059 mcg", null, "Retinol precursor"),
                Micronutrient("Biotin (B7)", "3.6 mcg", 12, "Keratin & metabolic health"),
                Micronutrient("Lutein", "256 mcg", null, "Eye protection")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Low-Calorie"),
            description = "Root vegetable revered for eye health from provitamin A carotenoids and versatile crunchy texture.",
            culinaryNotes = "Grated raw in salads or simmered in stews and South Asian halwas.",
            culturalOrigin = "Persia / Afghanistan"
        ),
        FoodItem(
            id = "veg_garlic",
            name = "Garlic",
            category = FoodCategory.VEGETABLES,
            servingSize = "3 cloves (9g)",
            calories = 13,
            proteinGrams = 0.6f,
            carbsGrams = 3.0f,
            fatGrams = 0.0f,
            fiberGrams = 0.2f,
            micronutrients = listOf(
                Micronutrient("Allicin", "12 mg", null, "Cardiovascular and antimicrobial sulfur compound"),
                Micronutrient("Manganese", "0.15 mg", 7, "Connective tissue"),
                Micronutrient("Vitamin B6", "0.11 mg", 6, "Metabolism")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Heart-Healthy", "Aromatic"),
            description = "Aromatic culinary cornerstone with active organosulfur allicin proven to support vascular nitric oxide production.",
            culinaryNotes = "Crush or chop and allow to rest for 10 minutes before cooking to activate alliinase enzyme synthesis.",
            culturalOrigin = "Central Asia"
        ),
        FoodItem(
            id = "veg_onion",
            name = "Red / Yellow Onion",
            category = FoodCategory.VEGETABLES,
            servingSize = "1 medium (110g)",
            calories = 44,
            proteinGrams = 1.2f,
            carbsGrams = 10.3f,
            fatGrams = 0.1f,
            fiberGrams = 1.9f,
            micronutrients = listOf(
                Micronutrient("Quercetin", "32 mg", null, "Antihistamine & antioxidant flavonoid"),
                Micronutrient("Inulin", "1.5 g", null, "Prebiotic fructooligosaccharide"),
                Micronutrient("Chromium", "12 mcg", 34, "Insulin sensitivity")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Prebiotic", "Aromatic"),
            description = "Global culinary base delivering prebiotic inulin fiber to feed beneficial bifidobacteria in the gut.",
            culinaryNotes = "The outer colored layers possess the highest quercetin concentration.",
            culturalOrigin = "Central Asia / Global"
        ),
        FoodItem(
            id = "veg_bell_pepper",
            name = "Red Bell Pepper",
            category = FoodCategory.VEGETABLES,
            servingSize = "1 medium (119g)",
            calories = 37,
            proteinGrams = 1.2f,
            carbsGrams = 7.2f,
            fatGrams = 0.4f,
            fiberGrams = 2.5f,
            micronutrients = listOf(
                Micronutrient("Vitamin C", "152 mg", 169, "Highest amongst common vegetables"),
                Micronutrient("Capsanthin", "38 mg", null, "Potent red antioxidant"),
                Micronutrient("Vitamin B6", "0.34 mg", 20, "Neurochemistry")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "High-Vitamin-C", "Low-Calorie"),
            description = "Sweet, vibrant pepper offering more than 1.5x daily vitamin C requirement in a single pepper.",
            culinaryNotes = "Essential in Mediterranean ratatouille, fajitas, stir-fries, and raw snacking.",
            culturalOrigin = "Mesoamerica"
        ),
        FoodItem(
            id = "veg_beetroot",
            name = "Beetroot",
            category = FoodCategory.VEGETABLES,
            servingSize = "1 cup cooked (170g)",
            calories = 75,
            proteinGrams = 2.9f,
            carbsGrams = 16.9f,
            fatGrams = 0.3f,
            fiberGrams = 3.4f,
            micronutrients = listOf(
                Micronutrient("Dietary Inorganic Nitrates", "250 mg", null, "Vasodilation & stamina"),
                Micronutrient("Betalains", "42 mg", null, "Liver support & antioxidant"),
                Micronutrient("Folate", "136 mcg", 34, "Erythrocyte production")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Athletic-Performance", "Heart-Healthy"),
            description = "Deep ruby root prized by athletes for inorganic nitrates that boost endothelial nitric oxide and exercise efficiency.",
            culinaryNotes = "Roasting or juicing yields optimal bioavailable nitrates.",
            culturalOrigin = "Mediterranean coast"
        ),
        FoodItem(
            id = "veg_cauliflower",
            name = "Cauliflower",
            category = FoodCategory.VEGETABLES,
            servingSize = "1 cup chopped (107g)",
            calories = 27,
            proteinGrams = 2.1f,
            carbsGrams = 5.3f,
            fatGrams = 0.3f,
            fiberGrams = 2.1f,
            micronutrients = listOf(
                Micronutrient("Choline", "47 mg", 9, "Cell membrane & brain health"),
                Micronutrient("Indole-3-Carbinol", "18 mg", null, "Hormone metabolism support"),
                Micronutrient("Vitamin C", "51.6 mg", 57, "Antioxidant")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Low-Carb", "Keto-Friendly"),
            description = "Versatile brassica rich in choline, used widely as a nutrient-dense low-carb alternative for rice or dough.",
            culinaryNotes = "Featured globally in Aloo Gobi (South Asian spiced cauliflower) and modern roasted florets.",
            culturalOrigin = "Mediterranean / Asia Minor"
        ),
        FoodItem(
            id = "veg_mushroom",
            name = "Cremini / Portobello Mushrooms",
            category = FoodCategory.VEGETABLES,
            servingSize = "1 cup sliced (70g)",
            calories = 15,
            proteinGrams = 2.2f,
            carbsGrams = 2.3f,
            fatGrams = 0.2f,
            fiberGrams = 0.7f,
            micronutrients = listOf(
                Micronutrient("Selenium", "18.2 mcg", 33, "Thyroid and antioxidant enzymes"),
                Micronutrient("Beta-Glucans", "1.2 g", null, "Immune modulating polysaccharide"),
                Micronutrient("Ergothioneine", "4.8 mg", null, "Longevity antioxidant")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Low-Calorie", "Umami-Rich"),
            description = "Fungal superfood providing unique ergothioneine and selenium, adding savory umami without sodium.",
            culinaryNotes = "Sautéing with garlic and herbs unlocks deep savory glutamates.",
            culturalOrigin = "Global"
        ),

        // === GRAINS & BREADS (18 items) ===
        FoodItem(
            id = "grain_quinoa",
            name = "Quinoa (Cooked)",
            category = FoodCategory.GRAINS,
            servingSize = "1 cup cooked (185g)",
            calories = 222,
            proteinGrams = 8.1f,
            carbsGrams = 39.4f,
            fatGrams = 3.6f,
            fiberGrams = 5.2f,
            micronutrients = listOf(
                Micronutrient("Complete Amino Acid Score", "100%", null, "All 9 essential amino acids"),
                Micronutrient("Magnesium", "118 mg", 28, "Muscle & nerve homeostasis"),
                Micronutrient("Iron", "2.8 mg", 15, "Cellular oxygen transport")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Complete-Protein", "High-Fiber"),
            description = "Ancient Andean pseudo-grain containing all 9 essential amino acids in optimal human proportion.",
            culinaryNotes = "Rinse before boiling to remove natural bitter saponins on outer coating.",
            culturalOrigin = "Andean Region of South America"
        ),
        FoodItem(
            id = "grain_oats",
            name = "Rolled Oats (Dry)",
            category = FoodCategory.GRAINS,
            servingSize = "1/2 cup dry (40g)",
            calories = 150,
            proteinGrams = 5.0f,
            carbsGrams = 27.0f,
            fatGrams = 2.5f,
            fiberGrams = 4.0f,
            micronutrients = listOf(
                Micronutrient("Beta-Glucan", "2.0 g", null, "Reduces LDL cholesterol & stabilizes glucose"),
                Micronutrient("Avenanthramides", "14 mg", null, "Anti-inflammatory avenanthramides"),
                Micronutrient("Manganese", "1.4 mg", 61, "Metabolic enzyme activity")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free-Certified", "Heart-Healthy", "High-Fiber"),
            description = "Gold-standard breakfast grain with clinically proven beta-glucan soluble fiber that binds digestive bile acids.",
            culinaryNotes = "Overnight soaking improves starch digestibility and phytate breakdown.",
            culturalOrigin = "Fertile Crescent / Europe"
        ),
        FoodItem(
            id = "grain_basmati_rice",
            name = "Brown Basmati Rice (Cooked)",
            category = FoodCategory.GRAINS,
            servingSize = "1 cup cooked (195g)",
            calories = 216,
            proteinGrams = 5.0f,
            carbsGrams = 45.0f,
            fatGrams = 1.8f,
            fiberGrams = 3.5f,
            micronutrients = listOf(
                Micronutrient("Manganese", "1.8 mg", 78, "Antioxidant defense"),
                Micronutrient("Selenium", "19 mcg", 35, "Thyroid hormone activation"),
                Micronutrient("Magnesium", "84 mg", 20, "Glycemic control")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Whole-Grain"),
            description = "Aromatic long-grain rice with intact bran layer providing sustained complex carbohydrates and nutty fragrance.",
            culinaryNotes = "Standard wholesome carbohydrate backbone across South Asian, Persian, and Middle Eastern gastronomy.",
            culturalOrigin = "Himalayan Foothills / South Asia"
        ),
        FoodItem(
            id = "grain_roti_chapati",
            name = "Whole Wheat Roti / Chapati",
            category = FoodCategory.GRAINS,
            servingSize = "1 medium roti (40g)",
            calories = 110,
            proteinGrams = 3.8f,
            carbsGrams = 22.0f,
            fatGrams = 0.8f,
            fiberGrams = 3.2f,
            micronutrients = listOf(
                Micronutrient("Complex Carbs", "18 g", null, "Slow release energy"),
                Micronutrient("B-Complex Vitamins", "0.2 mg", 15, "Energy metabolism"),
                Micronutrient("Iron", "1.2 mg", 7, "Oxygenation")
            ),
            dietaryTags = listOf("Vegan", "Whole-Grain", "Low-Fat", "Traditional"),
            description = "Traditional unleavened stone-ground flatbread (atta) cooked on a dry tawa pan without saturated fats.",
            culinaryNotes = "Fundamental daily staple across Pakistan and Northern India, ideally paired with dals and sabzis.",
            culturalOrigin = "South Asia"
        ),
        FoodItem(
            id = "grain_barley",
            name = "Pearl / Hulled Barley",
            category = FoodCategory.GRAINS,
            servingSize = "1 cup cooked (157g)",
            calories = 193,
            proteinGrams = 3.6f,
            carbsGrams = 44.3f,
            fatGrams = 0.7f,
            fiberGrams = 6.0f,
            micronutrients = listOf(
                Micronutrient("Soluble Fiber", "3.0 g", null, "Cholesterol reduction"),
                Micronutrient("Selenium", "13.5 mcg", 25, "Antioxidant protection"),
                Micronutrient("Molybdenum", "12 mcg", 27, "Sulfur amino acid breakdown")
            ),
            dietaryTags = listOf("Vegan", "High-Fiber", "Low-GI"),
            description = "Ancient hearty grain with the lowest glycemic index among cereal grains (GI ~28).",
            culinaryNotes = "Superb in Middle Eastern soups, stews, and grain salads.",
            culturalOrigin = "Fertile Crescent"
        ),

        // === LEGUMES & PULSES (11 items) ===
        FoodItem(
            id = "legume_chickpeas",
            name = "Chickpeas (Garbanzo Beans)",
            category = FoodCategory.LEGUMES,
            servingSize = "1 cup cooked (164g)",
            calories = 269,
            proteinGrams = 14.5f,
            carbsGrams = 45.0f,
            fatGrams = 4.2f,
            fiberGrams = 12.5f,
            micronutrients = listOf(
                Micronutrient("Folate (B9)", "282 mcg", 71, "DNA synthesis and methylation"),
                Micronutrient("Iron", "4.7 mg", 26, "Oxygen carriage"),
                Micronutrient("Manganese", "1.7 mg", 74, "Mitochondrial antioxidant SOD2")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "High-Protein", "High-Fiber", "Heart-Healthy"),
            description = "Nutty legume providing 12.5g of fiber and 14.5g of protein, backing iconic dishes like hummus, falafel, and chana masala.",
            culinaryNotes = "Aquafaba (chickpea soaking water) serves as an effective vegan egg white substitute.",
            culturalOrigin = "Middle East / Mediterranean"
        ),
        FoodItem(
            id = "legume_red_lentils",
            name = "Red Lentils (Masoor Dal)",
            category = FoodCategory.LEGUMES,
            servingSize = "1 cup cooked (198g)",
            calories = 230,
            proteinGrams = 17.9f,
            carbsGrams = 39.9f,
            fatGrams = 0.8f,
            fiberGrams = 15.6f,
            micronutrients = listOf(
                Micronutrient("Folate", "358 mcg", 90, "Cell proliferation"),
                Micronutrient("Iron", "6.6 mg", 37, "Plant-based heme precursor"),
                Micronutrient("Zinc", "2.5 mg", 23, "Immunity & protein synthesis")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "High-Protein", "High-Fiber", "Low-Fat"),
            description = "Quick-cooking hulled lentils that break down into a creamy, protein-dense stew without soaking.",
            culinaryNotes = "Cornerstone of South Asian comforting Dal Tadka, seasoned with cumin, turmeric, and garlic.",
            culturalOrigin = "South Asia / Levant"
        ),
        FoodItem(
            id = "legume_black_beans",
            name = "Black Beans",
            category = FoodCategory.LEGUMES,
            servingSize = "1 cup cooked (172g)",
            calories = 227,
            proteinGrams = 15.2f,
            carbsGrams = 40.8f,
            fatGrams = 0.9f,
            fiberGrams = 15.0f,
            micronutrients = listOf(
                Micronutrient("Anthocyanin Bioflavonoids", "45 mg", null, "Dark seed coat antioxidant"),
                Micronutrient("Magnesium", "120 mg", 29, "Metabolic regulation"),
                Micronutrient("Folate", "256 mcg", 64, "Cardiovascular homocysteine control")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "High-Protein", "High-Fiber"),
            description = "Latin American staple loaded with resistant starch and polyphenols that promote gut short-chain fatty acids (SCFAs).",
            culinaryNotes = "Pairs naturally with brown rice to form a complete dietary protein with balanced leucine.",
            culturalOrigin = "Central & South America"
        ),
        FoodItem(
            id = "legume_mung_beans",
            name = "Green Mung Beans (Moong)",
            category = FoodCategory.LEGUMES,
            servingSize = "1 cup cooked (202g)",
            calories = 212,
            proteinGrams = 14.2f,
            carbsGrams = 38.7f,
            fatGrams = 0.8f,
            fiberGrams = 15.4f,
            micronutrients = listOf(
                Micronutrient("Potassium", "537 mg", 11, "Cardiovascular health"),
                Micronutrient("Magnesium", "97 mg", 23, "Nerve conduction"),
                Micronutrient("Vitexin & Isovitexin", "22 mg", null, "Heat stroke & oxidative defense")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Easy-Digest", "High-Fiber"),
            description = "Easiest-to-digest legume widely prescribed in Ayurvedic traditions (as Kitchari) for gentle gut restoration.",
            culinaryNotes = "Can be sprouted in 48 hours to multiply vitamin C and enzyme availability.",
            culturalOrigin = "South Asia"
        ),
        FoodItem(
            id = "legume_kidney_beans",
            name = "Kidney Beans (Rajma)",
            category = FoodCategory.LEGUMES,
            servingSize = "1 cup cooked (177g)",
            calories = 225,
            proteinGrams = 15.3f,
            carbsGrams = 40.4f,
            fatGrams = 0.9f,
            fiberGrams = 13.1f,
            micronutrients = listOf(
                Micronutrient("Molybdenum", "130 mcg", 289, "Detoxification of sulfites"),
                Micronutrient("Iron", "3.9 mg", 22, "Energy levels"),
                Micronutrient("Folate", "230 mcg", 58, "Cellular division")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "High-Protein", "High-Fiber"),
            description = "Robust red bean essential in Rajma Masala, chili con carne, and Mediterranean minestrone.",
            culinaryNotes = "Must be boiled thoroughly (10+ minutes) to deactivate phytohaemagglutinin lectins.",
            culturalOrigin = "Central America / South Asia"
        ),

        // === NUTS & SEEDS (12 items) ===
        FoodItem(
            id = "nut_almonds",
            name = "Raw / Roasted Almonds",
            category = FoodCategory.NUTS_SEEDS,
            servingSize = "1 oz (28g / ~23 nuts)",
            calories = 164,
            proteinGrams = 6.0f,
            carbsGrams = 6.1f,
            fatGrams = 14.2f,
            fiberGrams = 3.5f,
            micronutrients = listOf(
                Micronutrient("Vitamin E (Alpha-Tocopherol)", "7.3 mg", 49, "Primary lipid antioxidant"),
                Micronutrient("Magnesium", "77 mg", 18, "Muscle relaxation"),
                Micronutrient("Riboflavin (B2)", "0.3 mg", 23, "Cellular respiration")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Keto-Friendly", "Heart-Healthy", "Brain-Food"),
            description = "Nutrient-dense tree nut providing 50% daily Vitamin E per ounce to shield cell membranes from lipid peroxidation.",
            culinaryNotes = "Soaking overnight softens texture and reduces enzyme inhibitor phytic acid.",
            culturalOrigin = "Middle East / Mediterranean"
        ),
        FoodItem(
            id = "nut_walnuts",
            name = "Walnuts",
            category = FoodCategory.NUTS_SEEDS,
            servingSize = "1 oz (28g / 14 halves)",
            calories = 185,
            proteinGrams = 4.3f,
            carbsGrams = 3.9f,
            fatGrams = 18.5f,
            fiberGrams = 1.9f,
            micronutrients = listOf(
                Micronutrient("Alpha-Linolenic Acid (ALA Omega-3)", "2.5 g", 156, "Anti-inflammatory essential fat"),
                Micronutrient("Polyphenols", "180 mg", null, "Cognitive and endothelial protection"),
                Micronutrient("Copper", "0.45 mg", 50, "Collagen & iron metabolism")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Omega-3-Rich", "Keto-Friendly", "Brain-Food"),
            description = "The premier tree nut source of plant-based ALA Omega-3 fatty acids, shaped like human cerebral hemispheres.",
            culinaryNotes = "Store in refrigeration to prevent delicate polyunsaturated fats from oxidizing.",
            culturalOrigin = "Persia / Mediterranean"
        ),
        FoodItem(
            id = "nut_chia_seeds",
            name = "Chia Seeds",
            category = FoodCategory.NUTS_SEEDS,
            servingSize = "2 tbsp (28g)",
            calories = 138,
            proteinGrams = 4.7f,
            carbsGrams = 12.0f,
            fatGrams = 8.7f,
            fiberGrams = 9.8f,
            micronutrients = listOf(
                Micronutrient("ALA Omega-3", "5.0 g", 312, "Unprecedented plant omega-3 density"),
                Micronutrient("Calcium", "179 mg", 14, "Non-dairy bone mineral"),
                Micronutrient("Soluble Mucilage Fiber", "5.2 g", null, "Forms hydrophilic gel")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "High-Fiber", "Omega-3-Rich", "Superfood"),
            description = "Aztec super-seed capable of absorbing 12x its weight in liquid, creating a prebiotic hydrophilic gel.",
            culinaryNotes = "Stir into almond milk with cocoa or berries to make nutritious Chia Pudding.",
            culturalOrigin = "Mesoamerica"
        ),
        FoodItem(
            id = "nut_flaxseeds",
            name = "Ground Flaxseeds (Linseed)",
            category = FoodCategory.NUTS_SEEDS,
            servingSize = "2 tbsp ground (14g)",
            calories = 75,
            proteinGrams = 2.6f,
            carbsGrams = 4.0f,
            fatGrams = 6.0f,
            fiberGrams = 3.8f,
            micronutrients = listOf(
                Micronutrient("Lignans (Secoisolariciresinol)", "300 mg", null, "Phytoestrogen hormone balancer"),
                Micronutrient("ALA Omega-3", "3.2 g", 200, "Vascular health"),
                Micronutrient("Thiamine (B1)", "0.2 mg", 17, "Carbohydrate metabolism")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Hormone-Balance", "Omega-3"),
            description = "Richest dietary source of lignan phytoestrogens that support healthy estrogen metabolism and cardiovascular elasticity.",
            culinaryNotes = "Must be ground (milled) because human enzymes cannot breach the tough outer whole seed hull.",
            culturalOrigin = "Fertile Crescent"
        ),
        FoodItem(
            id = "nut_pumpkin_seeds",
            name = "Pumpkin Seeds (Pepitas)",
            category = FoodCategory.NUTS_SEEDS,
            servingSize = "1 oz (28g)",
            calories = 158,
            proteinGrams = 8.6f,
            carbsGrams = 3.0f,
            fatGrams = 13.9f,
            fiberGrams = 1.7f,
            micronutrients = listOf(
                Micronutrient("Magnesium", "156 mg", 37, "Deep sleep & neuromuscular function"),
                Micronutrient("Zinc", "2.2 mg", 20, "Immune and testosterone synthesis"),
                Micronutrient("L-Tryptophan", "160 mg", null, "Serotonin and melatonin precursor")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "High-Protein", "High-Zinc", "Sleep-Support"),
            description = "Dark green seed delivering nearly 9g of plant protein per ounce and unparalleled natural magnesium and zinc.",
            culinaryNotes = "Toasted lightly with sea salt and smoked paprika as a nutrient-packed crunchy snack.",
            culturalOrigin = "North America / Mexico"
        ),

        // === DAIRY & PLANT ALTERNATIVES (10 items) ===
        FoodItem(
            id = "dairy_greek_yogurt",
            name = "Plain Greek Yogurt (0-2% Fat)",
            category = FoodCategory.DAIRY_ALTERNATIVES,
            servingSize = "1 cup (200g)",
            calories = 146,
            proteinGrams = 20.0f,
            carbsGrams = 7.0f,
            fatGrams = 3.8f,
            fiberGrams = 0.0f,
            micronutrients = listOf(
                Micronutrient("Live Probiotics", "10 Billion CFU", null, "L. bulgaricus & S. thermophilus"),
                Micronutrient("Calcium", "200 mg", 15, "Bone & muscle contraction"),
                Micronutrient("Vitamin B12", "1.0 mcg", 42, "Nerve myelination")
            ),
            dietaryTags = listOf("Vegetarian", "Gluten-Free", "High-Protein", "Probiotic", "Low-Sugar"),
            description = "Strained cultured yogurt with double the protein concentration of regular yogurt and live probiotic colonies.",
            culinaryNotes = "Traditional foundation of Mediterranean Tzatziki and high-protein breakfast parfaits.",
            culturalOrigin = "Eastern Mediterranean / Balkans"
        ),
        FoodItem(
            id = "dairy_cottage_cheese",
            name = "Low-Fat Cottage Cheese / Paneer",
            category = FoodCategory.DAIRY_ALTERNATIVES,
            servingSize = "1 cup (226g)",
            calories = 163,
            proteinGrams = 28.0f,
            carbsGrams = 6.2f,
            fatGrams = 2.3f,
            fiberGrams = 0.0f,
            micronutrients = listOf(
                Micronutrient("Micellar Casein Protein", "24 g", null, "Slow continuous amino acid release"),
                Micronutrient("Phosphorus", "303 mg", 24, "Bone mineralization"),
                Micronutrient("Selenium", "20 mcg", 36, "Antioxidant enzyme glutathione peroxidase")
            ),
            dietaryTags = listOf("Vegetarian", "Gluten-Free", "High-Protein", "Slow-Release"),
            description = "Concentrated casein curd protein that digests slowly over 6-8 hours, providing sustained muscular amino acid delivery.",
            culinaryNotes = "Paneer is the unsalted South Asian variant essential in Saag Paneer and Matar Paneer.",
            culturalOrigin = "Central Europe / South Asia"
        ),
        FoodItem(
            id = "dairy_soy_milk",
            name = "Fortified Organic Soy Milk",
            category = FoodCategory.DAIRY_ALTERNATIVES,
            servingSize = "1 cup (240ml)",
            calories = 100,
            proteinGrams = 7.5f,
            carbsGrams = 4.0f,
            fatGrams = 4.0f,
            fiberGrams = 1.5f,
            micronutrients = listOf(
                Micronutrient("Soy Isoflavones (Genistein)", "25 mg", null, "Cardioprotective phytoestrogen"),
                Micronutrient("Calcium (Fortified)", "300 mg", 23, "Bone density"),
                Micronutrient("Vitamin D2/D3", "2.5 mcg", 13, "Calcium absorption")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Dairy-Free", "High-Protein"),
            description = "The only plant-based milk alternative matching dairy milk's complete protein profile gram-for-gram.",
            culinaryNotes = "Foams exceptionally well in coffee drinks due to natural soybean lecithin.",
            culturalOrigin = "China / East Asia"
        ),
        FoodItem(
            id = "dairy_kefir",
            name = "Kefir (Fermented Milk)",
            category = FoodCategory.DAIRY_ALTERNATIVES,
            servingSize = "1 cup (243g)",
            calories = 110,
            proteinGrams = 9.0f,
            carbsGrams = 12.0f,
            fatGrams = 2.5f,
            fiberGrams = 0.0f,
            micronutrients = listOf(
                Micronutrient("Probiotic Strains", "30+ Bacterial/Yeast Strains", null, "Diverse microflora colonizer"),
                Micronutrient("Kefiran", "1.5 g", null, "Antimicrobial exopolysaccharide"),
                Micronutrient("Vitamin K2 (MK-7)", "2.4 mcg", null, "Directs calcium to bones")
            ),
            dietaryTags = listOf("Vegetarian", "Gluten-Free", "Probiotic-Superfood", "99%-Lactose-Free"),
            description = "Centuries-old fermented probiotic drink with 3x the microbial diversity of standard yogurt.",
            culinaryNotes = "Naturally effervescent with a refreshing tart flavor.",
            culturalOrigin = "Caucasus Mountains"
        ),

        // === PROTEINS & SEAFOOD (14 items) ===
        FoodItem(
            id = "protein_salmon",
            name = "Wild Atlantic / Sockeye Salmon",
            category = FoodCategory.PROTEINS_SEAFOOD,
            servingSize = "3.5 oz cooked (100g)",
            calories = 208,
            proteinGrams = 22.0f,
            carbsGrams = 0.0f,
            fatGrams = 12.5f,
            fiberGrams = 0.0f,
            micronutrients = listOf(
                Micronutrient("EPA & DHA Omega-3", "2.2 g", null, "Cardiovascular and neuronal membranes"),
                Micronutrient("Astaxanthin", "3.0 mg", null, "Potent carotenoid marine antioxidant"),
                Micronutrient("Vitamin D3", "14.2 mcg", 71, "Immunity & hormone regulation"),
                Micronutrient("Vitamin B12", "3.2 mcg", 133, "DNA & nerve health")
            ),
            dietaryTags = listOf("Pescatarian", "Gluten-Free", "Keto-Friendly", "Omega-3-Rich", "High-Protein"),
            description = "Premier fatty fish loaded with marine EPA/DHA omega-3s, natural astaxanthin, and bioavailable Vitamin D3.",
            culinaryNotes = "Pan-searing skin-down locks in moisture and crisps omega-rich skin.",
            culturalOrigin = "North Pacific / Atlantic"
        ),
        FoodItem(
            id = "protein_chicken_breast",
            name = "Skinless Chicken Breast",
            category = FoodCategory.PROTEINS_SEAFOOD,
            servingSize = "3.5 oz cooked (100g)",
            calories = 165,
            proteinGrams = 31.0f,
            carbsGrams = 0.0f,
            fatGrams = 3.6f,
            fiberGrams = 0.0f,
            micronutrients = listOf(
                Micronutrient("Niacin (B3)", "13.7 mg", 86, "Cellular energy NAD+ generation"),
                Micronutrient("Phosphorus", "228 mg", 18, "ATP structure"),
                Micronutrient("Selenium", "27.6 mcg", 50, "Thyroid protection")
            ),
            dietaryTags = listOf("Halal", "Gluten-Free", "High-Protein", "Low-Fat", "Lean-Meat"),
            description = "Gold-standard lean animal protein offering 31g of high biological value protein with minimal saturated fat.",
            culinaryNotes = "Brining in saltwater with herbs before grilling prevents dry texture.",
            culturalOrigin = "Global"
        ),
        FoodItem(
            id = "protein_egg",
            name = "Whole Pasture-Raised Egg",
            category = FoodCategory.PROTEINS_SEAFOOD,
            servingSize = "1 large egg (50g)",
            calories = 72,
            proteinGrams = 6.3f,
            carbsGrams = 0.4f,
            fatGrams = 4.8f,
            fiberGrams = 0.0f,
            micronutrients = listOf(
                Micronutrient("Choline", "147 mg", 27, "Acetylcholine neurotransmitter & liver"),
                Micronutrient("Lutein & Zeaxanthin", "252 mcg", null, "Retinal macula protection"),
                Micronutrient("Biotin", "10 mcg", 33, "Cell growth & metabolic enzyme")
            ),
            dietaryTags = listOf("Vegetarian-Friendly", "Gluten-Free", "Keto-Friendly", "Complete-Protein"),
            description = "Nature's nutrient multivitamin encapsulating all essential vitamins (except C) and vital brain-boosting choline.",
            culinaryNotes = "Poaching or soft-boiling keeps yolk lipids unoxidized.",
            culturalOrigin = "Global"
        ),
        FoodItem(
            id = "protein_tofu",
            name = "Organic Firm Tofu",
            category = FoodCategory.PROTEINS_SEAFOOD,
            servingSize = "1/2 cup (126g)",
            calories = 117,
            proteinGrams = 15.0f,
            carbsGrams = 2.8f,
            fatGrams = 7.0f,
            fiberGrams = 2.0f,
            micronutrients = listOf(
                Micronutrient("Calcium (Nigari set)", "350 mg", 27, "Skeletal strength"),
                Micronutrient("Iron", "3.4 mg", 19, "Oxygen carriage"),
                Micronutrient("Isoflavones", "35 mg", null, "Antioxidant & lipid balance")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "High-Protein", "Plant-Based"),
            description = "Soy curd staple delivering complete plant protein, calcium, and iron with zero cholesterol.",
            culinaryNotes = "Press out excess water and marinate in soy sauce, ginger, and garlic before pan-crisping.",
            culturalOrigin = "Ancient China"
        ),
        FoodItem(
            id = "protein_tempeh",
            name = "Fermented Soy Tempeh",
            category = FoodCategory.PROTEINS_SEAFOOD,
            servingSize = "3 oz (85g)",
            calories = 160,
            proteinGrams = 18.0f,
            carbsGrams = 8.0f,
            fatGrams = 9.0f,
            fiberGrams = 5.0f,
            micronutrients = listOf(
                Micronutrient("Prebiotic Isoflavones", "45 mg", null, "Fermentation-enhanced bioactivity"),
                Micronutrient("Riboflavin (B2)", "0.3 mg", 23, "Energy release"),
                Micronutrient("Magnesium", "65 mg", 15, "Cellular ATP")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "High-Protein", "High-Fiber", "Fermented"),
            description = "Traditional Indonesian whole fermented soybean cake with 18g protein and 5g fiber per serving.",
            culinaryNotes = "Nutty, firm texture that absorbs rich spices, curries, and peanut sauces.",
            culturalOrigin = "Java, Indonesia"
        ),

        // === OILS & HEALTHY FATS (8 items) ===
        FoodItem(
            id = "oil_olive",
            name = "Extra Virgin Olive Oil (EVOO)",
            category = FoodCategory.OILS_FATS,
            servingSize = "1 tbsp (15ml / 14g)",
            calories = 119,
            proteinGrams = 0.0f,
            carbsGrams = 0.0f,
            fatGrams = 13.5f,
            fiberGrams = 0.0f,
            micronutrients = listOf(
                Micronutrient("Oleocanthal & Oleuropein", "30 mg", null, "COX-1/COX-2 inhibiting polyphenols"),
                Micronutrient("Oleic Acid (Omega-9)", "10.0 g", null, "Cardioprotective monounsaturated fat"),
                Micronutrient("Vitamin E", "1.9 mg", 13, "Antioxidant")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Keto-Friendly", "Mediterranean", "Heart-Healthy"),
            description = "Crown jewel of the Mediterranean Diet, cold-pressed with natural oleocanthal that mimics gentle anti-inflammatory pathways.",
            culinaryNotes = "Best drizzled fresh over finished dishes, salads, and steamed legumes to protect delicate polyphenols.",
            culturalOrigin = "Mediterranean Basin"
        ),
        FoodItem(
            id = "oil_ghee",
            name = "Grass-Fed Desi Ghee (Clarified Butter)",
            category = FoodCategory.OILS_FATS,
            servingSize = "1 tbsp (14g)",
            calories = 120,
            proteinGrams = 0.0f,
            carbsGrams = 0.0f,
            fatGrams = 14.0f,
            fiberGrams = 0.0f,
            micronutrients = listOf(
                Micronutrient("Butyrate (Butyric Acid)", "450 mg", null, "Primary fuel for colonocytes"),
                Micronutrient("Conjugated Linoleic Acid (CLA)", "150 mg", null, "Metabolic lipid modulation"),
                Micronutrient("Vitamin A", "120 mcg", 13, "Fat-soluble vitamin")
            ),
            dietaryTags = listOf("Vegetarian", "Gluten-Free", "Keto-Friendly", "Lactose-Free", "Ayurvedic"),
            description = "Slow-simmered clarified butter with milk solids removed, packed with short-chain butyrate for colon mucosal lining.",
            culinaryNotes = "High smoke point (485°F / 250°C), iconic aroma in South Asian curries, dals, and khichdi.",
            culturalOrigin = "Ancient South Asia"
        ),

        // === BEVERAGES & TEAS (9 items) ===
        FoodItem(
            id = "bev_green_tea",
            name = "Matcha / Green Tea",
            category = FoodCategory.BEVERAGES,
            servingSize = "1 cup (240ml)",
            calories = 2,
            proteinGrams = 0.2f,
            carbsGrams = 0.4f,
            fatGrams = 0.0f,
            fiberGrams = 0.0f,
            micronutrients = listOf(
                Micronutrient("EGCG (Epigallocatechin Gallate)", "120 mg", null, "Potent thermogenic polyphenol"),
                Micronutrient("L-Theanine", "25 mg", null, "Promotes alpha brain waves and calm focus"),
                Micronutrient("Caffeine", "35 mg", null, "Gentle metabolic stimulation")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Zero-Calorie", "Nootropic", "Antioxidant"),
            description = "Steeped Camellia sinensis leaves loaded with EGCG and L-Theanine for calm, sustained cognitive alertness without jitters.",
            culinaryNotes = "Brew at 175°F (80°C) rather than boiling water to avoid scorching delicate tea catechins.",
            culturalOrigin = "China / Japan"
        ),
        FoodItem(
            id = "bev_coconut_water",
            name = "Fresh Coconut Water",
            category = FoodCategory.BEVERAGES,
            servingSize = "1 cup (240ml)",
            calories = 45,
            proteinGrams = 1.7f,
            carbsGrams = 8.9f,
            fatGrams = 0.5f,
            fiberGrams = 2.6f,
            micronutrients = listOf(
                Micronutrient("Potassium", "600 mg", 13, "Natural isotonic electrolyte"),
                Micronutrient("Sodium", "252 mg", 11, "Hydration balance"),
                Micronutrient("Magnesium", "60 mg", 14, "Electrolyte homeostasis")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Isotonic", "Hydration"),
            description = "Natural isotonic fluid containing the 5 essential human electrolytes: potassium, sodium, magnesium, calcium, and phosphorus.",
            culinaryNotes = "Ideal natural post-workout rehydration beverage without artificial colorings or refined syrups.",
            culturalOrigin = "Tropical Regions / South & Southeast Asia"
        ),

        // === PREPARED CULTURAL DISHES (South Asian, Middle Eastern, East Asian, Mediterranean, Global) ===
        FoodItem(
            id = "dish_chicken_biryani",
            name = "Chicken Biryani with Mint Raita",
            category = FoodCategory.CULTURAL_MEALS,
            servingSize = "1 plate (350g)",
            calories = 520,
            proteinGrams = 32.0f,
            carbsGrams = 68.0f,
            fatGrams = 14.0f,
            fiberGrams = 4.2f,
            micronutrients = listOf(
                Micronutrient("Curcumin (from Turmeric)", "45 mg", null, "Anti-inflammatory spice bioactivity"),
                Micronutrient("Saffron Crocin", "8 mg", null, "Mood and neurological support"),
                Micronutrient("Probiotics (from Yogurt Raita)", "2 Billion CFU", null, "Digestive balance"),
                Micronutrient("Iron", "3.2 mg", 18, "Oxygenation")
            ),
            dietaryTags = listOf("Halal", "High-Protein", "South-Asian", "Aromatic-Spices"),
            description = "Celebrated South Asian fragrant basmati rice layered with tender spiced marinated chicken, saffron, mint, and cooling yogurt raita.",
            culinaryNotes = "Pairing with cucumber-mint raita cools capsaicin and adds live probiotic enzymes that ease grain digestion.",
            culturalOrigin = "South Asia (Mughlai / Hyderabadi / Pakistani)",
            glycemicIndex = "Medium"
        ),
        FoodItem(
            id = "dish_chana_masala",
            name = "Chana Masala with Whole Wheat Roti",
            category = FoodCategory.CULTURAL_MEALS,
            servingSize = "1 bowl chana + 1 roti (300g)",
            calories = 380,
            proteinGrams = 16.5f,
            carbsGrams = 62.0f,
            fatGrams = 7.5f,
            fiberGrams = 14.0f,
            micronutrients = listOf(
                Micronutrient("Dietary Fiber", "14 g", 50, "Half of daily fiber requirement"),
                Micronutrient("Folate", "220 mcg", 55, "Cellular division"),
                Micronutrient("Gingerol & Piperine", "15 mg", null, "Digestive enzyme stimulants")
            ),
            dietaryTags = listOf("Vegan", "High-Fiber", "South-Asian", "Plant-Protein"),
            description = "Tender chickpeas simmered in a spiced tomato, ginger, garlic, and amchur (dry mango) gravy, served with handmade atta roti.",
            culinaryNotes = "A complete nutritional synergy: legumes supply lysine while wheat provides methionine for balanced amino acid intake.",
            culturalOrigin = "Northern South Asia (Punjab)",
            glycemicIndex = "Low"
        ),
        FoodItem(
            id = "dish_dal_tadka",
            name = "Yellow Lentil Dal Tadka with Steamed Rice & Salad",
            category = FoodCategory.CULTURAL_MEALS,
            servingSize = "1 serving (320g)",
            calories = 340,
            proteinGrams = 15.0f,
            carbsGrams = 56.0f,
            fatGrams = 6.0f,
            fiberGrams = 9.5f,
            micronutrients = listOf(
                Micronutrient("Iron", "4.8 mg", 27, "Energy and hemoglobin"),
                Micronutrient("Turmeric Curcumin", "35 mg", null, "Cellular protection"),
                Micronutrient("Potassium", "580 mg", 12, "Blood pressure balance")
            ),
            dietaryTags = listOf("Vegetarian", "Gluten-Free-Option", "South-Asian", "Heart-Healthy"),
            description = "Comforting golden lentils tempered with aromatic cumin seeds, garlic, ghee, and fresh cilantro, served with crisp cucumber-tomato kachumber.",
            culinaryNotes = "A daily balanced meal in millions of South Asian homes, rich in polyphenols and gentle prebiotic fiber.",
            culturalOrigin = "South Asia",
            glycemicIndex = "Low"
        ),
        FoodItem(
            id = "dish_chicken_karahi",
            name = "Chicken Karahi (Pakistani Style)",
            category = FoodCategory.CULTURAL_MEALS,
            servingSize = "1 portion (250g)",
            calories = 360,
            proteinGrams = 34.0f,
            carbsGrams = 8.0f,
            fatGrams = 21.0f,
            fiberGrams = 2.4f,
            micronutrients = listOf(
                Micronutrient("Lycopene (from Tomatoes)", "14 mg", null, "Cardioprotective antioxidant"),
                Micronutrient("Gingerol", "18 mg", null, "Digestive stimulant"),
                Micronutrient("Niacin (B3)", "12 mg", 75, "Cellular energy")
            ),
            dietaryTags = listOf("Halal", "High-Protein", "Low-Carb", "South-Asian"),
            description = "Tender chicken wok-cooked with ripe tomatoes, ginger juliennes, green chilies, and black pepper without heavy onions or flour thickeners.",
            culinaryNotes = "High in bioavailable lean protein and lycopene synthesized by cooking tomatoes in healthy oil.",
            culturalOrigin = "Pakistan (Khyber Pakhtunkhwa / Lahore)",
            glycemicIndex = "Low"
        ),
        FoodItem(
            id = "dish_hummus_falafel",
            name = "Mediterranean Hummus & Baked Falafel Bowl",
            category = FoodCategory.CULTURAL_MEALS,
            servingSize = "1 bowl (320g)",
            calories = 410,
            proteinGrams = 18.0f,
            carbsGrams = 48.0f,
            fatGrams = 17.0f,
            fiberGrams = 13.0f,
            micronutrients = listOf(
                Micronutrient("Sesame Lignans (Sesamol)", "28 mg", null, "Lipid antioxidant from tahini"),
                Micronutrient("Folate", "240 mcg", 60, "DNA repair"),
                Micronutrient("Calcium", "165 mg", 13, "Bone health")
            ),
            dietaryTags = listOf("Vegan", "Gluten-Free", "Mediterranean", "High-Fiber"),
            description = "Crispy herb-infused chickpea falafels served over creamy tahini hummus, kalamata olives, cucumber, and fresh parsley.",
            culinaryNotes = "Tahini (sesame paste) adds essential minerals like calcium, zinc, and healthy unsaturated fatty acids.",
            culturalOrigin = "Levant / Middle East",
            glycemicIndex = "Low"
        ),
        FoodItem(
            id = "dish_shawarma_plate",
            name = "Grilled Chicken Shawarma Plate with Fattoush",
            category = FoodCategory.CULTURAL_MEALS,
            servingSize = "1 plate (340g)",
            calories = 460,
            proteinGrams = 36.0f,
            carbsGrams = 32.0f,
            fatGrams = 20.0f,
            fiberGrams = 5.5f,
            micronutrients = listOf(
                Micronutrient("Sumac Polyphenols", "32 mg", null, "Antioxidant spice profile"),
                Micronutrient("Vitamin C", "45 mg", 50, "Tissue synthesis"),
                Micronutrient("Zinc", "3.4 mg", 31, "Immune resilience")
            ),
            dietaryTags = listOf("Halal", "High-Protein", "Middle-Eastern", "Balanced"),
            description = "Cardamom and sumac spiced chicken breast paired with garlic toum, pickled turnip, and sumac-dressed crunchy fattoush salad.",
            culinaryNotes = "Sumac is one of the highest ORAC antioxidant spices in the world, providing natural tartness.",
            culturalOrigin = "Levant / Middle East",
            glycemicIndex = "Medium"
        ),
        FoodItem(
            id = "dish_ramen_chashu",
            name = "Japanese Ramen with Soft-Boiled Egg & Bok Choy",
            category = FoodCategory.CULTURAL_MEALS,
            servingSize = "1 large bowl (450g)",
            calories = 490,
            proteinGrams = 24.0f,
            carbsGrams = 62.0f,
            fatGrams = 16.0f,
            fiberGrams = 4.0f,
            micronutrients = listOf(
                Micronutrient("Choline (from Ajitsuke Tamago)", "140 mg", 25, "Cognitive performance"),
                Micronutrient("Vitamin A (from Bok Choy)", "180 mcg", 20, "Visual cycle"),
                Micronutrient("Glutamate Umami", "1.2 g", null, "Natural savory satiety signaling")
            ),
            dietaryTags = listOf("East-Asian", "Satiating", "Umami-Rich"),
            description = "Wheat ramen noodles in rich savory broth topped with soft ajitsuke tamago egg, tender protein, nori seaweed, and steamed bok choy.",
            culinaryNotes = "Adding nutrient-dense nori and bok choy supplies trace marine iodine and carotenoids.",
            culturalOrigin = "Japan",
            glycemicIndex = "Medium"
        ),
        FoodItem(
            id = "dish_salmon_sushi",
            name = "Salmon Nigiri & Avocado Roll Set",
            category = FoodCategory.CULTURAL_MEALS,
            servingSize = "8 pieces (220g)",
            calories = 360,
            proteinGrams = 19.0f,
            carbsGrams = 46.0f,
            fatGrams = 11.0f,
            fiberGrams = 3.5f,
            micronutrients = listOf(
                Micronutrient("Marine Omega-3 (EPA/DHA)", "1.4 g", null, "Cardiovascular and neuronal health"),
                Micronutrient("Iodine (from Nori)", "120 mcg", 80, "Thyroid hormone T3/T4 synthesis"),
                Micronutrient("Vitamin B12", "2.1 mcg", 88, "Nerve conduction")
            ),
            dietaryTags = listOf("Pescatarian", "High-Omega-3", "East-Asian", "Clean-Eating"),
            description = "Fresh raw sashimi-grade salmon over seasoned sushi rice with avocado slices and mineral-rich nori sheets.",
            culinaryNotes = "Wasabi provides natural isothiocyanates with antimicrobial and anti-inflammatory properties.",
            culturalOrigin = "Japan",
            glycemicIndex = "Medium"
        ),
        FoodItem(
            id = "dish_greek_salad_tzatziki",
            name = "Mediterranean Greek Salad with Kalamata & Feta",
            category = FoodCategory.CULTURAL_MEALS,
            servingSize = "1 large bowl (280g)",
            calories = 290,
            proteinGrams = 9.0f,
            carbsGrams = 12.0f,
            fatGrams = 23.0f,
            fiberGrams = 4.5f,
            micronutrients = listOf(
                Micronutrient("EVOO Polyphenols", "40 mg", null, "Vascular health"),
                Micronutrient("Lycopene", "8.5 mg", null, "Antioxidant protection"),
                Micronutrient("Calcium", "240 mg", 18, "Bone mineral")
            ),
            dietaryTags = listOf("Vegetarian", "Gluten-Free", "Keto-Friendly", "Mediterranean", "Low-Carb"),
            description = "Crisp cucumbers, vine tomatoes, red onions, kalamata olives, and sheep's milk feta tossed in cold-pressed extra virgin olive oil and wild oregano.",
            culinaryNotes = "Classic representation of raw vegetable polyphenol absorption mediated by monounsaturated fats.",
            culturalOrigin = "Greece / Mediterranean",
            glycemicIndex = "Low"
        ),
        FoodItem(
            id = "dish_burrito_bowl",
            name = "Chipotle-Style Chicken & Black Bean Quinoa Bowl",
            category = FoodCategory.CULTURAL_MEALS,
            servingSize = "1 bowl (360g)",
            calories = 480,
            proteinGrams = 35.0f,
            carbsGrams = 52.0f,
            fatGrams = 14.0f,
            fiberGrams = 11.0f,
            micronutrients = listOf(
                Micronutrient("Plant & Animal Protein Matrix", "35 g", null, "Optimal leucine and branched-chain aminos"),
                Micronutrient("Dietary Fiber", "11 g", 39, "Microbiome fuel"),
                Micronutrient("Potassium", "720 mg", 15, "Electrolyte balance")
            ),
            dietaryTags = listOf("Gluten-Free", "High-Protein", "High-Fiber", "Latin-American"),
            description = "Grilled adobo chicken served over tri-color quinoa, black beans, charred corn salsa, avocado chunks, and cilantro lime dressing.",
            culinaryNotes = "Balancing high fiber beans with lean chicken and avocado creates hours of steady glycemic satiety.",
            culturalOrigin = "Mexico / Americas",
            glycemicIndex = "Low"
        )
    )

    fun getFoodById(id: String): FoodItem? {
        return foods.firstOrNull { it.id.equals(id, ignoreCase = true) }
    }

    fun searchFoods(
        query: String = "",
        category: FoodCategory? = null,
        dietaryTag: String? = null
    ): List<FoodItem> {
        return foods.filter { food ->
            val matchesQuery = query.isBlank() ||
                food.name.contains(query, ignoreCase = true) ||
                food.description.contains(query, ignoreCase = true) ||
                food.culturalOrigin.contains(query, ignoreCase = true) ||
                food.dietaryTags.any { it.contains(query, ignoreCase = true) }

            val matchesCategory = category == null || food.category == category

            val matchesTag = dietaryTag == null || dietaryTag == "All" ||
                food.dietaryTags.any { it.equals(dietaryTag, ignoreCase = true) }

            matchesQuery && matchesCategory && matchesTag
        }
    }
}
