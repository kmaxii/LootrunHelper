package me.kmaxi.lootrunhelper.challenges;

import me.kmaxi.lootrunhelper.beacon.Beacon;
import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.BeaconType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ChallengeTriangulation {

    // Define your Challenge class with a name and position
    private HashMap<BeaconType, Vector2d> beaconLocations = new HashMap<>();
    private HashMap<BeaconType, Vector2d> beaconDirections = new HashMap<>();

    private List<ChallengeBeaconInfo> infoList;

    private Vector2d oldPlayerPos;

    public ChallengeTriangulation(List<ChallengeBeaconInfo> currentBeacons) {
        for (ChallengeBeaconInfo beaconInfo : currentBeacons) {
            Beacon beacon = beaconInfo.beacon;
            beaconLocations.put(beacon.beaconType, new Vector2d(beacon.position.x, beacon.position.z));
            beaconDirections.put(beacon.beaconType, beacon.getLastDirectionVector());
        }
        infoList = currentBeacons;
        oldPlayerPos = getPlayerPos();
    }


    public List<ChallengeBeaconInfo> runTriangulationIfBlocksAway(double distance){
        System.out.println("Distance moved: " + oldPlayerPos.distance(getPlayerPos()) + " > " + distance);
        if (oldPlayerPos.distance(getPlayerPos()) < distance){
            return null;
        }
        List<Challenge> challenges = ChallengesLoader.loadRightChallenges();

        return triangulateDestinations(challenges);

    }

    private Vector2d getPlayerPos(){

        Vec3d playerPos3d = MinecraftClient.getInstance().player.getPos();
        return new Vector2d(playerPos3d.x, playerPos3d.z);
    }

    private List<ChallengeBeaconInfo> triangulateDestinations(List<Challenge> challenges){

        HashSet<Beacon> newBeaconsPos = BeaconChecker.checkBeacons();

        System.out.println("LootrunHelper triangulate Beacons:");
        System.out.println(newBeaconsPos);

        Vec3d playerPosition = MinecraftClient.getInstance().player.getPos();
        Vector2d newPlayerPos = new Vector2d(playerPosition.x, playerPosition.z);

        for (ChallengeBeaconInfo beaconInfo : infoList) {
            Beacon oldBeaconData = beaconInfo.beacon;
            Vector2d oldDirection = oldBeaconData.getLastDirectionVector();
            Beacon newBeacon = findBeacon(oldBeaconData.beaconType, newBeaconsPos);
            Vector2d newDirection = new Vector2d(newBeacon.position.x - playerPosition.x, newBeacon.position.z - playerPosition.z).normalize();

            Challenge challenge =  findClosestChallenge(oldDirection, oldPlayerPos, newDirection, newPlayerPos, challenges);
            beaconInfo.challenge = challenge;
        }
        return infoList;
    }

    private static Beacon findBeacon(BeaconType type, HashSet<Beacon> beacons) {
        for (Beacon beacon : beacons) {
            if (beacon.beaconType == type) {
                return beacon;
            }
        }
        return null;
    }

    private Challenge findClosestChallenge(Vector2d vector1, Vector2d position1, Vector2d vector2, Vector2d position2, List<Challenge> Challenges) {
        Vector2d intersectionPoint = findIntersectionPoint(vector1, position1, vector2, position2);

        // Find the closest Challenge to the intersection point
        Challenge closestChallenge = findClosestChallenge(intersectionPoint, Challenges);

        return closestChallenge;
    }

    // Method to find the intersection point of two lines defined by their direction vectors and positions
    private Vector2d findIntersectionPoint(Vector2d vector1, Vector2d position1, Vector2d vector2, Vector2d position2) {
        double x1 = position1.x();
        double y1 = position1.y();
        double x2 = position2.x();
        double y2 = position2.y();
        double a1 = vector1.x();
        double b1 = vector1.y();
        double a2 = vector2.x();
        double b2 = vector2.y();

        // Calculate the intersection point coordinates
        double x = (b1 * x1 - a1 * y1 - b1 * x2 + a1 * y2) / (b1 * a2 - a1 * b2);
        double y = (x2 * a2 - y2 * b2 - x1 * a2 + y1 * b2) / (b1 * a2 - a1 * b2);

        return new Vector2d(x, y);
    }

    // Method to find the closest Challenge to a given point
    private Challenge findClosestChallenge(Vector2d point, List<Challenge> Challenges) {
        Challenge closestChallenge = null;
        double minDistance = Double.MAX_VALUE;

        for (Challenge Challenge : Challenges) {
            double distance = point.distance(new Vector2d(Challenge.x, Challenge.z));
            if (distance < minDistance) {
                minDistance = distance;
                closestChallenge = Challenge;
            }
        }

        return closestChallenge;
    }
}
