package goldenshadow.decorations;

import goldenshadow.decorations.commands.Command;
import goldenshadow.decorations.commands.TabComplete;
import goldenshadow.decorations.io.FileManager;
import goldenshadow.decorations.util.GuiManager;
import goldenshadow.decorations.util.InventoryClick;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Decorations extends JavaPlugin {

    private static Decorations plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        Objects.requireNonNull(this.getCommand("decoration")).setExecutor(new Command());
        Objects.requireNonNull(this.getCommand("decoration")).setTabCompleter(new TabComplete());
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            FileManager.loadFromFiles();
            GuiManager.updateInventories();
        }, 1L);

    }

    @Override
    public void onDisable() {
        FileManager.saveToFiles();
    }

    public static Decorations getPlugin() {
        return plugin;
    }
}
