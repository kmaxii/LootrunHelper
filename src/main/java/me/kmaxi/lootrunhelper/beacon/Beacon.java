package me.kmaxi.lootrunhelper.beacon;

import me.kmaxi.lootrunhelper.challenges.Challenge;
import me.kmaxi.lootrunhelper.utils.ColorUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2d;

import java.util.List;

public class Beacon {


    public Vec3d position;
    public BeaconType beaconType;
    public boolean isVibrant;

    public BeaconType getType(){
        return beaconType;
    }


    public Beacon(Vec3d position, BeaconType beaconType) {
        this.position = position;
        this.beaconType = beaconType;
        //We replace the underscore for the dark grey to work correctly
        isVibrant = VibrantBeaconInfo.isVibrant(beaconType.toString());
    }



    public String getBeaconName() {
        return ColorUtils.getCorrectColor(beaconType.toString());
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

    public static BeaconType getBeaconType(Item item, int damage) {


        if (item == Items.GOLDEN_SHOVEL && damage == 25)
            return BeaconType.GREEN;

        if (!(item == Items.GOLDEN_PICKAXE)) {
            return null;

        }

        switch (damage) {
            case 3:
                return BeaconType.YELLOW;
            case 4:
                return BeaconType.BLUE;

            case 5:
                return BeaconType.PURPLE;

            case 6:
                return BeaconType.GREY;

            case 7:
                return BeaconType.ORANGE;

            case 8:
                return BeaconType.RED;

            case 9:
                return BeaconType.DARK_GREY;

            case 10:
                return BeaconType.WHITE;

            case 11:
                return BeaconType.AQUA;

            case 12:
                return BeaconType.RAINBOW;
        }

        System.out.println("Lootrunhelper: Beacon type not found for " + item.toString() + " with durra: " + damage);
        return null;
    }

    private static boolean sameCords(Vec3d v1, Vec3d v2) {
        return v1.x == v2.x && v1.z == v2.z;
    }


    @Override
    public String toString() {
        return "Position: " + position + " Type: " + beaconType + " Hashcode: " + hashCode() + "\n";
    }

    public Challenge findChallengeItLeadsTo(List<Challenge> waypoints) {

        Challenge closestWaypoint = null;

        Vec3d playerPos = MinecraftClient.getInstance().player.getPos();
        Vector2d directionVector = new Vector2d(position.x - playerPos.x, position.z - playerPos.z).normalize();


        double maxDotProduct = Double.NEGATIVE_INFINITY;

        for (Challenge waypoint : waypoints) {

            Vector2d wayPointVector = new Vector2d(waypoint.getX() - playerPos.x, waypoint.getZ() - playerPos.z);
            wayPointVector = wayPointVector.normalize();

            // Calculate the dot product between the normalized vector and the current direction vector
            double dotProduct = directionVector.x * wayPointVector.x + directionVector.y * wayPointVector.y;

            // Check if the dot product is greater than the previous maxDotProduct
            if (dotProduct > maxDotProduct) {
                maxDotProduct = dotProduct;
                closestWaypoint = waypoint;
            }
        }


        return closestWaypoint;
    }
}
