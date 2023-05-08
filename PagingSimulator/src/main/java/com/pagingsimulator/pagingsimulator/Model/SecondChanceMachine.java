package com.pagingsimulator.pagingsimulator.Model;

import java.util.ArrayList;

public class SecondChanceMachine extends Machine {
    public SecondChanceMachine(int totalMemory, int pageSize) {
        super(totalMemory, pageSize);
    }

    @Override
    int selectPageToVRAM() {
        while(true) {
            for (int i = 0; i < realMemory.size(); i++) {
                if (pages.get(realMemory.get(i)).getMark() == 1) {
                    pages.get(realMemory.get(i)).setMark(0);
                } else {
                    pages.get(realMemory.get(i)).setMark(1);
                    return i;
                }
            }
        }
    }

    @Override
    public long getUsedMark(long currentMark, long simTime) {
        return 1;
    }

    @Override
    public long getNewMark(long simTime) {
        return 1;
    }
}
