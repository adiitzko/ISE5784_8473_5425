package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



/**
 * test class to check all function of Point class
 *
 */
class PointTests {
    private final Point p1 = new Point(1, 1, 1);
    private final Point p2 = new Point(2, 2, 2);
    private final Vector v1 = new Vector(-1, -1, -1);

    /**
     * checking if the adding work correctly
     */
    @Test
    void testAdd() {
        // =======Equivalence Partitions Tests=======
        // TC01: testing the adding of point and vector (simple test)
        assertEquals(p1.add(v1), new Point(0, 0, 0), "the adding does not work correctly");
    }

    /**
     * checking if the subtracting work correctly
     */
    @Test
    void testSubtract() {
        // =======Equivalence Partitions Tests=======
        // TC01: testing the subtract between two points (simple test)
        assertEquals(p2.subtract(p1), new Vector(1, 1, 1), "ERROR: TC01");

        // ======== subtraction of a point itself ========
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1), "the subtracting does not work correctly");
    }

    /**
     * checking if the DistanceSquared function work correctly
     */
    @Test
    void testDistanceSquared() {
        // =======Equivalence Partitions Tests=======
        // TC01: testing if the distance squared between two points is correct (simple
        // test)
        assertEquals(3, p1.distanceSquared(p2), "ERROR: DistanceSquared doesn't work correctly");

        // =============== Boundary Values Tests ==================

        // TC11: testing if the distance squared between the point to itself is zero
        assertEquals(0, p1.distanceSquared(p1), "ERROR: DistanceSquared doesn't work correctly");
    }

    /**
     * checking if the Distance function work correctly
     */
    @Test
    void testDistance() {
        // =======Equivalence Partitions Tests=======
        // TC01: testing if the distance between two points -with squared - is correct
        // (simple test)
        assertEquals(Math.sqrt(3), p1.distance(p2), "ERROR: Distance doesn't work correctly");

        // =============== Boundary Values Tests ==================

        // TC11: testing if the distance between the point to itself is zero
        assertEquals(0, p1.distance(p1), "ERROR: Distance doesn't work correctly");
    }
}