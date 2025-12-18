package Ex2;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for Index2D: constructors, getters, distance and equals.
 */
public class Index2DTest {

    // Test main constructor and getters
    @Test
    public void testConstructorAndGetters() {
        Index2D p = new Index2D(3, 5);
        assertEquals(3, p.getX());
        assertEquals(5, p.getY());
    }

    // Test copy constructor with another Pixel2D
    @Test
    public void testCopyConstructor() {
        Pixel2D src = new Index2D(7, 9);
        Index2D copy = new Index2D(src);
        assertEquals(7, copy.getX());
        assertEquals(9, copy.getY());
        assertNotSame(src, copy);
    }

    // Test distance2D for two points with known distance
    @Test
    public void testDistance2D() {
        Index2D p1 = new Index2D(0, 0);
        Index2D p2 = new Index2D(3, 4);
        double d = p1.distance2D(p2);
        assertEquals(5.0, d, 1e-9);
    }

    // Test distance2D when input is null
    @Test(expected = RuntimeException.class)
    public void testDistance2DNull() {
        Index2D p1 = new Index2D(0, 0);
        p1.distance2D(null);
    }

    // Test equals for two points with the same coordinates
    @Test
    public void testEqualsSameCoordinates() {
        Index2D p1 = new Index2D(2, 8);
        Index2D p2 = new Index2D(2, 8);
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));
    }

    // Test equals for two points with different coordinates
    @Test
    public void testEqualsDifferentCoordinates() {
        Index2D p1 = new Index2D(2, 8);
        Index2D p2 = new Index2D(2, 9);
        assertFalse(p1.equals(p2));
    }

    // Test equals with null and with an object of another type
    @Test
    public void testEqualsNullAndDifferentType() {
        Index2D p1 = new Index2D(1, 1);
        assertFalse(p1.equals(null));
        assertFalse(p1.equals("not pixel"));
    }

}
