package com.willis.erick.views;

import com.willis.erick.gui.VisualizerWindow;
import com.willis.erick.interfaces.IMenuItem;
import com.willis.erick.models.Rectangle;
import com.willis.erick.utils.ConsoleHelper;

import java.util.ArrayList;
import java.util.Scanner;

public class CustomAnalysis implements IMenuItem {
    @Override
    public void onDisplay(Scanner scanner) {
        // get preview window dimensions
        int width = ConsoleHelper.getInt(scanner, "Screen Width (100-1000): ", "Please enter a valid integer value in the range 100-1000", 100, 1000);
        int height = ConsoleHelper.getInt(scanner, "Screen Height (100-1000): ", "Please enter a valid integer value in the range 100-1000", 100, 1000);

        ArrayList<Rectangle> rectangles = new ArrayList<>();

        boolean adding = true;

        // get rectangles
        while (adding) {
            System.out.println("\nAdd a rectangle");
            rectangles.add(ConsoleHelper.getRect(scanner, width, height));

            System.out.println("\nAdd another? (y/n)");
            adding = ConsoleHelper.getBoolean(scanner, "y", "n");
        }

        // open visualizer window
        VisualizerWindow visualizerWindow = new VisualizerWindow(width, height);
        for (Rectangle r : rectangles) {
            visualizerWindow.addRectangle(r);
        }

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
        return "Analyze a custom set of rectangles";
    }
}
