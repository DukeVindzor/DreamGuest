package com.thedreamsanctuary.dreamguest.handlers;

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

	
	public static TreeSet<PermissionGroup> getGroups(){
		List<PermissionGroup> unsortedGroups = pex.getGroupList();
		TreeSet<PermissionGroup> groups = new TreeSet<PermissionGroup>(unsortedGroups);
		ArrayList<PermissionGroup> missingGroups = new ArrayList<PermissionGroup>(unsortedGroups);
		Integer minweight = null;
		for(PermissionGroup g : groups){
			if(minweight == null){
				minweight = g.getWeight();
			}else{
				minweight = (g.getWeight() < minweight)? g.getWeight() : minweight;
			}
			missingGroups.remove(g);
		}
		for(PermissionGroup g : missingGroups){
			int temp = minweight-1;
			g.setWeight(temp);
			minweight = temp;
			groups.add(g);
		}
		return groups;
	}
	
	public static PermissionGroup getPlayerGroup(Player player){
		PermissionUser user = pex.getUser(player);
		if (user == null) {
    		return null;
    	} else if (user.getParents(player.getWorld().toString()).size() > 0) {
    		return user.getParents(player.getWorld().toString()).get(0);
    	} else {
    		return null;
    	}
	}
	
}
