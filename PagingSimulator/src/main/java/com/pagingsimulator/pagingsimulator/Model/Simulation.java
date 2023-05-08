package com.pagingsimulator.pagingsimulator.Model;

import com.pagingsimulator.pagingsimulator.Main;
import com.pagingsimulator.pagingsimulator.UI.Model.SimulationUpdate;
import javafx.application.Platform;

import java.util.*;

public class Simulation {

    private boolean paused;
    private int refreshRate;
    private long randomSeed;
    private int numberOfOperations;
    private int numberOfProcesses;
    private final Machine otherMachine;
    private final OptimalMachine OPTMachine;
    private final PagingAlgorithmSimulationStatus optimalAlgorithmStatus;
    private final PagingAlgorithmSimulationStatus otherAlgorithmStatus;
    private final ArrayList<Operation> operations;
    private LinkedList<Integer> ramUsageHistoricalOPT;
    private LinkedList<Integer> ramUsageHistoricalOther;
    private LinkedList<Integer> vramUsageHistoricalOPT;
    private LinkedList<Integer> vramUsageHistoricalOther;
    private Operation currentOperation;

    public Simulation(String pagingAlgorithm, long randomSeed, int numberOfOperations, int numberOfProcesses, ArrayList<Operation> operations, boolean isFileLoaded) {
        this.paused = false;
        this.refreshRate = 1000;
        this.numberOfOperations = numberOfOperations;
        this.numberOfProcesses = numberOfProcesses;
        this.randomSeed = randomSeed;
        this.operations = operations;

        switch (pagingAlgorithm){
            case "FIFO" -> {
                otherMachine = new FIFOMachine(400000, 4000);
            }
            case "Second Chance" -> {
                otherMachine = new SecondChanceMachine(400000, 4000);
            }
            case "Most recently used" -> {
                otherMachine = new MRUMachine(400000, 4000);
            }
            case "Random" -> {
                otherMachine = new RandomMachine(400000, 4000, randomSeed);
            }
            default -> {
                otherMachine = null;
            }
        };

        OPTMachine = new OptimalMachine(400000, 4000, operations);

        otherAlgorithmStatus = new PagingAlgorithmSimulationStatus();
        optimalAlgorithmStatus = new PagingAlgorithmSimulationStatus();

        ramUsageHistoricalOther = new LinkedList<>();
        ramUsageHistoricalOPT = new LinkedList<>();
        vramUsageHistoricalOther = new LinkedList<>();
        vramUsageHistoricalOPT = new LinkedList<>();
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }


    public void updateOptimalAlgorithmStatus(Collection<Page> pages, int simulationElapsedTime, int thrashingTime, int ramUsage, int vRamUsage, int internalFragmentationVolume, int processCount, int loadedPages, int unloadedPages){
        optimalAlgorithmStatus.setPages(pages);
        optimalAlgorithmStatus.setSimulationElapsedTime(simulationElapsedTime);
        optimalAlgorithmStatus.setThrashingTime(thrashingTime);
        optimalAlgorithmStatus.setRamUsage(ramUsage);
        optimalAlgorithmStatus.setVRamUsage(vRamUsage);
        optimalAlgorithmStatus.setInternalFragmentationVolume(internalFragmentationVolume);
        optimalAlgorithmStatus.setSimulatedProcesses(processCount);
        optimalAlgorithmStatus.setUnloadedPages(unloadedPages);
        optimalAlgorithmStatus.setLoadedPages(loadedPages);
    }

    public void updateOtherAlgorithmStatus(Collection<Page> pages, int simulationElapsedTime, int thrashingTime, int ramUsage, int vRamUsage, int internalFragmentationVolume, int processCount, int loadedPages, int unloadedPages){
        otherAlgorithmStatus.setPages(pages);
        otherAlgorithmStatus.setSimulationElapsedTime(simulationElapsedTime);
        otherAlgorithmStatus.setThrashingTime(thrashingTime);
        otherAlgorithmStatus.setRamUsage(ramUsage);
        otherAlgorithmStatus.setVRamUsage(vRamUsage);
        otherAlgorithmStatus.setInternalFragmentationVolume(internalFragmentationVolume);
        otherAlgorithmStatus.setSimulatedProcesses(processCount);
        otherAlgorithmStatus.setLoadedPages(loadedPages);
        otherAlgorithmStatus.setUnloadedPages(unloadedPages);
    }

    public void runSimulation() throws InterruptedException {
        for(Operation operation : operations){
            while(paused){
                Thread.sleep(1000);
            }
            currentOperation = operation;

            switch (operation.getName()) {
                case "new" -> {
                    otherMachine.newAlloc(operation.getParameters().get(0), operation.getParameters().get(1));
                }
                case "use" -> {
                    otherMachine.use(operation.getParameters().get(0));
                }
                case "delete" -> {
                    otherMachine.delete(operation.getParameters().get(0), false);
                }
                case "kill" -> {
                    otherMachine.kill(operation.getParameters().get(0));
                }
                default -> {
                }
            }
            OPTMachine.next();

            int ramUsageOther = (otherMachine.getPages().size() - otherMachine.virtualMemory.size())*(otherMachine.getPageSize()/1000);

            updateOtherAlgorithmStatus(
                    otherMachine.getPages().values(),
                    otherMachine.getTime(),
                    otherMachine.getTrashing(),
                    ramUsageOther,
                    otherMachine.getVirtualMemory().size()*(otherMachine.getPageSize()/1000),
                    (otherMachine.getTotalMemory()/1000) - ramUsageOther,
                    otherMachine.getProcesses().size(),
                    (otherMachine.getPages().size() - otherMachine.virtualMemory.size()),
                    otherMachine.virtualMemory.size()
            );

            int ramUsageOPT = (OPTMachine.getPages().size() - OPTMachine.virtualMemory.size())*(OPTMachine.getPageSize()/1000);

            updateOptimalAlgorithmStatus(
                    OPTMachine.getPages().values(),
                    OPTMachine.getTime(),
                    OPTMachine.getTrashing(),
                    ramUsageOPT,
                    OPTMachine.getVirtualMemory().size()*(OPTMachine.getPageSize()/1000),
                    (otherMachine.getTotalMemory()/1000) - ramUsageOther,
                    OPTMachine.getProcesses().size(),
                    (OPTMachine.getPages().size() - OPTMachine.virtualMemory.size()),
                    OPTMachine.virtualMemory.size()
            );



            Platform.runLater(this::updateSimulationDataOnGUI);
            Thread.sleep(refreshRate);
        }
        Platform.runLater(Main.UISimulationController::handleSimulationCompleted);
    }

    private void updateSimulationDataOnGUI(){
        // ------------- OPT --------------

        // INIT
        SimulationUpdate simulationUpdateOPT = new SimulationUpdate();
        simulationUpdateOPT.setAlgorithmStatusUpdate(optimalAlgorithmStatus);

        // REAL MEMORY PROCESSES MAP OPT
        ArrayList<Integer> ramUsageMappingOPT = new ArrayList<>();
        for (Integer pageId : OPTMachine.realMemory){
            if(pageId == -1){
                ramUsageMappingOPT.add(pageId);
            }else{
                ramUsageMappingOPT.add(OPTMachine.pages.get(pageId).getPID());
            }
        }
        simulationUpdateOPT.setRAMUsageMapping(ramUsageMappingOPT);

        // TIME LINE RAM OPT
        if(ramUsageHistoricalOPT.size() == 61){
            ramUsageHistoricalOPT.remove(0);
        }
        ramUsageHistoricalOPT.add(optimalAlgorithmStatus.getRamUsage());
        simulationUpdateOPT.setRAMUsageTimeline(ramUsageHistoricalOPT.toArray());

        // TIME LINE VRAM OPT
        if(vramUsageHistoricalOPT.size() == 61){
            vramUsageHistoricalOPT.remove(0);
        }
        vramUsageHistoricalOPT.add(optimalAlgorithmStatus.getVRamUsage());
        simulationUpdateOPT.setVirtualRAMUsageTimeline(vramUsageHistoricalOPT.toArray());

        simulationUpdateOPT.setCurrentOperation(currentOperation.toString());

        Main.UISimulationController.updateOptimalSimulationData(simulationUpdateOPT);

        // ------------- OTHER --------------

        //INIT
        SimulationUpdate simulationUpdateOther = new SimulationUpdate();
        simulationUpdateOther.setAlgorithmStatusUpdate(otherAlgorithmStatus);

        // REAL MEMORY PROCESSES MAP OPT
        ArrayList<Integer> ramUsageMappingOther = new ArrayList<>();
        for (Integer pageId : otherMachine.realMemory){
            if(pageId == -1){
                ramUsageMappingOther.add(pageId);
            }else{
                ramUsageMappingOther.add(otherMachine.pages.get(pageId).getPID());
            }
        }
        simulationUpdateOther.setRAMUsageMapping(ramUsageMappingOther);

        // TIME LINE RAM OTHER
        if(ramUsageHistoricalOther.size() == 61){
            ramUsageHistoricalOther.remove(0);
        }
        ramUsageHistoricalOther.add(otherAlgorithmStatus.getRamUsage());
        simulationUpdateOther.setRAMUsageTimeline(ramUsageHistoricalOther.toArray());

        // TIME LINE VRAM OTHER
        if(vramUsageHistoricalOther.size() == 61){
            vramUsageHistoricalOther.remove(0);
        }
        vramUsageHistoricalOther.add(otherAlgorithmStatus.getVRamUsage());
        simulationUpdateOther.setVirtualRAMUsageTimeline(vramUsageHistoricalOther.toArray());
        simulationUpdateOther.setCurrentOperation(currentOperation.toString());

        Main.UISimulationController.updateOtherSimulationData(simulationUpdateOther);
    }

    public ArrayList<Integer> getAllProcesses(){
        return OPTMachine.getAllProcesses();
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }
}
