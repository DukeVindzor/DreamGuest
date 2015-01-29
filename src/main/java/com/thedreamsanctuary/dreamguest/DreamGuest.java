package com.thedreamsanctuary.dreamguest;


import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.thedreamsanctuary.dreamguest.command.admin.Ban;
import com.thedreamsanctuary.dreamguest.command.admin.Kick;
import com.thedreamsanctuary.dreamguest.command.admin.Unban;
import com.thedreamsanctuary.dreamguest.command.chat.Who;
import com.thedreamsanctuary.dreamguest.handlers.AfkHandler;
import com.thedreamsanctuary.dreamguest.handlers.VanishFakeQuitHandler;
import com.thedreamsanctuary.dreamguest.listeners.ConnectionEventListener;
import com.thedreamsanctuary.dreamguest.util.JSON;
import com.thedreamsanctuary.dreamguest.util.MessageFormatter;

public class DreamGuest extends JavaPlugin{
	private final VanishFakeQuitHandler vanishHandler = new VanishFakeQuitHandler(this);
	private final AfkHandler afkHandler = new AfkHandler(this);
	private static PermissionManager pex;
	private static Permission perm;
	public void onEnable(){
		this.saveDefaultConfig();
		if(!JSON.createFile("bans")){
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		this.getCommand("who").setExecutor(new Who(this));
		this.getCommand("ban").setExecutor(new Ban(this));
		this.getCommand("unban").setExecutor(new Unban(this));
		this.getCommand("kick").setExecutor(new Kick(this));
		this.getServer().getPluginManager().registerEvents(new ConnectionEventListener(this), this);
		perm = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class).getProvider();
	}
	
	public void onDisable(){
		
	}
	
	public PermissionManager getPermissionManager(){
		return pex;
	}
	
	public boolean isVanished(Player player){
		return vanishHandler.isVanished(player);
	}
	
	public boolean isFakeQuit(Player player){
		return vanishHandler.isFakeQuit(player);
	}
	
	public boolean isAFK(Player player){
		return afkHandler.isAfk(player);
	}
	public int getFakeQuitSize(){
		return vanishHandler.getFakeQuitSize();
	}
	
	public Permission getPerm(){
		return perm;
	}

}
