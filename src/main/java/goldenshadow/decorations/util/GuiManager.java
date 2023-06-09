package goldenshadow.decorations.util;

import goldenshadow.decorations.Decorations;
import goldenshadow.decorations.data.Decoration;
import goldenshadow.decorations.data.DecorationManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;


public class GuiManager {

    private static Inventory[] inventories;

    public static void updateInventories() {
        List<String> keyList = DecorationManager.getKeys();
        keyList.sort(String::compareTo);
        int amount = DecorationManager.getKeys().size();
        inventories = new Inventory[(amount / 45) + 1];
        for (int i = 0; i < inventories.length; i++) {
            inventories[i] = createPage(i * 45, Math.min((i * 45) + 46, keyList.size()), i, keyList);
        }
    }

    private static Inventory createPage(int startIndex, int endIndex, int pageIndex, List<String> keyList) {
        if (endIndex - startIndex > 46) throw new RuntimeException("Too many decorations given to single inventory!");
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.BOLD + "Decorations Page " + (pageIndex+1));
        for (int i = 0; i < endIndex - startIndex; i++) {
            inv.setItem(i, getDecorationItem(DecorationManager.getDecoration(keyList.get(startIndex + i))));
        }
        for (int i = 45; i < 54; i++) {
            switch (i) {
                case 46 -> {
                    if (startIndex == 0) inv.setItem(i, getBorderItem());
                    else inv.setItem(i, getPreviousPageItem());
                }
                case 52 -> {
                    if (endIndex == keyList.size()) inv.setItem(i, getBorderItem());
                    else inv.setItem(i, getNextPageItem());
                }
                default -> inv.setItem(i, getBorderItem());
            }
        }
        return inv;
    }


    public static Inventory getInventory(int pageIndex) {
        return inventories[pageIndex];
    }

    private static ItemStack getBorderItem() {
        ItemStack itemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(" ");
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack getNextPageItem() {
        ItemStack itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_GREEN + String.valueOf(ChatColor.BOLD) + "Next Page");
        meta.getPersistentDataContainer().set(new NamespacedKey(Decorations.getPlugin(), "PageSwap"), PersistentDataType.STRING, "next");
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack getPreviousPageItem() {
        ItemStack itemStack = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta meta = itemStack.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_GREEN + String.valueOf(ChatColor.BOLD) + "Previous Page");
        meta.getPersistentDataContainer().set(new NamespacedKey(Decorations.getPlugin(), "PageSwap"), PersistentDataType.STRING, "previous");
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static ItemStack getDecorationItem(Decoration decoration) {
        if (decoration != null) {
            ItemStack itemStack = decoration.getDisplayItem();
            ItemMeta meta = itemStack.getItemMeta();
            assert meta != null;
            meta.setDisplayName(ChatColor.DARK_GREEN + String.valueOf(ChatColor.BOLD) + decoration.getName());
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Creator: " + decoration.getAuthor());
            lore.add(ChatColor.GRAY + "Description:");
            lore.addAll(splitString(decoration.getDescription()));
            lore.add(" ");
            lore.add(ChatColor.GREEN + String.valueOf(ChatColor.BOLD) + "LEFT-CLICK " + ChatColor.RESET + ChatColor.GREEN + "to place");
            meta.addItemFlags(ItemFlag.values());
            meta.setLore(lore);
            meta.getPersistentDataContainer().set(new NamespacedKey(Decorations.getPlugin(), "DecorationName"), PersistentDataType.STRING, decoration.getName());
            itemStack.setItemMeta(meta);
            return itemStack;
        }
        return null;
    }


    private static List<String> splitString(String raw) {
        String[] words = raw.split("\\s+");
        List<String> segments = new ArrayList<>();
        StringBuilder currentSegment = new StringBuilder();

        for (String word : words) {
            if (currentSegment.length() == 0) {
                currentSegment.append(word.trim());
            } else if (currentSegment.length() + 1 + word.trim().length() <= 40) {
                currentSegment.append(" ").append(word.trim());
            } else {
                segments.add(currentSegment.toString());
                currentSegment = new StringBuilder(word.trim());
            }
        }

        segments.add(currentSegment.toString());

        segments.replaceAll(s -> ChatColor.GRAY + s);

        return segments;
    }


}
