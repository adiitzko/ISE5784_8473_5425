package primitives;

public class Point {
    protected final Double3 xyz;

    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return "Point [xyz=" + xyz + "]";
    }

    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    public Point subtract(Point otherPoint) {
        if(xyz.equals(otherPoint.xyz))
            throw new IllegalArgumentException("Error: Subtraction of two equal vectors results a zero vector");
        return new Vector(xyz.subtract(otherPoint.xyz));
    }

    public double distanceSquared(Point otherPoint) {
        Double3 difference = xyz.subtract(otherPoint.xyz);
        return difference.d1 * difference.d1 + difference.d2 * difference.d2 + difference.d3 * difference.d3;
    }

    public double distance(Point otherPoint) {
        return Math.sqrt(distanceSquared(otherPoint));
    }

}

