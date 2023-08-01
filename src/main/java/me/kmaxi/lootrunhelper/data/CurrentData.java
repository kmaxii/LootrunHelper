package me.kmaxi.lootrunhelper.data;

import me.kmaxi.lootrunhelper.beacon.Beacon;
import me.kmaxi.lootrunhelper.beacon.BeaconType;
import me.kmaxi.lootrunhelper.utils.CodingUtils;
import me.kmaxi.lootrunhelper.utils.FileUtils;

import java.io.IOException;
import java.util.List;

import static me.kmaxi.lootrunhelper.utils.CodingUtils.msg;

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
    private static final String CHALLENGES_FINISHED_COUNT = "CHALLENGES_FINISHED_COUNT";
    private static final String CHALLENGES_FROM_WHITE = "CHALLENGES_FROM_WHITE";
    private static final String ENEMY_WALK_SPEED = "ENEMY_WALK_SPEED";
    private static final String ENEMY_ATTACK_SPEED = "ENEMY_ATTACK_SPEED";
    private static final String ENEMY_HEALTH = "ENEMY_HEALTH";
    private static final String ENEMY_RESISTANCE = "ENEMY_RESISTANCE";
    private static final String ENEMY_DAMAGE = "ENEMY_DAMAGE";
    private static final String CURRENT_LOOTRUN_NAME = "CURRENT_LOOTRUN_NAME";

    private static final String TIME = "TIME";
    private static final String ENEMY_HEALTH_CHALLENGE = "ENEMY_HEALTH_CHALLENGE";
    private static final String ENEMY_DAMAGE_CHALLENGE = "ENEMY_DAMAGE_CHALLENGE";

    private static final String BEACON_OFFERED_APPEND = "_OFFERED";
    private static final String CURRENT_AQUA_SHOWN_STREAK = "CURRENT_AQUA_SHOWN_STREAK";
    private static final String CURRENT_GREY_SHOWN_STREAK = "CURRENT_GREY_SHOWN_STREAK";
    private static final String LAST_SAVED_OFFERED = "LAST_SAVED_OFFERED";
    private static final String SAVED_FIRST_CHOICES = "SAVED_FIRST_CHOICES";
    private static final String REROLLS_USED_COUNT = "REROLLS_USED_COUNT";

    public static boolean hasSavedFirstChoices(){
        return jsonHashMap.get(SAVED_FIRST_CHOICES) == 1;
    }

    public static void saveFirstChoices(){
        jsonHashMap.put(SAVED_FIRST_CHOICES, 1);
    }

    public static int getRerollsUsedCount() {
        return jsonHashMap.get(REROLLS_USED_COUNT);
    }

    public static void addRerollsUsedCount() {
        jsonHashMap.add(REROLLS_USED_COUNT);
    }

    private static int getLastSavedOffered() {
        return jsonHashMap.get(LAST_SAVED_OFFERED);
    }

    private static void setLastSavedOffered(int number) {
        jsonHashMap.put(LAST_SAVED_OFFERED, number);
    }
    /**
     * Calculates how many times the player has been given a choice of beacons
     * @return Challenges completed + challenges failed + rerolls used
     */
    private static int getOfferedChoicesAmount() {
        return getChallengesFailedCount() + getFinishedChallengesCount() + getRerollsUsedCount();
    }

    /**
     * Adds to number of choices given if these have not been saved already
     * @param beaconType a list of all beacons offered right now
     */
    public static void saveBeaconChoices(List<BeaconType> beaconType) {
        int offeredChoicesAmount = getOfferedChoicesAmount();
        if (offeredChoicesAmount == getLastSavedOffered() && offeredChoicesAmount  != 0
        || hasSavedFirstChoices() && offeredChoicesAmount == 0) {
            return;
        }
        saveFirstChoices();

        setLastSavedOffered(offeredChoicesAmount);

        boolean foundAqua = false;
        boolean foundGrey = false;
        for (BeaconType type : beaconType) {
            addBeaconOfferedCount();

            jsonHashMap.add(type.toString() + BEACON_OFFERED_APPEND);
            switch (type) {
                case AQUA:
                    foundAqua = true;
                    addCurrentAquaShownStreak();
                    break;
                case GREY:
                    foundGrey = true;
                    addCurrentGreyShownStreak();
                    break;
            }
        }
        if (!foundAqua) {
            resetAquaShownStreak();
        }
        if (!foundGrey) {
            resetGreyShownStreak();
        }
        saveJson();
    }


    private static void addCurrentAquaShownStreak() {
        jsonHashMap.add(CURRENT_AQUA_SHOWN_STREAK);
    }

    private static void addCurrentGreyShownStreak() {
        jsonHashMap.add(CURRENT_GREY_SHOWN_STREAK);
    }

    private static void resetAquaShownStreak() {
        jsonHashMap.put(CURRENT_AQUA_SHOWN_STREAK, 0);
    }

    private static void resetGreyShownStreak() {
        jsonHashMap.put(CURRENT_GREY_SHOWN_STREAK, 0);
    }
    public static int getCurrentAquaShownStreak() {
        return jsonHashMap.get(CURRENT_AQUA_SHOWN_STREAK);
    }
    public static int getCurrentGreyShownStreak() {
        return jsonHashMap.get(CURRENT_GREY_SHOWN_STREAK);
    }


    private static Beacon currentBacon;

    public static void saveJson() {
        try {
            jsonHashMap.saveToJsonFile(FileUtils.getDataFileName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void clearCurrent() {
        saveJson();
        jsonHashMap.reset();
    }

    public static void loadFromFile() {
        try {
            jsonHashMap.loadFromJsonFile(FileUtils.getDataFileName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resetFile() {
        jsonHashMap.reset();
        try {
            jsonHashMap.saveToJsonFile(FileUtils.getDataFileName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setCurrentLootrunIndex(int index) {
        jsonHashMap.put(CURRENT_LOOTRUN_NAME, index);
    }

    public static int getCurrentLootrunIndex() {
        return jsonHashMap.contains(CURRENT_LOOTRUN_NAME) ? jsonHashMap.get(CURRENT_LOOTRUN_NAME) : Integer.MAX_VALUE;
    }

    public static void addEnemyDamageChallenge(int amount) {
        jsonHashMap.add(ENEMY_DAMAGE_CHALLENGE, amount);
    }

    public static void addEnemyHealthChallenge(int amount) {
        jsonHashMap.add(ENEMY_HEALTH_CHALLENGE, amount);
    }

    public static int getEnemyDamageChallenge() {
        return jsonHashMap.get(ENEMY_DAMAGE_CHALLENGE);
    }

    public static int getEnemyHealthChallenge() {
        return jsonHashMap.get(ENEMY_HEALTH_CHALLENGE);
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

    public static int getAquaStreak() {
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

    public static int getEffectivePulls() {
        return getPullsCount() + getPullsCount() * getRerollsCount();
    }


    private static void addChallengesFromWhite(int amount) {
        jsonHashMap.add(CHALLENGES_FROM_WHITE, amount);
    }


    public static int getChallengesFromWhiteCount() {
        return jsonHashMap.get(CHALLENGES_FROM_WHITE);
    }

    private static void addBeaconOfferedCount() {
        jsonHashMap.add(BEACONS_OFFERED_COUNT);
    }

    public static int getBeaconOfferedCount() {
        return jsonHashMap.get(BEACONS_OFFERED_COUNT);
    }

    private static void addBoonsOffered(int amount) {
        jsonHashMap.add(BOON_OFFERED_COUNT, amount);
    }

    public static int getBoonOfferedCount() {
        return jsonHashMap.get(BOON_OFFERED_COUNT);
    }

    private static void addBoonPicked() {
        jsonHashMap.add(BOON_PICKED_COUNT);
    }

    public static int getBoonPickedCount() {
        return jsonHashMap.get(BOON_PICKED_COUNT);
    }

    private static void addCurses(int amount) {
        jsonHashMap.add(CURSES_COUNT, amount);
    }


    public static int getCursesCount() {
        return jsonHashMap.get(CURSES_COUNT);
    }


    public static void addChallengesFailed() {
        jsonHashMap.add(CHALLENGES_FAILED_COUNT);
        currentBacon = null;
        CurrentData.saveJson();
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
        if (beacon == null || beacon.beaconType == null) {
            System.out.println("BEACON IS NULL THAT WAS PICKED");
            msg("BEACON IS NULL THAT WAS PICKED");
            return;
        }
        currentBacon = beacon;
        removeRed();
        saveJson();
    }

    public static void finishedBeacon() {
        if (currentBacon == null || currentBacon.beaconType == null) {
            currentBacon = null;
            System.out.println("Finished beacon is null");
            return;
        }

        jsonHashMap.add(currentBacon.beaconType.toString());
        addPulls(1);

        boolean isVibrant = currentBacon.isVibrant;

        if (isVibrant)
            jsonHashMap.add(VIBRANT_COUNT);

        boolean clearAqua = true;


        switch (currentBacon.beaconType) {
            case BLUE:
                finishedBlue(isVibrant);
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

        addFinishedChallenge();
        saveJson();
        currentBacon = null;
    }

    private static void addFinishedChallenge() {
        jsonHashMap.add(CHALLENGES_FINISHED_COUNT);
    }


    public static int getFinishedChallengesCount() {
        return jsonHashMap.get(CHALLENGES_FINISHED_COUNT);
    }


    private static void finishedGray(boolean isVibrant) {
        addRerolls(calculateEffect(1, isVibrant));
    }

    private static void finishedBlue(boolean isVibrant) {
        addBoonPicked();
        int boonsOffered = 1 + calculateEffect(1, isVibrant);
        addBoonsOffered(boonsOffered);
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
