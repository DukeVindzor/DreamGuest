package com.thedreamsanctuary.dreamguest.chat;

import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.admin.handlers.VanishFakeQuitHandler;

public class AdminConnector {
	private final ChatModule m;
	private final boolean enabled;
	public AdminConnector(ChatModule m){
		this.m = m;
		if(m.getPlugin().isInstalled("com.thedreamsanctuary.dreamguest.admin.AdminModule.class")){
			enabled = true;
		}else{
			enabled = false;
		}
	}
	
	public boolean isVanished(Player p){
		if(!enabled){
			return false;
		}
		return VanishFakeQuitHandler.isVanished(p);
	}
	
	public boolean isFakeQuit(Player p){
		if(!enabled){
			return false;
		}
		return VanishFakeQuitHandler.isFakeQuit(p);
	}
	
	public int getFakeQuitSize(){
		if(!enabled){
			return 0;
		}
		return VanishFakeQuitHandler.getFakeQuitSize();
	}
}
