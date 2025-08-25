package dev.strwbry.eventhorizon.listeners;

import dev.strwbry.eventhorizon.EventHorizon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class PlayerGamemodeChangeListener implements Listener {

    @EventHandler
    public void onPlayerGamemodeChange(PlayerGameModeChangeEvent event) {

        // If the player is switching to spectator mode, remove them from available event players
        // If the player switches out of spectator mode, the next event that runs will add them back
        Player player = event.getPlayer();
        EventHorizon.getEventManager().getAvailableEventPlayers().remove(player);
    }
}
