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


//@Override
    /**
     * Finds the intersection point(s) of a given Ray with the Sphere.
     * @param ray the Ray to intersect with the Sphere
     * @return a List of Point objects representing the intersection point(s) of the Ray and the Sphere.
     * If no intersection occurs, returns null.
     */
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Point p0 = ray.head; // ray's starting point
        Point O = this._center; //the sphere's center point
        Vector V = ray.direction; // "the v vector" from the presentation

        // if p0 on center, calculate with line parametric representation
        // the direction vector normalized.
        if (O.equals(p0)) {
            Point newPoint = p0.add(ray.direction.scale(this._radius));
            return List.of(new GeoPoint(this,newPoint));
        }

        Vector U = O.subtract(p0);
        double tm = V.dotProduct(U);
        double d = Math.sqrt(U.lengthSquared() - tm * tm);
        if (d >= this._radius) {
            return null;
        }

        double th = Math.sqrt(this._radius * this._radius - d * d);
        double t1 = tm - th;
        double t2 = tm + th;

        if (t1 > 0 && t2 > 0) {
            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this,p1),new GeoPoint(this,p2));
        }

        if (t1 > 0) {
            Point p1 = ray.getPoint(t1);
            return List.of(new GeoPoint(this,p1));
        }

        if (t2 > 0) {
            Point p2 = ray.getPoint(t2);
            return List.of(new GeoPoint(this,p2));
        }
        return null;

    }
}
