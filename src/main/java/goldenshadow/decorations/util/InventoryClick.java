package goldenshadow.decorations.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;

public class InventoryInteract implements Listener {

    public void interact(InventoryInteractEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.getOpenInventory().getTitle().contains(ChatColor.BOLD + "Decorations Page")) {
            if (event.getInventory() != null && event.getInventory() != player.getInventory()) {
                
            }
        }
    }
}
