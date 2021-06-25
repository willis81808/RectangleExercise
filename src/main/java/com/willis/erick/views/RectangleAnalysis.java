package com.willis.erick.views;

import com.willis.erick.gui.VisualizerWindow;
import com.willis.erick.interfaces.IMenuItem;
import com.willis.erick.models.Rectangle;
import com.willis.erick.utils.ConsoleHelper;

import java.util.Scanner;

public class RectangleAnalysis implements IMenuItem {
    @Override
    public void OnDisplay(Scanner scanner) {
        // get preview window dimensions
        int width = ConsoleHelper.GetInt(scanner, "Screen Width (100-1000): ", "Please enter a valid integer value in the range 100-1000", 100, 1000);
        int height = ConsoleHelper.GetInt(scanner, "Screen Height (100-1000): ", "Please enter a valid integer value in the range 100-1000", 100, 1000);

        // get rectangles
        System.out.println("Rectangle 1");
        Rectangle rect1 = ConsoleHelper.GetRect(scanner, width, height);
        System.out.println("Rectangle 2");
        Rectangle rect2 = ConsoleHelper.GetRect(scanner, width, height);

        // open visualizer window
        VisualizerWindow visualizerWindow = new VisualizerWindow(width, height);
        visualizerWindow.AddRectangle(rect1);
        visualizerWindow.AddRectangle(rect2);

        // pause console input while window is open
        ConsoleHelper.WaitWhile(scanner, new ConsoleHelper.BooleanConditional() {
            @Override
            public boolean IsTrue() {
                return visualizerWindow.isOpen;
            }
        });
    }

    @Override
    public String GetTitle() {
        return "Analyze a custom rectangle pair";
    }
}
