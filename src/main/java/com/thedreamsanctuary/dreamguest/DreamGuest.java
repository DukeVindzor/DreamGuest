package com.thedreamsanctuary.dreamguest;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.thedreamsanctuary.dreamguest.admin.AdminModule;
import com.thedreamsanctuary.dreamguest.admin.handlers.VanishFakeQuitHandler;
import com.thedreamsanctuary.dreamguest.border.BorderModule;
import com.thedreamsanctuary.dreamguest.chat.ChatModule;
import com.thedreamsanctuary.dreamguest.metrics.MetricsLite;
import com.thedreamsanctuary.dreamguest.util.JSON;
import com.thedreamsanctuary.dreamguest.util.Text;

public class DreamGuest extends JavaPlugin{
	public static PermissionManager pex;
	private List<Module> modules = new ArrayList<Module>();
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
		
		modules.add(new AdminModule(this));
		modules.add(new ChatModule(this));
		modules.add(new BorderModule(this));
		
		
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
		for(Module m : modules){
			m.disable();
		}
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
