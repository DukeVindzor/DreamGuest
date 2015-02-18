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

public class ChatConnectionEventListener implements Listener{
	private final DreamGuest pl;
	
	public ChatConnectionEventListener(DreamGuest pl){
		this.pl = pl;
	}
	/**
     * Handles player join events.
     *
     * @param event The Bukkit event.
     */
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        event.setJoinMessage(MessageFormatter.formatJoinLeaveMessage(pl.getConfig().getString("join-message"), player));
        if (pl.isFakeQuit(player))
        {
            event.setJoinMessage("");
        }
        VanishFakeQuitHandler.handleJoin(player);
    }

    /**
     * Handles player quit events.
     *
     * @param event The Bukkit event.
     */
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event)
    {
    	final Player player = event.getPlayer();

        event.setQuitMessage(MessageFormatter.formatJoinLeaveMessage(pl.getConfig().getString("leave-message"), player));
        if (pl.isFakeQuit(player))
        {
            event.setQuitMessage("");
        }
        VanishFakeQuitHandler.handleLeave(player);
    }

    /**
     * Handles player kick events.
     *
     * @param event The Bukkit event.
     */
    @EventHandler
    public void onPlayerKick(final PlayerKickEvent event)
    {
    	final Player player = event.getPlayer();

        event.setLeaveMessage(MessageFormatter.formatJoinLeaveMessage(pl.getConfig().getString("kick-message"), player));
        if (pl.isFakeQuit(player))
        {
            event.setLeaveMessage("");
        }
        VanishFakeQuitHandler.handleLeave(player);
    }
}
