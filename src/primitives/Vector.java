package primitives;

public class Vector extends Point{

    public Vector(double x, double y, double z) {
        super(x,y,z);
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector cannot be zero");
        }
    }

    public Vector (Double3 xyz) {
        super(xyz);
        if(xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector cannot be zero");
        }
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (obj instanceof Vector other)
                return super.equals(other);
        return false;
    }

    @Override
    public String toString(){
        return super.toString();
    }


    public Vector add(Vector vec) {
        if(this.equals(vec.scale(-1))) {
            throw new IllegalArgumentException("Error: Adding opposite vectors gives the zero vector");
        }
        return new Vector(this.xyz.add(vec.xyz));
    }

    public Vector scale(double scalar) {
        return new Vector(this.xyz.scale(scalar));
    }

    public double dotProduct(Vector vec) {
        return this.xyz.d1 * vec.xyz.d1 + this.xyz.d2 * vec.xyz.d2 + this.xyz.d3 * vec.xyz.d3;
    }

    public Vector crossProduct(Vector vec) {
       double x = this.xyz.d2 * vec.xyz.d3 - this.xyz.d3 * vec.xyz.d2;
       double y = this.xyz.d3 * vec.xyz.d1 - this.xyz.d1 * vec.xyz.d3;
       double z = this.xyz.d1 * vec.xyz.d2 - this.xyz.d2 * vec.xyz.d1;
       return new Vector(x, y, z);
    }

    public double lengthSquared(){
        return this.dotProduct(this);
    }

    public double length(){
        return Math.sqrt(this.lengthSquared());
    }

    public Vector normalize() {
        Vector vec = new Vector(this.xyz);
        return vec.scale(1/this.length());
    }
}
