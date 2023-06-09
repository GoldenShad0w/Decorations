package goldenshadow.decorations.data;

import goldenshadow.decorations.util.Serializer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Rotation;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.*;

/**
 * Class which saves the serialized data of an entity
 */
public class EntityData {


    //block display data
    @Nullable
    public String block = Bukkit.createBlockData(Material.GRASS_BLOCK).getAsString();
    //Text display data
    @Nullable
    public TextDisplay.TextAlignment textAlignment = TextDisplay.TextAlignment.CENTER;
    @Nullable
    public Color backgroundColor = Color.GRAY;
    public int lindWidth = 20;
    @Nullable
    public String text = "";
    public byte textOpacity = 127;  
    public boolean isDefaultBackground = true;
    public boolean isSeeThrough = true;
    public boolean isShadowed = true;
    //Item display data
    @Nullable
    public ItemDisplay.ItemDisplayTransform itemDisplayTransform = ItemDisplay.ItemDisplayTransform.NONE;
    @Nullable
    public String itemDisplayItem = null;
    // General display entity data
    @Nullable
    public Display.Billboard billboard = null;
    @Nullable
    public Display.Brightness brightness = null;
    public float displayHeight = 0;
    public float displayWidth = 0;
    @Nullable
    public Color glowColorOverride = Color.WHITE;
    public int interpolationDelay = 0;
    public int interpolationDuration = 0;
    public float shadowRadius = 0;
    public float shadowStrength = 1;
    public Transformation transformation = new Transformation(new Vector3f(0,0,0), new Quaternionf(0,0,0,0), new Vector3f(1,1,1), new Quaternionf(0,0,0,0));
    public float viewRange = 1;
    //Armor Stand data

    // [0] = helmet, [1] = chest, etc... [4] = main hand, [5] = off hand
    public String[] armorStandEquipment = new String[6];
    public EulerAngle leftArmPose = null;
    public EulerAngle leftLegPose = null;
    public EulerAngle rightArmPose = null;
    public EulerAngle rightLegPose = null;
    public EulerAngle bodyPose = null;
    public EulerAngle headPose = null;
    public boolean hasArms = true;
    public boolean hasBasePlate = true;
    public boolean isMarker = false;
    public boolean isSmall = false;
    public boolean isVisible = true;
    public ArmorStand.LockType[] headLocks = {};
    public ArmorStand.LockType[] chestLocks = {};
    public ArmorStand.LockType[] legsLocks = {};
    public ArmorStand.LockType[] feetLocks = {};
    public ArmorStand.LockType[] mainLocks = {};
    public ArmorStand.LockType[] offhandLocks = {};

    // Item frame data
    @Nullable
    public String itemFrameItem = null;
    public float itemDropChance = 0;
    public Rotation rotation = Rotation.NONE;
    public boolean isFixed = false;

    //general stuff
    public EntityType entityType;
    public boolean isInvulnerable;
    public boolean hasGravity;
    public boolean isCustomNameVisible;
    public String customName;
    public float yaw = 0;
    public float pitch = 0;
    public boolean isGlowing = false;


    public EntityData() {

    }



    /**
     * Creates a new entity data object
     * @param entity The entity whose data should be serialized. If the entity should be one if this list: Any display entity, armor stand or item frame/glow item from
     */
    @SuppressWarnings("deprecation")
    public EntityData(Entity entity) {
        entityType = entity.getType();

        pitch = entity.getLocation().getPitch();
        yaw = entity.getLocation().getYaw();
        isInvulnerable = entity.isInvulnerable();
        hasGravity = entity.hasGravity();
        isCustomNameVisible = entity.isCustomNameVisible();
        customName = entity.getCustomName();
        isGlowing = entity.isGlowing();
        if (entity instanceof Display display) {
            billboard = display.getBillboard();
            brightness = display.getBrightness();
            displayHeight = display.getDisplayHeight();
            displayWidth = display.getDisplayWidth();
            glowColorOverride = display.getGlowColorOverride();
            interpolationDelay = display.getInterpolationDelay();
            interpolationDuration = display.getInterpolationDuration();
            shadowRadius = display.getShadowRadius();
            shadowStrength = display.getShadowStrength();
            transformation = display.getTransformation();
            viewRange = display.getViewRange();
            if (entity instanceof BlockDisplay) {
                block = ((BlockDisplay) entity).getBlock().getAsString();
            }
            if (entity instanceof TextDisplay textDisplay) {
                textAlignment = textDisplay.getAlignment();
                backgroundColor = textDisplay.getBackgroundColor();
                lindWidth = textDisplay.getLineWidth();
                text = textDisplay.getText();
                textOpacity = textDisplay.getTextOpacity();
                isDefaultBackground = textDisplay.isDefaultBackground();
                isSeeThrough = textDisplay.isSeeThrough();
                isShadowed = textDisplay.isShadowed();

            }
            if (entity instanceof ItemDisplay itemDisplay) {
                itemDisplayTransform = itemDisplay.getItemDisplayTransform();
                itemDisplayItem = Serializer.encodeSerializable(itemDisplay.getItemStack());
            }
        }
        if (entity instanceof ItemFrame itemFrame) {
            itemFrameItem = Serializer.encodeSerializable(itemFrame.getItem());
            itemDropChance = itemFrame.getItemDropChance();
            rotation = itemFrame.getRotation();
            isFixed = itemFrame.isFixed();
        }
        if (entity instanceof ArmorStand armorStand) {
            assert armorStand.getEquipment() != null;
            armorStandEquipment[0] = Serializer.encodeSerializable(armorStand.getEquipment().getHelmet());
            armorStandEquipment[1] = Serializer.encodeSerializable(armorStand.getEquipment().getChestplate());
            armorStandEquipment[2] = Serializer.encodeSerializable(armorStand.getEquipment().getLeggings());
            armorStandEquipment[3] = Serializer.encodeSerializable(armorStand.getEquipment().getBoots());
            armorStandEquipment[4] = Serializer.encodeSerializable(armorStand.getEquipment().getItemInMainHand());
            armorStandEquipment[5] = Serializer.encodeSerializable(armorStand.getEquipment().getItemInOffHand());
            leftArmPose = armorStand.getLeftArmPose();
            leftLegPose = armorStand.getLeftLegPose();
            rightArmPose = armorStand.getRightArmPose();
            rightLegPose = armorStand.getRightLegPose();
            headPose = armorStand.getHeadPose();
            bodyPose = armorStand.getBodyPose();
            hasArms = armorStand.hasArms();
            hasBasePlate = armorStand.hasBasePlate();
            isMarker = armorStand.isMarker();
            isSmall = armorStand.isSmall();
            isVisible = armorStand.isVisible();
            headLocks = getLocks(EquipmentSlot.HEAD, armorStand);
            chestLocks = getLocks(EquipmentSlot.CHEST, armorStand);
            legsLocks = getLocks(EquipmentSlot.LEGS, armorStand);
            feetLocks = getLocks(EquipmentSlot.FEET, armorStand);
            mainLocks = getLocks(EquipmentSlot.HAND, armorStand);
            offhandLocks = getLocks(EquipmentSlot.OFF_HAND, armorStand);

        }
    }


    private ArmorStand.LockType[] getLocks(EquipmentSlot slot, ArmorStand armorStand) {
        ArmorStand.LockType[] array = new ArmorStand.LockType[3];
        int index = 0;

        if (armorStand.hasEquipmentLock(slot, ArmorStand.LockType.ADDING_OR_CHANGING)) {
            array[index++] = ArmorStand.LockType.ADDING_OR_CHANGING;
        }
        if (armorStand.hasEquipmentLock(slot, ArmorStand.LockType.REMOVING_OR_CHANGING)) {
            array[index++] = ArmorStand.LockType.REMOVING_OR_CHANGING;
        }
        if (armorStand.hasEquipmentLock(slot, ArmorStand.LockType.ADDING)) {
            array[index++] = ArmorStand.LockType.ADDING;
        }

        return Arrays.copyOf(array, index);
    }


}
