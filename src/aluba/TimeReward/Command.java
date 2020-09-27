package aluba.TimeReward;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

class Commands implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String string, String[] arg) {
		FileConfiguration config = Main.plugin.getConfig();
		if(command.getName().equalsIgnoreCase("ptr")) {
			if(arg.length == 1) {
				if (arg[0].equalsIgnoreCase("reload")) {
					if(sender.hasPermission("playertimereward.reload")) {
						Main.reload();
						Main.plugin.reloadConfig();
						sender.sendMessage(Main.smsg(Main.plugin.getConfig().getString("reload")));
						return true;
					}else {
						sender.sendMessage(Main.smsg(Main.plugin.getConfig().getString("no-permission")));
						return true;
					}
				}else if(arg[0].equalsIgnoreCase("reset")){
					if(sender.hasPermission("playertimereward.reset")){
						for(String uuid : Main.dataConfig.getKeys(false)) {
							Main.dataConfig.set(uuid + ".time", 0);
							Main.dataConfig.set(uuid + ".get" , null);
						}
						Main.dataSave();
						Main.reload();
						sender.sendMessage(Main.smsg(Main.plugin.getConfig().getString("reset")));
						return true;
					}else{
						sender.sendMessage(Main.smsg(Main.plugin.getConfig().getString("no-permission")));
						return true;
					}
				}else if(sender instanceof Player) {
					Player player = (Player) sender;
					
					switch (arg[0]){
						case "time":{
							if(sender.hasPermission("playertimereward.time")) {
							player.sendMessage(Main.msg(player, config.getString("see-time")));
							return true;
							}else {
								player.sendMessage(Main.smsg(config.getString("no-permission")));
								return true;
							}
						}
						case "gui":{
							if(sender.hasPermission("playertimereward.gui")) {
							Gui.openGui(player);
							player.sendMessage(Main.msg(player, config.getString("open-gui")));
							return true;
							}else {
								player.sendMessage(Main.smsg(config.getString("no-permission")));
								return true;
							}
						}
					}							
				}else {
					sender.sendMessage(Main.smsg(config.getString("not-player")));
					return true;
				}
				
				

			}else if(arg.length == 2){
				OfflinePlayer op = Bukkit.getOfflinePlayer(arg[1]);
				if(arg[0].equalsIgnoreCase("gui")) {
					if(sender instanceof Player) {
						if(sender.hasPermission("playertimereward.gui.all")) {
							if(player_not_null(sender , op.getUniqueId() + "")) {
								playerGui.openGui((Player) sender, op.getName() + "");
								sender.sendMessage(Main.smsg(config.getString("open-player-gui")
										.replace("<player>", op.getName())));
								return true;
								
							}else {
								sender.sendMessage(Main.smsg(config.getString("player-is-null").replace("<player>", op.getName())));
								return true;
							}
						}else {
							sender.sendMessage(Main.smsg(config.getString("no-permission")));
							return true;
						}
					}else {
						sender.sendMessage(Main.smsg(config.getString("not-player")));
						return true;
					}
					
				}else if(arg[0].equalsIgnoreCase("time")) {
					if(sender.hasPermission("playertimereward.time.all")) {
						if(player_not_null(sender , op.getUniqueId() +"" )) {
							sender.sendMessage(Main.smsg(config.getString("see-player-time")
									.replace("&", "§")
									.replace("<player>", op.getName())
									.replace("<time>", Main.dataConfig.getString(op.getUniqueId() + ".time"))));
							return true;
						}else {
							sender.sendMessage(Main.smsg(config.getString("player-is-null").replace("<player>", op.getName())));
							return true;
						}
					}else {
						sender.sendMessage(Main.smsg(config.getString("no-permission")));
						return true;
					}
				}
			}
		}
		sender.sendMessage(Main.smsg(config.getString("command-error")));
		return false;
	}
	
	public boolean player_not_null(CommandSender player , String s) {
		FileConfiguration data = Main.dataConfig;
		
		if(data.getKeys(false).contains(s)) {
			return true;
		}
		return false;
	}
}