package me.kmaxi.lootrunhelper.beacon;

import me.kmaxi.lootrunhelper.challenges.Challenge;
import me.kmaxi.lootrunhelper.challenges.ChallengeBeaconInfo;
import me.kmaxi.lootrunhelper.challenges.ChallengeTriangulation;
import me.kmaxi.lootrunhelper.challenges.ChallengesLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BeaconDestinations {

    public static String destinations = "";

    private static List<ChallengeBeaconInfo> beaconsInfo = new ArrayList<>();

    private static Vector2d playerLast;

    private static ChallengeTriangulation challengeTriangulation;


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

    /**
     * Returns
     * @return If it ran, false if the player has not moved enough
     */
    public static boolean updateWithTriangulationIfMovedEnough(){
        var result = challengeTriangulation.runTriangulationIfBlocksAway( 50d);
        if (result == null){
            System.out.println("Has not moved enough");
            return false;
        }

        beaconsInfo = result;
        updateDistances();
        MinecraftClient.getInstance().player.sendMessage(Text.of("RAN TRIANGULATION"));
        System.out.println("Ran triangulation");
        return true;
    }

    private static void instantiateTriangulation(){
        challengeTriangulation = new ChallengeTriangulation(beaconsInfo);
    }


    public static void updateDestWithDot() {
        List<Challenge> challenges = ChallengesLoader.loadRightChallenges();

        beaconsInfo.clear();

        for (Beacon beacon : BeaconChecker.getLastBeacons()) {
            Challenge challengeItLeadsTo = beacon.findChallengeItLeadsTo(challenges);


            ChallengeBeaconInfo beaconInfo = new ChallengeBeaconInfo(beacon, challengeItLeadsTo);
            beaconsInfo.add(beaconInfo);
        }

        sortList();
        updateDistances();
        instantiateTriangulation();
    }

    private static void sortList(){
        beaconsInfo.sort(Comparator.comparingInt(challengeBeaconInfo -> challengeBeaconInfo.beacon.beaconType.ordinal()));

    }

    private static int getChallengeDistance(Challenge challenge) {

        Vec3d challengePos = new Vec3d(challenge.getX(), challenge.getY(), challenge.getZ());
        Vec3d playerPos = MinecraftClient.getInstance().player.getPos();
        System.out.println("Challenge pos: " + challengePos + " Player pos: " + playerPos + " Distance: " + challengePos.distanceTo(playerPos));

        return (int) challengePos.distanceTo(playerPos);
    }
}
