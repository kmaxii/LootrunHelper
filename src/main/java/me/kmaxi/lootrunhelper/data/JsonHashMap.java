package me.kmaxi.lootrunhelper.data;

import java.io.*;
import java.util.HashMap;

public class JsonHashMap {
    private HashMap<String, Integer> hashMap;

    public JsonHashMap() {
        hashMap = new HashMap<>();
    }

    public void put(String key, int value) {
        hashMap.put(key, value);
    }

    public int get(String key) {
        return hashMap.getOrDefault(key, 0); // Default value is 0 if key not found
    }

    public void saveToJsonFile(String filePath) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(JsonConverter.toJson(hashMap));
        }
    }

    public void loadFromJsonFile(String filePath) throws IOException {
        try (FileReader fileReader = new FileReader(filePath)) {
            StringBuilder sb = new StringBuilder();
            int character;
            while ((character = fileReader.read()) != -1) {
                sb.append((char) character);
            }
            hashMap = JsonConverter.fromJson(sb.toString());
        }
    }

    public static void main(String[] args) {
        JsonHashMap jsonHashMap = new JsonHashMap();
        jsonHashMap.put("apple", 5);
        jsonHashMap.put("banana", 3);
        jsonHashMap.put("orange", 8);

        String filePath = "data.json";

        try {
            jsonHashMap.saveToJsonFile(filePath);
            jsonHashMap.loadFromJsonFile(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Value for 'apple': " + jsonHashMap.get("apple"));
        System.out.println("Value for 'banana': " + jsonHashMap.get("banana"));
        System.out.println("Value for 'orange': " + jsonHashMap.get("orange"));
    }
}
