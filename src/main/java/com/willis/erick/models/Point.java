package com.willis.erick.models;

import com.willis.erick.interfaces.IDrawable;

import java.awt.*;

public class Point implements IDrawable {
    public int x, y;
    private String text;
    private Color color;

    public Point(int x, int y) {
        this(x, y, Color.white);
    }
    public Point(int x, int y, Color color) {
        this(x, y, color, String.format("(%d, %d)", x, y));
    }
    public Point(int x, int y, Color color, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.color = color;
    }

    public void Draw(Graphics g) {
        g.setColor(color);
        g.drawArc(x - 5, y - 5, 10, 10, 0, 360);
        g.setColor(Color.white);
        g.drawString(text, x, y);
    }
}
