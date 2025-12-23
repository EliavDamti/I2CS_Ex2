package Ex2;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/**

 * Simple GUI and file utilities for Map2D.

 * Uses StdDraw to draw the map and text files to load and save maps.
 */
public class Ex2_GUI {

    private static final int CANVAS_SIZE = 600;

    /**
     * Draws the given map on a StdDraw window.
     * <p>
     * Each cell is drawn as a square colored by its value.
     *
     * @param map the map to draw, can be null.
     */
    public static void drawMap(Map2D map) {
        if (map == null) {
            return;
        }
        int w = map.getWidth();
        int h = map.getHeight();
        if (w <= 0 || h <= 0) {
            return;
        }
        StdDraw.setCanvasSize(CANVAS_SIZE, CANVAS_SIZE);
        StdDraw.setXscale(0, w);
        StdDraw.setYscale(0, h);
        StdDraw.clear();
        // draw squares so that the first line in the file is shown at the top
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int v = map.getPixel(x, y);
                Color c = valueToColor(v);
                StdDraw.setPenColor(c);

                int yy = h - 1 - y;
                StdDraw.filledSquare(x + 0.5, yy + 0.5, 0.5);
            }
        }


        StdDraw.setPenColor(Color.GRAY);
        for (int x = 0; x <= w; x++) {
            StdDraw.line(x, 0, x, h);
        }
        for (int y = 0; y <= h; y++) {
            StdDraw.line(0, y, w, y);
        }

        StdDraw.show();
    }

    /**
     * Converts an integer value to a drawing color.
     * <p>
     * 0 is white, 1 is black, bigger values are gray.
     *
     * @param v value of the cell.
     * @return color used to draw this value.
     */
    private static Color valueToColor(int v) {
        switch (v) {
            case 0:
                return Color.WHITE;
            case 1:
                return Color.BLACK;
            case 2:
                return Color.RED;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.BLUE;
            default:
                // other values set to grey,
                int g = 200;
                return new Color(g, g, g);
        }
    }


    /**
     * Loads a map from a text file.
     * <p>
     * Each line is a row and values are separated by spaces.
     *
     * @param mapFileName name of the map file.
     * @return a new Map2D instance, or null if loading failed.
     */
    public static Map2D loadMap(String mapFileName) {
        if (mapFileName == null) {
            return null;
        }
        List<int[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(mapFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split("\s+");
                int[] row = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    row[i] = Integer.parseInt(parts[i]);
                }
                rows.add(row);
            }
        } catch (IOException e) {
            return null;
        }

        if (rows.isEmpty()) {
            return null;
        }

        int w = rows.get(0).length;
        for (int[] r : rows) {
            if (r.length != w) {
                return null;
            }
        }

        int h = rows.size();
        int[][] data = new int[w][h];
        for (int y = 0; y < h; y++) {
            int[] r = rows.get(y);
            for (int x = 0; x < w; x++) {
                data[x][y] = r[x];
            }
        }
        return new Map(data);
    }

    /**
     * Saves the given map to a text file.
     * <p>
     * The format is the same as in loadMap.
     *
     * @param map         the map to save, can be null.
     * @param mapFileName target file name.
     */
    public static void saveMap(Map2D map, String mapFileName) {
        if (map == null || mapFileName == null) {
            return;
        }
        int w = map.getWidth();
        int h = map.getHeight();
        try (PrintWriter pw = new PrintWriter(new FileWriter(mapFileName))) {
            for (int y = 0; y < h; y++) {
                StringBuilder sb = new StringBuilder();
                for (int x = 0; x < w; x++) {
                    if (x > 0) {
                        sb.append(" ");
                    }
                    sb.append(map.getPixel(x, y));
                }
                pw.println(sb.toString());
            }
        } catch (IOException e) {
// ignore write errors
        }
    }

    /**
     * Simple demo of the GUI.
     * <p>
     * Loads map.txt and draws it.
     *
     * @param a command line arguments, not used.
     */
    public static void main(String[] a) {
        String mapFile = "map.txt";
        Map2D map = loadMap(mapFile);
        if (map == null) {
            return;
        }
        drawMap(map);
        interactiveShortestPath(map);
    }

    /**
     * waits for two mouse clicks on the map
     * computes shortest path between them
     * colors the path and redraws the map
     */
    private static void interactiveShortestPath(Map2D map) {
        int obsColor = 1;
        int pathColor = 4;

        Pixel2D start = waitForClick(map);
        if (start == null) {
            return;
        }

        Pixel2D end = waitForClick(map);
        if (end == null) {
            return;
        }

        Pixel2D[] path = map.shortestPath(start, end, obsColor, false);
        if (path != null) {
            for (Pixel2D p : path) {
                map.setPixel(p, pathColor);
            }
            drawMap(map);
        }
    }

    /**
     * waits for a single mouse click on the canvas
     * converts the click position to a Pixel2D in map coordinates
     */
    private static Pixel2D waitForClick(Map2D map) {
        int w = map.getWidth();
        int h = map.getHeight();

        while (!StdDraw.isMousePressed()) {
            StdDraw.pause(20);
        }
        double x = StdDraw.mouseX();
        double y = StdDraw.mouseY();

        while (StdDraw.isMousePressed()) {
            StdDraw.pause(20);
        }

        int ix = (int) x;
        int iyScreen = (int) y;
        if (ix < 0 || ix >= w || iyScreen < 0 || iyScreen >= h) {
            return null;
        }

        int iyMap = h - 1 - iyScreen;
        return new Index2D(ix, iyMap);
    }




}
