package dev.strwbry.eventhorizon.events.utility;

import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemUtility {

    public static List<Pair<ItemStack, Double>> generateWeightedSurvivalDropsList() {
        List<Pair<ItemStack, Double>> drops = new ArrayList<>();

        for (Material material : Material.values()) {
            if (!material.isItem() || material.isAir() || !isSurvivalObtainable(material)) {
                continue;
            }

            double weight = 1.0;
            drops.add(Pair.of(new ItemStack(material), weight));
        }

        return drops;
    }

    public static List<ItemStack> generateSurvivalDropsList() {
        List<ItemStack> drops = new ArrayList<>();

        for (Material material : Material.values()) {
            if (!material.isItem() || material.isAir() || !isSurvivalObtainable(material)) {
                continue;
            }

            drops.add(new ItemStack(material));
        }

        return drops;
    }

    private static final Set<Material> CREATIVE_ONLY_MATERIALS = Set.of(
            Material.COMMAND_BLOCK, Material.CHAIN_COMMAND_BLOCK,
            Material.REPEATING_COMMAND_BLOCK, Material.COMMAND_BLOCK_MINECART,
            Material.JIGSAW, Material.STRUCTURE_BLOCK, Material.STRUCTURE_VOID,
            Material.BARRIER, Material.DEBUG_STICK, Material.TEST_BLOCK,
            Material.TEST_INSTANCE_BLOCK, Material.LIGHT, Material.KNOWLEDGE_BOOK,
            Material.END_PORTAL_FRAME, Material.SPAWNER, Material.TRIAL_SPAWNER,
            Material.BEDROCK, Material.VAULT, Material.REINFORCED_DEEPSLATE,
            Material.ENCHANTED_BOOK, Material.FILLED_MAP, Material.CHORUS_PLANT
    );

    private static boolean isSurvivalObtainable(Material material) {
        // First check the explicit creative-only materials list
        if (CREATIVE_ONLY_MATERIALS.contains(material)) {
            return false;
        }

        String name = material.name();

        // Quick exclusions by pattern
        if (name.contains("SPAWN_EGG") ||
                name.contains("COMMAND_BLOCK") ||
                name.contains("POTION") ||
                name.contains("TIPPED_ARROW") ||
                name.contains("LEGACY")) {
            return false;
        }

        return true;
    }
}
