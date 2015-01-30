package com.thedreamsanctuary.dreamguest.command.admin;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.command.CommandHandler;
import com.thedreamsanctuary.dreamguest.handlers.BanHandler;
import com.thedreamsanctuary.dreamguest.util.MessageFormatter;
import com.thedreamsanctuary.dreamguest.util.UUIDFetcher;

public class BanReason extends CommandHandler{

	public BanReason(DreamGuest pl) {
		super(pl);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(args.length != 1){
			return false;
		}
		String target = args[0];
		if(target.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}")){
			getReasonByUUID(sender, UUID.fromString(target));
		}else{
			getReasonByName(sender, target);
		}
		return true;
	}
	
	public void getReasonByUUID(CommandSender sender, UUID playerUUID){
		String reason = BanHandler.getPlayerBanreason(playerUUID);
		String name = BanHandler.getBannedPlayerName(playerUUID);
		sender.sendMessage(MessageFormatter.formatKickBanMessage(pl.getConfig().getString("admin-banreason-message"), "", name, reason));
	}
	
	public void getReasonByName(CommandSender sender, String target){
		try {
			UUID playerUUID = UUIDFetcher.getUUIDOf(target);
			getReasonByUUID(sender, playerUUID);
		} catch (Exception e) {
			sender.sendMessage(ChatColor.RED + "Playername could either not be converted to UUID or he is not banned. He may have changed names, please try the UUID directly.");
			return;
		}
	}

}
