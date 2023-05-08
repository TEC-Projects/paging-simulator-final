package com.pagingsimulator.pagingsimulator.Controller.Utils;

import java.util.ArrayList;

public class ProcessTemp {
    int pid;
    ArrayList<Integer> ptrs;

    public ProcessTemp(int pid) {
        this.pid = pid;
        ptrs =  new ArrayList<>();
    }

    public void addPtr(int ptr){
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
}
