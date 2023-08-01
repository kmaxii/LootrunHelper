package me.kmaxi.lootrunhelper.beacon;

import me.kmaxi.lootrunhelper.challenges.Challenge;
import me.kmaxi.lootrunhelper.challenges.ChallengeBeaconInfo;
import me.kmaxi.lootrunhelper.challenges.ChallengesLoader;
import me.kmaxi.lootrunhelper.utils.CodingUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

import java.util.*;
import java.util.stream.Collectors;

public class BeaconDestinations {

    public static String destinations = "";

    private static final List<ChallengeBeaconInfo> beaconsInfo = new ArrayList<>();

    private static final HashMap<BeaconType, Double> lastDistanceToChallenge = new HashMap<>();

    public static HashSet<ChallengeBeaconInfo> updateWhenStandingStill = new HashSet<>();

    private static final double DISTANCE_MOVED_TO_UPDATE = 30;
    private static final double DISTANCE_MOVED_TO_UPDATE_SQUARED = DISTANCE_MOVED_TO_UPDATE * DISTANCE_MOVED_TO_UPDATE;
    private static Vec3d lastPlayerPosition;
    private static int ticksWithoutMovement = 0;

    private static final int TICKS_NOT_MOVING = 20;
    private static final double MAX_MOVEMENT_DISTANCE = 2.5;
    private static final double MAX_MOVEMENT_DISTANCE_SQUARED = MAX_MOVEMENT_DISTANCE * MAX_MOVEMENT_DISTANCE;

    public static void onTick() {

        if (updateWhenStandingStill.size() == 0)
            return;


        Vec3d currentPos = MinecraftClient.getInstance().player.getPos();

        // Check if the player has moved since the last tick
        if (lastPlayerPosition == null || currentPos.squaredDistanceTo(lastPlayerPosition) > MAX_MOVEMENT_DISTANCE_SQUARED) {
            // The player has moved, reset the counter
            ticksWithoutMovement = 0;
            lastPlayerPosition = currentPos;
            return;
        }

        ticksWithoutMovement++;
        // Check if the player has not moved more than 5 blocks within the last ticks
        if (ticksWithoutMovement >= TICKS_NOT_MOVING) {
            // Call your method here
            updateDistancesToUpdate();
            ticksWithoutMovement = 0;
            lastPlayerPosition = currentPos;
        }
    }

    public static void beaconDestinationClose(Beacon beacon, Vec3d challengePos) {
        for (ChallengeBeaconInfo beaconInfo : beaconsInfo) {
            if (beaconInfo.beacon.beaconType == beacon.beaconType) {
                //If it already ran this
                if (lastDistanceToChallenge.getOrDefault(beacon.beaconType, 1d) == 0)
                    return;


                Challenge challenge = getChallengeAtPos(challengePos);
                if (challenge == null)
                    return;

                beaconInfo.challenge = challenge;

                updateWhenStandingStill.remove(beacon.beaconType);
                lastDistanceToChallenge.put(beacon.beaconType, 0d);
                return;
            }
        }
    }

    private static Challenge getChallengeAtPos(Vec3d pos) {

        var challenges = ChallengesLoader.loadRightChallenges();

        for (var challenge : challenges) {
            if (challenge.getX() == pos.x && challenge.getZ() == pos.z)
                return challenge;
        }

        return null;
    }


    private static void updateDistancesToUpdate() {

        List<Challenge> challenges = ChallengesLoader.loadRightChallenges();

        for (ChallengeBeaconInfo beaconInfo : updateWhenStandingStill) {
            updateBeaconFull(beaconInfo, challenges);
        }

        updateWhenStandingStill.clear();
    }

    private static void updateBeaconFull(ChallengeBeaconInfo beaconInfo, List<Challenge> challenges) {
        beaconInfo.challenge = beaconInfo.beacon.findChallengeItLeadsTo(challenges);
        double distanceToChallenge = getChallengeDistanceSquared(beaconInfo.challenge);
        lastDistanceToChallenge.put(beaconInfo.beacon.beaconType, distanceToChallenge);
    }

    public static void resetDistances() {
        lastDistanceToChallenge.clear();
    }

    public static void updateBeacons(HashSet<Beacon> beacons) {
        for (Beacon beacon : beacons) {
            for (ChallengeBeaconInfo beaconInfo : beaconsInfo) {
                if (beaconInfo.beacon.beaconType == beacon.beaconType) {
                    beaconInfo.beacon = beacon;
                    break;
                }
            }
        }
    }

    public static Set<ChallengeBeaconInfo> getBeaconsWithSameChallenge(List<ChallengeBeaconInfo> beacons) {


        Map<Challenge, Long> beaconTypeCount = beacons.stream()
                .collect(Collectors.groupingBy(ChallengeBeaconInfo::getChallenge, Collectors.counting()));

        return beacons.stream()
                .filter(beacon -> beaconTypeCount.get(beacon.getChallenge()) > 1)
                .collect(Collectors.toSet());
    }


    public static void updateDoubleDestinations(){
        Set<ChallengeBeaconInfo> beaconsWithSameDestination = getBeaconsWithSameChallenge(beaconsInfo);

        if (beaconsWithSameDestination.size() != 0){
            var challenges = ChallengesLoader.loadRightChallenges();
            for (ChallengeBeaconInfo beacon : beaconsWithSameDestination){
                updateBeaconFull(beacon, challenges);
            }
        }
    }
    public static void updateDistances() {
        StringBuilder finalString = new StringBuilder();

        if (MinecraftClient.getInstance() == null || MinecraftClient.getInstance().player == null)
            return;


        for (ChallengeBeaconInfo beaconInfo : beaconsInfo) {

            double distanceToChallenge = getChallengeDistanceSquared(beaconInfo.challenge);
            Beacon beacon = beaconInfo.beacon;

            if (!lastDistanceToChallenge.containsKey(beacon.beaconType))
                lastDistanceToChallenge.put(beacon.beaconType, distanceToChallenge);

            double previousDistance = lastDistanceToChallenge.get(beacon.beaconType);


            if (previousDistance - distanceToChallenge > DISTANCE_MOVED_TO_UPDATE_SQUARED) {
                updateWhenStandingStill.add(beaconInfo);
                lastDistanceToChallenge.put(beacon.beaconType, distanceToChallenge);
            }


            finalString
                    .append(beacon.getBeaconName())
                    .append("§l") //leads to
                    .append(beaconInfo.challenge.getChallengeName())
                    .append("§r;§n ") //of type
                    .append(beaconInfo.challenge.getType())
                    .append(". §l")
                    .append((int) Math.sqrt(distanceToChallenge))
                    .append("§rm.")
                    .append("\n");
        }

        destinations = finalString.toString();
    }


    public static void updateDestinations() {
        List<Challenge> challenges = ChallengesLoader.loadRightChallenges();

        beaconsInfo.clear();

        for (Beacon beacon : BeaconChecker.getLastBeacons()) {
            Challenge challengeItLeadsTo = beacon.findChallengeItLeadsTo(challenges);

            ChallengeBeaconInfo beaconInfo = new ChallengeBeaconInfo(beacon, challengeItLeadsTo);
            beaconsInfo.add(beaconInfo);
        }

        sortList();
        updateDistances();
    }

    private static void sortList() {
        beaconsInfo.sort(Comparator.comparingInt(challengeBeaconInfo -> challengeBeaconInfo.beacon.beaconType.ordinal()));

    }

    private static int getChallengeDistanceSquared(Challenge challenge) {

        Vec3d challengePos = new Vec3d(challenge.getX(), challenge.getY(), challenge.getZ());
        Vec3d playerPos = MinecraftClient.getInstance().player.getPos();

        return (int) challengePos.squaredDistanceTo(playerPos);
    }

    private static int getChallengeDistance(Challenge challenge) {

        Vec3d challengePos = new Vec3d(challenge.getX(), challenge.getY(), challenge.getZ());
        Vec3d playerPos = MinecraftClient.getInstance().player.getPos();

        return (int) challengePos.distanceTo(playerPos);
    }
}
