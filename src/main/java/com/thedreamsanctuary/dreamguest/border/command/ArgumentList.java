package com.thedreamsanctuary.dreamguest.border.command;

import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.border.command.Argument;
import com.thedreamsanctuary.dreamguest.border.handlers.BorderHandler;

class ArgumentList extends Argument{
	ArgumentList() {
		super(0, "/dborder list");
	}
	
	@Override
	void run(Player player, String[] args) {
		for (String borderInfo : BorderHandler.getBorders()) {
	        player.sendMessage(borderInfo);
	    }
	}
}
