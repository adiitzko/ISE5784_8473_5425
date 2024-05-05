package geometries;

import primitives.Point;
import primitives.Ray;
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
    public Vector getNormal(Point _p) {
        return super.getNormal(_p);
    }

}
