package hu.bedike16.betterteamsaddon.utils;

import hu.bedike16.betterteamsaddon.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class ConfigUtils {

    public static String getOrSet(String path, String defaultValue) {
        if(defaultValue == null) return null;
        if (Main.getInstance().getConfig().contains(path, true)) {
            return Main.getInstance().getConfig().getString(path, defaultValue);
        } else {
            Main.getInstance().getConfig().set(path, defaultValue);
            Main.getInstance().saveConfig();
            return defaultValue;
        }
    }

    public static String getOrSet(String path) {
        if (Main.getInstance().getConfig().contains(path, true)) {
            return Main.getInstance().getConfig().getString(path);
        }
        return null;
    }

    public static int getOrSet(String path, int defaultValue) {
        if (Main.getInstance().getConfig().contains(path, true)) {
            return Main.getInstance().getConfig().getInt(path, defaultValue);
        } else {
            Main.getInstance().getConfig().set(path, defaultValue);
            Main.getInstance().saveConfig();
            return defaultValue;
        }
    }

    public static boolean getOrSet(String path, boolean defaultValue) {
        if (Main.getInstance().getConfig().contains(path, true)) {
            return Main.getInstance().getConfig().getBoolean(path, defaultValue);
        } else {
            Main.getInstance().getConfig().set(path, defaultValue);
            Main.getInstance().saveConfig();
            return defaultValue;
        }
    }

    public static long getOrSet(String path, long defaultValue) {
        if (Main.getInstance().getConfig().contains(path, true)) {
            return Main.getInstance().getConfig().getLong(path, defaultValue);
        } else {
            Main.getInstance().getConfig().set(path, defaultValue);
            Main.getInstance().saveConfig();
            return defaultValue;
        }
    }

    public static double getOrSet(String path, double defaultValue) {
        if (Main.getInstance().getConfig().contains(path, true)) {
            return Main.getInstance().getConfig().getDouble(path, defaultValue);
        } else {
            Main.getInstance().getConfig().set(path, defaultValue);
            Main.getInstance().saveConfig();
            return defaultValue;
        }
    }

    public static List<String> getOrSet(String path, List<String> defaultValue) {
        if (Main.getInstance().getConfig().contains(path, true)) {
            return Main.getInstance().getConfig().getStringList(path);
        } else {
            Main.getInstance().getConfig().set(path, defaultValue);
            Main.getInstance().saveConfig();
            return defaultValue;
        }
    }

//    public static ItemStack getOrSet(String path, ItemStack defaultValue) {
//        ItemStack is = new ItemStack(Material.STONE, 1);
//        ConfigurationSection cooldownSection = Main.getInstance().getConfig().getConfigurationSection(path);
//        if(cooldownSection != null) {
//            is = ItemUtils.getGadgetItemStack(cooldownSection);
//            return is;
//        } else {
//            Main.getInstance().getConfig().set(path, XItemStack.serialize(is));
//            Main.getInstance().saveConfig();
//            return defaultValue;
//        }
//    }

    public static ConfigurationSection getOrSet(String path, Map<?, ?> defaultValue) {
        if (Main.getInstance().getConfig().contains(path, true)) {
            return Main.getInstance().getConfig().getConfigurationSection(path);
        }
        return Main.getInstance().getConfig().createSection(path, defaultValue);
    }
}
