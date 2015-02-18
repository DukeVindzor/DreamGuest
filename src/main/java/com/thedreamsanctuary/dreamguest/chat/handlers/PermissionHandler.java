package com.thedreamsanctuary.dreamguest.chat.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;

import com.thedreamsanctuary.dreamguest.DreamGuest;

public class PermissionHandler {
	static final PermissionManager pex = DreamGuest.pex;

	/**
	 * get a sorted list of all PEX groups
	 * @return sorted TreeSet containing all PermissionGroups, sorted by weight
	 */
	public static TreeSet<PermissionGroup> getGroups(){
		//get an unsorted list of all groups
		List<PermissionGroup> unsortedGroups = pex.getGroupList();
		//create a sorted list of all groups
		TreeSet<PermissionGroup> groups = new TreeSet<PermissionGroup>(unsortedGroups);
		//create a list to contain the groups that are missing in the sorted list
		ArrayList<PermissionGroup> missingGroups = new ArrayList<PermissionGroup>(unsortedGroups);
		//minimum registered weight
		Integer minweight = null;
		//iterate through sorted groups
		for(PermissionGroup g : groups){
			//update minimum weight
			if(minweight == null){
				minweight = g.getWeight();
			}else{
				minweight = (g.getWeight() < minweight)? g.getWeight() : minweight;
			}
			//remove group from missing group list
			missingGroups.remove(g);
		}
		//if there are groups left unsorted
		if(missingGroups.size()>0){
			//iterate through missing groups
			for(PermissionGroup g : missingGroups){
				int temp = minweight-1;
				//set current group's weight to one less than the minimum
				g.setWeight(temp);
				minweight = temp;
				//add to sorted list
				groups.add(g);
			}
		}
		
		return groups;
	}
	
	/**
	 * get a player's primary group
	 * @param player targeted player
	 * @return the primary group of a player in the world he is currently in
	 */
	public static PermissionGroup getPlayerGroup(Player player){
		//get PermissionUser of specified player
		PermissionUser user = pex.getUser(player);
		//if no user could be found, return null
		if (user == null) {
    		return null;
    	//if there exists one ore more groups for the player in his current world, return the first one
    	} else if (user.getParents(player.getWorld().toString()).size() > 0) {
    		return user.getParents(player.getWorld().toString()).get(0);
    	//no groups set for player, return null
    	} else {
    		return null;
    	}
	}
	
}
