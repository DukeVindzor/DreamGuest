package com.thedreamsanctuary.dreamguest.admin.handlers;

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

import com.google.gson.Gson;
import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.admin.Ban;

public class BanHandler {
	public static final File BANS_FILE = new File("plugins/DreamGuest/bans.json");
	private static Set<Ban> bans = new HashSet<Ban>();
	private static DreamGuest plugin;
	
	/**Initiates the handler and import banned players from file
	 * 
	 * @param pl 	DreamGuest plugin
	 */
	public static void init(DreamGuest pl) {
		plugin = pl;
		final File banFolder = new File("plugins/DreamGuest/");
		if (!banFolder.exists()) {
			banFolder.mkdirs();
		}
		readBans(BANS_FILE);
	}
	
	/**Adds the ban to both file and plugin storage if ban does not already exist
	 * 
	 * @param ban 		Ban of a player
	 * @return 			True if ban was successfully added, false otherwise
	 */
	public static boolean addBan(Ban ban) {
		for (Ban savedBan : bans) {
			if (ban.getUUID().equals(savedBan.getUUID())) {
				return false;
			}
		}
		if (bans.add(ban)) {
			saveBans(BANS_FILE);
			return true;
		}
		return false;
	}
	
	/**Gives you the ban of a player
	 * 
	 * @param playerUUID 		UUID of a player
	 * @return 					Ban of the player, null if not found
	 */
	public static Ban getBan(UUID playerUUID) {
		for (Ban b : bans) {
            if (b.getUUID().equals(playerUUID)) {
                return b;
            }
        }
        return null;
	}
	
	/**Gives you the ban of a player
	 * 
	 * @param name 		Name of a player
	 * @return 			Ban of the player, null if not found
	 */
	public static Ban getBan(String name) {
		for (Ban b : bans) {
			if (b.getName().equalsIgnoreCase(name)) {
				return b;
			}
		}
		return null;
	}
	
	/**Removes ban
	 * 
	 * @param oldBan 		Ban of a player
	 * @return 				True if removal was successful, false otherwise
	 */
	public static boolean removeBan(Ban oldBan) {
        return bans.remove(oldBan);
    }

	/**Read bans from Json file
	 * 
	 * @param banFile 		File containing bans
	 */
	public static void readBans(File banFile) {
        Gson gson = new Gson();
        if (banFile.exists()) {
            Scanner scan;
            try {
                scan = new Scanner(banFile);
            }
            catch (Exception e) {
                plugin.getLogger().severe("Can not open config files");
                e.printStackTrace();
                return;
            }
            try {
                while (scan.hasNextLine()) {
                    Ban ban = gson.fromJson(scan.nextLine(), Ban.class);
                    addBan(ban);
                }
            }
            catch (final Exception e) {
                plugin.getLogger().severe("Can not read config files");
                e.printStackTrace();
                return;
            }
            finally {
                scan.close();
            }
        }
    }
	
	/**Save bans to Json file
	 * 
	 * @param banFile 		File you wish to save bans to
	 */
    public static void saveBans(File banfile) {
        Gson gson = new Gson();
        if (!banfile.exists()) {
            try {
                banfile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        else {
            if (!banfile.delete()) {
                plugin.getLogger().severe("Can not save config files");
            }
            try {
                banfile.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        PrintWriter pw;
        try {
            pw = new PrintWriter(banfile);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        for (Ban b : bans) {
                pw.println(gson.toJson(b));
        }
        pw.close();
    }
    
	/**Check whether a player is banned or not
	 * 
	 * @param playerUUID 		UUID of the player to check
	 * @return 					True if player is banned, false if not
	 */
	public static boolean isPlayerBanned(UUID playerUUID) {
		if (getBan(playerUUID) != null) {
			return true;
		}
		return false;
	}
	
	/**Retrieve a player's ban reason
	 * 
	 * @param playerUUID 		UUID of the player to check
	 * @return 					The ban reason specified in bans.json
	 */
	public static String getPlayerBanreason(UUID playerUUID) {
		Ban b = getBan(playerUUID);
		if (b == null) {
			return "";
		}
		return b.getReason();
	}
	
	/**Unbans a player
	 * 
	 * @param playerUUID 		UUID of the player to ban
	 * @return 					True if unban was successful, false otherwise
	 */
	public static boolean unbanPlayer(UUID playerUUID) {
		//check if player is banned, if he isn't, return NOT_BANNED
		if (!isPlayerBanned(playerUUID)) {
			return false;
		}
		removeBan(getBan(playerUUID));
		saveBans(BANS_FILE);
		return true;
	}
	
	/**Get a banned player's name
	 * 
 	 * @param playerUUID 		UUID of the targeted player
	 * @return 					The name the player had at the time of ban
	 */
	public static String getBannedPlayerName(UUID playerUUID) {
		//check if player is banned, if not, return an empty String
		if (!isPlayerBanned(playerUUID)) {
			return "";
		}
		return getBan(playerUUID).getName();
	}
	
	/**Get a list of all banned players' names
	 * 
	 * @param start 		Beginning of players name
	 * @return 				A List<String> containing all the banned player names beginning their name with start
	 */
	public static List<String> getBannedPlayers(String start) {
		List<Ban> banned = new ArrayList<Ban>();
		for (Ban b : bans) {
			String banName = b.getName();
			if (banName.toLowerCase().startsWith(start.toLowerCase())) {
				banned.add(b);
			}
		}
		Collections.sort(banned);
		List<String> bannedNames = new ArrayList<String>();
		for (Ban b : banned) {
			bannedNames.add(b.getName());
		}
		return bannedNames;
	}
	
}
