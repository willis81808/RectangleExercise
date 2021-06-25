package com.willis.erick.views;

import com.willis.erick.gui.VisualizerWindow;
import com.willis.erick.interfaces.IMenuItem;
import com.willis.erick.models.Rectangle;
import com.willis.erick.utils.ConsoleHelper;

import java.util.Scanner;

public class ExampleAnalysis implements IMenuItem {

    @Override
    public void onDisplay(Scanner scanner) {
        System.out.println(
                "Available Presets:\n" +
                "1) 200x200 and 300x300\n" +
                "2) 300x100 and 100x300\n" +
                "3) Three rectangles");
        int selection = ConsoleHelper.getInt(scanner, "> ", "Please select a valid integer in the range 1-3", 1, 3);

        // open visualizer window
        VisualizerWindow visualizerWindow = new VisualizerWindow(700, 700);
        switch (selection) {
            case 1:
                visualizerWindow.addRectangle(new Rectangle(200, 200, 400, 400));
                visualizerWindow.addRectangle(new Rectangle(300, 300, 600, 600));
                break;
            case 2:
                visualizerWindow.addRectangle(new Rectangle(200, 300, 500, 400));
                visualizerWindow.addRectangle(new Rectangle(300, 200, 400, 500));
                break;
            case 3:
                visualizerWindow.addRectangle(new Rectangle(200, 200, 400, 400));
                visualizerWindow.addRectangle(new Rectangle(300, 300, 600, 600));
                visualizerWindow.addRectangle(new Rectangle(400, 400, 600, 600));
                break;
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
        return "Example rectangle analysis";
    }
}
