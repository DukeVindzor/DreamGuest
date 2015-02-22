package com.thedreamsanctuary.dreamguest.chat;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.Module;
import com.thedreamsanctuary.dreamguest.chat.command.AFK;
import com.thedreamsanctuary.dreamguest.chat.command.AddAFKMessage;
import com.thedreamsanctuary.dreamguest.chat.command.Who;
import com.thedreamsanctuary.dreamguest.chat.listeners.ConnectionEventListener;
import com.thedreamsanctuary.dreamguest.chat.listeners.PlayerEventListener;

public class ChatModule extends Module{
	protected AdminConnector con;
	public ChatModule(DreamGuest pl) {
		super(pl);
		AdminConnector.init(this);
	}

	@Override
	public void disable() {
		
	}

	@Override
	public void enable() {
		addCommand("who", new Who(this));
		addCommand("afk", new AFK(this));
		addCommand("addafkmessage", new AddAFKMessage(this));
		addListener(new ConnectionEventListener(pl));
		addListener(new PlayerEventListener());
	}

}
