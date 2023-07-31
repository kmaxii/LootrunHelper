package me.kmaxi.lootrunhelper.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static me.kmaxi.lootrunhelper.data.CurrentData.*;
import static me.kmaxi.lootrunhelper.events.ReceiveChatEvent.receivedChat;


public class CursesTracker {

    public static void updateCurses(String noColorMessage) {
        if (!noColorMessage.contains("ÀCurses")) {
            return;
        }
        noColorMessage = noColorMessage.substring(noColorMessage.indexOf("ÀCurses") + 7);
        var cursesNumbers = Arrays.stream(noColorMessage.replaceAll("[^0123456789\n]", "").split("\n")).toList();
        var finalList = cursesNumbers.stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());
        List<Integer> curseNums = new ArrayList(Arrays.asList(15,25,30,35,40));
        for (int i = 0; i < finalList.size(); i++) {
            switch (finalList.get(i)){
                case "15":
                    addEnemyResistanceCurse(15);
                    break;
                case "25":
                    addEnemyAttackSpeedCurse(25);
                    break;
                case "30":
                    addEnemyDamageCurse(30);
                    break;
                case "35":
                    addEnemyWalkSpeedCurse(35);
                    break;
                case "40":
                    addEnemyHealthCurse(40);
                    break;
            }
        }
    }
}
