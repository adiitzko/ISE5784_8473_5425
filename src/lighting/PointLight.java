/**
 *
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * light that starts at given point without direction
 *
 * @author Yonatan
 *
 */
public class PointLight extends Light implements LightSource {
    private final Point position;
    double kC = 1, kL = 0, kQ = 0;

    /**
     * getter for kC
     *
     * @param kC the kC to set
     * @return this object
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * getter for kL
     *
     * @param kL the kL to set
     * @return this object
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * getter for kQ
     *
     * @param kQ the kQ to set
     * @return this object
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * construct PointLight using color, position
     *
     * @param intensity the light color
     * @param position  the light position
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p)
    {
        double dSquared = p.distanceSquared(position);
        double d = Math.sqrt(dSquared);
        return getIntensity().scale(1 / (kC + kL * d + kQ * dSquared));
    }

    @Override
    public Vector getL(Point p)
    {
        if (p.equals(position))
        {
            return null;
        }
        return p.subtract(position).normalize();
    }

    /**
     * get distance of light from given point
     * @param point point
     * @return double
     */
    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }

}
