package com.thedreamsanctuary.dreamguest.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.DreamGuest;

public class MessageFormatter {
	private static String playerplaceholder = "${n}";
	private static String playercountplaceholder = "${no}";
	private static String playercountmin1placeholder = "${no-1}";
	private static String adminplaceholder = "${admin}";
	private static String reasonplaceholder = "${reason}";
	
	public static String formatJoinLeaveMessage(String format, Player player){
		return format.replace(playerplaceholder, player.getDisplayName()).replace(playercountplaceholder, "" + Bukkit.getOnlinePlayers().size()).replace(playercountmin1placeholder, "" + (Bukkit.getOnlinePlayers().size()-1));
	}
	
	public static String formatKickBanMessage(String format, CommandSender sender, String target, String reason){
		return format.replace(playerplaceholder, target).replace(adminplaceholder, sender.getName()).replace(reasonplaceholder, reason);
	}
}
