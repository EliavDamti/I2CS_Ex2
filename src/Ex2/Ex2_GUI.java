package Ex2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

/**

 Intro2CS_2026A

 Simple GUI and file utilities for Map2D.
 */
public class Ex2_GUI {

    private static final int CANVAS_SIZE = 600;

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
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int v = map.getPixel(x, y);
                Color c = valueToColor(v);
                StdDraw.setPenColor(c);
                StdDraw.filledSquare(x + 0.5, y + 0.5, 0.5);
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

    private static Color valueToColor(int v) {
        if (v <= 0) {
            return Color.WHITE;
        }
        if (v == 1) {
            return Color.BLACK;
        }
        int g = 255 - Math.min(200, v * 10);
        if (g < 0) {
            g = 0;
        }
        return new Color(g, g, g);
    }

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
        }
    }

    public static void main(String[] a) {
        String mapFile = "map.txt";
        Map2D map = loadMap(mapFile);
        drawMap(map);
    }
}





