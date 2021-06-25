package com.willis.erick.utils;

import com.willis.erick.models.AdjacencyLine;
import com.willis.erick.models.Line;
import com.willis.erick.models.Point;
import com.willis.erick.models.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Geometry {
    /**
     * Calculate the point of intersection (if any) of two lines
     *
     * @param pStart1   Start point of line 1
     * @param pEnd1     End point of line 1
     * @param pStart2   Start point of line 2
     * @param pEnd2     End point of line 2
     * @return          A point defining the intersection; null if there is no intersection
     */
    public static Point getLineIntersection(Point pStart1, Point pEnd1, Point pStart2, Point pEnd2) {
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

    /**
     * Calculate all intersection points (if any) between a rectangle and a line
     *
     * @param r     Rectangle to check
     * @param p1    Start point of line
     * @param p2    End point of line
     * @return      An array of Points defining all intersections; empty if there are none
     */
    public static Point[] getRectangleLineIntersections(Rectangle r, Point p1, Point p2) {
        ArrayList<Point> points = new ArrayList<>();

        Point[] corners = r.getCorners();
        points.add(getLineIntersection(p1, p2, corners[0], corners[1]));
        points.add(getLineIntersection(p1, p2, corners[1], corners[2]));
        points.add(getLineIntersection(p1, p2, corners[3], corners[2]));
        points.add(getLineIntersection(p1, p2, corners[0], corners[3]));

        points.removeAll(Collections.singleton(null));
        return points.toArray(new Point[points.size()]);
    }

    /**
     * Calculate all intersection points (if any) between a rectangle and another rectangle
     *
     * @param r1    First rectangle
     * @param r2    Second rectangle
     * @return      An array of Points defining all intersections; empty if there are none
     */
    public static Point[] getRectangleIntersections(Rectangle r1, Rectangle r2) {
        ArrayList<Point> points = new ArrayList<>();

        Point[] corners = r2.getCorners();
        points.addAll(Arrays.asList(getRectangleLineIntersections(r1, corners[0], corners[1])));
        points.addAll(Arrays.asList(getRectangleLineIntersections(r1, corners[1], corners[2])));
        points.addAll(Arrays.asList(getRectangleLineIntersections(r1, corners[3], corners[2])));
        points.addAll(Arrays.asList(getRectangleLineIntersections(r1, corners[0], corners[3])));

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

    /**
     * Calculate points defining the area of overlap between two rectangles
     *
     * @param r1    First rectangle
     * @param r2    Second rectangle
     * @return      An array of points defining the area of overlap; empty if there are none
     */
    public static Point[] getOverlapPoints(Rectangle r1, Rectangle r2) {
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

    /**
     * Calculate all lines of adjacency between two rectangles (if any), including adjacency type
     *
     * @param r1    First rectangle
     * @param r2    Second rectangle
     * @return      An array of lines, each representing a unique line of adjacency, and including the adjacency type
     *              (partial, sub-line, or proper). Empty if there is no adjacency.
     */
    public static AdjacencyLine[] getAdjacencyLines(Rectangle r1, Rectangle r2) {
        // return nothing if no adjacency exists
        if (!isAdjacent(r1, r2)) return new AdjacencyLine[0];

        ArrayList<AdjacencyLine> results = new ArrayList<>();

        // get points along the vertical
        int x1 = Math.max(r1.x1, r2.x1);
        int x2 = Math.min(r1.x2, r2.x2);

        // get points along the horizontal
        int y1 = Math.max(r1.y1, r2.y1);
        int y2 = Math.min(r1.y2, r2.y2);

        // calculate vertical line adjacency
        AdjacencyLine.AdjacencyType verticalType = calculateAdjacencyType(y1, y2, r1.height(), r2.height(), r1.y1, r2.y2);
        // handle simple adjacency
        if (x2 - x1 == 0) {
            return new AdjacencyLine[] { new AdjacencyLine(verticalType, new Line(x1, y1, x1, y2)) };
        }
        // handle complex adjacency
        if (x2 - x1 == Math.min(r1.width(), r2.width())) {
            if (r1.x1 == r2.x1 || r1.x1 == r2.x2) {
                results.add(new AdjacencyLine(verticalType, new Line(r1.x1, y1, r1.x1, y2)));
            }
            if (r1.x2 == r2.x1 || r1.x2 == r2.x2) {
                results.add(new AdjacencyLine(verticalType, new Line(r1.x2, y1, r1.x2, y2)));
            }
        }

        // calculate horizontal line adjacency
        AdjacencyLine.AdjacencyType horizontalType = calculateAdjacencyType(x1, x2, r1.width(), r2.width(), r1.x1, r2.x2);
        // handle simple adjacency
        if (y2 - y1 == 0) {
            return new AdjacencyLine[] { new AdjacencyLine(horizontalType, new Line(x1, y1, x2, y1)) };
        }
        // handle complex adjacency
        if (y2 - y1 == Math.min(r1.height(), r2.height())) {
            if (r1.y1 == r2.y1 || r1.y1 == r2.y2) {
                results.add(new AdjacencyLine(horizontalType, new Line(x1, r1.y1, x2, r1.y1)));
            }
            if (r1.y2 == r2.y1 || r1.y2 == r2.y2) {
                results.add(new AdjacencyLine(horizontalType, new Line(x1, r1.y2, x2, r1.y2)));
            }
        }

        return results.toArray(new AdjacencyLine[results.size()]);
    }
    private static AdjacencyLine.AdjacencyType calculateAdjacencyType(int a, int b, int size1, int size2, int min, int max) {
        AdjacencyLine.AdjacencyType type = AdjacencyLine.AdjacencyType.UNKNOWN;
        if (b - a == size1 && b - a == size2)
            type = AdjacencyLine.AdjacencyType.PROPER;
        else if (b - a == size1 || b - a == size2)
            type = AdjacencyLine.AdjacencyType.SUB_LINE;
        else if ((a >= min && a < max) || (a > min && a <= max))
            type = AdjacencyLine.AdjacencyType.PARTIAL;
        return type;
    }

    /**
     * Check if two rectangles have an adjacency relationship of any kind
     *
     * @param r1    First rectangle
     * @param r2    Second rectangle
     * @return      A boolean representing whether the given rectangles are adjacent or not
     */
    public static boolean isAdjacent(Rectangle r1, Rectangle r2) {
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

    /**
     * Check if either of two rectangles wholly contains the other
     *
     * @param r1    First rectangle
     * @param r2    Second rectangle
     * @return      A boolean representing whether either rectangles contains the other
     */
    public static boolean isContained(Rectangle r1, Rectangle r2) {
        return (r2.x1 >= r1.x1) && (r2.y1 >= r1.y1) && (r2.x2 <= r1.x2) && (r2.y2 <= r1.y2);
    }
}