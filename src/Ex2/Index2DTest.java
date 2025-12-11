package Ex2;
import org.junit.Test;
import static org.junit.Assert.*;

public class Index2DTest
{
    @Test
    public void testConstructorAndGetters() {
        Index2D p = new Index2D(3, 5);
        assertEquals(3, p.getX());
        assertEquals(5, p.getY());
    }

    @Test
    public void testCopyConstructor() {
        Pixel2D src = new Index2D(7, 9);
        Index2D copy = new Index2D(src);
        assertEquals(7, copy.getX());
        assertEquals(9, copy.getY());
        assertNotSame(src, copy);
    }

    @Test
    public void testDistance2D() {
        Index2D p1 = new Index2D(0, 0);
        Index2D p2 = new Index2D(3, 4);
        double d = p1.distance2D(p2);
        assertEquals(5.0, d, 1e-9);
    }

    @Test(expected = RuntimeException.class)
    public void testDistance2DNull() {
        Index2D p1 = new Index2D(0, 0);
        p1.distance2D(null);
    }

    @Test
    public void testEqualsSameCoordinates() {
        Index2D p1 = new Index2D(2, 8);
        Index2D p2 = new Index2D(2, 8);
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));
    }

    @Test
    public void testEqualsDifferentCoordinates() {
        Index2D p1 = new Index2D(2, 8);
        Index2D p2 = new Index2D(2, 9);
        assertFalse(p1.equals(p2));
    }

    @Test
    public void testEqualsNullAndDifferentType() {
        Index2D p1 = new Index2D(1, 1);
        assertFalse(p1.equals(null));
        assertFalse(p1.equals("not pixel"));
    }

    @Test
    public void testHashCodeConsistentWithEquals() {
        Index2D p1 = new Index2D(4, 6);
        Index2D p2 = new Index2D(4, 6);
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }


}
