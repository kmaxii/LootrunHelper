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

    public static void main(String[] args) {
        String message1 = "hub";
        String message2 = "Lobby";
        String message3 = "HUB";
        String message4 = "lobby";
        String message5 = "random";


        System.out.println("Message 1: " + isHubCommand(message1)); // true
        System.out.println("Message 2: " + isHubCommand(message2)); // true
        System.out.println("Message 3: " + isHubCommand(message3)); // true
        System.out.println("Message 4: " + isHubCommand(message4)); // true
        System.out.println("Message 5: " + isHubCommand(message5)); // false


    }
}
