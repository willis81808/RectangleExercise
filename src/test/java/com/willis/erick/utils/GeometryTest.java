package com.willis.erick.utils;

import com.willis.erick.models.AdjacencyLine;
import com.willis.erick.models.Line;
import com.willis.erick.models.Point;
import com.willis.erick.models.Rectangle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeometryTest {
    @Test
    public void getLineIntersections_NoIntersection_ReturnsNull() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(0, 1);
        Point p4 = new Point(1, 1);

        Point result = Geometry.getLineIntersection(p1, p2, p3, p4);

        Assertions.assertNull(result);
    }

    @Test
    public void getLineIntersections_ParallelOverlap_ReturnsNull() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(0, 0);
        Point p4 = new Point(1, 0);

        Point result = Geometry.getLineIntersection(p1, p2, p3, p4);

        Assertions.assertNull(result);
    }

    @Test
    public void getLineIntersections_Collinear_ReturnsNull() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 0);
        Point p3 = new Point(1, 0);
        Point p4 = new Point(2, 0);

        Point result = Geometry.getLineIntersection(p1, p2, p3, p4);

        Assertions.assertNull(result);
    }

    @Test
    public void getLineIntersections_OneIntersection_ReturnsPoint() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 0);
        Point p3 = new Point(1, -1);
        Point p4 = new Point(1, 1);

        Point expected = new Point(1, 0);
        Point result = Geometry.getLineIntersection(p1, p2, p3, p4);

        Assertions.assertEquals(expected, result);
    }

    @Test
    public void getRectangleLineIntersections_NoIntersection_ReturnsEmpty() {
        Rectangle r = new Rectangle(0, 0, 10, 10);
        Point p1 = new Point(11, 0);
        Point p2 = new Point(11, 10);

        Point[] expected = new Point[0];
        Point[] result = Geometry.getRectangleLineIntersections(r, p1, p2);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void getRectangleLineIntersections_OneIntersection_ReturnsOne() {
        Rectangle r = new Rectangle(-1, 0, 1, 1);
        Point p1 = new Point(0, -1);
        Point p2 = new Point(0, 0);

        Point[] expected = new Point[] { new Point(0, 0) };
        Point[] result = Geometry.getRectangleLineIntersections(r, p1, p2);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void getRectangleLineIntersections_TwoIntersections_ReturnsTwo() {
        Rectangle r = new Rectangle(-1, -1, 1, 1);
        Point p1 = new Point(0, -2);
        Point p2 = new Point(0, 2);

        Point[] expected = new Point[] { new Point(0, 1), new Point(0, -1) };
        Point[] result = Geometry.getRectangleLineIntersections(r, p1, p2);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void getRectangleIntersections_NoIntersection_ReturnsEmpty() {
        Rectangle r1 = new Rectangle(0, 0, 1, 1);
        Rectangle r2 = new Rectangle(2, 0, 3, 1);

        Point[] expected = new Point[0];
        Point[] result = Geometry.getRectangleIntersections(r1, r2);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void getRectangleIntersections_EdgeOnly_ReturnsTwo() {
        Rectangle r1 = new Rectangle(0, 0, 1, 1);
        Rectangle r2 = new Rectangle(1, 0, 2, 1);

        Point[] expected = new Point[] { new Point(1, 1), new Point(1, 0) };
        Point[] result = Geometry.getRectangleIntersections(r1, r2);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void getRectangleIntersections_CornerOverlap_ReturnsTwo() {
        Rectangle r1 = new Rectangle(0, 0, 2, 2);
        Rectangle r2 = new Rectangle(1, -1, 3, 1);

        Point[] expected = new Point[] { new Point(1, 0), new Point(2, 1) };
        Point[] result = Geometry.getRectangleIntersections(r1, r2);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void getRectangleIntersections_SameRectangle_ReturnsFour() {
        Rectangle r1 = new Rectangle(0, 0, 1, 1);
        Rectangle r2 = new Rectangle(0, 0, 1, 1);

        Point[] expected = new Point[] { new Point(0, 1), new Point(0, 0), new Point(1, 1), new Point(1, 0) };
        Point[] result = Geometry.getRectangleIntersections(r1, r2);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void getOverlapPoints_NoOverlap_ReturnsEmpty() {
        Rectangle r1 = new Rectangle(0, 0, 1, 1);
        Rectangle r2 = new Rectangle(2, 0, 3, 1);

        Point[] expected = new Point[0];
        Point[] result = Geometry.getOverlapPoints(r1, r2);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void getOverlapPoints_EdgeOnly_ReturnsTwo() {
        Rectangle r1 = new Rectangle(0, 0, 1, 1);
        Rectangle r2 = new Rectangle(1, 0, 2, 1);

        Point[] expected = new Point[] { new Point(1, 0), new Point(1, 1) };
        Point[] result = Geometry.getOverlapPoints(r1, r2);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void getOverlapPoints_CornerOverlap_ReturnsFour() {
        Rectangle r1 = new Rectangle(0, 0, 2, 2);
        Rectangle r2 = new Rectangle(1, -1, 3, 1);

        Point[] expected = new Point[] { new Point(1, 0), new Point(1, 1), new Point(2, 0), new Point(2, 1) };
        Point[] result = Geometry.getOverlapPoints(r1, r2);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void getAdjacencyLines_NotTouching_ReturnsEmpty() {
        Rectangle r1 = new Rectangle(0, 0, 1, 1);
        Rectangle r2 = new Rectangle(2, 0, 3, 1);

        AdjacencyLine[] expected = new AdjacencyLine[0];
        AdjacencyLine[] result = Geometry.getAdjacencyLines(r1, r2);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void getAdjacencyLines_Partial_ReturnsOne() {
        Rectangle r1 = new Rectangle(0, 0, 1, 2);
        Rectangle r2 = new Rectangle(1, -1, 2, 1);

        AdjacencyLine[] expected = new AdjacencyLine[] { new AdjacencyLine(AdjacencyLine.AdjacencyType.PARTIAL, new Line(1, 0, 1, 1)) };
        AdjacencyLine[] result = Geometry.getAdjacencyLines(r1, r2);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void getAdjacencyLines_Proper_ReturnsOne() {
        Rectangle r1 = new Rectangle(0, 0, 1, 1);
        Rectangle r2 = new Rectangle(1, 0, 2, 1);

        AdjacencyLine[] expected = new AdjacencyLine[] { new AdjacencyLine(AdjacencyLine.AdjacencyType.PROPER, new Line(1, 0, 1, 1)) };
        AdjacencyLine[] result = Geometry.getAdjacencyLines(r1, r2);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void getAdjacencyLines_SubLine_ReturnsOne() {
        Rectangle r1 = new Rectangle(0, -1, 1, 2);
        Rectangle r2 = new Rectangle(1, 0, 2, 1);

        AdjacencyLine[] expected = new AdjacencyLine[] { new AdjacencyLine(AdjacencyLine.AdjacencyType.SUB_LINE, new Line(1, 0, 1, 1)) };
        AdjacencyLine[] result = Geometry.getAdjacencyLines(r1, r2);

        Assertions.assertArrayEquals(expected, result);
    }

    @Test
    public void isAdjacent_NoOverlap_ReturnsFalse() {
        Rectangle r1 = new Rectangle(0, 0, 1, 1);
        Rectangle r2 = new Rectangle(2, 0, 3, 1);

        boolean result = Geometry.isAdjacent(r1, r2);

        Assertions.assertFalse(result);
    }

    @Test
    public void isAdjacent_OverlapNoAdjacency_ReturnsFalse() {
        Rectangle r1 = new Rectangle(0, 0, 2, 2);
        Rectangle r2 = new Rectangle(1, -1, 3, 1);

        boolean result = Geometry.isAdjacent(r1, r2);

        Assertions.assertFalse(result);
    }

    @Test
    public void isAdjacent_EdgeOnly_ReturnsTrue() {
        Rectangle r1 = new Rectangle(0, 0, 1, 1);
        Rectangle r2 = new Rectangle(1, 0, 2, 1);

        boolean result = Geometry.isAdjacent(r1, r2);

        Assertions.assertTrue(result);
    }

    @Test
    public void isContained_NoOverlap_ReturnsFalse() {
        Rectangle r1 = new Rectangle(0, 0, 1, 1);
        Rectangle r2 = new Rectangle(2, 0, 3, 1);

        boolean result = Geometry.isContained(r1, r2);

        Assertions.assertFalse(result);
    }

    @Test
    public void isContained_PartialIntersection_ReturnsFalse() {
        Rectangle r1 = new Rectangle(0, 0, 2, 2);
        Rectangle r2 = new Rectangle(1, -1, 3, 1);

        boolean result = Geometry.isContained(r1, r2);

        Assertions.assertFalse(result);
    }

    @Test
    public void isContained_FullContainment_ReturnsTrue() {
        Rectangle r1 = new Rectangle(0, 0, 3, 3);
        Rectangle r2 = new Rectangle(1, 1, 2, 2);

        boolean result = Geometry.isContained(r1, r2);

        Assertions.assertTrue(result);
    }
}