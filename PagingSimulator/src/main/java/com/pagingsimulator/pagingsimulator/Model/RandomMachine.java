package com.pagingsimulator.pagingsimulator.Model;

import java.util.Random;

public class RandomMachine extends Machine{
    private Random random;

    public RandomMachine(int totalMemory, int pageSize, long randomSeed) {
        super(totalMemory, pageSize);
        random = new Random(randomSeed);
    }

    @Override
    int selectPageToVRAM() {
        return random.nextInt(0, realMemory.size());
    }

    @Override
    public long getNewMark(long simTime) {
        return 0;
    }

    @Override
    public long getUsedMark(long currentMark, long simTime) {
        return 0;
    }
}
