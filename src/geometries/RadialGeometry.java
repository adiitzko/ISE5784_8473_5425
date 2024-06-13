package geometries;
/**
 * An abstract class representing all geometries with a radius.
 * @author Ruth Katanov 326295425  Adi Itzkovich 214608473
 */
public abstract class RadialGeometry extends Geometry {

    /** The radius of the geometry */
    protected final double _radius;

    /**
     * Constructs a radial geometry with the given radius.
     * @param radius The radius of the geometry.
     */
    public RadialGeometry(double radius) {
        this._radius = radius;
    }

}
