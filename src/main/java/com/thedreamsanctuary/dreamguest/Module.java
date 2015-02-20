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
	public abstract void disable();
	public abstract void enable();
	public void addListener(Listener l){
		if(!listeners.contains(l)){
			listeners.add(l);
		}
	}
	public void addCommand(String name, CommandHandler c){
		if(!commands.containsKey(name)){
			commands.put(name, c);
		}
	}
	public void enableListeners(){
		for(Listener l : listeners){
			pl.getServer().getPluginManager().registerEvents(l, pl);
		}
	}
	public void enableCommandHandlers(){
		for(String s : commands.keySet()){
			CommandHandler c = commands.get(s);
			pl.getCommand(s).setExecutor(c);
			if(c instanceof TabCompleter){
				TabCompleter tab = (TabCompleter) c;
				pl.getCommand(s).setTabCompleter(tab);
			}
		}
	}
	public DreamGuest getPlugin(){
		return pl;
	}
}
