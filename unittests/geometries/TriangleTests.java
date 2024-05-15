package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test class to check all function of triangle class
 */
class TriangleTests {
    /**
     * get normal
     */
    @Test
    void testGetNormal() {
        // =============== Equivalence Partitions Tests ==============

        Triangle t = new Triangle(new Point(0, 1, 0), new Point(1, 0, 0), new Point(1, 1, 0));
        assertEquals(new Vector(0, 0, 1),
                t.getNormal(new Point(0, 1, 0)), "the normal not correct!");
    }
}