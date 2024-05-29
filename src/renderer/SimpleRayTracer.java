package renderer;


import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 *
 * RayTracerBasic.
 *
 */

public class SimpleRayTracer extends RayTracerBase {

    /**
     * Creates basic ray tracer
     *
     * @param scene
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersectionPoints = scene.geometries.findIntersections(ray);
        if (intersectionPoints == null)
            return scene.background;
        return calcColor(ray.findClosestPoint(intersectionPoints));
    }

    /**
     * Calculates the color at a certain point of intersection.
     *
     * @param p intersection point
     * @return color at the point
     */
    private Color calcColor(Point p) {
        return scene.ambientLight.getIntensity();
    }

}