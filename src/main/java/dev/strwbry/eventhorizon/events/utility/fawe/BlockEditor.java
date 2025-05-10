package dev.strwbry.eventhorizon.events.utility.fawe;

import dev.strwbry.eventhorizon.utility.MsgUtility;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.mask.BlockTypeMask;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for manipulating blocks in WorldEdit regions.
 * This class provides functionality to replace blocks and undo modifications.
 */
public class BlockEditor
{
    /**
     * List of edit history entries storing world and edit session data.
     * Used to track and undo block modifications made through this editor.
     */
private static List<EditHistoryEntry> editHistory = new ArrayList<>();

    /**
     * Inner class to store the necessary information for undoing edit operations.
     */
    private static class EditHistoryEntry {
        /**
         * The WorldEdit world instance where the edit operation was performed.
         * Used to create new edit sessions for undo operations.
         */
        private final com.sk89q.worldedit.world.World world;
        /**
         * The EditSession containing the changes made during the edit operation.
         * Used to undo the changes when necessary.
         */
        private final EditSession session;

        /**
         * Creates a new edit history entry.
         *
         * @param world The WorldEdit world where the edit occurred
         * @param session The EditSession containing the changes
         */
        public EditHistoryEntry(com.sk89q.worldedit.world.World world, EditSession session) {
            this.world = world;
            this.session = session;
        }
    }

    /**
     * Replaces blocks in a specified region using a pattern.
     *
     * @param region The region where blocks should be replaced
     * @param replacingPattern The pattern to replace blocks with
     * @param blockTypesToReplace Collection of block types to be replaced
     * @param isMaskInverted If true, replaces blocks that don't match the mask
     */
    public static void replaceBlocksInRegion(@NotNull Region region, @NotNull Pattern replacingPattern, @NotNull Collection<BlockType> blockTypesToReplace, boolean isMaskInverted) {
        com.sk89q.worldedit.world.World world = region.getWorld();
        EditSession editSession = WorldEdit.getInstance()
                .newEditSessionBuilder()
                .world(world)
                .maxBlocks(-1)
                .build();

        BlockTypeMask mask = new BlockTypeMask(editSession, blockTypesToReplace);
        editSession.replaceBlocks(region, isMaskInverted ? mask.inverse() : mask, replacingPattern);
        Operations.complete(editSession.commit());
        editSession.flushQueue();

        // Store the history entry
        editHistory.add(new EditHistoryEntry(world, editSession));
    }

    /**
     * Replaces blocks in a specified region using a Bukkit Material.
     *
     * @param region The region where blocks should be replaced
     * @param blockId The Bukkit Material to replace blocks with
     * @param blockTypesToReplace Collection of block types to be replaced
     * @param isMaskInverted If true, replaces blocks that don't match the mask
     */
    public static void replaceBlocksInRegion(Region region, Material blockId, Collection<BlockType> blockTypesToReplace, boolean isMaskInverted) {
        BlockType blockType = BukkitAdapter.asBlockType(blockId);
        if (blockType == null) {
            MsgUtility.warning("Block type Null");
            return;
        }
        Pattern pattern = blockType.getDefaultState();
        replaceBlocksInRegion(region, pattern, blockTypesToReplace, isMaskInverted);
    }

    /**
     * Undoes all block modifications stored in the edit history.
     * Creates new sessions for each undo operation and closes them afterward.
     * Clears the edit history after completion.
     */
    public static void undoAllBlockModifications() {
        try {
            // Create new sessions for each undo operation
            for (EditHistoryEntry entry : editHistory) {
                // Create a new session for undoing
                EditSession newSession = WorldEdit.getInstance()
                        .newEditSessionBuilder()
                        .world(entry.world)
                        .maxBlocks(-1)
                        .build();

                // Perform the undo
                entry.session.undo(newSession);
                Operations.complete(newSession.commit());

                // Close both sessions
                newSession.close();
                entry.session.close();
            }
            editHistory.clear();
        } catch (Exception e) {
            MsgUtility.warning("Failed to undo block modifications: " + e.getMessage());
        }
    }

    /**
     * Closes all active edit sessions and clears the edit history.
     * Should be called during plugin shutdown or when cleanup is needed.
     */
    public static void clearActiveEditSessions() {
        for (EditHistoryEntry entry : editHistory) {
            entry.session.close();
        }
        editHistory.clear();
    }
}
