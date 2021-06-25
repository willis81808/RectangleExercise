package com.willis.erick.gui;

import com.willis.erick.models.Point;
import com.willis.erick.models.Rectangle;
import com.willis.erick.utils.Geometry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class VisualizerWindow extends JFrame {
    public boolean isOpen = false;

    private VisualizerCanvas canvas;

    public VisualizerWindow(int width, int height) {
        isOpen = true;
        canvas = new VisualizerCanvas();

        this.setPreferredSize(new Dimension(width + 16, height + 39));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                OnClose();
            }
        });

        this.setContentPane(canvas);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Rectangle Tester");
        this.pack();
        this.setVisible(true);
    }

    private void OnClose() {
        isOpen = false;
    }

    public void AddRectangle(Rectangle item) {
        canvas.rectangles.add(item);
    }
}