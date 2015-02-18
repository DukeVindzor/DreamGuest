package com.thedreamsanctuary.dreamguest.admin.tabcompletion;


import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.admin.handlers.BanHandler;

public class ConstructTabComplete implements TabCompleter{
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if(cmd.getName().equalsIgnoreCase("unban")&& args.length >=1){
			if(sender instanceof Player){
				return BanHandler.getBannedPlayers();
			}
		}
		return null;
	}

}
