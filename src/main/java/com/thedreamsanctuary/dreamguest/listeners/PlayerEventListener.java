package com.thedreamsanctuary.dreamguest.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.handlers.AfkHandler;

public class PlayerEventListener implements Listener{
	public PlayerEventListener(DreamGuest pl){
		
	}
	
	/**
     * Handles player move events.
     *
     * @param event The Bukkit event.
     */
    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event)
    {
        if (event.getPlayer() != null)
        {
            AfkHandler.playerReturned(event.getPlayer());
        }
    }

    /**
     * Handles player chat events.
     *
     * @param event The Bukkit event.
     */
    @EventHandler
    public void onPlayerChat(final AsyncPlayerChatEvent event)
    {
        if (event.getPlayer() != null)
        {
        	AfkHandler.playerReturned(event.getPlayer());
        }
    }

    /**
     * Handles player teleport events.
     *
     * @param event The Bukkit event.
     */
    @EventHandler
    public void onPlayerTeleport(final PlayerTeleportEvent event)
    {
        if (event.getPlayer() != null)
        {
        	AfkHandler.playerReturned(event.getPlayer());
        }
    }

    /**
     * Handles player command events.
     *
     * @param event The Bukkit event.
     */
    @EventHandler
    public void onPlayerCommand(final PlayerCommandPreprocessEvent event)
    {
        if (event.getPlayer() != null)
        {
        	if(event.getMessage().startsWith("/afk")){
        		return;
        	}
        	AfkHandler.playerReturned(event.getPlayer());
        }
    }

    /**
     * Handles player interact events.
     *
     * @param event The Bukkit event.
     */
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event)
    {
        if (event.getPlayer() != null)
        {
        	AfkHandler.playerReturned(event.getPlayer());
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
        if (event.getPlayer() != null)
        {
        	AfkHandler.playerReturned(event.getPlayer());
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
        if (event.getPlayer() != null)
        {
        	AfkHandler.playerReturned(event.getPlayer());
        }
    }
}
