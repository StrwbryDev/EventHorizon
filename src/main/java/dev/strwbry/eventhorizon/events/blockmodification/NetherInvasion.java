package dev.strwbry.eventhorizon.events.blockmodification;

import dev.strwbry.eventhorizon.EventHorizon;
import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.blockmodification.subevents.SubNetherRaid;
import dev.strwbry.eventhorizon.events.blockmodification.subevents.SubPlantsToFire;
import dev.strwbry.eventhorizon.events.blockmodification.subevents.SubWaterToLava;
import dev.strwbry.eventhorizon.events.utility.EventLoggerUtility;
import dev.strwbry.eventhorizon.events.utility.fawe.region.GenericCylindricalRegion;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import org.bukkit.Bukkit;

/**
 * A negative event that transforms the environment into a Nether-like landscape.
 * Creates a cylindrical region of 100 blocks radius and 400 blocks height where ground blocks
 * are replaced with Nether-themed blocks, water is converted to lava, grass is set on fire,
 * and Nether mobs are spawned.
 */
public class NetherInvasion extends BaseBlockModification
{
    private final SubNetherRaid subNetherRaid;

    /**
     * Constructs a new NetherInvasion event.
     * Creates a cylindrical region with radius 100 and height 400 where ground blocks
     * will be replaced with a random Nether-themed pattern.
     */
    public NetherInvasion()
    {
        super(EventClassification.NEGATIVE, "netherInvasion", new GenericCylindricalRegion(AdvConfig.getNetherRadius(),AdvConfig.getNetherHeight(),AdvConfig.getNetherOffset()),
                EventHorizon.getRandomPatterns().getNetherPattern(), EventHorizon.getBlockMasks().getGroundBlocks(), false);
        this.subNetherRaid = new SubNetherRaid();

        EventLoggerUtility.logEventInitialization("Nether Invasion",
                "radius", AdvConfig.getNetherRadius(),
                "height", AdvConfig.getNetherHeight(),
                "offset", AdvConfig.getNetherOffset()
        );
    }

    /**
     * Executes the Nether invasion event by transforming blocks and spawning mobs.
     * First applies the Nether pattern transformation, then schedules water-to-lava conversion,
     * grass-to-fire conversion, and spawns Nether mobs on the next server tick.
     */
    public void execute(){
        super.execute(true);
        Bukkit.getScheduler().runTask(EventHorizon.getPlugin(), task -> new SubWaterToLava().execute(false));
        Bukkit.getScheduler().runTask(EventHorizon.getPlugin(), task -> new SubPlantsToFire().execute(false));
        Bukkit.getScheduler().runTask(EventHorizon.getPlugin(), task -> subNetherRaid.execute());
    }

    /**
     * Terminates the Nether invasion event by clearing all active edit sessions.
     * This method overrides the parent class terminate method to use a different cleanup approach.
     */
    @Override
    public void terminate(){
        super.terminate();
        subNetherRaid.terminate();
    }
}
