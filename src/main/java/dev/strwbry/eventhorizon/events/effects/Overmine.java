package dev.strwbry.eventhorizon.events.effects;

import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import org.bukkit.potion.PotionEffectType;

/**
 * Event that gives players an enhanced mining speed effect.
 * This event applies a level 2 haste effect for 6000 ticks (5 minutes),
 * allowing players to mine blocks faster than normal.
 */
public class Overmine extends BaseEffects {
    /**
     * Constructs a new Overmine event with POSITIVE classification.
     * Initializes the event with a moderate-level haste effect.
     */
    public Overmine() {
        super(EventClassification.POSITIVE, "overmine");
        addEffect(PotionEffectType.HASTE, AdvConfig.getOvermineDuration(), AdvConfig.getOvermineAmplifier(),
                AdvConfig.getOvermineAmbient(), AdvConfig.getOvermineParticles(), AdvConfig.getOvermineIcon());
    }

    /**
     * Executes the Overmine event by applying the haste effect to all players.
     * Delegates execution to the parent class.
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * Terminates the Overmine event by removing the haste effect from affected players.
     * Delegates termination to the parent class.
     */
    @Override
    public void terminate() {
        super.terminate();
    }
}
