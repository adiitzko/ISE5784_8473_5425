package primitives;

/**
 * This class represents a 3D vector using linear algebra methods. It inherits from the `Point` class
 * which represents a point in 3D space. A vector has a magnitude and direction.
 */
public class Vector extends Point{

    /**
     * Constructor that initializes the vector with the specified x, y, and z components.
     *
     * @param x The x-component of the vector.
     * @param y The y-component of the vector.
     * @param z The z-component of the vector.
     * @throws IllegalArgumentException If the resulting vector is zero (all components are zero).
     */
    public Vector(double x, double y, double z) {
        super(x,y,z);
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector cannot be zero");
        }
    }

    /**
     * Constructor that initializes the vector with a Double3 object representing the coordinates.
     *
     * @param xyz The Double3 object representing the vector's coordinates.
     * @throws IllegalArgumentException If the resulting vector is zero (all components are zero).
     */
    public Vector (Double3 xyz) {
        super(xyz);
        if(xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector cannot be zero");
        }
    }

    /**
     * Checks if two vectors are equal by comparing their coordinates using the superclass method.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal (same coordinates), false otherwise.
     */
    @Override
    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (obj instanceof Vector other)
                return super.equals(other);
        return false;
    }

    /**
     * Returns a string representation of the vector in the same format as the Point class.
     *
     * @return The string representation of the vector.
     */
    @Override
    public String toString(){
        return super.toString();
    }

    /**
     * Adds another vector to this vector, resulting in a new vector with the sum of the coordinates.
     *
     * @param vec The vector to add.
     * @return A new Vector object representing the sum of the two vectors.
     * @throws IllegalArgumentException If adding the vectors results in a zero vector (opposite directions).
     */
    public Vector add(Vector vec) {
        if(this.equals(vec.scale(-1))) {
            throw new IllegalArgumentException("Error: Adding opposite vectors gives the zero vector");
        }
        return new Vector(this.xyz.add(vec.xyz));
    }

    /**
     * Scales the vector by a scalar factor, resulting in a new vector with the scaled coordinates.
     *
     * @param scalar The scalar factor to multiply the vector by.
     * @return A new Vector object representing the scaled vector.
     */
    public Vector scale(double scalar) {
        return new Vector(this.xyz.scale(scalar));
    }

    /**
     * Calculates the dot product (scalar product) of this vector with another vector.
     *
     * @param vec The other vector.
     * @return The dot product of the two vectors.
     */
    public double dotProduct(Vector vec) {
        return this.xyz.d1 * vec.xyz.d1 + this.xyz.d2 * vec.xyz.d2 + this.xyz.d3 * vec.xyz.d3;
    }

    /**
     * Calculates the cross product (vector product) of this vector with another vector.
     *
     * @param vec The other vector.
     * @return A new Vector object representing the cross product of the two vectors.
     */
    public Vector crossProduct(Vector vec) {
       double x = this.xyz.d2 * vec.xyz.d3 - this.xyz.d3 * vec.xyz.d2;
       double y = this.xyz.d3 * vec.xyz.d1 - this.xyz.d1 * vec.xyz.d3;
       double z = this.xyz.d1 * vec.xyz.d2 - this.xyz.d2 * vec.xyz.d1;
       return new Vector(x, y, z);
    }

    /**
     * Calculates the squared length (magnitude) of the vector.
     * This avoids calculating the square root for efficiency purposes.
     *
     * @return The squared length of the vector.
     */
    public double lengthSquared(){
        return this.dotProduct(this);
    }

    /**
     * Calculates the length (magnitude) of the vector using the squared length.
     *
     * @return The length (magnitude) of the vector.
     */
    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Normalizes the vector by dividing it by its length, resulting in a unit vector with a magnitude of 1.
     * A new vector is created to avoid modifying the original vector.
     *
     * @return A new Vector object representing the normalized vector.
     * @throws ArithmeticException If the vector length is zero (division by zero).
     */
    public Vector normalize() {
        Vector vec = new Vector(this.xyz);
        return vec.scale(1/this.length());
    }
}
