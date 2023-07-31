package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.VibrantBeaconInfo;
import me.kmaxi.lootrunhelper.data.CurrentData;
import me.kmaxi.lootrunhelper.data.CursesTracker;
import me.kmaxi.lootrunhelper.utils.CodingUtils;

import static me.kmaxi.lootrunhelper.utils.CodingUtils.removeColorCodes;

public class ReceiveChatEvent {

    private static boolean ignoreDupe;

    private static boolean ignoreBeaconShowMessage = false;

    public static void receivedChat(String message) {

        if (ignoreDupe){
            ignoreDupe = false;
            return;
        }
        ignoreDupe = true;

        String noColorMessage = removeColorCodes(message);
        if (noColorMessage.startsWith("                       ÀÀÀChallenge Completed")) {
            FinishedChallenge(message);
        }


        if (message.startsWith("Select a character!")) {
            BeaconChecker.disable();
            BeaconChecker.stashCurrentBeacons();

            System.out.println("Showed select character");

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

        CodingUtils.msg("Choose a beacon!");

        if (ignoreBeaconShowMessage) {
            ignoreBeaconShowMessage = false;
            return;
        }
        CurrentData.loadFromFile();

        BeaconChecker.enable();
    }


    private static void FinishedChallenge(String noColorMessage) {

        CurrentData.finishedBeacon();
        BeaconChecker.enable();
        ignoreBeaconShowMessage = true;


     //   CursesTracker.updateCurses(noColorMessage);
    }


}
