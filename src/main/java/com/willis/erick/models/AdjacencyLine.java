package com.willis.erick.models;

import com.willis.erick.interfaces.IDrawable;

import java.awt.*;

public class AdjacencyLine implements IDrawable {
    public enum AdjacencyType {
        SUB_LINE, PROPER, PARTIAL, UNKNOWN
    }

    public AdjacencyType type;
    public Line line;

    public AdjacencyLine(Line line) {
        this(AdjacencyType.UNKNOWN, line);
    }
    public AdjacencyLine(AdjacencyType type, Line line) {
        this.type = type;
        this.line = line;
    }

    @Override
    public void Draw(Graphics g) {
        line.Draw(g);
        g.setColor(Color.yellow);
        String typeString = type.toString();
        g.drawString(typeString, (line.p2.x - line.p1.x) / 2 + line.p1.x - g.getFontMetrics().stringWidth(typeString) / 2, (line.p2.y - line.p1.y) / 2 + line.p1.y);
    }
}
