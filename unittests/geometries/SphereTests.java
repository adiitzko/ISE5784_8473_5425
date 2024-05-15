package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Double3;
import java.util.List;
import geometries.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test class to check all function of Sphere class
 */
class SphereTests {
    private final Sphere sphere1 = new Sphere(new Point(1, 2, 3),10);// positive coordinate
    private final Point p1 = new Point(-1, -2, -4);// negative coordinate

    /***
     * The function checks the integrity of the getNormal function
     */
    @Test
    void testGetNormal() {
        // positive coordinate sphere and point
        assertEquals(new Vector(-2 / Math.sqrt(69), -4 / Math.sqrt(69), -7 / Math.sqrt(69)), sphere1.getNormal(p1),
                "ERROR: TC 01");
    }

    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray( new Point(-1, 0, 0),new Vector(1, 1, 0))),"Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = sphere.findIntersections(new Ray( new Point(-1, 0, 0), new Vector(3, 1, 0) ));

        assertEquals(2, result.size(),"Wrong number of points");
        if (result.get(0).getXyz().d1 > result.get(1).getXyz().getD1())
            result = List.of(result.get(1), result.get(0));
        assertEquals( List.of(p1, p2), result,"Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray(new Point(1, 0.5, 0),new Vector(-1, -1, -2)));
        assertEquals(1, result.size(), "Wrong number of points");

        // TC04: Ray starts after the sphere (0 points)
        assertNull("The ray starts after the sphere", sphere.findIntersections(new Ray(new Vector(1, 2, 0),new Point(4, 10, 0))));

        // =============== Boundary Values Tests ==================

        // ** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(2, 0, 0), new Vector(-1, 0, 1)));
        assertEquals( 1, result.size(), "Wrong number of points");

        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull("The ray starts at sphere and goes outside", sphere.findIntersections(new Ray(new Vector(1, 0, 0),new Point(3, 0, 0))));

        // ** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)

        result = sphere.findIntersections(new Ray(new Point(1, -2, 0),new Vector(0, 1, 0)));
        assertEquals(2, result.size(),"Wrong number of points");
        if (result.get(0).getXyz().getD1() > result.get(1).getXyz().getD1())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(1,- 1, 0), new Point(1, 1, 0)), result,"Ray crosses sphere");

        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, -1, 0),new Vector(0, 1, 0)));
        assertEquals(1, result.size(),"Wrong number of points");

        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray(new Point(0.5, 0, 0),new Vector(4, 0, 0)));
        assertEquals(1, result.size(),"Wrong number of points");

        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Point(1, 0, 0),new Vector(2.52,-5.02, 0)));
        assertEquals(1, result.size(),"Wrong number of points");

        // TC17: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray(new Point(2, 0, 0),new Vector(1, 0, 0)));
        assertNull(result,"Wrong number of points");
        assertNull("The ray starts at sphere and goes outside", sphere.findIntersections(new Ray(new Vector(0, 1, 0),new Point(1, 1, 0))));

        // TC18: Ray starts after sphere (0 points)
        assertNull("The ray starts  after sphere", sphere.findIntersections(new Ray(new Vector(0, 1, 0),new Point(1, 2, 0))));

        // ** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull("The ray starts before the tangent point", sphere.findIntersections(new Ray(new Vector(-2, -1, 0),new Point(-0.5,-0.5, 0))));

        // TC20: Ray starts at the tangent point
        assertNull("The ray starts at the tangent point", sphere.findIntersections(new Ray(new Vector(-1,-1, 0),new Point(1, 0, 1))));

        // TC21: Ray starts after the tangent point
        assertNull("The ray starts after the tangent point", sphere.findIntersections(new Ray(new Vector(0, -2, 1),new Point(1, 1, 1))));

        // ** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull("The ray's line is outside, ray is orthogonal to ray start to sphere's center line", sphere.findIntersections(new Ray(new Vector(0, -2, 1),new Point(-0.5, 0, 0))));

    }
}