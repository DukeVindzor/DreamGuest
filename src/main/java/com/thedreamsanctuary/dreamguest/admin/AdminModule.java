package com.thedreamsanctuary.dreamguest.admin;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.Module;
import com.thedreamsanctuary.dreamguest.admin.command.Ban;
import com.thedreamsanctuary.dreamguest.admin.command.BanReason;
import com.thedreamsanctuary.dreamguest.admin.command.Kick;
import com.thedreamsanctuary.dreamguest.admin.command.Unban;
import com.thedreamsanctuary.dreamguest.admin.command.Vanish;
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
		addCommand("ban", new Ban(this));
		addCommand("unban", new Unban(this));
		addCommand("banreason", new BanReason(this));
		addCommand("kick", new Kick(this));
		addCommand("vanish", new Vanish(this));
		addListener(new ConnectionEventListener());
		addListener(new PlayerEventListener());
	}

}
