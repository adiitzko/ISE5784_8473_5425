package primitives;

public class Material {

    /**
     * Diffuse reflection coefficient (kD) for the material.
     * This determines how much light is diffusely reflected from the surface.
     */
    public Double3 kD = Double3.ZERO;

    /**
     * Specular reflection coefficient (kS) for the material.
     * This determines how much light is reflected in a mirror-like fashion from the surface.
     */
    public Double3 kS = Double3.ZERO;

    /**
     * Transparency coefficient (kT) for the material.
     * This determines how transparent the material is (0 means completely opaque).
     */
    public Double3 kT = Double3.ZERO;

    /**
     * Reflection coefficient (kR) for the material.
     * This determines how reflective the material is (0 means no reflection).
     */
    public Double3 kR = Double3.ZERO;

    /**
     * Shininess factor for the material.
     * This determines the sharpness of the specular reflection (higher values mean sharper reflections).
     */
    public int Shininess = 0;

    /**
     * Set the diffuse color of the material to the given color and return the material.
     *
     * @param kD The diffuse color of the material.
     * @return The material itself.
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Set the diffuse coefficient to the given value.
     *
     * @param kD Diffuse reflectivity.
     * @return The material itself.
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Set the specular reflectance of this material to the given value.
     *
     * @param kS specular reflectivity
     * @return The material itself.
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Set the specular reflectance of the material to the given value.
     *
     * @param kS specular reflectivity
     * @return The material itself.
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }
    /**
     * Set the transparency of the material to the given value.
     coefficient is the transmitted fraction where 𝒌𝑻 = 𝟏 when
     object is translucent, 𝒌𝑻 = 𝟎 when the object is opaque.
     *
     * @param kT transparency reflectivity
     * @return The material itself.
     */
    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }
    /**
     * Set the reflection reflectance of the material to the given value.
     Perfect mirror has a 𝒌𝑹 = 𝟏 and matt surface has a 𝒌𝑹 = 0
     * @param kR reflection reflectivity
     * @return The material itself.
     */
    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }
    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param kT transparency reflectivity
     *           channels.
     * @return The updated Material object.
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param kR reflection reflectivity
     *           channels.
     * @return The updated Material object.
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }



    /**
     * This function sets the shininess of the material.
     *
     * @param shininess The shininess of the material.
     * @return The material object itself.
     */
    public Material setShininess(int shininess) {
        this.Shininess = shininess;
        return this;
    }
}