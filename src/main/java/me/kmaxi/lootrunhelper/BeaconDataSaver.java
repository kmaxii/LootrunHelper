package me.kmaxi.lootrunhelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Hashtable;

public class BeaconDataSaver {

    private HashMap<String, Integer> beaconData;

    private String fileName;

    public BeaconDataSaver(String fileName) {
        beaconData = new HashMap<>();
        initializeBeaconData();
        this.fileName = fileName;
    }

    private void initializeBeaconData() {
        beaconData.put("RED", 0);
        beaconData.put("GREEN", 0);
        beaconData.put("BLUE", 0);
        beaconData.put("PURPLE", 0);
        beaconData.put("YELLOW", 0);
        beaconData.put("GRAY", 0);
        beaconData.put("WHITE", 0);
        beaconData.put("ORANGE", 0);
        beaconData.put("DARK_GRAY", 0);
        beaconData.put("AQUA", 0);
        beaconData.put("RAINBOW", 0);
    }

    public void clearData(){
    	beaconData.clear();
        initializeBeaconData();
        saveToFile();
    }

    public void pickBeacon(String beacon) {
        Integer currentCount = (Integer) beaconData.get(beacon);
        if (currentCount == null) {
            currentCount = 0;
        }
        beaconData.put(beacon, currentCount + 1);
        saveToFile();
    }

    public void saveToFile() {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(beaconData, writer);
            System.out.println("Data saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BeaconDataSaver loadFromFile(String fileName) {
        try (Reader reader = new FileReader(fileName)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Hashtable<String, Integer>>() {}.getType();
            BeaconDataSaver dataSaver = new BeaconDataSaver(fileName);
            dataSaver.beaconData = gson.fromJson(reader, type);
            if (dataSaver.beaconData == null) {
                dataSaver.beaconData = new HashMap<>();
            }
            return dataSaver;
        } catch (IOException e) {
            return new BeaconDataSaver(fileName);
        }
    }

    public void sendDataToChat(){
        if (beaconData == null) {
            System.out.println("ERROR! BEACON DATA WAS NULL!!");
        }

        StringBuilder stringBuilder  = new StringBuilder();
        for (String key : beaconData.keySet()) {
            stringBuilder.append(key).append(": ").append(beaconData.get(key)).append("\n");
        }
        assert MinecraftClient.getInstance().player != null;
        MinecraftClient.getInstance().player.sendMessage(Text.of(stringBuilder.toString()));
    }

    public static void main(String[] args) {
        BeaconDataSaver dataSaver = loadFromFile("beacon_data.json");

        // Simulating Beacon picks
        dataSaver.pickBeacon("RED");
        dataSaver.pickBeacon("BLUE");
        dataSaver.pickBeacon("RED");
        dataSaver.pickBeacon("GREEN");
        dataSaver.pickBeacon("YELLOW");
        dataSaver.pickBeacon("YELLOW");
        dataSaver.pickBeacon("YELLOW");

/*
        // Load data from the JSON file
        BeaconDataSaver loadedData = BeaconDataSaver.loadFromFile("beacon_data.json");
        if (loadedData != null) {
            System.out.println("Loaded data:");
            System.out.println(loadedData.beaconData);
        }*/
    }
}
