package com.willis.erick.models;

import com.willis.erick.interfaces.IDrawable;

import java.awt.*;

public class Rectangle implements IDrawable {
    public int x1, y1, x2, y2;

    public Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void Draw(Graphics g) {
        g.setColor(Color.white);
        g.drawRect(this.x1, this.y1, this.x2 - this.x1, this.y2 - this.y1);
        g.setColor(Color.red);
        g.fillArc(this.x1 - 5, this.y1 - 5, 10, 10, 0, 360);
        g.fillArc(this.x2 - 5, this.y2 - 5, 10, 10, 0, 360);
        g.setColor(Color.white);
        g.drawString(String.format("(%d, %d)", this.x1, this.y1), this.x1, this.y1);
        g.drawString(String.format("(%d, %d)", this.x2, this.y2), this.x2, this.y2);
    }

    public int Width() {
        return x2 - x1;
    }

    public int Height() {
        return y2 - y1;
    }

    public Point[] GetCorners() {
        return new Point[] {
                new Point(x1, y1),
                new Point(x1, y2),
                new Point(x2, y2),
                new Point(x2, y1),
        };
    }
}
