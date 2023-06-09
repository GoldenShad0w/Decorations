package goldenshadow.decorations.io;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import goldenshadow.decorations.Decorations;
import goldenshadow.decorations.data.Decoration;
import goldenshadow.decorations.data.DecorationComponent;
import goldenshadow.decorations.data.DecorationManager;
import goldenshadow.decorations.util.GuiManager;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FileManager {

    public static void saveToFiles() {
        File decorationsFolder = new File(Decorations.getPlugin().getDataFolder(), "files");
        decorationsFolder.getParentFile().mkdir();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Decoration.class, new DecorationTypeAdapter())
                .registerTypeAdapter(DecorationComponent.class, new DecorationTypeAdapter())
                .registerTypeAdapter(EntityDataTypeAdapter.class, new EntityDataTypeAdapter())
                .create();

        File[] files = decorationsFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }

        try {
            for (String s : DecorationManager.getKeys()) {
                File file = new File(decorationsFolder, s + ".json");
                file.getParentFile().mkdirs();
                file.createNewFile();
                Writer writer = new FileWriter(file, false);
                gson.toJson(DecorationManager.getDecoration(s), writer);
                writer.flush();
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFromFiles() {
        File decorationsFolder = new File(Decorations.getPlugin().getDataFolder(), "files");

        List<File> fileList = new ArrayList<>();


        if(decorationsFolder.exists() && decorationsFolder.isDirectory()) {
            File[] decorationFiles = decorationsFolder.listFiles();
            if (decorationFiles != null) {
                fileList = Arrays.asList(decorationFiles);
            }
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Decoration.class, new DecorationTypeAdapter())
                    .registerTypeAdapter(DecorationComponent.class, new DecorationTypeAdapter())
                    .registerTypeAdapter(EntityDataTypeAdapter.class, new EntityDataTypeAdapter())
                    .create();

            HashMap<String, Decoration> map = new HashMap<>();

            

            if (!fileList.isEmpty()) {
                for (File file : fileList) {
                    if (file.getName().contains(".json")) {
                        try {
                            Reader reader = new FileReader(file);
                            Type type = new TypeToken<Decoration>() {
                            }.getType();

                            Decoration decoration = gson.fromJson(reader, type);

                            map.put(decoration.getName(), decoration);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            DecorationManager.setHashMap(map);
            GuiManager.updateInventories();
        }
    }
}
