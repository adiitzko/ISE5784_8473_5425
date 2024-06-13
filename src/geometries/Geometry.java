package geometries;

import primitives.*;

/**
 * Represents a geometric shape in three-dimensional space.
 * @author Ruth Katanov 326295425  Adi Itzkovich 214608473
 */
public abstract class Geometry extends Intersectable {

    private Material material = new Material();

    /**
     * emission light of the object
     */
    protected Color emission = Color.BLACK;

    /**
     * Gets the normal vector to the geometry at a specified point.
     *
     * @param point A point on the surface of the geometry.
     * @return The normal vector to the geometry.
     */
    abstract public Vector getNormal(Point point);

    /**
     * getter for the color of the emission light
     * @return the color of the emission light
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * setter for the color of the emission light
     * @param emission the color of the emission light
     * @return this object
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /** getter for the material
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }
    /** setter for the Geometry
     * @param material the material to set
     * @return this object
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}
