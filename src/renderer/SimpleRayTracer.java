package renderer;


import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;
import lighting.*;

/**
 *
 * RayTracerBasic.
 *
 */

public class SimpleRayTracer extends RayTracerBase {
    private static final double DELTA = 0.1;

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
        List<GeoPoint> geoPoints = scene.geometries.findGeoIntersections(ray);
        return (geoPoints == null) ? scene.background : calcColor(ray.findClosestGeoPoint(geoPoints), ray);
    }

    /**
     *
     * @param intersection of the geometry
     * @param ray to calculate the color
     * @return the color
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity().add(intersection.geometry.getEmission()).add(calcLocalEffects(intersection, ray));
    }

    /**
     * calculate the effects of the geometry itself without the effect of other
     * geometries on this object
     *
     * @param gp  the point to calculate the effects on
     * @param ray the ray we intersected with
     * @return the result color of the local effects
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
            if (nl * nv > 0 && unshaded(gp, l, n, nl,lightSource)){
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)), iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }


    /**
     * calculates the diffusive light part of the object
     *
     * @param material the material of the object
     * @param cosAngle the cosine of the angle between the light and the normal to
     *                 the object
     * @return the diffusive light color
     */
    private Double3 calcDiffusive(Material material, double cosAngle) {
        return material.kD.scale(cosAngle > 0 ? cosAngle : -cosAngle);
    }

    /**
     * calculates the specular light part of the object
     *
     * @param material the material of the object
     * @param normal   the normal to the object
     * @param lightDir the direction of the light
     * @param cosAngle the cosine of the angle between the light and the normal to
     *                 the object
     * @param rayDir   the direction the camera is pointed to
     * @return
     */
    private Double3 calcSpecular(Material material, Vector normal, Vector lightDir, double cosAngle, Vector rayDir) {
        Vector r = lightDir.subtract(normal.scale(2 * cosAngle));
        double coefficient = -rayDir.dotProduct(r);
        coefficient = coefficient > 0 ? coefficient : 0;
        return material.kS.scale(Math.pow(coefficient, material.nShininess));
    }

    /**
     * checks if a point on a geometry is shaded by a light source
     *
     * @param gp       the point on the geometry
     * @param l        the direction vector of the light source
     * @param n        the normal to the geometry
     * @return True if the object is not shaded, False otherwise
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl, LightSource light) {
        Vector lightDir = l.scale(-1);
        Ray lightRay = new Ray(gp.point.add(n.scale(nl < 0 ? DELTA : -DELTA)), lightDir);
        return scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point)) == null;
    }
}
