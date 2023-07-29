package me.kmaxi.lootrunhelper.beacon;

import me.kmaxi.lootrunhelper.commands.ListBeaconDestinations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;

import static me.kmaxi.lootrunhelper.beacon.BeaconDataSaver.loadFromFile;

public class BeaconChecker {

    private static boolean nextCalculateChallenges = false;

    public static void clearCurrentBeacons() {
        if (beaconList != null)
            beaconList.clear();
        closestBeacon = null;
        BeaconDestinations.destinations = "";
    }

    public static void stashCurrentBeacons(){
        if (beaconList != null)
            beaconList = null;
    }
    public static void enable() {
        enabled = true;
        nextCalculateChallenges = true;
    }

    public static void disable() {
        enabled = false;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    private static boolean enabled = false;
    private static int tickCounter = 0;

    private static int checkDelay = 20;
    public static Beacon closestBeacon;

    private static BeaconList beaconList;

    public static BeaconList getLastBeacons() {
        return beaconList;
    }

    private static BeaconDataSaver dataSaver;

    private static boolean runTriangulation = false;


    public static void clearDataSaver(){
        dataSaver = null;
    }

    public static BeaconDataSaver activeDataSaver() {
        if (dataSaver == null)
            dataSaver = loadFromFile();
        return dataSaver;
    }

    public static void onTick() {

        if (!enabled)
            return;

        tickCounter++;

        if (tickCounter % checkDelay == 0) {
            tickCounter = 0;

            if (MinecraftClient.getInstance() == null || MinecraftClient.getInstance().player == null) {
                System.out.println("MINECRAFT CLIENT OR PLAYER IS NULL");
                return;
            }

            if (runTriangulation) {
                runTriangulation = !(BeaconDestinations.updateWithTriangulationIfMovedEnough());
            }

            checkBeacons();

            saveClosestBeacon(MinecraftClient.getInstance().player.getPos());
            if (nextCalculateChallenges) {
                ListBeaconDestinations.run(null);
                nextCalculateChallenges = false;
                runTriangulation = true;
            }
        }

        if (beaconList == null || beaconList.size() == 0) {
            return;
        }

        BeaconDestinations.updateDistances();


    }

    public static void PickClosestBeacon() {
        if (closestBeacon == null)
            return;

        if (dataSaver == null) {
            dataSaver = loadFromFile();
        }
        dataSaver.pickBeacon(String.valueOf(closestBeacon.beaconType));
    }

    private static void saveClosestBeacon(Vec3d pos) {
        double closestDistance = Double.MAX_VALUE;

        if (beaconList == null) {
            System.out.println("LAST BEACONS IS NULL");
            return;
        }

        for (Beacon beacon : beaconList) {
            double distance = beacon.position.distanceTo(pos);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestBeacon = beacon;
            }
        }

    }

    private static void printClosestBeacon() {

        if (closestBeacon != null) {
            System.out.println("Closest beacon: " + closestBeacon.beaconType + " at " + closestBeacon.position);
        }
    }


    public static HashSet<Beacon>  checkBeacons() {
        HashSet<Beacon> beacons = BeaconHandler.getBeacons();


        if (beacons == null)     {
            System.out.println("Beacons is null");
            return null;
        }

        System.out.println("Beacons found: " + beacons.size());

        //Must have entered a challenge or classed
        if (beacons.size() == 0) {
            return null;
        }

        if (beaconList == null) {
            beaconList = new BeaconList(beacons);
            return beacons;
        }

        //Our current beacons are smaller than what we had before, must be close to a challenge
        //So find what beacons are not in the new list and add their latest location
        if (beacons.size() < beaconList.size()) {
           for (Beacon beacon : beaconList) {
                boolean foundSameType = false;
                for (Beacon newBeacon : beacons) {
                    if (beacon.beaconType == newBeacon.beaconType) {
                        foundSameType = true;
                        break;
                    }
                }
                if (!foundSameType) {
                    beacons.add(beacon);
                }
            }
        }

        beaconList.replace(beacons);
        return beacons;
    }
}
