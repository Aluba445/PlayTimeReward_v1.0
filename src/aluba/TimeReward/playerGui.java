package aluba.TimeReward;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class playerGui {

	public static HashMap <String , String> Map = new HashMap<String , String>();
	
	@SuppressWarnings("deprecation")
	public static void openGui(Player player , String playername) {
		FileConfiguration config = Main.guiConfig;
		OfflinePlayer op = Bukkit.getOfflinePlayer(playername);
		String menuName = Main.papi(player, Main.guiConfig.getString("menu-settings.see-name").replace("<see-name>", playername).replace("<see-time>", Main.dataConfig.getInt(player.getUniqueId() + ".time") + ""));
		
		Inventory inv = Bukkit.createInventory((InventoryHolder) null, Main.guiConfig.getInt("menu-settings.rows") * 9, menuName);

		for (String s : config.getConfigurationSection("item").getKeys(false)) {
			inv.setItem(Integer.valueOf(s), getItem(player, s));
		}
		Map.put(player.getName(), menuName);
		player.openInventory(inv);
	}

	public static ItemStack getItem(Player player, String s) {
		
		FileConfiguration config = Main.guiConfig;
		
		if(!Main.dataConfig.getStringList(player.getUniqueId() + ".get").contains(s)) {
			ArrayList<String> a = new ArrayList<String>();
			for (String list : config.getStringList("item." + s + ".lore")) {
				a.add(Main.papi(player, list));
			}
			ItemStack i = new ItemStack(Material.matchMaterial(config.getString("item." + s + ".ID")));
			i.setAmount(config.getInt("item." + s + ".amount"));
	
			ItemMeta im = i.getItemMeta();
			im.setDisplayName(Main.papi(player, config.getString("item." + s + ".name")));
			im.setLore(a);
	
			if (config.getBoolean("item." + s + ".glowing")) {
				im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
				im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
			}
	
			i.setItemMeta(im);
			return i;
		}else {
			ArrayList<String> a = new ArrayList<String>();
			for (String list : config.getStringList("get." + s + ".lore")) {
				a.add(Main.papi(player, list));
			}
			
			ItemStack i = new ItemStack(Material.matchMaterial(config.getString("get." + s + ".ID")));
			i.setAmount(config.getInt("get." + s + ".amount"));
	
			ItemMeta im = i.getItemMeta();
			im.setDisplayName(Main.papi(player, config.getString("get." + s + ".name")));
			im.setLore(a);
	
			if (config.getBoolean("get." + s + ".glowing")) {
				im.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
				im.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
			}
	
			i.setItemMeta(im);
			return i;
		}
		

	}

}
