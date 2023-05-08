package com.pagingsimulator.pagingsimulator.Model;

import java.util.ArrayList;
import java.util.HashMap;

abstract class Machine {
    protected int pageCount;
    protected int ptrCount;
    protected int usedRam;
    protected int time;
    protected int trashing;
    protected HashMap<Integer, Allocation> memoryMap;
    protected ArrayList<Integer> realMemory;
    protected ArrayList<Integer> virtualMemory;
    protected HashMap<Integer, Page> pages;
    protected HashMap<Integer, Process> processes;
    protected int totalMemory;
    private int pageSize;

    private void addPtrToProcess(int pid, int ptr){
        Process process = processes.get(pid);
        if(process != null){
            process.addPtr(ptr);
        }else{
            processes.put(pid, new Process(pid, ptr));
        }
    }

    private void deletePtrFromProcess(int pid, int ptr){
        Process process = processes.get(pid);
        if(process != null){
            process.deletePtr(ptr);
        }else{
            System.out.println("Error: deleting ptr from unexisting process");
        }
    }

    private void addTime(int hits, int misses){
        time += hits + misses*5;
        trashing += misses*5;
    }

    public Machine(int totalMemory, int pageSize) {
        ptrCount = 0;
        pageCount = 0;
        usedRam = 0;
        memoryMap = new HashMap<>();
        realMemory = new ArrayList<>(totalMemory/pageSize);
        virtualMemory = new ArrayList<>();
        pages = new HashMap<>();
        processes = new HashMap<>();
        time = 0;
        trashing = 0;
        this.totalMemory = totalMemory;
        this.pageSize = pageSize;
        for (int i = 0; i < totalMemory/pageSize; i++) {
            realMemory.add(-1);
        }
    }

    abstract int selectPageToVRAM();

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getUsedRam() {
        return usedRam;
    }

    public void setUsedRam(int usedRam) {
        this.usedRam = usedRam;
    }

    public int getPtrCount() {
        return ptrCount;
    }

    public void setPtrCount(int ptrCount) {
        this.ptrCount = ptrCount;
    }

    public HashMap<Integer, Allocation> getMemoryMap() {
        return memoryMap;
    }

    public void setMemoryMap(HashMap<Integer, Allocation> memoryMap) {
        this.memoryMap = memoryMap;
    }

    public ArrayList<Integer> getRealMemory() {
        return realMemory;
    }

    public void setRealMemory(ArrayList<Integer> realMemory) {
        this.realMemory = realMemory;
    }

    public ArrayList<Integer> getVirtualMemory() {
        return virtualMemory;
    }

    public void setVirtualMemory(ArrayList<Integer> virtualMemory) {
        this.virtualMemory = virtualMemory;
    }

    public HashMap<Integer, Page> getPages() {
        return pages;
    }

    public void setPages(HashMap<Integer, Page> pages) {
        this.pages = pages;
    }

    public HashMap<Integer, Process> getProcesses() {
        return processes;
    }

    public void setProcesses(HashMap<Integer, Process> processes) {
        this.processes = processes;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTrashing() {
        return trashing;
    }

    public void setTrashing(int trashing) {
        this.trashing = trashing;
    }

    public abstract long getNewMark(long simTime);
    public abstract long getUsedMark(long currentMark, long simTime);

    public int getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(int totalMemory) {
        this.totalMemory = totalMemory;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void newAlloc(int pid, int allocSize) {

        double pagesCount = Math.ceil(allocSize * 1.0 / pageSize);

        ArrayList<Integer> createdPages = new ArrayList<>();

        for (int i = 0; i < pagesCount; i++) {
            if (pages.size() - virtualMemory.size() == realMemory.size()){
                int pageReplacedIndex = selectPageToVRAM();
                int pageReplacedId = realMemory.get(pageReplacedIndex);
                pages.get(pageReplacedId).sendPageToVirtualMemory();
                virtualMemory.add(pageReplacedId);
                createdPages.add(pageCount);
                realMemory.set(pageReplacedIndex, pageCount);
                pages.put(pageCount, new Page(pageCount++, pid, pageReplacedIndex, ptrCount, time, getNewMark(time)));
                addTime(0, 1);
            }else{
                for (int j = 0; j < realMemory.size(); j++) {
                    if(realMemory.get(j) == -1){
                        createdPages.add(pageCount);
                        realMemory.set(j, pageCount);
                        pages.put(pageCount, new Page(pageCount++, pid, j, ptrCount, time, getNewMark(time)));
                        break;
                    }
                }
                addTime(1, 0);
                usedRam++;
            }
        }
        memoryMap.put(ptrCount, new Allocation(ptrCount, pid , createdPages));
        addPtrToProcess(pid, ptrCount++);
    }

    public void use(int ptr) {
        Allocation allocation = memoryMap.get(ptr);

        for(int pageId : allocation.getPageIds()){
            if (!realMemory.contains(pageId)){
                int pageReplacedIndex = -1;

                if(pages.size() - virtualMemory.size() == realMemory.size()){
                    pageReplacedIndex = selectPageToVRAM();
                    int pageReplacedId = realMemory.get(pageReplacedIndex);
                    pages.get(pageReplacedId).sendPageToVirtualMemory();
                    virtualMemory.add(pageReplacedId);
                }else{
                    for (int i = 0; i < realMemory.size(); i++) {
                        if (realMemory.get(i) == -1){
                            pageReplacedIndex = i;
                            break;
                        }
                    }
                    if(pageReplacedIndex == -1){
                        System.out.println("Error: no page selected");
                    }
                }
                realMemory.set(pageReplacedIndex, virtualMemory.remove(virtualMemory.indexOf(pageId)));
                pages.get(realMemory.get(pageReplacedIndex)).sendPageToRealMemory(pageReplacedIndex, time);

                addTime(0, 1);
            }else{
                addTime(1, 0);
            };
            Page page = pages.get(pageId);
            page.setMark(getUsedMark(page.getMark(), time));
        }

    }

    public void delete(int ptr, boolean isKill){
        Allocation allocation = memoryMap.get(ptr);
        for (int pageId : allocation.getPageIds()){
            Page page = pages.get(pageId);
            if(page.isLoaded() && realMemory.contains((Integer) page.getPageId())){
                realMemory.set(page.getMemoryAddress(), -1);
                usedRam--;
            }else{
                virtualMemory.remove((Integer) pageId);
            }
            pages.remove(pageId);
        }
        addTime(allocation.getPageIds().size(), 0);
        if(!isKill){
            deletePtrFromProcess(allocation.getPid(), ptr);
        }
        memoryMap.remove(ptr);
    }

    public void kill(int pid){
        Process process = processes.get(pid);
        if(process != null){
            for(Integer ptr : process.getPtrs()){
                delete(ptr, true);
            }
            processes.remove(pid);
        }
    }
}
