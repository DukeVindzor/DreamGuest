package com.thedreamsanctuary.dreamguest;

import org.bukkit.command.CommandExecutor;

public abstract class CommandHandler implements CommandExecutor {
	protected final Module module;
	protected final DreamGuest pl;
	
	public CommandHandler(Module module) {
		this.module = module;
		this.pl = module.getPlugin();
	}
}
