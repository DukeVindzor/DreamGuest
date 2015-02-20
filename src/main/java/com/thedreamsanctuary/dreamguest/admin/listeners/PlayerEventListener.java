package com.thedreamsanctuary.dreamguest.admin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.thedreamsanctuary.dreamguest.admin.handlers.VanishFakeQuitHandler;

public class PlayerEventListener implements Listener{
	/**
     * Handles entity target events.
     *
     * @param event The Bukkit event.
     */
    @EventHandler
    public void onEntityTarget(final EntityTargetEvent event)
    {
        if (event.getTarget() != null)
        {
            if (event.getTarget() instanceof Player)
            {
                final Player player = (Player) event.getTarget();
                if (VanishFakeQuitHandler.isVanished(player))
                {
                    event.setCancelled(true);
                    return;
                }
                if (VanishFakeQuitHandler.isFakeQuit(player))
                {
                    event.setCancelled(true);
                }
            }
        }
    }
    
    /**
     * Handles player item pickup events.
     *
     * @param event The Bukkit event.
     */
    @EventHandler
    public void onPlayerPickupItem(final PlayerPickupItemEvent event)
    {
        if (event.getPlayer() != null)
        {
            final Player player = event.getPlayer();
            if (VanishFakeQuitHandler.isVanished(player))
            {
                event.setCancelled(true);
                return;
            }
            if (VanishFakeQuitHandler.isFakeQuit(player))
            {
                event.setCancelled(true);
            }
        }
    }
}
