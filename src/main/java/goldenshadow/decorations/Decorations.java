package goldenshadow.decorations;

import goldenshadow.decorations.commands.Command;
import goldenshadow.decorations.commands.TabComplete;
import goldenshadow.decorations.io.FileManager;
import goldenshadow.decorations.util.GuiManager;
import goldenshadow.decorations.util.InventoryClick;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Consumer;

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

        new Metrics(plugin, 18704);

        getVersion(v -> {
            if (this.getDescription().getVersion().equals(v)) {
                getLogger().info("You are on the latest version!");
            } else {
                getLogger().warning("You are not running the latest version! Update your plugin here: https://www.spigotmc.org/resources/decorations.110356/");
            }
        });

    }

    @Override
    public void onDisable() {
        FileManager.saveToFiles();
    }

    public static Decorations getPlugin() {
        return plugin;
    }

    private void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=110356").openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                plugin.getLogger().warning("Unable to check for updates: " + exception.getMessage());
            }
        });
    }
}
