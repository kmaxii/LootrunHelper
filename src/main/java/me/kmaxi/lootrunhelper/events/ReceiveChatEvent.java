package me.kmaxi.lootrunhelper.events;

import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.VibrantBeaconInfo;
import me.kmaxi.lootrunhelper.data.CurrentData;

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
        if (!BeaconChecker.isEnabled())
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

        if (!noColorMessage.contains("ÀCurses")) {
            return;
        }
        updateCurses(noColorMessage);
    }

    private static void updateCurses(String noColorMessage) {

        String noColorMessageSubstring = noColorMessage.substring(noColorMessage.indexOf("ÀCurses") + 8);
        System.out.println(noColorMessageSubstring);
        /*                       ÀÀÀChallenge Completed
                  ÀÀÀNext beacons will appear soon!

                          ÀÀ[+15% Mob Damage]
                           À[+50% Mob Health]

                                 ÀCurses
                      ÀÀÀ[+35% Enemy Walk Speed]
                      ÀÀÀ[+15% Enemy Resistance]
                     [+25% Enemy Attack Speed]
                      ÀÀÀ[+35% Enemy Walk Speed]
                      ÀÀÀ[+35% Enemy Walk Speed]
                      ÀÀÀ[+15% Enemy Resistance]
                      ÀÀÀ[+15% Enemy Resistance]
                      ÀÀÀ[+35% Enemy Walk Speed]
                         ÀÀÀ[+40% Enemy Health]
                         [+30% Enemy Damage]
                         ÀÀÀ[+40% Enemy Health]
                         ÀÀÀ[+40% Enemy Health]
                         ÀÀÀ[+40% Enemy Health]
                         ÀÀÀ[+40% Enemy Health]
                         [+30% Enemy Damage]
                         [+30% Enemy Damage]
                         [+30% Enemy Damage]
                         ÀÀÀ[+40% Enemy Health]*/

    }

    /*public static String[] extractWordsAfterCurses(String input){
        List<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile("Curses\n")
    }*/
    public static void main(String[] args) {
        finalMessage("                       ÀÀÀChallenge Completed\n" +
                "                  ÀÀÀNext beacons will appear soon!\n" +
                "\n" +
                "                          ÀÀ[+15% Mob Damage]\n" +
                "                           À[+50% Mob Health]\n" +
                "\n" +
                "                                 ÀCurses\n" +
                "                      ÀÀÀ[+35% Enemy Walk Speed]\n" +
                "                      ÀÀÀ[+15% Enemy Resistance]\n" +
                "                     [+25% Enemy Attack Speed]\n" +
                "                      ÀÀÀ[+35% Enemy Walk Speed]\n" +
                "                      ÀÀÀ[+35% Enemy Walk Speed]\n" +
                "                      ÀÀÀ[+15% Enemy Resistance]\n" +
                "                      ÀÀÀ[+15% Enemy Resistance]\n" +
                "                      ÀÀÀ[+35% Enemy Walk Speed]\n" +
                "                         ÀÀÀ[+40% Enemy Health]\n" +
                "                         [+30% Enemy Damage]\n" +
                "                         ÀÀÀ[+40% Enemy Health]\n" +
                "                         ÀÀÀ[+40% Enemy Health]\n" +
                "                         ÀÀÀ[+40% Enemy Health]\n" +
                "                         ÀÀÀ[+40% Enemy Health]\n" +
                "                         [+30% Enemy Damage]\n" +
                "                         [+30% Enemy Damage]\n" +
                "                         [+30% Enemy Damage]\n" +
                "                         ÀÀÀ[+40% Enemy Health]");
    }


}
