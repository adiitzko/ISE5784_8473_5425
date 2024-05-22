package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test class to check all function of Tube class
 */
 class TubeTests {

    /**
     * test get normal
     */
    @Test
    void testGetNormal() {
        Ray ray = new Ray(Point.ZERO, new Vector(0, 0, 1));
        Tube tube = new Tube(ray,Math.sqrt(2));

        // =============== Equivalence Partitions Tests ==============
        // TC01: simple check
        assertEquals(new Vector(1, 1, 0), tube.getNormal(new Point(1, 1, 2)), "the normal is not correct");

        // =============== Boundary Values Tests ==================
        // TC11: checking if the
        assertEquals(new Vector(1, 1, 0), tube.getNormal(new Point(1, 1, 1)), "the normal is not correct");
    }
}