package dev.strwbry.eventhorizon.events.testconfig;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AdvConfigTest {
    private static final Map<String, Event> events = new HashMap<>();
    private static FileConfiguration config;

    static {
        loadEvents();
    }

    public static EffectEvent getIceEvent(){
        return (EffectEvent) events.get("ice-is-nice");
    }

    public static AttributeEvent getGrowthEvent(){
        return (AttributeEvent) events.get("growth-spurt");
    }

    private static void loadEvents() {
        // loads config file
        config = YamlConfiguration.loadConfiguration(new File("adv-config-test.yml"));

        // loads effect events
        loadEffectEvents();
        loadAttributeEvents();
    }

    // generic method for any event
    @SuppressWarnings("unchecked")
    public static <T extends Event> T getEvent(String name, Class<T> type) {
        Event event = events.get(name);
        if (event != null && type.isInstance(event)) {
            return (T) event;
        }
        return null;
    }


    private static void loadEffectEvents() {
        ConfigurationSection effectsSection = config.getConfigurationSection("events.effects");
        if (effectsSection == null) return;

        for (String eventName : effectsSection.getKeys(false)) {
            String path = "events.effects." + eventName + ".";

            EffectEvent event = new EffectEvent(
                    eventName,
                    getOptionalInt(path + "duration"),
                    getOptionalDouble(path + "amplifier"),
                    getOptionalBoolean(path + "ambient"),
                    getOptionalBoolean(path + "showParticles"),
                    getOptionalBoolean(path + "showIcon")
            );

            events.put(eventName, event);
        }
    }

    private static void loadAttributeEvents() {
        ConfigurationSection attributesSection = config.getConfigurationSection("events.attributes");
        if (attributesSection == null) return;

        for (String eventName : attributesSection.getKeys(false)) {
            String path = "events.attributes." + eventName + ".";

            AttributeEvent event = new AttributeEvent(
                    eventName,
                    getOptionalDouble(path + "growthScale"),
                    getOptionalDouble(path + "maxHealth"),
                    getOptionalDouble(path + "attackDmg"),
                    getOptionalDouble(path + "knockResist"),
                    getOptionalDouble(path + "movespeed"),
                    getOptionalDouble(path + "sneakspeed"),
                    getOptionalDouble(path + "waterMovespeed"),
                    getOptionalDouble(path + "stepHeight"),
                    getOptionalDouble(path + "jumpStrength"),
                    getOptionalDouble(path + "safeFallDist"),
                    getOptionalDouble(path + "blockInteractRange"),
                    getOptionalDouble(path + "entityInteractRange")
                    
            );

            events.put(eventName, event);
        }
    }



    private static Integer getOptionalInt(String path) {
        return config.contains(path) ? config.getInt(path) : null;
    }

    private static Double getOptionalDouble(String path) {
        return config.contains(path) ? config.getDouble(path) : null;
    }

    private static Boolean getOptionalBoolean(String path) {
        return config.contains(path) ? config.getBoolean(path) : null;
    }

/*
    private static String getOptionalString(String path) {
        return config.contains(path) ? config.getString(path) : null;
    }
*/
}
