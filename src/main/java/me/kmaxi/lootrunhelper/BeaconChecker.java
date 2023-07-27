package me.kmaxi.lootrunhelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;

import static me.kmaxi.lootrunhelper.BeaconDataSaver.loadFromFile;

public class BeaconChecker {

    public static boolean enabled = false;
    private static int tickCounter = 0;

    private static int checkDelay = 20;
    public static Beacon closestBeacon;

    private static HashSet<Beacon> lastBeacons;

    public static BeaconDataSaver activeDataSaver;

    public static void onTick() {
        if (enabled && tickCounter % checkDelay == 0) {
            tickCounter = 0;
            checkBeacons();
            if (MinecraftClient.getInstance() != null && MinecraftClient.getInstance().player != null){
                saveClosestBeacon(MinecraftClient.getInstance().player.getPos());
                printClosestBeacon();

            }

        }
        tickCounter++;
    }

    public static void PickClosestBeacon() {
        if (activeDataSaver == null){
            activeDataSaver = loadFromFile("beacon_data.json");
        }
        activeDataSaver.pickBeacon(String.valueOf(closestBeacon.beaconType));
    }

    private static void saveClosestBeacon(Vec3d pos) {
        double closestDistance = Double.MAX_VALUE;

        if (lastBeacons == null)
            return;

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


        if (beacons == null)
            return;

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
