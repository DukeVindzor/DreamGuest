package com.thedreamsanctuary.dreamguest.handlers;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanishFakeQuitHandler {
	static ArrayList<Player> vanished = new ArrayList<Player>();
	static final String vanishSeePermission = "dreamguest.admin.vanish";
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
	
	public static void vanishPlayer(Player player){
		if(!vanished.contains(player)){
			vanished.add(player);
		}
		for(Player p : Bukkit.getOnlinePlayers()){
			if(!p.hasPermission(vanishSeePermission)){
				p.hidePlayer(player);
			}
		}
	}
	
	public static void unvanishPlayer(Player player){
		if(vanished.contains(player)){
			vanished.remove(player);
		}
		for(Player p : Bukkit.getOnlinePlayers()){
			p.showPlayer(player);
		}
	}
	
	public static ArrayList<Player> getVanished(){
		return vanished;
	}
	
	public static void handleJoin(Player player){
        if(!player.hasPermission(vanishSeePermission)){
        	for(Player p : VanishFakeQuitHandler.getVanished()){
        		player.hidePlayer(p);
        	}
        }
	}
	
	public static void handleLeave(Player player){
		unvanishPlayer(player);
	}
}
