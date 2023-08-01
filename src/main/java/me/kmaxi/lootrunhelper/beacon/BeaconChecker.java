package me.kmaxi.lootrunhelper.beacon;

import me.kmaxi.lootrunhelper.commands.ListBeaconDestinations;
import me.kmaxi.lootrunhelper.data.CurrentData;
import me.kmaxi.lootrunhelper.utils.CodingUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;

import static me.kmaxi.lootrunhelper.beacon.BeaconDataSaver.loadFromFile;

public class BeaconChecker {

    private static boolean nextPrintChallengeInfo = false;

    public static void clearCurrentBeacons() {
        if (beaconList != null)
            beaconList.clear();
        closestBeacon = null;
        BeaconDestinations.destinations = "";
    }

    public static void setBeaconListToNull() {
        if (beaconList != null)
            beaconList = null;
    }

    public static void enable() {
        enabled = true;
        nextPrintChallengeInfo = true;
        //updateBeacons();
        tickCounter = 15;
    }

    public static void disable() {
        enabled = false;
        BeaconDestinations.resetDistances();
    }

    public static boolean isEnabled() {
        return enabled;
    }

    private static boolean enabled = false;
    private static int tickCounter = 0;

    private static int checkDelay = 20;
    private static int updateDistanceDelay = 5;
    public static Beacon closestBeacon;

    private static BeaconList beaconList;

    public static BeaconList getLastBeacons() {
        return beaconList;
    }

    private static BeaconDataSaver dataSaver;


    public static void clearDataSaver() {
        dataSaver = null;
    }

    public static BeaconDataSaver activeDataSaver() {
        if (dataSaver == null)
            dataSaver = loadFromFile();
        return dataSaver;
    }

    public static void onTick() {

        if (!enabled || MinecraftClient.getInstance() == null || MinecraftClient.getInstance().player == null)
            return;
        BeaconDestinations.onTick();


        tickCounter++;

        boolean updateDestinations = tickCounter % updateDistanceDelay == 0;

        if (tickCounter % checkDelay == 0) {
            tickCounter = 0;

            BeaconChecker.activeDataSaver().updateString();


            updateBeacons();

        }

        if (beaconList == null || beaconList.size() == 0) {
            return;
        }

        if (updateDestinations)
            BeaconDestinations.updateDoubleDestinations();
        BeaconDestinations.updateDistances();
    }

    public static void updateBeacons() {

        checkBeacons();

        saveClosestBeacon(MinecraftClient.getInstance().player.getPos());

        if (nextPrintChallengeInfo) {
            ListBeaconDestinations.run(null);
            CurrentData.saveBeaconChoices(beaconList.stream().map(beacon -> beacon.beaconType).toList());
            nextPrintChallengeInfo = false;
        }
    }

    public static void PickClosestBeacon() {
        if (closestBeacon == null)
            return;

        if (dataSaver == null) {
            dataSaver = loadFromFile();
        }


        CurrentData.picketBeacon(closestBeacon);


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


    private static void checkBeacons() {
        HashSet<Beacon> beacons = BeaconHandler.getBeacons();


        if (beacons == null) {
            System.out.println("Beacons is null");
            return;
        }


        //Must have entered a challenge or classed
        if (beacons.size() == 0) {
            return;
        }

        BeaconDestinations.updateBeacons(beacons);

        if (beaconList == null) {
            beaconList = new BeaconList(beacons);
            nextPrintChallengeInfo = true;
            return;
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

        if (beacons.size() > beaconList.size()) {
            nextPrintChallengeInfo = true;
        }

        beaconList.replace(beacons);
    }
}
