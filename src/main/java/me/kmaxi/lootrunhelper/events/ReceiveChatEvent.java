package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;

public class ReceiveChatEvent {

    private static boolean ignoreDupe;

    public static void receivedChat(String message) {


        if (message.startsWith("Select a character!")) {
            BeaconChecker.disable();
            BeaconChecker.clearCurrentBeacons();

            return;
        }

        if (!message.startsWith("\n" +
                "§7§r                         ÀÀ§6§lChoose a Beacon!")) {
            ignoreDupe = false;
            return;
        }

        BeaconChecker.enable();
    }

}
