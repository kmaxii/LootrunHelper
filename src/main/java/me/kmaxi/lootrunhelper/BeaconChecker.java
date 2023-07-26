package me.kmaxi.lootrunhelper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;

public class BeaconChecker {

    public boolean enabled = true;
    private int tickCounter = 0;

    private int checkDelay = 20;

    private HashSet<Beacon> lastBeacons;

    public void onTick(){
        if (enabled && tickCounter % checkDelay == 0){
            tickCounter = 0;
            checkBeacons();
            if (MinecraftClient.getInstance() != null && MinecraftClient.getInstance().player != null)
                printClosestBeacon(MinecraftClient.getInstance().player.getPos());

        }
        tickCounter++;
    }


    private void printClosestBeacon(Vec3d pos){
        double closestDistance = Double.MAX_VALUE;
        Beacon closestBeacon = null;

        if (lastBeacons == null)
            return;

        for (Beacon beacon : lastBeacons){
            double distance = beacon.position.distanceTo(pos);
            if (distance < closestDistance){
                closestDistance = distance;
                closestBeacon = beacon;
            }
        }
        if (closestBeacon != null){
            MinecraftClient.getInstance().player.sendMessage(Text.of("Closest beacon: " + closestBeacon.beaconType + " at " + closestBeacon.position), false);
            System.out.println("Closest beacon: " + closestBeacon.beaconType + " at " + closestBeacon.position);
        }
    }


    private void checkBeacons(){
        HashSet<Beacon> beacons = BeaconHandler.getBeacons();


        if (beacons == null)
            return;

        //Must have entered a challenge or classed
        if (beacons.size() == 0){
            lastBeacons  = null;
            return;
        }

        if (lastBeacons == null){
            lastBeacons = beacons;
            return;
        }

        //Our current beacons are bigger than what we had before, must be close to a challenge
        //So find what beacons are not in the new list and add their latest location
        if (beacons.size() < lastBeacons.size()){
            for(Beacon beacon : lastBeacons){
                boolean foundSameType = false;
                for (Beacon newBeacon : beacons){
                    if (beacon.beaconType == newBeacon.beaconType){
                        foundSameType = true;
                        break;
                    }
                }
                if (!foundSameType){
                    beacons.add(beacon);
                }
            }
            return;
        }

        lastBeacons = beacons;

    }
}
