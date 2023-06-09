package goldenshadow.decorations.commands;

import goldenshadow.decorations.Decorations;
import goldenshadow.decorations.data.DecorationManager;
import goldenshadow.decorations.io.FileManager;
import goldenshadow.decorations.util.ChatMessageFactory;
import goldenshadow.decorations.util.GuiManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, String[] args) {

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("list")) {
                DecorationManager.getInfo(sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("gui")) {
                if (sender instanceof Player p) {
                    p.openInventory(GuiManager.getInventory(0));
                    return true;
                }
                sender.sendMessage(ChatMessageFactory.buildErrorMessage("This command must be run by a player!"));
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                FileManager.loadFromFiles();
                sender.sendMessage(ChatMessageFactory.buildInfoMessage("Files reloaded!"));
                return true;
            }
            if (args[0].equalsIgnoreCase("undo")) {
                if (sender instanceof Player p) {
                    DecorationManager.undo(p);
                    return true;
                }
                sender.sendMessage(ChatMessageFactory.buildErrorMessage("This command must be run by a player!"));
                return true;
            }
            if (args[0].equalsIgnoreCase("save")) {
                if (sender instanceof Player player) {
                    if (args.length == 3) {
                        if (isValidRadius(args[2])) {
                            if (DecorationManager.saveDecoration(args[1], null, player.getName(), player.getLocation() ,Double.parseDouble(args[2]))) {
                                player.sendMessage(ChatMessageFactory.buildInfoMessage("Saved new decoration as " + args[1].toLowerCase() + "!"));
                                return true;
                            }
                            player.sendMessage(ChatMessageFactory.buildErrorMessage("No valid entities nearby!"));
                            return true;
                        }
                        player.sendMessage(ChatMessageFactory.buildErrorMessage("The given radius either wasn't a number or too large! (Max radius: " + Decorations.getPlugin().getConfig().getDouble("max-radius-size") + ")"));
                        return true;
                    }
                    if (args.length >= 4) {
                        if (isValidRadius(args[2])) {
                            StringBuilder builder = new StringBuilder();
                            for (int i = 3; i < args.length; i++) {
                                if (i != 3) builder.append(" ");
                                builder.append(args[i]);
                            }

                            if (DecorationManager.saveDecoration(args[1], builder.toString(), player.getName(), player.getLocation() ,Double.parseDouble(args[2]))) {
                                player.sendMessage(ChatMessageFactory.buildInfoMessage("Saved new decoration as " + args[1].toLowerCase() + "!"));
                                return true;
                            }
                            player.sendMessage(ChatMessageFactory.buildErrorMessage("No valid entities nearby!"));
                            return true;
                        }
                        player.sendMessage(ChatMessageFactory.buildErrorMessage("The given radius either wasn't a number or too large! (Max radius: " + Decorations.getPlugin().getConfig().getDouble("max-radius-size") + ")"));
                        return true;
                    }
                    player.sendMessage(ChatMessageFactory.buildUsageMessage("/decoration save <name> <radius> <optional: description>"));
                    return true;
                }
                sender.sendMessage(ChatMessageFactory.buildErrorMessage("This command must be run by a player!"));
                return true;
            }
            if (args[0].equalsIgnoreCase("place")) {
                if (args.length == 2) {
                    if (DecorationManager.containsKey(args[1])) {
                        if (sender instanceof Player p) {
                            DecorationManager.placeDecoration(args[1], p.getLocation(), p.getUniqueId());
                            sender.sendMessage(ChatMessageFactory.buildInfoMessage("Successfully placed decoration: " + args[1] + "!"));
                            sender.sendMessage(ChatColor.GRAY + "(Run " + ChatColor.DARK_GRAY + "/decorations undo " + ChatColor.GRAY + "to undo)");
                            return true;
                        }
                        if (sender instanceof BlockCommandSender) {
                            DecorationManager.placeDecoration(args[1], ((BlockCommandSender) sender).getBlock().getLocation(), null);
                            return true;
                        }
                        sender.sendMessage(ChatMessageFactory.buildErrorMessage("This command must be run by a player or command block!"));
                        return true;
                    }
                    sender.sendMessage(ChatMessageFactory.buildErrorMessage("No decoration of that name exists!"));
                    return true;
                }
                sender.sendMessage(ChatMessageFactory.buildUsageMessage("/decoration place <name>"));
                return true;
            }
            if (args[0].equalsIgnoreCase("delete")) {
                if (args.length == 2) {
                    if (DecorationManager.containsKey(args[1])) {
                        DecorationManager.deleteDecoration(args[1]);
                        sender.sendMessage(ChatMessageFactory.buildInfoMessage("Permanently deleted decoration " + args[1] + "!"));
                        return true;
                    }
                    sender.sendMessage(ChatMessageFactory.buildErrorMessage("No decoration of that name exists!"));
                    return true;
                }
                sender.sendMessage(ChatMessageFactory.buildUsageMessage("/decoration delete <name>"));
                return true;
            }
            if (args[0].equalsIgnoreCase("preview_radius")) {
                if (args.length == 2) {
                    if (sender instanceof Player) {
                        if (isValidRadius(args[1])) {
                            DecorationManager.previewRadius(((Player) sender).getLocation(), Double.parseDouble(args[1]));
                            sender.sendMessage(ChatMessageFactory.buildInfoMessage("These are the entities that would be saved with this radius!"));
                            return true;
                        }
                        sender.sendMessage(ChatMessageFactory.buildErrorMessage("The given radius either wasn't a number or too large! (Max radius: " + Decorations.getPlugin().getConfig().getDouble("max-radius-size") + ")"));
                        return true;
                    }
                    sender.sendMessage(ChatMessageFactory.buildErrorMessage("This command must be run by a player!"));
                     return true;
                }
                sender.sendMessage(ChatMessageFactory.buildUsageMessage("/decoration preview_radius <radius>"));
                return true;
            }
            if (args[0].equalsIgnoreCase("edit")) {
                if (args.length >= 3) {
                    if (DecorationManager.containsKey(args[1])) {
                        if (args[2].equalsIgnoreCase("display_item")) {
                            if (sender instanceof Player p) {
                                if (p.getInventory().getItemInMainHand().getType() != Material.AIR) {
                                    Objects.requireNonNull(DecorationManager.getDecoration(args[1])).setDisplayItem(p.getInventory().getItemInMainHand().clone());
                                    sender.sendMessage(ChatMessageFactory.buildInfoMessage("Changed display item of " + args[1] + "!"));
                                    return true;
                                }
                                sender.sendMessage(ChatMessageFactory.buildErrorMessage("You must be holding an item to run this command!"));
                                return true;
                            }
                            sender.sendMessage(ChatMessageFactory.buildErrorMessage("This command must be run by a player!"));
                            return true;
                        }
                        if (args[2].equalsIgnoreCase("description")) {
                            if (args.length >= 4) {
                                StringBuilder builder = new StringBuilder();
                                for (int i = 3; i < args.length; i++) {
                                    if (i != 3) builder.append(" ");
                                    builder.append(args[i]);
                                }
                                Objects.requireNonNull(DecorationManager.getDecoration(args[1])).setDescription(builder.toString());
                                sender.sendMessage(ChatMessageFactory.buildInfoMessage("Changed description of " + args[2] + "!"));
                                return true;
                            }
                            sender.sendMessage(ChatMessageFactory.buildUsageMessage("/description edit <name> description <description>"));
                            return true;
                        }
                    }
                    sender.sendMessage(ChatMessageFactory.buildErrorMessage("No decoration of that name exists!"));
                    return true;
                }
                sender.sendMessage(ChatMessageFactory.buildUsageMessage("/decoration edit <display_item/description> ..."));
                return true;
            }
        }
        sender.sendMessage(ChatMessageFactory.buildUsageMessage("/decoration <save/place/list/gui/delete/preview_radius/edit> ..."));
        return true;
    }

    /**
     * Used to check if a string given as the radius is valid
     * @param string The string that should be checked
     * @return True if it is a double and within the size specified in the config, otherwise false
     */
    private boolean isValidRadius(String string) {
        try {
            return Double.parseDouble(string) <= Decorations.getPlugin().getConfig().getDouble("max-radius-size");
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }

}
