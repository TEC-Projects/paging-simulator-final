package com.pagingsimulator.pagingsimulator.UI.Model;

import java.io.File;
import java.util.ArrayList;

public class SimulationRequest {
    private String pagingAlgorithm;
    private Long randomSeed;
    private Boolean simulationThroughOperationFile;
    private File operationsFile;
    private int numberOfProcesses;
    private int numberOfOperations;
    private ArrayList<Integer> processes;

    public SimulationRequest(){
        simulationThroughOperationFile = true;
    };

    public SimulationRequest(String pagingAlgorithm, Long randomSeed, Boolean simulationThroughOperationFile, File operationsFile, int numberOfProcesses, int numberOfOperations) {
        this.pagingAlgorithm = pagingAlgorithm;
        this.randomSeed = randomSeed;
        this.simulationThroughOperationFile = simulationThroughOperationFile;
        this.operationsFile = operationsFile;
        this.numberOfProcesses = numberOfProcesses;
        this.numberOfOperations = numberOfOperations;
    }

    public String getPagingAlgorithm() {
        return pagingAlgorithm;
    }

    public void setPagingAlgorithm(String pagingAlgorithm) {
        this.pagingAlgorithm = pagingAlgorithm;
    }

    public Long getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(Long randomSeed) {
        this.randomSeed = randomSeed;
    }

    public Boolean isSimulationThroughOperationFile() {
        return simulationThroughOperationFile;
    }

    public void setSimulationThroughOperationFile(Boolean simulationThroughOperationFile) {
        this.simulationThroughOperationFile = simulationThroughOperationFile;
    }

    public File getOperationsFile() {
        return operationsFile;
    }

    public void setOperationsFile(File operationsFile) {
        this.operationsFile = operationsFile;
    }

    public int getNumberOfProcesses() {
        return numberOfProcesses;
    }

    public void setNumberOfProcesses(int numberOfProcesses) {
        this.numberOfProcesses = numberOfProcesses;
    }

    public int getNumberOfOperations() {
        return numberOfOperations;
    }

    public void setNumberOfOperations(int numberOfOperations) {
        this.numberOfOperations = numberOfOperations;
    }

    public Boolean getSimulationThroughOperationFile() {
        return simulationThroughOperationFile;
    }

    public ArrayList<Integer> getProcesses() {
        return processes;
    }

    public void setProcesses(ArrayList<Integer> processes) {
        this.processes = processes;
    }
}
