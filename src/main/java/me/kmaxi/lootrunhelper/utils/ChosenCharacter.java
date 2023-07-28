package me.kmaxi.lootrunhelper.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChosenCharacter {

    private static int chosenCharacter = Integer.MAX_VALUE;
    private static final String FILE_PATH = Config.CONFIG_DIRS + "last_played_character.txt";
    public static boolean listenForNextClick = false;


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
    }
}
