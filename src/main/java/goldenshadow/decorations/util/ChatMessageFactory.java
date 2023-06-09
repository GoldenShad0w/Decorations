package goldenshadow.decorations.util;

import org.bukkit.ChatColor;

public class ChatMessageFactory {


    public static String buildUsageMessage(String message) {
        return ChatColor.RED + "[Decorations] Usage: " + message;
    }

    public static String buildErrorMessage(String message) {
        return ChatColor.RED + "[Decorations] Error: " + message;
    }

    public static String buildInfoMessage(String message) {
        return ChatColor.DARK_GREEN + "[Decorations] " + ChatColor.GREEN + message;
    }
}
