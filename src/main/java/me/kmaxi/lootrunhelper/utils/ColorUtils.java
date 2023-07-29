package me.kmaxi.lootrunhelper.utils;

import java.util.stream.Stream;

public class ColorUtils {

    public static String getCorrectColor(String beaconType){

        String colorText = "";

        switch (beaconType) {
            case "RED":
                colorText = "§cRED: ";
                break;
            case "GREEN":
                colorText = "§aGREEN: ";
                break;
            case "BLUE":
                colorText = "§9BLUE: ";
                break;
            case "PURPLE":
                colorText = "§dPURPLE: ";
                break;
            case "YELLOW":
                colorText = "§eYELLOW: ";
                break;
            case "GRAY":
                colorText = "§7GRAY: ";
                break;
            case "WHITE":
                colorText = "§fWHITE: ";
                break;
            case "ORANGE":
                colorText = "§6ORANGE: ";
                break;
            case "DARK_GRAY":
                colorText = "§8DARK_GRAY: ";
                break;
            case "AQUA":
                colorText = "§bAQUA: ";
                break;
            case "RAINBOW":
                colorText = "§cR§6A§eI§aN§9B§5O§dW: ";
                break;

            default:
                colorText = beaconType + ": ";
        }

        colorText += "§f";
        return colorText;
    }

    public static Stream<String> getColorStream() {
        return Stream.of(
                "BLUE",
                "PURPLE",
                "YELLOW",
                "RED",
                "GREEN",
                "AQUA",
                "ORANGE",
                "GRAY",
                "WHITE",
                "DARK_GRAY",
                "RAINBOW"
        );
    }
}
