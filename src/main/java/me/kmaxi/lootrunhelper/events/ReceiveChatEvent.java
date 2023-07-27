package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;

public class ReceiveChatEvent {

    public static void receivedChat(String message) {
        System.out.println("got message: " + message);

        if (message.startsWith("Select a character!")) {
            BeaconChecker.disable();
            return;
        }

        if (!message.startsWith("\n" +
                "§7§r                         ÀÀ§6§lChoose a Beacon!")) {
            return;
        }

        BeaconChecker.enable();
    }

}
