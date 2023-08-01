package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.utils.CodingUtils;

import static me.kmaxi.lootrunhelper.events.Events.*;

public class ReceiveChatEvent {

    private static boolean ignoreDupes;


    public static void receivedChat(String message) {
        if (ignoreDupes) {
            ignoreDupes = false;
            return;
        }
        ignoreDupes = true;


        if (message.startsWith("\n                       ÀÀÀChallenge Completed")) {
            FinishedChallenge(message);
            return;
        }

        if (message.startsWith("Select a character!")) {

            onLeftLootrun();
            return;
        }
        if (CodingUtils.removeColorCodes(message).startsWith("\n" +
                "                          ÀÀChallenge Failed!")) {
            onChallengeFailed(message);
            return;
        }

        if (!message.startsWith("\n" +
                "§7§r                         ÀÀ§6§lChoose a Beacon!")) {
            return;
        }


        onChooseBeaconMessage(message);
    }


}
