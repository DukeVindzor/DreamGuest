package com.thedreamsanctuary.dreamguest.command.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import ru.tehkode.permissions.PermissionManager;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.command.CommandHandler;

public class Kick extends CommandHandler{

	public Kick(DreamGuest pl, PermissionManager pex) {
		super(pl, pex);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(args.length < 1){
			return false;
		}
		String target = args[0];
		String reason = pl.getConfig().getString("default-reason");
		if(args.length > 1){
			reason = "";
			for(int i = 1 ; i < args.length ; i++){
				reason += args[i] + " ";
			}
		}
		return false;
	}

}
