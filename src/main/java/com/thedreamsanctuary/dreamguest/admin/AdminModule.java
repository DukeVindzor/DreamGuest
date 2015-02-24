package com.thedreamsanctuary.dreamguest.admin;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.Module;
import com.thedreamsanctuary.dreamguest.admin.command.BanCommand;
import com.thedreamsanctuary.dreamguest.admin.command.BanReasonCommand;
import com.thedreamsanctuary.dreamguest.admin.command.KickCommand;
import com.thedreamsanctuary.dreamguest.admin.command.UnbanCommand;
import com.thedreamsanctuary.dreamguest.admin.command.VanishCommand;
import com.thedreamsanctuary.dreamguest.admin.listeners.ConnectionEventListener;
import com.thedreamsanctuary.dreamguest.admin.listeners.PlayerEventListener;

public class AdminModule extends Module{

	public AdminModule(DreamGuest pl) {
		super(pl);
	}

	@Override
	public void disable() {
		
	}

	@Override
	public void enable() {
		addCommand("ban", new BanCommand(this));
		addCommand("unban", new UnbanCommand(this));
		addCommand("banreason", new BanReasonCommand(this));
		addCommand("kick", new KickCommand(this));
		addCommand("vanish", new VanishCommand(this));
		addListener(new ConnectionEventListener());
		addListener(new PlayerEventListener());
	}

}
