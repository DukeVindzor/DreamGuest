<h1>DreamGuest</h1>

<h2>Dependencies</h2>
<b>PermissionsEx</b><br>
This plugin uses PEX functions that Vault does not provide. I will try to move this dependency over to Vault in the future.

<h2>Commands</h2>

<h3>/who</h3>
<b>Permission node:</b> <code>dreamguest.chat.who</code> <br>
<b>Usage: </b> /who <br>
<b>Description: </b> lists all currently online players, sorted by group. <br>

<h3>/kick</h3>
<b>Permission node:</b> <code>dreamguest.admin.kick</code> <br>
<b>Usage: </b> /kick &lt;target&gt; [reason] <br>
<b>Description: </b> kicks the targeted player from the server. If no reason is provided, the default reason is used (from config.yml) <br>

<h3>/ban</h3>
<b>Permission node:</b> <code>dreamguest.admin.ban</code> <br>
<b>Usage: </b> /ban &lt;target&gt; [reason] <br>
<b>Description: </b> bans the targeted player from the server. If no reason is provided, the default reason is used (from config.yml). &lt;target&gt; can be either the name of the player or his UUID. If a player is offline, provide the exact player name for him to be banned. Uses default reason if none is provided.
 
 <h3>/unban</h3>
 <b>Permission node:</b> <code>dreamguest.admin.unban</code> <br>
 <b>Usage: </b> /unban <target> <br>
 <b>Description: </b> unbans the targeted player. &lt;target&gt; can be either name or UUID, name must be exact.<br>

<h2>Configuration</h2>
The default configuration file:
<pre>
# List of Placeholders:
# ${no} amount of players
# ${no-1} amount of players minus 1
# ${n} player name
# ${reason} kick / ban reason
# ${admin} source of kick / ban
# --------------------------------------------
# JOIN / LEAVE MANAGER
# accepted placeholders : ${no}, ${no-1}, ${n} 
# --------------------------------------------

join-message: §8(§7${no}§8) §f${n}§7 joined
leave-message: §8(§7${no-1}§8) §f${n}§7 left
#only used if player randomly loses connection, for /kick see "KICK / BAN MANAGER"
kick-message: §8(§7${no}§8) §f${n}§4 was kicked out

# --------------------------------------------
# KICK / BAN MANAGER
# accepted placeholders : ${n}, ${reason}, ${admin}
# --------------------------------------------
admin-kick-message: "§8Player §c${n}§8 has been kicked by §c${admin}§8 for: §9${reason}"
admin-ban-message: "§8Player §c${n}§8 has been banned by §c${admin}§8 for: §9${reason}"
admin-unban-message: "§8Player §c${n}§8 has been unbanned by §c${admin}"
default-reason: asshat
</pre>

