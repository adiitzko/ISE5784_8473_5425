package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a geometric shape in three-dimensional space.
 * @author Ruth Katanov 326295425  Adi Itzkovich 214608473
 */
public interface Geometry {

    /**
     * Gets the normal vector to the geometry at a specified point.
     *
     * @param point A point on the surface of the geometry.
     * @return The normal vector to the geometry.
     */
    Vector getNormal(Point point);
}
