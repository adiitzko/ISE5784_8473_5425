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
     * calculates the distance between the light source and a given point
     *
     * @param point the given point
     * @return the distance
     */


    double getDistance(Point point);

}
