package com.thedreamsanctuary.dreamguest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;

public abstract class Module {
	protected DreamGuest pl;
	protected List<Listener> listeners;
	protected Map<String, CommandHandler> commands;
	
	public Module(DreamGuest pl){
		this.pl = pl;
		this.listeners = new ArrayList<Listener>();
		this.commands = new HashMap<String, CommandHandler>();
		this.enable();
		this.enableCommandHandlers();
		this.enableListeners();
	}
	
	public DreamGuest getPlugin(){
		return pl;
	}
	
	/**Adds a listener that module should listen to
	 * 
	 * @param listener		A listener module will subscribe to
	 */
	public void addListener(Listener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	/**Adds a command to the specified command handler
	 * 
	 * @param command				Command you want to add
	 * @param commandHandler		Command handler you want to add this command to
	 */
	public void addCommand(String command, CommandHandler commandHandler){
		if (!commands.containsKey(command)) {
			commands.put(command, commandHandler);
		}
	}
	
	/**Registers the listeners to plugin
	 */
	public void enableListeners(){
		for (Listener l : listeners) {
			pl.getServer().getPluginManager().registerEvents(l, pl);
		}
	}
	
	/**Registers the commands and command handlers to plugin
	 */
	public void enableCommandHandlers(){
		for (String s : commands.keySet()) {
			CommandHandler c = commands.get(s);
			pl.getCommand(s).setExecutor(c);
			if (c instanceof TabCompleter) {
				TabCompleter tab = (TabCompleter) c;
				pl.getCommand(s).setTabCompleter(tab);
			}
		}
	}
	
	public abstract void disable();
	public abstract void enable();
}
