package com.thedreamsanctuary.dreamguest.border.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.thedreamsanctuary.dreamguest.border.handlers.BorderHandler;
import com.thedreamsanctuary.dreamguest.util.RangeBlockHelper;

public class BorderListener implements Listener{
	
	@EventHandler
	public void onPlayerItemConsume(final PlayerItemConsumeEvent event){
		if(event.getItem().getType().equals(Material.ENDER_PEARL)){
			event.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerInteract(final PlayerInteractEvent event){
		if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			//replacement EnderPearl handler
			if (event.getItem().getType().equals(Material.ENDER_PEARL)){
				event.setUseItemInHand(Result.DENY);
				if(!event.isCancelled()){
					event.setCancelled(true);
					if(event.getPlayer().hasPermission("dreamguest.border.enderpearl")){
						this.useItem(event.getPlayer());
						
						final RangeBlockHelper rangeHelper = new RangeBlockHelper(event.getPlayer(), event.getPlayer().getWorld());
						final Location curLoc = rangeHelper.getTargetBlock().getLocation();
						
						if(curLoc == null){
							return;
						}
						
						while(!this.isValidJump(curLoc)){
							curLoc.add(0,1,0);
						}
						event.getPlayer().teleport(curLoc, TeleportCause.COMMAND);
					
					}
				}
			}
		}
	}
	
	 /**
     * Helps handling item use by removing one item from the stack the player is holding, and clearing the stack if only 1 remains.
     * 
     * @param player The player using a material
     */
    private void useItem(final Player player)
    {
        final ItemStack hand = player.getItemInHand();
        if (hand != null)
        {
            if (hand.getAmount() > 1) {
                hand.setAmount(hand.getAmount() - 1);
            }
            else
            {
                final PlayerInventory inv = player.getInventory();
                if (inv != null)
                {
                    inv.clear(inv.getHeldItemSlot());
                }
            }
        }
    }
    
    /**
     * Calculates whether of not a point is a valid jump destination
     * 
     * @param jumpLoc The destination of the jump
     * @return True if valid
     */
    private boolean isValidJump(final Location jumpLoc)
    {
        final Location locA = jumpLoc.clone(), locB = jumpLoc.clone().add(0, 1, 0);
        return locA.getBlock().isEmpty() && locB.getBlock().isEmpty();
    }
	@EventHandler
	public void onPlayerMove(final PlayerMoveEvent event){
		if(!BorderHandler.canMoveTo(event.getPlayer(), event.getFrom(), event.getTo())){
			event.getPlayer().teleport(event.getFrom(), TeleportCause.ENDER_PEARL);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerTeleport(final PlayerTeleportEvent event){
		if(event.getTo() == null){
			event.setCancelled(true);
			return;
		}
		if(event.getCause().equals(TeleportCause.ENDER_PEARL)){
			return;
		}
		if(!BorderHandler.canMoveTo(event.getPlayer(), event.getFrom(), event.getTo())){
			event.setCancelled(true);
		}
	}
	
	@EventHandler
    public void onVehicleEnter(final VehicleEnterEvent event)
    {
        if (event.getEntered() instanceof Player)
        {
            final Player player = ((Player) event.getEntered());
            if (!BorderHandler.canMoveTo(player, event.getEntered().getLocation(), event.getVehicle().getLocation()))
            {
                event.getVehicle().eject();
                event.getEntered().teleport(event.getEntered().getLocation(), TeleportCause.PLUGIN);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onVehicleMove(final VehicleMoveEvent event)
    {
        if (event.getVehicle() == null) {
            return;
        }
        if (event.getVehicle().getPassenger() instanceof Player)
        {
            final Player player = ((Player) event.getVehicle().getPassenger());
            if (!BorderHandler.canMoveTo(player, event.getFrom(), event.getTo()))
            {
                final Entity entity = event.getVehicle().getPassenger();
                event.getVehicle().eject();
                entity.teleport(event.getFrom(), TeleportCause.ENDER_PEARL);
            }
        }
    }
	
	
}
