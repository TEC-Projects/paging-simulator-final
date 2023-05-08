package com.pagingsimulator.pagingsimulator.Controller;

import com.pagingsimulator.pagingsimulator.Controller.Utils.DummyDataUtil;
import com.pagingsimulator.pagingsimulator.Controller.Utils.OperationsFileManager;
import com.pagingsimulator.pagingsimulator.Main;
import com.pagingsimulator.pagingsimulator.Model.Operation;
import com.pagingsimulator.pagingsimulator.Model.Simulation;
import com.pagingsimulator.pagingsimulator.UI.Model.SimulationRequest;

import java.io.IOException;
import java.util.ArrayList;

public class SimulationController {
    private Simulation simulation;
    private final OperationsFileManager operationsFileManager;

    public SimulationController(){
        operationsFileManager = new OperationsFileManager();
    }
    public void initializeSimulation(SimulationRequest simulationRequest) throws IOException {

        ArrayList<Operation> operations;
        if(simulationRequest.isSimulationThroughOperationFile()){
            operations = operationsFileManager.retrieveOperationsFromFile(simulationRequest.getOperationsFile());
        }else{
            operations = operationsFileManager.generateOperations(simulationRequest.getRandomSeed(), simulationRequest.getNumberOfOperations(), simulationRequest.getNumberOfProcesses());

        }
        simulation = new Simulation(
                simulationRequest.getPagingAlgorithm(),
                simulationRequest.getRandomSeed(),
                simulationRequest.getNumberOfOperations(),
                simulationRequest.getNumberOfProcesses(),
                operations,
                simulationRequest.isSimulationThroughOperationFile());

        Main.UISimulationController.initializeSimulationDetails(
                simulationRequest.getPagingAlgorithm(),
                simulationRequest.isSimulationThroughOperationFile(),
                simulationRequest.getNumberOfOperations(),
                simulationRequest.getNumberOfProcesses(),
                simulation.getAllProcesses());

    }

    public void startSimulation(){
        new Thread(() -> {
            try {
                simulation.runSimulation();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void pauseResumeSimulation(){
        simulation.setPaused(!simulation.isPaused());
    }

    public void selectRefreshRate(Integer refreshRate){
        simulation.setRefreshRate(refreshRate);
    }
}
