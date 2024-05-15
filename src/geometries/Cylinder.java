package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Represents a cylinder in three-dimensional space.
 * A cylinder is defined by a height, an axis ray, and a radius.
 *  * @author Ruth Katanov 326295425  Adi Itzkovich 214608473
 */
public class Cylinder extends Tube {

    /** The height of the cylinder */
    private final double _height;

    /**
     * Constructs a cylinder with the given height, axis ray, and radius.
     * @param axisRay The axis ray of the cylinder.
     * @param radius The radius of the cylinder.
     * @param height The height of the cylinder.
     */
    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        this._height = height;
    }


    /**
     * Gets the normal vector to the cylinder at a specified point.
     *
     * @param point A point on the surface of the cylinder.
     * @return The normal vector to the cylinder.
     */
    @Override
    public Vector getNormal(Point point) {
        Point p0 = axisRay.head;
        Vector dir = axisRay.direction;
        double t;
        // if the point is at the base of the cylinder
        try {
            t = dir.dotProduct(point.subtract(p0));
            if (Util.isZero(t))
                return dir.scale(-1);
        } catch (IllegalArgumentException ignore) {
            // the point is in the center of the base
            return dir.scale(-1);
        }

        // if the point is at the top of the cylinder
        if (Util.isZero(t - _height))
            return dir;
        if (Util.isZero(dir.dotProduct(point.subtract(p0))))
            return dir;

        return super.getNormal(point);
    }

}
