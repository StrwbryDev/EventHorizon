package dev.strwbry.eventhorizon.events.utility;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class ItemUtility {
    private static final Set<Material> NON_OBTAINABLE_MATERIALS = initializeNonObtainableMaterials();

    private static Set<Material> initializeNonObtainableMaterials() {
        Set<Material> nonObtainable = EnumSet.noneOf(Material.class);

        // Add specific creative-only blocks and items
        nonObtainable.add(Material.BARRIER);
        nonObtainable.add(Material.STRUCTURE_BLOCK);
        nonObtainable.add(Material.STRUCTURE_VOID);
        nonObtainable.add(Material.JIGSAW);
        nonObtainable.add(Material.DEBUG_STICK);
        nonObtainable.add(Material.LIGHT);
        nonObtainable.add(Material.TEST_BLOCK);
        nonObtainable.add(Material.TEST_INSTANCE_BLOCK);
        nonObtainable.add(Material.VAULT);
        nonObtainable.add(Material.SPAWNER);
        nonObtainable.add(Material.TRIAL_SPAWNER);
        nonObtainable.add(Material.BEDROCK);
        nonObtainable.add(Material.END_PORTAL_FRAME);
        nonObtainable.add(Material.CHORUS_PLANT);
        nonObtainable.add(Material.KNOWLEDGE_BOOK);
        nonObtainable.add(Material.REINFORCED_DEEPSLATE);
        nonObtainable.add(Material.FILLED_MAP);

        // Process all materials to catch pattern-based exclusions
        for (Material material : Material.values()) {
            String name = material.name();

            // Pattern matching for categories of items
            if (name.contains("COMMAND_BLOCK") ||
                    name.contains("SPAWN_EGG") ||
                    name.contains("POTION") ||
                    name.contains("TIPPED_ARROW") ||
                    name.contains("ENCHANTED_BOOK")) {
                nonObtainable.add(material);
            }
        }

        return nonObtainable;
    }

    public static List<ItemStack> generateSurvivalDropsList() {
        List<ItemStack> drops = new ArrayList<>();

        for (Material material : Material.values()) {
            // Skip non-items and AIR
            if (!material.isItem() || material == Material.AIR) {
                continue;
            }

            // Skip non-obtainable materials
            if (!isSurvivalObtainable(material)) {
                continue;
            }

            // Add the item to our valid drops list
            drops.add(new ItemStack(material));
        }

        return drops;
    }

    private static boolean isSurvivalObtainable(Material material) {
        return !NON_OBTAINABLE_MATERIALS.contains(material);
    }
}
