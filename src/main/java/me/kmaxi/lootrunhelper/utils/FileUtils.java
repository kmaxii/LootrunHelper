package me.kmaxi.lootrunhelper.utils;

import me.kmaxi.lootrunhelper.LootrunHelper;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {


    public static String getDataFileName(){
        return Config.CONFIG_DIRS + "data_" + ChosenCharacter.getChosenCharacter() + ".json";
    }

    public static String getBeaconListFileName() {
        return Config.CONFIG_DIRS + "beacon_list_" + ChosenCharacter.getChosenCharacter() + ".json";
    }

    public static void copyLootrunFiles() {
        copyFilesFromResources("corkusLootrun.json");
        copyFilesFromResources("seLootrun.json");
        copyFilesFromResources("starts.json");
    }


    private static void copyFilesFromResources(String fileName) {
        InputStream inputStream = LootrunHelper.class.getResourceAsStream("/locations/" + fileName);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(Config.LOCATIONS_DIRS + fileName);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createDirectory(String directoryPath) {
        Path path = Paths.get(directoryPath).getParent();
        if (path != null) {
            File file = new File(path.toString());
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }
    public static boolean doesFileExistInDirectory(String fileName) {

        // Create a File object representing the file we want to check
        File fileToCheck = new File(fileName);

        // Use the exists() method to check if the file exists
        return fileToCheck.exists();
    }

}
