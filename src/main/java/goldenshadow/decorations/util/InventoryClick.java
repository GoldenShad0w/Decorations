package goldenshadow.decorations.util;

import goldenshadow.decorations.Decorations;
import goldenshadow.decorations.data.DecorationManager;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class InventoryClick implements Listener {

    @EventHandler
    public void clickEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.getOpenInventory().getTitle().contains(ChatColor.BOLD + "Decorations Page")) {
            event.setCancelled(true);
            if (event.getClickedInventory() != null && event.getInventory() != player.getInventory()) {
                if (event.getCurrentItem() != null) {
                    ItemStack i = event.getCurrentItem();
                    if (i.getItemMeta() != null) {
                        if (i.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Decorations.getPlugin(), "DecorationName"), PersistentDataType.STRING)) {
                            String key = i.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Decorations.getPlugin(), "DecorationName"), PersistentDataType.STRING);
                            if (key != null) {
                                DecorationManager.placeDecoration(key, player.getLocation(), player.getUniqueId());
                                player.sendMessage(ChatMessageFactory.buildInfoMessage("Successfully placed decoration: " + key + "!"));
                            }
                            return;
                        }
                        if (i.getItemMeta().getPersistentDataContainer().has(new NamespacedKey(Decorations.getPlugin(), "PageSwap"), PersistentDataType.STRING)) {

                            char pageNumberChar = player.getOpenInventory().getTitle().charAt(player.getOpenInventory().getTitle().length()-1);
                            int nextPage = Integer.parseInt(String.valueOf(pageNumberChar));

                            String s = i.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Decorations.getPlugin(), "PageSwap"), PersistentDataType.STRING);
                            if (s != null) {
                                if (s.equals("next")) {
                                    player.openInventory(GuiManager.getInventory(nextPage));
                                    return;
                                }
                                if (s.equals("previous")) {
                                    player.openInventory(GuiManager.getInventory(nextPage-2));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
