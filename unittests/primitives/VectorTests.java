package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 *Test class to check all function of Vector class
 */
class VectorTests {

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void testAdd() {

            // ============ Equivalence Partitions Tests ==============
            final Vector v1 = new Vector(1, 2, 3);
            final Vector v2 = new Vector(1, -4, 3);
            assertEquals( v1.add(v2),new Vector(2, -2, 6), "Wrong add");

            // =============== Boundary Values Tests ==================
            try {
                final Vector v3=new Vector(-1,-1,-1);
                final Vector v4=new Vector(1,1,1);
                v3.add(v4);
                fail("Vector (0,0,0) shouldn't be valid");
            }
            catch  (IllegalArgumentException e)
            {
                assertTrue(e.getMessage()!= null);
            }

    }


    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    void testScaling() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        assertEquals( v1.scale(2), new Vector(2, 4, 6),"Wrong Scale");

        // =============== Boundary Values Tests ==================
        try {

            v1.scale(0);
            fail("Vector (0,0,0) not valid");
        }
        catch  (IllegalArgumentException e)
        {
            assertTrue(e.getMessage()!= null); }
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============

        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, -4, 3);
        assertTrue(isZero(v1.dotProduct(v2)-2),"Wrong DotProduct");
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        assertTrue( isZero(v1.length()-Math.sqrt(14)), "Wrong length squared calculate");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        assertTrue(isZero(v1.lengthSquared()-14 ), "Wrong length squared calculate" );
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(0, 3, 4);
        Vector n = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals( 1d, n.lengthSquared(), 0.00001,"wrong normalized vector length");
        assertEquals( new Vector(0, 0.6, 0.8), n,"wrong normalized vector");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(0.00001,v1.length() * v3.length(), vr.length(),"crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue( isZero(vr.dotProduct(v1)),"crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}
    }
}