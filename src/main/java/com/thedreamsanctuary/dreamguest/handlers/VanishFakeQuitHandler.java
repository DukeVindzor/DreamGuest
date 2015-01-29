package com.thedreamsanctuary.dreamguest.handlers;

import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.DreamGuest;

public class VanishFakeQuitHandler {
	private final DreamGuest pl;
	public VanishFakeQuitHandler(DreamGuest pl){
		this.pl = pl;
	}
	
	public boolean isVanished(Player player){
		return false;
		//TODO vanish feature
	}
	
	public boolean isFakeQuit(Player player){
		return false;
		//TODO vanish feature
	}
	
	public int getFakeQuitSize(){
		return 0;
		//TODO
	}
}
