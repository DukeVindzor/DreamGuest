package com.thedreamsanctuary.dreamguest.handlers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.json.simple.JSONObject;

import com.thedreamsanctuary.dreamguest.util.BanResult;
import com.thedreamsanctuary.dreamguest.util.JSON;

public class BanHandler {
	
	public static boolean isPlayerBanned(UUID playerUUID){
		JSONObject banlist = JSON.parseFile("bans");
		if(banlist.containsKey(playerUUID.toString())){
			return true;
		}
		return false;
	}
	
	public static String getPlayerBanreason(UUID playerUUID){
		JSONObject banlist = JSON.parseFile("bans");
		JSONObject entry = (JSONObject) banlist.get(playerUUID.toString());
		return entry.get("reason").toString();
	}
	
	public static BanResult addPlayer(UUID playerUUID, String reason){
		if(isPlayerBanned(playerUUID)){
			return BanResult.ALREADY_BANNED;
		}
		OfflinePlayer player = Bukkit.getOfflinePlayer(playerUUID);
		String name = player.getName();
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		obj.put("reason", reason);
		JSONObject banlist = JSON.parseFile("bans");
		if(banlist == null){
			return BanResult.ERROR;
		}
		banlist.put(playerUUID, obj);
		JSON.writeObjectToFile("bans", banlist);
		return BanResult.SUCCESS;
	}
	
	public static BanResult unbanPlayer(UUID playerUUID){
		if(!isPlayerBanned(playerUUID)){
			return BanResult.NOT_BANNED;
		}
		JSONObject banlist = JSON.parseFile("bans");
		if(banlist == null){
			return BanResult.ERROR;
		}
		banlist.remove(playerUUID.toString());
		JSON.writeObjectToFile("bans", banlist);
		return BanResult.SUCCESS;
	}
	
	public static String getBannedPlayerName(UUID playerUUID){
		if(!isPlayerBanned(playerUUID)){
			return "";
		}
		JSONObject banlist = JSON.parseFile("bans");
		if(banlist == null){
			return "";
		}
		JSONObject entry = (JSONObject) banlist.get(playerUUID.toString());
		return entry.get("name").toString();
	}
	
}
