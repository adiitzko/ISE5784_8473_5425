package lighting;

import primitives.*;

/**
 * the functionality light objects should have
 */
public interface LightSource {
    /**
     * calculate the intensity of the light at given point
     *
     * @param p given point
     * @return the intensity at the point
     */
    Color getIntensity(Point p);

    /**
     * calculate the normalized vector from the light source to given point
     *
     * @param p the point
     * @return the normalized vector
     */
    Vector getL(Point p);

    /**
     * Calculates the distance between this light source and a given point in space.
     *
     * @param point The point to which the distance is calculated.
     * @return The distance between the light source and the specified point.
     */
    double getDistance(Point point);
}
