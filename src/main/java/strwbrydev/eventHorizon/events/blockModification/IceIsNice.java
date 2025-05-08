package strwbrydev.eventHorizon.events.blockModification;

import strwbrydev.eventHorizon.EventHorizon;
import strwbrydev.eventHorizon.events.EventClassification;
import strwbrydev.eventHorizon.events.blockModification.subEvents.SubSpawnIceMobs;
import strwbrydev.eventHorizon.events.utility.fawe.BlockEditor;
import strwbrydev.eventHorizon.events.utility.fawe.region.GenericCylindricalRegion;
import org.bukkit.Bukkit;
import org.bukkit.Material;

/**
 * A neutral event that creates an icy environment by replacing ground blocks with packed ice
 * in a cylindrical region and spawns ice-themed mobs.
 * The event transforms the terrain into a winter wonderland with a radius of 100 blocks
 * and a height of 10 blocks.
 */
public class IceIsNice extends BaseBlockModification
{
    /**
     * Constructs a new IceIsNice event.
     * Creates a cylindrical region with radius 100 and height 10 where ground blocks
     * will be replaced with packed ice.
     */
    public IceIsNice()
    {
        super(EventClassification.NEUTRAL, "iceIsNice", new GenericCylindricalRegion(100,10,0), Material.PACKED_ICE, EventHorizon.getBlockMasks().getGroundBlocks(), false);
    }

    /**
     * Executes the ice transformation event and spawns ice-themed mobs.
     * First replaces blocks with packed ice, then schedules the spawning of ice mobs
     * on the next server tick.
     */
    public void execute(){
        super.execute(false);
        Bukkit.getScheduler().runTask(EventHorizon.getPlugin(), task -> new SubSpawnIceMobs().execute());

    }

    /**
     * Terminates the ice event by clearing all active edit sessions.
     * This method overrides the parent class terminate method to use a different cleanup approach.
     */
    @Override
    public void terminate(){
        BlockEditor.clearActiveEditSessions();
    }
}
