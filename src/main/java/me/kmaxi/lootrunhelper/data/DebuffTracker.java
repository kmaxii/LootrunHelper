package me.kmaxi.lootrunhelper.data;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DebuffTracker{
    public static void updateDebuffs(String noColorMessage){

        noColorMessage = noColorMessage.substring(0,noColorMessage.indexOf("Mob Health"));
        var cursesNumbers = Arrays.stream(noColorMessage.replaceAll("[^0123456789\n]", "").split("\n")).toList();
        var finalList = cursesNumbers.stream().filter(s -> !s.isEmpty()).collect(Collectors.toList());
        if (finalList.size() == 2) {
            CurrentData.addEnemyDamageChallenge(Integer.parseInt(finalList.get(0)));
            CurrentData.addEnemyHealthChallenge(Integer.parseInt(finalList.get(1)));
        } else if (finalList.size() == 3) {
            CurrentData.addEnemyDamageChallenge(Integer.parseInt(finalList.get(1)));
            CurrentData.addEnemyHealthChallenge(Integer.parseInt(finalList.get(2)));
        }
    }

}