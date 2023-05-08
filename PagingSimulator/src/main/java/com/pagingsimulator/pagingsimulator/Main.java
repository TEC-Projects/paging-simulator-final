package com.pagingsimulator.pagingsimulator;

import com.pagingsimulator.pagingsimulator.Controller.SimulationController;
import com.pagingsimulator.pagingsimulator.Controller.Utils.OperationsFileManager;
import com.pagingsimulator.pagingsimulator.UI.Utils.SceneManagerUtil;
import com.pagingsimulator.pagingsimulator.UI.Controller.UIMainMenuController;
import com.pagingsimulator.pagingsimulator.UI.Controller.UISimulationController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static UIMainMenuController UIMainMenuController;
    public static UISimulationController UISimulationController;
    public static SimulationController simulationController = new SimulationController();
    public static SceneManagerUtil sceneManager = new SceneManagerUtil();
    public static OperationsFileManager operationsFileManager = new OperationsFileManager();


    @Override
    public void start(Stage stage) throws IOException {
        sceneManager.loadScene(stage, "/com/pagingsimulator/pagingsimulator/screens/mainMenu.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }
}