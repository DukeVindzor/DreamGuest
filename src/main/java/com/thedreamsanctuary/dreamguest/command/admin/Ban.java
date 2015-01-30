package com.thedreamsanctuary.dreamguest.command.admin;

import java.util.UUID;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.command.CommandHandler;
import com.thedreamsanctuary.dreamguest.handlers.BanHandler;
import com.thedreamsanctuary.dreamguest.util.BanResult;
import com.thedreamsanctuary.dreamguest.util.MessageFormatter;
import com.thedreamsanctuary.dreamguest.util.UUIDFetcher;

public class Ban extends CommandHandler{
	private final DreamGuest pl;
	public Ban(DreamGuest pl) {
		super(pl);
		this.pl = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(args.length < 1){
			return false;
		}
		String target = args[0];
		String reason = pl.getConfig().getString("default-reason");
		if(args.length > 1){
			reason = "";
			for(int i = 1 ; i < args.length ; i++){
				reason += args[i] + " ";
			}
		}
		Player player = null;
		try{
			player = Bukkit.getPlayer(target);
		}catch (Exception e){
			
		}
		UUID playerUUID;
		boolean online = true;
		if(player==null){
			online = false;
			try {
				playerUUID = UUIDFetcher.getUUIDOf(target);
			} catch (Exception e) {
				sender.sendMessage(ChatColor.RED + "Player could not be found");
				return true;
			}
		}else{
			playerUUID = player.getUniqueId();
		}
		BanResult result = BanHandler.addPlayer(playerUUID, reason);
		switch(result){
		case SUCCESS:
			Bukkit.broadcastMessage(MessageFormatter.formatKickBanMessage(pl.getConfig().getString("admin-ban-message"), sender.getName(), target, reason));
			if(online){
				player.kickPlayer(reason);
			}
			break;
		case ALREADY_BANNED:
			sender.sendMessage(ChatColor.RED + "That player is already banned.");
			break;
		case ERROR:
			BanList bl = Bukkit.getBanList(BanList.Type.NAME);
			bl.addBan(player.getDisplayName(), reason, null, sender.getName());
			player.kickPlayer(reason);
			Bukkit.broadcastMessage(MessageFormatter.formatKickBanMessage(pl.getConfig().getString("admin-ban-message"), sender.getName(), player.getDisplayName(), reason));
			sender.sendMessage(ChatColor.RED + "Error parsing ban file, banning via bukkit API. Please notify an Administrator of this.");
			break;
		default:
			break;
		}
		return true;
	}

}
