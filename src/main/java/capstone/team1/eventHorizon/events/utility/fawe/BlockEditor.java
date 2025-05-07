package capstone.team1.eventHorizon.events.utility.fawe;

import capstone.team1.eventHorizon.EventHorizon;
import capstone.team1.eventHorizon.utility.MsgUtility;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.function.mask.BlockTypeMask;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.function.pattern.TypeApplyingPattern;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility class for manipulating blocks in WorldEdit regions.
 * This class provides functionality to replace blocks and undo modifications.
 */
public class BlockEditor
{
//    /** List to track active WorldEdit edit sessions */
//    private static List<EditSession> activeEditSessions = new ArrayList<>();
//
//    /**
//     * Replaces blocks in a specified region with a given block type based on a mask of block types.
//     *
//     * @param region The WorldEdit region where blocks will be replaced
//     * @param blockId The Bukkit Material type to replace blocks with
//     * @param blockTypesToReplace Collection of BlockTypes that should be replaced
//     * @param isMaskInverted If true, replaces blocks that don't match the mask instead of those that do
//     */
//    public static void replaceBlocksInRegion(Region region, Material blockId, Collection<BlockType> blockTypesToReplace, boolean isMaskInverted) {
//
//        com.sk89q.worldedit.world.World world = region.getWorld();
//        EditSession editSession = WorldEdit.getInstance()
//                .newEditSessionBuilder()
//                .world(world)
//                .maxBlocks(-1)
//                .build();
//
//        BlockType blockType = BukkitAdapter.asBlockType(blockId);
//        if (blockType == null) {
//            MsgUtility.warning("Block type Null");
//        }
//        Pattern pattern = blockType.getDefaultState();
//
//        BlockTypeMask mask = new BlockTypeMask(editSession, blockTypesToReplace);
//
//        editSession.replaceBlocks(region, isMaskInverted ? mask.inverse() : mask, pattern);
//        Operations.complete(editSession.commit());
//        editSession.flushQueue();
//        activeEditSessions.add(editSession);
//        MsgUtility.log(String.valueOf("# of sessions: " + activeEditSessions.size()));
//        MsgUtility.log(String.valueOf("# of blocks changed: " + editSession.getBlockChangeCount()));
//    }
//    public static void replaceBlocksInRegion(Region region, Pattern replacingPattern, Collection<BlockType> blockTypesToReplace, boolean isMaskInverted) {
//
//        com.sk89q.worldedit.world.World world = region.getWorld();
//        EditSession editSession = WorldEdit.getInstance()
//                .newEditSessionBuilder()
//                .world(world)
//                .maxBlocks(-1)
//                .build();
//
//        BlockTypeMask mask = new BlockTypeMask(editSession, blockTypesToReplace);
//
//        editSession.replaceBlocks(region, isMaskInverted ? mask.inverse() : mask, replacingPattern);
//        Operations.complete(editSession.commit());
//        editSession.flushQueue();
//        activeEditSessions.add(editSession);
//    }
//
//     /**
//     * Undoes all block modifications made through active edit sessions.
//     * Clears the list of active sessions after undoing all changes.
//     * If an error occurs during the undo process, it will be logged as a warning.
//     */
//    public static void undoAllBlockModifications() {
//        try {
//            for (EditSession session : activeEditSessions) {
//                session.undo(session);
//                Operations.complete(session.commit());
//                session.close();
//            }
//            activeEditSessions.clear();
//        } catch (Exception e) {
//            MsgUtility.warning("Failed to undo block modifications: " + e.getMessage());
//        }
//    }
//
//    public static void clearActiveEditSessions() {
//        for (EditSession session : activeEditSessions) {
//            session.close();
//        }
//        activeEditSessions.clear();
//    }
// Store the world and changes for each session instead of the session itself
private static List<EditHistoryEntry> editHistory = new ArrayList<>();

    // Inner class to store the necessary information for undoing
    private static class EditHistoryEntry {
        private final com.sk89q.worldedit.world.World world;
        private final EditSession session;

        public EditHistoryEntry(com.sk89q.worldedit.world.World world, EditSession session) {
            this.world = world;
            this.session = session;
        }
    }

    // Modify the replaceBlocksInRegion methods to store history
    public static void replaceBlocksInRegion(Region region, Pattern replacingPattern, Collection<BlockType> blockTypesToReplace, boolean isMaskInverted) {
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
    public static void replaceBlocksInRegion(Region region, Material blockId, Collection<BlockType> blockTypesToReplace, boolean isMaskInverted) {
        BlockType blockType = BukkitAdapter.asBlockType(blockId);
        if (blockType == null) {
            MsgUtility.warning("Block type Null");
            return;
        }
        Pattern pattern = blockType.getDefaultState();
        replaceBlocksInRegion(region, pattern, blockTypesToReplace, isMaskInverted);
    }

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

    public static void clearActiveEditSessions() {
        for (EditHistoryEntry entry : editHistory) {
            entry.session.close();
        }
        editHistory.clear();
    }
}
