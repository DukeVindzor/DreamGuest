package com.thedreamsanctuary.dreamguest.border.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.border.Border;
import com.thedreamsanctuary.dreamguest.border.command.Argument;
import com.thedreamsanctuary.dreamguest.border.handlers.BorderHandler;

class ArgumentDelete extends Argument {
	ArgumentDelete() {
		super(1, "/dborder delete <NAME>");	
	}
	
	@Override
	void run(Player player, String[] args) {
		Border oldBorder = BorderHandler.getBorder(args[0]);
		if (oldBorder != null) {
			BorderHandler.removeBorder(oldBorder);
			player.sendMessage(ChatColor.GRAY + "Zone sucessfully removed");
			Bukkit.getLogger().info("Player removed border " + oldBorder);
		}
		else {
			player.sendMessage(ChatColor.GREEN + "Incorrect parameters " + ChatColor.GRAY + this.getUsage());
		}
	}
}
