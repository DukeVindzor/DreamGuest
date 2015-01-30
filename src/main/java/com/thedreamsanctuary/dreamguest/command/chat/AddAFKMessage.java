package com.thedreamsanctuary.dreamguest.command.chat;

import java.io.IOException;
import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.command.CommandHandler;
import com.thedreamsanctuary.dreamguest.util.Text;

public class AddAFKMessage extends CommandHandler{

	public AddAFKMessage(DreamGuest pl) {
		super(pl);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length < 1){
			return false;
		}
		//only execute if random afk messages are enabled
		if(!pl.getConfig().getBoolean("random-afk-messages")){
			sender.sendMessage(ChatColor.RED + "Sorry, random afk messages have to be enabled to use this feature.");
			return true;
		}
		String message = "";
		for(int i = 0 ; i < args.length ; i++){
			message += args[i] + " ";
		}
		//parse given message
		ArrayList<String> afkMessages = Text.parseFile("afk-messages");
		//check if afk message exists in message list
		if(afkMessages.contains(message)){
			sender.sendMessage(ChatColor.DARK_AQUA + "That AFK message already exists!");
			return true;
		}
		afkMessages.add(message);
		//write updated AFK message ArrayList to file
		try {
			Text.writeTextToFile("afk-messages", afkMessages);
		} catch (IOException e) {
			sender.sendMessage(ChatColor.RED + "There was an error writing to file. Please contact an Administrator.");
			e.printStackTrace();
			return true;
		}
		sender.sendMessage(ChatColor.DARK_AQUA + "Your AFK message has been added.");
		return true;
	}

}
