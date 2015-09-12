package com.thedreamsanctuary.dreamguest.admin.command;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.CommandHandler;
import com.thedreamsanctuary.dreamguest.Module;
import com.thedreamsanctuary.dreamguest.util.MessageFormatter;

public class KickCommand extends CommandHandler {
	public KickCommand(Module m) {
		super(m);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			return false;
		}
		String target = args[0];
		String reason = pl.getConfig().getString("default-reason");
		if (args.length > 1) {
			reason = StringUtils.join(args, " ");
		}
		Player player = Bukkit.getPlayer(target);
		if (player == null) {
			sender.sendMessage(ChatColor.RED + "Player not found");
			return true;
		}
		player.kickPlayer(reason);
		Bukkit.broadcastMessage(MessageFormatter.formatKickBanMessage(pl.getConfig().getString("admin-kick-message"), sender.getName(), player.getDisplayName(), reason));
		return true;
	}

}
