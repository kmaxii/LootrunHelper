package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ReceiveTitleEvent {

    /**
     * If the next received title should be ignored. Useful because this method is called twice for each title
     */
    private static boolean ignoreNext;
    public static void receivedTitle(String title){
        title = title.toLowerCase().replaceAll("[^abcdefghijklmnopqrstuvwxyz?.! /]", "");

        System.out.println("Title: " + title);

        if (title.contains("prepare to lootrun")){
            lootrunStarted();
            return;
        }

        if (title.contains("lootrun completed")){
            lootrunCompleted();
            return;
        }

        if (title.contains("lootrun failed")){
            lootrunCompleted();
            return;
        }


        if (!isChallengeTitle(title) || ignoreNext) {
            ignoreNext = false;
            return;
        }
        ignoreNext = true;

        beaconOptionShowed();

    }

    private static void lootrunStarted(){
        BeaconChecker.activeDataSaver().clearData();
    }

    private static void lootrunCompleted(){
        BeaconChecker.disable();
        BeaconChecker.activeDataSaver().sendDataToChat();
    }

    private static void beaconOptionShowed(){
        BeaconChecker.PickClosestBeacon();

        MinecraftClient.getInstance().player.sendMessage(Text.of("Started Challange"));
        BeaconChecker.disable();
    }

    private static boolean isChallengeTitle(String title){
        return title.equalsIgnoreCase("spelunk!")
                || title.equalsIgnoreCase("defend!")
                || title.equalsIgnoreCase("slay target!")
                || title.equalsIgnoreCase("destroy");
    }
}
