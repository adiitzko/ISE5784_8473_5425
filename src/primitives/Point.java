package primitives;

public class Point {
    /**
     * Represents the origin point (0, 0, 0).
     */
    public static final Point ZERO = new Point(0, 0,0);

    /**
     * Field representing the coordinates of the point as a Double3 object.
     */
    protected final Double3 xyz;

    /**
     * Constructor that initializes the point with the specified x, y, and z coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @param z The z-coordinate of the point.
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructor that initializes the point with the specified Double3 object representing the coordinates.
     *
     * @param xyz The Double3 object representing the point's coordinates.
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    public Double3 getXyz() {
        return xyz;
    }
    /**
     * Checks if two points are equal by comparing their coordinates.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal (same coordinates), false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }

    /**
     * Returns a string representation of the point in the format "Point [xyz=<coordinates>]".
     *
     * @return The string representation of the point.
     */
    @Override
    public String toString() {
        return "Point [xyz=" + xyz + "]";
    }

    /**
     * Adds a vector to the point, resulting in a new point with the sum of the coordinates.
     *
     * @param vector The vector to add.
     * @return A new Point object representing the sum of the point and the vector.
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * Subtracts another point from this point, resulting in a new vector representing the difference between them.
     *
     * @param otherPoint The point to subtract.
     * @return A new Vector object representing the difference between this point and the other point.
     * @throws IllegalArgumentException If the two points are identical, resulting in a zero vector.
     */
    public Vector subtract(Point otherPoint) {
        if(xyz.equals(otherPoint.xyz))
            throw new IllegalArgumentException("Error: Subtraction of two equal vectors results a zero vector");
        return new Vector(xyz.subtract(otherPoint.xyz));
    }

    /**
     * Calculates the squared distance between this point and another point.
     *
     * @param otherPoint The other point to calculate the distance from.
     * @return The squared distance between the two points.
     */
    public double distanceSquared(Point otherPoint) {
        Double3 difference = xyz.subtract(otherPoint.xyz);
        return difference.d1 * difference.d1 + difference.d2 * difference.d2 + difference.d3 * difference.d3;
    }

    /**
     * Calculates the Euclidean distance (straight-line distance) between this point and another point.
     *
     * @param otherPoint The other point to calculate the distance from.
     * @return The Euclidean distance between the two points.
     */
    public double distance(Point otherPoint) {
        return Math.sqrt(distanceSquared(otherPoint));
    }

}

