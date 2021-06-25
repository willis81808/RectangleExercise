package com.willis.erick.views;

import com.willis.erick.interfaces.IMenuItem;

import java.util.Scanner;

public class UnitTester implements IMenuItem {
    @Override
    public void OnDisplay(Scanner scanner) {
        //TODO: Implement menu for running tests
        System.out.println("TODO...");
    }

    @Override
    public String GetTitle() {
        return "Run unit tests";
    }
}
