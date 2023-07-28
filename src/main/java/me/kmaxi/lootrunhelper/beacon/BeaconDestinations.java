package me.kmaxi.lootrunhelper.beacon;

import me.kmaxi.lootrunhelper.challenges.Challenge;
import me.kmaxi.lootrunhelper.challenges.ChallengesLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class BeaconDestinations {

    public static String destinations = "";

    public static void updateDestinations() {
        List<Challenge> challenges = ChallengesLoader.loadRightChallenges();


        StringBuilder stringBuilder = new StringBuilder();
        for (Beacon beacon : BeaconChecker.getLastBeacons()) {
            Challenge challengeItLeadsTo = beacon.findChallengeItLeadsTo(challenges);
            stringBuilder
                    .append(beacon.getBeaconName())
                    .append("§l") //leads to
                    .append(challengeItLeadsTo.getChallengeName())
                    .append("§r;§n ") //of type
                    .append(challengeItLeadsTo.getType())
                    .append(". §l")
                    .append(getChallengeDistance(challengeItLeadsTo))
                    .append("§rb")
                    .append("\n");
        }

        destinations = stringBuilder.toString();
    }

    private static int getChallengeDistance(Challenge challenge) {
        Vec3d challengePos = new Vec3d(challenge.getX(), 50, challenge.getZ());
        Vec3d playerPos = MinecraftClient.getInstance().player.getPos();
        return (int) challengePos.distanceTo(playerPos);
    }
}
