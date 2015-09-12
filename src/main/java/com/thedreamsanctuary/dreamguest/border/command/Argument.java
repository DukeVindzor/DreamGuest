package com.thedreamsanctuary.dreamguest.border.command;

import org.bukkit.entity.Player;

abstract class Argument {
	private final int arguments;
	private final String usage;
	
	Argument(int argument, String usage) {
		this.arguments = argument;
		this.usage = usage;
	}
	
	int getArgLength() {
		return arguments;
	}

	String getUsage() {
		return usage;
	}
	
	abstract void run(Player player, String[] args);
}
