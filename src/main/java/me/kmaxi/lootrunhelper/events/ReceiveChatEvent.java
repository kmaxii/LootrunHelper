package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.VibrantBeaconInfo;
import me.kmaxi.lootrunhelper.challenges.ChallengesLoader;
import me.kmaxi.lootrunhelper.data.CurrentData;
import me.kmaxi.lootrunhelper.utils.CodingUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.tick.SimpleTickScheduler;

import static me.kmaxi.lootrunhelper.data.CursesTracker.updateCurses;
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
        }

        if (message.startsWith("Select a character!")) {

            onSwitchCharacterMessage();
            return;
        }

        if (!message.startsWith("\n" +
                "§7§r                         ÀÀ§6§lChoose a Beacon!")) {
            return;
        }

        onChooseBeaconMessage(message);
    }







}
