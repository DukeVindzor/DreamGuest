package com.thedreamsanctuary.dreamguest.chat;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.Module;
import com.thedreamsanctuary.dreamguest.chat.command.AFKCommand;
import com.thedreamsanctuary.dreamguest.chat.command.AddAFKMessageCommand;
import com.thedreamsanctuary.dreamguest.chat.command.WhoCommand;
import com.thedreamsanctuary.dreamguest.chat.listeners.ConnectionEventListener;
import com.thedreamsanctuary.dreamguest.chat.listeners.PlayerEventListener;

public class ChatModule extends Module {
	protected AdminConnector con;
	
	public ChatModule(DreamGuest pl) {
		super(pl);
		AdminConnector.init(this);
	}

	@Override
	public void enable() {
		addCommand("who", new WhoCommand(this));
		addCommand("afk", new AFKCommand(this));
		addCommand("addafkmessage", new AddAFKMessageCommand(this));
		addListener(new ConnectionEventListener(pl));
		addListener(new PlayerEventListener());
	}

	@Override
	public void disable() {
	}

}
