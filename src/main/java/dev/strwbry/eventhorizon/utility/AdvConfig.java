package dev.strwbry.eventhorizon.utility;

import dev.strwbry.eventhorizon.EventHorizon;


public class AdvConfig {

    /**
     * Retrieves a double value from the advanced configuration.
     * @param path the path of the configuration setting
     * @param def the default value to return if the setting is not found
     */
    private static double getD(String path, double def){
        return EventHorizon.getAdvConfig().getDouble(path, def);
    }

    /**
     * @see #getD(String, double)
     */
    private static boolean getB(String path, boolean def){
        return EventHorizon.getAdvConfig().getBoolean(path, def);
    }
    /**
     * @see #getD(String, double)
     */
    private static int getI(String path, int def){
        return EventHorizon.getAdvConfig().getInt(path, def);
    }

    public static void reload() {
        EventHorizon.reloadAdvConfig();
    }


    // Attributes //


    // Fasting Event

    // Growth Spurt Event
    private static final String growthPath = "attributes.events.growth-spurt.";
    /*public static double getGrowthScale(){
        return EventHorizon.getAdvConfig().getDouble("attributes.events.growth-spurt.scale-mult", 1);
    }*/
    public static double getGrowthScale(){
        return getD(growthPath+"scale-mult", 1);
    }
    public static double getGrowthMaxHP(){
        return getD(growthPath+"max-health", 4);
    }
    public static double getGrowthAttack(){
        return getD(growthPath+"attack-dmg", 1);
    }
    public static double getGrowthKnockResist(){
        return getD(growthPath+"knockback-resist-mult", 0.5);
    }
    public static double getGrowthMovespeed(){
        return getD(growthPath+"movespeed-mult", 0.5);
    }
    public static double getGrowthSneakspeed(){
        return getD(growthPath+"sneakspeed-mult", 0.5);
    }
    public static double getGrowthWaterMove(){
        return getD(growthPath+"water-movespeed-mult", 0.5);
    }
    public static double getGrowthStepHeight(){
        return getD(growthPath+"step-height", 1);
    }
    public static double getGrowthJumpStr(){
        return getD(growthPath+"jump-strength-mult", 0.5);
    }
    public static double getGrowthSafeFall(){
        return getD(growthPath+"safe-fall-dist", 2);
    }
    public static double getGrowthBlockInteract(){
        return getD(growthPath+"block-interact-range-mult", 0.25);
    }
    public static double getGrowthEntityInteract(){
        return getD(growthPath+"entity-interact-range-mult", 0.25);
    }

    // Half a Heart Event

    // Honey I Shrunk the Kids Event
    private static final String shrunkPath = "attributes.events.honey-i-shrunk-the-kids.";
    public static double getShrunkScale(){
        return getD(shrunkPath+"scale-mult", -0.75);
    }
    public static double getShrunkMaxHP(){
        return getD(shrunkPath+"max-health", -2);
    }
    public static double getShrunkAttack(){
        return getD(shrunkPath+"attack-dmg", -0.25);
    }
    /*public static double getShrunkKnockResist(){
        return getD(shrunkPath+"knockback-resist-mult");
    }*/
    public static double getShrunkMovespeed(){
        return getD(shrunkPath+"movespeed-mult", 0.25);
    }
    public static double getShrunkSneakspeed(){
        return getD(shrunkPath+"sneakspeed-mult", 0.25);
    }
    public static double getShrunkWaterMove(){
        return getD(shrunkPath+"water-movespeed-mult", 0.25);
    }
    /*public static double getShrunkStepHeight(){
        return getD(shrunkPath+"step-height");
    }*/
    public static double getShrunkJumpStr(){
        return getD(shrunkPath+"jump-strength-mult", 0.25);
    }
    public static double getShrunkSafeFall(){
        return getD(shrunkPath+"safe-fall-dist", 1);
    }
    /*public static double getShrunkBlockInteract(){
        return getD(shrunkPath+"block-interact-range-mult", 0.25);
    }
    public static double getShrunkEntityInteract(){
        return getD(shrunkPath+"entity-interact-range-mult", 0.25);
    }*/

    // Zero Gravity Event
    private static final String zeroGPath = "attributes.events.zero-gravity.";
    public static double getZeroGMovespeed(){
        return getD(zeroGPath+"movespeed-mult", -0.05);
    }
    public static double getZeroGGravity(){
        return getD(zeroGPath+"gravity", 0.05);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Block Modifications //


    // Universal // Implementation not yet decided
    
    // Deep Dark Invasion Event // Pattern not yet implemented
    private static final String deepDarkPath = "block-modification.events.deep-dark-invasion.";
    public static int getDeepDarkRadius(){
        return getI(deepDarkPath+"radius", 100);
    }
    public static int getDeepDarkHeight(){
        return getI(deepDarkPath+"height", 400);
    }
    public static int getDeepDarkOffset(){
        return getI(deepDarkPath+"height-offset", 200);
    }
    
    // Gold Rush Event
    private static final String goldRushPath = "block-modification.events.gold-rush.";
    public static int getGoldRushRadius(){
        return getI(goldRushPath+"radius", 50);
    }
    public static int getGoldRushHeight(){
        return getI(goldRushPath+"height", 400);
    }
    public static int getGoldRushOffset(){
        return getI(goldRushPath+"height-offset", 200);
    }

    // IceIsNice Event
    private static final String icePath = "block-modification.events.ice-is-nice.";
    public static int getIceRadius(){
        return getI(icePath+"radius", 100);
    }
    public static int getIceHeight(){
        return getI(icePath+"height", 10);
    }
    public static int getIceHeightOffset(){
        return getI(icePath+"height-offset", 0);
    }
    
    // Nether Invasion Event
    private static final String netherPath = "block-modification.events.nether-invasion.";
    public static int getNetherRadius(){
        return getI(netherPath+"radius", 100);
    }
    public static int getNetherHeight(){
        return getI(netherPath+"height", 400);
    }
    public static int getNetherOffset(){
        return getI(netherPath+"height-offset", 200);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Drop Modifications // Left empty for now, unsure of what to do to modify these events 
    
    
    // Block Drop Shuffle Event
    
    // double or Nothing Event
    
    // Mob Drop Shuffle Event

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Effects //
    
    
    // Universal // Implementation not yet decided
    
    // Food Coma Event - Saturation
    private static final String foodComaSatPath = "effects.events.food-coma.saturation";
    public static int getFoodComaSaturationDuration(){
        return getI(foodComaSatPath+".duration", 6000);
    }
    public static int getFoodComaSaturationAmplifier(){
        return getI(foodComaSatPath+".amplifier", 0);
    }
    public static boolean getFoodComaSaturationAmbient(){
        return getB(foodComaSatPath+".ambient", false);
    }
    public static boolean getFoodComaSaturationParticles(){
        return getB(foodComaSatPath+".show-particles", false);
    }
    public static boolean getFoodComaSaturationIcon(){
        return getB(foodComaSatPath+".show-icon", true);
    }

    // Food Coma Event - Slowness
    private static final String foodComaSlowPath = "effects.events.food-coma.slowness";
    public static int getFoodComaSlownessDuration(){
        return getI(foodComaSlowPath+".duration", 6000);
    }
    public static int getFoodComaSlownessAmplifier(){
        return getI(foodComaSlowPath+".amplifier", 0);
    }
    public static boolean getFoodComaSlownessAmbient(){
        return getB(foodComaSlowPath+".ambient", false);
    }
    public static boolean getFoodComaSlownessParticles(){
        return getB(foodComaSlowPath+".show-particles", false);
    }
    public static boolean getFoodComaSlownessIcon(){
        return getB(foodComaSlowPath+".show-icon", true);
    }

    // Gotta Go Fast Event
    private static final String gottaGoPath = "effects.events.gotta-go-fast.";
    public static int getGottaGoFastDuration(){
        return getI(gottaGoPath+"duration", 6000);
    }
    public static int getGottaGoFastAmplifier(){
        return getI(gottaGoPath+"amplifier", 2);
    }
    public static boolean getGottaGoFastAmbient(){
        return getB(gottaGoPath+"ambient", false);
    }
    public static boolean getGottaGoFastParticles(){
        return getB(gottaGoPath+"show-particles", false);
    }
    public static boolean getGottaGoFastIcon(){
        return getB(gottaGoPath+"show-icon", true);
    }

    // Overmine Event
    private static final String overminePath = "effects.events.overmine.";
    public static int getOvermineDuration(){
        return getI(overminePath+"duration", 6000);
    }
    public static int getOvermineAmplifier(){
        return getI(overminePath+"amplifier", 1);
    }
    public static boolean getOvermineAmbient(){
        return getB(overminePath+"ambient", false);
    }
    public static boolean getOvermineParticles(){
        return getB(overminePath+"show-particles", false);
    }
    public static boolean getOvermineIcon(){
        return getB(overminePath+"show-icon", true);
    }
    
    // Second Wind Event
    private static final String secondWindPath = "effects.events.second-wind.";
    public static int getSecondWindDuration(){
        return getI(secondWindPath+"duration", 6000);
    }
    public static int getSecondWindAmplifier(){
        return getI(secondWindPath+"amplifier", 1);
    }
    public static boolean getSecondWindAmbient(){
        return getB(secondWindPath+"ambient", false);
    }
    public static boolean getSecondWindParticles(){
        return getB(secondWindPath+"show-particles", false);
    }
    public static boolean getSecondWindIcon(){
        return getB(secondWindPath+"show-icon", true);
    }
    
    // You're Too Slow Event
    private static final String tooSlowPath = "effects.events.youre-too-slow.";
    public static int getTooSlowDuration(){
        return getI(tooSlowPath+"duration", 6000);
    }
    public static int getTooSlowAmplifier(){
        return getI(tooSlowPath+"amplifier", 1);
    }
    public static boolean getTooSlowAmbient(){
        return getB(tooSlowPath+"ambient", false);
    }
    public static boolean getTooSlowParticles(){
        return getB(tooSlowPath+"show-particles", false);
    }
    public static boolean getTooSlowIcon(){
        return getB(tooSlowPath+"show-icon", true);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Gamerule //


    // Lifesteal-Only Event
    private static final String lifePath = "gamerule.events.lifesteal-only.";
    public static double getLifeStealLifesteal(){
        return getD(lifePath+"lifesteal", 0.5);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Inventory Adjustments //


    // Universal // Implementation not yet decided

    // Butterfingers Event
    private static final String bFPath = "inventory-adjustment.events.butterfingers.";
    public static boolean getButterFingersUseCont(){
        return getB(bFPath+"use-continuous", true);
    }
    public static int getButterFingersOrigin(){
        return getI(bFPath+"origin", 5);
    }
    public static int getButterFingersBound(){
        return getI(bFPath+"bound", 61);
    }
    public static boolean getButterfingersMainhand(){
        return getB(bFPath+"affects-mainhand", true);
    }
    public static boolean getButterfingersOffhand(){
        return getB(bFPath+"affects-offhand", true);
    }

    // Flight School Event // Left empty for now, unsure of what to do to modify these events

    // Inventory Swap Event // Left empty for now, unsure of what to do to modify these events

    // Spoiled Food Event // Left empty for now, unsure of what to do to modify these events

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Item Spawn //


    // Universal // Implementation not yet decided
    
    // Drop Party Event
    private static final String dropPath = "item-spawn.events.drop-party.";
    public static int getDropPartyItemCt(){
        return getI(dropPath+"item-count", 32);
    }
    public static int getDropPartyMaxSpawnRad(){
        return getI(dropPath+"max-spawn-radius", 20);
    }
    public static int getDropPartyMinSpawnRad(){
        return getI(dropPath+"min-spawn-radius", 1);
    }
    public static int getDropPartyMaxYRad(){
        return getI(dropPath+"max-y-radius", 10);
    }
    public static int getDropPartyMinYRad(){
        return getI(dropPath+"min-y-rad", 1);
    }
    public static int getDropPartyMaxSpawnAtt(){
        return getI(dropPath+"max-spawn-attempts", 20);
    }
    public static int getDropPartySpawnInterval(){
        return getI(dropPath+"spawn-interval", 60);
    }
    public static int getDropPartyWidthClearance(){
        return getI(dropPath+"width-clearance", 1);
    }
    public static int getDropPartyHeightClearance(){
        return getI(dropPath+"height-clearance", 1);
    }
    public static boolean getDropPartyCenterY(){
        return getB(dropPath+"centered-on-y", true);
    }
    public static int getDropPartyGroupSpace(){
        return getI(dropPath+"group-spacing", 3);
    }
    public static boolean getDropPartySurfOnlySpawn(){
        return getB(dropPath+"surface-only-spawning", false);
    }
    public static boolean getDropPartyAllowWaterSpawn(){
        return getB(dropPath+"allow-water-spawns", false);
    }
    public static boolean getDropPartyAllowLavaSpawn(){
        return getB(dropPath+"allow-lava-spawns", false);
    }
    public static boolean getDropPartyUseGroupSpawn(){
        return getB(dropPath+"use-group-spawning", false);
    }
    public static boolean getDropPartyUseContinuousSpawn(){
        return getB(dropPath+"use-continuous-spawning", false);
    }
    public static boolean getDropPartyUseRandItemTypes(){
        return getB(dropPath+"use-random-item-types", true);
    }
    
    // Feast Event
    private static final String feastPath = "item-spawn.events.feast.";
    public static int getFeastItemCt(){
        return getI(feastPath+"item-count", 32);
    }
    public static int getFeastMaxSpawnRad(){
        return getI(feastPath+"max-spawn-radius", 20);
    }
    public static int getFeastMinSpawnRad(){
        return getI(feastPath+"min-spawn-radius", 1);
    }
    public static int getFeastMaxYRad(){
        return getI(feastPath+"max-y-radius", 10);
    }
    public static int getFeastMinYRad(){
        return getI(feastPath+"min-y-rad", 1);
    }
    public static int getFeastMaxSpawnAtt(){
        return getI(feastPath+"max-spawn-attempts", 20);
    }
    public static int getFeastSpawnInterval(){
        return getI(feastPath+"spawn-interval", 60);
    }
    public static int getFeastWidthClearance(){
        return getI(feastPath+"width-clearance", 1);
    }
    public static int getFeastHeightClearance(){
        return getI(feastPath+"height-clearance", 1);
    }
    public static boolean getFeastCenterY(){
        return getB(feastPath+"centered-on-y", true);
    }
    public static int getFeastGroupSpace(){
        return getI(feastPath+"group-spacing", 2);
    }
    public static boolean getFeastSurfOnlySpawn(){
        return getB(feastPath+"surface-only-spawning", false);
    }
    public static boolean getFeastAllowWaterSpawn(){
        return getB(feastPath+"allow-water-spawns", false);
    }
    public static boolean getFeastAllowLavaSpawn(){
        return getB(feastPath+"allow-lava-spawns", false);
    }
    public static boolean getFeastUseGroupSpawn(){
        return getB(feastPath+"use-group-spawning", false);
    }
    public static boolean getFeastUseContinuousSpawn(){
        return getB(feastPath+"use-continuous-spawning", false);
    }
    public static boolean getFeastUseRandItemTypes(){
        return getB(feastPath+"use-random-item-types", true);
    }
    
    // Ore Drop Party Event
    private static final String oreDropPath = "item-spawn.events.ore-drop-party.";
    public static int getOreDropItemCt(){
        return getI(oreDropPath+"item-count", 64);
    }
    public static int getOreDropMaxSpawnRad(){
        return getI(oreDropPath+"max-spawn-radius", 20);
    }
    public static int getOreDropMinSpawnRad(){
        return getI(oreDropPath+"min-spawn-radius", 1);
    }
    public static int getOreDropMaxYRad(){
        return getI(oreDropPath+"max-y-radius", 10);
    }
    public static int getOreDropMinYRad(){
        return getI(oreDropPath+"min-y-rad", 1);
    }
    public static int getOreDropMaxSpawnAtt(){
        return getI(oreDropPath+"max-spawn-attempts", 60);
    }
    public static int getOreDropSpawnInterval(){
        return getI(oreDropPath+"spawn-interval", 60);
    }
    public static int getOreDropWidthClearance(){
        return getI(oreDropPath+"width-clearance", 1);
    }
    public static int getOreDropHeightClearance(){
        return getI(oreDropPath+"height-clearance", 1);
    }
    public static boolean getOreDropCenterY(){
        return getB(oreDropPath+"centered-on-y", true);
    }
    public static int getOreDropGroupSpace(){
        return getI(oreDropPath+"group-spacing", 2);
    }
    public static boolean getOreDropSurfOnlySpawn(){
        return getB(oreDropPath+"surface-only-spawning", false);
    }
    public static boolean getOreDropAllowWaterSpawn(){
        return getB(oreDropPath+"allow-water-spawns", false);
    }
    public static boolean getOreDropAllowLavaSpawn(){
        return getB(oreDropPath+"allow-lava-spawns", false);
    }
    public static boolean getOreDropUseGroupSpawn(){
        return getB(oreDropPath+"use-group-spawning", false);
    }
    public static boolean getOreDropUseContinuousSpawn(){
        return getB(oreDropPath+"use-continuous-spawning", false);
    }
    public static boolean getOreDropUseRandItemTypes(){
        return getB(oreDropPath+"use-random-item-types", true);
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Mob Spawn //

    
    // Universal // Implementation not yet decided
    
    // Chicken Flock Event
    private static final String chickPath = "mob-spawn.events.chicken-flock.";
    public static int getChickenFMobCt(){
        return getI(chickPath+"mob-count", 5);
    }
    public static int getChickenFMaxSpawnRad(){
        return getI(chickPath+"max-spawn-radius", 30);
    }
    public static int getChickenFMinSpawnRad(){
        return getI(chickPath+"min-spawn-radius", 3);
    }
    public static int getChickenFMaxYRad(){
        return getI(chickPath+"max-y-radius", 20);
    }
    public static int getChickenFMinYRad(){
        return getI(chickPath+"min-y-rad", 3);
    }
    public static int getChickenFMaxSpawnAtt(){
        return getI(chickPath+"max-spawn-attempts", 20);
    }
    public static int getChickenFSpawnInterval(){
        return getI(chickPath+"spawn-interval", 60);
    }
    public static int getChickenFWidthClearance(){
        return getI(chickPath+"width-clearance", 1);
    }
    public static int getChickenFHeightClearance(){
        return getI(chickPath+"height-clearance", 1);
    }
    public static int getChickenFGroupSpace(){
        return getI(chickPath+"group-spacing", 2);
    }
    public static boolean getChickenFSurfOnlySpawn(){
        return getB(chickPath+"surface-only-spawning", false);
    }
    public static boolean getChickenFAllowWaterSpawn(){
        return getB(chickPath+"allow-water-spawns", false);
    }
    public static boolean getChickenFAllowLavaSpawn(){
        return getB(chickPath+"allow-lava-spawns", false);
    }
    public static boolean getChickenFUseGroupSpawn(){
        return getB(chickPath+"use-group-spawning", true);
    }
    public static boolean getChickenFUseContinuousSpawn(){
        return getB(chickPath+"use-continuous-spawning", false);
    }
    
    // Cow Herd Event
    private static final String cowPath = "mob-spawn.events.cow-herd.";
    public static int getCowHerdMobCt(){
        return getI(cowPath+"mob-count", 5);
    }
    public static int getCowHerdMaxSpawnRad(){
        return getI(cowPath+"max-spawn-radius", 30);
    }
    public static int getCowHerdMinSpawnRad(){
        return getI(cowPath+"min-spawn-radius", 3);
    }
    public static int getCowHerdMaxYRad(){
        return getI(cowPath+"max-y-radius", 20);
    }
    public static int getCowHerdMinYRad(){
        return getI(cowPath+"min-y-rad", 3);
    }
    public static int getCowHerdMaxSpawnAtt(){
        return getI(cowPath+"max-spawn-attempts", 20);
    }
    public static int getCowHerdSpawnInterval(){
        return getI(cowPath+"spawn-interval", 60);
    }
    public static int getCowHerdWidthClearance(){
        return getI(cowPath+"width-clearance", 1);
    }
    public static int getCowHerdHeightClearance(){
        return getI(cowPath+"height-clearance", 2);
    }
    public static int getCowHerdGroupSpace(){
        return getI(cowPath+"group-spacing", 2);
    }
    public static boolean getCowHerdSurfOnlySpawn(){
        return getB(cowPath+"surface-only-spawning", false);
    }
    public static boolean getCowHerdAllowWaterSpawn(){
        return getB(cowPath+"allow-water-spawns", false);
    }
    public static boolean getCowHerdAllowLavaSpawn(){
        return getB(cowPath+"allow-lava-spawns", false);
    }
    public static boolean getCowHerdUseGroupSpawn(){
        return getB(cowPath+"use-group-spawning", true);
    }
    public static boolean getCowHerdUseContinuousSpawn(){
        return getB(cowPath+"use-continuous-spawning", false);
    }
    
    // Drop Creeper Event
    private static final String creepPath = "mob-spawn.events.drop-creeper.";
    public static int getDropCreepMobCt(){
        return getI(creepPath+"mob-count", 1);
    }
    public static int getDropCreepMaxSpawnRad(){
        return getI(creepPath+"max-spawn-radius", 10);
    }
    public static int getDropCreepMinSpawnRad(){
        return getI(creepPath+"min-spawn-radius", 3);
    }
    public static int getDropCreepMaxYRad(){
        return getI(creepPath+"max-y-radius", 30);
    }
    public static int getDropCreepMinYRad(){
        return getI(creepPath+"min-y-rad", 3);
    }
    public static int getDropCreepMaxSpawnAtt(){
        return getI(creepPath+"max-spawn-attempts", 20);
    }
    public static int getDropCreepSpawnInterval(){
        return getI(creepPath+"spawn-interval", 60);
    }
    public static int getDropCreepWidthClearance(){
        return getI(creepPath+"width-clearance", 1);
    }
    public static int getDropCreepHeightClearance(){
        return getI(creepPath+"height-clearance", 2);
    }
    public static int getDropCreepGroupSpace(){
        return getI(creepPath+"group-spacing", 2);
    }
    public static boolean getDropCreepSurfOnlySpawn(){
        return getB(creepPath+"surface-only-spawning", false);
    }
    public static boolean getDropCreepAllowWaterSpawn(){
        return getB(creepPath+"allow-water-spawns", false);
    }
    public static boolean getDropCreepAllowLavaSpawn(){
        return getB(creepPath+"allow-lava-spawns", false);
    }
    public static boolean getDropCreepUseGroupSpawn(){
        return getB(creepPath+"use-group-spawning", false);
    }
    public static boolean getDropCreepUseContinuousSpawn(){
        return getB(creepPath+"use-continuous-spawning", false);
    }
    
    // End Raid Event
    private static final String endPath = "mob-spawn.events.end-raid.";
    public static int getEndRaidMobCt(){
        return getI(endPath+"mob-count", 15);
    }
    public static int getEndRaidMaxSpawnRad(){
        return getI(endPath+"max-spawn-radius", 30);
    }
    public static int getEndRaidMinSpawnRad(){
        return getI(endPath+"min-spawn-radius", 5);
    }
    public static int getEndRaidMaxYRad(){
        return getI(endPath+"max-y-radius", 20);
    }
    public static int getEndRaidMinYRad(){
        return getI(endPath+"min-y-rad", 5);
    }
    public static int getEndRaidMaxSpawnAtt(){
        return getI(endPath+"max-spawn-attempts", 20);
    }
    public static int getEndRaidSpawnInterval(){
        return getI(endPath+"spawn-interval", 60);
    }
    public static int getEndRaidWidthClearance(){
        return getI(endPath+"width-clearance", 1);
    }
    public static int getEndRaidHeightClearance(){
        return getI(endPath+"height-clearance", 2);
    }
    public static int getEndRaidGroupSpace(){
        return getI(endPath+"group-spacing", 2);
    }
    public static boolean getEndRaidSurfOnlySpawn(){
        return getB(endPath+"surface-only-spawning", false);
    }
    public static boolean getEndRaidAllowWaterSpawn(){
        return getB(endPath+"allow-water-spawns", false);
    }
    public static boolean getEndRaidAllowLavaSpawn(){
        return getB(endPath+"allow-lava-spawns", false);
    }
    public static boolean getEndRaidUseGroupSpawn(){
        return getB(endPath+"use-group-spawning", false);
    }
    public static boolean getEndRaidUseContinuousSpawn(){
        return getB(endPath+"use-continuous-spawning", false);
    }
    public static boolean getEndRaidUseRandMobTypes(){
        return getB(endPath+"use-random-mob-types", true);
    }
    
    // Nether Raid Event
    private static final String netherRaid = "mob-spawn.events.nether-raid.";
    public static int getNetherRaidMobCt(){
        return getI(netherRaid+"mob-count", 15);
    }
    public static int getNetherRaidMaxSpawnRad(){
        return getI(netherRaid+"max-spawn-radius", 30);
    }
    public static int getNetherRaidMinSpawnRad(){
        return getI(netherRaid+"min-spawn-radius", 5);
    }
    public static int getNetherRaidMaxYRad(){
        return getI(netherRaid+"max-y-radius", 20);
    }
    public static int getNetherRaidMinYRad(){
        return getI(netherRaid+"min-y-rad", 5);
    }
    public static int getNetherRaidMaxSpawnAtt(){
        return getI(netherRaid+"max-spawn-attempts", 20);
    }
    public static int getNetherRaidSpawnInterval(){
        return getI(netherRaid+"spawn-interval", 60);
    }
    public static int getNetherRaidWidthClearance(){
        return getI(netherRaid+"width-clearance", 1);
    }
    public static int getNetherRaidHeightClearance(){
        return getI(netherRaid+"height-clearance", 2);
    }
    public static int getNetherRaidGroupSpace(){
        return getI(netherRaid+"group-spacing", 2);
    }
    public static boolean getNetherRaidSurfOnlySpawn(){
        return getB(netherRaid+"surface-only-spawning", false);
    }
    public static boolean getNetherRaidAllowWaterSpawn(){
        return getB(netherRaid+"allow-water-spawns", false);
    }
    public static boolean getNetherRaidAllowLavaSpawn(){
        return getB(netherRaid+"allow-lava-spawns", false);
    }
    public static boolean getNetherRaidUseGroupSpawn(){
        return getB(netherRaid+"use-group-spawning", false);
    }
    public static boolean getNetherRaidUseContinuousSpawn(){
        return getB(netherRaid+"use-continuous-spawning", false);
    }
    public static boolean getNetherRaidUseRandMobTypes(){
        return getB(netherRaid+"use-random-mob-types", true);
    }
    
    // Random Mob Spawn Event
    private static final String randMobPath = "mob-spawn.events.random-mob-spawn.";
    public static int getRandMobSpawnMobCt(){
        return getI(randMobPath+"mob-count", 10);
    }
    public static int getRandMobSpawnMaxSpawnRad(){
        return getI(randMobPath+"max-spawn-radius", 30);
    }
    public static int getRandMobSpawnMinSpawnRad(){
        return getI(randMobPath+"min-spawn-radius", 3);
    }
    public static int getRandMobSpawnMaxYRad(){
        return getI(randMobPath+"max-y-radius", 20);
    }
    public static int getRandMobSpawnMinYRad(){
        return getI(randMobPath+"min-y-rad", 3);
    }
    public static int getRandMobSpawnMaxSpawnAtt(){
        return getI(randMobPath+"max-spawn-attempts", 20);
    }
    public static int getRandMobSpawnSpawnInterval(){
        return getI(randMobPath+"spawn-interval", 60);
    }
    public static int getRandMobSpawnWidthClearance(){
        return getI(randMobPath+"width-clearance", 1);
    }
    public static int getRandMobSpawnHeightClearance(){
        return getI(randMobPath+"height-clearance", 2);
    }
    public static int getRandMobSpawnGroupSpace(){
        return getI(randMobPath+"group-spacing", 2);
    }
    public static boolean getRandMobSpawnSurfOnlySpawn(){
        return getB(randMobPath+"surface-only-spawning", false);
    }
    public static boolean getRandMobSpawnAllowWaterSpawn(){
        return getB(randMobPath+"allow-water-spawns", false);
    }
    public static boolean getRandMobSpawnAllowLavaSpawn(){
        return getB(randMobPath+"allow-lava-spawns", false);
    }
    public static boolean getRandMobSpawnUseGroupSpawn(){
        return getB(randMobPath+"use-group-spawning", false);
    }
    public static boolean getRandMobSpawnUseContinuousSpawn(){
        return getB(randMobPath+"use-continuous-spawning", false);
    }
    public static boolean getRandMobSpawnUseRandMobTypes(){
        return getB(randMobPath+"use-random-mob-types", true);
    }
    
    // Wolf Pack Event
    private static final String wolfPath = "mob-spawn.events.wolf-pack.";
    public static int getWolfPMobCt(){
        return getI(wolfPath+"mob-count", 5);
    }
    public static int getWolfPMaxSpawnRad(){
        return getI(wolfPath+"max-spawn-radius", 30);
    }
    public static int getWolfPMinSpawnRad(){
        return getI(wolfPath+"min-spawn-radius", 3);
    }
    public static int getWolfPMaxYRad(){
        return getI(wolfPath+"max-y-radius", 20);
    }
    public static int getWolfPMinYRad(){
        return getI(wolfPath+"min-y-rad", 3);
    }
    public static int getWolfPMaxSpawnAtt(){
        return getI(wolfPath+"max-spawn-attempts", 20);
    }
    public static int getWolfPSpawnInterval(){
        return getI(wolfPath+"spawn-interval", 60);
    }
    public static int getWolfPWidthClearance(){
        return getI(wolfPath+"width-clearance", 1);
    }
    public static int getWolfPHeightClearance(){
        return getI(wolfPath+"height-clearance", 1);
    }
    public static int getWolfPGroupSpace(){
        return getI(wolfPath+"group-spacing", 2);
    }
    public static boolean getWolfPSurfOnlySpawn(){
        return getB(wolfPath+"surface-only-spawning", false);
    }
    public static boolean getWolfPAllowWaterSpawn(){
        return getB(wolfPath+"allow-water-spawns", false);
    }
    public static boolean getWolfPAllowLavaSpawn(){
        return getB(wolfPath+"allow-lava-spawns", false);
    }
    public static boolean getWolfPUseGroupSpawn(){
        return getB(wolfPath+"use-group-spawning", true);
    }
    public static boolean getWolfPUseContinuousSpawn(){
        return getB(wolfPath+"use-continuous-spawning", false);
    }
    
    // Zombie Horde Event
    private static final String zombHordePath = "mob-spawn.events.zombie-horde.";
    public static int getZombHordeMobCt(){
        return getI(zombHordePath+"mob-count", 10);
    }
    public static int getZombHordeMaxSpawnRad(){
        return getI(zombHordePath+"max-spawn-radius", 30);
    }
    public static int getZombHordeMinSpawnRad(){
        return getI(zombHordePath+"min-spawn-radius", 3);
    }
    public static int getZombHordeMaxYRad(){
        return getI(zombHordePath+"max-y-radius", 20);
    }
    public static int getZombHordeMinYRad(){
        return getI(zombHordePath+"min-y-rad", 3);
    }
    public static int getZombHordeMaxSpawnAtt(){
        return getI(zombHordePath+"max-spawn-attempts", 20);
    }
    public static int getZombHordeSpawnInterval(){
        return getI(zombHordePath+"spawn-interval", 60);
    }
    public static int getZombHordeWidthClearance(){
        return getI(zombHordePath+"width-clearance", 1);
    }
    public static int getZombHordeHeightClearance(){
        return getI(zombHordePath+"height-clearance", 2);
    }
    public static int getZombHordeGroupSpace(){
        return getI(zombHordePath+"group-spacing", 2);
    }
    public static boolean getZombHordeSurfOnlySpawn(){
        return getB(zombHordePath+"surface-only-spawning", false);
    }
    public static boolean getZombHordeAllowWaterSpawn(){
        return getB(zombHordePath+"allow-water-spawns", false);
    }
    public static boolean getZombHordeAllowLavaSpawn(){
        return getB(zombHordePath+"allow-lava-spawns", false);
    }
    public static boolean getZombHordeUseGroupSpawn(){
        return getB(zombHordePath+"use-group-spawning", false);
    }
    public static boolean getZombHordeUseContinuousSpawn(){
        return getB(zombHordePath+"use-continuous-spawning", false);
    }
    
    // Zombie Invasion Event
    private static final String zombInvPath = "mob-spawn.events.zombie-invasion.";
    public static int getZombInvMobCt(){
        return getI(zombInvPath+"mob-count", 1);
    }
    public static int getZombInvMaxSpawnRad(){
        return getI(zombInvPath+"max-spawn-radius", 30);
    }
    public static int getZombInvMinSpawnRad(){
        return getI(zombInvPath+"min-spawn-radius", 3);
    }
    public static int getZombInvMaxYRad(){
        return getI(zombInvPath+"max-y-radius", 20);
    }
    public static int getZombInvMinYRad(){
        return getI(zombInvPath+"min-y-rad", 3);
    }
    public static int getZombInvMaxSpawnAtt(){
        return getI(zombInvPath+"max-spawn-attempts", 20);
    }
    public static int getZombInvSpawnInterval(){
        return getI(zombInvPath+"spawn-interval", 20);
    }
    public static int getZombInvWidthClearance(){
        return getI(zombInvPath+"width-clearance", 1);
    }
    public static int getZombInvHeightClearance(){
        return getI(zombInvPath+"height-clearance", 2);
    }
    public static int getZombInvGroupSpace(){
        return getI(zombInvPath+"group-spacing", 2);
    }
    public static boolean getZombInvSurfOnlySpawn(){
        return getB(zombInvPath+"surface-only-spawning", false);
    }
    public static boolean getZombInvAllowWaterSpawn(){
        return getB(zombInvPath+"allow-water-spawns", false);
    }
    public static boolean getZombInvAllowLavaSpawn(){
        return getB(zombInvPath+"allow-lava-spawns", false);
    }
    public static boolean getZombInvUseGroupSpawn(){
        return getB(zombInvPath+"use-group-spawning", false);
    }
    public static boolean getZombInvUseContinuousSpawn(){
        return getB(zombInvPath+"use-continuous-spawning", true);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////














}

