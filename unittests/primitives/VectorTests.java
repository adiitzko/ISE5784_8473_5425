package primitives;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTests {

    @Test
    void testEquals() {
    }

    @Test
    void testToString() {
    }
    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    void add() {

            // ============ Equivalence Partitions Tests ==============
            Vector v1 = new Vector(1, 2, 3);
            Vector v2 = new Vector(1, -4, 3);
            assertEquals( v1.add(v2),new Vector(2, -2, 6), "Wrong add");

            // =============== Boundary Values Tests ==================
            try {
                Vector v3=new Vector(-1,-1,-1);
                Vector v4=new Vector(1,1,1);
                v3.add(v4);
                fail("Vector (0,0,0) shouldnt be valid");
            }
            catch  (IllegalArgumentException e)
            {
                assertTrue(e.getMessage()!= null);
            }

    }


    @Test
    void scale() {
    }

    @Test
    void dotProduct() {
    }

    @Test
    void crossProduct() {
    }

    @Test
    void lengthSquared() {
    }

    @Test
    void length() {
    }

    @Test
    void normalize() {
    }
}