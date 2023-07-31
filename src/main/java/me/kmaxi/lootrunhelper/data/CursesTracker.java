package me.kmaxi.lootrunhelper.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static me.kmaxi.lootrunhelper.events.ReceiveChatEvent.finalMessage;

public class CursesTracker {

    public static void updateCurses(String noColorMessage) {
        if (!noColorMessage.contains("ÀCurses")) {
            return;
        }

        String noColorMessageSubstring = noColorMessage.substring(noColorMessage.indexOf("ÀCurses") + 8);
        System.out.println(getCurseFromText(noColorMessageSubstring));
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
    public static String[] getCurseFromText(String input){
        List<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            words.add(matcher.group());
        }
        return words.toArray(new String[0]);
    }


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
