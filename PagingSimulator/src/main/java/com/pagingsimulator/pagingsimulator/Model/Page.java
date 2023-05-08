package com.pagingsimulator.pagingsimulator.Model;

public class Page {
    private int pageId;
    private int PID;
    private boolean loaded;
    private int logicalAddress;
    private int memoryAddress;
    private int diskAddress;
    private int loadedAt;
    private long mark;

    public Page(int pageId, int PID, int memoryAddress, int ptr, int loadedAt, long mark) {
        this.pageId = pageId;
        this.PID = PID;
        loaded = true;
        diskAddress = -1;
        logicalAddress = ptr;
        this.memoryAddress = memoryAddress;
        this.loadedAt = loadedAt;
        this.mark = mark;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public int getLogicalAddress() {
        return logicalAddress;
    }

    public void setLogicalAddress(int logicalAddress) {
        this.logicalAddress = logicalAddress;
    }

    public int getMemoryAddress() {
        return memoryAddress;
    }

    public void setMemoryAddress(int memoryAddress) {
        this.memoryAddress = memoryAddress;
    }

    public int getDiskAddress() {
        return diskAddress;
    }

    public void setDiskAddress(int diskAddress) {
        this.diskAddress = diskAddress;
    }

    public int getLoadedAt() {
        return loadedAt;
    }

    public void setLoadedAt(int loadedAt) {
        this.loadedAt = loadedAt;
    }

    public long getMark() {
        return mark;
    }

    public void setMark(long mark) {
        this.mark = mark;
    }

    public void sendPageToVirtualMemory(){
        loaded = false;
        memoryAddress = -1;
        this.diskAddress = pageId;
        this.loadedAt = 0;
    }

    public void sendPageToRealMemory(int memoryAddress, int loadedAt){
        loaded = true;
        diskAddress = -1;
        this.loadedAt = loadedAt;
        this.memoryAddress = memoryAddress;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageId=" + pageId +
                ", PID=" + PID +
                ", loaded=" + loaded +
                ", diskAddress=" + diskAddress +
                ", memoryAddress=" + memoryAddress +
                ", mark=" + mark +
                '}';
    }
}
