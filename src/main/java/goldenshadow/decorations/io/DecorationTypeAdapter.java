package goldenshadow.aurum.entities;

import com.google.gson.*;
import goldenshadow.aurum.other.RespawnLocation;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.lang.reflect.Type;

public class RespawnLocationAdapter implements JsonSerializer<RespawnLocation>, JsonDeserializer<RespawnLocation> {

    @Override
    public RespawnLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        double x = jsonObject.get("x").getAsDouble();
        double y = jsonObject.get("y").getAsDouble();
        double z = jsonObject.get("z").getAsDouble();
        String worldName = jsonObject.get("worldName").getAsString();
        int range = jsonObject.get("range").getAsInt();
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            throw new JsonParseException("Invalid world name: " + worldName);
        }
        return new RespawnLocation(world, x, y, z, range);
    }

    @Override
    public JsonElement serialize(RespawnLocation spawnLocation, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", spawnLocation.getLocation().getX());
        jsonObject.addProperty("y", spawnLocation.getLocation().getY());
        jsonObject.addProperty("z", spawnLocation.getLocation().getZ());
        jsonObject.addProperty("worldName", spawnLocation.getLocation().getWorld().getName());
        jsonObject.addProperty("range", spawnLocation.getRange());
        return jsonObject;
    }

}

