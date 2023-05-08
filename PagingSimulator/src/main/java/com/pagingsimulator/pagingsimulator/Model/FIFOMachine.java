package com.pagingsimulator.pagingsimulator.Model;


import java.time.Instant;
import java.util.Date;

public class FIFOMachine extends Machine{
    public FIFOMachine(int totalMemory, int pageSize) {
        super(totalMemory, pageSize);
    }

    @Override
    int selectPageToVRAM() {
        int tiniestMarkIndex = -1;
        long tiniestMark = Long.MAX_VALUE;
        for (int i = 0; i < realMemory.size(); i++) {
            Page page = pages.get(realMemory.get(i));
            if(tiniestMark > page.getMark()){
                tiniestMark = page.getMark();
                tiniestMarkIndex = i;
            }
        }
        return tiniestMarkIndex;
    }

    @Override
    public long getNewMark(long simTime) {
        return simTime;
    }

    @Override
    public long getUsedMark(long currentMark, long simTime) {
        return currentMark;
    }
}
