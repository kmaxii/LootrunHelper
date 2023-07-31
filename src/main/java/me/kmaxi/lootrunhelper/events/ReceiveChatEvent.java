package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.VibrantBeaconInfo;
import me.kmaxi.lootrunhelper.data.CurrentData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.tick.SimpleTickScheduler;

import static me.kmaxi.lootrunhelper.data.CursesTracker.updateCurses;

public class ReceiveChatEvent {

    private static boolean ignoreDupes;

    private static boolean ignoreBeaconShowMessage = false;

    public static void receivedChat(String message) {

        System.out.println("Message: " + message);
        if (ignoreDupes) {
            ignoreDupes = false;
            return;
        }
        ignoreDupes = true;


        finalMessage(message);

        if (message.startsWith("Select a character!")) {
            BeaconChecker.disable();
            BeaconChecker.stashCurrentBeacons();

            return;
        }

        if (!message.startsWith("\n" +
                "§7§r                         ÀÀ§6§lChoose a Beacon!")) {
            return;
        }

        onChooseBeaconMessage(message);
    }

    private static void onChooseBeaconMessage(String message) {
        VibrantBeaconInfo.clear();
        VibrantBeaconInfo.updateFromChatMessage(message);

        if (ignoreBeaconShowMessage) {
            ignoreBeaconShowMessage = false;
            return;
        }
        CurrentData.loadFromFile();

        BeaconChecker.enable();
    }

    public static void finalMessage(String message) {

        if (!message.startsWith("\n                       ÀÀÀChallenge Completed")) {
            return;
        }
        FinishedChallenge(message);
    }

    private static void FinishedChallenge(String noColorMessage) {

        CurrentData.finishedBeacon();
        BeaconChecker.enable();
        ignoreBeaconShowMessage = true;
        updateCurses(noColorMessage);

    }


}
