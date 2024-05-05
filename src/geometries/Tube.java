package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

/**
 * Represents a tube in three-dimensional space.
 * A tube is defined by a ray (axis) and a radius.
 * @author Ruth Katanov 326295425  Adi Itzkovich 214608473
 */
public class Tube extends RadialGeometry {

    /** The axis ray of the tube */
    private final Ray _axisRay;

    /**
     * Constructs a tube with the given axis ray and radius.
     * @param axisRay The axis ray of the tube.
     * @param radius The radius of the tube.
     */
    public Tube(Ray axisRay, double radius) {
        super(radius);
        this._axisRay = axisRay;
    }

    /**
     * Gets the normal vector to the tube at a specified point.
     *
     * @param point A point on the surface of the tube.
     * @return The normal vector to the tube.
     */
    @Override
    public Vector getNormal(Point _p) { return null;}
}