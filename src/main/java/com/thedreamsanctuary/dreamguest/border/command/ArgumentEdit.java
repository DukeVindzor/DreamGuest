package com.thedreamsanctuary.dreamguest.border.command;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.border.Border;
import com.thedreamsanctuary.dreamguest.border.command.Argument;
import com.thedreamsanctuary.dreamguest.border.handlers.BorderHandler;

class ArgumentEdit extends Argument {
	ArgumentEdit() {
		super(5, "/dborder edit <NAME> <X1> <Z1> <X2> <Z2>");
	}
	
	@Override
	void run(Player player, String[] args) {
		Border oldBorder = BorderHandler.getBorder(args[0]);
		if (oldBorder != null) {
			String borderName = oldBorder.getName();
			UUID worldID = oldBorder.getWorldID();
			int x1, z1, x2, z2;
			try {
                x1 = Integer.parseInt(args[1]);
                z1 = Integer.parseInt(args[2]);
                x2 = Integer.parseInt(args[3]);
                z2 = Integer.parseInt(args[4]);
            }
            catch (Exception e) {
                player.sendMessage(ChatColor.GREEN + "Incorrect parameters " + ChatColor.GRAY + "/vBorder <create:remove:edit> [name] x z x z");
                return;
            }
			BorderHandler.removeBorder(oldBorder);
			BorderHandler.addBorder(new Border(borderName, x1, z1, x2, z2, worldID));
			player.sendMessage(ChatColor.GRAY + "Zone sucessfully edited");
			Bukkit.getLogger().info("Player edited zone " + oldBorder.getName() + ", it is now " + BorderHandler.getBorder(oldBorder.getName()));
		}
		else {
			player.sendMessage(ChatColor.RED + "No border named \"" + args[0] + "\" exists!");
		}
		
	}
}
