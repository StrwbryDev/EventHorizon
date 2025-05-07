package capstone.team1.eventHorizon.events.utility.fawe;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.pattern.AbstractPattern;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.Material;

public class RandomPatterns
{
    private static Pattern netherPattern;
    private static Pattern deepDarkPattern;
    private static Pattern sculkInteractivePattern;

    private void initializeNetherPattern() {
        RandomPattern pattern = new RandomPattern();
        pattern.add(BukkitAdapter.asBlockType(Material.NETHERRACK), 0.5);
        pattern.add(BukkitAdapter.asBlockType(Material.SOUL_SAND), 0.5);
        netherPattern = pattern;
    }

    private void initializeDeepDarkPattern() {
        RandomPattern pattern = new RandomPattern();
        pattern.add(BukkitAdapter.asBlockType(Material.SCULK), 0.6);
        pattern.add(BukkitAdapter.asBlockType(Material.DEEPSLATE), 0.4);
    }

    private void initializeSculkInteractivePattern() {
        RandomPattern pattern = new RandomPattern();

        NaturalSculkShrieker naturalShrieker = new NaturalSculkShrieker();
        naturalShrieker.setCanSummon(true);

        pattern.add(BukkitAdapter.asBlockType(Material.SCULK_VEIN), 0.2);
        pattern.add(BukkitAdapter.asBlockType(Material.SCULK_CATALYST), 0.1);
        pattern.add(BukkitAdapter.asBlockType(naturalShrieker.getMaterial()), 0.1);
        pattern.add(BukkitAdapter.asBlockType(Material.SCULK_SENSOR), 0.2);
        pattern.add(BukkitAdapter.asBlockType(Material.AIR), 0.4);
        deepDarkPattern = pattern;
    }

    public Pattern getNetherPattern() {
        if (netherPattern == null) {
            initializeNetherPattern();
        }
        return netherPattern;
    }

    public Pattern getDeepDarkPattern() {
        if (deepDarkPattern == null) {
            initializeDeepDarkPattern();
        }
        return deepDarkPattern;
    }
    public Pattern getSculkInteractivePattern() {
        if (sculkInteractivePattern == null) {
            initializeSculkInteractivePattern();
        }
        return sculkInteractivePattern;
    }

}
