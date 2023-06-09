package goldenshadow.decorations.data;

import goldenshadow.decorations.Decorations;
import goldenshadow.decorations.io.FileManager;
import goldenshadow.decorations.util.ChatMessageFactory;
import goldenshadow.decorations.util.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Manager class used to manage all decorations
 */
public class DecorationManager {


    private static HashMap<String, Decoration> decorations = new HashMap<>();
    private static final HashMap<UUID, List<Entity>> undoMap = new HashMap<>();

    /**
     * Used to set the map of saved decorations
     * @param newMap The new map that it should be set to
     */
    public static void setHashMap(HashMap<String, Decoration> newMap) {
        decorations = newMap;
        GuiManager.updateInventories();
    }

    /**
     * Used to delete a decoration
     * @param name The name of the decoration that should be deleted
     */
    public static void deleteDecoration(String name) {
        decorations.remove(name);
        FileManager.saveToFiles();
        GuiManager.updateInventories();
    }

    /**
     * Getter for a decoration
     * @param name Name of the decoration that should be gotten
     * @return A saved decoration
     */
    @Nullable
    public static Decoration getDecoration(String name) {
        if (decorations.containsKey(name.toLowerCase())) {
            return decorations.get(name.toLowerCase());
        }
        return null;
    }

    /**
     * Used to place a decoration in the world
     * @param name The name of the decoration that should be placed
     * @param rawLocation The location where it should be placed
     */
    public static void placeDecoration(String name, Location rawLocation, @Nullable UUID placerUUID) {
        Location location = rawLocation.clone();
        if (decorations.containsKey(name.toLowerCase())) {
            decorations.get(name.toLowerCase()).spawn(location, placerUUID);
        }
    }

    /**
     * Used to save a new decoration
     * @param name The name it should be saved under
     * @param description The description it should have
     * @param author The players name who is saving this decoration
     * @param rawLocation The root rawLocation of the template
     * @param radius The radius in which all valid entities should be saved
     * @return True if the decoration was saved, false if there are no valid entities in the radius
     */
    public static boolean saveDecoration(String name, String description, String author , Location rawLocation, double radius) {
        List<DecorationComponent> list = new ArrayList<>();
        Location location = rawLocation.clone();
        assert rawLocation.getWorld() != null;
        for (Entity entity : rawLocation.getWorld().getNearbyEntities(rawLocation, radius,radius,radius)) {
            if (entity instanceof Display || entity instanceof ArmorStand || entity instanceof ItemFrame) {
                list.add(new DecorationComponent(new EntityData(entity), entity.getLocation(), location));
            }
        }
        if (list.isEmpty()) return false;
        decorations.put(name.toLowerCase(), new Decoration(name, description, list, author));
        FileManager.saveToFiles();
        GuiManager.updateInventories();
        return true;
    }

    /**
     * Used to preview what entities would be saved for a given radius
     * @param location The root location
     * @param radius The radius
     */
    public static void previewRadius(Location location, double radius) {
        assert location.getWorld() != null;
        for (Entity entity : location.getWorld().getNearbyEntities(location, radius,radius,radius)) {
            if (entity instanceof Display || entity instanceof ArmorStand || entity instanceof ItemFrame) {
                entity.setGlowing(true);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Decorations.getPlugin(), () -> entity.setGlowing(false), 20L);
            }
        }
    }

    /**
     * Getter for the list of keys
     * @return List of all decoration keys
     */
    public static List<String> getKeys() {
        return new ArrayList<>(decorations.keySet());
    }


    /**
     * Used for the "/d list" command
     * @param sender The CommandSender who should get the info sent into their chat
     */
    public static void getInfo(CommandSender sender) {
        if (decorations.isEmpty()) {
            sender.sendMessage(ChatColor.YELLOW + "There are no currently saved decorations...");
            return;
        }
        for (Decoration decoration : decorations.values()) {
            decoration.printInfo(sender);
        }
    }

    /**
     * Used to check if a string is a key in the list of saved decorations
     * @param key The string that should be checked
     * @return True if it is, false if it is not
     */
    public static boolean containsKey(String key) {
        return decorations.containsKey(key);
    }

    /**
     * Used to undo the last decoration placement
     * @param player The players whose last placement should be undone
     */
    public static void undo(Player player) {
        if (undoMap.containsKey(player.getUniqueId())) {
            for (Entity e : undoMap.get(player.getUniqueId())) {
                e.remove();
            }
            undoMap.remove(player.getUniqueId());
            player.sendMessage(ChatMessageFactory.buildInfoMessage("Removed the last decoration you placed!"));
        } else player.sendMessage(ChatMessageFactory.buildErrorMessage("Nothing to undo!"));
    }

    /**
     * Used to set the undo list of a specific player
     * @param uuid The uuid of the player
     * @param list The list containing the entities
     */
    public static void setUndo(UUID uuid, List<Entity> list) {
        undoMap.put(uuid, list);
    }

}
