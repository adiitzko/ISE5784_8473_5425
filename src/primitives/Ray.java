package primitives;

import java.util.List;
import geometries.Intersectable.GeoPoint;

/**
 * This class represents a ray in 3D space. A ray originates from a point (head) and extends infinitely in a specific direction (normalized vector).
 */
public class Ray {

    /**
     * Field representing the starting point (head) of the ray.
     */
    public Point head;

    /**
     * Field representing the direction of the ray as a normalized vector.
     */
    public Vector direction;

    /**
     * Constructor that initializes the ray with a starting point (head) and a direction vector.
     *
     * @param head The Point object representing the starting point of the ray.
     * @param direction The Vector object representing the direction of the ray. The direction vector is normalized before being assigned.
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    /**
     * get point on the ray
     *
     * @param t distance from the start of the ray
     * @return new Point3D
     */
    public Point getPoint(double t) {
        return Util.isZero(t) ? head : head.add(direction.scale(t));
    }

    /**
     * Checks if two rays are equal by comparing their head points and direction vectors.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal (same head point and direction vector), false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }

    /**
     * Returns a string representation of the ray in the format "Ray [head=<point coordinates>, direction=<vector components>]".
     *
     * @return The string representation of the ray.
     */
    @Override
    public String toString() {
        return "Ray [head=" + head + ", direction=" + direction + "]";
    }

    /**
     * finds the closest point to the head point of the ray
     *
     * @param points list of points to check
     * @return the closest point
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }
    /**
     * finds the closest geoPoint to a given geoPoint
     * @param geoPoints a list of geoPoints
     * @return the closest geoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {
        if (geoPoints == null || geoPoints.size() == 0)
            return null;
        GeoPoint closestGeoPoint = geoPoints.get(0);
        double minDistance = head.distanceSquared(closestGeoPoint.point);
        for (GeoPoint geoPoint : geoPoints) {
            double currentDistance = head.distanceSquared(geoPoint.point);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                closestGeoPoint = geoPoint;
            }
        }
        return closestGeoPoint;
    }
}
