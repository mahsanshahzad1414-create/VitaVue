package com.example.data.datasource

import com.example.data.model.ArticleCategory
import com.example.data.model.ArticleSection
import com.example.data.model.NutritionArticle
import com.example.data.model.NutritionMyth

object NutritionKnowledgeSource {

    val myths: List<NutritionMyth> = listOf(
        NutritionMyth(
            id = "myth_carbs_bad",
            myth = "Carbohydrates are inherently fattening and should be eliminated.",
            fact = "Carbohydrates are the body and brain's primary energy substrate. Quality and dietary fiber matter far more than total carb quantity.",
            evidenceExplanation = "Unrefined complex carbohydrates (oats, legumes, whole grains, root vegetables) come packaged with viscous soluble fiber, resistant starch, and polyphenols that slow glucose absorption, nourish the colonic microbiome, and elevate satiety hormones (GLP-1, PYY). Weight gain is driven by hyper-palatable processed energy density, not wholesome carbohydrate molecules.",
            practicalTip = "Focus on intact grains (quinoa, brown basmati, oats) and legumes rather than refined flours and isolated syrups.",
            category = "Macronutrients"
        ),
        NutritionMyth(
            id = "myth_all_fats_unhealthy",
            myth = "Eating dietary fat makes you gain body fat and clogs arteries.",
            fact = "Essential fatty acids (Omega-3 and Omega-6) and monounsaturated fats are critical for hormone synthesis, cell membranes, and nutrient absorption.",
            evidenceExplanation = "Decades of randomized clinical trials (such as the PREDIMED trial) show that diets rich in extra virgin olive oil, avocados, and nuts significantly reduce cardiovascular mortality without inducing unwanted weight gain. Fats also facilitate the absorption of fat-soluble vitamins (A, D, E, K).",
            practicalTip = "Include extra virgin olive oil, chia seeds, walnuts, and wild fatty fish regularly in your weekly meal rotation.",
            category = "Fats & Lipids"
        ),
        NutritionMyth(
            id = "myth_fruit_sugar",
            myth = "Fruit sugar (fructose) is just as harmful as high-fructose corn syrup.",
            fact = "Whole fruits contain fructose encased within cellular water, insoluble cellulose, and soluble pectin matrices that prevent rapid hepatic fructose flooding.",
            evidenceExplanation = "Consuming whole fruit is consistently correlated with lower risks of Type 2 diabetes and cardiovascular disease in large epidemiological cohorts. Intact fiber slows gastric emptying and ensures gradual digestive transit, unlike isolated crystalline syrups in beverages.",
            practicalTip = "Eat whole fresh fruits with peel intact when possible, rather than strained juices or fruit concentrates.",
            category = "Carbohydrates"
        ),
        NutritionMyth(
            id = "myth_detox_diets",
            myth = "Juice cleanses and detox teas are necessary to remove bodily toxins.",
            fact = "The human liver, kidneys, lungs, skin, and gastrointestinal tract maintain continuous, highly sophisticated endogenous detoxification pathways.",
            evidenceExplanation = "Phase I (Cytochrome P450 enzymes) and Phase II (glucuronidation, sulfation, glutathione conjugation) hepatic detox pathways require amino acids, sulfur compounds (from cruciferous vegetables and alliums), and B-vitamins—not calorie deprivation or laxative teas.",
            practicalTip = "Support liver function naturally with adequate protein, cruciferous vegetables (broccoli, cabbage), garlic, and 2-3 liters of water daily.",
            category = "Metabolism"
        ),
        NutritionMyth(
            id = "myth_protein_excess",
            myth = "More protein is always better; there is no ceiling to its benefits.",
            fact = "While adequate protein (1.2–2.0g/kg for active individuals) is vital, muscle protein synthesis (MPS) plateaus per meal, and excess calories are stored as fat or oxidized.",
            evidenceExplanation = "The 'muscle-full' effect shows that ~20-40g of high-quality protein (containing ~2.5-3g leucine) maximally stimulates the mTORC1 pathway in a single sitting. Distributing protein evenly across 3-4 meals produces superior muscle retention compared to a single massive bolus.",
            practicalTip = "Target 25-35g of bioavailable protein per meal rather than consuming the bulk of your protein in one giant dinner.",
            category = "Protein"
        ),
        NutritionMyth(
            id = "myth_eating_late_fat",
            myth = "Eating after 8:00 PM automatically turns food directly into body fat.",
            fact = "Total 24-hour energy balance and nutrient composition determine weight change, not an arbitrary clock hour.",
            evidenceExplanation = "While circadian alignment and eating during daylight hours optimize insulin sensitivity and digestive enzyme release, calories consumed late at night are subject to the identical biochemical laws of thermodynamics. Late-night eating often leads to mindless overconsumption of calorie-dense snacks rather than metabolic malfunction.",
            practicalTip = "Leave a 2-hour window between your last meal and sleep to enhance sleep architecture and prevent acid reflux.",
            category = "Energy Balance"
        ),
        NutritionMyth(
            id = "myth_supplements_essential",
            myth = "You must take a cabinet full of multivitamins and supplements to be healthy.",
            fact = "Whole food matrices contain thousands of synergistic co-factors, flavonoids, and fiber that synthetic pills cannot replicate.",
            evidenceExplanation = "With specific exceptions (e.g., Vitamin D in low-sun regions, B12 for strict vegans, prenatal folate, or clinically diagnosed deficiencies), randomized trials repeatedly demonstrate that general multivitamin supplementation in healthy populations does not reduce chronic disease incidence compared to a colorful whole-food dietary pattern.",
            practicalTip = "Prioritize whole diverse foods first; supplement strategically only based on bloodwork or specific dietary restrictions.",
            category = "Vitamins & Minerals"
        ),
        NutritionMyth(
            id = "myth_natural_always_healthy",
            myth = "'Natural' or 'organic' stamped on a package guarantees the food is healthy.",
            fact = "'Natural' is an unstandardized marketing term; organic cane sugar and deep-fried chips carry the exact same caloric and glycemic load.",
            evidenceExplanation = "Processing level (NOVA classification) is a far stronger predictor of metabolic health outcomes than marketing labels. Ultra-processed foods engineered with salt-sugar-fat bliss points override satiety neurochemistry regardless of whether their raw ingredients were labeled organic.",
            practicalTip = "Read the ingredients list and nutrient facts panel rather than relying on front-of-package marketing slogans.",
            category = "Food Literacy"
        )
    )

    val articles: List<NutritionArticle> = listOf(
        // Macronutrients
        NutritionArticle(
            id = "art_protein_mastery",
            slug = "protein-mastery-science",
            title = "Protein Fundamentals: Muscle Synthesis, Satiety & Bioavailability",
            summary = "Explore the biochemistry of amino acids, the leucine trigger threshold, and how to optimize daily protein distribution across diverse cuisines.",
            category = ArticleCategory.MACRONUTRIENTS,
            readingTimeMin = 6,
            difficulty = "Intermediate",
            sections = listOf(
                ArticleSection(
                    heading = "The Role of Essential Amino Acids",
                    content = "Proteins are polymer chains constructed from 20 amino acids, 9 of which are essential (EAAs) because the human body lacks the enzymatic machinery to synthesize them de novo. These amino acids serve not only as muscular building blocks but as structural collagen, immune immunoglobulins, enzymatic catalysts, and peptide hormones."
                ),
                ArticleSection(
                    heading = "The Leucine Trigger & Muscle Protein Synthesis",
                    content = "Among the branched-chain amino acids, L-Leucine acts as the primary molecular trigger for the mTORC1 (mechanistic target of rapamycin) kinase pathway. To initiate peak muscle protein synthesis in adults, a single meal should generally deliver between 2.2g and 3.0g of leucine, typically found in 25–35g of high-quality animal protein or 35–45g of complementary plant protein blends."
                ),
                ArticleSection(
                    heading = "Plant Protein Complementarity in Practice",
                    content = "Contrary to outdated theories, plant-based eaters do not need to combine specific proteins in every single bite. The human body maintains a dynamic circulating amino acid pool. Pairing legumes (rich in lysine, low in methionine) with cereal grains (rich in methionine, low in lysine)—such as South Asian Dal with Roti, Mexican Rice with Black Beans, or Middle Eastern Hummus with Pita—across the day provides a complete amino acid score."
                )
            ),
            keyTakeaways = listOf(
                "Aim for 1.2 to 1.8 grams of protein per kilogram of body weight for active wellness.",
                "Distribute protein intake evenly across 3 to 4 meals (25-35g per meal) to maximize synthesis.",
                "Combine diverse grains, legumes, seeds, and nuts to achieve complete essential amino acid profiles."
            ),
            relatedTopicSlugs = listOf("fiber-gut-microbiome", "energy-balance-metabolism")
        ),

        NutritionArticle(
            id = "art_fiber_gut_microbiome",
            slug = "fiber-gut-microbiome",
            title = "Dietary Fiber & The Gut Microbiome: Beyond Regularity",
            summary = "Understand soluble, insoluble, and prebiotic resistant starches, and how short-chain fatty acids (SCFAs) regulate immunity and brain chemistry.",
            category = ArticleCategory.MACRONUTRIENTS,
            readingTimeMin = 7,
            difficulty = "Intermediate",
            sections = listOf(
                ArticleSection(
                    heading = "The Fiber Spectrum: Soluble vs. Insoluble",
                    content = "Dietary fiber represents non-digestible carbohydrates that transit through the upper gastrointestinal tract intact. Insoluble fiber (cellulose, lignin in wheat bran and vegetable skins) adds mechanical bulk and accelerates transit. Soluble fiber (pectin in apples, beta-glucan in oats, mucilage in chia) dissolves in water to form a viscous gel that traps cholesterol and moderates postprandial blood glucose spikes."
                ),
                ArticleSection(
                    heading = "Fermentation & Short-Chain Fatty Acids (SCFAs)",
                    content = "When prebiotic fiber reaches the distal colon, anaerobic commensal microbes ferment it into powerful short-chain fatty acids: acetate, propionate, and butyrate. Butyrate serves as the primary metabolic fuel for colonic epithelial cells, sealing tight junctions to prevent gut permeability and suppressing systemic inflammation."
                ),
                ArticleSection(
                    heading = "The Microbiota-Gut-Brain Axis",
                    content = "Over 90% of the body's serotonin receptors and significant vagal nerve signaling pathways reside in the enteric nervous system. By supporting a diverse microbial ecosystem with 30+ distinct plant species per week, dietary fiber directly influences mood, stress resilience, and neurocognitive clarity."
                )
            ),
            keyTakeaways = listOf(
                "Target at least 28–38 grams of total dietary fiber per day from whole foods.",
                "Aim for 'Plant Diversity': consuming 30 distinct plant types per week optimizes microbial richness.",
                "Increase fiber intake gradually while increasing water consumption to avoid transient digestive gas."
            ),
            relatedTopicSlugs = listOf("protein-mastery-science", "balanced-plate-method")
        ),

        NutritionArticle(
            id = "art_healthy_fats_lipids",
            slug = "healthy-fats-lipids",
            title = "The Lipid Blueprint: Omega-3s, Monounsaturates & Cellular Fluidity",
            summary = "Demystifying saturated, monounsaturated, and polyunsaturated fats, with an emphasis on EPA/DHA marine lipids and anti-inflammatory pathways.",
            category = ArticleCategory.MACRONUTRIENTS,
            readingTimeMin = 5,
            difficulty = "Beginner",
            sections = listOf(
                ArticleSection(
                    heading = "Lipid Structure & Biological Function",
                    content = "Fats are concentrated energy sources yielding 9 kcal/gram, but their most vital role is architectural: forming the lipid bilayer of trillions of human cell membranes, insulating myelin nerve sheaths, and acting as precursors for prostaglandins and steroid hormones."
                ),
                ArticleSection(
                    heading = "The Omega-6 to Omega-3 Balance",
                    content = "Modern diets frequently exhibit an inflammatory Omega-6 to Omega-3 ratio of 16:1 or higher, primarily driven by ultra-refined seed oils. Restoring a ratio closer to 3:1 through cold-water fatty fish (salmon, sardines, mackerel), chia seeds, flaxseeds, and walnuts dampens systemic inflammatory cytokines."
                ),
                ArticleSection(
                    heading = "Monounsaturated Oleic Acid in Culinary Traditions",
                    content = "Extra virgin olive oil and avocados are dense in oleic acid (Omega-9), which is resistant to thermal oxidation and enhances endothelial nitric oxide synthesis, keeping arteries flexible and healthy."
                )
            ),
            keyTakeaways = listOf(
                "Replace highly refined industrial seed oils with extra virgin olive oil, avocado, and nuts.",
                "Consume 2 servings of fatty cold-water fish weekly or take an algae-derived EPA/DHA supplement.",
                "Store delicate omega-3 rich oils and ground flaxseeds in airtight, refrigerated containers."
            ),
            relatedTopicSlugs = listOf("mediterranean-diet-blueprint")
        ),

        // Vitamins & Minerals
        NutritionArticle(
            id = "art_micronutrient_powerhouses",
            slug = "essential-vitamins-and-minerals",
            title = "Micronutrient Synergy: Iron, Vitamin C, Calcium & Vitamin D",
            summary = "How vitamins and minerals interact biochemically, how to maximize plant-based iron absorption, and preventing common global deficiencies.",
            category = ArticleCategory.MINERALS,
            readingTimeMin = 6,
            difficulty = "Beginner",
            sections = listOf(
                ArticleSection(
                    heading = "Bioavailability & The Vitamin C + Iron Pair",
                    content = "Non-heme iron (found in lentils, spinach, beans, and seeds) exists in the ferric (Fe3+) state, which is poorly absorbed. Pairing plant-based iron with ascorbic acid (Vitamin C from citrus, tomatoes, or bell peppers) reduces ferric iron to the highly absorbable ferrous (Fe2+) state, boosting absorption by up to 300%."
                ),
                ArticleSection(
                    heading = "The Calcium, Vitamin D3 & Vitamin K2 Triad",
                    content = "Bone mineral density is governed by an exquisite triad: Vitamin D3 facilitates intestinal calcium absorption; Calcium provides mineral scaffold density; and Vitamin K2 (MK-7) activates osteocalcin to direct calcium directly into bones while preventing vascular calcification."
                ),
                ArticleSection(
                    heading = "Magnesium: The 300-Enzyme Regulator",
                    content = "Magnesium is a vital enzymatic cofactor involved in over 300 cellular biochemical reactions, including ATP energy production, DNA synthesis, glucose homeostasis, and parasympathetic nervous system tone."
                )
            ),
            keyTakeaways = listOf(
                "Always squeeze lemon juice or add fresh tomatoes/peppers over cooked lentils and leafy greens.",
                "Include magnesium-rich pumpkin seeds, dark chocolate, spinach, and black beans in your diet.",
                "Pair calcium foods with sunshine exposure or vitamin D3 for optimal skeletal and muscular health."
            ),
            relatedTopicSlugs = listOf("fiber-gut-microbiome", "balanced-plate-method")
        ),

        // Practical Nutrition & Patterns
        NutritionArticle(
            id = "art_balanced_plate_method",
            slug = "balanced-plate-method",
            title = "The Visual Plate Method: Effortless Portion Control Without Obsessive Counting",
            summary = "A practical, evidence-based visual heuristic for constructing nutritious, satisfying meals anywhere in the world.",
            category = ArticleCategory.PRACTICAL,
            readingTimeMin = 4,
            difficulty = "Beginner",
            sections = listOf(
                ArticleSection(
                    heading = "The 50 / 25 / 25 Division",
                    content = "Rather than carrying food scales everywhere, visualize a standard 9-inch dinner plate divided into three intuitive zones: 1/2 of the plate filled with colorful non-starchy vegetables and greens; 1/4 of the plate allocated to quality lean or plant protein; and 1/4 of the plate reserved for high-fiber complex carbohydrates."
                ),
                ArticleSection(
                    heading = "The Healthy Fat Thumbnail Rule",
                    content = "Incorporate a thumb-sized portion of healthy fats (such as a drizzle of extra virgin olive oil, 1/4 avocado, a sprinkle of toasted seeds, or a teaspoon of desi ghee) to ensure hormonal health and complete fat-soluble vitamin absorption."
                ),
                ArticleSection(
                    heading = "Adaptable Across Global Cuisines",
                    content = "This framework translates effortlessly: for South Asian dining, fill half the plate with cucumber-tomato salad and sauteed spinach (palak), one quarter with chicken tikka or dal, and one quarter with brown basmati rice or one whole wheat roti."
                )
            ),
            keyTakeaways = listOf(
                "50% colorful vegetables + 25% protein + 25% complex whole grains + 1 thumb of healthy fat.",
                "Focus on color variety across the week to ensure comprehensive polyphenol intake.",
                "Eat slowly, giving gastric stretch receptors and satiety hormones 15-20 minutes to signal fullness."
            ),
            relatedTopicSlugs = listOf("protein-mastery-science", "student-budget-nutrition")
        ),

        NutritionArticle(
            id = "art_mediterranean_blueprint",
            slug = "mediterranean-diet-blueprint",
            title = "The Mediterranean Dietary Pattern: Longevity & Metabolic Health",
            summary = "Examining the scientific evidence behind the world's most rigorously researched dietary pattern.",
            category = ArticleCategory.PATTERNS,
            readingTimeMin = 5,
            difficulty = "Intermediate",
            sections = listOf(
                ArticleSection(
                    heading = "The Core Tenets of Mediterranean Eating",
                    content = "The traditional Mediterranean diet is characterized by an abundance of minimally processed plant foods: vegetables, fruits, legumes, whole grains, nuts, and seeds; extra virgin olive oil as the principal source of added fat; moderate consumption of fish and poultry; and minimal red meat and refined sugars."
                ),
                ArticleSection(
                    heading = "Cardiovascular & Cognitive Outcomes",
                    content = "Randomized clinical trials (such as the landmark PREDIMED study) demonstrated a 30% reduction in major cardiovascular events among participants adhering to an EVOO or nut-enriched Mediterranean diet compared to standard low-fat control groups."
                ),
                ArticleSection(
                    heading = "Lifestyle & Mindful Eating",
                    content = "Crucially, the Mediterranean paradigm encompasses more than food chemistry: it embraces shared communal meals, physical activity integrated into daily life, and unhurried culinary enjoyment."
                )
            ),
            keyTakeaways = listOf(
                "Make extra virgin olive oil your primary daily cooking and finishing fat.",
                "Eat legumes at least 3-4 times per week as hearty meal foundations.",
                "Emphasize whole seasonal produce, seafood twice weekly, and mindful communal eating."
            ),
            relatedTopicSlugs = listOf("healthy-fats-lipids", "balanced-plate-method")
        ),

        NutritionArticle(
            id = "art_student_budget_nutrition",
            slug = "student-budget-nutrition",
            title = "High-Nutrition on a Student Budget: Smart Staples & Meal Prep",
            summary = "Practical strategies for maximizing nutritional density per dollar spent using global wholesome pantry items.",
            category = ArticleCategory.PRACTICAL,
            readingTimeMin = 4,
            difficulty = "Beginner",
            sections = listOf(
                ArticleSection(
                    heading = "High-Value Nutrient Powerhouses",
                    content = "The most nutrient-dense foods on Earth are often the least expensive per serving. Dried lentils, canned chickpeas, frozen vegetables (which match or exceed fresh produce nutrient retention), rolled oats, eggs, and peanut butter offer world-class nutrition for pennies per portion."
                ),
                ArticleSection(
                    heading = "Batch Cooking & Smart Freezing",
                    content = "Cooking a large pot of Chana Masala, Black Bean Chili, or Lentil Dal on Sunday yields 4-5 ready-to-eat lunches throughout the week, eliminating the temptation of expensive, hyper-processed convenience fast food."
                )
            ),
            keyTakeaways = listOf(
                "Stock up on dry legumes, brown rice, whole oats, eggs, and frozen spinach/berries.",
                "Batch cook nutrient-dense stews and grain bases once or twice a week.",
                "Season with bulk spices (turmeric, cumin, garlic, chili) for antioxidant benefits on a budget."
            ),
            relatedTopicSlugs = listOf("balanced-plate-method")
        )
    )

    fun getArticleBySlug(slug: String): NutritionArticle? {
        return articles.firstOrNull { it.slug.equals(slug, ignoreCase = true) }
    }

    fun searchArticles(query: String = "", category: ArticleCategory? = null): List<NutritionArticle> {
        return articles.filter { article ->
            val matchesQuery = query.isBlank() ||
                article.title.contains(query, ignoreCase = true) ||
                article.summary.contains(query, ignoreCase = true) ||
                article.keyTakeaways.any { it.contains(query, ignoreCase = true) }

            val matchesCat = category == null || article.category == category

            matchesQuery && matchesCat
        }
    }
}
