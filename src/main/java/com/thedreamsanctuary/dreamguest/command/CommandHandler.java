package com.thedreamsanctuary.dreamguest.command;

import org.bukkit.command.CommandExecutor;

import ru.tehkode.permissions.PermissionManager;

import com.thedreamsanctuary.dreamguest.DreamGuest;

public abstract class CommandHandler implements CommandExecutor{
	protected final DreamGuest pl;	
	public CommandHandler(DreamGuest pl){
		this.pl = pl;
	}
}
