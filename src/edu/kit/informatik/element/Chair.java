package edu.kit.informatik.element;

import java.util.ArrayList;

public class Chair {
    private String name;
    private ArrayList<Staff> staffList = new ArrayList<>();

    public Chair(String name) {
        this.name = name;
    }
}
