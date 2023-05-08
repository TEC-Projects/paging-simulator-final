package com.pagingsimulator.pagingsimulator.Model;

public class OperationTime {
    private int realTime;
    private int trashing;

    public OperationTime(int hits, int misses) {
        realTime = hits + misses*5;
        trashing = misses*5;
    }

    public int getRealTime() {
        return realTime;
    }

    public void setRealTime(int realTime) {
        this.realTime = realTime;
    }

    public int getTrashing() {
        return trashing;
    }

    public void setTrashing(int trashing) {
        this.trashing = trashing;
    }
}
