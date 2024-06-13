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

    /**
     @Override
    public List<Point> findIntersections(Ray ray) {
        if(plane.findIntersections(ray)==null)
            {return null;}
        else {
            Vector rayDirection = ray.direction;

            // point of ray p0
            Point p0 = ray.head;

            // 3 points of 3 triangle vertex
            Point p1 = vertices.get(0);
            Point p2 = vertices.get(1);
            Point p3 = vertices.get(2);

            // calculate the direction from any vertex to ray p0
            Vector vector1 = p1.subtract(p0);
            Vector vector2 = p2.subtract(p0);
            Vector vector3 = p3.subtract(p0);

            // calculate the cross product between 3 vectors
            Vector crossProduct1 = vector1.crossProduct(vector2);
            Vector crossProduct2 = vector2.crossProduct(vector3);
            Vector crossProduct3 = vector3.crossProduct(vector1);

            // calculate if the dot product between ray direction and vectors are positive
            // or negative
            double dotProduct1 = rayDirection.dotProduct(crossProduct1);
            double dotProduct2 = rayDirection.dotProduct(crossProduct2);
            double dotProduct3 = rayDirection.dotProduct(crossProduct3);

            // check if all dot product result is with same sign
            if ((dotProduct1 > 0 && dotProduct2 > 0 && dotProduct3 > 0)
                    || (dotProduct1 < 0 && dotProduct2 < 0 && dotProduct3 < 0)) {
                return plane.findIntersections(ray);
            }
            return null;

        }
    }*/

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersections = plane.findGeoIntersectionsHelper(ray);
        if (intersections == null)
            return null;
        intersections = List.of(new GeoPoint(this,intersections.get(0).point));
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