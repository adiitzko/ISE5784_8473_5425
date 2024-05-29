package renderer;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import primitives.Util;

import java.util.MissingResourceException;

/**
 * This class represents a Camera with various attributes such as location, direction vectors,
 * width, height, and distance.
 * The Camera class uses the Builder Design Pattern for object creation and implements the Cloneable
 * interface to allow cloning of Camera objects.
 */
public class Camera implements Cloneable {
    private Point p0;    // location
    private Vector vUp, vTo, vRight;
    private double width = 0, height = 0, distance = 0;

    @Override
    public Camera clone() {
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported", e);
        }
    }

    /**
     * Returns the location of the Camera.
     *
     * @return the location point p0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the up direction vector of the Camera.
     *
     * @return the up direction vector vUp
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * Returns the forward direction vector of the Camera.
     *
     * @return the forward direction vector vTo
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * Returns the right direction vector of the Camera.
     *
     * @return the right direction vector vRight
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * Returns the width of the view plane.
     *
     * @return the width of the view plane
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns the height of the view plane.
     *
     * @return the height of the view plane
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the distance to the view plane.
     *
     * @return the distance to the view plane
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Private default constructor to prevent direct instantiation.
     */
    private Camera() {
        // This constructor is intentionally empty to prevent direct instantiation
    }

    /**
     * Returns a new Builder object for constructing a Camera instance.
     *
     * @return a new Builder object
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    public Ray constructRay(int nX, int nY, int j, int i)  {      // Implementation of ray construction (not provided in the initial code)
        Point Pc = p0.add(vTo.scale(distance));

        // Calculate the pixel dimensions on the view plane
        double Rx = width / nX;
        double Ry = height / nY;

        // Set the initial point on the view plane to Pc
        Point Pij = Pc;

        // Calculate the coordinates of the pixel on the view plane
        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = -(i - (nY - 1) / 2d) * Ry;

        // Check if the pixel is at the center of the view plane
        if (Util.isZero(Xj) && Util.isZero(Yi)) {
            return new Ray(p0, Pij.subtract(p0));
        }

        // Check if the pixel is on the horizontal axis of the view plane
        if (Util.isZero(Xj)) {
            Pij = Pij.add(vUp.scale(Yi));
            return new Ray(p0, Pij.subtract(p0));
        }

        // Check if the pixel is on the vertical axis of the view plane
        if (Util.isZero(Yi)) {
            Pij = Pij.add(vRight.scale(Xj));
            return new Ray(p0, Pij.subtract(p0));
        }

        // Calculate the final point on the view plane for the specified pixel
        Pij = Pij.add(vRight.scale(Xj).add(vUp.scale(Yi)));

        // Return the constructed ray from the camera's location to the calculated point on the view plane
        return new Ray(p0, Pij.subtract(p0));
    }

    public static class Builder {
        private final Camera camera;

        /**
         * Default constructor that initializes a new Camera object.
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * Sets the location of the Camera.
         *
         * @param p0 the location point to set
         * @return the Builder object for chaining
         * @throws IllegalArgumentException if p0 is null
         */
        public Builder setLocation(Point p0) {
            if (p0 == null) {
                throw new IllegalArgumentException("Location point cannot be null");
            }
            this.camera.p0 = p0;
            return this;
        }

        /**
         * Sets the direction vectors of the Camera.
         *
         * @param vTo the forward direction vector to set
         * @param vUp the up direction vector to set
         * @return the Builder object for chaining
         * @throws IllegalArgumentException if the vectors are not perpendicular or are null
         */
        public Builder setDirection(Vector vTo, Vector vUp) {
            if (vTo == null || vUp == null) {
                throw new IllegalArgumentException("Direction vectors cannot be null");
            }
            if (!Util.isZero(vTo.dotProduct(vUp))) {
                throw new IllegalArgumentException("Direction vectors must be perpendicular");
            }
            this.camera.vTo = vTo.normalize();
            this.camera.vUp = vUp.normalize();
            this.camera.vRight = vTo.crossProduct(vUp).normalize();
            return this;
        }

        /**
         * Sets the view plane size of the Camera.
         *
         * @param width the view plane width to set
         * @param height the view plane height to set
         * @return the Builder object for chaining
         * @throws IllegalArgumentException if width or height are non-positive
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0) {
                throw new IllegalArgumentException("Width must be positive");
            }
            if (height <= 0) {
                throw new IllegalArgumentException("Height must be positive");
            }
            this.camera.width = width;
            this.camera.height = height;
            return this;
        }

        /**
         * Sets the view plane distance of the Camera.
         *
         * @param distance the view plane distance to set
         * @return the Builder object for chaining
         * @throws IllegalArgumentException if distance is non-positive
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance must be positive");
            }
            this.camera.distance = distance;
            return this;
        }

        /**
         * Builds a new Camera object based on the configured parameters and ensures all relevant fields have non-zero values.
         *
         * @return a new Camera object with validated and calculated parameters
         * @throws MissingResourceException if any relevant camera fields are zero or missing
         */
        public Camera build() {
            // Constants for exception messages
            final String MISSING_DATA_DESCRIPTION = "rendering data is missing";
            final String CAMERA_CLASS_NAME = "Camera";

            // Check if any relevant fields are zero or missing
            if (camera.p0 == null || camera.vUp == null || camera.vTo == null || camera.width == 0 || camera.height == 0 || camera.distance == 0) {
                throw new MissingResourceException("Rendering data is missing", CAMERA_CLASS_NAME, MISSING_DATA_DESCRIPTION);
            }

            // Check if vectors are normalized, normalize them if necessary
            if (camera.vUp.length() != 1) {
                camera.vUp = camera.vUp.normalize();
            }
            if (camera.vTo.length() != 1) {
                camera.vTo = camera.vTo.normalize();
            }
            if (camera.vRight.length() != 1) {
                camera.vRight = camera.vRight.normalize();
            }

            // Return a cloned Camera object
            return camera.clone();
        }
    }


}
