package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.VibrantBeaconInfo;
import me.kmaxi.lootrunhelper.challenges.ChallengesLoader;
import me.kmaxi.lootrunhelper.data.CurrentData;
import me.kmaxi.lootrunhelper.data.DebuffTracker;

import static me.kmaxi.lootrunhelper.data.CurrentData.addChallengesFailed;
import static me.kmaxi.lootrunhelper.data.CursesTracker.updateCurses;
import static me.kmaxi.lootrunhelper.utils.CodingUtils.msg;

public class Events {

    private static boolean ignoreBeaconShowMessage = false;

    public static void lootrunStarted() {
        BeaconChecker.activeDataSaver().clearData();
    }

    public static void onChooseBeaconMessage(String message) {
        VibrantBeaconInfo.clear();
        VibrantBeaconInfo.updateFromChatMessage(message);

        if (ignoreBeaconShowMessage) {
            ignoreBeaconShowMessage = false;
            return;
        }
        CurrentData.loadFromFile();

        BeaconChecker.enable();
        BeaconChecker.activeDataSaver().updateString();
    }

    public static void enteredChallenge() {
        BeaconChecker.PickClosestBeacon();

        BeaconChecker.disable();

        BeaconChecker.clearCurrentBeacons();
    }

    public static void FinishedChallenge(String message) {

        CurrentData.loadFromFile();
        DebuffTracker.updateDebuffs(message);
        CurrentData.finishedBeacon();
        BeaconChecker.enable();
        ignoreBeaconShowMessage = true;
        updateCurses(message);
        BeaconChecker.activeDataSaver().updateString();
    }
    public static void onChallengeFailed(String noColorMessage) {
        DebuffTracker.updateDebuffs(noColorMessage);

        addChallengesFailed();
    }

    /**
     * Called when the lootrun is completed and when the player fails it
     */
    public static void lootrunCompleted() {
        BeaconChecker.disable();
        BeaconChecker.clearCurrentBeacons();

        CurrentData.resetFile();
        BeaconChecker.activeDataSaver().sendDataToChat();
        ChallengesLoader.clearSavedList();
        ScoreBoardUpdated.reset();

    }

    /**
     * Called when the player classes or goes to hub
     */
    public static void onLeftLootrun() {
        BeaconChecker.disable();
        BeaconChecker.setBeaconListToNull();
        ChallengesLoader.clearSavedList();
        ScoreBoardUpdated.reset();
    }


}
