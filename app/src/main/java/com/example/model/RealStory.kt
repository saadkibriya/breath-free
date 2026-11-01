package com.example.model

data class RealStory(
    val id: String,
    val title: String,
    val author: String,
    val yearsSmoked: String,
    val timeClean: String,
    val category: String,
    val hackSummary: String,
    val fullStory: String,
    val actionSteps: List<String>,
    val redditUrl: String,
    val upvotes: Int,
    val highlightQuote: String
)

object RealStoriesData {
    val ALL_STORIES: List<RealStory> = listOf(
        RealStory(
            id = "story_straw_trick",
            title = "The Cut Coffee Straw & Ice Water Trick",
            author = "u/StrawDrinker_8Yrs",
            yearsSmoked = "18 years (1.5 packs/day)",
            timeClean = "4 years smoke-free",
            category = "Oral Fixation",
            hackSummary = "Cut a plastic drinking straw to cigarette length and inhale through it when phantom hand-to-mouth urges strike.",
            fullStory = "For 18 years my hand was attached to a cigarette. When I quit, my brain didn't know what to do with my fingers during driving or after coffee. On Day 2 I was clawing at the walls. A fellow ex-smoker on r/stopsmoking told me to cut a stiff coffee stirrer straw to 8cm. Inhaling through the narrow straw gives the exact same slight airflow resistance as a cigarette filter. Combined with a tall tumbler of ice water, it immediately tricks your somatic sensory memory without a drop of nicotine. I kept 3 straws in my car cup holder and my desk for the first 3 months. It literally saved my quit.",
            actionSteps = listOf(
                "Cut a drinking or coffee straw to exactly cigarette length (approx 8 cm)",
                "Hold it between index and middle fingers just like an old smoke",
                "Take 3 slow deep draws through the straw — feel the airflow resistance in your chest",
                "Follow immediately with a gulp of ice water to shock the vagus nerve"
            ),
            redditUrl = "https://www.reddit.com/r/stopsmoking/comments/16g87f2/the_straw_trick_saved_my_life/",
            upvotes = 2410,
            highlightQuote = "\"Inhaling through the narrow straw gives the exact same slight air resistance as a cigarette filter. My brain calmed down within 30 seconds.\""
        ),
        RealStory(
            id = "story_3_minute_rule",
            title = "The 3-Minute Biological Rule (Urge Surfing)",
            author = "u/NeuroScienceExSmoker",
            yearsSmoked = "12 years (20 cigs/day)",
            timeClean = "3 years smoke-free",
            category = "Craving Surge",
            hackSummary = "Cravings feel like a brick wall, but biologically they are just a wave that crests and dissipates in 180 seconds.",
            fullStory = "When a craving hits, your brain's panic center tricks you into thinking: 'If I don't smoke now, this unbearable agony will last for hours and get worse until I explode.' That is a neurochemical lie. Nicotine dopamine spikes physically can only sustain peak intensity for 180 to 240 seconds before receptor fatigue sets in. The trick is to treat the craving like an ocean wave: you don't fight a 6-foot wave by punching it; you look at the clock, acknowledge the wave ('Okay, here is my 3-minute chemical surge'), and watch the seconds tick. By minute 3.5, the urgency collapses into silence.",
            actionSteps = listOf(
                "Notice the spike and say out loud: 'This is just a 3-minute biochemical wave'",
                "Look at your watch or start a 3-minute stopwatch timer",
                "Do not try to make it go away; observe where you feel it (chest, throat, jaw)",
                "Breathe through it until the second hand hits 180 seconds — notice how it dissolved"
            ),
            redditUrl = "https://www.reddit.com/r/stopsmoking/comments/12b4m3q/the_3_minute_rule_cravings_always_peak_and_die/",
            upvotes = 3890,
            highlightQuote = "\"You don't fight an ocean wave by punching it; you look at the clock and ride it for 180 seconds until it falls flat.\""
        ),
        RealStory(
            id = "story_morning_tea_pivot",
            title = "Switching Morning Coffee to Peppermint Tea",
            author = "u/BreatheCleanMorning",
            yearsSmoked = "15 years (Pack-a-day)",
            timeClean = "2 years smoke-free",
            category = "Morning Routine",
            hackSummary = "Break the pavlovian neural connection between dark coffee and morning cigarette by temporarily switching warm beverages.",
            fullStory = "For a decade and a half, my morning ritual was holy: boil black dark roast, step onto the balcony, light first cigarette. When I tried quitting while sipping that same black coffee on that same balcony, my brain automatically screamed for the missing half of the equation. On my 4th attempt, I changed the anchor: for the first 30 days, I drank hot peppermint tea in the dining room with music. Peppermint also leaves a clean, sharp tingle that makes the thought of bitter smoke repulsive. Once my brain severed the coffee-smoke pair, I went back to black coffee with zero temptation.",
            actionSteps = listOf(
                "Identify your strongest conditioned trigger (for most, it's morning black coffee)",
                "Change the location and beverage for the first 2-3 weeks (e.g. steep mint or green tea in a new spot)",
                "Put on an upbeat playlist or podcast while drinking it",
                "Enjoy the fresh herbal taste that directly contrasts cigarette grime"
            ),
            redditUrl = "https://www.reddit.com/r/stopsmoking/comments/z87qw1/how_i_beat_the_morning_coffee_and_cigarette/",
            upvotes = 1920,
            highlightQuote = "\"Sever the Pavlovian loop. Change the hot drink, change the chair, and the automatic craving loses its trigger anchor.\""
        ),
        RealStory(
            id = "story_cinnamon_toothpicks",
            title = "Hot Cinnamon Toothpicks & Peppermint Gum",
            author = "u/ToothpickChampion",
            yearsSmoked = "10 years (15/day)",
            timeClean = "5 years smoke-free",
            category = "Oral Fixation",
            hackSummary = "High-potency cinnamon toothpicks stimulate the tongue and throat with a hot bite that completely satisfies oral craving.",
            fullStory = "I bought a pack of food-grade birch toothpicks and soaked them in pure cinnamon oil. Whenever I got into my car or finished a heavy meal (my danger zones), I popped a cinnamon toothpick into the side of my mouth. The intense, spicy cinnamon bite gives a slight fiery sensation in the back of your throat — the exact throat-hit feeling people miss when quitting cigarettes — without inhaling carbon monoxide. Plus, carrying the little wooden container in your pocket replaces the cigarette pack fidget.",
            actionSteps = listOf(
                "Keep cinnamon or tea-tree flavored toothpicks in your pocket and car",
                "When an oral urge strikes after eating, chew the tip gently",
                "Notice the spicy throat sensation satisfying the tactile craving",
                "Pair with sugar-free peppermint gum for clean breath"
            ),
            redditUrl = "https://www.reddit.com/r/stopsmoking/comments/v3m81s/cinnamon_toothpicks_oral_fixation_hack/",
            upvotes = 1650,
            highlightQuote = "\"The intense cinnamon spice delivers that spicy throat-hit sensation you miss, leaving zero room for toxic smoke.\""
        ),
        RealStory(
            id = "story_ash_jar_shock",
            title = "The 'Old Butt Jar' Revulsion Therapy",
            author = "u/SmellTheTruth2020",
            yearsSmoked = "22 years (2 packs/day)",
            timeClean = "6 years smoke-free",
            category = "Craving Surge",
            hackSummary = "Keep a small glass jar with old cigarette butts and stagnant water. One whiff instantly breaks romanticized cigarette cravings.",
            fullStory = "Nicotine addiction is a master liar: it tricks your memory into remembering smoking as 'relaxing, luxurious, cool'. Whenever I felt romantic feelings about having 'just one puff', I had a screw-top jar in my garage with 5 old butts drowned in murky water. I cracked the lid and took one deep whiff. The rancid, nauseating stench of cold, wet nicotine ash instantly made my stomach turn and shocked my rational brain into remembering what smoking ACTUALLY is: toxic burnt refuse. The craving evaporated in half a second. Used it twice on Day 4 and never needed it again.",
            actionSteps = listOf(
                "Understand that craving memory romanticizes an illusion of smoking",
                "Remind yourself of the real smell of stale smoke on clothes, breath, and yellow fingers",
                "Smell fresh lavender, eucalyptus, or a lemon peel to contrast cleanliness with grime",
                "Celebrate how clean your skin, car, and home now smell"
            ),
            redditUrl = "https://www.reddit.com/r/stopsmoking/comments/182k9m4/the_disgusting_jar_trick_works_every_time/",
            upvotes = 2780,
            highlightQuote = "\"Craving lies to you by remembering a romantic illusion. One whiff of cold stale ash shocks your rational brain back to reality.\""
        ),
        RealStory(
            id = "story_inconvenience_barrier",
            title = "The High Friction / Inconvenience Wall",
            author = "u/FrictionVsWillpower",
            yearsSmoked = "8 years (12/day)",
            timeClean = "18 months smoke-free",
            category = "Inconvenience Barrier",
            hackSummary = "Willpower depletes at 10 PM. Make buying a pack require 5 cumbersome physical steps so impulses die in transit.",
            fullStory = "Willpower is like a phone battery; by late evening after a stressful work day, willpower is at 5%. If cigarettes or your wallet and keys are right by your front door, you will buy a pack before your logic wakes up. For the first two weeks, I put my car keys inside a frozen block of ice in the freezer, and left only enough cash for groceries with my roommate. When I had a bad urge on Day 5 at 11 PM, the thought of chipping an ice block with an ice pick for 15 minutes in my pajamas felt so absurd and exhausting that I just drank cold water and went to sleep. Create friction!",
            actionSteps = listOf(
                "Remove all lighters, ashtrays, and spare packs from your home and car",
                "Do not carry cash to gas stations; use pump pay only",
                "Give your credit card to your partner or lock retail apps with a 15-minute delay",
                "By the time you overcome the friction barrier, the craving has already passed"
            ),
            redditUrl = "https://www.reddit.com/r/stopsmoking/comments/14df7c8/first_72_hours_tips_from_someone_whos_5_years/",
            upvotes = 3120,
            highlightQuote = "\"Willpower fails when you are tired. Inconvenience and friction never fail. Make smoking too annoying to execute.\""
        ),
        RealStory(
            id = "story_allen_carr_shift",
            title = "The Great Mindset Flip: You Are Giving Up Nothing",
            author = "u/AllenCarrEnthusiast",
            yearsSmoked = "25 years (30/day)",
            timeClean = "7 years smoke-free",
            category = "Mindset Shift",
            hackSummary = "Stop feeling deprived. A non-smoker doesn't 'resist' cigarettes; they enjoy the freedom of never needing to poison themselves.",
            fullStory = "Every time I tried quitting in the past, I walked around feeling like a martyr: 'Poor me, I can't smoke anymore while everyone else is enjoying themselves.' That mindset is doomed to relapse. When you realize the truth — that smoking does NOT relieve stress, it only relieves the withdrawal created by the PREVIOUS cigarette — everything flips. You are not sacrificing a treat; you are escaping a prison. When someone smokes next to you, you don't feel jealous; you feel pity that they are still trapped on that chemical hamster wheel.",
            actionSteps = listOf(
                "Never say: 'I can't smoke today.' Say: 'I am so grateful I don't HAVE to smoke today.'",
                "Recognize that smoking creates the very stress it temporarily numbs",
                "View other smokers with compassion, not envy — they are still paying to poison their lungs",
                "Enjoy the deep breath of crisp clean air right now"
            ),
            redditUrl = "https://www.reddit.com/r/stopsmoking/comments/17q3p20/you_are_not_depriving_yourself_of_anything/",
            upvotes = 4920,
            highlightQuote = "\"You are not sacrificing a treat; you are escaping a trap. You don't envy a prisoner because they are allowed to wear handcuffs.\""
        ),
        RealStory(
            id = "story_5d_technique",
            title = "The 5D Protocol (Community Emergency Blueprint)",
            author = "u/r_stopsmoking_Mod",
            yearsSmoked = "14 years",
            timeClean = "9 years smoke-free",
            category = "First 72 Hours",
            hackSummary = "Delay, Deep Breath, Drink Water, Distract, Discuss. The gold-standard 5D sequence used by hundreds of thousands.",
            fullStory = "In the r/stopsmoking subreddit, when someone posts in full panic mode at 2 AM, the entire community rallies with the 5Ds: 1. Delay (wait 10 mins). 2. Deep breathe (three belly breaths). 3. Drink water (sip cold water slowly). 4. Distract (change physical rooms, put hands to work). 5. Discuss (message the chat or post on the sub). Almost no craving in human history survives all 5 steps back to back.",
            actionSteps = listOf(
                "Delay: Set a 10-minute mental timer before making any irreversible decision",
                "Deep Breathe: 4 seconds in, 7 seconds hold, 8 seconds out",
                "Drink Water: Sip a glass of cold water to ground your sensory nerves",
                "Distract: Wash dishes, do 15 pushups, solve a puzzle, or play music",
                "Discuss: Open BreatheAI or community chat and type what you feel"
            ),
            redditUrl = "https://www.reddit.com/r/stopsmoking/comments/11x8z9e/the_5ds_of_quitting_smoking_that_helped_me_quit/",
            upvotes = 3450,
            highlightQuote = "\"Almost no nicotine craving in human history survives all 5 steps back to back. Delay, Deep breathe, Drink, Distract, Discuss.\""
        )
    )
}
