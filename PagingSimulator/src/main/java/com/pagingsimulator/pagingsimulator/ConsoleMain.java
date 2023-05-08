package com.pagingsimulator.pagingsimulator;

import com.pagingsimulator.pagingsimulator.Controller.SimulationController;
import com.pagingsimulator.pagingsimulator.Controller.Utils.OperationsFileManager;
import com.pagingsimulator.pagingsimulator.UI.Model.SimulationRequest;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class ConsoleMain {
    public static void main(String[] args) throws IOException {
        OperationsFileManager operationsFileManager = new OperationsFileManager();
//        System.out.println(operationsFileManager.retrieveOperationsFromFile(new File("C:\\Users\\joshg\\Documents\\Projects\\paging-simulator\\PagingSimulator\\src\\main\\java\\com\\pagingsimulator\\pagingsimulator\\op.txt")));
//        System.out.println(operationsFileManager.convertOperationsToFileString(operationsFileManager.generateOperations(1232L, 500, 10)));
//        SimulationController simulationController = new SimulationController();
//        simulationController.initializeSimulation(
//                new SimulationRequest(
//                        "Random",
//                        1232L,
//                        false,
//                        null,
//                        10,
//                        500
//                )
//        );
//        simulationController.startSimulation();
//    }
        System.out.println(Instant.now().getEpochSecond());
    }
}
