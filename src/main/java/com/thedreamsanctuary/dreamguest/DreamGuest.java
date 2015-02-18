package com.thedreamsanctuary.dreamguest;


import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.thedreamsanctuary.dreamguest.admin.command.Ban;
import com.thedreamsanctuary.dreamguest.admin.command.BanReason;
import com.thedreamsanctuary.dreamguest.admin.command.Kick;
import com.thedreamsanctuary.dreamguest.admin.command.Unban;
import com.thedreamsanctuary.dreamguest.admin.command.Vanish;
import com.thedreamsanctuary.dreamguest.admin.handlers.VanishFakeQuitHandler;
import com.thedreamsanctuary.dreamguest.admin.listeners.AdminConnectionEventListener;
import com.thedreamsanctuary.dreamguest.admin.listeners.AdminPlayerEventListener;
import com.thedreamsanctuary.dreamguest.chat.command.AFK;
import com.thedreamsanctuary.dreamguest.chat.command.AddAFKMessage;
import com.thedreamsanctuary.dreamguest.chat.command.Who;
import com.thedreamsanctuary.dreamguest.chat.listeners.ChatConnectionEventListener;
import com.thedreamsanctuary.dreamguest.chat.listeners.ChatPlayerEventListener;
import com.thedreamsanctuary.dreamguest.metrics.MetricsLite;
import com.thedreamsanctuary.dreamguest.util.JSON;
import com.thedreamsanctuary.dreamguest.util.Text;

public class DreamGuest extends JavaPlugin{
	public static PermissionManager pex;
	public void onEnable(){
		this.saveDefaultConfig();
		//initialize PEX Manager
		DreamGuest.pex = PermissionsEx.getPermissionManager();
		if(!JSON.createFile("bans")){
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		if(this.getConfig().getBoolean("random-afk-messages")&&!Text.createFile("afk-messages")){
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		this.getCommand("who").setExecutor(new Who(this));
		this.getCommand("ban").setExecutor(new Ban(this));
		this.getCommand("unban").setExecutor(new Unban(this));
		this.getCommand("banreason").setExecutor(new BanReason(this));
		this.getCommand("kick").setExecutor(new Kick(this));
		this.getCommand("afk").setExecutor(new AFK(this));
		this.getCommand("addafkmessage").setExecutor(new AddAFKMessage(this));
		this.getCommand("vanish").setExecutor(new Vanish(this));
		this.getServer().getPluginManager().registerEvents(new ChatConnectionEventListener(this), this);
		this.getServer().getPluginManager().registerEvents(new AdminConnectionEventListener(), this);
		this.getServer().getPluginManager().registerEvents(new ChatPlayerEventListener(), this);
		this.getServer().getPluginManager().registerEvents(new AdminPlayerEventListener(), this);
		if(getConfig().getBoolean("collect-metrics")){
			try {
		        MetricsLite metrics = new MetricsLite(this);
		        System.out.println(metrics.start());
		        System.out.println("[DreamGuest] Logging enabled");
		    } catch (IOException e) {
		        Bukkit.getLogger().log(Level.SEVERE, "Failed to link to metrics service, disabling metrics.");
		    }
		}	
	}
	
	public void onDisable(){
		
	}
	
	public PermissionManager getPermissionManager(){
		return pex;
	}
	
	public boolean isVanished(Player player){
		return VanishFakeQuitHandler.isVanished(player);
	}
	
	public boolean isFakeQuit(Player player){
		return VanishFakeQuitHandler.isFakeQuit(player);
	}

	public int getFakeQuitSize(){
		return VanishFakeQuitHandler.getFakeQuitSize();
	}
}
