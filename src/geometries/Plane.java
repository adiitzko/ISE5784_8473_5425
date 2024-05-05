package geometries;

import primitives.Vector;
import primitives.Point;
/**
 * Represents a plane in three-dimensional space.
 *  *  * @author Ruth Katanov 326295425  Adi Itzkovich 214608473
 */
public class Plane implements Geometry  {

         private final Point p;
         protected final Vector normal;
    /**
     * Constructs a plane from three points.
     * @param p1 The first point on the plane.
     * @param p2 The second point on the plane.
     * @param p3 The third point on the plane.
     */
        public Plane(Point p1, Point p2, Point p3) {
            this.normal = null;
            this.p = p1;
        }
    /**
     * Constructs a plane from a point and a normal vector.
     * @param p      A point on the plane.
     * @param normal The normal vector to the plane.
     */
        public Plane(Point _p, Vector _normal) {
            this.p = _p;
            this.normal = _normal.normalize();
        }
    /**
     * Gets the normal vector to the plane at a specified point.
     * @param point A point on the surface of the plane (unused).
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

    }
