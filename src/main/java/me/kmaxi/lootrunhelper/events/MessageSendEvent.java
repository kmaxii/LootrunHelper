package me.kmaxi.lootrunhelper.events;

import java.util.regex.Pattern;

public class MessageSendEvent {

    private static final String HUB_REGGEX = "(?i)hub|lobby";


    public static boolean isHubCommand(String message) {
        return Pattern.compile(HUB_REGGEX).matcher(message).matches();
    }

    public static void sendMessage(String message) {

        if (isHubCommand(message)) {
            Events.onLeftLootrun();
        }
    }
}
