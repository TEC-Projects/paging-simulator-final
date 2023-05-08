package com.pagingsimulator.pagingsimulator.Model;

import java.util.ArrayList;

public class Process {
    private int pid;
    private ArrayList<Integer> ptrs;

    public Process(int pid, int ptr) {
        this.pid = pid;
        ptrs = new ArrayList<>();
        ptrs.add(ptr);
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public ArrayList<Integer> getPtrs() {
        return ptrs;
    }

    public void setPtrs(ArrayList<Integer> ptrs) {
        this.ptrs = ptrs;
    }

    public void addPtr(int ptr){
        ptrs.add(ptr);
    }

    public void deletePtr(int ptr){
        ptrs.remove((Integer) ptr);
    }
}
