package capstone.team1.eventHorizon.events.blockModification;

import capstone.team1.eventHorizon.EventHorizon;
import capstone.team1.eventHorizon.events.EventClassification;
import capstone.team1.eventHorizon.events.blockModification.subEvents.SubAirToSculkInteractive;
import capstone.team1.eventHorizon.events.blockModification.subEvents.SubGrassToFire;
import capstone.team1.eventHorizon.events.blockModification.subEvents.SubNetherRaid;
import capstone.team1.eventHorizon.events.blockModification.subEvents.SubPlantsToAir;
import capstone.team1.eventHorizon.events.blockModification.subEvents.SubWaterToLava;
import capstone.team1.eventHorizon.events.utility.fawe.region.GenericCylindricalRegion;
import org.bukkit.Bukkit;

public class DeepDarkInvasion extends BaseBlockModification
{

    public DeepDarkInvasion()
    {
        super(EventClassification.NEGATIVE, "deepDarkInvasion", new GenericCylindricalRegion(100,400,200),
                EventHorizon.getRandomPatterns().getDeepDarkPattern(), EventHorizon.getBlockMasks().getGroundBlocks(), false);
    }

    public void execute(){
        super.execute(true);
        Bukkit.getScheduler().runTask(EventHorizon.getPlugin(), task -> new SubPlantsToAir().execute(false));
        Bukkit.getScheduler().runTask(EventHorizon.getPlugin(), task -> new SubAirToSculkInteractive().execute(false));
    }

    @Override
    public void terminate(){
        super.terminate();
    }
}
