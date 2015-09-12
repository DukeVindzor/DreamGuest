package com.thedreamsanctuary.dreamguest.chat.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.admin.handlers.VanishFakeQuitHandler;
import com.thedreamsanctuary.dreamguest.util.MessageFormatter;

public class ConnectionEventListener implements Listener {
	private final DreamGuest pl;
	
	public ConnectionEventListener(DreamGuest pl) {
		this.pl = pl;
	}
	
	/**Handles player join events
     *
     * @param event		 A PlayerJoinEvent
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(MessageFormatter.formatJoinLeaveMessage(pl.getConfig().getString("join-message"), player));
        VanishFakeQuitHandler.handleJoin(player);
    }

    /**Handles player quit events
     *
     * @param event		A PlayerQuitEvent
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
    	Player player = event.getPlayer();
        event.setQuitMessage(MessageFormatter.formatJoinLeaveMessage(pl.getConfig().getString("leave-message"), player));
        VanishFakeQuitHandler.handleLeave(player);
    }

    /**Handles player kick events
     * 
     * @param event		A PlayerKickEvent
     */
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
    	Player player = event.getPlayer();
        event.setLeaveMessage(MessageFormatter.formatJoinLeaveMessage(pl.getConfig().getString("kick-message"), player));
        VanishFakeQuitHandler.handleLeave(player);
    }
}
