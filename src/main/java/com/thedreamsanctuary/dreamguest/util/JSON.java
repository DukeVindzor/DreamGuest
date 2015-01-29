package com.thedreamsanctuary.dreamguest.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSON {
	public static boolean createFile(String file){
		File f = new File(Bukkit.getPluginManager().getPlugin("DreamGuest").getDataFolder(), file + ".json");
		if(f.exists() && !f.isDirectory()){
			return true;
		}
		f.getParentFile().mkdir();
		try{
			f.createNewFile();
			FileWriter filewriter = new FileWriter(Bukkit.getPluginManager().getPlugin("DreamGuest").getDataFolder() + "/" + file + ".json");
			try{
				filewriter.write(new JSONObject().toString());
			}catch (Exception e){
				e.printStackTrace();
				return false;
			}finally{
				filewriter.flush();
				filewriter.close();
			}
			return true;
		}catch (Exception e){
			Bukkit.getLogger().log(Level.SEVERE, "Failed to create file " + file + ".json!");
			return false;
		}
		
	}
	
	public static void writeObjectToFile(String file, JSONObject object){
		try {
			FileWriter filewriter = new FileWriter(Bukkit.getPluginManager().getPlugin("DreamGuest").getDataFolder() + "/" + file + ".json");
			try{
				filewriter.write(object.toString());
			}catch (Exception e){
				e.printStackTrace();
			}finally{
				filewriter.flush();
				filewriter.close();
			}
		} catch (IOException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Failed to write to file " + file + ".json!");
			e.printStackTrace();
		}
		
	}
	
	public static JSONObject parseFile(String file){
		JSONParser parser = new JSONParser();
		 
		Object object = null;
		try {
			object = parser.parse(new FileReader(Bukkit.getPluginManager().getPlugin("DreamGuest").getDataFolder() + "/" + file + ".json"));
		} catch (FileNotFoundException ex) {
			Bukkit.getLogger().log(Level.SEVERE, "Failed to find file " + file + ".json!");
			return null;
		} catch (IOException ex) {
			Bukkit.getLogger().log(Level.SEVERE, "Failed to read file " + file + ".json!");
			return null;
		} catch (ParseException ex) {
			Bukkit.getLogger().log(Level.SEVERE, "Failed to parse file " + file + ".json!");
			return null;
		}
		 
		JSONObject jObject = (JSONObject) object;
		return jObject;
	}
}
