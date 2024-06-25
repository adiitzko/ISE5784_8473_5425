package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;

public abstract class Intersectable {
    /**
     * find all points in a geometry that intersect with a ray
     *
     * @param ray to find intersections with
     * @return list of intersection points
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }



    /**
     * find all GeoPoints that intersect with a ray while ignoring the points that
     * are further than a given distance
     *
     * @param ray         to find intersections with
     * @return list of intersection points
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * Finds all intersection points between the given ray and the geometry up to a maximum distance.
     * This method is to be implemented by subclasses to provide the actual intersection logic.
     *
     * @param ray the ray for which intersections are to be found.
     * @return a list of GeoPoint objects where the ray intersects the geometry within the specified distance, or null if there are no intersections.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);


    /**
     * this class represents a point and the geometry that contains the point
     *
     * @author Shulman and Yonatan
     *
     */
    public static class GeoPoint {
        /**
         * the geometry that contains the point
         */
        public Geometry geometry;
        /**
         * the point
         */
        public Point point;

        /**
         * constructs a GeoPoint with a point and the geometry that contains the point
         *
         * @param geometry the geometry that contains the point/
         * @param point    the point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj instanceof GeoPoint other)
                return this.point.equals(other.point) && this.geometry.equals(other.geometry);
            return false;
        }

        @Override
        public String toString() {
            return geometry.toString() + point;
        }

    }

}

