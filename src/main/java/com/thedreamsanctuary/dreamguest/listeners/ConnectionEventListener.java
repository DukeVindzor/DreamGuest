package com.thedreamsanctuary.dreamguest.listeners;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.handlers.BanHandler;
import com.thedreamsanctuary.dreamguest.util.MessageFormatter;
import com.thedreamsanctuary.dreamguest.util.UUIDFetcher;

public class ConnectionEventListener implements Listener{
	private final DreamGuest pl;
	
	public ConnectionEventListener(DreamGuest pl){
		this.pl = pl;
	}
	
	/**
     * Handles banned players.
     *
     * @param event The event.
     */
	@EventHandler
	public void onPlayerLogin(final AsyncPlayerPreLoginEvent event){
		final String playerName = event.getName();
		final UUID playerUUID;
		try{
			playerUUID = UUIDFetcher.getUUIDOf(playerName);
		}catch (Exception e){
			e.printStackTrace();
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "We're having trouble determining your UUID. Please get in touch with an admin.");
            return;
		}
		
		if(BanHandler.isPlayerBanned(playerUUID)){
			event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, BanHandler.getPlayerBanreason(playerUUID));
		}
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
        System.out.println(pl.getConfig().getString("join-format"));
        event.setJoinMessage(MessageFormatter.formatJoinLeaveMessage(pl.getConfig().getString("join-message"), player));
        if (pl.isFakeQuit(player))
        {
            event.setJoinMessage("");
        }
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
    }
}
