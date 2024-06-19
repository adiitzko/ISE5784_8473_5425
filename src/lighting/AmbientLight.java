package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Ambient lighting for scene
 */
public class AmbientLight extends Light {
    /** Black background */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * construct ambient light using a color and an attenuation vector
     *
     * @param iA the color
     * @param kA the attenuation vector
     */
    public AmbientLight(Color iA, Double3 kA) {
        super(iA.scale(kA));
    }

    /**
     * construct ambient light using a color and an attenuation scalar
     *
     * @param iA the color
     * @param kA the attenuation scalar
     */
    public AmbientLight(Color iA, double kA) {
        super(iA.scale(kA));
    }
}