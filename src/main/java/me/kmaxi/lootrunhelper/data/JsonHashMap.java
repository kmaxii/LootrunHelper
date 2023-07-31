package me.kmaxi.lootrunhelper.data;

import me.kmaxi.lootrunhelper.utils.FileUtils;

import java.io.*;
import java.util.HashMap;

public class JsonHashMap {
    private HashMap<String, Integer> hashMap;

    public JsonHashMap() {
        hashMap = new HashMap<>();
    }

    public boolean contains(String key) {
        return hashMap.containsKey(key);
    }

    public void put(String key, int value) {
        hashMap.put(key, value);
    }


    public void add(String key) {
        add(key, 1);
    }

    public void add(String key, int amount) {
        int currentValue = hashMap.getOrDefault(key, 0);
        hashMap.put(key, currentValue + amount);
    }

    public void subtract(String key) {
        subtract(key, 1);
    }


    public void subtract(String key, int amount) {
        int currentValue = hashMap.getOrDefault(key, 0);
        currentValue -= amount;
        if (currentValue < 0)
            currentValue = 0;

        hashMap.put(key, currentValue);
    }

    public int get(String key) {
        return hashMap.getOrDefault(key, 0); // Default value is 0 if key not found
    }

    public void saveToJsonFile(String filePath) throws IOException {
        FileUtils.createDirectory(filePath);
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(JsonConverter.toJson(hashMap));
        }
    }

    public void reset() {
        hashMap.clear();
    }

    public void loadFromJsonFile(String filePath) throws IOException {
        if (!FileUtils.doesFileExistInDirectory(filePath))
            return;

        try (FileReader fileReader = new FileReader(filePath)) {
            StringBuilder sb = new StringBuilder();
            int character;
            while ((character = fileReader.read()) != -1) {
                sb.append((char) character);
            }
            hashMap = JsonConverter.fromJson(sb.toString());
        }
    }


}
