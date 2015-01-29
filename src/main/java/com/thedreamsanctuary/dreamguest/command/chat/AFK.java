package com.thedreamsanctuary.dreamguest.command.chat;

import java.util.ArrayList;

import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.DreamGuest;
import com.thedreamsanctuary.dreamguest.command.CommandHandler;
import com.thedreamsanctuary.dreamguest.handlers.AfkHandler;
import com.thedreamsanctuary.dreamguest.util.MessageFormatter;
import com.thedreamsanctuary.dreamguest.util.Text;

public class AFK extends CommandHandler{

	public AFK(DreamGuest pl) {
		super(pl);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("Only players can execute that command");
			return true;
		}
		Player player = (Player) sender;
		String afkmessage = pl.getConfig().getString("default-afk-message");
		if(AfkHandler.toggleAFK(player)){
			if(args.length > 0){
				afkmessage = "";
				for(int i = 0 ; i < args.length ; i++){
					afkmessage += args[i] + " ";
				}
			}else{
				if(pl.getConfig().getBoolean("random-afk-messages")){
					afkmessage = getRandomAFKMessage();
				}
			}
		}else{
			afkmessage = pl.getConfig().getString("default-return-message");
		}
		Bukkit.broadcastMessage(MessageFormatter.formatAFKMessage(pl.getConfig().getString("afk-format"), player, afkmessage));
		return true;
	}
	
	public String getRandomAFKMessage(){
		ArrayList<String> messages = Text.parseFile("afk-messages");
		System.out.println(messages.toString());
		if(messages.size()==0){
			return pl.getConfig().getString("default-afk-message");
		}
		int randomIndex = RandomUtils.nextInt(messages.size());
		return messages.get(randomIndex);
	}
	
}
