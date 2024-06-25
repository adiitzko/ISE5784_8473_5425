package renderer;

import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;
import lighting.*;

/**
 * SimpleRayTracer class for basic ray tracing operations.
 */
public class SimpleRayTracer extends RayTracerBase {
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * Creates a basic ray tracer.
     *
     * @param scene The scene to be rendered.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint intersectionPoint = findClosestIntersection(ray);
        return (intersectionPoint == null) ? scene.background : calcColor(intersectionPoint, ray);
    }

    /**
     * Calculates the color at the intersection point.
     *
     * @param intersection The intersection point.
     * @param ray           The ray.
     * @return The color at the intersection point.
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE));
    }

    /**
     * Recursive method to calculate the color with global effects.
     *
     * @param gp    The intersection point.
     * @param ray   The ray.
     * @param level The recursion level.
     * @param k     The attenuation coefficient.
     * @return The calculated color.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray);
        return (level == 1) ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * Calculates the local effects (diffuse and specular) at the intersection point.
     *
     * @param gp  The intersection point.
     * @param ray The ray.
     * @return The color with local effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.direction;
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = Util.alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = Util.alignZero(n.dotProduct(l));
            if (nl * nv > 0 && unshaded(gp, l, n, nl,lightSource)) {
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)), iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }

    /**
     * Calculates the diffuse light part of the object.
     *
     * @param material The material of the object.
     * @param cosAngle The cosine of the angle between the light and the normal to the object.
     * @return The diffuse light color.
     */
    private Double3 calcDiffusive(Material material, double cosAngle) {
        return material.kD.scale(cosAngle > 0 ? cosAngle : -cosAngle);
    }

    /**
     * Calculates the specular light part of the object.
     *
     * @param material The material of the object.
     * @param normal   The normal to the object.
     * @param lightDir The direction of the light.
     * @param cosAngle The cosine of the angle between the light and the normal to the object.
     * @param rayDir   The direction the camera is pointed to.
     * @return The specular light color.
     */
    private Double3 calcSpecular(Material material, Vector normal, Vector lightDir, double cosAngle, Vector rayDir) {
        Vector r = lightDir.subtract(normal.scale(2 * cosAngle));
        double coefficient = -rayDir.dotProduct(r);
        coefficient = coefficient > 0 ? coefficient : 0;
        return material.kS.scale(Math.pow(coefficient, material.Shininess));
    }

    /**
     * Checks if a given point is unshaded by finding intersections between the point and the light source.
     *
     * @param gp   The geometric point to check for shading.
     * @param l    The direction from the point towards the light source.
     * @param n    The normal vector at the point.
     * @param light The light source.
     * @param nv   The dot product between the normal vector and the light direction.
     * @return {@code true} if the point is unshaded, {@code false} otherwise.
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nv, LightSource light) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nv < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return true;
        double lightDistance = light.getDistance(gp.point);
        for (GeoPoint gp1 : intersections) {
            if (Util.alignZero(gp1.point.distance(gp.point) - lightDistance) <= 0)
                //&& gp1.geometry.getMaterial().kT == 0)
                return false;
        }
        return true;
    }

    /**
     * Find the closest intersection point with a ray.
     *
     * @param ray The ray to check intersections with.
     * @return The closest intersection point of the ray and geometry.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null)
            return null;
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * Helper method for calculating the color of a point considering global effects such as reflection and refraction.
     *
     * @param geoPoint The observed point on the geometry.
     * @param ray      The ray from the camera that intersected the geometry.
     * @param level    The recursion level for global effects calculation.
     * @param k        The initial attenuation coefficient (between 0-1).
     * @return The color of the point with consideration of global effects.
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
        Material material = geoPoint.geometry.getMaterial();
        Vector v = ray.direction;
        Vector normal = geoPoint.geometry.getNormal(geoPoint.point);
        Ray reflectedRay = constructReflectionRay(geoPoint, normal, v);
        Ray refractedRay = constructRefractionRay(geoPoint, normal, v);
        return calcGlobalEffect(reflectedRay, level, k, material.kR)
                .add(calcGlobalEffect(refractedRay, level, k, material.kT));
    }

    /**
     * Helping method for color calculation (function calcGlobalEffects) of a point
     * on a geometry as it sees from the camera point of view. Calculating the
     * global effects.
     *
     * @param ray   - ray from the camera that intersect the geometry
     * @param level - level of recursion
     * @param k     - the material reflection\refraction coefficient value (between
     *              0-1)
     * @param kx    - product of the global and local attenuation coefficient
     * @return the color of the point with consideration of global effect
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K))
            return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null)
            return scene.background.scale(kx);
        return Util.isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.direction)) ? Color.BLACK
                : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }
    /**
     * The function calculates the ray reflection of these rays will move the
     * starting point of the ray on the regular geometry line towards the new ray.
     *
     * @param geoPoint - the intersected point on the geometry
     * @param normal   - the normal of the geometry
     * @param vector   - the ray to be reflected from light source
     * @return the reflected ray
     */
    private Ray constructReflectionRay(GeoPoint geoPoint, Vector normal, Vector vector) {
        Vector reflectedVector = vector.subtract(normal.scale(2 * vector.dotProduct(normal))).normalize();
        // Move the start point slightly in the direction of the normal to avoid self-intersection
        Point movedPoint = geoPoint.point.add(normal.scale(-2 * normal.dotProduct(vector))).add(normal.scale(1e-6));
        return new Ray(movedPoint, reflectedVector);
    }


    /**
     * The function calculates the ray transparency of these rays will move the
     * starting point of the ray on the regular geometry line towards the new ray.
     *
     * @param geoPoint - the intersected point on the geometry
     * @param normal   - the normal of the geometry
     * @param vector   - the ray to be transparency from light source
     * @return the refracted ray
     */
    private Ray constructRefractionRay(GeoPoint geoPoint, Vector normal, Vector vector) {
        // Move the start point slightly in the direction of the normal to avoid self-intersection
        Point movedPoint = geoPoint.point.add(normal.scale(1e-6));
        return new Ray(movedPoint, vector);
    }

}