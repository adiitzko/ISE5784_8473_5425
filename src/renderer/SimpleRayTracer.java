package renderer;

import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
import java.util.List;
import lighting.*;
import primitives.Ray;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


/**
 * SimpleRayTracer class for basic ray tracing operations.
 */
public class SimpleRayTracer extends RayTracerBase {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;

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
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * Calculates the color at the intersection point.
     *
     * @param intersection The intersection point.
     * @param ray           The ray.
     * @return The color at the intersection point.
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K));
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
        Color color = calcLocalEffects(gp, ray, k);
        return (level == 1) ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     *
     * Calculates the local effects (diffuse and specular) at a given intersection
     * point with a ray.
     *
     * @param gp  The intersection point and its associated geometry.
     * @param ray The ray that intersected with the geometry.
     * @return The color resulting from the local effects.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.direction;
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = Util.alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = Util.alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sign(nv)

                Double3 ktr = transparency(gp,l,n,lightSource);
                if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K)||ktr.product(k).equals(MIN_CALC_COLOR_K))) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)),
                            iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }
    /**
     * Calculate the diffuse light effect on the point
     *
     * @param material       diffuse attenuation factor
     * @param nl the intensity of the light source at this point
     * @return the color of the diffusive
     */
    private Double3 calcDiffusive(Material material, double nl) {
        nl = Math.abs(nl);
        return material.kD.scale(nl); // Kd * |l * n| * Il
    }

    /**
     * Calculate the specular factor and change the color by it, Light created by a
     * special break of light.
     *
     * @param material       specular attenuation factor
     * @param l              the direction of the light
     * @param n              normal from the point
     * @param v              direction of the viewer
     * @param nl the intensity of the light source at the point
     * @return the color of the point
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, Double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl)); // r=l-2*(l*n)*n
        double vr = Util.alignZero(v.dotProduct(r)); // vr=v*r
        double vrnsh = Math.pow(Math.max(0, -vr), material.Shininess); // vrnsh=max(0,-vr)^nshininess
        return material.kS.scale(vrnsh); // Ks * (max(0, - v * r) ^ Nsh) * Il
    }
    /**
     * Constructs the reflected ray from a given point.
     *
     * @param gp the point at which the ray is reflected.
     * @param v the view direction.
     * @param n the normal at the point.
     * @return the reflected ray.
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(gp.point, r, n); // שימוש בבנאי החדש של Ray
    }

    /**
     * Constructs the refracted ray from a given point.
     *
     * @param gp the point at which the ray is refracted.
     * @param v the view direction.
     * @param n the normal at the point.
     * @return the refracted ray.
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v, n);
    }

    /**
     * Helper method for calculating the color of a point considering global effects such as reflection and refraction.
     *
     * @param gp    The intersection point on a geometry object.
     * @param ray   The ray from the camera that intersected the geometry.
     * @param level The recursion level for global effects calculation.
     * @param k     The initial attenuation coefficient (between 0-1).
     * @return The color of the point with consideration of global effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Vector v = ray.direction;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(material, n, constructReflectedRay(gp, v, n), level, material.kR, k)
                .add(calcGlobalEffect(material, n, constructRefractedRay(gp, v, n), level, material.kT, k));
    }

    /**
     * Helping method for color calculation (function calcGlobalEffects) of a point
     * on a geometry as it sees from the camera point of view. Calculating the
     * global effects.
     *
     * @param material The material of the object.
     * @param n The normal at the intersection point.
     * @param ray The reflected or refracted ray.
     * @param level The recursion level.
     * @param kx The reflection or refraction coefficient.
     * @param k The accumulated attenuation factor.
     * @return The color of the point with consideration of global effects.
     */
    private Color calcGlobalEffect(Material material, Vector n, Ray ray, int level, Double3 kx, Double3 k) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);
       //
        return calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * Find the closest intersection point with a ray.
     *
     * @param ray The ray to check intersections with.
     * @return The closest intersection point of the ray and geometry.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return null;
        return ray.findClosestGeoPoint(intersections);
    }
//    /**
//     * Checks if a given point is unshaded by finding intersections between the point and the light source.
//     *
//     * @param gp   The geometric point to check for shading.
//     * @param l    The direction from the point towards the light source.
//     * @param n    The normal vector at the point.
//     * @param light The light source.
//     * @return {@code true} if the point is unshaded, {@code false} otherwise.
//     */
//    private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource light) {
//        Vector lightDirection = l.scale(-1); // from point to light source
//        Point point = gp.point.add(n.scale(Util.alignZero(n.dotProduct(lightDirection)) < 0 ? DELTA : -DELTA));
//        Ray lightRay = new Ray(point, lightDirection);
//        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
//        if (intersections == null)
//            return true;
//        double lightDistance = light.getDistance(gp.point);
//        for (GeoPoint gp1 : intersections) {
//            if (Util.alignZero(gp1.point.distance(gp.point) - lightDistance) <= 0)
//                //&& gp1.geometry.getMaterial().kT == 0)
//                return false;
//        }
//        return true;
//    }


    /**
     * Calculates the transparency coefficient of a point for a specific light source.
     *
     * @param gp     The intersection point on a geometry object.
     * @param l      The direction from the point to the light source.
     * @param n      The normal vector at the intersection point.
     * @param light  The light source.
     * @return The transparency coefficient of the point for the light source.
     */
    private Double3 transparency(GeoPoint gp, Vector l, Vector n,  LightSource light) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection,n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return Double3.ONE;
        Double3 ktr=Double3.ONE;
        double lightDistance = light.getDistance(gp.point);
        for (GeoPoint gp1 : intersections) {
            if (Util.alignZero(gp1.point.distance(gp.point) - lightDistance) <= 0)
            {
                ktr=ktr.product(gp1.geometry.getMaterial().kT);
                if(ktr.lowerThan(MIN_CALC_COLOR_K))
                    return Double3.ZERO;
            }

        }
        return ktr;
    }
    /**
     * Trace the ray and calculates the color of the point that interact with the geometries of the scene
     *
     * @param rays the ray that came out of the camera
     * @return the color of the object that the ray is interact with
     */
    @Override
    public Color TraceRays(List<Ray> rays) {
        Color color = Color.BLACK;
        for (Ray ray : rays) {
            GeoPoint clossestGeoPoint = findClosestIntersection(ray);
            if (clossestGeoPoint == null)
                color = color.add(scene.background);
            else color = color.add(calcColor(clossestGeoPoint, ray));
        }
        return color.reduce(rays.size());
    }

    /**
     * Performs adaptive super-sampling for a given pixel.
     *
     * @param centerP     The center point of the pixel.
     * @param Width       The width of the pixel.
     * @param Height      The height of the pixel.
     * @param minWidth    The minimum width of a sub-pixel for further sampling.
     * @param minHeight   The minimum height of a sub-pixel for further sampling.
     * @param cameraLoc   The location of the camera.
     * @param Vright      The vector representing the right direction.
     * @param Vup         The vector representing the up direction.
     * @param prePoints   A list of pre-sampled points to avoid redundancy.
     * @return The color computed for the pixel through adaptive super-sampling.
     */
    @Override
    public Color AdaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints) {
        if (Width < minWidth * 2 || Height < minHeight * 2) {
            // If the pixel is smaller than the minimum size, trace a ray through the pixel and return the color.
            return this.traceRay(new Ray(cameraLoc, centerP.subtract(cameraLoc)));
        }

        List<Point> nextCenterPList = new LinkedList<>();
        List<Point> cornersList = new LinkedList<>();
        List<primitives.Color> colorList = new LinkedList<>();
        Point tempCorner;
        Ray tempRay;
        for (int i = -1; i <= 1; i += 2) {
            for (int j = -1; j <= 1; j += 2) {
                tempCorner = centerP.add(Vright.scale(i * Width / 2)).add(Vup.scale(j * Height / 2));
                cornersList.add(tempCorner);
                if (prePoints == null || !Util.isInList(prePoints, tempCorner)) {
                    tempRay = new Ray(cameraLoc, tempCorner.subtract(cameraLoc));
                    nextCenterPList.add(centerP.add(Vright.scale(i * Width / 4)).add(Vup.scale(j * Height / 4)));
                    colorList.add(traceRay(tempRay));
                }
            }
        }

        if (nextCenterPList == null || nextCenterPList.size() == 0) {
            // If no valid sub-pixels were found, return black color.
            return primitives.Color.BLACK;
        }

        boolean isAllEquals = true;
        primitives.Color tempColor = colorList.get(0);
        // Check if all colors in the colorList are almost equal
        for (primitives.Color color : colorList) {
            if (!tempColor.isAlmostEquals(color))
                isAllEquals = false;
        }
        if (isAllEquals && colorList.size() > 1)
            // If all colors are equal and there is more than one color, return the first color.
            return tempColor;


        tempColor = primitives.Color.BLACK;
        // Recursively perform adaptive super-sampling on sub-pixels
        for (Point center : nextCenterPList) {
            tempColor = tempColor.add(AdaptiveSuperSamplingRec(center, Width / 2, Height / 2, minWidth, minHeight, cameraLoc, Vright, Vup, cornersList));
        }
        // Reduce the color by dividing by the number of sub-pixels
        return tempColor.reduce(nextCenterPList.size());
    }
    /**
     * Performs regular super-sampling for a given pixel.
     *
     * @param centerP     The center point of the pixel.
     * @param Width       The width of the pixel.
     * @param Height      The height of the pixel.
     * @param minWidth    The minimum width of a sub-pixel for further sampling.
     * @param minHeight   The minimum height of a sub-pixel for further sampling.
     * @param cameraLoc   The location of the camera.
     * @param Right       The vector representing the right direction.
     * @param Vup         The vector representing the up direction.
     * @param prePoints   A list of pre-sampled points to avoid redundancy.
     * @return The color computed for the pixel through regular super-sampling.
     */
    public Color RegularSuperSampling(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Right, Vector Vup, List<Point> prePoints) {
        List<Color> colorList = new ArrayList<>();

        int numSubPixelsX = (int) Math.ceil(Width / minWidth);
        int numSubPixelsY = (int) Math.ceil(Height / minHeight);

        Random random = new Random();
        // Iterate over sub-pixels and perform regular super-sampling
        for (int i = 0; i < numSubPixelsY; i++) {
            for (int j = 0; j < numSubPixelsX; j++) {
                double offsetX = minWidth * j;
                double offsetY = minHeight * i;

                double randomX = offsetX + random.nextDouble() * minWidth;
                double randomY = offsetY + random.nextDouble() * minHeight;

                Point subPixelPoint = centerP.add(Right.scale(randomX - Width / 2)).add(Vup.scale(randomY - Height / 2));

                // Check if the sub-pixel's point is already sampled
                if (prePoints == null || !Util.isInList(prePoints, subPixelPoint)) {
                    Ray ray = new Ray(cameraLoc, subPixelPoint.subtract(cameraLoc));
                    colorList.add(traceRay(ray));
                }
            }
        }

        if (colorList.isEmpty()) {
            // If no valid sub-pixels were found, return black color.
            return primitives.Color.BLACK;
        }

        Color averageColor = Color.BLACK;
        // Calculate the average color by adding all colors in the colorList
        for (Color color : colorList) {
            averageColor = averageColor.add(color);
        }
        // Reduce the color by dividing by the number of sub-pixels
        return averageColor.reduce(colorList.size());
    }

}