package Ex2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

/**

 Intro2CS, 2026A

 Tests for Map: init, equals, drawing, fill, shortestPath and allDistance.
 */
class MapTest {
    private int[][] _map_3_3 = {{0, 1, 0}, {1, 0, 1}, {0, 1, 0}};
    private Map2D _m0, _m1, _m3_3;

    @BeforeEach
    public void setuo() {
        _m0 = new Map(_map_3_3);
        _m1 = new Map(_map_3_3);
        _m3_3 = new Map(_map_3_3);
    }

    // Test init with a big array and simple fill call
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void init() {
        int[][] bigarr = new int[500][500];
        _m1.init(bigarr);
        assertEquals(bigarr.length, _m1.getWidth());
        assertEquals(bigarr[0].length, _m1.getHeight());
        Pixel2D p1 = new Index2D(3, 2);
        _m1.fill(p1, 1, true);
    }

    // Test that init from the same array makes two equal maps
    @Test
    void testInit() {
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0, _m1);
    }



      //test init by width, height and value
      //checks dimensions and that all cells are set to v

    @Test
    void testInitBySizeAndValue() {
        Map2D m = new Map(3, 4, 7);

        assertEquals(3, m.getWidth());
        assertEquals(4, m.getHeight());

        for (int x = 0; x < m.getWidth(); x++) {
            for (int y = 0; y < m.getHeight(); y++) {
                assertEquals(7, m.getPixel(x, y));
            }
        }
    }

    // Test equals before and after init on the same data
    @Test
    void testEquals() {
        assertEquals(_m0, _m1);
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0, _m1);
    }

    // Test that drawRect paints exactly the rectangle area
    @Test
    void testDrawRect() {
        int[][] arr = new int[5][5];
        Map2D m = new Map(arr);
        Pixel2D p1 = new Index2D(1, 1);
        Pixel2D p2 = new Index2D(3, 3);
        int color = 7;
        m.drawRect(p1, p2, color);
        for (int x = 0; x < m.getWidth(); x++) {
            for (int y = 0; y < m.getHeight(); y++) {
                int v = m.getPixel(x, y);
                if (x >= 1 && x <= 3 && y >= 1 && y <= 3) {
                    assertEquals(color, v);
                } else {
                    assertEquals(0, v);
                }
            }
        }
    }

    // Test that drawLine paints a horizontal line between two points
    @Test
    void testDrawLineHorizontal() {
        int[][] arr = new int[5][5];
        Map2D m = new Map(arr);
        Pixel2D p1 = new Index2D(1, 2);
        Pixel2D p2 = new Index2D(3, 2);
        int color = 4;
        m.drawLine(p1, p2, color);
        for (int x = 0; x < m.getWidth(); x++) {
            for (int y = 0; y < m.getHeight(); y++) {
                int v = m.getPixel(x, y);
                if (y == 2 && x >= 1 && x <= 3) {
                    assertEquals(color, v);
                } else {
                    assertEquals(0, v);
                }
            }
        }
    }

    // Test that drawCircle paints at least the center pixel
    @Test
    void testDrawCircleSimple() {
        int[][] arr = new int[7][7];
        Map2D m = new Map(arr);
        Pixel2D center = new Index2D(3, 3);
        int color = 9;
        m.drawCircle(center, 1.5, color);
        assertEquals(color, m.getPixel(3, 3));
    }

    // Test that rescale doubles the size and keeps top left value
    @Test
    void testRescale() {
        int[][] arr = {
                {1, 2},
                {3, 4}
        };
        Map2D m = new Map(arr);
        m.rescale(2.0, 2.0);
        assertEquals(4, m.getWidth());
        assertEquals(4, m.getHeight());
        assertEquals(1, m.getPixel(0, 0));
    }

    // Test allDistance without obstacles from the center of a small map
    @Test
    void testAllDistanceNoObstacles() {
        int[][] arr = new int[3][3];
        Map2D m = new Map(arr);
        Pixel2D start = new Index2D(1, 1);
        int obsColor = 1;
        Map2D distMap = m.allDistance(start, obsColor, false);
        assertNotNull(distMap);
        assertEquals(0, distMap.getPixel(1, 1));
        assertEquals(1, distMap.getPixel(1, 0));
        assertEquals(2, distMap.getPixel(0, 0));
    }

    // Test shortestPath on a simple map with one obstacle
    @Test
    void testShortestPathSimple() {
        int[][] arr = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        Map2D m = new Map(arr);
        Pixel2D s = new Index2D(0, 0);
        Pixel2D t = new Index2D(2, 2);
        int obsColor = 1;
        Pixel2D[] path = m.shortestPath(s, t, obsColor, false);
        assertNotNull(path);
        assertTrue(path.length <= 5);
        assertEquals(0, path[0].getX());
        assertEquals(0, path[0].getY());
        assertEquals(2, path[path.length - 1].getX());
        assertEquals(2, path[path.length - 1].getY());

    }
    // test setpixel and getpixel on inside and outside cells
    @Test
    void testSetPixelAndGetPixel() {
        int[][] arr = new int[3][3];
        Map2D m = new Map(arr);

        m.setPixel(1, 1, 5);
        assertEquals(5, m.getPixel(1, 1));
        assertEquals(0, m.getPixel(0, 0));

        m.setPixel(-1, 0, 7);
        assertEquals(0, m.getPixel(0, 0));
    }

    // test isinside for valid and invalid pixels
    @Test
    void testIsInside() {
        int[][] arr = new int[2][2];
        Map2D m = new Map(arr);

        assertTrue(m.isInside(new Index2D(0, 0)));
        assertTrue(m.isInside(new Index2D(1, 1)));

        assertFalse(m.isInside(new Index2D(-1, 0)));
        assertFalse(m.isInside(new Index2D(2, 1)));
        assertFalse(m.isInside(new Index2D(1, 2)));
    }

    // test samedimensions for maps with same and different sizes
    @Test
    void testSameDimensions() {
        int[][] a1 = new int[3][4];
        int[][] a2 = new int[3][4];
        int[][] a3 = new int[2][2];

        Map2D m1 = new Map(a1);
        Map2D m2 = new Map(a2);
        Map2D m3 = new Map(a3);

        assertTrue(m1.sameDimensions(m2));
        assertFalse(m1.sameDimensions(m3));
    }

    // test addmap2d on maps with same size
    @Test
    void testAddMap2DSameSize() {
        int[][] a1 = {
                {1, 1},
                {1, 1}
        };
        int[][] a2 = {
                {2, 2},
                {2, 2}
        };

        Map2D m1 = new Map(a1);
        Map2D m2 = new Map(a2);

        m1.addMap2D(m2);

        assertEquals(3, m1.getPixel(0, 0));
        assertEquals(3, m1.getPixel(1, 0));
        assertEquals(3, m1.getPixel(0, 1));
        assertEquals(3, m1.getPixel(1, 1));
    }

    // test addmap2d does nothing when sizes are different
    @Test
    void testAddMap2DDifferentSize() {
        int[][] a1 = new int[2][2];
        int[][] a2 = new int[3][3];

        Map2D m1 = new Map(a1);
        Map2D m2 = new Map(a2);

        m1.setPixel(0, 0, 5);
        m1.addMap2D(m2);

        assertEquals(5, m1.getPixel(0, 0));
        assertEquals(0, m1.getPixel(1, 1));
        assertEquals(2, m1.getWidth());
        assertEquals(2, m1.getHeight());
    }

    // test fill: start pixel is painted and count is positive
    @Test
    void testFillSimple() {
        int[][] arr = {
                {0, 0, 1},
                {0, 1, 1},
                {0, 0, 0}
        };
        Map2D m = new Map(arr);
        Pixel2D start = new Index2D(0, 0);
        int newColor = 5;

        int count = m.fill(start, newColor, false);

        assertTrue(count >= 1);
        assertEquals(newColor, m.getPixel(start));
        assertTrue(count <= m.getWidth() * m.getHeight());
    }

    // test setPixel(Pixel2D, int) and getPixel(Pixel2D)
    @Test
    void testSetPixelWithPixel2D() {
        int[][] arr = new int[3][3];
        Map2D m = new Map(arr);
        Pixel2D p = new Index2D(1, 2);

        m.setPixel(p, 7);

        assertEquals(7, m.getPixel(p));
        assertEquals(0, m.getPixel(0, 0));
    }

    // test fill on a simple full map
    @Test
    void testFillSimpleAllMap() {
        int[][] arr = new int[3][3];
        Map2D m = new Map(arr);
        Pixel2D start = new Index2D(1, 1);

        int count = m.fill(start, 5, false);

        assertEquals(9, count);
        for (int x = 0; x < m.getWidth(); x++) {
            for (int y = 0; y < m.getHeight(); y++) {
                assertEquals(5, m.getPixel(x, y));
            }
        }
    }




    // test setpixel and getpixel using a pixel2d object
    @Test
    void testSetAndGetPixelByObject() {
        Map2D m = new Map(3, 3, 0);
        Pixel2D p = new Index2D(1, 2);

        m.setPixel(p, 7);
        assertEquals(7, m.getPixel(p));
    }


    // test mul multiplies all cells by scalar 2.0
    @Test
    void testMul() {
        Map2D m = new Map(2); // 2x2 map, init to 0

        m.setPixel(0, 0, 1);
        m.setPixel(1, 0, 2);
        m.setPixel(0, 1, 3);
        m.setPixel(1, 1, 4);

        m.mul(2.0);

        assertEquals(2, m.getPixel(0, 0));
        assertEquals(4, m.getPixel(1, 0));
        assertEquals(6, m.getPixel(0, 1));
        assertEquals(8, m.getPixel(1, 1));
    }



    // test getmap returns a deep copy and not the inner array
    @Test
    void testGetMapDeepCopy() {
        int[][] arr = {
                {1, 2},
                {3, 4}
        };
        Map2D m = new Map(arr);

        int[][] copy = m.getMap();
        copy[0][0] = 99;

        assertEquals(1, m.getPixel(0, 0));
    }

}