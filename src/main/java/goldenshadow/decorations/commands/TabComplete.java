package goldenshadow.decorations.commands;

import goldenshadow.decorations.data.DecorationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter {

    /*
    /d save <name> <radius> <optional: description>
    /d place <name>
    /d list
    /d gui
    /d delete <name>
    /d preview_radius <radius>
    /d edit <name> display_item
    /d edit <name> description <description>
    /d reload
     */

    List<String> arguments = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender.hasPermission("Decorations.admin")){
            List<String> result = new ArrayList<>();
            if (args.length == 1) {
                arguments = new ArrayList<>(Arrays.asList("save", "place", "delete", "list", "gui", "preview_radius", "edit", "reload"));
                for (String a : arguments) {
                    if (a.toLowerCase().startsWith(args[0].toLowerCase()))
                        result.add(a);
                }
                return result;
            }
            if (args.length == 2) {

                if (args[0].equalsIgnoreCase("delete") ||  args[0].equalsIgnoreCase("place") || args[0].equalsIgnoreCase("edit")) {
                    arguments = DecorationManager.getKeys();
                } else if (args[0].equalsIgnoreCase("preview_radius")) {
                    arguments = new ArrayList<>(Collections.singletonList("<radius>"));
                }
                else if (args[0].equalsIgnoreCase("save")) {
                    arguments = new ArrayList<>(Collections.singletonList("<name>"));
                }

                else arguments.clear();
                for (String a : arguments) {
                    if (a.toLowerCase().startsWith(args[1].toLowerCase()))
                        result.add(a);
                }
                return result;
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("save")) {
                    arguments = new ArrayList<>(Collections.singletonList("<radius>"));
                } else if (args[0].equalsIgnoreCase("edit")) {
                    arguments = new ArrayList<>(Arrays.asList("description","display_item"));
                }

                else arguments.clear();

                for (String a : arguments) {
                    if (a.toLowerCase().startsWith(args[2].toLowerCase()))
                        result.add(a);
                }
                return result;
            }
            if (args.length == 4) {

                if (args[0].equalsIgnoreCase("save")) {
                    arguments = new ArrayList<>(Collections.singletonList("<optional: description>"));
                } else if (args[0].equalsIgnoreCase("edit") && (args[2].equalsIgnoreCase("description"))) {
                    arguments = new ArrayList<>(Collections.singletonList("<description>"));
                }

                else arguments.clear();

                for (String a : arguments) {
                    if (a.toLowerCase().startsWith(args[3].toLowerCase()))
                        result.add(a);
                }
                return result;
            }
            if (args.length > 4) {
                arguments.clear();
                return arguments;
            }
        }
        return null;
    }
}
