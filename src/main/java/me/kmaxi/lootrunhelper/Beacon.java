package me.kmaxi.lootrunhelper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

import static me.kmaxi.lootrunhelper.BeaconHandler.beaconItems;

public class Beacon {


    public Vec3d position;
    public BeaconType beaconType;


    public Beacon(Vec3d position, ItemStack itemStack) {
        this.position = position;
        this.beaconType = getBeaconType(itemStack.getItem(), itemStack.getDamage());
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Beacon beacon = (Beacon) o;

        if (position != null ? !sameCords(position, beacon.position) : beacon.position != null) return false;
        return beaconType == beacon.beaconType;
    }


    @Override
    public int hashCode() {
        int result = (beaconType != null ? beaconType.hashCode() : 0);
        //add x and z cords to the hashcode
        result = 31 * result + (int) position.x;
        result = 31 * result + (int) position.z;
        return result;
    }

    public BeaconType getBeaconType(Item item, int damage) {

        if (!beaconItems.contains(item))
            return null;

        switch(damage){
            case 25:
                return BeaconType.GREEN;

            case 3:
                return BeaconType.YELLOW;

            case 4:
                return BeaconType.BLUE;

            case 5:
                return BeaconType.PURPLE;

            case 6:
                return BeaconType.GRAY;

            case 7:
                return BeaconType.ORANGE;

            case 8:
                return BeaconType.RED;

            case 9:
                return BeaconType.DARK_GRAY;

            case 10:
                return BeaconType.WHITE;

            case 11:
                return BeaconType.AQUA;

            case 12:
                return BeaconType.RAINBOW;
        }
        return null;
    }
    private static boolean sameCords(Vec3d v1, Vec3d v2) {
        return v1.x == v2.x && v1.z == v2.z;
    }


    @Override
    public String toString() {
        return "Position: " + position + " Type: " + beaconType + " Hashcode: " + hashCode() + "\n";
    }
}
