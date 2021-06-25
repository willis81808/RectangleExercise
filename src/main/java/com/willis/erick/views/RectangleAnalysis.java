package com.willis.erick.views;

import com.willis.erick.gui.VisualizerWindow;
import com.willis.erick.interfaces.IMenuItem;
import com.willis.erick.models.Rectangle;
import com.willis.erick.utils.ConsoleHelper;

import java.util.Scanner;

public class RectangleAnalysis implements IMenuItem {
    @Override
    public void onDisplay(Scanner scanner) {
        // get preview window dimensions
        int width = ConsoleHelper.getInt(scanner, "Screen Width (100-1000): ", "Please enter a valid integer value in the range 100-1000", 100, 1000);
        int height = ConsoleHelper.getInt(scanner, "Screen Height (100-1000): ", "Please enter a valid integer value in the range 100-1000", 100, 1000);

        // get rectangles
        System.out.println("Top-left corner");
        Rectangle rect1 = ConsoleHelper.getRect(scanner, width, height);
        System.out.println("Bottom-right corner");
        Rectangle rect2 = ConsoleHelper.getRect(scanner, width, height);

        // open visualizer window
        VisualizerWindow visualizerWindow = new VisualizerWindow(width, height);
        visualizerWindow.addRectangle(rect1);
        visualizerWindow.addRectangle(rect2);

        // pause console input while window is open
        ConsoleHelper.waitWhile(scanner, new ConsoleHelper.BooleanConditional() {
            @Override
            public boolean IsTrue() {
                return visualizerWindow.isOpen;
            }
        });
    }

    @Override
    public String getTitle() {
        return "Analyze a custom rectangle pair";
    }
}
