package com.thedreamsanctuary.dreamguest.chat.handlers;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
	/**
	 * toggles the afk status of a player
	 * @param player
	 * @return true if player is now afk, false if player is not afk anymore
	 */
	public static boolean toggleAFK(Player player){
		if(!playerlist.contains(player)){
			playerlist.add(player);
			return true;
		}else{
			playerlist.remove(player);
			return false;
		}
	}
	/**
	 * indicate that the player interacted with something, so he might have returned
	 * @param player the specified player
	 */
	public static void playerReturned(Player player) {
		//if players should not be returned upon interact, stop execution now
		if(!Bukkit.getPluginManager().getPlugin("DreamGuest").getConfig().getBoolean("toggle-afk-on-interact")){
			return;
		}
		//remove player from AFK list if it contains him
		if(playerlist.contains(player)){
			playerlist.remove(player);
			String afkmessage = Bukkit.getPluginManager().getPlugin("DreamGuest").getConfig().getString("default-return-message");
			//broadcast return message
			Bukkit.broadcastMessage(MessageFormatter.formatAFKMessage(Bukkit.getPluginManager().getPlugin("DreamGuest").getConfig().getString("afk-format"), player, afkmessage));
		}
	}
}
