package com.willis.erick.models;

import com.willis.erick.interfaces.IDrawable;

import java.awt.*;
import java.awt.geom.Line2D;

public class Line implements IDrawable {
    public Point p1, p2;

    private Color color = Color.white;
    private int thickness;

    public Line(int x1, int y1, int x2, int y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }
    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Line SetColor(Color color) {
        this.color = color;
        return this;
    }
    public Line SetThickness(int thickness) {
        this.thickness = thickness;
        return this;
    }

    public int Length() {
        int dx = p1.x - p2.x;
        int dy = p1.y - p2.y;
        return (int)Math.sqrt(dx * dx + dy * dy);
    }
    public int SquareLength() {
        int dx = p1.x - p2.x;
        int dy = p1.y - p2.y;
        return dx * dx + dy * dy;
    }

    @Override
    public void Draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        Stroke defaultStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(4));
        g2.setColor(color);
        g2.draw(new Line2D.Float(p1.x, p1.y, p2.x, p2.y));
        g2.setStroke(defaultStroke);
    }

    @Override
    public boolean equals(Object obj) {
        try {
            Line other = (Line)obj;
            return other.p1.x == this.p1.x && other.p1.y == this.p1.y && other.p2.x == this.p2.x && other.p2.y == this.p2.y;
        } catch (Exception e) {
            return false;
        }
    }
}
