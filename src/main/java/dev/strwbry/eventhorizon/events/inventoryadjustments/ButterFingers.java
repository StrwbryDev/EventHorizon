package dev.strwbry.eventhorizon.events.inventoryadjustments;

import dev.strwbry.eventhorizon.events.EventClassification;
import dev.strwbry.eventhorizon.events.utility.EventLoggerUtility;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import dev.strwbry.eventhorizon.events.utility.EventLoggerUtility;
import dev.strwbry.eventhorizon.utility.AdvConfig;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Event that causes players to randomly drop items from their hands at random intervals.
 * This negative event continuously checks players' hands and forces them to drop held items.
 */
public class ButterFingers extends BaseInventoryAdjustment {
    /**
     * Constructs a new ButterFingers event.
     * Initializes the event as NEGATIVE classification with continuous operation enabled.
     * Sets a random initial operation interval between 5 and 60 seconds.
     */
    public ButterFingers() {
        super(EventClassification.NEGATIVE, "butterFingers");
        // Configure the event parameters
        this.useContinuousOperation = true;
        this.useVariableIntervals = true;
        this.minInterval = AdvConfig.getButterFingersOrigin(); // Minimum interval in seconds
        this.maxInterval = AdvConfig.getButterFingersBound(); // Maximum interval in seconds (inclusive)
        this.operationInterval = getRandomInterval(); // Initial random interval

        EventLoggerUtility.logEventInitialization("Butterfingers",
                "use-continuous-operation", this.useContinuousOperation,
                "origin", AdvConfig.getButterFingersOrigin(),
                "bound", AdvConfig.getButterFingersBound()
        );
    }

    /**
     * Applies the butter fingers effect to a specific player.
     * Checks both main hand and off hand for items and forces the player to drop them.
     *
     * @param player the target player
     * @return true if an item was dropped, false if both hands were empty
     */
    @Override
    protected boolean applyToPlayer(Player player) {
        ItemStack handItem;

        if (AdvConfig.getButterfingersMainhand()){
            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                handItem = player.getInventory().getItemInMainHand();
                player.getInventory().setItemInMainHand(null);
                player.dropItem(handItem);
            }
        }
        else if (AdvConfig.getButterfingersOffhand()){
            if (player.getInventory().getItemInOffHand().getType() != Material.AIR) {
                handItem = player.getInventory().getItemInOffHand();
                player.getInventory().setItemInOffHand(null);
                player.dropItem(handItem);
            }
        }
        else {
            return false;
        }

        return true;
    }

    /**
     * Executes the ButterFingers event.
     */
    @Override
    public void execute() {
        super.execute();
    }

    /**
     * Terminates the ButterFingers event.
     */
    @Override
    public void terminate() {
        super.terminate();
    }
}
