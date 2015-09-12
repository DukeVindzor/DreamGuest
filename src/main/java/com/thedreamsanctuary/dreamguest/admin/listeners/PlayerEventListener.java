package com.thedreamsanctuary.dreamguest.admin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.thedreamsanctuary.dreamguest.admin.handlers.VanishFakeQuitHandler;

public class PlayerEventListener implements Listener {
	
	/**Handles entity target events
     *
     * @param event 	An EntityTargetEvent
     */
    @EventHandler
    public void onEntityTarget(final EntityTargetEvent event) {
        if (event.getTarget() != null) {
            if (event.getTarget() instanceof Player) {
                Player player = (Player) event.getTarget();
                if (VanishFakeQuitHandler.isVanished(player)) {
                    event.setCancelled(true);
                    return;
                }
            }
        }
    }
    
    /**Handles player item pickup events
     * 
     * @param event 		A PlayerPickupItemEvent
     */
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        if (event.getPlayer() != null) {
            Player player = event.getPlayer();
            if (VanishFakeQuitHandler.isVanished(player)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
