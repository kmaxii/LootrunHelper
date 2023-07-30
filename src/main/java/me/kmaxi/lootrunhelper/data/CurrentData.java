package me.kmaxi.lootrunhelper.data;

import me.kmaxi.lootrunhelper.beacon.Beacon;
import me.kmaxi.lootrunhelper.utils.FileUtils;

import java.io.IOException;

public class CurrentData {
    private static JsonHashMap jsonHashMap = new JsonHashMap();
    private static final String VIBRANT_COUNT = "VIBRANT_COUNT";
    private static final String AQUA_STREAK = "AQUA_STREAK";
    private static final String RED_CHALLENGE_COUNT = "RED_CHALLENGE_COUNT";
    private static final String CURSES_COUNT = "CURSES_COUNT";
    private static final String BOON_PICKED_COUNT = "BOON_PICKED_COUNT";
    private static final String BOON_OFFERED_COUNT = "BOON_OFFERED_COUNT";
    private static final String REROLLS_COUNT = "REROLLS_COUNT";
    private static final String PULLS_COUNT = "PULLS_COUNT";
    private static final String BEACONS_OFFERED_COUNT = "BEACONS_OFFERED_COUNT";
    private static final String CHALLENGES_FAILED_COUNT = "CHALLENGES_FAILED_COUNT";
    private static final String CHALLENGES_FROM_WHITE = "CHALLENGES_FROM_WHITE";
    private static final String ENEMY_WALK_SPEED = "ENEMY_WALK_SPEED";
    private static final String ENEMY_ATTACK_SPEED = "ENEMY_ATTACK_APEED";
    private static final String ENEMY_HEALTH = "ENEMY_HEALTH";
    private static final String ENEMY_RESISTANCE = "ENEMY_RESISTANCE";
    private static final String ENEMY_DAMAGE = "ENEMY_DAMAGE";

    private static final String TIME = "TIME";

    private static Beacon currentBacon;


    public static void loadFromFile(){
        try {
            jsonHashMap.loadFromJsonFile(FileUtils.getDataFileName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetFile(){

    }

    public static void addEnemyWalkSpeedCurse(int amount) {
        jsonHashMap.add(ENEMY_WALK_SPEED, amount);
    }

    public static void addEnemyAttackSpeedCurse(int amount) {
        jsonHashMap.add(ENEMY_ATTACK_SPEED, amount);
    }


    public static void addEnemyHealthCurse(int amount) {
        jsonHashMap.add(ENEMY_HEALTH, amount);
    }

    public static void addEnemyResistanceCurse(int amount) {
        jsonHashMap.add(ENEMY_RESISTANCE, amount);
    }

    public static void addEnemyDamageCurse(int amount) {
        jsonHashMap.add(ENEMY_DAMAGE, amount);
    }

    public static int getEnemyWalkSpeedCurse() {
        return jsonHashMap.get(ENEMY_WALK_SPEED);
    }


    public static int getEnemyAttackSpeedCurse() {
        return jsonHashMap.get(ENEMY_ATTACK_SPEED);
    }

    public static int getEnemyHealthCurse() {
        return jsonHashMap.get(ENEMY_HEALTH);
    }

    public static int getEnemyResistanceCurse() {
        return jsonHashMap.get(ENEMY_RESISTANCE);
    }

    public static int getEnemyDamageCurse() {
        return jsonHashMap.get(ENEMY_DAMAGE);
    }

    private static void addAquaStreak(boolean isVibrant) {
        jsonHashMap.add(AQUA_STREAK, isVibrant ? 2 : 1);
    }

    private static int getAquaStreak() {
        return jsonHashMap.get(AQUA_STREAK);
    }

    private static void clearAquaStreak() {
        jsonHashMap.put(AQUA_STREAK, 0);
    }

    public static int getVibrantCount() {
        return jsonHashMap.get(VIBRANT_COUNT);
    }

    private static void addRerolls(int amount) {
        jsonHashMap.add(REROLLS_COUNT, amount);
    }

    public static int getRerollsCount() {
        return jsonHashMap.get(REROLLS_COUNT);
    }


    private static void addPulls(int amount) {
        jsonHashMap.add(PULLS_COUNT, amount);
    }


    public static int getPullsCount() {
        return jsonHashMap.get(PULLS_COUNT);
    }


    private static void addChallengesFromWhite(int amount) {
        jsonHashMap.add(CHALLENGES_FROM_WHITE, amount);
    }


    public static int getChallengesFromWhiteCount() {
        return jsonHashMap.get(CHALLENGES_FROM_WHITE);
    }

    private static void addBeaconOffered() {
        jsonHashMap.add(BEACONS_OFFERED_COUNT);
    }

    public static int getBeaconOfferedCount() {
        return jsonHashMap.get(BEACONS_OFFERED_COUNT);
    }

    private static void addBoonOffered() {
        jsonHashMap.add(BOON_OFFERED_COUNT);
    }

    public static int getBoonOfferedCount() {
        return jsonHashMap.get(BOON_OFFERED_COUNT);
    }

    private static void addBoonPicked() {
        jsonHashMap.add(BOON_PICKED_COUNT);
    }

    private static void addCurses(int amount) {
        jsonHashMap.add(CURSES_COUNT, amount);
    }


    public static int getCursesCount() {
        return jsonHashMap.get(CURSES_COUNT);
    }


    private static void addChallengesFailed() {
        jsonHashMap.add(CHALLENGES_FAILED_COUNT);
    }


    public static int getChallengesFailedCount() {
        return jsonHashMap.get(CHALLENGES_FAILED_COUNT);
    }

    private static void addRedCount(int amount) {
        jsonHashMap.add(RED_CHALLENGE_COUNT, amount);
    }

    public static int getRedChallengeCount() {
        return jsonHashMap.get(RED_CHALLENGE_COUNT);
    }

    private static void removeRed() {
        jsonHashMap.subtract(RED_CHALLENGE_COUNT);
    }


    public static void picketBeacon(Beacon beacon) {
        currentBacon = beacon;
        removeRed();
    }

    public static void finishedBeacon() {
        jsonHashMap.add(currentBacon.beaconType.toString());
        addPulls(1);

        boolean isVibrant = currentBacon.isVibrant;

        if (isVibrant)
            jsonHashMap.add(VIBRANT_COUNT);

        boolean clearAqua = true;


        switch (currentBacon.beaconType) {
            case BLUE:
                break;
            case PURPLE:
                finishedPurple(isVibrant);
                break;
            case RED:
                finishedRed(isVibrant);
                break;
            case AQUA:
                addAquaStreak(isVibrant);
                clearAqua = false;
                break;
            case GREY:
                finishedGray(isVibrant);
                break;
            case WHITE:
                finishedWhite(isVibrant);
                break;
            case DARK_GRAY:
                finishedDarkGray(isVibrant);
                break;
            case RAINBOW:
                clearAqua = false;
        }

        if (clearAqua)
            clearAquaStreak();

        try {
            jsonHashMap.saveToJsonFile(FileUtils.getDataFileName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void finishedGray(boolean isVibrant) {
        addRerolls(calculateEffect(1, isVibrant));
    }

    private static void finishedRed(boolean isVibrant) {
        addRedCount(calculateEffect(2, isVibrant));
    }

    private static void finishedPurple(boolean isVibrant) {
        addCurses(calculateEffect(1, isVibrant));
        addPulls(calculateEffect(1, isVibrant));
    }

    private static void finishedDarkGray(boolean isVibrant) {
        addCurses(calculateEffect(3, isVibrant));
        addPulls(calculateEffect(5, isVibrant));
    }

    private static void finishedWhite(boolean isVibrant) {
        addChallengesFromWhite(calculateEffect(5, isVibrant));
    }

    private static int calculateEffect(int defaultEffectAmount, boolean isVibrant) {
        return (defaultEffectAmount * (getAquaStreak() + 1)) * (isVibrant ? 2 : 1);
    }
}
