package com.pagingsimulator.pagingsimulator.Controller.Utils;

import java.util.ArrayList;

public class DummyDataUtil {

    private ArrayList<Integer> PIDs;

    public DummyDataUtil(){
        PIDs = new ArrayList<>();
        fillDummyPIDs();
    }

    private void fillDummyPIDs(){
        PIDs.add(5);
        PIDs.add(3);
        PIDs.add(2);
        PIDs.add(1);
        PIDs.add(8);
    }

    public ArrayList<Integer> getPIDs() {
        return PIDs;
    }
}
