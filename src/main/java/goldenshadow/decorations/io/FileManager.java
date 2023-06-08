package goldenshadow.decorations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import goldenshadow.decorations.data.Decoration;
import goldenshadow.decorations.data.DecorationManager;
import goldenshadow.decorations.data.EntityData;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FileManager {

    public static void saveToFiles() {
        File decorationsFolder = new File(Decorations.getPlugin().getDataFolder(), "decorations");
        decorationsFolder.getParentFile().mkdir();
        Gson gson = new GsonBuilder()
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
        File decorationsFolder = new File(Decorations.getPlugin().getDataFolder(), "decorations");

        List<File> fileList = new ArrayList<>();


        if(decorationsFolder.exists() && decorationsFolder.isDirectory()) {
            File[] decorationFiles = decorationsFolder.listFiles();
            if (decorationFiles != null) {
                fileList = Arrays.asList(decorationFiles);
            }

            Gson gson = new GsonBuilder()
                    .create();

            HashMap<String, Decoration> map = new HashMap<>();

            for (File file : fileList) {
                try {
                    Reader reader = new FileReader(file);
                    map.put(file.getName(), gson.fromJson(reader, Decoration.class));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            DecorationManager.setHashMap(map);
        }
    }
}
