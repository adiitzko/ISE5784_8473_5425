package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

    /**
     * A class to test the functionality of the Ray class.
     */
    public class RayTest {

        /**
         * Tests the calculation of a point at a positive distance along the ray.
         */
        @Test
        void testPositiveDistance() {
            Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
            Point point = ray.getPoint(1); // Positive distance
            assertEquals(new Point(1, 0, 0), point, "Positive distance calculation failed");
        }

        /**
         * Tests the calculation of a point at a negative distance along the ray.
         */
        @Test
        void testNegativeDistance() {
            Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
            Point point = ray.getPoint(-1); // Negative distance
            assertEquals(new Point(-1, 0, 0), point, "Negative distance calculation failed");
        }

        /**
         * Tests the calculation of a point at zero distance along the ray.
         */
        @Test
        void testZeroDistance() {
            Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
            Point point = ray.getPoint(0); // Zero distance
            assertEquals(new Point(0, 0, 0), point, "Zero distance calculation failed");
        }
    }


