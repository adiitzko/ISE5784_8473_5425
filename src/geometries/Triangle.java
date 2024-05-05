package geometries;

import primitives.Point;

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
}