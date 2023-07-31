package me.kmaxi.lootrunhelper.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class CursesTracker {

    public static void updateCurses(String noColorMessage) {
        if (!noColorMessage.contains("ÀCurses")) {
            return;
        }


        System.out.println(getCurseFromText(noColorMessage));
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

    public static String[] getCurseFromText(String input) {
        List<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile("ÀCurses\\s+\\[\\+(\\d+)%");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            words.add(matcher.group(1));
            System.out.println(matcher.group(1));
        }
        return words.toArray(new String[0]);
    }


    public static void main(String[] args) {
        /*finalMessage("                       ÀÀÀChallenge Completed\n" +
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
    */

        String input = "ÀÀÀChallenge Completed\n" +
                "ÀÀÀNext beacons will appear soon!\n" +
                "\n" +
                "ÀÀ[+15% Mob Damage]\n" +
                "À[+50% Mob Health]\n" +
                "\n" +
                "ÀCurses\n" +
                "ÀÀÀ[+35% Enemy Walk Speed]\n" +
                "ÀÀÀ[+15% Enemy Resistance]\n" +
                "[+25% Enemy Attack Speed]\n" +
                "ÀÀÀ[+35% Enemy Walk Speed]\n" +
                "ÀÀÀ[+35% Enemy Walk Speed]\n" +
                "ÀÀÀ[+15% Enemy Resistance]\n" +
                "ÀÀÀ[+15% Enemy Resistance]\n" +
                "ÀÀÀ[+35% Enemy Walk Speed]\n" +
                "ÀÀÀ[+40% Enemy Health]\n" +
                "[+30% Enemy Damage]\n" +
                "ÀÀÀ[+40% Enemy Health]\n" +
                "ÀÀÀ[+40% Enemy Health]\n" +
                "ÀÀÀ[+40% Enemy Health]\n" +
                "ÀÀÀ[+40% Enemy Health]\n" +
                "[+30% Enemy Damage]\n" +
                "[+30% Enemy Damage]\n" +
                "[+30% Enemy Damage]\n" +
                "ÀÀÀ[+40% Enemy Health]";

        var cursesNumbers = Arrays.stream(input.replaceAll("[^0123456789\n]", "").split("\n")).toList();
        var finalList = cursesNumbers.stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());
        System.out.println(finalList);
       // List<Integer> cursesNumbers = extractCursesNumbers(input);
     //   System.out.println(cursesNumbers);
    }

    public static List<Integer> extractCursesNumbers(String input) {
        List<Integer> numbers = new ArrayList<>();
        Pattern pattern = Pattern.compile("curse.*?\\[\\+(\\d+)%\\]");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String numberStr = matcher.group(1);
            int number = Integer.parseInt(numberStr);
            numbers.add(number);
        }

        return numbers;
    }
}
