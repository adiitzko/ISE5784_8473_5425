package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.List;


/**
 * Represents a triangle in three-dimensional space.
 * A triangle is a specific type of polygon with three vertices.
 * @author Ruth Katanov 326295425  Adi Itzkovich 214608473
 */
public class Triangle extends Polygon {

    /**
     * Constructs a triangle with the given vertices.
     * The vertices must be provided in a counterclockwise order.
     *
     * @param p1 The first vertex of the triangle.
     * @param p2 The second vertex of the triangle.
     * @param p3 The third vertex of the triangle.
     * @throws IllegalArgumentException if the vertices do not form a valid triangle.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {

        Vector rayDirection = ray.direction;

        // point of ray p0
        Point p0 = ray.head;

        // 3 points of 3 triangle vertex
        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Point p3 = vertices.get(2);

        // calculate the direction from any vertex to ray p0
        Vector vector1 = p1.subtract(p0);
        Vector vector2 = p2.subtract(p0);
        Vector vector3 = p3.subtract(p0);

        // calculate the cross product between 3 vectors
        Vector crossProduct1 = vector1.crossProduct(vector2);
        Vector crossProduct2 = vector2.crossProduct(vector3);
        Vector crossProduct3 = vector3.crossProduct(vector1);

        // calculate if the dot product between ray direction and vectors are positive
        // or negative
        double dotProduct1 = rayDirection.dotProduct(crossProduct1);
        double dotProduct2 = rayDirection.dotProduct(crossProduct2);
        double dotProduct3 = rayDirection.dotProduct(crossProduct3);

        // check if all dot product result is with same sign
        if ((dotProduct1 > 0 && dotProduct2 > 0 && dotProduct3 > 0)
                || (dotProduct1 < 0 && dotProduct2 < 0 && dotProduct3 < 0)) {
            return plane.findIntersections(ray);
        }

        return null;
    }
}