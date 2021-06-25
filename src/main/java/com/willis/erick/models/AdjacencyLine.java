package com.willis.erick.models;

import com.willis.erick.interfaces.IDrawable;

import java.awt.*;

public class AdjacencyLine implements IDrawable {
    public enum AdjacencyType {
        SUB_LINE, PROPER, PARTIAL, UNKNOWN
    }

    public AdjacencyType type;
    public Line line;

    public AdjacencyLine(AdjacencyType type, Line line) {
        this.type = type;
        this.line = line;
    }

    @Override
    public void draw(Graphics g) {
        line.draw(g);
        g.setColor(Color.yellow);
        String typeString = type.toString();
        g.drawString(typeString, (line.p2.x - line.p1.x) / 2 + line.p1.x - g.getFontMetrics().stringWidth(typeString) / 2, (line.p2.y - line.p1.y) / 2 + line.p1.y);
    }

    /**
     * Compare line points and adjacency type
     *
     * @param obj Object to compare
     * @return Boolean equality
     */
    @Override
    public boolean equals(Object obj) {
        try {
            AdjacencyLine other = (AdjacencyLine)obj;
            return other.type == this.type && other.line.equals(this.line);
        } catch (Exception e) {
            return false;
        }
    }
}
