package com.thedreamsanctuary.dreamguest.border;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.Module;
import com.thedreamsanctuary.dreamguest.border.command.DBorderCommand;
import com.thedreamsanctuary.dreamguest.border.handlers.BorderHandler;
import com.thedreamsanctuary.dreamguest.border.listeners.BorderListener;

public class BorderModule extends Module {
	
	public BorderModule(final DreamGuest pl) {
		super(pl);
	}
	
	@Override
	public void disable() {
		BorderHandler.saveBorders(BorderHandler.BORDER_FILE);
	}

	@Override
	public void enable() {
		BorderHandler.init(pl);
		addCommand("dborder", new DBorderCommand(this));
		addListener(new BorderListener());
	}
}
