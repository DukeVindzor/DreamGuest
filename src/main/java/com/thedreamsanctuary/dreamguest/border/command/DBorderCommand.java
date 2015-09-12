package com.thedreamsanctuary.dreamguest.border.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.Module;
import com.thedreamsanctuary.dreamguest.CommandHandler;

public class DBorderCommand extends CommandHandler implements TabCompleter {
	private HashMap<String, Argument> arguments = new HashMap<String, Argument>();
	private final String usage = "/dborder <create|delete|edit|list> [arguments] ";
	
	public DBorderCommand(Module m) {
		super(m);
		arguments.put("create", new ArgumentCreate());
		arguments.put("delete", new ArgumentDelete());
		arguments.put("edit", new ArgumentEdit());
		arguments.put("list", new ArgumentList());
	}

	public void printUsage(Player player, String usage){
		player.sendMessage(ChatColor.RED + "Usage: " + usage);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("Must be ingame to use!");
			return true;
		}
		Player player = (Player) sender;
		if (args.length < 1||!arguments.containsKey(args[0].toLowerCase())) {
			printUsage(player, usage);
			return true;
		}
		Argument arg = arguments.get(args[0].toLowerCase());
		args = Arrays.copyOfRange(args, 1, args.length);
		if (args.length != arg.getArgLength()) {
			printUsage(player, arg.getUsage());
			return true;
		}
		arg.run(player, args);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		List<String> output = new ArrayList<String>();
		output.addAll(arguments.keySet());
		return output;
	}

}
