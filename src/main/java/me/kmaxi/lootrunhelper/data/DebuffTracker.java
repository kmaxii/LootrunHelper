package me.kmaxi.lootrunhelper.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DebuffTracker{
    private static final String NUMBER_PERCENT_PATTERN = "(\\+|-)?(\\d+)%?";
    public static void updateDebuffs(String message){

        for (String s : extractTextBetweenBrackets(message)) {
            saveDebuff(s);
        }
        CurrentData.saveJson();
    }


    /**
     *
     * @param input The Input should be of type "+x% [Name]" example: +40% Enemy Health
     */
    public static void saveDebuff(String input) {
        Pattern pattern = Pattern.compile(NUMBER_PERCENT_PATTERN);
        Matcher matcher = pattern.matcher(input);

        // If it did not contain the right pattern
        if (!matcher.find())
            return;

        String type = input.replace(matcher.group(), "").trim();
        int number = Integer.parseInt(matcher.group(2)); // Group 2 contains the effect amount

        switch (type) {
            case "Mob Damage":
                CurrentData.addEnemyDamageChallenge(number);
                break;
            case "Mob Health":
                CurrentData.addEnemyHealthChallenge(number);
                break;
            case "Enemy Health":
                CurrentData.addEnemyHealthCurse(number);
                break;
            case "Enemy Attack Speed":
                CurrentData.addEnemyAttackSpeedCurse(number);
                break;
            case "Enemy Damage":
                CurrentData.addEnemyDamageCurse(number);
                break;
            case "Enemy Walk Speed":
                CurrentData.addEnemyWalkSpeedCurse(number);
                break;
            case "Enemy Resistance":
                CurrentData.addEnemyResistanceCurse(number);
                break;
        }

    }


    public static List<String> extractTextBetweenBrackets(String input) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String match = matcher.group(1);
            result.add(match);
        }

        return result;
    }
}