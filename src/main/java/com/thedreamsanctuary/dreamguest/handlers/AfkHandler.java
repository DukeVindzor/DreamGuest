package com.thedreamsanctuary.dreamguest.handlers;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.util.MessageFormatter;

public class AfkHandler {
	private static ArrayList<Player> playerlist = new ArrayList<Player>();
	/**
	 * Checks if a player is afk
	 * @param player
	 * @return true if player is afk, false if not
	 */
	public static boolean isAFK(Player player){
		if(playerlist.contains(player)){
			return true;
		}
		return false;
	}
	public static boolean toggleAFK(Player player){
		if(!playerlist.contains(player)){
			playerlist.add(player);
			return true;
		}else{
			playerlist.remove(player);
			return false;
		}
	}
	public static void playerReturned(Player player) {
		
		if(!Bukkit.getPluginManager().getPlugin("DreamGuest").getConfig().getBoolean("toggle-afk-on-interact")){
			return;
		}
		if(playerlist.contains(player)){
			playerlist.remove(player);
			String afkmessage = Bukkit.getPluginManager().getPlugin("DreamGuest").getConfig().getString("default-return-message");
			Bukkit.broadcastMessage(MessageFormatter.formatAFKMessage(Bukkit.getPluginManager().getPlugin("DreamGuest").getConfig().getString("afk-format"), player, afkmessage));
		}
	}
}
