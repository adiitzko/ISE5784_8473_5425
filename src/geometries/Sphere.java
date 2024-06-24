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
public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
    Point p0 = ray.head;
    if (_center.equals(p0))
        return List.of(new GeoPoint(this, ray.getPoint(_radius)));
    Vector u = _center.subtract(p0);
    double tm = u.dotProduct(ray.direction);
    double d = Math.sqrt(u.lengthSquared() - tm * tm);
    double dif = Util.alignZero(d - _radius);
    if (dif >= 0)
        return null;
    double th = Math.sqrt(_radius * _radius - d * d);
    double t2 = Util.alignZero(tm + th);
    double t1 = Util.alignZero(tm - th);
    if (t2 <= 0 || Util.alignZero(t1 - maxDistance) >= 0)
        return null;
    if (Util.alignZero(t2 - maxDistance) >= 0)
        return t1 > 0 ? List.of(new GeoPoint(this, ray.getPoint(t1))) : null;
    return t1 > 0 //
            ? List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2))) //
            : List.of(new GeoPoint(this, ray.getPoint(t2)));
}
}
