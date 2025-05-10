package dev.strwbry.eventhorizon.events.utility.fawe;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.registry.state.Property;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.Material;

/**
 * Manages and provides various random block patterns for world editing operations.
 * This class handles the creation and initialization of themed block patterns,
 * such as Nether-themed and Deep Dark-themed patterns, using WorldEdit's pattern system.
 * Patterns are lazily initialized when first requested.
 */
public class RandomPatterns
{
    /** Pattern consisting of Nether-themed blocks like Netherrack and Soul Sand */
    private static Pattern netherPattern;
    /** Pattern consisting of Deep Dark-themed blocks like Sculk and Deepslate */
    private static Pattern deepDarkPattern;
    /** Pattern consisting of interactive Sculk blocks like Sensors and Shriekers */
    private static Pattern sculkInteractivePattern;
    /** Property determining if a Sculk Shrieker can summon the Warden */
    final Property<Boolean> CAN_SUMMON;
    /** BlockState representing a naturally generated Sculk Shrieker that can summon the Warden */
    final BlockState naturalShrieker;

    /**
     * Constructor for RandomPatterns class.
     * Initializes properties for Sculk Shrieker blocks,
     * specifically setting up the can_summon property and natural shrieker state.
     */
    public RandomPatterns() {
        CAN_SUMMON = BlockTypes.SCULK_SHRIEKER.getProperty("can_summon");
        naturalShrieker = BlockTypes.SCULK_SHRIEKER.getDefaultState().with(CAN_SUMMON, true);
    }

    /**
     * Initializes the Nether-themed pattern with a mix of blocks.
     * Creates a random pattern with equal parts Netherrack and Soul Sand.
     */
    private void initializeNetherPattern() {
        RandomPattern pattern = new RandomPattern();
        pattern.add(BukkitAdapter.asBlockType(Material.NETHERRACK), 0.5);
        pattern.add(BukkitAdapter.asBlockType(Material.SOUL_SAND), 0.5);
        netherPattern = pattern;
    }

    /**
     * Initializes the Deep Dark-themed pattern.
     * Creates a random pattern with 70% Sculk blocks and 30% Deepslate blocks.
     */
    private void initializeDeepDarkPattern() {
        RandomPattern pattern = new RandomPattern();
        pattern.add(BukkitAdapter.asBlockType(Material.SCULK), 0.7);
        pattern.add(BukkitAdapter.asBlockType(Material.DEEPSLATE), 0.3);
        deepDarkPattern = pattern;
    }

    /**
     * Initializes the interactive Sculk pattern.
     * Creates a random pattern with Sculk-related blocks in specific proportions:
     * - 20% Sculk Vein
     * - 10% Sculk Catalyst
     * - 40% Natural Sculk Shrieker (with Warden summoning enabled)
     * - 20% Sculk Sensor
     * - 10% Air
     */
    private void initializeSculkInteractivePattern() {
        RandomPattern pattern = new RandomPattern();
        pattern.add(BukkitAdapter.asBlockType(Material.SCULK_VEIN), 0.2);
        pattern.add(BukkitAdapter.asBlockType(Material.SCULK_CATALYST), 0.1);
        pattern.add(naturalShrieker, 0.4);
        pattern.add(BukkitAdapter.asBlockType(Material.SCULK_SENSOR), 0.2);
        pattern.add(BukkitAdapter.asBlockType(Material.AIR), 0.1);
        sculkInteractivePattern = pattern;
    }

    /**
     * Retrieves the Nether-themed pattern, initializing it if necessary.
     * @return Pattern containing Nether-themed blocks
     */
    public Pattern getNetherPattern() {
        if (netherPattern == null) {
            initializeNetherPattern();
        }
        return netherPattern;
    }

    /**
     * Retrieves the Deep Dark-themed pattern, initializing it if necessary.
     * @return Pattern containing Deep Dark-themed blocks
     */
    public Pattern getDeepDarkPattern() {
        if (deepDarkPattern == null) {
            initializeDeepDarkPattern();
        }
        return deepDarkPattern;
    }

    /**
     * Retrieves the Sculk interactive pattern, initializing it if necessary.
     * @return Pattern containing interactive Sculk blocks
     */
    public Pattern getSculkInteractivePattern() {
        if (sculkInteractivePattern == null) {
            initializeSculkInteractivePattern();
        }
        return sculkInteractivePattern;
    }
}
