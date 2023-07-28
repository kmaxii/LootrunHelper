package me.kmaxi.lootrunhelper.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            writer.write(String.valueOf(character));
        } catch (IOException e) {
            // Handle exception if needed
            e.printStackTrace();
        }
    }

    private static int loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                return Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            // Handle exceptions if needed
            e.printStackTrace();
        }
        return Integer.MAX_VALUE;
    }
}
