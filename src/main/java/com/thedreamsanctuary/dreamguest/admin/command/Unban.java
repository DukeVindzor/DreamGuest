package com.thedreamsanctuary.dreamguest.admin.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.CommandHandler;
import com.thedreamsanctuary.dreamguest.Module;
import com.thedreamsanctuary.dreamguest.admin.handlers.BanHandler;
import com.thedreamsanctuary.dreamguest.util.BanResult;
import com.thedreamsanctuary.dreamguest.util.MessageFormatter;
import com.thedreamsanctuary.dreamguest.util.UUIDFetcher;

public class Unban extends CommandHandler implements TabCompleter{

	public Unban(Module m) {
		super(m);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		if(cmd.getName().equalsIgnoreCase("unban") && args.length == 1){
			if(sender instanceof Player){
				return BanHandler.getBannedPlayers(args[0]);
			}
		}
		return new ArrayList<String>();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(args.length != 1){
			return false;
		}
		String target = args[0];
		//check if argument is UUID or name
		if(target.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")){
			return unbanByUUID(target, sender);
		}else{
			 return unbanByName(target, sender);
		}
	}
	
	public boolean unbanByUUID(String target, CommandSender sender){
		UUID playerUUID = UUID.fromString(target);
		BanResult result = BanHandler.unbanPlayer(playerUUID);
		switch(result){
		case SUCCESS:
			target = Bukkit.getOfflinePlayer(playerUUID).getName();
			Bukkit.broadcastMessage(MessageFormatter.formatKickBanMessage(pl.getConfig().getString("admin-unban-message"), sender.getName(), target, ""));
			return true;
		case ERROR:
			sender.sendMessage(ChatColor.RED + "An error occurred while unbanning the Player, please contact an Administrator.");
			return true;
		case NOT_BANNED:
			sender.sendMessage(ChatColor.RED + "That player is already unbanned.");
			return true;
		default:
			break;
		}
		return true;
	}
	
	public boolean unbanByName(String target, CommandSender sender){
		Player player = Bukkit.getPlayer(target);
		if(player == null){
			try {
				UUID playerUUID = UUIDFetcher.getUUIDOf(target);
				return unbanByUUID(playerUUID.toString(),sender);
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED + "Player could not be found.");
				return true;
			}
		}else{
			return unbanByUUID(player.getUniqueId().toString(),sender);
		}
	}

}
