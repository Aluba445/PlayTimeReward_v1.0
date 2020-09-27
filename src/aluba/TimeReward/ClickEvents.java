package aluba.TimeReward;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ClickEvents implements Listener {

	
	
	@EventHandler
	public void click(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		FileConfiguration config = Main.guiConfig;
		int solt = event.getSlot();
		if(event.getView().getTitle().equalsIgnoreCase(playerGui.Map.get(player.getName()))) event.setCancelled(true);
		if (event.getView().getTitle().equalsIgnoreCase(Main.papi(player , config.getString("menu-settings.name")))) {
			if (event.getRawSlot() == event.getSlot()) {
				if (config.getBoolean("item." + solt + ".reward")) {
					if (event.getView().getItem(solt).getType() != Material.AIR) {
						if (permission(player , solt+"")) {
							if(getnull(player , event.getView().getBottomInventory()) > config.getInt("item." + solt + ".Full")) {
								if(Main.dataConfig.getInt(player.getUniqueId() + ".time") >= config.getInt("item." + solt + ".time")) {
									if(!Main.dataConfig.getStringList(player.getUniqueId() + ".get").contains(solt+"")) {
										for(String s : config.getStringList("item." + solt + ".command")) {
											Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Main.papi(player, s));
										}
										player.sendMessage(Main.msg(player, Main.plugin.getConfig().getString("give-kit")));
										getReward(player , solt);
										event.setCurrentItem(getItem(player , solt + ""));
									}else {
										player.sendMessage(Main.msg(player, Main.plugin.getConfig().getString("receive")));
									}
								}else {
									player.sendMessage(Main.msg(player, Main.plugin.getConfig().getString("not-time-out")));
								}
							}else {
								player.sendMessage(Main.msg(player, Main.plugin.getConfig().getString("No-space").replace("<space>", Main.dataConfig.getInt("item." + solt + ".Full") + "")));
							}
						}else {
							player.sendMessage(Main.msg(player, Main.plugin.getConfig().getString("no-kit-permission")));
						}
					}
				}
				if(config.getBoolean("item." + solt + ".close-gui")) {
					player.closeInventory();
					player.sendMessage(Main.msg(player, Main.plugin.getConfig().getString("close-gui")));
				}
			}
			event.setCancelled(true);
		}
	}
	public ItemStack getItem(Player player , String str){
		FileConfiguration config = Main.guiConfig;
		ArrayList<String> a = new ArrayList<String>();
		for (String list : config.getStringList("get." + str + ".lore")) {
			a.add(Main.papi(player, list));
		}

		ItemStack i = new ItemStack(Material.matchMaterial(config.getString("get." + str + ".ID")));
		i.setAmount(config.getInt("get." + str + ".amount"));

		ItemMeta im = i.getItemMeta();
		im.setDisplayName(Main.papi(player, config.getString("get." + str + ".name")));
		im.setLore(a);

		if (config.getBoolean("get." + str + ".glowing")) {
			im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
			im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
		}

		i.setItemMeta(im);
		return i;
	}

	public boolean permission (Player player , String string){
		String permission = Main.guiConfig.getString("item." + string + ".permission");
		if(permission != null){
			if(player.hasPermission(permission)){
				return true;
			}
		}
		return false;
	}

	public void getReward(Player player , Integer i) {
		
		List<String> list = Main.dataConfig.getStringList(player.getUniqueId() + ".get");
		
		list.add(i+"");
		Main.dataConfig.set(player.getUniqueId() + ".get", list);
		Main.dataSave();

	}

	public int getnull(Player player , Inventory inv) {
		int ni = 0;
		
		for(ItemStack s : inv.getStorageContents().clone()) {
			if(s == null) {
				ni++;
			}
		}
		return ni;
	}

}