package goldenshadow.decorations.data;


import goldenshadow.decorations.Decorations;
import goldenshadow.decorations.util.GuiManager;
import goldenshadow.decorations.util.Serializer;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;


/**
 * A class representing a saved decoration
 */
public class Decoration {

    private final List<DecorationComponent> components;
    private final String name;
    private String description;
    private final String author;
    private String displayItem;


    /**
     * Creates a new decoration object
     * @param name Namespace of the new object
     * @param description Optional description of the contents
     * @param components All the entity components
     * @param author The player who saved this decoration
     */
    public Decoration(String name, String description, List<DecorationComponent> components, String author) {
        this.components = components;
        this.name = name;
        this.description = description != null ? description : "No description given";
        this.author = author;
        this.displayItem = Serializer.encodeSerializable(new ItemStack(Material.ARMOR_STAND));
    }

    /**
     * Creates a new decoration object
     * @param name Namespace of the new object
     * @param description Optional description of the contents
     * @param components All the entity components
     * @param author The player who saved this decoration
     * @param displayItem The encoded string of the display item
     */
    public Decoration(String name, String description, List<DecorationComponent> components, String author, String displayItem) {
        this.components = components;
        this.name = name;
        this.description = description != null ? description : "No description given";
        this.author = author;
        this.displayItem = displayItem;
    }

    /**
     * Used to spawn the decoration
     * @param pivot The root location of where it should spawn
     */
    public void spawn(Location pivot) {
        for (DecorationComponent component : components) {
            spawnComponent(component, pivot);
        }
    }

    /**
     * Getter for the decorations name
     * @return The name of the decoration
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the description
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the decorations components
     * @return The components
     */
    public List<DecorationComponent> getComponents() {
        return components;
    }

    /**
     * Getter for the display item
     * @return The display item
     */
    public ItemStack getDisplayItem() {
        return Serializer.decodeItem(displayItem);
    }

    /**
     * Getter for the encoded display item
     * @return The encoded display item
     */
    public String getDisplayItemString() {
        return displayItem;
    }

    /**
     * Getter for the author
     * @return The author of the decoration
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Used to send a sender information about the decoration through chat messages
     * @param sender The CommandSender who should receive the information
     */
    public void printInfo(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_GREEN + name);
        sender.sendMessage(ChatColor.GREEN + "    - Creator: " + author);
        sender.sendMessage(ChatColor.GREEN + "    - Description: " + description);
    }

    /**
     * Internal method used to spawn a component at its correct relative location
     * @param component The component that should be placed
     * @param pivot The root location to which the component should be placed relatively
     */
    @SuppressWarnings("deprecation")
    private static void spawnComponent(DecorationComponent component, Location pivot) {

        Location spawnLoc = pivot.clone().add(component.getOffsetVector());
        spawnLoc.setDirection(component.getDirection());


        EntityData data = component.getData();
        assert pivot.getWorld() != null;

        if ((data.entityType == EntityType.ITEM_FRAME) || (data.entityType == EntityType.GLOW_ITEM_FRAME)) {
            ItemFrame itemFrame = (ItemFrame) pivot.getWorld().spawnEntity(spawnLoc, data.entityType, false);
            if (data.itemFrameItem != null) {
                itemFrame.setItem(Serializer.decodeItem(data.itemFrameItem), false);
            }
            itemFrame.setItemDropChance(data.itemDropChance);
            itemFrame.setFixed(data.isFixed);
            itemFrame.setRotation(data.rotation);
            itemFrame.setVisible(data.isVisible);
            itemFrame.setInvulnerable(data.isInvulnerable);
            itemFrame.setGravity(data.hasGravity);
            itemFrame.setCustomName(data.customName);
            itemFrame.setCustomNameVisible(data.isCustomNameVisible);
            itemFrame.setGlowing(data.isGlowing);
            itemFrame.setRotation(data.yaw, data.pitch);
            return;
        }
        if (data.entityType == EntityType.ARMOR_STAND) {
            ArmorStand armorStand = (ArmorStand) pivot.getWorld().spawnEntity(spawnLoc, data.entityType, false);
            armorStand.setInvulnerable(data.isInvulnerable);
            armorStand.setGravity(data.hasGravity);
            armorStand.setCustomName(data.customName);
            armorStand.setCustomNameVisible(data.isCustomNameVisible);
            armorStand.setMarker(data.isMarker);
            armorStand.setSmall(data.isSmall);
            armorStand.setVisible(data.isVisible);
            armorStand.setArms(data.hasArms);
            armorStand.setBasePlate(data.hasBasePlate);
            armorStand.setBodyPose(data.bodyPose);
            armorStand.setHeadPose(data.headPose);
            armorStand.setLeftArmPose(data.leftArmPose);
            armorStand.setRightArmPose(data.rightArmPose);
            armorStand.setLeftLegPose(data.leftLegPose);
            armorStand.setRightLegPose(data.rightLegPose);
            setLocks(EquipmentSlot.HEAD, armorStand, data.headLocks);
            setLocks(EquipmentSlot.CHEST, armorStand, data.chestLocks);
            setLocks(EquipmentSlot.LEGS, armorStand, data.legsLocks);
            setLocks(EquipmentSlot.FEET, armorStand, data.feetLocks);
            setLocks(EquipmentSlot.HAND, armorStand, data.mainLocks);
            setLocks(EquipmentSlot.OFF_HAND, armorStand, data.offhandLocks);
            assert armorStand.getEquipment() != null;
            armorStand.getEquipment().setHelmet(Serializer.decodeItem(data.armorStandEquipment[0]),true);
            armorStand.getEquipment().setChestplate(Serializer.decodeItem(data.armorStandEquipment[1]),true);
            armorStand.getEquipment().setLeggings(Serializer.decodeItem(data.armorStandEquipment[2]),true);
            armorStand.getEquipment().setBoots(Serializer.decodeItem(data.armorStandEquipment[3]),true);
            armorStand.getEquipment().setItemInMainHand(Serializer.decodeItem(data.armorStandEquipment[4]),true);
            armorStand.getEquipment().setItemInOffHand(Serializer.decodeItem(data.armorStandEquipment[5]),true);
            armorStand.setGlowing(data.isGlowing);
            armorStand.setRotation(data.yaw, data.pitch);
            return;
            
        }
        Display display = (Display) pivot.getWorld().spawnEntity(spawnLoc, data.entityType, false);
        display.setInvulnerable(data.isInvulnerable);
        display.setGravity(data.hasGravity);
        display.setCustomName(data.customName);
        display.setCustomNameVisible(data.isCustomNameVisible);
        if (data.billboard != null) display.setBillboard(data.billboard);
        display.setDisplayHeight(data.displayHeight);
        display.setDisplayWidth(data.displayWidth);
        display.setBrightness(data.brightness);
        display.setGlowColorOverride(data.glowColorOverride);
        display.setInterpolationDelay(data.interpolationDelay);
        display.setInterpolationDuration(data.interpolationDuration);
        display.setShadowRadius(data.shadowRadius);
        display.setShadowStrength(data.shadowStrength);
        display.setViewRange(data.viewRange);
        display.setGlowing(data.isGlowing);
        display.setRotation(data.yaw, data.pitch);



        Bukkit.getScheduler().scheduleSyncDelayedTask(Decorations.getPlugin(), () -> display.setTransformation(data.transformation), 1L);

        if (display instanceof BlockDisplay blockDisplay) {
            if (data.block != null) {
                blockDisplay.setBlock(Bukkit.createBlockData(data.block));
            }
            return;
        }
        if (display instanceof ItemDisplay itemDisplay) {
            if (data.itemDisplayTransform != null) {
                itemDisplay.setItemDisplayTransform(data.itemDisplayTransform);
            }
            if (data.itemDisplayItem != null) {
                itemDisplay.setItemStack(Serializer.decodeItem(data.itemDisplayItem));
            }
            return;
        }

        if (display instanceof TextDisplay textDisplay) {
            if (data.textAlignment != null) {
                textDisplay.setAlignment(data.textAlignment);
            }
            textDisplay.setText(data.text);
            textDisplay.setDefaultBackground(data.isDefaultBackground);
            textDisplay.setLineWidth(data.lindWidth);
            textDisplay.setSeeThrough(data.isSeeThrough);
            textDisplay.setShadowed(data.isShadowed);
            textDisplay.setTextOpacity(data.textOpacity);
            textDisplay.setBackgroundColor(data.backgroundColor);
        }
    }


    /**
     * Used to set all the lock types of a slot for armor stands
     * @param slot The slot that should be handled
     * @param armorStand The armor stand whose slot should be locked
     * @param array All the lock types that should be applied
     */
    private static void setLocks(EquipmentSlot slot, ArmorStand armorStand, ArmorStand.LockType[] array) {
        for (ArmorStand.LockType l : array) {
            armorStand.addEquipmentLock(slot, l);
        }
    }

    /**
     * Setter for the description
     * @param description The new description
     */
    public void setDescription(String description) {
        this.description = description;
        GuiManager.updateInventories();
    }

    /**
     * Setter for the display item
     * @param displayItem The new display item
     */
    public void setDisplayItem(ItemStack displayItem) {
        this.displayItem = Serializer.encodeSerializable(displayItem);
        GuiManager.updateInventories();
    }


}
