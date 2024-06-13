package renderer;


import primitives.Color;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

/**
 * Class implementing a ray tracer for any scene.
 */
public abstract class RayTracerBase {

    protected Scene scene;

    /**
     * Creates a ray tracer for a given scene.
     *
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Receives ray and returns the color of the nearest intersection point on the
     * ray.
     *
     * @param ray
     * @return color of the closest intersection point on the ray
     */
    public abstract Color traceRay(Ray ray);
}