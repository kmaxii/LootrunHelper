package me.kmaxi.lootrunhelper.challenges;

import me.kmaxi.lootrunhelper.beacon.Beacon;
import me.kmaxi.lootrunhelper.beacon.BeaconChecker;
import me.kmaxi.lootrunhelper.beacon.BeaconType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ChallengeTriangulation {

   private static final double DOT_TOLERANCE = 0.99f;

    // Define your Challenge class with a name and position
    private HashMap<BeaconType, Vector2d> beaconLocations = new HashMap<>();
    private HashMap<BeaconType, Vector2d> beaconDirections = new HashMap<>();

    private List<ChallengeBeaconInfo> infoList;

    private final Vector2d oldPlayerPos;

    public ChallengeTriangulation(List<ChallengeBeaconInfo> currentBeacons) {
        for (ChallengeBeaconInfo beaconInfo : currentBeacons) {
            Beacon beacon = beaconInfo.beacon;
            beaconLocations.put(beacon.beaconType, new Vector2d(beacon.position.x, beacon.position.z));
            beaconDirections.put(beacon.beaconType, beacon.getLastDirectionVector());
        }
        infoList = currentBeacons;
        oldPlayerPos = getPlayerPos();
    }


    public List<ChallengeBeaconInfo> runTriangulationIfBlocksAway(double distance) {
        if (oldPlayerPos.distance(getPlayerPos()) < distance) {
            return null;
        }
        List<Challenge> challenges = ChallengesLoader.loadRightChallenges();

        return triangulateDestinations(challenges);

    }

    private List<ChallengeBeaconInfo> triangulateDestinations(List<Challenge> challenges) {

        HashSet<Beacon> newBeaconsPos = BeaconChecker.checkBeacons();

        System.out.println("LootrunHelper triangulate Beacons:");
        System.out.println(newBeaconsPos);

        Vector2d newPlayerPos = getPlayerPos();

        System.out.println("Player pos at start: " + oldPlayerPos.x + " " + oldPlayerPos.y);

        for (ChallengeBeaconInfo beaconInfo : infoList) {
            Beacon oldBeaconData = beaconInfo.beacon;
            Vector2d oldDirection = oldBeaconData.getLastDirectionVector();
            Beacon newBeacon = findBeacon(oldBeaconData.beaconType, newBeaconsPos);
            Vector2d newDirection = new Vector2d(newBeacon.position.x - newPlayerPos.x, newBeacon.position.z - newPlayerPos.y).normalize();

            if (newDirection.dot(oldDirection) > DOT_TOLERANCE){
                Challenge challengeItLeadTo = newBeacon.findChallengeItLeadsTo(challenges);
                beaconInfo.challenge = challengeItLeadTo;
                continue;
            }

            MinecraftClient.getInstance().player.sendMessage(Text.of(newBeacon.beaconType.toString() + ": " + newDirection.dot(oldDirection)));

            Challenge challenge = findClosestChallenge(new Vector2d(oldPlayerPos.x, oldPlayerPos.y), oldDirection, newPlayerPos, newDirection, challenges);
            beaconInfo.challenge = challenge;
            System.out.println("Player pos in loop: " + oldPlayerPos.x + " " + oldPlayerPos.y);

        }
        return infoList;
    }

    private Vector2d getPlayerPos() {

        Vec3d playerPos3d = MinecraftClient.getInstance().player.getPos();
        return new Vector2d(playerPos3d.x, playerPos3d.z);
    }

    private static Beacon findBeacon(BeaconType type, HashSet<Beacon> beacons) {
        for (Beacon beacon : beacons) {
            if (beacon.beaconType == type) {
                return beacon;
            }
        }
        return null;
    }

    private Challenge findClosestChallenge(Vector2d position1, Vector2d vector1, Vector2d position2, Vector2d vector2, List<Challenge> Challenges) {
        //Vector2d intersectionPoint = findIntersectionPoint(position1, vector1, position2, vector2);
        Vector2d intersectionPoint = getPointOfIntersection(position1, position2, vector1 , vector2);

        // Find the closest Challenge to the intersection point
        Challenge closestChallenge = findClosestChallenge(intersectionPoint, Challenges);

        return closestChallenge;
    }

  /*  public static Vector2d findIntersection(Vector2d pos1, Vector2d dir1, Vector2d pos2, Vector2d dir2) {
        // Find the point of intersection using vector algebra
        Vector2d dir2Perpendicular = new Vector2d(-dir2.y, dir2.x); // Perpendicular to dir2

        // Parametric form of line intersection (see https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection)
        double t = dir2Perpendicular.sub(pos1.sub(pos2)).mul(1.0 / dir1.sub(dir2).dot(dir2Perpendicular)).dot(dir1);

        // Calculate the intersection point
        Vector2d intersectionPoint = pos1.add(dir1.mul(t));

        System.out.println("First pos: " + pos1.x + ", " + pos1.y + " dir1: " + dir1.x + ", " + dir1.y + " pos2: " + pos2.x + ", " + pos2.y + " dir2: " + dir2.x + ", " + dir2.y);
        return intersectionPoint;
    }

   */

    public static void main(String[] args) {
        //Point 1 926.5 -794.5
        // Point 2 996.6250712573996 -784.6568630123111
        // Normal 1 0.7401700458976934 0.672419737333614
        // Normal 2 0.39894878905049114 0.9169732077411785

        Vector2d point1 = new Vector2d(926.5, -794.5);
        Vector2d point2 = new Vector2d(996.6250712573996, -784.6568630123111);
        Vector2d normal1 = new Vector2d(0.7401700458976934, 0.672419737333614);
        Vector2d normal2 = new Vector2d(0.39894878905049114, 0.9169732077411785);

        Vector2d intersect = getPointOfIntersection(point1, point2, normal1, normal2);

        System.out.println(doesRaysIntersects(point1, point2, normal1, normal2));
        System.out.println(intersect.x + " " + intersect.y);
    }

    private static boolean doesRaysIntersects(Vector2d p1, Vector2d p2, Vector2d n1, Vector2d n2)
    {
        double u = (p1.y * n2.x + n2.y * p2.x - p2.y * n2.x - n2.y * p1.x) / (n1.x * n2.y - n1.y * n2.x);
        double v = (p1.x + n1.x * u - p2.x) / n1.x;

        return u > 0 && v > 0;
    }

    private static Vector2d getPointOfIntersection(Vector2d p1, Vector2d p2, Vector2d n1, Vector2d n2)
    {

        System.out.println("Point 1 " + p1.x + " " + p1.y + " Point 2 " + p2.x + " " + p2.y + " Normal 1 " + n1.x + " " + n1.y + " Normal 2 " + n2.x + " " + n2.y);

        double p1EndX = p1.x + n1.x; // another point in line p1->n1
        double p1EndY = p1.y + n1.y; // another point in line p1->n1
        double p2EndX = p2.x + n2.x; // another point in line p2->n2
        double p2EndY = p2.y + n2.y; // another point in line p2->n2



        double m1 = (p1EndY - p1.y) / (p1EndX - p1.x); // slope of line p1->n1
        double m2 = (p2EndY - p2.y) / (p2EndX - p2.x); // slope of line p2->n2


        double b1 = p1.y - m1 * p1.x; // y-intercept of line p1->n1
        double b2 = p2.y - m2 * p2.x; // y-intercept of line p2->n2

        double px = (b2 - b1) / (m1 - m2); // collision x
        double py = m1 * px + b1; // collision y

        System.out.println("Point of intersection " + px + " " + py);

        return new Vector2d(px, py); // return statement
    }

/*
    // Method to find the intersection point of two lines defined by their direction vectors and positions
    private Vector2d findIntersectionPoint(Vector2d position1, Vector2d vector1, Vector2d position2, Vector2d vector2) {
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

        System.out.println("Position1: " + position1.x() + ", " + position1.y()+ " position2: " + position2.x() + ", " + position2.y() + " vector1: " + vector1.x() + ", " + vector1.y() + " vector2: " + vector2.x() + ", " + vector2.y());
        System.out.println("Intersection point: " + x + ", " + y);

        return new Vector2d(x, y);
    }
    */




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
