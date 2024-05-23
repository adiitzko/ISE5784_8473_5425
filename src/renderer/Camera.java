package renderer;
import static primitives.Util.*;

import java.util.MissingResourceException;

import primitives.*;

/**
 * This class represents a Camera with various attributes such as location, direction vectors,
 * width, height, and distance.
 * The Camera class uses the Builder Design Pattern for object creation and implements the Cloneable
 * interface to allow cloning of Camera objects.
 */
public class Camera implements Cloneable {
    private Point p0;    //location
    private Vector vUp, vTo, vRight;
    private double width=0, height=0, distance=0;

    /**
     * point p0
     * @return p0
     */

    public Point getP0() {
        return p0;
    }
    /**
     * vector up
     * @return vUp
     */
    public Vector getvUp() {
        return vUp;
    }
    /**
     * vector To
     * @return vTo
     */
    public Vector getvTo() {
        return vTo;
    }
    /**
     * vector right
     * @return vRight
     */
    public Vector getvRight() {
        return vRight;
    }
    /**
     * the width
     * @return width
     */
    public double getWidth() {
        return width;
    }
    /**
     * height
     * @return height
     */
    public double getHeight() {
        return height;
    }
    /**
     * distance
     * @return distance
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
    public Ray constructRay(int Nx, int Ny, int j, int i) {
        return null;
    }

    public static class Builder
    {
        private final Camera camera;

        /**
         * Default constructor that initializes a new Camera object.
         */
        public Builder() {
            this.camera = new Camera();
        }

        /**
         * Constructor that initializes the Builder with an existing Camera object.
         *
         * @param camera the Camera object to initialize the Builder with
         */
        public Builder(Camera camera) {
            this.camera = camera;
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
            if (!isZero(vTo.dotProduct(vUp))) {
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
        public Camera build() throws MissingResourceException {
            // Constants for exception messages
            final String MISSING_DATA_DESCRIPTION = "rendering data is missing";
            final String CAMERA_CLASS_NAME = "Camera";

            try {
                // Check if any relevant fields are zero or missing
                if (camera.p0 == null || camera.vUp == null || camera.vTo == null || camera.width == 0 || camera.height == 0 || camera.distance == 0) {
                    throw new MissingResourceException("Rendering data is missing", CAMERA_CLASS_NAME, MISSING_DATA_DESCRIPTION);
                }

                // Check if the vector pointing to the right is missing
                if (camera.vRight == null) {
                    // Calculate the cross product of vTo and vUp to get the "right" vector
                    camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
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

                // Return a copy of the camera fields
                return (Camera) this.clone();
            } catch (CloneNotSupportedException e) {
                // Handle the CloneNotSupportedException by throwing a RuntimeException
                throw new RuntimeException("Cloning not supported", e);
            }
        }


    }
}
