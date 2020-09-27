package aluba.TimeReward;

import org.bukkit.entity.Player;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class Papi extends PlaceholderExpansion {

    private Main plugin;

    public Papi(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier(){
        return "PlayTimeReward";
    }

    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier){

        if(player == null){
            return "";
        }

        switch(identifier) {
        case "time":{
        	return Main.dataConfig.getInt(player.getUniqueId() + ".time") + "";
        }
 
        }
        return null;
    }
}
