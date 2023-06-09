package goldenshadow.decorations.io;

import com.google.gson.*;
import goldenshadow.decorations.data.EntityData;
import org.bukkit.Color;
import org.bukkit.Rotation;
import org.bukkit.entity.*;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.lang.reflect.Type;

public class EntityDataTypeAdapter implements JsonSerializer<EntityData>, JsonDeserializer<EntityData> {

    @Override
    public EntityData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        EntityData entityData = new EntityData();

        entityData.entityType = EntityType.valueOf(jsonObject.get("entityType").getAsString());
        entityData.isInvulnerable = jsonObject.get("isInvulnerable").getAsBoolean();
        entityData.hasGravity = jsonObject.get("hasGravity").getAsBoolean();
        entityData.isCustomNameVisible = jsonObject.get("isCustomNameVisible").getAsBoolean();
        entityData.customName = jsonObject.get("customName").getAsString();
        entityData.isGlowing = jsonObject.get("isGlowing").getAsBoolean();
        entityData.yaw = jsonObject.get("yaw").getAsFloat();
        entityData.pitch = jsonObject.get("pitch").getAsFloat();

        if (jsonObject.has("block")) {
            entityData.block = jsonObject.get("block").getAsString();
        }

        if (jsonObject.has("textAlignment")) {
            String textAlignmentString = jsonObject.get("textAlignment").getAsString();
            entityData.textAlignment = TextDisplay.TextAlignment.valueOf(textAlignmentString);
        }

        if (jsonObject.has("backgroundColor")) {
            entityData.backgroundColor = deserializeColor(jsonObject.get("backgroundColor").getAsJsonObject());
        }

        if (jsonObject.has("lindWidth")) {
            entityData.lindWidth = jsonObject.get("lindWidth").getAsInt();
        }

        if (jsonObject.has("text")) {
            entityData.text = jsonObject.get("text").getAsString();
        }

        if (jsonObject.has("textOpacity")) {
            entityData.textOpacity = jsonObject.get("textOpacity").getAsByte();
        }

        if (jsonObject.has("isDefaultBackground")) {
            entityData.isDefaultBackground = jsonObject.get("isDefaultBackground").getAsBoolean();
        }

        if (jsonObject.has("isSeeThrough")) {
            entityData.isSeeThrough = jsonObject.get("isSeeThrough").getAsBoolean();
        }

        if (jsonObject.has("isShadowed")) {
            entityData.isShadowed = jsonObject.get("isShadowed").getAsBoolean();
        }

        if (jsonObject.has("itemDisplayTransform")) {
            String itemDisplayTransformString = jsonObject.get("itemDisplayTransform").getAsString();
            entityData.itemDisplayTransform = ItemDisplay.ItemDisplayTransform.valueOf(itemDisplayTransformString);
        }

        if (jsonObject.has("itemDisplayItem")) {
            entityData.itemDisplayItem = jsonObject.get("itemDisplayItem").getAsString();
        }

        if (jsonObject.has("billboard")) {
            String billboardString = jsonObject.get("billboard").getAsString();
            entityData.billboard = Display.Billboard.valueOf(billboardString);
        }

        if (jsonObject.has("brightness")) {
            JsonObject brightnessObject = jsonObject.get("brightness").getAsJsonObject();
            int brightness1 = brightnessObject.get("brightness1").getAsInt();
            int brightness2 = brightnessObject.get("brightness2").getAsInt();
            entityData.brightness = new Display.Brightness(brightness1, brightness2);

        }

        if (jsonObject.has("displayHeight")) {
            entityData.displayHeight = jsonObject.get("displayHeight").getAsFloat();
        }
        if (jsonObject.has("displayWidth")) {
            entityData.displayWidth = jsonObject.get("displayWidth").getAsFloat();
        }
        if (jsonObject.has("glowColorOverride")) {
            entityData.glowColorOverride = deserializeColor(jsonObject.get("glowColorOverride").getAsJsonObject());
        }
        if (jsonObject.has("interpolationDelay")) {
            entityData.interpolationDelay = jsonObject.get("interpolationDelay").getAsInt();
        }
        if (jsonObject.has("interpolationDuration")) {
            entityData.interpolationDelay = jsonObject.get("interpolationDuration").getAsInt();
        }
        if (jsonObject.has("shadowRadius")) {
            entityData.shadowRadius = jsonObject.get("shadowRadius").getAsFloat();
        }
        if (jsonObject.has("shadowStrength")) {
            entityData.shadowStrength = jsonObject.get("shadowStrength").getAsFloat();
        }
        if (jsonObject.has("transformation")) {
            entityData.transformation = deserializeTransformation(jsonObject.get("transformation").getAsJsonObject());
        }
        if (jsonObject.has("viewRange")) {
            entityData.viewRange = jsonObject.get("viewRange").getAsFloat();
        }
        if (jsonObject.has("armorStandEquipment")) {
            JsonArray arrayObj = jsonObject.get("armorStandEquipment").getAsJsonArray();
            String[] array = new String[6];
            for (int i = 0; i < 6; i++) {
                array[i] = arrayObj.get(i).getAsString();
            }
            entityData.armorStandEquipment = array;
        }
        if (jsonObject.has("leftArmPose")) {
            entityData.leftArmPose = deserializeEulerAngle(jsonObject.get("leftArmPose").getAsJsonObject());
        }
        if (jsonObject.has("leftLegPose")) {
            entityData.leftLegPose = deserializeEulerAngle(jsonObject.get("leftLegPose").getAsJsonObject());
        }
        if (jsonObject.has("rightArmPose")) {
            entityData.rightArmPose = deserializeEulerAngle(jsonObject.get("rightArmPose").getAsJsonObject());
        }
        if (jsonObject.has("rightLegPose")) {
            entityData.rightLegPose = deserializeEulerAngle(jsonObject.get("rightLegPose").getAsJsonObject());
        }
        if (jsonObject.has("bodyPose")) {
            entityData.bodyPose = deserializeEulerAngle(jsonObject.get("bodyPose").getAsJsonObject());
        }
        if (jsonObject.has("headPose")) {
            entityData.headPose = deserializeEulerAngle(jsonObject.get("headPose").getAsJsonObject());
        }
        entityData.hasArms = jsonObject.get("hasArms").getAsBoolean();
        entityData.hasBasePlate = jsonObject.get("hasBasePlate").getAsBoolean();
        entityData.isMarker = jsonObject.get("isMarker").getAsBoolean();
        entityData.isSmall = jsonObject.get("isSmall").getAsBoolean();
        entityData.isVisible = jsonObject.get("isVisible").getAsBoolean();

        if (jsonObject.has("headLocks")) {
            entityData.headLocks = deserializeLocks(jsonObject.get("headLocks").getAsJsonArray());
        }
        if (jsonObject.has("chestLocks")) {
            entityData.chestLocks = deserializeLocks(jsonObject.get("chestLocks").getAsJsonArray());
        }
        if (jsonObject.has("legsLocks")) {
            entityData.legsLocks = deserializeLocks(jsonObject.get("legsLocks").getAsJsonArray());
        }
        if (jsonObject.has("feetLocks")) {
            entityData.feetLocks = deserializeLocks(jsonObject.get("feetLocks").getAsJsonArray());
        }
        if (jsonObject.has("mainLocks")) {
            entityData.mainLocks = deserializeLocks(jsonObject.get("mainLocks").getAsJsonArray());
        }
        if (jsonObject.has("offhandLocks")) {
            entityData.offhandLocks = deserializeLocks(jsonObject.get("offhandLocks").getAsJsonArray());
        }

        entityData.itemFrameItem = jsonObject.get("itemFrameItem").getAsString();
        entityData.itemDropChance = jsonObject.get("itemDropChance").getAsFloat();
        entityData.rotation = Rotation.valueOf(jsonObject.get("rotation").getAsString());
        entityData.isFixed = jsonObject.get("isFixed").getAsBoolean();

        
        return entityData;
    }

    @Override
    public JsonElement serialize(EntityData src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("entityType", src.entityType.name());
        jsonObject.addProperty("isInvulnerable", src.isInvulnerable);
        jsonObject.addProperty("hasGravity", src.hasGravity);
        jsonObject.addProperty("isCustomNameVisible", src.isCustomNameVisible);
        jsonObject.addProperty("customName", src.customName);
        jsonObject.addProperty("yaw", src.yaw);
        jsonObject.addProperty("pitch", src.pitch);
        jsonObject.addProperty("isGlowing", src.isGlowing);


        if (src.block != null) {
            jsonObject.addProperty("block", src.block);
        }

        if (src.textAlignment != null) {
            jsonObject.addProperty("textAlignment", src.textAlignment.name());
        }

        if (src.backgroundColor != null) {
            jsonObject.add("backgroundColor", serializeColor(src.backgroundColor));
        }

        jsonObject.addProperty("lindWidth", src.lindWidth);

        jsonObject.addProperty("text", src.text);

        jsonObject.addProperty("textOpacity", src.textOpacity);

        jsonObject.addProperty("isDefaultBackground", src.isDefaultBackground);

        jsonObject.addProperty("isSeeThrough", src.isSeeThrough);

        jsonObject.addProperty("isShadowed", src.isShadowed);

        if (src.itemDisplayTransform != null) {
            jsonObject.addProperty("itemDisplayTransform", src.itemDisplayTransform.name());
        }

        if (src.itemDisplayItem != null) {
            jsonObject.addProperty("itemDisplayItem", src.itemDisplayItem);
        }

        if (src.billboard != null) {
            jsonObject.addProperty("billboard", src.billboard.name());
        }

        if (src.brightness != null) {
            JsonObject brightnessObject = new JsonObject();
            brightnessObject.addProperty("brightness1", src.brightness.getBlockLight());
            brightnessObject.addProperty("brightness2", src.brightness.getSkyLight());
            jsonObject.add("brightness", brightnessObject);
        }

        jsonObject.addProperty("displayHeight", src.displayHeight);
        jsonObject.addProperty("displayWidth", src.displayWidth);

        if (src.glowColorOverride != null) {
            jsonObject.add("glowColorOverride", serializeColor(src.glowColorOverride));
        }

        jsonObject.addProperty("interpolationDelay", src.interpolationDelay);
        jsonObject.addProperty("interpolationDuration", src.interpolationDuration);

        jsonObject.addProperty("shadowRadius", src.shadowRadius);
        jsonObject.addProperty("shadowStrength", src.shadowStrength);
        if (src.transformation != null) {
            jsonObject.add("transformation", serializeTransformation(src.transformation));
        }
        jsonObject.addProperty("viewRange", src.viewRange);

        JsonArray armorStandArray = new JsonArray();
        for (int i = 0; i < src.armorStandEquipment.length; i++) {
            armorStandArray.add(src.armorStandEquipment[i]);
        }
        jsonObject.add("armorStandEquipment", armorStandArray);

        if (src.leftArmPose != null) {
            jsonObject.add("leftArmPose", serializeEulerAngle(src.leftArmPose));
        }

        if (src.leftLegPose != null) {
            jsonObject.add("leftLegPose", serializeEulerAngle(src.leftLegPose));
        }

        if (src.rightArmPose != null) {
            jsonObject.add("rightArmPose", serializeEulerAngle(src.rightArmPose));
        }

        if (src.rightLegPose != null) {
            jsonObject.add("rightLegPose", serializeEulerAngle(src.rightLegPose));
        }

        if (src.bodyPose != null) {
            jsonObject.add("bodyPose", serializeEulerAngle(src.bodyPose));
        }

        if (src.headPose != null) {
            jsonObject.add("headPose", serializeEulerAngle(src.headPose));
        }


        jsonObject.addProperty("hasArms", src.hasArms);
        jsonObject.addProperty("hasBasePlate", src.hasBasePlate);
        jsonObject.addProperty("isMarker", src.isMarker);
        jsonObject.addProperty("isSmall", src.isSmall);
        jsonObject.addProperty("isVisible", src.isVisible);

        jsonObject.add("headLocks", serializeLocks(src.headLocks));
        jsonObject.add("chestLocks", serializeLocks(src.chestLocks));
        jsonObject.add("legsLocks", serializeLocks(src.legsLocks));
        jsonObject.add("feetLocks", serializeLocks(src.feetLocks));
        jsonObject.add("mainLocks", serializeLocks(src.mainLocks));
        jsonObject.add("offhandLocks", serializeLocks(src.offhandLocks));

        jsonObject.addProperty("itemFrameItem", src.itemFrameItem);
        jsonObject.addProperty("itemDropChance", src.itemDropChance);
        jsonObject.addProperty("rotation", src.rotation.name());
        jsonObject.addProperty("isFixed", src.isFixed);

        return jsonObject;

    }

    private Transformation deserializeTransformation(JsonObject transObject) {
        JsonObject translationObj = transObject.get("translation").getAsJsonObject();
        Vector3f translation = new Vector3f(translationObj.get("x").getAsFloat(),translationObj.get("y").getAsFloat(),translationObj.get("z").getAsFloat());

        JsonObject leftRotObj = transObject.get("leftRotation").getAsJsonObject();
        Quaternionf leftRotation = new Quaternionf(leftRotObj.get("x").getAsFloat(),leftRotObj.get("y").getAsFloat(),leftRotObj.get("z").getAsFloat(),leftRotObj.get("w").getAsFloat());

        JsonObject scaleObj = transObject.get("scale").getAsJsonObject();
        Vector3f scale = new Vector3f(scaleObj.get("x").getAsFloat(),scaleObj.get("y").getAsFloat(),scaleObj.get("z").getAsFloat());

        JsonObject rightRotObj = transObject.get("rightRotation").getAsJsonObject();
        Quaternionf rightRotation = new Quaternionf(rightRotObj.get("x").getAsFloat(),rightRotObj.get("y").getAsFloat(),rightRotObj.get("z").getAsFloat(),rightRotObj.get("w").getAsFloat());

        return new Transformation(translation, leftRotation, scale, rightRotation);
    }

    private EulerAngle deserializeEulerAngle(JsonObject angleObject) {
        double x = angleObject.get("x").getAsDouble();
        double y = angleObject.get("y").getAsDouble();
        double z = angleObject.get("z").getAsDouble();
        return new EulerAngle(x,y,z);
    }

    private ArmorStand.LockType[] deserializeLocks(JsonArray lockObj) {
        ArmorStand.LockType[] array = new ArmorStand.LockType[lockObj.size()];
        for (int i = 0; i < lockObj.size(); i++) {
            array[i] = ArmorStand.LockType.valueOf(lockObj.get(i).getAsString());
        }
        return array;
    }
    
    private JsonObject serializeTransformation(Transformation transformation) {
        JsonObject transObj = new JsonObject();
        
        JsonObject translationObj = new JsonObject();
        translationObj.addProperty("x", transformation.getTranslation().x);
        translationObj.addProperty("y", transformation.getTranslation().y);
        translationObj.addProperty("z", transformation.getTranslation().z);

        JsonObject leftRotObj = new JsonObject();
        leftRotObj.addProperty("x", transformation.getLeftRotation().x);
        leftRotObj.addProperty("y", transformation.getLeftRotation().y);
        leftRotObj.addProperty("z", transformation.getLeftRotation().z);
        leftRotObj.addProperty("w", transformation.getLeftRotation().w);

        JsonObject scaleObj = new JsonObject();
        scaleObj.addProperty("x", transformation.getScale().x);
        scaleObj.addProperty("y", transformation.getScale().y);
        scaleObj.addProperty("z", transformation.getScale().z);

        JsonObject rightRotObj = new JsonObject();
        rightRotObj.addProperty("x", transformation.getRightRotation().x);
        rightRotObj.addProperty("y", transformation.getRightRotation().y);
        rightRotObj.addProperty("z", transformation.getRightRotation().z);
        rightRotObj.addProperty("w", transformation.getRightRotation().w);
        
        transObj.add("translation", translationObj);
        transObj.add("leftRotation", leftRotObj);
        transObj.add("scale", scaleObj);
        transObj.add("rightRotation", rightRotObj);
        
        return transObj;
    }

    private JsonObject serializeEulerAngle(EulerAngle angle) {
        JsonObject obj = new JsonObject();
        obj.addProperty("x", angle.getX());
        obj.addProperty("y", angle.getY());
        obj.addProperty("z", angle.getZ());
        return obj;
    }

    private JsonArray serializeLocks(ArmorStand.LockType[] locks) {
        JsonArray array = new JsonArray();
        for (ArmorStand.LockType lock : locks) {
            array.add(lock.name());
        }
        return array;
    }

    private JsonObject serializeColor(Color color) {
        JsonObject backgroundObj = new JsonObject();
        backgroundObj.addProperty("alpha", color.getAlpha());
        backgroundObj.addProperty("red", color.getRed());
        backgroundObj.addProperty("green", color.getGreen());
        backgroundObj.addProperty("blue", color.getBlue());
        return backgroundObj;
    }

    private Color deserializeColor(JsonObject object) {
        int alpha = object.get("alpha").getAsInt();
        int red = object.get("red").getAsInt();
        int green = object.get("green").getAsInt();
        int blue = object.get("blue").getAsInt();
        return Color.fromARGB(alpha, red, green, blue);
    }

}
