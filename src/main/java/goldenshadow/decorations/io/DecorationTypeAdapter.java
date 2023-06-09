package goldenshadow.decorations.io;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import goldenshadow.decorations.data.Decoration;
import goldenshadow.decorations.data.DecorationComponent;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DecorationTypeAdapter implements JsonSerializer<Decoration>, JsonDeserializer<Decoration> {

    @Override
    public Decoration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String name = jsonObject.get("name").getAsString();
        String description = jsonObject.get("description").getAsString();
        String author = jsonObject.get("author").getAsString();
        String displayItem = jsonObject.get("displayItem").getAsString();

        Type componentType = new TypeToken<DecorationComponent>() {}.getType();
        DecorationComponentTypeAdapter adapter = new DecorationComponentTypeAdapter();
        List<DecorationComponent> components = new ArrayList<>();

        JsonArray componentArray = jsonObject.getAsJsonArray("components");

        for (JsonElement e : componentArray) {
            DecorationComponent c = adapter.deserialize(e, componentType, context);
            components.add(c);
        }

        return new Decoration(name, description, components, author, displayItem);
    }

    @Override
    public JsonElement serialize(Decoration src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", src.getName());
        if (src.getDescription() != null) {
            jsonObject.addProperty("description", src.getDescription());
        }
        jsonObject.addProperty("author", src.getAuthor());
        jsonObject.addProperty("displayItem", src.getDisplayItemString());
        DecorationComponentTypeAdapter adapter = new DecorationComponentTypeAdapter();
        JsonArray array = new JsonArray();
        for (DecorationComponent c : src.getComponents()) {
            array.add(adapter.serialize(c, typeOfSrc, context));
        }
        jsonObject.add("components", array);
        return jsonObject;
    }

}

