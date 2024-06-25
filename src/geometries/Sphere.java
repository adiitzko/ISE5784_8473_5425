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


    /**Finds the intersection-geoPoints between a ray and the sphere represented by this object.
     @param ray The ray to intersect with the sphere.
     @return A list of GeoPoints representing the intersection-geoPoints between the ray and the plsphereane**/
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray){
        if (ray.head.equals(_center)) // if the begin of the ray in the center, the point, is on the radius
            return List.of(new GeoPoint(this,ray.getPoint(_radius)));
        Vector u = _center.subtract(ray.head);
        double tm = Util.alignZero(ray.direction.dotProduct(u));
        double d = Util.alignZero(Math.sqrt(u.lengthSquared()- tm * tm));
        double th = Util.alignZero(Math.sqrt(_radius*_radius - d*d));
        double t1 = Util.alignZero(tm+th);
        double t2 = Util.alignZero(tm-th);


        if (d > _radius)//the ray is out of the sphere
            return null; // there are no instructions


        if (t1 <=0 && t2<=0)//the ray begins after the sphere and goes to the opposite side
            return null;


        if (t1 > 0 && t2 >0)//2 intersections
        {
            //if(Util.alignZero(t1-maxDis)<=0&&Util.alignZero(t2-maxDis)<=0)
            return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this,ray.getPoint(t2)));
        }

        if (t1 > 0)//p0 is in the sphere
        {
            return List.of(new GeoPoint(this,ray.getPoint(t1)));
        }

        else//p0 is in the sphere
            return List.of(new GeoPoint(this,ray.getPoint(t2)));
    }
}
