package dev.strwbry.eventhorizon.events.testconfig;

import java.util.Map;
import java.util.Set;

/**
 * Represents an effect event with specific parameters.
 * All Effect Events might not utilize all parameters.
 *
 * @param name      The name of the event
 * @param effects   A map of all potion effects associated with the event
 * @implNote This record implements the Event interface
 * Usage example:
 * <pre>
 * {@code EffectEvent <name> = AdvConfigTest.get<event-name>Event();
 *
 * Integer duration = <name>.getEffect("<potion-effect>").duration();
 * AdvConfigTest.get<event-name>Event().getEffect("<potion-effect>").duration();
 * }
 * </pre>
 * @see Event
 */
public record EffectEvent(
        String name,
        Map<String, PotionEffectData> effects
) implements Event {
    @Override
    public String getName() { return name; }

    // Get a specific potion effect by type
    public PotionEffectData getEffect(String potionType) {
        return effects.get(potionType);
    }

    // Convenience method to check if a potion effect exists
    public boolean hasEffect(String potionType) {
        return effects.containsKey(potionType);
    }

    // Get all available potion effect types
    public Set<String> getEffectTypes() {
        return effects.keySet();
    }

    /**
     * This is a record that holds the data for a specific potion effect.
     * Each event may have multiple potion effects, and this record
     * encapsulates the parameters for each effect.
     *
     * @param duration       The duration of the effect in seconds
     * @param amplifier      The amplifier level of the effect
     * @param ambient        Whether the effect is ambient
     * @param showParticles  Whether to show particles for the effect
     * @param showIcon       Whether to show HUD icon for the effect
     * @see EffectEvent
     * @implNote Used within EffectEvent to define the properties of each potion effect.
     * It allows for flexible configuration of multiple potion effects in a single event.
     */
    public record PotionEffectData(
            Integer duration,
            Double amplifier,
            Boolean ambient,
            Boolean showParticles,
            Boolean showIcon
    ) {}
}
