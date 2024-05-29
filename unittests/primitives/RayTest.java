package primitives;

import java.util.List;
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
        /**
         * Test method for {@link primitives.Ray#findClosestPoint(List<Point>)}.
         */
        @Test
        void testFindClosestPoint() {
            Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 1, 0));
            Point a = new Point(2, 2, 0);
            Point b = new Point(3, 3, 0);
            Point c = new Point(5, 5, 0);
            List<Point> points = List.of(b, a, c);
            // ============ Equivalence Partitions Tests ==============

            // TC01: Closest point is in the middle of the list
            assertEquals(a, ray.findClosestPoint(points), "Returned wrong result");

            // ============ Boundary Values Tests ==============
            points = List.of();
            // TC02: list is empty (should return null)
            assertNull(ray.findClosestPoint(points), "Should have returned null");

            // TC03: closest point is at start of list
            points = List.of(a, b, c);
            assertEquals(a, ray.findClosestPoint(points), "Returned wrong result");

            // TC04: closest point is at end of list
            points = List.of(b, c, a);
            assertEquals(a, ray.findClosestPoint(points), "Returned wrong result");
        }

    }


