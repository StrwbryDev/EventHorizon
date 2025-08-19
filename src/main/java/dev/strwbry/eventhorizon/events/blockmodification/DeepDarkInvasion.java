package dev.strwbry.eventhorizon.events.blockmodification;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.blockmodification.subevents.SubPlantsToSculkInteractive;
import dev.strwbry.eventhorizon.events.utility.fawe.region.GenericCylindricalRegion;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import org.bukkit.Bukkit;

/**
 * Represents a negative event that transforms an area into a Deep Dark biome-like environment.
 * This event modifies terrain using deep dark-themed blocks and converts plants to sculk.
 */
public class DeepDarkInvasion extends BaseBlockModification
{
    /**
     * Constructs a new DeepDarkInvasion event.
     * Initializes the event with negative classification, a cylindrical region of effect,
     * deep dark block patterns, and ground block masks.
     */
    public DeepDarkInvasion()
    {
        super(EventClassification.NEGATIVE, "deepDarkInvasion", new GenericCylindricalRegion(AdvConfig.getDeepDarkRadius(),AdvConfig.getDeepDarkHeight(),AdvConfig.getDeepDarkOffset()),
                EventHorizon.getRandomPatterns().getDeepDarkPattern(), EventHorizon.getBlockMasks().getGroundBlocks(), false);

        EventHorizon.getPlugin().getLogger().info(String.format("DeepDarkInvasion event initialized with radius %d, height %d, and height offset %d",
                AdvConfig.getDeepDarkRadius(),
                AdvConfig.getDeepDarkHeight(),
                AdvConfig.getDeepDarkOffset())
        );
    }

    /**
     * Executes the Deep Dark invasion event.
     * Performs the main terrain transformation and schedules the plant-to-sculk conversion.
     */
    public void execute(){
        super.execute(true);
        Bukkit.getScheduler().runTask(EventHorizon.getPlugin(), task -> new SubPlantsToSculkInteractive().execute(true));
    }

    /**
     * Terminates the Deep Dark invasion event.
     * Cleans up any resources by calling the parent class termination.
     */
    @Override
    public void terminate(){
        super.terminate();
    }
}
