package com.willis.erick.utils;

import com.willis.erick.models.AdjacencyLine;
import com.willis.erick.models.Line;
import com.willis.erick.models.Point;
import com.willis.erick.models.Rectangle;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Geometry {
    public static Point LineIntersections(Point pStart1, Point pEnd1, Point pStart2, Point pEnd2) {
        // calculate lengths along X and Y axis
        int dx1 = pStart1.x - pEnd1.x;
        int dy1 = pEnd1.y - pStart1.y;
        int dx2 = pStart2.x - pEnd2.x;
        int dy2 = pEnd2.y - pStart2.y;

        // check if parallel with cross-product
        int delta = dy1*dx2 - dy2*dx1;
        if (delta == 0) {
            return null;
        }

        int c2 = dy2 * pStart2.x + dx2 * pStart2.y;
        int c1 = dy1 * pStart1.x + dx1 * pStart1.y;

        // calculate intersection point along ray
        int x = (dx2 * c1 - dx1 * c2) / delta;
        int y = (dy1 * c2 - dy2 * c1) / delta;

        // filter out points that don't lie within the line segment bounds
        if (dy1 == 0 && (x < pStart1.x || x > pEnd1.x)) return null;
        if (dx1 == 0 && (y < pStart1.y || y > pEnd1.y)) return null;
        if (dy2 == 0 && (x < pStart2.x || x > pEnd2.x)) return null;
        if (dx2 == 0 && (y < pStart2.y || y > pEnd2.y)) return null;

        return new Point(x, y, Color.green);
    }

    public static Point[] RectangleLineIntersections(Rectangle r, Point p1, Point p2) {
        ArrayList<Point> points = new ArrayList<>();

        Point[] corners = r.GetCorners();
        points.add(LineIntersections(p1, p2, corners[0], corners[1]));
        points.add(LineIntersections(p1, p2, corners[1], corners[2]));
        points.add(LineIntersections(p1, p2, corners[3], corners[2]));
        points.add(LineIntersections(p1, p2, corners[0], corners[3]));

        points.removeAll(Collections.singleton(null));
        return points.toArray(new Point[points.size()]);
    }
    public static Point[] RectangleIntersections(Rectangle r1, Rectangle r2) {
        ArrayList<Point> points = new ArrayList<>();

        Point[] corners = r2.GetCorners();
        points.addAll(Arrays.asList(RectangleLineIntersections(r1, corners[0], corners[1])));
        points.addAll(Arrays.asList(RectangleLineIntersections(r1, corners[1], corners[2])));
        points.addAll(Arrays.asList(RectangleLineIntersections(r1, corners[3], corners[2])));
        points.addAll(Arrays.asList(RectangleLineIntersections(r1, corners[0], corners[3])));

        // remove null entries and duplicates
        points.removeAll(Collections.singleton(null));
        for (int i = points.size() - 1; i >= 0; i--) {
            Point pi = points.get(i);
            for (int j = i - 1; j >= 0; j--) {
                Point pj = points.get(j);
                if (pi.x == pj.x && pi.y == pj.y) points.remove(i);
            }
        }

        return points.toArray(new Point[points.size()]);
    }
    public static Point[] OverlapPoints(Rectangle r1, Rectangle r2) {
        boolean adjacentX = false, adjacentY = false;

        // get points along the vertical
        int x1 = Math.max(r1.x1, r2.x1);
        int x2 = Math.min(r1.x2, r2.x2);

        // check for overlap
        if (x2 - x1 < 0) {
            return new Point[0];
        }

        // get points along the horizontal
        int y1 = Math.max(r1.y1, r2.y1);
        int y2 = Math.min(r1.y2, r2.y2);

        // check for overlap
        if (y2 - y1 < 0) {
            return new Point[0];
        }

        // check for adjacency
        adjacentX = x2 - x1 == 0;
        adjacentY = y2 - y1 == 0;

        // construct final points
        ArrayList<Point> results = new ArrayList<>();
        if (adjacentX) {
            results.add(new Point(x1, y1));
            results.add(new Point(x1, y2));
        } else if (adjacentY) {
            results.add(new Point(x1, y1));
            results.add(new Point(x2, y1));
        } else {
            results.add(new Point(x1, y1));
            results.add(new Point(x1, y2));
            results.add(new Point(x2, y1));
            results.add(new Point(x2, y2));
        }


        // return results
        return results.toArray(new Point[results.size()]);
    }

    private static AdjacencyLine.AdjacencyType CalculateAdjacencyType(int a, int b, int size1, int size2, int min, int max) {
        AdjacencyLine.AdjacencyType type = AdjacencyLine.AdjacencyType.UNKNOWN;
        if (b - a == size1 && b - a == size2)
            type = AdjacencyLine.AdjacencyType.PROPER;
        else if (b - a == size1 || b - a == size2)
            type = AdjacencyLine.AdjacencyType.SUB_LINE;
        else if ((a >= min && a < max) || (a > min && a <= max))
            type = AdjacencyLine.AdjacencyType.PARTIAL;
        return type;
    }
    public static AdjacencyLine[] AdjacencyLines(Rectangle r1, Rectangle r2) {
        ArrayList<AdjacencyLine> results = new ArrayList<>();

        // get points along the vertical
        int x1 = Math.max(r1.x1, r2.x1);
        int x2 = Math.min(r1.x2, r2.x2);

        // get points along the horizontal
        int y1 = Math.max(r1.y1, r2.y1);
        int y2 = Math.min(r1.y2, r2.y2);

        // calculate vertical line adjacency
        AdjacencyLine.AdjacencyType verticalType = CalculateAdjacencyType(y1, y2, r1.Height(), r2.Height(), r1.y1, r2.y2);
        // handle simple adjacency
        if (x2 - x1 == 0) {
            return new AdjacencyLine[] { new AdjacencyLine(verticalType, new Line(x1, y1, x1, y2)) };
        }
        // handle complex adjacency
        if (x2 - x1 == Math.min(r1.Width(), r2.Width())) {
            if (r1.x1 == r2.x1 || r1.x1 == r2.x2) {
                results.add(new AdjacencyLine(verticalType, new Line(r1.x1, y1, r1.x1, y2)));
            }
            if (r1.x2 == r2.x1 || r1.x2 == r2.x2) {
                results.add(new AdjacencyLine(verticalType, new Line(r1.x2, y1, r1.x2, y2)));
            }
        }

        // calculate horizontal line adjacency
        AdjacencyLine.AdjacencyType horizontalType = CalculateAdjacencyType(x1, x2, r1.Width(), r2.Width(), r1.x1, r2.x2);
        // handle simple adjacency
        if (y2 - y1 == 0) {
            return new AdjacencyLine[] { new AdjacencyLine(horizontalType, new Line(x1, y1, x2, y1)) };
        }
        // handle complex adjacency
        if (y2 - y1 == Math.min(r1.Height(), r2.Height())) {
            if (r1.y1 == r2.y1 || r1.y1 == r2.y2) {
                results.add(new AdjacencyLine(horizontalType, new Line(x1, r1.y1, x2, r1.y1)));
            }
            if (r1.y2 == r2.y1 || r1.y2 == r2.y2) {
                results.add(new AdjacencyLine(horizontalType, new Line(x1, r1.y2, x2, r1.y2)));
            }
        }

        return results.toArray(new AdjacencyLine[results.size()]);
    }

    public static boolean IsAdjacent(Rectangle r1, Rectangle r2) {
        boolean // does either rectangle share a Y value?
                sharedY = r2.y1 == r1.y1 || r2.y1 == r1.y2 || r2.y2 == r1.y1 || r2.y2 == r1.y2,
                // if so, ensure that the collinear sides share at least 1 point along the X axis
                verticallyAdjacent = sharedY && ((r2.x2 <= r1.x2 && r2.x2 >= r1.x1) || (r2.x1 >= r1.x1 && r2.x1 <= r1.x2) || (r1.x2 <= r2.x2 && r1.x2 >= r2.x1) || (r1.x1 >= r2.x1 && r1.x1 <= r2.x2)),

                // does either rectangle share a X value?
                sharedX = r2.x1 == r1.x1 || r2.x1 == r1.x2 || r2.x2 == r1.x1 || r2.x2 == r1.x2,
                // if so, ensure that the collinear sides share at least 1 point along the Y axis
                horizontallyAdjacent = sharedX && ((r2.y2 <= r1.y2 && r2.y2 >= r1.y1) || (r2.y1 >= r1.y1 && r2.y1 <= r1.y2) || (r1.y2 <= r2.y2 && r1.y2 >= r2.y1) || (r1.y1 >= r2.y1 && r1.y1 <= r2.y2));

        return horizontallyAdjacent || verticallyAdjacent;
    }
    public static boolean Contains(Rectangle r1, Rectangle r2) {
        return (r2.x1 >= r1.x1) && (r2.y1 >= r1.y1) && (r2.x2 <= r1.x2) && (r2.y2 <= r1.y2);
    }
}
