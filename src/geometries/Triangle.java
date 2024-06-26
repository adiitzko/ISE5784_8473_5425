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
    /**
     * Finding intersection-geoPoints with a given ray
     * @param ray A ray
     * @return All the intersection-geoPoints of this triangle and the given ray
     */
    //Check if the ray intersect the plane.
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        List<GeoPoint> intersections = plane.findGeoIntersections(ray);
        if (intersections == null) return null;

        Point p0 = ray.head;
        Vector v = ray.direction;

        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector v3 = vertices.get(2).subtract(p0);

        //Check every side of the triangle
        double s1 = v.dotProduct(v1.crossProduct(v2));

        if (Util.isZero(s1)) return null;

        double s2 = v.dotProduct(v2.crossProduct(v3));

        if (Util.isZero(s2)) return null;

        double s3 = v.dotProduct(v3.crossProduct(v1));

        if (Util.isZero(s3)) return null;

        if (!((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0))) return null;

        return List.of(new GeoPoint(this,intersections.get(0).point));
    }
}