package com.thedreamsanctuary.dreamguest.chat.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionGroup;

import com.thedreamsanctuary.dreamguest.chat.ChatModule;
import com.thedreamsanctuary.dreamguest.chat.handlers.AfkHandler;
import com.thedreamsanctuary.dreamguest.chat.handlers.PermissionHandler;
import com.thedreamsanctuary.dreamguest.CommandHandler;

public class Who extends CommandHandler{
	ChatModule m;
	public Who(ChatModule m) {
		super(m);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		boolean canSeeFQ = sender.hasPermission("dreamguest.vanish");
		//create a map of all (for the sender visible) online players sorted into their groups, organised by weight attribute;
		TreeMap<PermissionGroup, List<Player>> groupMap = createGroupMap(canSeeFQ);
		//groupMap = new TreeMap<PermissionGroup, List<Player>>(groupMap);
		sender.sendMessage(ChatColor.DARK_GRAY + "------------------------------");
		//get header (list of groups with amount of (for the sender visible) online players per group)
		sender.sendMessage(getLegend(canSeeFQ));
		
		//iterate through the groupmap
		for(Entry<PermissionGroup, List<Player>> entry : groupMap.entrySet()){
			final PermissionGroup group = entry.getKey();
			final String groupName = group.getName();
			final StringBuilder groupStrBuilder = new StringBuilder();
			//get current group legend symbol
			groupStrBuilder.append(ChatColor.DARK_GRAY).append("[").append(this.getColor(group)).append(this.getGroupChar(groupName)).append(ChatColor.DARK_GRAY).append("] ");
			boolean previous = false; //whether there is a previous entry for this group already appended
			boolean comma = false; //whether a comma has been prepended
			//iterate through players in the current group
			for(Player player : entry.getValue()){
				comma = false;
				//if player is fakequit
				if(m.getAdminConnector().isFakeQuit(player)){
					//if commandsender can't see fakequit people, skip this player
					if(!canSeeFQ){
						continue;
					}
					//if there is a previous entry AND no comma has been prepended yet, prepend comma now
					if(previous && !comma){
						groupStrBuilder.append(ChatColor.GOLD).append(", ");
						comma = true;
					}
					//prepend [FQ] to fakequit player
					groupStrBuilder.append(ChatColor.DARK_GRAY).append("[").append(ChatColor.AQUA).append("FQ").append(ChatColor.DARK_GRAY).append("] ");
				}
				//if there is a previous entry AND no comma has been prepended yet, prepend comma now
				if(previous && !comma){
					groupStrBuilder.append(ChatColor.GOLD).append(", ");
					comma = true;
				}
				//if player is afk, append name in gray, otherwise in white
				if(AfkHandler.isAFK(player)){
					groupStrBuilder.append(ChatColor.GRAY).append(player.getDisplayName());
				}else{
					groupStrBuilder.append(ChatColor.WHITE).append(player.getDisplayName());
				}
				//set previous to true, since now there is already at least on entry in this group
				previous = true;
			}
			//dispatch message
			sender.sendMessage(groupStrBuilder.toString());
		}
		sender.sendMessage(ChatColor.DARK_GRAY + "------------------------------");
		return true;
	}
	
	/**
	 * gets the /who legend / header.
	 * @param canSeeFQ whether to include fakequit players or not
	 * @return a String listing all groups with amount of players and colour code
	 */
	private String getLegend(boolean canSeeFQ){
		final TreeMap<PermissionGroup, Integer> groupCount = new TreeMap<PermissionGroup, Integer>();
		//get a list of all groups and sort them by weight
		TreeSet<PermissionGroup> groups = PermissionHandler.getGroups();
		//iterate through groups
		for(PermissionGroup group : groups){
			groupCount.put(group, 0);
			//iterate through online players
			for(Player player : Bukkit.getOnlinePlayers()){
				//if the player is fakequit, skip if canSeeFQ equals false
				if(m.getAdminConnector().isFakeQuit(player)){
					if(!canSeeFQ){
						continue;
					}
				}
				//check if the current player's primary group is the currently selected group
				if(PermissionHandler.getPlayerGroup(player).equals(group)){
					//if so, increase player count for currently selected group by one
					groupCount.put(group, groupCount.get(group)+1);
				}
			}
		}
		final StringBuilder stringBuilder = new StringBuilder();
		//iterate through sorted grouplist
		for(PermissionGroup group : groupCount.keySet()){
			//get amount of players (including FQ players if canSeeFQ is set to true)
			final int groupSize = groupCount.get(group);
			final char groupChar = this.getGroupChar(group.getName());
			//create group char with group color, player count and brackets
			stringBuilder.append(ChatColor.DARK_GRAY).append("[").append(this.getColor(group)).append(groupChar).append(":").append(groupSize).append(ChatColor.DARK_GRAY).append("] ");
			
		}
		//get total to the user visible online players
		final int onlinePlayers = canSeeFQ ? Bukkit.getOnlinePlayers().size() : Bukkit.getOnlinePlayers().size() - m.getAdminConnector().getFakeQuitSize();
		//append "Overall" character
		stringBuilder.append(ChatColor.DARK_GRAY).append("(").append(ChatColor.WHITE).append("O:").append(onlinePlayers).append(ChatColor.DARK_GRAY).append(")");
		//return finished legend
		return stringBuilder.toString();
	}
	/**
	 * Generates the group char for a group
	 * @param groupName the name of the group as String
	 * @return the char to use for the group
	 */
	private char getGroupChar(final String groupName){
		return groupName.toUpperCase().charAt(0);
	}
	
	/**
	 * Generate the colour to use for a group in the legend
	 * @param group the group to retrieve the colour of
	 * @return the ChatColor String of the group's colour
	 */
	private String getColor(PermissionGroup group){
		//get colour code from group option
		String option = group.getOption("dreamguest.who.colour");
		//convert colour code to ChatColor or use white if no option was present
		String colour = ChatColor.translateAlternateColorCodes('&', ((option==null)?"&f":option));
		return colour;
	}
	
	/**
	 * Create a map of all online players sorted by group
	 * @param canSeeFQ whether to include FakeQuit players or not
	 * @return a sorted TreeMap of groups
	 */
	private TreeMap<PermissionGroup, List<Player>> createGroupMap(final boolean canSeeFQ){
		final TreeMap<PermissionGroup, List<Player>> groupPlayerMap = new TreeMap<PermissionGroup, List<Player>>();
		//iterate through list of all online players
		for(Player player : Bukkit.getOnlinePlayers()){
			//get current player's primary group
			final PermissionGroup group = PermissionHandler.getPlayerGroup(player);
			//check if current main group is already present in group
			if(!groupPlayerMap.containsKey(group)){
				//if not, create new player ArrayList for current group
				final List<Player> newGroupList = new ArrayList<Player>();
				//if current player is fakequit, only include if canSeeFQ is set to true
				if(m.getAdminConnector().isFakeQuit(player)){
					if(!canSeeFQ){
						continue;
					}
				}
				//add player to ArrayList
				newGroupList.add(player);
				//add the ArrayList into the GroupMap
				groupPlayerMap.put(group, newGroupList);
			//group entry already exists
			}else{
				//if current player is fakequit, only include if canSeeFQ is set to true
				if(m.getAdminConnector().isFakeQuit(player)){
					if(!canSeeFQ){
						continue;
					}
				}
				//add player to current group's player list in the GroupMap
				groupPlayerMap.get(group).add(player);
			}
		}
		return groupPlayerMap;
	}

}
