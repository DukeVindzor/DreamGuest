package com.thedreamsanctuary.dreamguest.chat.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.thedreamsanctuary.dreamguest.chat.handlers.AfkHandler;

public class PlayerEventListener implements Listener {
	
	/**Handles player move events
     *
     * @param event		A PlayerMoveEvent
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
    	returnPlayer(event.getPlayer());
    }

    /**Handles player chat events
     *
     * @param event		An AsyncPlayerChatEvent
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
    	returnPlayer(event.getPlayer());
    }

    /**Handles player teleport events
     *
     * @param event 	A PlayerTeleportEvent
     */
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
    	returnPlayer(event.getPlayer());
    }
    
    /**Handles player interact events
    *
    * @param event 	A PlayerInteractEvent
    */
   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
   	returnPlayer(event.getPlayer());
   }

   /**Handles player quit events.
    *
    * @param event 	A PlayerQuitEvent
    */
   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent event) {
   	returnPlayer(event.getPlayer());
   }

   /**Handles player kick events.
    *
    * @param event 	A PlayerKickEvent
    */
   @EventHandler
   public void onPlayerKick(PlayerKickEvent event) {
   	returnPlayer(event.getPlayer());
   }

    /**Handles player command events
     *
     * @param event 	A PlayerCommandPreprocessEvent
     */
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer() != null) {
        	if (event.getMessage().startsWith("/afk")) {
        		return;
        	}
        	AfkHandler.playerReturned(event.getPlayer());
        }
    }

    private static void returnPlayer(Player player) {
    	if (player != null) {
        	AfkHandler.playerReturned(player);
        }
    }
}
