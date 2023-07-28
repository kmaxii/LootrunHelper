package me.kmaxi.lootrunhelper.utils;

import me.kmaxi.lootrunhelper.LootrunHelper;

import java.io.*;
import java.net.URL;

public class FileUtils {

    public static void copyLootrunFiles() {
        ClassLoader classLoader = LootrunHelper.class.getClassLoader();
        URL resource = classLoader.getResource("locations");
        if (resource == null) {
            throw new IllegalArgumentException("LootrunHelper - Locations - Folder not found!");
        } else {
            File folder = new File(resource.getFile());
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    System.out.println(file.getName());
                    try {
                        copyFileUsingStream(file, Config.LOCATIONS_DIRS + file.getName());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }


    private static void copyFileUsingStream(File source, String dest) throws IOException {
        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(source);
            File destFile = new File(dest);
            if (!destFile.exists()) {
                destFile.getParentFile().mkdirs();
                destFile.createNewFile();
                os = new FileOutputStream(destFile);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } else {
                System.out.println("Destination file already exists. File not copied.");
            }
        } finally {
            is.close();
            if (os != null) {
                os.close();
            }
        }
    }
}
