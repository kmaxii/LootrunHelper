package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;

public class ReceiveTitleEvent {

    /**
     * If the next received title should be ignored. Useful because this method is called twice for each title
     */
    private static boolean ignoreNext;
    private static boolean ignoreDupe;
    public static void receivedTitle(String title){
        title = title.toLowerCase().replaceAll("[^abcdefghijklmnopqrstuvwxyz?.! /]", "");

        System.out.println("Title: " + title);

        if (title.contains("prepare to lootrun")){
            ignoreDupe = false;
            lootrunStarted();
            return;
        }

        if (title.contains("lootrun completed")&&!ignoreDupe){
            ignoreDupe= true;
            lootrunCompleted();
            return;
        }

        if (title.contains("lootrun failed" ) && !ignoreDupe){
            ignoreDupe= true;
            lootrunCompleted();
            return;
        }


        if (!isChallengeTitle(title) || ignoreNext) {
            ignoreNext = false;
            return;
        }
        ignoreNext = true;

        enteredChallenge();

    }

    private static void lootrunStarted(){
        BeaconChecker.activeDataSaver().clearData();
    }

    private static void lootrunCompleted(){
        BeaconChecker.disable();
        BeaconChecker.clearCurrentBeacons();


        BeaconChecker.activeDataSaver().sendDataToChat();
    }

    private static void enteredChallenge(){
        BeaconChecker.PickClosestBeacon();

        BeaconChecker.disable();

        BeaconChecker.clearCurrentBeacons();
    }

    private static boolean isChallengeTitle(String title){
        return title.equalsIgnoreCase("spelunk!")
                || title.equalsIgnoreCase("defend!")
                || title.equalsIgnoreCase("slay target!")
                || title.equalsIgnoreCase("destroy");
    }
}
