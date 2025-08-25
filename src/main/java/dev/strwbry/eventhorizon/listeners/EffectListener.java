package dev.strwbry.eventhorizon.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EffectListener implements Listener {
    Map<UUID, Collection<PotionEffect>> playerEffects = new HashMap<>();

    @EventHandler
    public void onPlayerDeathStorePotions(PlayerDeathEvent event) {
        // If the player has active potion effects upon death, store them
        if (!event.getPlayer().getActivePotionEffects().isEmpty()) {
            playerEffects.put(event.getPlayer().getUniqueId(), event.getPlayer().getActivePotionEffects());
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {

       Player player = event.getPlayer();
       // If the player has stored effects, reapply them
       if (playerEffects.containsKey(player.getUniqueId())){
           player.addPotionEffects(playerEffects.get(player.getUniqueId()));
       }
    }
}
