package com.pagingsimulator.pagingsimulator.Model;

import java.util.*;

public class OptimalMachine extends Machine{
    private ArrayList<Integer> allProcesses;
    private HashMap<Integer, LinkedList<Integer>> earliestAccessToPages;
    private int currentInstruction;
    private ArrayList<Operation> operations;
    private ArrayList<Page> futurePages;
    private HashMap<Integer, ArrayList<Integer>> futureMemoryMap;

    public OptimalMachine(int totalMemory, int pageSize, ArrayList<Operation> operations) {
        super(totalMemory, pageSize);
        this.operations = operations;
        currentInstruction = 0;
        earliestAccessToPages = new HashMap<>();
        futurePages = new ArrayList<>();
        futureMemoryMap = new HashMap<>();
        allProcesses = new ArrayList<>();

        // COUNTERS
        int pageCount = 0;
        int ptrCount = 0;
        int instructionCount = 0;

        // ITERATION OVER THE WHOLE OPERATION LIST
        for (Operation operation : operations) {
            switch (operation.getName()) {
                case "new" -> {
                    // ADD NEW PROCESS ID TO LIST
                    if(!allProcesses.contains(operation.getParameters().get(0))){
                        allProcesses.add(operation.getParameters().get(0));
                    }

                    // IDENTIFY ALL NEEDED PAGES TO SUPPLY THE MEMORY ALLOCATION REQUEST
                    ArrayList<Integer> createdPages = new ArrayList<>();
                    for (int i = 0; i < Math.ceil(operation.getParameters().get(1)*1.0/pageSize); i++) {
                        Page page = new Page(pageCount, operation.getParameters().get(0), -1, -1,-1, -1);
                        futurePages.add(page);
                        createdPages.add(pageCount);
                        earliestAccessToPages.put(pageCount++, new LinkedList<>());
                    }

                    futureMemoryMap.put(ptrCount++, createdPages);
                }
                case "use" -> {
                    // REGISTER IN ALL THE PAGES THAT BELONG TO THE PTR ITS ACCESSES FOR FURTHER CONSULT
                    for(Integer pageId : futureMemoryMap.get(operation.getParameters().get(0))){
                        earliestAccessToPages.get(pageId).add(instructionCount);
                    }
                }
                default -> {
                }
            }
            instructionCount++;
        }
    }

    public void next(){
        Operation operation = operations.get(currentInstruction);
        System.out.println("OPT op: " + operation);
        switch (operation.getName()) {
            case "new" -> {
                newAlloc(operation.getParameters().get(0), operation.getParameters().get(1));
            }
            case "use" -> {
                use(operation.getParameters().get(0));
                for(LinkedList<Integer> uses : earliestAccessToPages.values()){
                    if(uses.isEmpty()){
                        continue;
                    }
                    if(uses.peek() == currentInstruction){
                        uses.remove(uses.peek());
                    }
                }
            }
            case "delete" -> {
                for(Integer pageId : memoryMap.get(operation.getParameters().get(0)).getPageIds()){
                    earliestAccessToPages.remove(pageId);
                }
                delete(operation.getParameters().get(0), false);
            }
            case "kill" -> {
                if (processes.get(operation.getParameters().get(0)) != null){
                    for(Integer ptr : processes.get(operation.getParameters().get(0)).getPtrs()){
                        for(Integer pageId : memoryMap.get(ptr).getPageIds()){
                            earliestAccessToPages.remove(pageId);
                        }
                    }
                }

                kill(operation.getParameters().get(0));
            }
            default -> {
            }
        }
        currentInstruction++;
    }

    @Override
    int selectPageToVRAM() {
        int latestAccessIndex = -1;
        int latestInstructionCount = 0;
        int realMemoryIndex = 0;
        // ITERATE OVER REAL MEMORY PAGES
        for(Integer pageId : realMemory){
            LinkedList<Integer> accessesToPage = earliestAccessToPages.get(pageId);
            // IF THE PAGE DOESNT HAVE MORE ACCESSES IN THE FUTURE, SELECT IT
            if(accessesToPage.isEmpty()){
                return realMemoryIndex;
            }
            // COMPARE THE LATEST ACCESS FOUND WITH THE NEXT ACCESS FOR THE CURRENT PAGE
            int instructionCount = accessesToPage.peek();
            if(instructionCount > latestInstructionCount && instructionCount != currentInstruction){
                latestAccessIndex = realMemoryIndex;
                latestInstructionCount = instructionCount;
            }
            realMemoryIndex++;
        };
        return latestAccessIndex;
    }

    @Override
    public long getNewMark(long simTime) {
        return 0;
    }

    @Override
    public long getUsedMark(long currentMark, long simTime) {
        return 0;
    }

    public ArrayList<Integer> getAllProcesses() {
        return allProcesses;
    }

    public void setAllProcesses(ArrayList<Integer> allProcesses) {
        this.allProcesses = allProcesses;
    }

    public HashMap<Integer, LinkedList<Integer>> getEarliestAccessToPages() {
        return earliestAccessToPages;
    }

    public void setEarliestAccessToPages(HashMap<Integer, LinkedList<Integer>> earliestAccessToPages) {
        this.earliestAccessToPages = earliestAccessToPages;
    }

    public int getCurrentInstruction() {
        return currentInstruction;
    }

    public void setCurrentInstruction(int currentInstruction) {
        this.currentInstruction = currentInstruction;
    }

    public ArrayList<Operation> getOperations() {
        return operations;
    }

    public void setOperations(ArrayList<Operation> operations) {
        this.operations = operations;
    }

    public ArrayList<Page> getFuturePages() {
        return futurePages;
    }

    public void setFuturePages(ArrayList<Page> futurePages) {
        this.futurePages = futurePages;
    }

    public HashMap<Integer, ArrayList<Integer>> getFutureMemoryMap() {
        return futureMemoryMap;
    }

    public void setFutureMemoryMap(HashMap<Integer, ArrayList<Integer>> futureMemoryMap) {
        this.futureMemoryMap = futureMemoryMap;
    }
}
