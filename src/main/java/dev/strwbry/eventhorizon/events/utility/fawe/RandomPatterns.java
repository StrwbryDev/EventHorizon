package dev.strwbry.eventhorizon.events.utility.fawe;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.registry.state.Property;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.Material;

public class RandomPatterns
{
    private static Pattern netherPattern;
    private static Pattern deepDarkPattern;
    private static Pattern sculkInteractivePattern;

    final Property<Boolean> CAN_SUMMON;
    final BlockState naturalShrieker;

    public RandomPatterns() {
        CAN_SUMMON = BlockTypes.SCULK_SHRIEKER.getProperty("can_summon");
        naturalShrieker = BlockTypes.SCULK_SHRIEKER.getDefaultState().with(CAN_SUMMON, true);
    }

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
        deepDarkPattern = pattern;
    }

    private void initializeSculkInteractivePattern() {
        RandomPattern pattern = new RandomPattern();
        pattern.add(BukkitAdapter.asBlockType(Material.SCULK_VEIN), 0.2);
        pattern.add(BukkitAdapter.asBlockType(Material.SCULK_CATALYST), 0.1);
        pattern.add(naturalShrieker, 0.4);
        pattern.add(BukkitAdapter.asBlockType(Material.SCULK_SENSOR), 0.2);
        pattern.add(BukkitAdapter.asBlockType(Material.AIR), 0.1);
        sculkInteractivePattern = pattern;
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
