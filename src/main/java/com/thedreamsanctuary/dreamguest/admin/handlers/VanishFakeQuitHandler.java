package com.thedreamsanctuary.dreamguest.admin.handlers;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class VanishFakeQuitHandler {
	private static ArrayList<Player> vanished = new ArrayList<Player>();
	private static final String VANISH_SEE_PERMNISSION = "dreamguest.admin.vanish";
	
	/**Check if a player is vanished
	 * 
	 * @param player 		Targeted Player
	 * @return 				True if player is vanished, false if not
	 */
	public static boolean isVanished(Player player) {
		return vanished.contains(player);
	}
	
	/**Vanish the specified player
	 * 
	 * @param player		Targeted player
	 */
	public static void vanishPlayer(Player player) {
		//check if player is already vanished, if not, add him to the vanished list
		if (!vanished.contains(player)) {
			vanished.add(player);
		}
		//iterate through all online players
		for (Player p : Bukkit.getOnlinePlayers() ){
			//if current player does not have the permission to see vanished players, hide the player from them
			if (!p.hasPermission(VANISH_SEE_PERMNISSION)) {
				p.hidePlayer(player);
			}
		}
	}
	
	/**Make a player reappear
	 * 
	 * @param player the player to check
	 */
	public static void unvanishPlayer(Player player) {
		//if player is vanished, remove him from the vanished list
		if (vanished.contains(player)) {
			vanished.remove(player);
		}
		//iterate through all players
		for (Player p : Bukkit.getOnlinePlayers()) {
			//show reappeared player to him
			p.showPlayer(player);
		}
	}
	
	/**Get the list of all currently vanished players
	 * 
	 * @return ArrayList		Containing all vanished players
	 */
	public static ArrayList<Player> getVanished() {
		return vanished;
	}
	
	/**Handle the joining of a new player
	 * 
	 * @param player		 Player that just joined
	 */
	public static void handleJoin(Player player) {
		//check if player is allowed to see vanished players
        if (!player.hasPermission(VANISH_SEE_PERMNISSION)) {
        	//hide all vanished people from joined player if not
        	for (Player p : VanishFakeQuitHandler.getVanished()) {
        		player.hidePlayer(p);
        	}
        }
	}
	
	/**Handle the leaving of a player
	 * 
	 * @param player		 Player that left
	 */
	public static void handleLeave(Player player) {
		//remove the player from the list of vanished people
		unvanishPlayer(player);
	}
}
