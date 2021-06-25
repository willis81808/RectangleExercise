package com.willis.erick;

import com.willis.erick.interfaces.IMenuItem;
import com.willis.erick.views.RectangleAnalysis;
import com.willis.erick.views.ExampleAnalysis;
import com.willis.erick.views.UnitTester;
import com.willis.erick.utils.ConsoleHelper;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean running = true;

    public static void main(String[] args) {
        MainMenu(new IMenuItem[]{
                 new ExampleAnalysis(),
                 new RectangleAnalysis(),
                 new UnitTester(),
                 new IMenuItem() {
                     @Override
                     public void OnDisplay(Scanner scanner) {
                        running = false;
                    }
                     @Override
                     public String GetTitle() {
                        return "Exit";
                    }
                 }
        });
    }

    private static void MainMenu(IMenuItem[] menuItems){
        while (running) {
            // get menu selection
            System.out.println("Main Menu");
            for (int i = 0; i < menuItems.length; i++) {
                System.out.println(i+1 + ") " + menuItems[i].GetTitle());
            }
            int result = ConsoleHelper.GetInt(scanner, "> ", "Please enter a valid integer value in the range 1-" + menuItems.length, 1, menuItems.length) - 1;

            // display selected menu
            if (result >= 0 && result < menuItems.length) {
                System.out.println();
                menuItems[result].OnDisplay(scanner);
            }

            System.out.println();
        }
    }
}
