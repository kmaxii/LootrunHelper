package me.kmaxi.lootrunhelper.beacon;

import me.kmaxi.lootrunhelper.commands.ListBeaconDestinations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;

import static me.kmaxi.lootrunhelper.beacon.BeaconDataSaver.loadFromFile;

public class BeaconChecker {

    private static boolean nextPrintChallengeInfo = false;

    public static void enable() {
        isEnabled = true;
        nextPrintChallengeInfo = true;
    }

    public static void disable() {
        isEnabled = false;
    }

    private static boolean isEnabled = false;
    private static int tickCounter = 0;

    private static int checkDelay = 20;
    public static Beacon closestBeacon;

    private static HashSet<Beacon> lastBeacons;

    public static HashSet<Beacon> getLastBeacons() {
        return lastBeacons;
    }

    private static BeaconDataSaver getDataSaver;


    public static BeaconDataSaver activeDataSaver() {
        if (getDataSaver == null)
            getDataSaver = loadFromFile("beacon_data.json");
        return getDataSaver;
    }

    public static void onTick() {
        if (isEnabled && tickCounter % checkDelay == 0) {
            tickCounter = 0;
            checkBeacons();

            if (MinecraftClient.getInstance() == null) {
                System.out.println("MINECRAFT CLIENT IS NULL");
                return;
            }


            if (MinecraftClient.getInstance().player == null) {
                System.out.println("PLAYER IS NULL");
                return;
            }

            saveClosestBeacon(MinecraftClient.getInstance().player.getPos());
            printClosestBeacon();
            if (nextPrintChallengeInfo) {
                ListBeaconDestinations.run(null);
                nextPrintChallengeInfo = false;
            }

        }
        tickCounter++;
    }

    public static void PickClosestBeacon() {
        if (getDataSaver == null) {
            getDataSaver = loadFromFile("beacon_data.json");
        }
        getDataSaver.pickBeacon(String.valueOf(closestBeacon.beaconType));
    }

    private static void saveClosestBeacon(Vec3d pos) {
        double closestDistance = Double.MAX_VALUE;

        if (lastBeacons == null) {
            System.out.println("LAST BEACONS IS NULL");
            return;
        }

        System.out.println("Saving closest beacon. There are: " + lastBeacons.size() + " beacons");
        for (Beacon beacon : lastBeacons) {
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


    private static void checkBeacons() {
        HashSet<Beacon> beacons = BeaconHandler.getBeacons();


        if (beacons == null)     {
            System.out.println("Beacons is null");
            return;
        }

        System.out.println("Beacons found: " + beacons.size());

        //Must have entered a challenge or classed
        if (beacons.size() == 0) {
            lastBeacons = null;
            return;
        }

        if (lastBeacons == null) {
            lastBeacons = beacons;
            return;
        }

        //Our current beacons are bigger than what we had before, must be close to a challenge
        //So find what beacons are not in the new list and add their latest location
        if (beacons.size() < lastBeacons.size()) {
            for (Beacon beacon : lastBeacons) {
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
            return;
        }

        lastBeacons = beacons;
    }
}
