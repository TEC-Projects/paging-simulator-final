package com.pagingsimulator.pagingsimulator.UI.Utils;

import com.pagingsimulator.pagingsimulator.Model.Page;
import com.pagingsimulator.pagingsimulator.UI.Controller.UISimulationController;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SimulationUtil {

    public SimulationUtil() {
    }

    public HashMap<Integer, String> generateProcessesColors(ArrayList<Integer> PIDs){

        HashMap<Integer, String> processesColors = new HashMap<>();

        Random rand = new Random();

        for (Integer PID: PIDs) {
            processesColors.put(PID, String.format("#%06x", rand.nextInt(0xffffff + 1)));
        }

        return processesColors;

    };

    public String simulationSizeFormatter(int numberOfOperations, int numberOfProcesses){
        return numberOfProcesses + " processes & " + numberOfOperations + " operations";
    }

    public XYChart.Series plottingDataFormatter(ArrayList<Pair<Integer, Integer>> plotData){
        XYChart.Series series = new XYChart.Series<>();
        for (Pair<Integer, Integer> point : plotData) {
            series.getData().add(new XYChart.Data<Number, Number>(point.getKey(), point.getValue()));
        }
        return series;
    }

    public ArrayList<Rectangle> RAMUsageMappingFormatter(ArrayList<Integer> RAMUsage, HashMap<Integer, String> processColors){
        ArrayList<Rectangle> RAMMapping = new ArrayList<>();

        for (int i = 1; i < RAMUsage.size(); i++) {
            Rectangle memorySector = new Rectangle(3, 20);
            if(RAMUsage.get(i) != -1){
                memorySector.setStyle("-fx-fill:" + processColors.get(RAMUsage.get(i)) + ";");
            }else{
                memorySector.setStyle("-fx-fill: #e1e1e1;");
            }

            RAMMapping.add(memorySector);

        }

        return RAMMapping;
    }

    public String percentageStringFormatter(double number){
        return new DecimalFormat("#.##").format(number * 100) + "%";
    }

    public void thrashingColorFormatter(int thrashingLevel, int simulationElapsedTime, Label thrashingTime, Label thrashingPercentage, Label thrashingTimeTitle, Label thrashingPercentageTitle){
        if((double) thrashingLevel / simulationElapsedTime > 0.49){
            thrashingTime.getStyleClass().addAll("text-danger");
            thrashingTimeTitle.getStyleClass().addAll("text-danger");
            thrashingPercentage.getStyleClass().addAll("text-danger");
            thrashingPercentageTitle.getStyleClass().addAll("text-danger");
        }else{
            thrashingTime.getStyleClass().addAll("text-default");
            thrashingTimeTitle.getStyleClass().addAll("text-default");
            thrashingPercentage.getStyleClass().addAll("text-default");
            thrashingPercentageTitle.getStyleClass().addAll("text-default");
        }
    }
}
