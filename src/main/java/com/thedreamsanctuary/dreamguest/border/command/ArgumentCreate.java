package com.thedreamsanctuary.dreamguest.border.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.border.Border;
import com.thedreamsanctuary.dreamguest.border.command.Argument;
import com.thedreamsanctuary.dreamguest.border.handlers.BorderHandler;

class ArgumentCreate extends Argument {
	ArgumentCreate() {
		super(5, "/dborder create <NAME> <X1> <Z1> <X2> <Z2>");
	}
	
	@Override
	void run (Player player, String[] args) {
		int x1, z1, x2, z2;
        try {
            x1 = Integer.parseInt(args[1]);
            z1 = Integer.parseInt(args[2]);
            x2 = Integer.parseInt(args[3]);
            z2 = Integer.parseInt(args[4]);
        }
        catch (Exception e) {
            player.sendMessage(ChatColor.GREEN + "Incorrect parameters " + ChatColor.GRAY + this.getUsage());
            return;
        }
        Border newBorder = new Border(args[0], x1, z1, x2, z2, player.getWorld().getUID());
        BorderHandler.addBorder(newBorder);
        player.sendMessage(ChatColor.GRAY + "Zone successfully created!");
        Bukkit.getLogger().info("Player created border " + newBorder);
	}
}
