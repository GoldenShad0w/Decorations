package goldenshadow.decorations.io;

import com.google.gson.*;
import goldenshadow.decorations.data.DecorationComponent;
import goldenshadow.decorations.data.EntityData;
import goldenshadow.decorations.util.Serializer;
import org.bukkit.util.Vector;

import java.lang.reflect.Type;

public class DecorationComponentTypeAdapter implements JsonSerializer<DecorationComponent>, JsonDeserializer<DecorationComponent> {

    @Override
    public DecorationComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Vector direction = (Vector) Serializer.decodeConfigurationSerializable(jsonObject.get("direction").getAsString());
        Vector offsetVector = (Vector) Serializer.decodeConfigurationSerializable(jsonObject.get("offsetVector").getAsString());
        EntityData data = context.deserialize(jsonObject.get("entityData"), EntityData.class);
        return new DecorationComponent(data, offsetVector, direction);
    }

    @Override
    public JsonElement serialize(DecorationComponent src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("direction", Serializer.encodeSerializable(src.getDirection()));
        jsonObject.addProperty("offsetVector", Serializer.encodeSerializable(src.getOffsetVector()));
        EntityDataTypeAdapter adapter = new EntityDataTypeAdapter();
        jsonObject.add("entityData", adapter.serialize(src.getData(), typeOfSrc, context));

        return jsonObject;
    }
}
