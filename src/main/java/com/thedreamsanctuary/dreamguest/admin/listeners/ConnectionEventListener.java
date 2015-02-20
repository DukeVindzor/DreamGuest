package com.thedreamsanctuary.dreamguest.admin.listeners;

import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.thedreamsanctuary.dreamguest.admin.handlers.BanHandler;
import com.thedreamsanctuary.dreamguest.util.UUIDFetcher;

public class ConnectionEventListener implements Listener{
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
}
