package com.thedreamsanctuary.dreamguest.handlers;

import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.DreamGuest;

public class AfkHandler {
	private final DreamGuest pl;
	public AfkHandler(DreamGuest pl){
		this.pl = pl;
	}
	public boolean isAfk(Player player){
		return false;
		//TODO
	}
}
