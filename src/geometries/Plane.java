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
public class Plane extends Geometry {

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point rayP0 = ray.head;
        if (rayP0.equals(p))
            return null;
        double denom = normal.dotProduct(ray.direction);
        if (Util.isZero(denom))
            return null;
        double t = Util.alignZero(normal.dotProduct(p.subtract(rayP0)) / denom);
        return t <= 0 || Util.alignZero(t - maxDistance) >= 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
    }


}
