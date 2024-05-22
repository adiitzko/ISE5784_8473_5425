package geometries;
import java.util.List;
import primitives.Vector;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
/**
 * Represents a plane in three-dimensional space.
 *  *  * @author Ruth Katanov 326295425  Adi Itzkovich 214608473
 */
public class Plane implements Geometry {

         private final Point p;
         protected final Vector normal;

    /**
     * Constructs a plane from three points.
     * @param p1 The first point on the plane.
     * @param p2 The second point on the plane.
     * @param p3 The third point on the plane.
     */
        public Plane(Point p1, Point p2, Point p3) {
            p = p1;
            try { // try for case the consructor get all point on the same vector or at least two point are the same
                normal = p1.subtract(p2).crossProduct(p1.subtract(p3)).normalize();
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("your points are on the same vector");
            }
        }
    /**
     * Constructs a plane from a point and a normal vector.
     * @param _p      A point on the plane.
     * @param _normal The normal vector to the plane.
     */
        public Plane(Point _p, Vector _normal) {
            this.p = _p;
            this.normal = _normal.normalize();
        }
    /**
     * Gets the normal vector to the plane at a specified point.
     * @param _p A point on the surface of the plane (unused).
     * @return The normal vector to the plane.
     */

        @Override
        public Vector getNormal(Point _p) {
            return this.normal;
        }

    /**
     * Gets the normal vector to the plane
     * @return The normal vector to the plane.
     */
    public Vector getNormal() {
            return normal;
        }

    @Override
    public List<Point> findIntersections(Ray ray) {

        // if the ray and plane are parallel (i.e., dot product between their normal vectors is 0),
        // then there is no intersection between them
        double denominator = this.getNormal().dotProduct(ray.direction);
        if (Util.isZero(denominator)) {
            return null;
        }
        // if the ray's starting point is on the plane, then the ray not intersects the plane at the starting point
        if (p.equals(ray.head))
            return null;
        // t represents the distance between the ray's starting point and the intersection point
        double t = (this.getNormal().dotProduct(p.subtract(ray.head))) / denominator;
        if (t > 0) {
            return List.of(ray.getPoint(t));
        }
        return null;
    }

    }
