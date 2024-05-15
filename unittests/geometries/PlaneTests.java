package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import java.util.List;
import primitives.Ray;


import static org.junit.jupiter.api.Assertions.*;

/**
 * test class to check all function of Plane class
 */
 class PlaneTests {

    /**
     * test for constructor
     */
    @Test
    void testConstructor() {
        // =============== Boundary Values Tests ==================

        // check constructor two point the same
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 6.3), new Point(1, 2, 6.3), new Point(0, 0, 0)),
                "ERROR: ctor get two point the same");

        // check constructor all point on the same line
        assertThrows(IllegalArgumentException.class,
                () -> new Plane(new Point(1, 2, 3), new Point(2, 4, 6), new Point(0.5, 1, 1.5)),
                "ERROR: ctor get all point on the same line");
    }

    /**
     * tests for getNormal(Point)
     */
    @Test
    void testGetNormal() {
        // plane to tests
        Plane p = new Plane(new Point(1, 1, 0), new Point(2, 1, 0), new Point(1, 2, 0));

        // ============ Equivalence Partitions Tests ==============

        // test normal to plane (it's can be 2 sides)
        boolean bool = new Vector(0, 0, 1).equals(p.getNormal(new Point(3, 2, 0)))
                || new Vector(0, 0, -1).equals(p.getNormal(new Point(3, 2, 0)));
        assertTrue(bool, " ERROR: getNormal() wrong result");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    void testFindIntersections() {
        Plane plane = new Plane(new Point(1, 1, 0), new Vector(0, 1, 0));
        List<Point> points;

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane (1 point)
        points = plane.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 1, 0)));
        assertEquals(List.of(new Point(1, 1, 0)), points, "TC01");

        // TC02: Ray does not intersect the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(10, 10, 10), new Vector(1, 1, 0))), "TC02");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC11: Ray included (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 0, 0))), "TC11");

        // TC12: Ray not included in the plane(0 points)
        assertNull(plane.findIntersections(new Ray(new Point(10, 10, 10), new Vector(1, 0, 0))), "TC12");

        // **** Group: Ray is orthogonal to the plane
        // TC13: Ray starts before the plane (1 points)
        points = plane.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 1, 0)));
        assertEquals(List.of(new Point(0, 1, 0)), points, "TC13");

        // TC14: Ray starts in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0, 1, 0), new Vector(0, 17, 0))), "TC14");

        // TC15: Ray starts after the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0, 5, 0), new Vector(0, 1, 0))), "TC15");

        // **** Group: Ray is neither orthogonal nor parallel
        // TC16: Ray starts at the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0, 1, 0), new Vector(1, 1, 0))), "TC16");

        // TC17: Ray starts begins in the same point which appears as reference point in
        // the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 1, 0))), "TC17");
    }
}