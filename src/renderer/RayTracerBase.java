package renderer;


import primitives.Color;
import primitives.Ray;
import scene.Scene;
import primitives.Point;
import primitives.Vector;
import java.util.List;


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
    /**
     * Trace the ray and calculates the color of the point that interact with the geometries of the scene
     * @param rays the ray that came out of the camera
     * @return the color of the object that the ray is interact with
     */
    public abstract Color TraceRays(List<Ray> rays);

    /**
     * Checks the color of the pixel with the help of individual rays and averages between
     * them and only if necessary continues to send beams of rays in recursion
     * @param centerP center pixl
     * @param Width Length
     * @param Height width
     * @param minWidth min Width
     * @param minHeight min Height
     * @param cameraLoc Camera location
     * @param Vright Vector right
     * @param Vup vector up
     * @param prePoints pre Points
     * @return Pixel color
     */
    public abstract Color AdaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints);
    public abstract Color RegularSuperSampling(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints);

}