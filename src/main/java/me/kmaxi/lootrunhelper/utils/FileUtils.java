package me.kmaxi.lootrunhelper.utils;

import me.kmaxi.lootrunhelper.LootrunHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    public static void copyLootrunFiles() {
        copyFilesFromResources("locationCorkus.json");
        copyFilesFromResources("locationsSE.json");
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
}
