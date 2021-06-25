package com.willis.erick.gui;

import com.willis.erick.models.AdjacencyLine;
import com.willis.erick.models.Point;
import com.willis.erick.models.Rectangle;
import com.willis.erick.utils.Geometry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class VisualizerCanvas extends JPanel {
    private static final Color transparentRed = new Color(1f, 0f, 0f, .25f);
    private static final Color transparentGreen = new Color(0f, 1f, 0f, .25f);

    public ArrayList<Rectangle> rectangles = new ArrayList<>();

    public VisualizerCanvas() {
        VisualizerCanvas me = this;
        this.addMouseMotionListener(new MouseMotionListener() {
            int lastX, lastY;
            Rectangle rect = null;

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                for (int i = 0; i < rectangles.size(); i++) {
                    Rectangle element = rectangles.get(i);
                    if (x > element.x1 && y > element.y1 && x < element.x2 && y < element.y2) {
                        if (element == rect) break;
                        rect = element;
                    }
                }
                if (rect != null) {
                    rect.x1 += x - lastX;
                    rect.x2 += x - lastX;
                    rect.y1 += y - lastY;
                    rect.y2 += y - lastY;
                    me.repaint();
                }
                lastX = x;
                lastY = y;
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
            }
        });
        this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.setLayout(new GridLayout(0, 1));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        for (Rectangle item : rectangles) {
            item.draw(g);
        }
        if (rectangles.size() >= 2) {
            if (rectangles.size() > 2) {
                drawRectRelationship(g, rectangles.get(rectangles.size() - 1), rectangles.get(0));
            }
            for (int i = 1; i < rectangles.size(); i++) {
                drawRectRelationship(g, rectangles.get(i - 1), rectangles.get(i));
            }
        }
    }

    private void drawRectRelationship(Graphics g, Rectangle r1, Rectangle r2) {
        // visualize overlap
        Point[] overlapPoints = Geometry.getOverlapPoints(r1, r2);
        boolean containment = Geometry.isContained(r1, r2) || Geometry.isContained(r2, r1);
        if (overlapPoints.length == 4) {
            g.setColor(containment ? transparentGreen : transparentRed);
            g.fillRect(overlapPoints[0].x, overlapPoints[0].y, overlapPoints[3].x - overlapPoints[0].x, overlapPoints[3].y - overlapPoints[0].y);
        }

        // visualize adjacency
        for (AdjacencyLine l : Geometry.getAdjacencyLines(r1, r2)) {
            l.line.setColor(Color.magenta).setThickness(3);
            l.draw(g);
        }

        // visualize intersection points
        Point[] intersectingPoints = Geometry.getRectangleIntersections(r1, r2);
        for (Point p : intersectingPoints) {
            p.draw(g);
        }
    }
}
