package com.thedreamsanctuary.dreamguest.handlers;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanishFakeQuitHandler {
	static ArrayList<Player> vanished = new ArrayList<Player>();
	static final String vanishSeePermission = "dreamguest.admin.vanish";
	static final String fakequitPermission = "dreamguest.admin.fakequit";
	
	/**
	 * check if a player is vanished
	 * @param player targeted Player
	 * @return true if player is vanished, false if not
	 */
	public static boolean isVanished(Player player){
		return vanished.contains(player);
	}
	
	public static boolean isFakeQuit(Player player){
		return false;
		//TODO vanish feature
	}
	
	public static int getFakeQuitSize(){
		return 0;
		//TODO
	}
	
	public static int getFakeOnline(Player player){
		int players = Bukkit.getOnlinePlayers().size();
		if(player.hasPermission(fakequitPermission)){
			return players;
		}else{
			return players - getFakeQuitSize();
		}
	}
	
	/**
	 * vanish the specified player
	 * @param player targeted player
	 */
	public static void vanishPlayer(Player player){
		//check if player is already vanished, if not, add him to the vanished list
		if(!vanished.contains(player)){
			vanished.add(player);
		}
		//iterate through all online players
		for(Player p : Bukkit.getOnlinePlayers()){
			//if current player does not have the permission to see vanished players, hide the player from them
			if(!p.hasPermission(vanishSeePermission)){
				p.hidePlayer(player);
			}
		}
	}
	
	/**
	 * make a player reappear
	 * @param player the player to check
	 */
	public static void unvanishPlayer(Player player){
		//if player is vanished, remove him from the vanished list
		if(vanished.contains(player)){
			vanished.remove(player);
		}
		//iterate through all players
		for(Player p : Bukkit.getOnlinePlayers()){
			//show reappeared player to him
			p.showPlayer(player);
		}
	}
	
	/**
	 * get the list of all currently vanished players
	 * @return ArrayList containing all vanished players
	 */
	public static ArrayList<Player> getVanished(){
		return vanished;
	}
	
	/**
	 * handle the joining of a new player
	 * @param player the player that just joined
	 */
	public static void handleJoin(Player player){
		//check if player is allowed to see vanished players
        if(!player.hasPermission(vanishSeePermission)){
        	//hide all vanished people from joined player if not
        	for(Player p : VanishFakeQuitHandler.getVanished()){
        		player.hidePlayer(p);
        	}
        }
	}
	
	/**
	 * handle the leaving of a player
	 * @param player player that left
	 */
	public static void handleLeave(Player player){
		//remove the player from the list of vanished people
		unvanishPlayer(player);
	}
}
