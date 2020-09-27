package aluba.TimeReward;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.*;
import net.md_5.bungee.api.*;
import java.util.regex.*;
import org.bukkit.*;
import me.clip.placeholderapi.*;

public class Formatter
{
    public static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String format(String msg) {
        if (Bukkit.getVersion().contains("1.16")) {
            Matcher match = pattern.matcher(msg);
            while (match.find()) {
                String color = msg.substring(match.start(), match.end());
                msg = msg.replace(color, ChatColor.of(color) + "");
                match = pattern.matcher(msg);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String removeColorCodes(String msg) {
        StringBuilder sb = new StringBuilder(msg.length());
        char[] chars =  msg.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            if (chars[i] == '&' || chars[i] == org.bukkit.ChatColor.COLOR_CHAR) {
                ++i;
                continue;
            }
            if (chars[i] == '#') {
                i += 6;
                continue;
            }
            sb.append(chars[i]);
        }
        return sb.toString();
    }
}
