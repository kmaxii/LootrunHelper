package me.kmaxi.lootrunhelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.Dictionary;
import java.util.Hashtable;
public class BeaconDataSaver {

    private Dictionary<String, Integer> beaconData;

    private String fileName;

    public BeaconDataSaver(String fileName) {
        beaconData = new Hashtable<>();
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

    public void pickBeacon(String beacon) {
        Integer currentCount = (Integer) beaconData.get(beacon);
        if (currentCount == null) {
            currentCount = 0;
        }
        beaconData.put(beacon, currentCount + 1);
    }

    public void saveToFile(String fileName) {
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
                dataSaver.beaconData = new Hashtable<>();
            }
            return dataSaver;
        } catch (IOException e) {
            return new BeaconDataSaver(fileName);
        }
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

        // Save data to a JSON file
        dataSaver.saveToFile("beacon_data.json");

        // Load data from the JSON file
        BeaconDataSaver loadedData = BeaconDataSaver.loadFromFile("beacon_data.json");
        if (loadedData != null) {
            System.out.println("Loaded data:");
            System.out.println(loadedData.beaconData);
        }
    }
}
