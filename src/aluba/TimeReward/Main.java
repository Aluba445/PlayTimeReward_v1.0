package aluba.TimeReward;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.PlaceholderAPI;
 
 
public class Main extends JavaPlugin implements Listener{ //Extending JavaPlugin so that Bukkit knows its the main class...
   
    public static Plugin plugin;
    public static File guif;
	public static FileConfiguration guiConfig;
	public static File dataf;
	public static FileConfiguration dataConfig;
	

    @Override
    public void onEnable() {
    	plugin = this;
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
        	new Papi(this).register();
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {

            Bukkit.getPluginManager().disablePlugin(this);
        }
        Bukkit.getPluginManager().registerEvents(new ClickEvents(), this);
        Bukkit.getPluginManager().registerEvents(new JoinEvents(), this);
        Bukkit.getPluginManager().registerEvents(new OpClickEvents(), this);
        getCommand("ptr").setExecutor(new Commands());
        
        dataRegister();
        saveDefaultConfig();
        GuiRegister();
        
        reloadConfig();
        reload();
        
        auto_delete();
    }
    
    public static void reload() {

		try {
			guiConfig.load(guif);
			dataConfig.load(dataf);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
    
	private void GuiRegister() {
		guif = new File(this.getDataFolder(), "gui.yml");
		guiConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(guif);
		if (!guif.exists())
			saveResource("gui.yml", false);

	}

	public static void GuiSave() {
		try {
			guiConfig.save(guif);
		} catch (IOException ex) {
		}
	}
	
	
	private void dataRegister() {
		dataf = new File(this.getDataFolder(), "data.yml");
		dataConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(dataf);
		if (!dataf.exists())
			saveResource("data.yml", false);

	}

	public static void dataSave() {
		try {
			dataConfig.save(dataf);
		} catch (IOException ex) {
		}
	}
	
	public static String papi(Player player , String s) {
		
		return s = Formatter.format(PlaceholderAPI.setPlaceholders(player, s).replace("&", "§"));
	} 
	
	public static String msg(Player player , String s) {

		return Formatter.format(plugin.getConfig().getString("prefix").replace("&", "§") + PlaceholderAPI.setPlaceholders(player, s).replace("&", "§") );
	}
	
	public static String smsg(String s) {

		return Formatter.format(plugin.getConfig().getString("prefix") + s);
	}
	
	public void auto_delete(){
		SimpleDateFormat sdFormat = new SimpleDateFormat("HH-mm-ss");
		Date date = new Date();
		
		Bukkit.getScheduler().runTaskTimer(Main.plugin,() -> {

			if(sdFormat.format(new Date()).equalsIgnoreCase(guiConfig.getString("auto-delete"))) {
				for(String uuid : dataConfig.getKeys(false)) {
					dataConfig.set(uuid + ".time", 0);
					dataConfig.set(uuid + ".get" , null);
				}
				dataSave();
				reload();
			}
			
		},20 , 20);
	}
	
	
}