package me.kmaxi.lootrunhelper.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ChosenCharacter {

    public static boolean listenForNextClick = false;


    /**
     * Gets the uuid of the current character by analyzing the soul point item which contains a unique id in the lore
     * @return The id or NULL if an error was encountered
     */
    public static String getChosenCharacter() {

        if (MinecraftClient.getInstance().player == null || MinecraftClient.getInstance().player.getInventory() == null)
            return "NULL";

        ItemStack soulPointItem = MinecraftClient.getInstance().player.getInventory().main.get(8);

        String id = soulPointItem.getOrCreateSubNbt("display").toString();
        id = id.substring(id.indexOf("§8") + 2, id.lastIndexOf("\""));

        return id;
    }
/*
    public static void setChosenCharacter(int character) {
        chosenCharacter = character;
        saveToFile(character);
    }


    public static int getChosenCharacter() {
        if (chosenCharacter == Integer.MAX_VALUE) {
            int characterFromFile = loadFromFile();
            chosenCharacter = characterFromFile;
        }
        return chosenCharacter;
    }

    private static void saveToFile(int character) {
        try {
            Path path = Paths.get(FILE_PATH);
            Files.createDirectories(path.getParent());
            try (FileWriter writer = new FileWriter(path.toFile())) {
                writer.write(String.valueOf(character));
            }
        } catch (IOException e) {
            // Handle exception if needed
            e.printStackTrace();
        }
    }
    private static int loadFromFile() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return Integer.MAX_VALUE;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine();
                if (line != null && !line.isEmpty()) {
                    return Integer.parseInt(line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            // Handle exceptions if needed
            e.printStackTrace();
        }
        return Integer.MAX_VALUE;
    }*/
}
