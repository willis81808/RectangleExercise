package com.willis.erick.models;

import com.willis.erick.interfaces.IDrawable;

import java.awt.*;

public class Rectangle implements IDrawable {
    public int x1, y1, x2, y2;

    public Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = Math.min(x1, x2);
        this.y1 = Math.min(y1, y2);
        this.x2 = Math.max(x1, x2);
        this.y2 = Math.max(y1, y2);
    }

    public int width() {
        return x2 - x1;
    }

    public int height() {
        return y2 - y1;
    }

    public Point[] getCorners() {
        return new Point[] {
                new Point(x1, y1),
                new Point(x1, y2),
                new Point(x2, y2),
                new Point(x2, y1),
        };
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(this.x1, this.y1, this.x2 - this.x1, this.y2 - this.y1);
        g.setColor(Color.red);
        g.fillArc(this.x1 - 5, this.y1 - 5, 10, 10, 0, 360);
        g.fillArc(this.x2 - 5, this.y2 - 5, 10, 10, 0, 360);
        g.setColor(Color.white);
        g.drawString(String.format("(%d, %d)", this.x1, this.y1), this.x1, this.y1);
        g.drawString(String.format("(%d, %d)", this.x2, this.y2), this.x2, this.y2);
    }

    /**
     * Compare the rectangle corner points
     *
     * @param obj Object to compare
     * @return Boolean equality
     */
    @Override
    public boolean equals(Object obj) {
        try {
            Rectangle other = (Rectangle)obj;
            return other.x1 == this.x1 && other.y1 == this.y1 && other.x2 == this.x2 && other.y2 == this.y2;
        } catch (Exception e) {
            return false;
        }
    }
}
