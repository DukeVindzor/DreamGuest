package com.thedreamsanctuary.dreamguest.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

import org.bukkit.Bukkit;

public class Text {
	public static boolean createFile(String file){
		File f = new File(Bukkit.getPluginManager().getPlugin("DreamGuest").getDataFolder(),file+".txt");
		if(f.exists() && !f.isDirectory()){
			return true;
		}
		f.getParentFile().mkdir();
		
		try{
			f.createNewFile();
			FileWriter filewriter = new FileWriter(Bukkit.getPluginManager().getPlugin("DreamGuest").getDataFolder() + "/" + file + ".txt");
			try{
				filewriter.write("");
			}catch (Exception e){
				e.printStackTrace();
				return false;
			}finally{
				filewriter.flush();
				filewriter.close();
			}
			return true;
		}catch (Exception e){
			Bukkit.getLogger().log(Level.SEVERE, "Failed to create file " + file + ".txt!");
			return false;
		}
	}
	public static void writeTextToFile(String file, ArrayList<String> text) throws IOException{
			File f = new File(Bukkit.getPluginManager().getPlugin("DreamGuest").getDataFolder(),file+".txt");
			FileOutputStream fos = new FileOutputStream(f);			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			for(String s : text){
				bw.write(s);
				bw.newLine();
			}
			
			bw.close();
			
	}
	public static ArrayList<String> parseFile(String file){
		File f = new File(Bukkit.getPluginManager().getPlugin("DreamGuest").getDataFolder() + "/" + file + ".txt");
		ArrayList<String> output = new ArrayList<String>();
		try {
			Scanner input = new Scanner(f);
			while(input.hasNext()){
				output.add(input.nextLine());
			}
			input.close();
			return output;
		} catch (FileNotFoundException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Failed to read file " + file + ".txt!");
			return null;
		}	
	}
}
