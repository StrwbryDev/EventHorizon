package dev.strwbry.eventhorizon.events.effects;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.utility.EventLoggerUtility;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import org.bukkit.potion.PotionEffectType;

/**
 * Event that gives the player saturation and slowness effects for 6000 ticks (5 minutes)
 */
public class FoodComa extends BaseEffects
{
    /**
     * Constructs a new FoodComa event with NEUTRAL classification.
     * Initializes the event with saturation and slowness effects.
     */
    public FoodComa()
    {
        super(EventClassification.NEUTRAL, "foodComa");
        addEffect(PotionEffectType.SATURATION, AdvConfig.getFoodComaSaturationDuration(), AdvConfig.getFoodComaSaturationAmplifier(),
                AdvConfig.getFoodComaSaturationAmbient(), AdvConfig.getFoodComaSaturationParticles(), AdvConfig.getFoodComaSaturationIcon());

        addEffect(PotionEffectType.SLOWNESS, AdvConfig.getFoodComaSlownessDuration(), AdvConfig.getFoodComaSlownessAmplifier(),
                AdvConfig.getFoodComaSaturationAmbient(),AdvConfig.getFoodComaSlownessParticles(), AdvConfig.getFoodComaSlownessIcon());

        /*EventLoggerUtility.logEventInitialization("FoodComa",
                "saturation duration", AdvConfig.getFoodComaSaturationDuration(),
                        "saturation amplifier", AdvConfig.getFoodComaSaturationAmplifier(),
                        "saturation ambient", AdvConfig.getFoodComaSaturationAmbient(),
                        "saturation particles", AdvConfig.getFoodComaSaturationParticles(),
                        "saturation icon", AdvConfig.getFoodComaSaturationIcon(),

                        "slowness duration", AdvConfig.getFoodComaSlownessDuration(),
                        "slowness amplifier", AdvConfig.getFoodComaSlownessAmplifier(),
                        "slowness ambient", AdvConfig.getFoodComaSlownessAmbient(),
                        "slowness particles", AdvConfig.getFoodComaSlownessParticles(),
                        "slowness icon", AdvConfig.getFoodComaSlownessIcon()
        );*/

        EventLoggerUtility.logEventInitialization(this);
    }

    /**
     * Executes the FoodComa event by applying saturation and slowness effects to all players.
     * Delegates execution to the parent class.
     */
    @Override
    public void execute()
    {
        super.execute();
    }

    /**
     * Terminates the FoodComa event by removing saturation and slowness effects from affected players.
     * Delegates termination to the parent class.
     */
    @Override
    public void terminate()
    {
        super.terminate();
    }
}
