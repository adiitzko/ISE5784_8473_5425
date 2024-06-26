package lighting;

import primitives.*;

/**
 * light that starts at given point and is strong in one direction
 *
 * @author Yonatan and Shulman
 *
 */
public class SpotLight extends PointLight {
    final private Vector direction;
    private int narrowness = 1;
    /**
     * construct SpotLight using color, position, and direction vector
     *
     * @param intensity the light color
     * @param position  the light position
     * @param direction the direction vector
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }
    /**
     * Sets the constant attenuation factor.
     * @param kC the constant attenuation factor
     * @return the current SpotLight instance (for chaining)
     */
    @Override
    public SpotLight setKc(double kC) {
        super.setKc(kC);
        return this;
    }

    /**
     * Sets the linear attenuation factor.
     * @param kL the linear attenuation factor
     * @return the current SpotLight instance (for chaining)
     */
    @Override
    public SpotLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }

    /**
     * Sets the quadratic attenuation factor.
     * @param kQ the quadratic attenuation factor
     * @return the current SpotLight instance (for chaining)
     */
    @Override
    public SpotLight setKq(double kQ) {
        super.setKq(kQ);
        return this;
    }


    /**
     * Calculates the intensity of the light at a given point, taking into account
     * the spotlight's direction.
     *
     * @param p The point in the scene.
     * @return The intensity of the light at the specified point.
     */
    @Override
    public Color getIntensity(Point p) {
        Vector l = super.getL(p);
        if (l == null)
            return super.getIntensity();

        double directionDotL = Util.alignZero(direction.dotProduct(l));
        if (directionDotL <= 0)
            return Color.BLACK;

        return super.getIntensity(p).scale(directionDotL); // the denominator from the super!!
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }

    /**
     * narrows the beam of the ray
     *
     * @param narrowness the narrowness of the beam
     * @return this object
     */
    public SpotLight setNarrowBeam(int narrowness) {
        this.narrowness = narrowness;
        return this;
    }

}
