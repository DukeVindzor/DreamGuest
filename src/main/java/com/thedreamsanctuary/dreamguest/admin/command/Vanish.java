package com.thedreamsanctuary.dreamguest.admin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.admin.handlers.VanishFakeQuitHandler;
import com.thedreamsanctuary.dreamguest.command.CommandHandler;

public class Vanish extends CommandHandler{
	static final String vanishstring = "You have vanished!";
	static final String appearstring = "You have reappeared!";
	static final String vanishotherstring = "You have vanished the player ${n}!";
	static final String appearotherstring = "You have made the player ${n} reappear!";
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		if(args.length > 1){
			return false;
		}
		//if no argument given, target is the player itself
		if(args.length == 0){
			Player player = (Player) sender;
			//toggle Vanishing
			if(VanishFakeQuitHandler.isVanished(player)){
				VanishFakeQuitHandler.unvanishPlayer(player);
				player.sendMessage(ChatColor.AQUA + appearstring);
			}else{
				VanishFakeQuitHandler.vanishPlayer(player);
				player.sendMessage(ChatColor.AQUA + vanishstring);
			}
			return true;
		}
		//try to find target player
		Player player = Bukkit.getPlayer(args[0]);
		if(player == null){
			sender.sendMessage(ChatColor.RED + "Player could not be found.");
			return true;
		}
		//toggle targeted players vanish status
		if(VanishFakeQuitHandler.isVanished(player)){
			VanishFakeQuitHandler.unvanishPlayer(player);
			sender.sendMessage(ChatColor.AQUA + appearotherstring.replace("${n}", player.getDisplayName()));
		}else{
			VanishFakeQuitHandler.vanishPlayer(player);
			sender.sendMessage(ChatColor.AQUA + vanishotherstring.replace("${n}", player.getDisplayName()));
		}
		return true;
	}
	
	
}
