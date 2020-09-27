package aluba.TimeReward;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.Date;

public class JoinEvents implements Listener {
	

//	@EventHandler
//	public void Join(PlayerJoinEvent event) {
//		Player player = event.getPlayer();
//		FileConfiguration data = Main.dataConfig;
//		data.set(player.getUniqueId() + ".name", player.getName());
//
//		if(data.getInt(player.getUniqueId() + ".time") == 0) data.set(player.getUniqueId() + ".time", 0);
//		//if(data.getStringList(player.getUniqueId() + ".get").isEmpty()) data.set(player.getUniqueId() + ".get","");
//		Main.dataSave();
//
//		BukkitTask task = Bukkit.getScheduler().runTaskTimer(Main.plugin,() -> {
//			Date date = new Date();
//			if(player.isOnline()) {
//			data.set(player.getUniqueId() + ".time", ((int) data.getInt(player.getUniqueId() + ".time") + 1));
//			Main.dataSave();
//			Bukkit.broadcastMessage(date.toLocaleString() + "");
//			}
//		},0 , 1200);
//
//		Bukkit.getScheduler().runTaskTimer(Main.plugin,() -> {
//			if(!player.isOnline()) {
//				task.cancel();
//			}
//		},20 , 20);
//
//	}
	
}
 