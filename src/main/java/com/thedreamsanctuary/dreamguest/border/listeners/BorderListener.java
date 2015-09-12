package com.thedreamsanctuary.dreamguest.border.listeners;

import java.util.HashSet;

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

public class BorderListener implements Listener {
	private static final HashSet<Material> NULL_SET = null;//Avoid deprecation
	
	/**Handles player item consume events
    *
    * @param event		 A PlayerItemConsumeEvent
    */
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		if (event.getItem().getType().equals(Material.ENDER_PEARL)) {
			event.setCancelled(true);
		}
	}
	
	/**Handles player interact events
    *
    * @param event		 A PlayerInteractEvent
    */
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerInteract(PlayerInteractEvent event){
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			//replacement EnderPearl handler
			if (event.getItem() == null || !event.getItem().getType().equals(Material.ENDER_PEARL)) {
				return;
			}
			event.setUseItemInHand(Result.DENY);
			if (!event.isCancelled()) {
				event.setCancelled(true);
				if (event.getPlayer().hasPermission("dreamguest.border.enderpearl")) {
					useItem(event.getPlayer());
					Location curLoc = event.getPlayer().getTargetBlock(NULL_SET, 50).getLocation();
					if (curLoc == null) {
						return;
					}
					while (!isValidJump(curLoc) ){
						curLoc.add(0,1,0);
					}
					event.getPlayer().teleport(curLoc, TeleportCause.COMMAND);
				}
			}
		}
	}
	
	 /**Helps handling item use by removing one item from the stack the player is holding, and clearing the stack if only 1 remains
     * 
     * @param player	 The player using a material
     */
    private void useItem(Player player) {
        ItemStack hand = player.getItemInHand();
        if (hand != null) {
            if (hand.getAmount() > 1) {
                hand.setAmount(hand.getAmount() - 1);
            }
            else {
                PlayerInventory inv = player.getInventory();
                if (inv != null) {
                    inv.clear(inv.getHeldItemSlot());
                }
            }
        }
    }
    
    /**Calculates whether of not a point is a valid jump destination
     * 
     * @param jumpLoc		The destination of the jump
     * @return 				True if valid, false otherwise
     */
    private boolean isValidJump(final Location jumpLoc) {
        Location locA = jumpLoc.clone(), locB = jumpLoc.clone().add(0, 1, 0);
        return locA.getBlock().isEmpty() && locB.getBlock().isEmpty();
    }
    
    /**Handles player move events
    *
    * @param event		 A PlayerMoveEvent
    */
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		if (!BorderHandler.canMoveTo(event.getPlayer(), event.getFrom(), event.getTo())) {
			event.getPlayer().teleport(event.getFrom(), TeleportCause.ENDER_PEARL);
		}
	}
	
	/**Handles player teleport events
    *
    * @param event		 A PlayerTeleportEvent
    */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if (event.getTo() == null) {
			event.setCancelled(true);
			return;
		}
		if (event.getCause().equals(TeleportCause.ENDER_PEARL)) {
			return;
		}
		if (!BorderHandler.canMoveTo(event.getPlayer(), event.getFrom(), event.getTo())) {
			event.setCancelled(true);
		}
	}
	
	/**Handles player enter vehicle events
    *
    * @param event		 A VehicleEnterEvent
    */
	@EventHandler
    public void onVehicleEnter(VehicleEnterEvent event) {
        if (event.getEntered() instanceof Player) {
            Player player = ((Player) event.getEntered());
            if (!BorderHandler.canMoveTo(player, event.getEntered().getLocation(), event.getVehicle().getLocation())) {
                event.getVehicle().eject();
                event.getEntered().teleport(event.getEntered().getLocation(), TeleportCause.PLUGIN);
                event.setCancelled(true);
            }
        }
    }
	
	/**Handles player move in vehicle events
    *
    * @param event		 A VehicleMoveEvent
    */
    @EventHandler
    public void onVehicleMove(VehicleMoveEvent event) {
        if (event.getVehicle() == null) {
            return;
        }
        if (event.getVehicle().getPassenger() instanceof Player) {
            Player player = ((Player) event.getVehicle().getPassenger());
            if (!BorderHandler.canMoveTo(player, event.getFrom(), event.getTo())) {
                Entity entity = event.getVehicle().getPassenger();
                event.getVehicle().eject();
                entity.teleport(event.getFrom(), TeleportCause.ENDER_PEARL);
            }
        }
    }
	
	
}
