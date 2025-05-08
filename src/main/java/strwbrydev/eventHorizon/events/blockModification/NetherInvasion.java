package strwbrydev.eventHorizon.events.blockModification;

import strwbrydev.eventHorizon.EventHorizon;
import strwbrydev.eventHorizon.events.EventClassification;
import strwbrydev.eventHorizon.events.blockModification.subEvents.SubPlantsToFire;
import strwbrydev.eventHorizon.events.blockModification.subEvents.SubNetherRaid;
import strwbrydev.eventHorizon.events.blockModification.subEvents.SubWaterToLava;
import strwbrydev.eventHorizon.events.utility.fawe.region.GenericCylindricalRegion;
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
        super(EventClassification.NEGATIVE, "netherInvasion", new GenericCylindricalRegion(100,400,200),
                EventHorizon.getRandomPatterns().getNetherPattern(), EventHorizon.getBlockMasks().getGroundBlocks(), false);
        this.subNetherRaid = new SubNetherRaid();
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
