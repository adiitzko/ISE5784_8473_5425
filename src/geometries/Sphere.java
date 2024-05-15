package geometries;

import primitives.Point;
import primitives.Vector;
import java.util.List;
import primitives.Ray;
import primitives.Util;

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
        Point p0 = ray.head;
        Vector v = ray.direction.subtract(_center);
        Vector u;

        try {
            u = _center.subtract(p0); // p0 == center the ray start from the center of the sphere
        } catch (IllegalArgumentException e) {
            return List.of(Util.isZero(this._radius) ? p0 : p0.add(ray.direction.scale(this._radius)));
        }

        double tm = Util.alignZero(v.dotProduct(u));
        double dSquared = u.lengthSquared() - tm * tm;
        double thSquared = Util.alignZero(this._radius * this._radius - dSquared);

        if (thSquared <= 0)
            return null;// no intersections

        double th = Util.alignZero(Math.sqrt(thSquared));
        if (th == 0)
            return null;// ray tangent to sphere

        double t1 = Util.alignZero(tm - th);
        double t2 = Util.alignZero(tm + th);

        // ray starts after sphere
        if (Util.alignZero(t1) <= 0 && Util.alignZero(t2) <= 0)
            return null;

        // 2 intersections
        if (Util.alignZero(t1) > 0 && Util.alignZero(t2) > 0) {
            // P1 , P2
            return List.of(Util.isZero(t1) ? p0 : p0.add(ray.direction.scale(t1)),
                    Util.isZero(t2) ? p0 : p0.add(ray.direction.scale(t2)));
        }

        // 1 intersection
        if (Util.alignZero(t1) > 0)
            return List.of(Util.isZero(t1) ? p0 : p0.add(ray.direction.scale(t1)));
        else
            return List.of(Util.isZero(t2) ? p0 : p0.add(ray.direction.scale(t2)));
    }
}
