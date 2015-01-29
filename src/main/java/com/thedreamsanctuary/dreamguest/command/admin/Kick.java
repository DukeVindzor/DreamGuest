package com.thedreamsanctuary.dreamguest.command.admin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.command.CommandHandler;
import com.thedreamsanctuary.dreamguest.util.MessageFormatter;

public class Kick extends CommandHandler{

	public Kick(DreamGuest pl) {
		super(pl);
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
			sender.sendMessage(ChatColor.RED + "Player not found");
			return true;
		}
		if(player == null){
			sender.sendMessage(ChatColor.RED + "Player not found");
			return true;
		}
		player.kickPlayer(reason);
		Bukkit.broadcastMessage(MessageFormatter.formatKickBanMessage(pl.getConfig().getString("admin-kick-message"), sender, player.getDisplayName(), reason));
		return true;
	}

}
