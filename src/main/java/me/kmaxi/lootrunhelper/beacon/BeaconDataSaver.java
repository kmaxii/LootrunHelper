package me.kmaxi.lootrunhelper.beacon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.kmaxi.lootrunhelper.data.CurrentData;
import me.kmaxi.lootrunhelper.utils.ChosenCharacter;
import me.kmaxi.lootrunhelper.utils.ColorUtils;
import me.kmaxi.lootrunhelper.utils.message.CenteredTextSender;
import net.minecraft.client.MinecraftClient;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class BeaconDataSaver {

    private HashMap<String, Integer> beaconData;

    private String fileName;

    private static String beaconDataString = "";

    public BeaconDataSaver(String fileName) {
        beaconData = new HashMap<>();
        initializeBeaconData();
        this.fileName = fileName;
    }

    private void initializeBeaconData() {
        beaconData.put("BLUE", 0);
        beaconData.put("PURPLE", 0);
        beaconData.put("YELLOW", 0);
        beaconData.put("GREEN", 0);
        beaconData.put("AQUA", 0);
        beaconData.put("ORANGE", 0);
        beaconData.put("GRAY", 0);
        beaconData.put("RED", 0);
        beaconData.put("WHITE", 0);
        beaconData.put("DARK_GRAY", 0);
        beaconData.put("RAINBOW", 0);
    }

    public void clearData() {
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

    public static BeaconDataSaver loadFromFile() {
        String fileName = "beacon_data_" + ChosenCharacter.getChosenCharacter() +  ".json";
        try (Reader reader = new FileReader(fileName)) {
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, Integer>>() {
            }.getType();
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



    public String getData() {
        return beaconDataString;
    }

    public void updateString(){
        if (beaconData == null) {
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();

        ColorUtils.getColorStream().forEach(color -> {
            stringBuilder.append(getFormattedData(color)).append("\n");
        });

        beaconDataString = stringBuilder.toString();
    }


    private String getFormattedData(String key) {
        String finalText = "ERROR! WRONG KEY: §c" + key;
        switch (key) {
            case "RED":
                finalText = "§cRED: §f" + beaconData.get(key) + "/10";
                break;
            case "GREEN":
                finalText = "§aGREEN: §f" + beaconData.get(key) + "/10";
                break;
            case "BLUE":
                finalText = "§9BLUE: §f" + beaconData.get(key) + "/∞";
                break;
            case "PURPLE":
                finalText = "§dPURPLE: §f" + beaconData.get(key) + "/∞";
                break;
            case "YELLOW":
                finalText = "§eYELLOW: §f" + beaconData.get(key) + "/∞";
                break;
            case "GRAY":
                finalText = "§7GRAY: §f" + beaconData.get(key) + "/1";
                break;
            case "WHITE":
                finalText = "§fWHITE: §f" + beaconData.get(key) + "/1";
                break;
            case "ORANGE":
                finalText = "§6ORANGE: §f" + beaconData.get(key) + "/2";
                break;
            case "DARK_GRAY":
                finalText = "§8DARK_GRAY: §f" + beaconData.get(key) + "/1";
                break;
            case "AQUA":
                finalText = "§bAQUA: " + getColorForAqua() + beaconData.get(key) + "/10";
                break;
            case "RAINBOW":
                finalText = "§cR§6A§eI§aN§9B§5O§dW: §f" + beaconData.get(key) + "/1";
                break;
        }

        return finalText;
    }

    private String getColorForAqua(){
        int aquaShownStreak = CurrentData.getCurrentAquaShownStreak();

        if (beaconData.get(BeaconType.AQUA.toString()) == 10 || aquaShownStreak == 0)
            return "§f";

        return aquaShownStreak == 1 || aquaShownStreak == 2 && CurrentData.getAquaStreak() != 0 ? "§a" : "§c";
    }

    public AquaType aquaInfo(){
        int aquaShownStreak = CurrentData.getCurrentAquaShownStreak();

        if (beaconData.get(BeaconType.AQUA.toString()) == 10 || aquaShownStreak == 0)
            return AquaType.NONE;

        return aquaShownStreak == 1 || aquaShownStreak == 2 && CurrentData.getAquaStreak() != 0 ? AquaType.GOOD : AquaType.BAD;
    }

    public String getBeaconDataCentered() {

        assert MinecraftClient.getInstance().player != null;

        StringBuilder toSend = new StringBuilder();
        AtomicReference<String> lastBeacon = new AtomicReference<>("");
        AtomicReference<String> lastBeacon2 = new AtomicReference<>("");
        ColorUtils.getColorStream().forEach(color -> {
            String beaconInfo = getFormattedData(color);
            if (lastBeacon.get().isEmpty()){
                lastBeacon.set(beaconInfo);
            } else if (lastBeacon2.get().isEmpty()){
                lastBeacon2.set(beaconInfo);
            }
            else {
                toSend.append(CenteredTextSender.getCenteredMessage(lastBeacon.get(), lastBeacon2.get(), beaconInfo.trim()));
                toSend.append("\n");
                lastBeacon.set("");
                lastBeacon2.set("");
            }
        });
        toSend.append(CenteredTextSender.getCenteredMessage(lastBeacon.get(), "", lastBeacon2.get()));
        return toSend.toString();
    }

}
