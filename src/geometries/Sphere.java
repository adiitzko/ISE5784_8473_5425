package geometries;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

/**
 * Represents a sphere in three-dimensional space.
 * A sphere is defined by its center point and radius.
 * @author Ruth Katanov 326295425  Adi Itzkovich 214608473
 */
public class Sphere extends RadialGeometry {

    /** The center point of the sphere */
    private final Point _center;

    /**
     * Constructs a sphere with the given center point and radius.
     * @param center The center point of the sphere.
     * @param radius The radius of the sphere.
     */
    public Sphere(Point center, double radius) {
        super(radius);
        this._center = center;
    }

    /**
     * @return The normal vector to the sphere.
     */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(_center).normalize();
    }


    @Override
    public List<Point> findIntersections(Ray ray) {
        // if the ray starts at the center of the sphere
        double tm = 0;
        double d = 0;
        if (!_center.equals(ray.head)){ // if the ray doesn't start at the center of the sphere
            Vector L = _center.subtract(ray.head);
            tm = L.dotProduct(ray.direction);
            d =L.lengthSquared() - tm * tm; // d = (|L|^2 - tm^2)
            if (d < 0)
                d = -d;
            d = Math.sqrt(d);
        }
        if (d > _radius) // if the ray doesn't intersect the sphere
            return null;
        // computing the distance from the ray's start point to the intersection points
        double th = Math.sqrt(_radius * _radius - d * d);
        double t1 = tm - th;
        double t2 = tm + th;
        if (t1 <= 0 && t2 <= 0)
            return null;
        if (Util.alignZero(t2) == 0) // if the ray is tangent to the sphere
            return null;
        if (th == 0)
            return null;
        if (t1 <= 0){ // if the ray starts inside the sphere or the ray starts after the sphere
            return List.of(ray.getPoint(t2));
        }
        if (t2 <= 0) { //if the ray starts after the sphere
            return List.of(ray.getPoint(t1));
        }
        return List.of(ray.getPoint(t1), ray.getPoint(t2)); // if the ray intersects the sphere twice
    }
}
