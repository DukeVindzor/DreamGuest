package com.thedreamsanctuary.dreamguest.chat;

import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.admin.handlers.VanishFakeQuitHandler;

public class AdminConnector {
	private static ChatModule module;
	private static boolean enabled;
	
	public static void init(ChatModule m){
		module = m;
		if(module.getPlugin().isInstalled("com.thedreamsanctuary.dreamguest.admin.AdminModule.class")){
			enabled = true;
		}else{
			enabled = false;
		}
	}
	
	public static boolean isVanished(Player p){
		if(!enabled){
			return false;
		}
		return VanishFakeQuitHandler.isVanished(p);
	}
	
	public static boolean isFakeQuit(Player p){
		if(!enabled){
			return false;
		}
		return VanishFakeQuitHandler.isFakeQuit(p);
	}
	
	public static int getFakeQuitSize(){
		if(!enabled){
			return 0;
		}
		return VanishFakeQuitHandler.getFakeQuitSize();
	}
}
