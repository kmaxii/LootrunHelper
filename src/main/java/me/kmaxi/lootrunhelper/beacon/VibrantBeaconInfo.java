package me.kmaxi.lootrunhelper.beacon;

import me.kmaxi.lootrunhelper.utils.CodingUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VibrantBeaconInfo {

    private static HashSet<String> vibrantBeacons = new HashSet<>();

    public static boolean isVibrant(String type) {
        System.out.println("Checking if " + type.toLowerCase() + " is vibrant. Current vibrants are: " + vibrantBeacons);
        return vibrantBeacons.contains(type.toLowerCase());
    }

    public static void addVibrantBeacon(String type) {
        vibrantBeacons.add(type.toLowerCase());
    }

    public static void updateVibrant(List<Beacon> beacons) {
        vibrantBeacons.clear();
        for (Beacon beacon : beacons) {
            if (beacon.isVibrant) {
                vibrantBeacons.add(beacon.beaconType.toString());
            }
        }
    }

    public static void removeVibrantBeacon(BeaconType type) {
        vibrantBeacons.remove(type);
    }

    public static void clear() {
        vibrantBeacons.clear();
    }

    public static void updateFromChatMessage(String message) {
        vibrantBeacons.clear();
        String noColorMessage = CodingUtils.removeColorCodes(message);
        if (!noColorMessage.contains("Vibrant"))
            return;


        for (String vibrantBeacon : extractWordsAfterVibrant(noColorMessage)) {
            addVibrantBeacon(vibrantBeacon);
            System.out.println("Added vibrant beacon: " + vibrantBeacon);
        }

    }

    public static void main(String[] args) {
        updateFromChatMessage(" \n" +
                "§7§r                         ÀÀ§6§lChoose a Beacon!\n" +
                "§7§r             ÀÀÀ§7Walk towards one to start a challenge\n" +
                "\n" +
                "§7§r   §7§a§lVibrant Green Beacon§r      ÀÀÀ§7§b§lVibrant Aqua Beacon\n" +
                "§7§r       ÀÀÀ§7§7+240s Time Bonus.§r              ÀÀÀ§7§7+200% Next Beacon\n" +
                "§7§r    §7Mobs gain no Buffs this§r                  ÀÀ§7Effects\n" +
                "§7§r          ÀÀ§7Challenge only§r                             ÀÀÀ\n" +
                "\n" +
                "§7§r   §7§e§lVibrant Yellow Beacon§r       ÀÀÀ§7§c§lVibrant Red Beacon\n" +
                "§7§r          §7§7Increase Loot§r                  ÀÀÀ§7§7+4 Challenges to\n" +
                "§7§r        À§7Rewards for this§r              §7this Lootrun. Gain no\n" +
                "§7§r          ÀÀ§7challenge only§r                    À§7Time Bonus for\n" +
                "§7§r                   À§r                            ÀÀ§7completing them.\n");
        System.out.println(vibrantBeacons);
    }
    public static String[] extractWordsAfterVibrant(String input) {
        List<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile("Vibrant\\s+(\\w+)");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            words.add(matcher.group(1));
        }

        return words.toArray(new String[0]);
    }
}
