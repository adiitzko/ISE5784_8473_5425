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
            try { // try for case the constructor get all point on the same vector or at least two point are the same
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
        Point rayP0 = ray.head;
        Vector rayDirection = ray.direction;

        // Ray starts begins in the same point which appears as reference point in the
        // plane (0 points)
        if (rayP0 == this.p)
            return null;

        /**
         * calculate the dotProduct between the ray direction and normal plane
         */
        double dotProduct = this.normal.dotProduct(rayDirection);

        // Checking whether the plane and the ray intersect each other or are parallel
        // to each other
        if (Util.isZero(dotProduct)) {
            return null;
        }

        // direction to plane p0 from ray p0
        Vector p0direction = p.subtract(rayP0);

        /**
         * checking if direction of ray is to plane if directionRayScale < 0 the ray
         * direction of the beam is not to the surface of plane if directionRayScale = 0
         * the ray is on surface of plane if directionRayScale > 0 the ray direction of
         * the beam is cut the surface of plane
         */
        double directionRayScale = Util.alignZero(this.normal.dotProduct(p0direction) / dotProduct);

        if (directionRayScale > 0) {
            // find the intersection by dot product between the direction to plane from the
            // po ray and
            // directionRayScale (which calculates the distance between the point and the
            // surface in the given direction)
            return List.of(ray.getPoint(directionRayScale));
        }

        return null;
    }

    }
