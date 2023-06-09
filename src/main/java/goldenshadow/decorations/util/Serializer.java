package goldenshadow.decorations.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Utility class used to convert an ItemStack into a string and back
 */
public class Serializer {


    /**
     * Used to decode a string back into an ItemStack
     * @param string The string that should be converted
     * @return The ItemStack or if the String is invalid, a red wool block
     */
    public static ItemStack decodeItem(String string) {
        if (string.equals("error")) return new ItemStack(Material.RED_WOOL);

        try {
            byte[] serializedObject = Base64.getDecoder().decode(string);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedObject);
            BukkitObjectInputStream inputStream = new BukkitObjectInputStream(byteArrayInputStream);

            ItemStack i = (ItemStack) inputStream.readObject();
            inputStream.close();
            return i;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ItemStack(Material.RED_WOOL);
        }
    }


    public static String encodeSerializable(ConfigurationSerializable configurationSerializable) {
        String encoded;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(configurationSerializable);
            objectOutputStream.flush();

            byte[] serialized = byteArrayOutputStream.toByteArray();
            encoded = Base64.getEncoder().encodeToString(serialized);

            objectOutputStream.close();

            return encoded;


        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public static ConfigurationSerializable decodeConfigurationSerializable(String string) {
        if (string.equals("error")) return null;

        try {
            byte[] serializedObject = Base64.getDecoder().decode(string);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedObject);
            BukkitObjectInputStream inputStream = new BukkitObjectInputStream(byteArrayInputStream);

            ConfigurationSerializable c = (ConfigurationSerializable) inputStream.readObject();
            inputStream.close();
            return c;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
