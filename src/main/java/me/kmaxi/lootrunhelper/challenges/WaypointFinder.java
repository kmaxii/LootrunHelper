package me.kmaxi.lootrunhelper.challenges;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2d;

import java.util.List;

public class WaypointFinder {

    // Assuming the normalized vector is represented by 'directionVector'
    // and 'waypoints' is a List of Waypoint objects
    public Challenge findChallenge(List<Challenge> waypoints, Vector2d directionVector) {
        Challenge closestWaypoint = null;

        Vec3d playerPos = MinecraftClient.getInstance().player.getPos();

        double maxDotProduct = Double.NEGATIVE_INFINITY;

        for (Challenge waypoint : waypoints) {

            Vector2d wayPointVector = new Vector2d(waypoint.getX(), waypoint.getZ());
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