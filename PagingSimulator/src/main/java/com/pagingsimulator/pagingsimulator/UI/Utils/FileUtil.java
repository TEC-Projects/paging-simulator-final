package com.pagingsimulator.pagingsimulator.UI.Utils;

import com.pagingsimulator.pagingsimulator.Main;
import com.pagingsimulator.pagingsimulator.Model.Operation;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class FileUtil {

    private FileChooser fileChooser = new FileChooser();

    private ValidatorUtil validatorUtil;

    public FileUtil(){
        validatorUtil = new ValidatorUtil();
    }

    public File loadSimulationFile() throws Exception{
        try{
            fileChooser.setTitle("Load simulation file");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
            return fileChooser.showOpenDialog(null);
        }catch (Exception e){
            throw new Exception("Error loading simulation file");
        }

    }

    public void generateSimulationFile(int numberOfOperations, int numberOfProcesses, String randomSeed) throws Exception {

        fileChooser.setTitle("Save simulation file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        validatorUtil.randomSeedValidator(randomSeed);

        try{
            FileWriter simulationFileWriter = new FileWriter(fileChooser.showSaveDialog(null).getAbsolutePath());
            ArrayList<Operation> simulationOperations = Main.operationsFileManager.generateOperations(
                    Long.parseLong(randomSeed),
                    numberOfOperations,
                    numberOfProcesses);
            simulationFileWriter.write(Main.operationsFileManager.convertOperationsToFileString(simulationOperations));
            simulationFileWriter.close();
        }catch (Exception e){
            throw new Exception("Error generating simulation file");
        }
    }

}
