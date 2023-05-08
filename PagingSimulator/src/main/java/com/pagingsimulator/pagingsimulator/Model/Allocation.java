package com.pagingsimulator.pagingsimulator.Model;

import java.util.ArrayList;

public class Allocation {
    private int ptr;
    private int pid;
    private ArrayList<Integer> pageIds;

    public Allocation(int ptr, int pid, ArrayList<Integer> pageIds) {
        this.ptr = ptr;
        this.pid = pid;
        this.pageIds = pageIds;
    }

    public int getPtr() {
        return ptr;
    }

    public void setPtr(int ptr) {
        this.ptr = ptr;
    }

    public ArrayList<Integer> getPageIds() {
        return pageIds;
    }

    public void setPageIds(ArrayList<Integer> pageIds) {
        this.pageIds = pageIds;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
