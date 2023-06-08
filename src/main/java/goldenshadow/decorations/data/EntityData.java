package goldenshadow.decorations.data;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;

public class DecorationComponent {

    //block display data
    BlockData block = Bukkit.createBlockData(Material.GRASS_BLOCK);
    //Text display data
    TextDisplay.TextAlignment textAlignment = TextDisplay.TextAlignment.CENTER;
    Color backgroundColor = Color.GRAY;
    int lindWidth = 20;
    String text = "";
    byte textOpacity = 127;
    boolean isDefaultBackground = true;
    boolean isSeeThrough = true;
    boolean isShadowed = true;
    //Item display data
    ItemDisplay.ItemDisplayTransform itemDisplayTransform = ItemDisplay.ItemDisplayTransform.NONE;
    ItemStack itemDisplayItem = null;
    // General display entity data
    Display.Billboard billboard = Display.Billboard.FIXED;
    Display.Brightness brightness = new Display.Brightness(0,0);
    float displayHeight = 1;
    float displayWidth = 1;
    Color glowColorOverride = Color.WHITE;
    int interpolationDelay = 0;
    int interpolationDuration = 0;
    float getShadowRadius = 1;
    float getShadowStrength = 1;
    Transformation transformation = null;
    float viewRange = 1;
    //Armor Stand data
    EntityEquipment armorStandEquipment = null;
    





}
