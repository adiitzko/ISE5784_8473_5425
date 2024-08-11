package renderer;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import primitives.Util;
import primitives.Color;
import java.util.List;
import java.util.LinkedList;
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
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    int antiAliasing = 1;
    private int numOfThreads = 1;
    private Point centerPoint;
    private boolean adaptive = false;

    /**
     * set the adaptive
     * @return the Camera object
     */
    public Camera setadaptive(boolean adaptive) {
        this.adaptive = adaptive;
        return this;
    }

    public Camera setRaynum(int nRays) {
        antiAliasing = nRays;
        return this;
    }
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

    /**
     * Sets image writer in builder pattern
     *
     * @param iw writer
     * @return Camera that results
     */
    public Camera setImageWriter(ImageWriter iw) {
        this.imageWriter = iw;
        return this;
    }

    /**
     * Sets ray tracer in builder pattern
     *
     * @param rt tracer
     * @return Camera that results
     */
    public Camera setRayTracer(RayTracerBase rt) {
        this.rayTracer = rt;
        return this;
    }

    /**
     * Constructs a ray from the camera through a specific pixel on the view plane.
     *
     * @param nX the number of pixels in the horizontal direction
     * @param nY the number of pixels in the vertical direction
     * @param j the horizontal index of the pixel
     * @param i the vertical index of the pixel
     * @return the constructed ray from the camera through the specified pixel
     */
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
         * Sets the image writer of the Camera.
         *
         * @param iw the ImageWriter to set
         * @return the Builder object for chaining
         */
        public Builder setImageWriter(ImageWriter iw) {
            if (iw == null) {
                throw new IllegalArgumentException("ImageWriter cannot be null");
            }
            this.camera.imageWriter = iw;
            return this;
        }

        /**
         * Sets the ray tracer of the Camera.
         *
         * @param rt the RayTracerBase to set
         * @return the Builder object for chaining
         */
        public Builder setRayTracer(RayTracerBase rt) {
            if (rt == null) {
                throw new IllegalArgumentException("RayTracerBase cannot be null");
            }
            this.camera.rayTracer = rt;
            return this;
        }

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
            this.camera.distance = distance;
            // every time that we change the distance from the view plane
            // we will calculate the center point of the view plane again
            camera.centerPoint = camera.p0.add(camera.vTo.scale(this.camera.distance));
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
            // Check if imageWriter and rayTracer are set
            if (camera.imageWriter == null || camera.rayTracer == null) {
                throw new MissingResourceException("ImageWriter or RayTracerBase is missing", CAMERA_CLASS_NAME, MISSING_DATA_DESCRIPTION);
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

//     Renders the image using the configured ray tracer and image writer.
//
//     @throws UnsupportedOperationException If the image writer or ray tracer is missing.
//     */
//    public Camera renderImage() {
//        if (this.imageWriter == null)
//            throw new UnsupportedOperationException("Missing imageWriter");
//        if (this.rayTracer == null)
//            throw new UnsupportedOperationException("Missing rayTracerBase");
//
//        for (int i = 0; i < this.imageWriter.getNy(); i++) {
//            for (int j = 0; j < this.imageWriter.getNx(); j++) {
//                Color color = castRay(j, i);
//                this.imageWriter.writePixel(j, i, color);
//            }
//        }
//        return this;
//    }    /**
//

    /**
     * Renders the image using the current image writer and ray tracer.
     * The ray tracer find the color and the image writer colors the pixels
     *
     * @return This camera instance.
     * @throws UnsupportedOperationException If either the image writer or the ray tracer is not initialized.
     */
    public Camera renderImage() {
        // Get the number of pixels in X and Y directions from the image writer
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        // Initialize the Pixel class with the number of rows, columns, and total pixels
        Pixel.initialize(nY, nX, 1);

        // Check if adaptive mode is enabled
        if (!adaptive) {
            // Render the image using regular super-sampling (non-adaptive)
            // Create multiple threads to process the pixels in parallel
            while (numOfThreads-- > 0) {
                new Thread(() -> {
                    // Iterate over each pixel in the image
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()) {
                        // Construct rays for the current pixel and trace them using the ray tracer
                        List<Ray> rays = constructRays(nX, nY, pixel.col, pixel.row);
                        Color pixelColor = rayTracer.TraceRays(rays);
                        // Write the pixel color to the image writer
                        imageWriter.writePixel(pixel.col, pixel.row, pixelColor);
                    }
                }).start();
            }
            // Wait for all the threads to finish processing the pixels
            Pixel.waitToFinish();
        }
        else {
            // Render the image using adaptive super-sampling
            // Create multiple threads to process the pixels in parallel
            while (numOfThreads-- > 0) {
                new Thread(() -> {
                    // Iterate over each pixel in the image
                    for (Pixel pixel = new Pixel(); pixel.nextPixel(); Pixel.pixelDone()) {
                        // Apply adaptive super-sampling to determine the pixel color
                        Color pixelColor = SuperSampling(nX, nY, pixel.col, pixel.row, antiAliasing, true);
                        // Write the pixel color to the image writer
                        imageWriter.writePixel(pixel.col, pixel.row, pixelColor);
                    }
                }).start();
            }
            // Wait for all the threads to finish processing the pixels
            Pixel.waitToFinish();
        }
        // Return the camera object
        return this;
    }
    /**
     * Creates a grid of lines on the image.
     * Colors the pixels where the grid lines appear and leaves the other pixels unchanged.
     *
     * @param interval the spacing between the grid lines in pixels.
     * @param color the color of the grid lines.
     * @return the Camera object for method chaining.
     */
    public Camera printGrid(int interval, Color color) {
        double nX = imageWriter.getNx();
        double nY = imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
        return this;
    }
    /**
     * set the threadsCount
     * @return the Camera object
     */
    public Camera setMultiThreading(int threadsCount) {
        this.numOfThreads = threadsCount;
        return this;
    }

    /**
     * Writes the image to the output.
     * Throws an exception if the imageWriter is null.
     *
     * @return the Camera object for method chaining.
     * @throws MissingResourceException if the imageWriter field is null.
     */

    public Camera writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException("ImageWriter field cannot be null", Camera.class.getName(), "");
        }
        imageWriter.writeToImage();
        return this;
    }


    /*
     Casts a ray through a specified pixel and returns the color of the intersection point.
     @param j The x-coordinate of the pixel.
     @param i The y-coordinate of the pixel.
     @return The color of the intersection point.
     */
    private Color castRay(int j, int i) {
        if (antiAliasing ==1){
            List<Ray>  ray = constructRays(this.imageWriter.getNx(),this.imageWriter.getNy(),j,i);
        return this.rayTracer.traceRay(ray.get(0));}
        else {
            List<Ray> rays = constructRays( this.imageWriter.getNx(),this.imageWriter.getNy(),j,i);
            return (calcColorSum(rays));
        }
    }
    /**
     * Calculates the sum of colors for a list of rays.
     *
     * @param rays The list of rays.
     * @return The sum of colors.
     */
    private Color calcColorSum(List<Ray> rays) {
        Color colorSum = new Color(0, 0, 0);
        for (Ray ray : rays) {
            // Trace each ray and add its color to the sum
            Color calcColor = rayTracer.traceRay(ray);
            colorSum = colorSum.add(calcColor);
        }
        // Reduce the sum of colors by dividing it by the number of rays
        colorSum = colorSum.reduce(rays.size());
        return colorSum;
    }

    /**
     * Constructs a list of rays for a given image pixel.
     *
     * @param nX     The number of pixels in the X direction.
     * @param nY     The number of pixels in the Y direction.
     * @param //xPixel      The X index of the pixel.
     * @param //yPixel      The Y index of the pixel.
     * @return The list of constructed rays.
     */

    public List<Ray> constructRays(int nX, int nY, int j, int i) {
        List<Ray> rays = new LinkedList<>();
        Point centralPixel = getCenterOfPixel(nX, nY, j, i);
        double rY = height / nY / antiAliasing;
        double rX = width / nX / antiAliasing;
        // Variables to store the X and Y offsets of each sub-pixel within the anti-aliasing grid
        double x, y;

        for (int rowNumber = 0; rowNumber < antiAliasing; rowNumber++) {
            for (int colNumber = 0; colNumber < antiAliasing; colNumber++) {
                // Calculate the X and Y offsets for the current sub-pixel
                y = -(rowNumber - (antiAliasing - 1d) / 2) * rY;
                x = (colNumber - (antiAliasing - 1d) / 2) * rX;
                // Calculate the position of the current sub-pixel within the pixel
                Point pIJ = centralPixel;
                if (y != 0) pIJ = pIJ.add(vUp.scale(y));
                if (x != 0) pIJ = pIJ.add(vRight.scale(x));
                // Construct a ray from the camera position to the current sub-pixel
                rays.add(new Ray(p0, pIJ.subtract(p0)));
            }
        }
        return rays;
    }
    /**
     * Checks the color of the pixel with the help of individual rays and averages between them and only
     * if necessary continues to send beams of rays in recursion
     * @param nX amount of pixels by length
     * @param nY amount of pixels by width
     * @param j The position of the pixel relative to the y-axis
     * @param i The position of the pixel relative to the x-axis
     * @param numOfRays The amount of rays sent
     * @return Pixel color
     */
    private Color SuperSampling(int nX, int nY, int j, int i,  int numOfRays, boolean adaptiveAlising)  {
        // Get the right and up vectors of the camera
        Vector Vright = vRight;
        Vector Vup = vUp;
        // Get the location of the camera
        Point cameraLoc = this.getP0();
        // Calculate the number of rays in each row and column
        int numOfRaysInRowCol = (int)Math.floor(Math.sqrt(numOfRays));
        // If the number of rays is 1, perform regular ray tracing
        if(numOfRaysInRowCol == 1)
            return rayTracer.traceRay(constructRayThroughPixel(nX, nY, j, i));
        // Calculate the center point of the current pixel
        Point pIJ = getCenterOfPixel(nX, nY, j, i);
        // Calculate the height and width ratios of the pixel
        double rY = Util.alignZero(height / nY);
        double rX = Util.alignZero(width / nX);

        // Calculate the pixel row and column ratios
        double PRy = rY/numOfRaysInRowCol;
        double PRx = rX/numOfRaysInRowCol;

        if (adaptiveAlising)
            return rayTracer.AdaptiveSuperSamplingRec(pIJ, rX, rY, PRx, PRy,cameraLoc,Vright, Vup,null);
        else
            return rayTracer.RegularSuperSampling(pIJ, rX, rY, PRx, PRy,cameraLoc,Vright, Vup,null);
    }

    /**
     * get the center point of the pixel in the view plane
     * @param nX number of pixels in the width of the view plane
     * @param nY number of pixels in the height of the view plane
     * @param j  index row in the view plane
     * @param i  index column in the view plane
     * @return the center point of the pixel
     */
    private Point getCenterOfPixel(int nX, int nY, int j, int i) {
        // calculate the ratio of the pixel by the height and by the width of the view plane
        // the ratio Ry = h/Ny, the height of the pixel
        double rY = Util.alignZero(height / nY);
        // the ratio Rx = w/Nx, the width of the pixel
        double rX = Util.alignZero(width / nX);

        // Xj = (j - (Nx -1)/2) * Rx
        double xJ = Util.alignZero((j - ((nX - 1d) / 2d)) * rX);
        // Yi = -(i - (Ny - 1)/2) * Ry
        double yI = Util.alignZero(-(i - ((nY - 1d) / 2d)) * rY);

        Point pIJ = centerPoint;

        if (!Util.isZero(xJ)) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if (!Util.isZero(yI)) {
            pIJ = pIJ.add(vUp.scale(yI));
        }
        return pIJ;
    }

    /**
     * construct ray through a pixel in the view plane
     * nX and nY create the resolution
     * @param nX number of pixels in the width of the view plane
     * @param nY number of pixels in the height of the view plane
     * @param j  index row in the view plane
     * @param i  index column in the view plane
     * @return ray that goes through the pixel (j, i)  Ray(p0, Vi,j)
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point pIJ = getCenterOfPixel(nX, nY, j, i); // center point of the pixel

        //Vi,j = Pi,j - P0, the direction of the ray to the pixel(j, i)
        Vector vIJ = pIJ.subtract(p0);
        return new Ray(p0, vIJ);
    }


    /**
     * Calculates a ray given a point.
     *
     * @param point The point to calculate the ray from.
     * @return The calculated ray.
     */
    public Ray calcRay(Point point) {
        Vector newVector = point.subtract(p0);
        return new Ray(p0, newVector);
    }


}
