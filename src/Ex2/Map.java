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


    /**
     * Creates a new map with size w by h.
     * All cells are set to the value v.
     * @param w map width in cells.
     * @param h map height in cells.
     * @param v initial value for all cells.
     */
    public Map(int w, int h, int v)
    {
        init(w, h, v);
    }

    /**
     * Constructs a square map (size*size).
     * @param size
     */

    /**
     * Creates a new square map with size by size cells.
     * All cells are initialized to zero.
     * @param size width and height of the map.
     */
    public Map(int size)
    {
        this(size,size, 0);
    }

    /**
     * Constructs a map from a given 2D array.
     * @param data
     */

    /**
     * Creates a new map from a 2D int array.

     * Uses init(int[][]) to copy and validate the data.

     * @param data source 2D array.
     */
    public Map(int[][] data)
    {
        init(data);
    }

    /**

     * Initializes this map to size w by h.

     * All cells are set to the value v.

     * @param w map width in cells.

     * @param h map height in cells.

     * @param v initial value for all cells.
     */

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

    /**

    * Initializes this map from a 2D int array.

    * Validates that the array is not null, not empty and not ragged.

    * Copies all values into the inner map.

    * @param arr source 2D int array.

    *  @throws RuntimeException if arr is null, empty or ragged.
     */

    @Override
    public void init(int[][] arr) {
        if (arr == null || arr.length == 0) {
            throw new RuntimeException("null or empty array");
        }
        int h = arr[0].length;
        if (h == 0) {
            throw new RuntimeException("empty rows");
        }
        for (int x = 0; x < arr.length; x++) {
            if (arr[x] == null || arr[x].length != h) {
                throw new RuntimeException("ragged array");
            }
        }
        _width = arr.length;
        _height = h;
        _map = new int[_width][_height];
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                _map[x][y] = arr[x][y];
            }
        }
    }

    /**

     * Returns a deep copy of the inner 2D map.

     * @return new 2D array with the same values as this map.
     */
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

    /**

     * Returns the width of this map in cells.

     * @return map width.
     */
    @Override
    public int getWidth()
    {
        return _width;
    }



    /**

     * Returns the height of this map in cells.

     * @return map height.
     */
    @Override
    public int getHeight()
    {
        return _height;
    }


    /**

    * Returns the value of the cell at (x, y).

    * If the coordinates are outside the map, returns -1.

    * @param x x coordinate.

    * @param y y coordinate.

    * @return value at (x, y) or -1 if outside.
     */
    @Override
    public int getPixel(int x, int y) {
        if (x < 0 || x >= _width || y < 0 || y >= _height)
        {
            return -1;
        }
        return _map[x][y];
    }

    /**

     * Returns the value of the cell at the given pixel.

     * If the pixel is null or outside the map, returns -1.

     * @param p pixel position.

     * @return value at p or -1 if outside.
     */
    @Override
    public int getPixel(Pixel2D p)
    {
        if (p == null)
        {
            return -1;
        }
        return getPixel(p.getX(), p.getY());
    }

    /**

    * Sets the value of the cell at (x, y) to v.

    * If the coordinates are outside the map, does nothing.

    * @param x x coordinate.

    * @param y y coordinate.

    * @param v new value for the cell.
     */
    @Override
    public void setPixel(int x, int y, int v)
    {
        if (x < 0 || x >= _width || y < 0 || y >= _height) {
            return;
        }
        _map[x][y] = v;
    }

    /**

    * Sets the value of the cell at the given pixel to v.

    * If the pixel is null or outside the map, does nothing.

    * @param p pixel position.

    * @param v new value for the cell.
     */
    @Override
    public void setPixel(Pixel2D p, int v)
    {
        if (p == null) {
            return;
        }
        setPixel(p.getX(), p.getY(), v);
    }

    /**

    * Checks if the given pixel is inside this map.

    * @param p pixel position.

    * @return true if p is inside the map, false otherwise.
     */
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

    /**

    * Checks if this map and the other map have the same dimensions.

    * @param p other map.

    * @return true if both maps have the same width and height.
     */
    @Override
    public boolean sameDimensions(Map2D p)
    {
        if (p == null) {
            return false;
        }
        return this.getWidth() == p.getWidth() && this.getHeight() == p.getHeight();
    }

    /**

     * Adds another map to this map cell by cell.

     * If the other map is null or has different dimensions, does nothing.

     * @param p other map to add.
     */
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

    /**

    * Multiplies all cells in this map by a scalar.

    * The result in each cell is cast to int.

    * @param scalar value to multiply each cell by.
     */
    @Override
    public void mul(double scalar) {
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                _map[x][y] = (int) (_map[x][y] * scalar);
            }
        }
    }





    /**

    * Resizes this map by scale factors sx and sy.

    * Uses nearest neighbor sampling from the original map.

    * @param sx scale factor in x direction.

    * @param sy scale factor in y direction.
     */
    @Override
    public void rescale(double sx, double sy) {
        if (sx <= 0 || sy <= 0) {
            return;
        }
        int newW = (int) Math.round(_width * sx);
        int newH = (int) Math.round(_height * sy);
        if (newW <= 0 || newH <= 0) {
            return;
        }
        int[][] newMap = new int[newW][newH];

        for (int x = 0; x < newW; x++) {
            for (int y = 0; y < newH; y++) {
                double ox = x / sx;
                double oy = y / sy;
                int ix = (int) Math.floor(ox);
                int iy = (int) Math.floor(oy);
                if (ix < 0) ix = 0;
                if (iy < 0) iy = 0;
                if (ix >= _width) ix = _width - 1;
                if (iy >= _height) iy = _height - 1;
                newMap[x][y] = _map[ix][iy];
            }
        }

        _width = newW;
        _height = newH;
        _map = newMap;

    }

    /**

    * Draws a filled circle on this map.

    * All cells with distance from center less than or equal to rad

    * are set to the given color.

    * @param center center of the circle.

    * @param rad radius in cells.

    * @param color value to write in the circle area.
     */
    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {
        if (center == null || rad < 0) {
            return;
        }
        int cx = center.getX();
        int cy = center.getY();
        int rCeil = (int) Math.ceil(rad);
        for (int x = cx - rCeil; x <= cx + rCeil; x++) {
            for (int y = cy - rCeil; y <= cy + rCeil; y++) {
                if (x >= 0 && x < _width && y >= 0 && y < _height) {
                    double dx = x - cx;
                    double dy = y - cy;
                    double dist = Math.sqrt(dx * dx + dy * dy);
                    if (dist <= rad) {
                        _map[x][y] = color;
                    }
                }
            }
        }
    }

    /**

     * Draws a discrete line between two pixels on this map.

     * Uses linear interpolation and rounding to set the cells.

     * @param p1 start pixel.

     * @param p2 end pixel.

     * @param color value to write along the line.
     */
        @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {
        if (p1 == null || p2 == null) {
            return;
        }
        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();
        double dx = x2 - x1;
        double dy = y2 - y1;
        int steps = (int) Math.max(Math.abs(dx), Math.abs(dy));
        if (steps == 0) {
            if (x1 >= 0 && x1 < _width && y1 >= 0 && y1 < _height) {
                _map[x1][y1] = color;
            }
            return;
        }
        double xInc = dx / steps;
        double yInc = dy / steps;

        double x = x1;
        double y = y1;
        for (int i = 0; i <= steps; i++) {
            int xi = (int) Math.round(x);
            int yi = (int) Math.round(y);
            if (xi >= 0 && xi < _width && yi >= 0 && yi < _height) {
                _map[xi][yi] = color;
            }
            x += xInc;
            y += yInc;
        }
    }

    /**

    * Draws a filled axis aligned rectangle between two pixels.

    * All cells inside the rectangle are set to the given color.

    * @param p1 first corner of the rectangle.

    * @param p2 opposite corner of the rectangle.

    * @param color value to write inside the rectangle.
     */
        @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {
        if (p1 == null || p2 == null) {
            return;
        }
        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (x >= 0 && x < _width && y >= 0 && y < _height) {
                    _map[x][y] = color;
                }
            }
        }
    }




    /**

    * Compares this map to another object.

    * Returns true if the other object is a Map2D

    * with the same dimensions and all values equal.

     * @param ob object to compare with.

    * @return true if maps are equal in size and content.
     */
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


    /**

    * Fills the connected component of a starting pixel with a new value.

    * The component is defined by cells with the same original value,

    * connected by four neighbors.

    * @param xy start pixel.

    * @param new_v new value for the component.

    * @param cyclic true for cyclic borders, false for normal borders.

    * @return number of cells that were changed.
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


    /**

    * Computes the shortest path between two pixels using BFS.

    * The path avoids cells with the obstacle color.

    * @param p1 start pixel.

    * @param p2 target pixel.

    * @param obsColor obstacle color.

    * @param cyclic true for cyclic borders, false for normal borders.

    * @return array of pixels along the path, or null if no path exists.
     */
    public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
        if (p1 == null || p2 == null) {
            return null;
        }
        int sx = p1.getX();
        int sy = p1.getY();
        int tx = p2.getX();
        int ty = p2.getY();

        if (!isInside(p1) || !isInside(p2)) {
            return null;
        }
        if (_map[sx][sy] == obsColor || _map[tx][ty] == obsColor) {
            return null;
        }

        boolean[][] visited = new boolean[_width][_height];
        Index2D[][] parent = new Index2D[_width][_height];

        java.util.Queue<Index2D> q = new java.util.LinkedList<>();
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        visited[sx][sy] = true;
        q.add(new Index2D(sx, sy));

        while (!q.isEmpty()) {
            Index2D cur = q.remove();
            int cx = cur.getX();
            int cy = cur.getY();

            if (cx == tx && cy == ty) {
                break;
            }

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

                if (_map[nx][ny] == obsColor) {
                    continue;
                }
                if (!visited[nx][ny]) {
                    visited[nx][ny] = true;
                    parent[nx][ny] = cur;
                    q.add(new Index2D(nx, ny));
                }
            }
        }

        if (!visited[tx][ty]) {
            return null;
        }

        java.util.List<Pixel2D> path = new java.util.ArrayList<>();
        Index2D step = new Index2D(tx, ty);
        while (step != null) {
            path.add(step);
            step = parent[step.getX()][step.getY()];
        }
        java.util.Collections.reverse(path);
        return path.toArray(new Pixel2D[0]);
    }


    /**

    * Computes shortest path distance from a start pixel to all cells.

    * Distances are computed with BFS while avoiding obstacle cells.

    * @param start start pixel.

    * @param obsColor obstacle color.

    * @param cyclic true for cyclic borders, false for normal borders.

    * @return new map with distances, or -1 for unreachable cells.
     */

    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        if (start == null) {
            return null;
        }
        int sx = start.getX();
        int sy = start.getY();
        if (sx < 0 || sx >= _width || sy < 0 || sy >= _height) {
            return null;
        }
        if (_map[sx][sy] == obsColor) {
            return null;
        }
        int[][] dist = new int[_width][_height];
        for (int x = 0; x < _width; x++) {
            for (int y = 0; y < _height; y++) {
                dist[x][y] = -1;
            }
        }

        Queue<Index2D> q = new LinkedList<>();
        dist[sx][sy] = 0;
        q.add(new Index2D(sx, sy));

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            Index2D cur = q.remove();
            int cx = cur.getX();
            int cy = cur.getY();
            int cd = dist[cx][cy];

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

                if (_map[nx][ny] == obsColor) {
                    continue;
                }
                if (dist[nx][ny] == -1) {
                    dist[nx][ny] = cd + 1;
                    q.add(new Index2D(nx, ny));
                }
            }
        }

        return new Map(dist);
    }
        ////////////////////// Private Methods ///////////////////////

}
