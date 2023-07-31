package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.VibrantBeaconInfo;
import me.kmaxi.lootrunhelper.data.CurrentData;
import me.kmaxi.lootrunhelper.data.CursesTracker;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.kmaxi.lootrunhelper.utils.CodingUtils.removeColorCodes;

public class ReceiveChatEvent {

    private static boolean ignoreDupe;

    private static boolean ignoreBeaconShowMessage = false;

    public static void receivedChat(String message) {

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

        onChooseBeaconMessage(message);
    }

    private static void onChooseBeaconMessage(String message){
        VibrantBeaconInfo.clear();
        VibrantBeaconInfo.updateFromChatMessage(message);

        if (ignoreBeaconShowMessage){
            ignoreBeaconShowMessage = false;
            return;
        }
        CurrentData.loadFromFile();

        BeaconChecker.enable();
    }
    public static void finalMessage(String message) {

        String noColorMessage = removeColorCodes(message);
        if (!noColorMessage.startsWith("                       ÀÀÀChallenge Completed")) {
            return;
        }
        FinishedChallenge(message);
    }

    private static void FinishedChallenge(String noColorMessage) {

        CurrentData.finishedBeacon();
        BeaconChecker.enable();
        ignoreBeaconShowMessage = true;


        CursesTracker.updateCurses(noColorMessage);
    }



}
