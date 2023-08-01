package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.utils.CodingUtils;

import java.util.regex.Pattern;

public class SentCommandEvent {

    private static final String HUB_REGGEX = "(?i)hub|lobby";


    public static boolean isHubCommand(String message) {
        return Pattern.compile(HUB_REGGEX).matcher(message).matches();
    }

    public static void sentCommand(String message) {

        CodingUtils.msg("Chat command: " + message);
        if (isHubCommand(message)) {
            Events.onLeftLootrun();
            return;
        }
        if (message.equalsIgnoreCase("rerollbeacon")){
            Events.rerolledBeacons();
        }
    }

}
