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

    /**Finds the intersection-geoPoints between a ray and the triangle represented by this object.
     @param ray The ray to intersect with the triangle.
     @return A list of GeoPoints representing the intersection-geoPoints between the ray and the triangle**/
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> rayPoints = plane.findGeoIntersectionsHelper(ray);
        if (rayPoints == null)
            return null;
        //check if the point in out or on the triangle:
        Vector v1 = vertices.get(0).subtract(ray.head);
        Vector v2 = vertices.get(1).subtract(ray.head);
        Vector v3 = vertices.get(2).subtract(ray.head);

        Vector n1 = v1.crossProduct(v2).normalize();
        Vector n2 = v2.crossProduct(v3).normalize();
        Vector n3 = v3.crossProduct(v1).normalize();

        rayPoints.get(0).geometry=this;
        //The point is inside if all  have the same sign (+/-)

        if (Util.alignZero(n1.dotProduct(ray.direction)) > 0 && Util.alignZero(n2.dotProduct(ray.direction)) > 0 && Util.alignZero(n3.dotProduct(ray.direction)) > 0)
        {
            return rayPoints;
        }
        else if (Util.alignZero(n1.dotProduct(ray.direction)) < 0 && Util.alignZero(n2.dotProduct(ray.direction)) < 0 && Util.alignZero(n3.dotProduct(ray.direction)) < 0)
        {
            return rayPoints;
        }
        if (Util.isZero(n1.dotProduct(ray.direction)) || Util.isZero(n2.dotProduct(ray.direction)) || Util.isZero(n3.dotProduct(ray.direction)))
            return null; //there is no instruction point
        return null;//opposite signs
    }
}