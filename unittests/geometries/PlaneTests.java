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
        try {
            Plane myPlane = new Plane(new Point(0, 5, 0), new Point(-5, 0, 0), new Point(0, 0, 3));
            // =============== Boundary Values Tests ==================

            //Ray is parallel to the plane
            // TC01: the ray included in the plane
            Ray myRay = new Ray(new Point(0, 5, 0), new Vector(-5, 0, 0));//the plane include this ray
            assertNull(myPlane.findIntersections(myRay),"A included ray has zero intersection points");
            // TC02: the ray not included in the plane
            myRay = new Ray(new Point(0, -5, 0), new Vector(5, 0, 0));//the plane included this ray
            assertNull(myPlane.findIntersections(myRay),"An un included ray has zero intersection points");

            //Ray is orthogonal to the plane
            // TC03: before the plane
            myRay = new Ray(new Point(2, 4, 0), new Vector(-3, 3, 5));//the ray is orthogonal to the plane
            assertEquals(1, myPlane.findIntersections(myRay).size(),"Ray is orthogonal to the plane and starts before the plane");
            // TC04: at the plane
            myRay = new Ray( new Point(-5, 0, 0), new Vector(-3, 3, 5));//the ray is orthogonal to the plane
            assertNull(myPlane.findIntersections(myRay), "Ray is orthogonal to the plane and starts at the plane" );
            // TC05: after the plane
            myRay = new Ray(new Point(-7, 2, 4), new Vector(-3, 3, 5));//the ray is orthogonal to the plane
            assertNull(myPlane.findIntersections(myRay), "Ray is orthogonal to the plane and starts after the plane");

            //Ray is neither orthogonal nor parallel to and begins at the plane
            // TC06:
            myRay = new Ray(new Point(-1, -1, 0), new Vector(1, 0, 0));//the ray isn't orthogonal or parallel to the plane
            assertNull(myPlane.findIntersections(myRay), "Ray is neither orthogonal nor parallel to and begins at reference point in the plane");

            //Ray is neither orthogonal nor parallel to the plane and begins in
            //the same point which appears as reference point in the plane
            // TC07:
            myRay = new Ray(new Point(0, 0, 3), new Vector(-5, 4, -3));//the ray isn't orthogonal or parallel to the plane but not intersects the plane
            assertNull(myPlane.findIntersections(myRay), "Ray is neither orthogonal nor parallel to and begins at the plane");

            // ============ Equivalence Partitions Tests ================
            // TC08: The Ray must be neither orthogonal nor parallel to the plane
            //Ray does not intersect the plane
            myRay = new Ray(new Point(1, 2, 0), new Vector(-3, -7, 0));
            assertNull(myPlane.findIntersections(myRay),"Ray is neither orthogonal nor parallel but doesnt intersects the plane");

            // TC09:
            // Ray intersects the plane
            myRay = new Ray(new Point(4, 3, 0), new Vector(-5.75, 3.57, 0));//the ray isn't orthogonal or parallel to the plane and intersects the plane
            assertEquals( 1, myPlane.findIntersections(myRay).size(), "Ray is neither orthogonal nor parallel and intersects the plane ");
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("not need throws exception!");
        }
    }
}