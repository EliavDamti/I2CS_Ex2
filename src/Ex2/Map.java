package Ex2;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents a 2D map (int[w][h]) as a "screen" or a raster matrix or maze over integers.
 * This is the main class needed to be implemented.
 *
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D, Serializable
{
    private int[][] _map;
    private int _width;
    private int _height;

    // edit this class below
    /**
     * Constructs a w*h 2D raster map with an init value v.
     * @param w
     * @param h
     * @param v
     */
    public Map(int w, int h, int v)
    {
        init(w, h, v);
    }
    /**
     * Constructs a square map (size*size).
     * @param size
     */
    public Map(int size)
    {
        this(size,size, 0);
    }

    /**
     * Constructs a map from a given 2D array.
     * @param data
     */
    public Map(int[][] data)
    {
        init(data);
    }
    @Override
    public void init(int w, int h, int v)
    {
        _width = w;
        _height = h;
        _map = new int[_width][_height];
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                _map[x][y] = v;
            }
        }
    }
    @Override
    public void init(int[][] arr)
    {
        if (arr == null || arr.length == 0) {
            _width = 0;
            _height = 0;
            _map = new int[0][0];
            return;
        }
        _width = arr.length;
        _height = arr[0].length;
        _map = new int[_width][_height];
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++)
            {
                _map[x][y] = arr[x][y];
            }
        }
    }

    @Override
    public int[][] getMap() {
        int[][] ans = new int[_width][_height];
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                ans[x][y] = _map[x][y];
            }
        }
        return ans;
    }

    @Override
    public int getWidth()
    {
        return _width;
    }
    @Override
    public int getHeight()
    {
        return _height;
    }
    @Override
    public int getPixel(int x, int y) {
        if (x < 0 || x >= _width || y < 0 || y >= _height)
        {
            return -1;
        }
        return _map[x][y];
    }
    @Override
    public int getPixel(Pixel2D p)
    {
        if (p == null)
        {
            return -1;
        }
        return getPixel(p.getX(), p.getY());
    }
    @Override
    public void setPixel(int x, int y, int v)
    {
        if (x < 0 || x >= _width || y < 0 || y >= _height) {
            return;
        }
        _map[x][y] = v;
    }
    @Override
    public void setPixel(Pixel2D p, int v)
    {
        if (p == null) {
            return;
        }
        setPixel(p.getX(), p.getY(), v);
    }

    @Override
    public boolean isInside(Pixel2D p)
    {
        if (p == null) {
            return false;
        }
        int x = p.getX();
        int y = p.getY();
        return x >= 0 && x < _width && y >= 0 && y < _height;
    }

    @Override
    public boolean sameDimensions(Map2D p)
    {
        if (p == null) {
            return false;
        }
        return this.getWidth() == p.getWidth() && this.getHeight() == p.getHeight();
    }

    @Override
    public void addMap2D(Map2D p)
    {
        if (p == null || !sameDimensions(p)) {
            return;
        }
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                int v = this._map[x][y] + p.getMap()[x][y];
                this._map[x][y] = v;
            }
        }
    }

    @Override
    public void mul(double scalar)
    {
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                _map[x][y] = (int) (_map[x][y] * scalar);
            }
        }
    }

    @Override
    public void rescale(double sx, double sy) {

    }

    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {

    }

    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {

    }

    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {

    }

    @Override
    public boolean equals(Object ob)
    {
        if (this == ob) {
            return true;
        }
        if (ob == null || getClass() != ob.getClass()) {
            return false;
        }
        Map other = (Map) ob;
        if (!sameDimensions(other)) {
            return false;
        }
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                if (this._map[x][y] != other._map[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    /**
     * Fills this map with the new color (new_v) starting from p.
     * https://en.wikipedia.org/wiki/Flood_fill
     */
    public int fill(Pixel2D xy, int new_v,  boolean cyclic)
    {
        if (xy == null) {
            return 0;
        }
        int x0 = xy.getX();
        int y0 = xy.getY();
        if (x0 < 0 || x0 >= _width || y0 < 0 || y0 >= _height) {
            return 0;
        }
        int old_v = _map[x0][y0];
        if (old_v == new_v) {
            return 0;
        }

        boolean[][] visited = new boolean[_width][_height];
        Queue<Index2D> q = new LinkedList<>();
        q.add(new Index2D(x0, y0));
        visited[x0][y0] = true;
        _map[x0][y0] = new_v;
        int count = 1;

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            Index2D p = q.remove();
            int cx = p.getX();
            int cy = p.getY();

            for (int k = 0; k < 4; k++) {
                int nx = cx + dx[k];
                int ny = cy + dy[k];

                if (cyclic) {
                    if (nx < 0) nx = _width - 1;
                    if (nx >= _width) nx = 0;
                    if (ny < 0) ny = _height - 1;
                    if (ny >= _height) ny = 0;
                } else {
                    if (nx < 0 || nx >= _width || ny < 0 || ny >= _height) {
                        continue;
                    }
                }

                if (!visited[nx][ny] && _map[nx][ny] == old_v) {
                    visited[nx][ny] = true;
                    _map[nx][ny] = new_v;
                    q.add(new Index2D(nx, ny));
                    count++;
                }
            }
        }
        return count;

    }

    @Override
    /**
     * BFS like shortest the computation based on iterative raster implementation of BFS, see:
     * https://en.wikipedia.org/wiki/Breadth-first_search
     */
    public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
        Pixel2D[] ans = null;  // the result.

        return ans;
    }
    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        Map2D ans = null;  // the result.

        return ans;
    }
    ////////////////////// Private Methods ///////////////////////

}
