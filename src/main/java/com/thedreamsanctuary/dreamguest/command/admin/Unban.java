package com.thedreamsanctuary.dreamguest.command.admin;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.command.CommandHandler;
import com.thedreamsanctuary.dreamguest.util.BanResult;
import com.thedreamsanctuary.dreamguest.util.BanHandler;
import com.thedreamsanctuary.dreamguest.util.MessageFormatter;
import com.thedreamsanctuary.dreamguest.util.UUIDFetcher;

public class Unban extends CommandHandler{

	public Unban(DreamGuest pl) {
		super(pl);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(args.length != 1){
			return false;
		}
		String target = args[0];
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
			sender.sendMessage(MessageFormatter.formatKickBanMessage(pl.getConfig().getString("admin-unban-message"), sender, target, ""));
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
