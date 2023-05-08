package com.pagingsimulator.pagingsimulator.Model;

public class MRUMachine extends Machine{
    public MRUMachine(int totalMemory, int pageSize) {
        super(totalMemory, pageSize);
    }

    @Override
    int selectPageToVRAM() {
        int biggestMarkIndex = -1;
        long biggesttMark = -1;
        for (int i = 0; i < realMemory.size(); i++) {
            Page page = pages.get(realMemory.get(i));
            if(biggesttMark < page.getMark()){
                biggesttMark = page.getMark();
                biggestMarkIndex = i;
            }
        }
        return biggestMarkIndex;
    }

    @Override
    public long getNewMark(long simTime) {
        return 0;
    }

    @Override
    public long getUsedMark(long currentMark, long simTime) {
        return simTime;
    }
}
