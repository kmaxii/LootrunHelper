package me.kmaxi.lootrunhelper.data;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.kmaxi.lootrunhelper.utils.Config;

import java.io.*;
import java.util.HashMap;

public class BooleanHashMap {
    private static final String JSON_FILE_PATH = Config.CONFIG_DIRS + "toggles.json";
    private static HashMap<String, Boolean> dataMap = new HashMap<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static {
        loadDataFromFile();
    }

    private static synchronized void add(String key, boolean value) {
        dataMap.put(key, value);
        saveDataToFile();
    }

    private static synchronized void remove(String key) {
        dataMap.remove(key);
        saveDataToFile();
    }

    private static synchronized void change(String key, boolean newValue) {
        dataMap.put(key, newValue);
        saveDataToFile();
    }

    private static void saveDataToFile() {
        try (Writer writer = new FileWriter(JSON_FILE_PATH)) {
            gson.toJson(dataMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadDataFromFile() {
        try (Reader reader = new FileReader(JSON_FILE_PATH)) {
            dataMap = gson.fromJson(reader, HashMap.class);
            if (dataMap == null) {
                dataMap = new HashMap<>();
            }
        } catch (IOException e) {
            // If the file doesn't exist, add default values
            dataMap.put("showBeacons", false);
            dataMap.put("showDestinations", false);
            saveDataToFile();
        }
    }

    public static boolean showBeacons(){
        return getValue("showBeacons");
    }

    public static boolean showDestinations(){
        return getValue("showDestinations");
    }

    public static void setShowBeacons(boolean showBeacons){
        change("showBeacons", showBeacons);
    }

    public static void setShowDestinations(boolean showDestinations){
        change("showDestinations", showDestinations);
    }

    private static synchronized boolean getValue(String key) {
        if (dataMap.containsKey(key)) {
            return dataMap.get(key);
        }
        return false; // Default value if key doesn't exist
    }

}