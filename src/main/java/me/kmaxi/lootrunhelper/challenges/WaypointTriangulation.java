package me.kmaxi.lootrunhelper.challenges;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class WaypointTriangulation {

    // Define your waypoint class with a name and position
    public static class Waypoint {
        public String name;
        public Point2D position;

        public Waypoint(String name, Point2D position) {
            this.name = name;
            this.position = position;
        }
    }

    public static void main(String[] args) {
        // Your normalized vectors (directions)
        Point2D vector1 = new Point2D.Double(0.5, 0.866); // Example vector 1
        Point2D vector2 = new Point2D.Double(-0.866, 0.5); // Example vector 2

        // Your positions
        Point2D position1 = new Point2D.Double(10, 20); // Example position 1
        Point2D position2 = new Point2D.Double(15, 25); // Example position 2

        // Your waypoints
        List<Waypoint> waypoints = new ArrayList<>();
        waypoints.add(new Waypoint("Waypoint 1", new Point2D.Double(10, 15))); // Example waypoint 1
        waypoints.add(new Waypoint("Waypoint 2", new Point2D.Double(20, 30))); // Example waypoint 2
        // Add more waypoints as needed

        // Find the intersection point of the normalized vectors
        Point2D intersectionPoint = findIntersectionPoint(vector1, position1, vector2, position2);

        // Find the closest waypoint to the intersection point
        Waypoint closestWaypoint = findClosestWaypoint(intersectionPoint, waypoints);

        // Output the result
        System.out.println("The vectors point to " + closestWaypoint.name);
    }

    // Method to find the intersection point of two lines defined by their direction vectors and positions
    private static Point2D findIntersectionPoint(Point2D vector1, Point2D position1, Point2D vector2, Point2D position2) {
        double x1 = position1.getX();
        double y1 = position1.getY();
        double x2 = position2.getX();
        double y2 = position2.getY();
        double a1 = vector1.getX();
        double b1 = vector1.getY();
        double a2 = vector2.getX();
        double b2 = vector2.getY();

        // Calculate the intersection point coordinates
        double x = (b1 * x1 - a1 * y1 - b1 * x2 + a1 * y2) / (b1 * a2 - a1 * b2);
        double y = (x2 * a2 - y2 * b2 - x1 * a2 + y1 * b2) / (b1 * a2 - a1 * b2);

        return new Point2D.Double(x, y);
    }

    // Method to find the closest waypoint to a given point
    private static Waypoint findClosestWaypoint(Point2D point, List<Waypoint> waypoints) {
        Waypoint closestWaypoint = null;
        double minDistance = Double.MAX_VALUE;

        for (Waypoint waypoint : waypoints) {
            double distance = point.distance(waypoint.position);
            if (distance < minDistance) {
                minDistance = distance;
                closestWaypoint = waypoint;
            }
        }

        return closestWaypoint;
    }
}
