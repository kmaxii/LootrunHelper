package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.VibrantBeaconInfo;
import me.kmaxi.lootrunhelper.data.CurrentData;

public class ReceiveChatEvent {

    private static boolean ignoreDupe;

    public static void receivedChat(String message) {

        //System.out.println("Got message: " + message);

        if (message.startsWith("Select a character!")) {
            BeaconChecker.disable();
            BeaconChecker.stashCurrentBeacons();

            return;
        }

        if (!message.startsWith("\n" +
                "§7§r                         ÀÀ§6§lChoose a Beacon!")) {
            ignoreDupe = false;
            return;
        }

        VibrantBeaconInfo.clear();
        VibrantBeaconInfo.updateFromChatMessage(message);

        CurrentData.loadFromFile();
        BeaconChecker.enable();
    }

}
