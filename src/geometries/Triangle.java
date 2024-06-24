package geometries;

import primitives.Point;
import primitives.Util;
import primitives.Vector;
import primitives.Ray;
import java.util.List;


/**
 * Represents a triangle in three-dimensional space.
 * A triangle is a specific type of polygon with three vertices.
 * @author Ruth Katanov 326295425  Adi Itzkovich 214608473
 */
public class Triangle extends Polygon {

    /**
     * Constructs a triangle with the given vertices.
     * The vertices must be provided in a counterclockwise order.
     *
     * @param p1 The first vertex of the triangle.
     * @param p2 The second vertex of the triangle.
     * @param p3 The third vertex of the triangle.
     * @throws IllegalArgumentException if the vertices do not form a valid triangle.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = plane.findGeoIntersectionsHelper(ray, maxDistance);
        if (intersections == null)
            return null;
        intersections = List.of(new GeoPoint(this, intersections.get(0).point));
        Point rayP0 = ray.head;
        Vector rayVec = ray.direction;
        Vector v1 = vertices.get(0).subtract(rayP0);
        Vector v2 = vertices.get(1).subtract(rayP0);
        double t1 = Util.alignZero(rayVec.dotProduct(v1.crossProduct(v2)));
        if (t1 == 0)
            return null;

        Vector v3 = vertices.get(2).subtract(rayP0);
        double t2 = Util.alignZero(rayVec.dotProduct(v2.crossProduct(v3)));
        if (t1 * t2 <= 0)
            return null;

        double t3 = Util.alignZero(rayVec.dotProduct(v3.crossProduct(v1)));
        return t1 * t3 <= 0 ? null : intersections;
    }
}