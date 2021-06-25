package com.willis.erick.utils;

import com.willis.erick.models.Rectangle;

import java.util.Scanner;

public class ConsoleHelper {
    // conditionals
    public static abstract class IntegerConditional {
        public abstract boolean IsValid(int value);
    }
    public static abstract class BooleanConditional {
        public abstract boolean IsTrue();
    }

    // input methods
    public static int getInt(Scanner scanner, String requestMessage, String errorMessage) {
        return getInt(scanner, requestMessage, errorMessage, new IntegerConditional() {
            @Override
            public boolean IsValid(int value) {
                return true;
            }
        });
    }
    public static int getInt(Scanner scanner, String requestMessage, String errorMessage, int minValue, int maxValue) {
        return getInt(scanner, requestMessage, errorMessage, new IntegerConditional() {
            @Override
            public boolean IsValid(int value) {
                return value >= minValue && value <= maxValue;
            }
        });
    }
    public static int getInt(Scanner scanner, String requestMessage, String errorMessage, IntegerConditional conditional) {
        boolean isValid = false;
        int result = 0;

        while (!isValid) {
            System.out.print(requestMessage);
            String input = scanner.nextLine();

            try {
                result = Integer.parseInt(input);

                if (!conditional.IsValid(result)) throw new Exception();

                isValid = true;
            } catch (Exception ex) {
                System.out.println(errorMessage + "\n");
            }
        }

        return result;
    }

    public static Rectangle getRect(Scanner scanner, int xMax, int yMax) {
        int x1 = ConsoleHelper.getInt(scanner, "x1: ", "Please enter a valid integer value in the range 0-" + xMax, 0, xMax);
        int y1 = ConsoleHelper.getInt(scanner, "y1: ", "Please enter a valid integer value in the range 0-" + yMax, 0, yMax);
        int x2 = ConsoleHelper.getInt(scanner, "x2: ", "Please enter a valid integer value in the range 0-" + xMax, 0, xMax);
        int y2 = ConsoleHelper.getInt(scanner, "y2: ", "Please enter a valid integer value in the range 0-" + yMax, 0, yMax);
        return new Rectangle(x1, y1, x2, y2);
    }

    // other
    public static void waitWhile(Scanner scanner, BooleanConditional conditional) {
        long count = 0;
        while (conditional.IsTrue()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
