package com.thedreamsanctuary.dreamguest.admin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.Module;
import com.thedreamsanctuary.dreamguest.admin.handlers.VanishFakeQuitHandler;
import com.thedreamsanctuary.dreamguest.CommandHandler;

public class VanishCommand extends CommandHandler {
	static final ChatColor TEXT_COLOR = ChatColor.AQUA;
	static final String VANISH_STRING = TEXT_COLOR + "You have vanished!";
	static final String APPEAR_STRING = TEXT_COLOR + "You have reappeared!";
	static final String VANISH_OTHER_STRING = TEXT_COLOR + "You have vanished the player "+ChatColor.GOLD+"${n}" + TEXT_COLOR + "!";
	static final String APPEAR_OTHER_STRING = TEXT_COLOR + "You have made the player "+ChatColor.GOLD+"${n}" + TEXT_COLOR + " reappear!";
	static final String OTHER_VANISHED_STRING = TEXT_COLOR + "You have been vanished by the player "+ChatColor.GOLD+"${n}" + TEXT_COLOR + "!";
	static final String OTHER_APPEARED_STRING = TEXT_COLOR + "You have been unvanished by the player "+ChatColor.GOLD+"${n}" + TEXT_COLOR + "!";
	public VanishCommand(Module m) {
		super(m);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		if (args.length > 1) {
			return false;
		}
		//if no argument given, target is the player itself
		if (args.length == 0) {
			Player player = (Player) sender;
			//toggle Vanishing
			if (VanishFakeQuitHandler.isVanished(player)) {
				VanishFakeQuitHandler.unvanishPlayer(player);
				player.sendMessage(APPEAR_STRING);
			}
			else {
				VanishFakeQuitHandler.vanishPlayer(player);
				player.sendMessage(VANISH_STRING);
			}
			return true;
		}
		//try to find target player
		Player player = Bukkit.getPlayer(args[0]);
		if (player == null) {
			sender.sendMessage(ChatColor.RED + "Player could not be found.");
			return true;
		}
		//toggle targeted players vanish status
		if (VanishFakeQuitHandler.isVanished(player)) {
			VanishFakeQuitHandler.unvanishPlayer(player);
			sender.sendMessage(APPEAR_OTHER_STRING.replace("${n}", player.getDisplayName()));
			player.sendMessage(OTHER_APPEARED_STRING.replace("${n}", sender.getName()));
		 }
		else {
			VanishFakeQuitHandler.vanishPlayer(player);
			sender.sendMessage(VANISH_OTHER_STRING.replace("${n}", player.getDisplayName()));
			player.sendMessage(OTHER_VANISHED_STRING.replace("${n}", sender.getName()));
		}
		return true;
	}
	
	
}
