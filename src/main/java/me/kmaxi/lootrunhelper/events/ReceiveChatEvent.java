package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.VibrantBeaconInfo;
import me.kmaxi.lootrunhelper.data.CurrentData;

public class ReceiveChatEvent {

    private static boolean ignoreDupe;

    public static void receivedChat(String message) {

        System.out.println("Got message: " + message);

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

    public static void finalMessage(String message) {


        message = message.toLowerCase().replaceAll("[^abcdefghijklmnopqrstuvwxyz1234567890%+]", "");
        if (!message.startsWith("challengecompletednextbeaconswillappearsoon"))
            return;

        FinishedChallenge(message);
    }

    private static void FinishedChallenge(String message){

        if (message.contains("curse"))
            updateCurses(message);

    }

    private static void updateCurses(String message){
        String substring = message.substring(message.indexOf("curses") + 6);



        while (substring.length() > 0) {
            if (substring.startsWith("+35%enemywalkspeed")) {
                CurrentData.addEnemyWalkSpeedCurse(35);
                substring = substring.substring(substring.indexOf("+35%enemywalkspeed") + 18);
                continue;
            }
            if (substring.startsWith("+25%enemyattackapeed")) {
                CurrentData.addEnemyAttackSpeedCurse(25);
                substring = substring.substring(substring.indexOf("+25%enemyattackaspeed") + 20);
                continue;
            }
            if (substring.startsWith("+40%enemyhealth")) {
                CurrentData.addEnemyDamageCurse(40);
                substring = substring.substring(substring.indexOf("+40%enemyhealth") + 15);
                continue;
            }
            if (substring.startsWith("+15%enemyresistance")) {
                CurrentData.addEnemyResistanceCurse(15);
                substring = substring.substring(substring.indexOf("+15%enemyresistance") + 19);
                continue;
            }
            if (substring.startsWith("+30%enemydamage")) {
                CurrentData.addEnemyDamageCurse(30);
                substring = substring.substring(substring.indexOf("+30%enemydamage") + 15);
            }
        }
    }


}
