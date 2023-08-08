package me.kmaxi.lootrunhelper.data;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.HashMap;

public class BooleanHashMap {
    private static final String JSON_FILE_PATH = "data.json";
    private static HashMap<String, Boolean> dataMap = new HashMap<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static {
        loadDataFromFile();
    }

    public static synchronized void add(String key, boolean value) {
        dataMap.put(key, value);
        saveDataToFile();
    }

    public static synchronized void remove(String key) {
        dataMap.remove(key);
        saveDataToFile();
    }

    public static synchronized void change(String key, boolean newValue) {
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
            dataMap = new HashMap<>();
        }
    }

    public static void main(String[] args) {
        // Example usage
        add("key1", true);
        add("key2", false);
        remove("key2");
        change("key1", false);
    }
}
