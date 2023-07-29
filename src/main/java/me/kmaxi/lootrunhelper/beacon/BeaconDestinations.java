package me.kmaxi.lootrunhelper.beacon;

import me.kmaxi.lootrunhelper.challenges.Challenge;
import me.kmaxi.lootrunhelper.challenges.ChallengeBeaconInfo;
import me.kmaxi.lootrunhelper.challenges.ChallengesLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class BeaconDestinations {

    public static String destinations = "";

    private static final List<ChallengeBeaconInfo> beaconsInfo = new ArrayList<>();

    public static void updateDistances(){
        StringBuilder finalString = new StringBuilder();

        for (ChallengeBeaconInfo beaconInfo : beaconsInfo) {
               finalString
                    .append(beaconInfo.beacon.getBeaconName())
                    .append("§l") //leads to
                    .append(beaconInfo.challenge.getChallengeName())
                    .append("§r;§n ") //of type
                    .append(beaconInfo.challenge.getType())
                    .append(". §l")
                    .append(getChallengeDistance(beaconInfo.challenge))
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

        updateDistances();
    }

    private static int getChallengeDistance(Challenge challenge) {

        Vec3d challengePos = new Vec3d(challenge.getX(), challenge.getY(), challenge.getZ());
        Vec3d playerPos = MinecraftClient.getInstance().player.getPos();
        System.out.println("Challenge pos: " + challengePos + " Player pos: " + playerPos + " Distance: " + challengePos.distanceTo(playerPos));

        return (int) challengePos.distanceTo(playerPos);
    }
}
