package dev.strwbry.eventhorizon.events.testconfig;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *  AdvConfigTest is a utility class that loads and manages events from a configuration file.
 *  It provides static methods to access various event types defined in the configuration.
 *  The events are loaded into a static map for easy retrieval.
 * <p>
 *  This class is designed to be used without instantiation, providing a simple interface
 *  to access event configurations.
 *  This class is also unused as of now, and serves as a potential replacement for AdvConfig.
 */
public class AdvConfigTest {

    /**
     *  events is a static map that holds all events loaded from the configuration file.
     *  The key is the event name, and the value is the Event object.
     */
    private static final Map<String, Event> events = new HashMap<>();
    private static FileConfiguration config;

    /*
     *  Static block to load events when the class is loaded.
     *  This ensures that the events are available for use without needing to call a method first.
     */
    static {
        loadEvents();
    }


    /**
     *  Loads the events from the configuration file.
     *  This method is called automatically in the static block when the class is loaded.
     */
    private static void loadEvents() {
        // loads config file
        config = YamlConfiguration.loadConfiguration(new File("adv-config-test.yml"));

        // loads effect events
        loadAttributeEvents();
        loadBlockModificationEvents();
        loadDropModificationEvents();
        loadEffectEvents();
        LoadGameruleEvents();
        loadInventoryAdjustmentEvents();
        loadItemSpawnEvents();
        loadMobSpawnEvents();
    }

    // Generic getter method for any event
    @SuppressWarnings("unchecked")
    public static <T extends Event> T getEvent(String name, Class<T> type) {
        Event event = events.get(name);
        if (event != null && type.isInstance(event)) {
            return (T) event;
        }
        return null;
    }

    /*
        This applies to ALL load<event-type>Events() methods.
        These functions load events from the adv-config.yml file.
        Each event is stored in the events map with its name as the key.
        If the respective section is missing, the method returns early with no error.
     */

    private static void loadAttributeEvents() {
        ConfigurationSection attributesSection = config.getConfigurationSection("attributes.events");
        if (attributesSection == null) return;

        for (String eventName : attributesSection.getKeys(false)) {
            String path = "attributes.events." + eventName + ".";

            AttributeEvent event = new AttributeEvent(
                    eventName,
                    getOptionalDouble(path + "scale-mult"),
                    getOptionalDouble(path + "max-health"),
                    getOptionalDouble(path + "attack-dmg"),
                    getOptionalDouble(path + "knockback-resist-mult"),
                    getOptionalDouble(path + "movespeed-mult"),
                    getOptionalDouble(path + "sneakspeed-mult"),
                    getOptionalDouble(path + "water-movespeed-mult"),
                    getOptionalDouble(path + "stepHeight"),
                    getOptionalDouble(path + "jump-strength-mult"),
                    getOptionalDouble(path + "safe-fall-dist"),
                    getOptionalDouble(path + "block-interact-range-mult"),
                    getOptionalDouble(path + "entity-interact-range-mult"),
                    getOptionalDouble(path + "gravity")
            );

            events.put(eventName, event);
        }
    }

    private static void loadBlockModificationEvents() {
        ConfigurationSection blockModSection = config.getConfigurationSection("block-modification.events");
        if (blockModSection == null) return;

        for (String eventName : blockModSection.getKeys(false)) {
            String path = "block-modification.events." + eventName + ".";

            BlockModificationEvent event = new BlockModificationEvent(
                    eventName,
                    getOptionalInt(path + "radius"),
                    getOptionalInt(path + "height"),
                    getOptionalInt(path + "height-offset")
            );

            events.put(eventName, event);
        }
    }

    private static void loadDropModificationEvents() {
        ConfigurationSection dropModSection = config.getConfigurationSection("drop-modification.events");
        if (dropModSection == null) return;

        for (String eventName : dropModSection.getKeys(false)) {
            String path = "drop-modification.events." + eventName + ".";

            DropModificationEvent event = new DropModificationEvent(
                    eventName
                    // more parameters? (TBD)
            );

            events.put(eventName, event);
        }
    }

    private static void loadEffectEvents() {
        ConfigurationSection effectsSection = config.getConfigurationSection("effects.events");
        if (effectsSection == null) return;

        for (String eventName : effectsSection.getKeys(false)) {
            ConfigurationSection eventSection = effectsSection.getConfigurationSection(eventName);
            if (eventSection == null) continue;

            Map<String, EffectEvent.PotionEffectData> potionEffects = new HashMap<>();

            // Iterate through each potion type under this event
            for (String potionType : eventSection.getKeys(false)) {
                String path = "effects.events." + eventName + "." + potionType + ".";

                EffectEvent.PotionEffectData effectData = new EffectEvent.PotionEffectData(
                        getOptionalInt(path + "duration"),
                        getOptionalDouble(path + "amplifier"),
                        getOptionalBoolean(path + "ambient"),
                        getOptionalBoolean(path + "show-particles"),
                        getOptionalBoolean(path + "show-icon")
                );

                potionEffects.put(potionType, effectData);
            }

            EffectEvent event = new EffectEvent(eventName, potionEffects);
            events.put(eventName, event);
        }
    }

    private static void LoadGameruleEvents() {
        ConfigurationSection gameruleSection = config.getConfigurationSection("gamerule.events");
        if (gameruleSection == null) return;

        for (String eventName : gameruleSection.getKeys(false)) {
            String path = "gamerule.events." + eventName + ".";

            GameruleEvent event = new GameruleEvent(
                    eventName
                    // more parameters? (TBD)
            );

            events.put(eventName, event);
        }
    }

    private static void loadInventoryAdjustmentEvents() {
        ConfigurationSection inventorySection = config.getConfigurationSection("inventory-adjustment.events");
        if (inventorySection == null) return;

        for (String eventName : inventorySection.getKeys(false)) {
            String path = "inventory-adjustment.events." + eventName + ".";

            InventoryAdjustmentEvent event = new InventoryAdjustmentEvent(
                    eventName,
                    getOptionalBoolean(path + "use-continuous"),
                    getOptionalInt(path + "origin"),
                    getOptionalInt(path + "bound"),
                    getOptionalBoolean(path + "affects-mainhand"),
                    getOptionalBoolean(path + "affects-offhand")
            );

            events.put(eventName, event);
        }
    }


    private static void loadItemSpawnEvents() {
        ConfigurationSection itemSpawnSection = config.getConfigurationSection("item-spawn.events");
        if (itemSpawnSection == null) return;

        for (String eventName : itemSpawnSection.getKeys(false)) {
            String path = "item-spawn.events." + eventName + ".";

            ItemSpawnEvent event = new ItemSpawnEvent(
                    eventName,
                    getOptionalInt(path + "item-count"),
                    getOptionalInt(path + "max-spawn-radius"),
                    getOptionalInt(path + "min-spawn-radius"),
                    getOptionalInt(path + "max-y-radius"),
                    getOptionalInt(path + "min-y-radius"),
                    getOptionalInt(path + "max-spawn-attempts"),
                    getOptionalInt(path + "spawn-interval"),
                    getOptionalInt(path + "width-clearance"),
                    getOptionalInt(path + "height-clearance"),
                    getOptionalBoolean(path + "centered-on-y"),
                    getOptionalInt(path + "group-spacing"),
                    getOptionalBoolean(path + "surface-only-spawning"),
                    getOptionalBoolean(path + "allow-water-spawns"),
                    getOptionalBoolean(path + "allow-lava-spawns"),
                    getOptionalBoolean(path + "use-group-spawning"),
                    getOptionalBoolean(path + "use-continuous-spawning"),
                    getOptionalBoolean(path + "use-random-item-types")
            );

            events.put(eventName, event);
        }
    }

    private static void loadMobSpawnEvents() {
        ConfigurationSection mobSpawnSection = config.getConfigurationSection("mob-spawn.events");
        if (mobSpawnSection == null) return;

        for (String eventName : mobSpawnSection.getKeys(false)) {
            String path = "mob-spawn.events." + eventName + ".";

            MobSpawnEvent event = new MobSpawnEvent(
                    eventName,
                    getOptionalInt(path + "mob-count"),
                    getOptionalInt(path + "max-spawn-radius"),
                    getOptionalInt(path + "min-spawn-radius"),
                    getOptionalInt(path + "max-y-radius"),
                    getOptionalInt(path + "min-y-radius"),
                    getOptionalInt(path + "max-spawn-attempts"),
                    getOptionalInt(path + "spawn-interval"),
                    getOptionalInt(path + "width-clearance"),
                    getOptionalInt(path + "height-clearance"),
                    getOptionalBoolean(path + "centered-on-y"),
                    getOptionalInt(path + "group-spacing"),
                    getOptionalBoolean(path + "surface-only-spawning"),
                    getOptionalBoolean(path + "allow-water-spawns"),
                    getOptionalBoolean(path + "allow-lava-spawns"),
                    getOptionalBoolean(path + "use-group-spawning"),
                    getOptionalBoolean(path + "use-continuous-spawning"),
                    getOptionalBoolean(path + "use-random-mob-types")
            );

            events.put(eventName, event);
        }
    }



    /**
     *  Helper methods to get optional config values. <p>
     *  If the config path exists, it returns either the specified config value, or the default
     *  If the path does not exist, i.e. that event does not specify that config value, it returns null
     *  @return Integer value from config, or null if not present
     */

    private static Integer getOptionalInt(String path) {
        return config.contains(path) ? config.getInt(path) : null;
    }

    /**
     *  @see #getOptionalInt(String)
     *  @return Double value from config, or null if not present
     */
    private static Double getOptionalDouble(String path) {
        return config.contains(path) ? config.getDouble(path) : null;
    }

    /**
     *  @see #getOptionalInt(String)
     *  @return Boolean value from config, or null if not present
     */
    private static Boolean getOptionalBoolean(String path) {
        return config.contains(path) ? config.getBoolean(path) : null;
    }

/*
    private static String getOptionalString(String path) {
        return config.contains(path) ? config.getString(path) : null;
    }
*/

    /*
        Specific getters for each individual Event
        These allow you to obtain any event's variables through this call
        Usage example:

        BlockModificationEvent iceEvent = AdvConfigTest.getIceEvent();
        Integer radius = iceEvent.radius();
        Integer height = iceEvent.height();

        The Event getters are static, so they do not require an instance of AdvConfigTest to be called
        Usage example:

        AdvConfigTest.getIceEvent().radius();
        AdvConfigTest.getIceEvent().height();
     */

    /**
     * @return The AttributeEvent for the Growth Spurt event. <p>
     * Usage shown in AttributeEvent.
     * @see AttributeEvent
     */
    public static AttributeEvent getGrowthEvent(){
        return (AttributeEvent) events.get("growth-spurt");
    }

    /**
     * @return The AttributeEvent for the Fasting event. <p>
     * Usage shown in AttributeEvent.
     * @see AttributeEvent
     */
    public static AttributeEvent getFastingEvent(){
        return (AttributeEvent) events.get("fasting");
    }
    /**
     * @return The AttributeEvent for the Half a Heart event. <p>
     * Usage shown in AttributeEvent.
     * @see AttributeEvent
     */
    public static AttributeEvent getHalfAHeartEvent(){
        return (AttributeEvent) events.get("half-a-heart");
    }

    /**
     * @return The AttributeEvent for the Honey I Shrunk the Kids event. <p>
     * Usage shown in AttributeEvent.
     * @see AttributeEvent
     */
    public static AttributeEvent getShrunkEvent(){
        return (AttributeEvent) events.get("honey-i-shrunk-the-kids");
    }
    /**
     * @return The AttributeEvent for the Zero Gravity event. <p>
     * Usage shown in AttributeEvent.
     * @see AttributeEvent
     */
    public static AttributeEvent getZeroGEvent(){
        return (AttributeEvent) events.get("zero-gravity");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return The BlockModificationEvent for the Deep Dark Invasion event. <p>
     * Usage shown in BlockModificationEvent.
     * @see BlockModificationEvent
     */
    public static BlockModificationEvent getDeepDarkEvent(){
        return (BlockModificationEvent) events.get("deep-dark-invasion");
    }

    /**
     * @return The BlockModificationEvent for the Gold Rush event. <p>
     * Usage shown in BlockModificationEvent.
     * @see BlockModificationEvent
     */
    public static BlockModificationEvent getGoldRushEvent(){
        return (BlockModificationEvent) events.get("gold-rush");
    }

    /**
     * @return The BlockModificationEvent for the Ice Is Nice event. <p>
     * Usage shown in BlockModificationEvent.
     * @see BlockModificationEvent
     */
    public static BlockModificationEvent getIceEvent(){
        return (BlockModificationEvent) events.get("ice-is-nice");
    }

    /**
     * @return The BlockModificationEvent for the Nether Invasion event. <p>
     * Usage shown in BlockModificationEvent.
     * @see BlockModificationEvent
     */
    public static BlockModificationEvent getNetherEvent(){
        return (BlockModificationEvent) events.get("nether-invasion");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
        Drop modification events will go here when implemented into config
    */

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return The EffectEvent for the Food Coma event. <p>
     * Usage shown in EffectEvent.
     * @see EffectEvent
     */
    public static EffectEvent getFoodComaEvent(){
        return (EffectEvent) events.get("food-coma");
    }

    /**
     * @return The EffectEvent for the Gotta Go Fast event. <p>
     * Usage shown in EffectEvent.
     * @see EffectEvent
     */
    public static EffectEvent getGottaGoFastEvent(){
        return (EffectEvent) events.get("gotta-go-fast");
    }

    /**
     * @return The EffectEvent for the Overmine event. <p>
     * Usage shown in EffectEvent.
     * @see EffectEvent
     */
    public static EffectEvent getOvermineEvent(){
        return (EffectEvent) events.get("overmine");
    }

    /**
     * @return The EffectEvent for the Second Wind event. <p>
     * Usage shown in EffectEvent.
     * @see EffectEvent
     */
    public static EffectEvent getSecondWindEvent(){
        return (EffectEvent) events.get("second-wind");
    }

    /**
     * @return The EffectEvent for the You're Too Slow event. <p>
     * Usage shown in EffectEvent.
     * @see EffectEvent
     */
    public static EffectEvent getTooSlowEvent() {
        return (EffectEvent) events.get("youre-too-slow");
    }



    /**
     * @return The GameruleEvent for the Lifesteal Only event. <p>
     * Usage shown in GameruleEvent.
     * @see GameruleEvent
     */
    public static GameruleEvent getLifeStealEvent() {
        return (GameruleEvent) events.get("lifesteal-only");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return The InventoryAdjustmentEvent for the ButterFingers event. <p>
     * Usage shown in InventoryAdjustmentEvent.
     * @see InventoryAdjustmentEvent
     */
    public static InventoryAdjustmentEvent getButterFingersEvent() {
        return (InventoryAdjustmentEvent) events.get("butterfingers");
    }

    // Flight School Event // Left empty for now, unsure of what to do to modify these events

    // Inventory Swap Event // Left empty for now, unsure of what to do to modify these events

    // Spoiled Food Event // Left empty for now, unsure of what to do to modify these events

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return The ItemSpawnEvent for the Drop Party event. <p>
     * Usage shown in ItemSpawnEvent.
     * @see ItemSpawnEvent
     */
    public static ItemSpawnEvent getDropPartyEvent() {
        return (ItemSpawnEvent) events.get("drop-party");
    }

    /**
     * @return The ItemSpawnEvent for the Feast event. <p>
     * Usage shown in ItemSpawnEvent.
     * @see ItemSpawnEvent
     */
    public static ItemSpawnEvent getFeastEvent() {
        return (ItemSpawnEvent) events.get("feast");
    }

    /**
     * @return The ItemSpawnEvent for the Ore Drop Party event. <p>
     * Usage shown in ItemSpawnEvent.
     * @see ItemSpawnEvent
     */
    public static ItemSpawnEvent getOreDropEvent() {
        return (ItemSpawnEvent) events.get("ore-drop-party");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return The MobSpawnEvent for the Chicken Flock event. <p>
     * Usage shown in MobSpawnEvent.
     * @see MobSpawnEvent
     */
    public static MobSpawnEvent getChickenFEvent() {
        return (MobSpawnEvent) events.get("chicken-flock");
    }

    /**
     * @return The MobSpawnEvent for the Cow Herd event. <p>
     * Usage shown in MobSpawnEvent.
     * @see MobSpawnEvent
     */
    public static MobSpawnEvent getCowHerd() {
        return (MobSpawnEvent) events.get("cow-herd");
    }

    /**
     * @return The MobSpawnEvent for the Drop Creeper event. <p>
     * Usage shown in MobSpawnEvent.
     * @see MobSpawnEvent
     */
    public static MobSpawnEvent getDropCreepEvent() {
        return (MobSpawnEvent) events.get("drop-creeper");
    }

    /**
     * @return The MobSpawnEvent for the End Raid event. <p>
     * Usage shown in MobSpawnEvent.
     * @see MobSpawnEvent
     */
    public static MobSpawnEvent getEndRaidEvent() {
        return (MobSpawnEvent) events.get("end-raid");
    }

    /**
     * @return The MobSpawnEvent for the Nether Raid event. <p>
     * Usage shown in MobSpawnEvent.
     * @see MobSpawnEvent
     */
    public static MobSpawnEvent getNetherRaidEvent() {
        return (MobSpawnEvent) events.get("nether-raid");
    }

    /**
     * @return The MobSpawnEvent for the Random Mob Spawn event. <p>
     * Usage shown in MobSpawnEvent.
     * @see MobSpawnEvent
     */
    public static MobSpawnEvent getRandMobSpawnEvent() {
        return (MobSpawnEvent) events.get("random-mob-spawn");
    }

    /**
     * @return The MobSpawnEvent for the Wolf Pack event. <p>
     * Usage shown in MobSpawnEvent.
     * @see MobSpawnEvent
     */
    public static MobSpawnEvent getWolfPackEvent() {
        return (MobSpawnEvent) events.get("wolf-pack");
    }

    /**
     * @return The MobSpawnEvent for the Zombie Horde event. <p>
     * Usage shown in MobSpawnEvent.
     * @see MobSpawnEvent
     */
    public static MobSpawnEvent getZombHordeEvent() {
        return (MobSpawnEvent) events.get("zombie-horde");
    }

    /**
     * @return The MobSpawnEvent for the Zombie Invasion event. <p>
     * Usage shown in MobSpawnEvent.
     * @see MobSpawnEvent
     */
    public static MobSpawnEvent getZombInvEvent() {
        return (MobSpawnEvent) events.get("zombie-invasion");
    }

}
