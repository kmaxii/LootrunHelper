package me.kmaxi.lootrunhelper.ui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.joml.Vector2i;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class UiPositions {

    public static final String DESTINATION_KEY = "Destination";
    public static final String BEACON_COUNTER_KEY = "Beacons";

    private static final Gson gson = new Gson();
    private static final String saveFileName = "uiPositions.json";

    private Map<String, Vector2i> Vector2iMap;

    private static UiPositions instance;


    public static UiPositions getInstance() {
        if (instance == null) {
            instance = new UiPositions();
        }
        return instance;
    }

    public UiPositions() {
        loadVector2is();
    }

    private void loadVector2is() {
        File saveFile = new File(saveFileName);

        if (!saveFile.exists()) {
            Vector2iMap = createDefaultVector2is();
            saveVector2is();
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(saveFile));

                // Define the type to deserialize into
                Type mapType = new TypeToken<Map<String, Vector2i>>() {
                }.getType();
                Vector2iMap = gson.fromJson(reader, mapType);

                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, Vector2i> createDefaultVector2is() {
        Map<String, Vector2i> defaultVector2is = new HashMap<>();
        defaultVector2is.put("DESTINATION_KEY", new Vector2i(-10, 10));
        defaultVector2is.put("BEACON_COUNTER_KEY", new Vector2i(-10, 0)); // Example default value

        return defaultVector2is;
    }

    private void saveVector2is() {
        try {
            File saveFile = new File(saveFileName);
            if (!saveFile.exists()) {
                saveFile.createNewFile(); // Create the file if it doesn't exist
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            gson.toJson(Vector2iMap, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vector2i getVector2i(String key) {
        return Vector2iMap.getOrDefault(key, new Vector2i());
    }

    public void setVector2i(String key, Vector2i value) {
        Vector2iMap.put(key, value);
        saveVector2is();
    }

    public static void main(String[] args) {
        UiPositions manager = new UiPositions();
        Vector2i spawnPoint = manager.getVector2i("spawnPoint");
        System.out.println("Spawn point: " + spawnPoint);
    }
}