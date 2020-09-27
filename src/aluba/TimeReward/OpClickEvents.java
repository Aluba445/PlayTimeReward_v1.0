package aluba.TimeReward;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OpClickEvents implements Listener {
	@EventHandler
	public void click(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		FileConfiguration config = Main.guiConfig;
		int solt = event.getSlot();
		if(event.getView().getTitle().equalsIgnoreCase(playerGui.Map.get(player.getName()))) {
			if(config.getBoolean("item." + solt + ".close-gui")) {
				player.closeInventory();
				player.sendMessage(Main.msg(player, Main.plugin.getConfig().getString("close-gui")));
			}
			event.setCancelled(true);
		}
	}
}
