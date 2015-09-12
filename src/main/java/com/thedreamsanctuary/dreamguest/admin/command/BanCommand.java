package com.thedreamsanctuary.dreamguest.admin.command;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thedreamsanctuary.dreamguest.CommandHandler;
import com.thedreamsanctuary.dreamguest.Module;
import com.thedreamsanctuary.dreamguest.admin.Ban;
import com.thedreamsanctuary.dreamguest.admin.handlers.BanHandler;
import com.thedreamsanctuary.dreamguest.util.MessageFormatter;
import com.thedreamsanctuary.dreamguest.util.UUIDFetcher;

public class BanCommand extends CommandHandler {
	public BanCommand(Module m) {
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
		UUID playerUUID;
		if (player == null) {
			playerUUID = UUIDFetcher.getUUIDOf(target);
			if (playerUUID == null) {
				sender.sendMessage(ChatColor.RED + "Player could not be found");
				return true;
			}
		}
		else {
			target = player.getName();
			playerUUID = player.getUniqueId();
		}
		Ban b = new Ban(playerUUID, target, sender.getName(), reason);
		if (BanHandler.addBan(b)) {
			if (player != null) {
				player.kickPlayer(reason);
			}
			Bukkit.broadcastMessage(MessageFormatter.formatKickBanMessage(pl.getConfig().getString("admin-ban-message"), sender.getName(), target, reason));
		}
		else {
			sender.sendMessage(ChatColor.RED + "That player is already banned.");
		}
		return true;
	}
}
