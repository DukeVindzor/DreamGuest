package com.thedreamsanctuary.dreamguest.border.handlers;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.gson.Gson;
import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.border.Border;

public class BorderHandler {
	public static final File BORDER_FILE = new File("plugins/DreamGuest/borders.json");
	private static Set<Border> borders = new HashSet<Border>();
	private static Set<UUID> worlds = new HashSet<UUID>();
	private static DreamGuest plugin;
	
	/**Initiates the handler and import borders from file
	 * 
	 * @param pl 	DreamGuest plugin
	 */
	public static void init(DreamGuest pl){
		plugin = pl;
		final File borderFolder = new File("plugins/DreamGuest/");
		if (!borderFolder.exists()) {
			borderFolder.mkdirs();
		}
		readBorders(BORDER_FILE);
	}
	
	/**Adds the border to both file and plugin storage if border does not already exist
	 * 
	 * @param border 		Border you  wish to save
	 * @return 				True if border was successfully added, false otherwise
	 */
	public static boolean addBorder(Border border) {
		for (Border savedBorder : borders) {
			if (savedBorder.getName().equalsIgnoreCase(border.getName())) {
				return false;
			}
		}
		worlds.add(border.getWorldID());
		if (borders.add(border)) {
			saveBorders(BORDER_FILE);
			return true;
		}
		return false;
	}
	
	/**Gives border with specified name
	 * 
	 * @param name 			Name of border
	 * @return 				The requested border, null otherwise
	 */
	public static Border getBorder(String name) {
		for (Border b : borders) {
            if (b.getName().equalsIgnoreCase(name)) {
                return b;
            }
        }
        return null;
	}
	
	/**Update which worlds are currently having borders
	 */
	private static void updateActiveWorlds() {
	        worlds.clear();
	        for (Border b : borders) {
	            worlds.add(b.getWorldID());
	        }
	    }
	
	/**Removes a border and updates worlds
	 * 
	 * @param oldBorder 	Border you wish to remove
	 * @return 				True if removal was successful, false otherwise
	 */
	public static boolean removeBorder(Border oldBorder) {
        boolean success = borders.remove(oldBorder);
        updateActiveWorlds();
        return success;
    }
	
	/**Gives you an array of names of all borders
	 * 
	 * @return 				String array with names of borders
	 */
	public static String[] getBorders() {
        List<String> borderTxt = new ArrayList<String>();
        for (Border border : borders) {
            borderTxt.add(border.toColoredString());
        }
        Collections.sort(borderTxt);
        if (borderTxt.isEmpty()) {
        	borderTxt.add(ChatColor.GRAY + "No zones active.");
        }
        return borderTxt.toArray(new String[0]);
    }
	
	/**Checks if player can move from startLoc to endLoc
	 * 
	 * @return 				True if player can pass, false otherwise
	 */
	public static boolean canMoveTo( Player player, Location startLoc, Location endLoc) {
		if (endLoc == null) {
			plugin.getLogger().severe("A move event reported a null destination location!");
	    	return true;
	    }
	    if (endLoc.getWorld() == null) {
	    	plugin.getLogger().severe("A move event reported a null destination world!");
	    	return true;
	    }
	    if (!worlds.contains(endLoc.getWorld().getUID())) {
	    	return true;
	    }
	    
	    boolean canEnter = true;
	    boolean inZone = false;
	    for (Border b : borders) {
	    	if (b.getWorldID().equals(endLoc.getWorld().getUID()) && b.isInside(endLoc)) {	    		
	    		inZone = true;
	    		if (b.isInside(startLoc)) {
	    			if (canEnter) {
	    				continue;
	    			}	
	    		}
	    		if (player.hasPermission(b.getPermission())) {
	    			if (canEnter) {
	    				player.sendMessage(ChatColor.GRAY + "Now crossing border of " + ChatColor.GREEN + b.getName().trim());
	    				continue;
	    			}
	    		}
	    		else {
	    			player.sendMessage(ChatColor.GRAY + "You can not cross the border of " + ChatColor.GREEN + b.getName().trim());
                   	canEnter = false;
                   	break;
	    		}
	    	}
	    }
	    if (inZone) {
	    	return canEnter;
	    }
	    player.sendMessage(ChatColor.GRAY + "You can not access area outside of the the borders");
        return false;
	}
	
	/**Read borders from Json file
	 * 
	 * @param borderFile 		File containing borders
	 */
	public static void readBorders(File borderFile) {
        Gson gson = new Gson();
        if (borderFile.exists()) {
            Scanner scan;
            try {
                scan = new Scanner(borderFile);
            }
            catch (Exception e) {
                plugin.getLogger().severe("Can not open config files");
                e.printStackTrace();
                return;
            }
            try {
                while (scan.hasNextLine()) {
                    Border border = gson.fromJson(scan.nextLine(), Border.class);
                    addBorder(border);
                }
            }
            catch (Exception e) {
                plugin.getLogger().severe("Can not read config files");
                e.printStackTrace();
                return;
            }
            finally {
                scan.close();
            }
        }
    }
	
	/**Save border to Json file
	 * 
	 * @param borderFile 		File you wish to save borders to
	 */
    public static void saveBorders(File borderfile) {
        Gson gson = new Gson();
        if (!borderfile.exists()) {
            try {
                borderfile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        else {
            if (!borderfile.delete()) {
                plugin.getLogger().severe("Can not save config files");
            }
            try {
                borderfile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        PrintWriter pw;
        try {
            pw = new PrintWriter(borderfile);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        for (Border b : borders) {
                pw.println(gson.toJson(b));
        }
        pw.close();
    }
}
