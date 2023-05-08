package com.pagingsimulator.pagingsimulator.UI.Controller;

import com.pagingsimulator.pagingsimulator.Main;
import com.pagingsimulator.pagingsimulator.Model.Page;
import com.pagingsimulator.pagingsimulator.Model.PagingAlgorithmSimulationStatus;
import com.pagingsimulator.pagingsimulator.UI.Model.SimulationUpdate;
import com.pagingsimulator.pagingsimulator.UI.Utils.DummyDataUtil;
import com.pagingsimulator.pagingsimulator.UI.Utils.SimulationUtil;
import com.pagingsimulator.pagingsimulator.UI.Utils.SnackBarUtil;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.*;

public class UISimulationController extends ScreenController implements Initializable {

    private SimulationUtil simulationUtil = new SimulationUtil();
    private DummyDataUtil dummyDataUtil = new DummyDataUtil();
    private final SnackBarUtil snackBarUtil = new SnackBarUtil();
    private HashMap<Integer, String> processColors = new HashMap<>();
    private int numberOfProcesses;
    private boolean isPaused;
    private boolean hasStarted;
    @FXML
    private TableView<Page>
            optimalMMUTable,
            otherMMUTable;
    @FXML
    private AreaChart<NumberAxis, NumberAxis>
            optimalRAMChart,
            otherRAMChart,
            optimalVirtualRAMChart,
            otherVirtualRAMChart;
    @FXML
    private HBox
            otherRAMDistribution,
            optimalRAMDistribution;

    @FXML
    private StackPane snackBarPane;

    @FXML
    private RadioButton
        oneXRadioButton,
        twoXRadioButton,
        threeXRadioButton;

    private ToggleGroup refreshRateToggleGroup;

    @FXML
    private Label
            currentOperationLabel,
            pagingAlgorithmLabel,
            simulationSizeLabel,
            otherRAMUsageKB,
            otherRAMUsagePercentage,
            otherSimulatedProcesses,
            otherSimulationTime,
            otherThrashingLevelSeconds,
            otherThrashingLevelSecondsTitle,
            otherThrashingLevelPercentageTitle,
            otherThrashingLevelPercentage,
            otherUnloadedPages,
            otherVirtualRAMUsageKB,
            otherVirtualRAMUsagePercentage,
            otherFragmentation,
            otherFragmentationPercentage,
            otherLoadedPages,
            optimalRAMUsageKB,
            optimalRAMUsagePercentage,
            optimalSimulatedProcesses,
            optimalSimulationTime,
            optimalThrashingLevelSeconds,
            optimalThrashingLevelPercentage,
            optimalThrashingLevelSecondsTitle,
            optimalThrashingLevelPercentageTitle,
            optimalUnloadedPages,
            optimalVirtualRAMUsageKB,
            optimalVirtualRAMUsagePercentage,
            optimalFragmentation,
            optimalFragmentationPercentage,
            optimalLoadedPages,
            snackBarMessage;
    @FXML
    private NumberAxis
            optimalRAMXAxis,
            optimalRAMYAxis,
            optimalVirtualRAMXAxis,
            optimalVirtualRAMYAxis,
            otherRAMXAxis,
            otherRAMYAxis,
            otherVirtualRAMXAxis,
            otherVirtualRAMYAxis;
    @FXML
    private Button generalSimulationButton;


    @FXML
    private void generalSimulationButtonEvent(){
        if(!hasStarted){
            handleStartSimulation();
        }else{
            if(isPaused){
                handleResumeSimulation();
            }else{
                handlePauseSimulation();
            }
        }

    }

    @FXML
    private void oneXRadioButtonEvent(){
//        twoXRadioButton.setSelected(false);
//        threeXRadioButton.setSelected(false);
    }

    @FXML
    private void twoXRadioButtonEvent(){
//        oneXRadioButton.setSelected(false);
//        threeXRadioButton.setSelected(false);
    }

    @FXML
    private void threeXRadioButtonEvent(){
//        twoXRadioButton.setSelected(false);
//        oneXRadioButton.setSelected(false);
    }

    public void handleSimulationCompleted(){
        snackBarUtil.showSnackBar("Simulation completed", "success", snackBarPane, snackBarMessage, false);
        generalSimulationButton.setVisible(false);
    }

    private void handlePauseSimulation(){
        Main.simulationController.pauseResumeSimulation();
        snackBarUtil.showSnackBar("Simulation paused", "info", snackBarPane, snackBarMessage, false);
        isPaused = true;
        generalSimulationButton.getStyleClass().setAll("btn", "btn-primary");
        generalSimulationButton.setText("RESUME SIMULATION");
    }

    private void handleResumeSimulation(){
        Main.simulationController.pauseResumeSimulation();
        snackBarUtil.hideSnackBar(snackBarPane);
        isPaused = false;
        generalSimulationButton.getStyleClass().setAll("btn", "btn-danger");
        generalSimulationButton.setText("PAUSE SIMULATION");
    }

    private void handleStartSimulation(){
        Main.simulationController.startSimulation();
        hasStarted = true;
        generalSimulationButton.getStyleClass().setAll("btn", "btn-danger");
        generalSimulationButton.setText("PAUSE SIMULATION");
    }

    public void updateOtherSimulationData(SimulationUpdate simulationUpdate){
        // Percentage data calculus
        String RAMUsagePercentage = simulationUtil.percentageStringFormatter(simulationUpdate.getAlgorithmStatusUpdate().getRamUsage() / 400D);
        String VRAMUsagePercentage = simulationUtil.percentageStringFormatter(simulationUpdate.getAlgorithmStatusUpdate().getVRamUsage() / 400D);
        String fragmentationPercentage = simulationUtil.percentageStringFormatter(simulationUpdate.getAlgorithmStatusUpdate().getInternalFragmentationVolume() / 400D);
        String thrashingPercentage = simulationUtil.percentageStringFormatter((double) simulationUpdate.getAlgorithmStatusUpdate().getThrashingTime() / simulationUpdate.getAlgorithmStatusUpdate().getSimulationElapsedTime());

        otherSimulationTime.setText(simulationUpdate.getAlgorithmStatusUpdate().getSimulationElapsedTime() + " s");

        otherSimulatedProcesses.setText(String.valueOf(simulationUpdate.getAlgorithmStatusUpdate().getSimulatedProcesses()));

        otherRAMUsageKB.setText(simulationUpdate.getAlgorithmStatusUpdate().getRamUsage() + " KB");
        otherRAMUsagePercentage.setText(RAMUsagePercentage);

        otherVirtualRAMUsageKB.setText(simulationUpdate.getAlgorithmStatusUpdate().getVRamUsage() + " KB");
        otherVirtualRAMUsagePercentage.setText(VRAMUsagePercentage);

        otherLoadedPages.setText(String.valueOf(simulationUpdate.getAlgorithmStatusUpdate().getLoadedPages()));
        otherUnloadedPages.setText(String.valueOf(simulationUpdate.getAlgorithmStatusUpdate().getUnloadedPages()));

        otherThrashingLevelSeconds.setText(simulationUpdate.getAlgorithmStatusUpdate().getThrashingTime() + " s");
        otherThrashingLevelPercentage.setText(thrashingPercentage);

        otherFragmentation.setText(simulationUpdate.getAlgorithmStatusUpdate().getInternalFragmentationVolume() + " KB");
        otherFragmentationPercentage.setText(fragmentationPercentage);

        // Thrashing level color formatting
        simulationUtil.thrashingColorFormatter(
                simulationUpdate.getAlgorithmStatusUpdate().getThrashingTime(),
                simulationUpdate.getAlgorithmStatusUpdate().getSimulationElapsedTime(),
                otherThrashingLevelSeconds,
                otherThrashingLevelSecondsTitle,
                otherThrashingLevelPercentage,
                otherThrashingLevelPercentageTitle);

        // Chart data update
        otherRAMChart.getData().clear();
        otherRAMChart.getData().addAll(simulationUtil.plottingDataFormatter(simulationUpdate.getRAMUsageTimeline()));
        otherVirtualRAMChart.getData().clear();
        otherVirtualRAMChart.getData().addAll(simulationUtil.plottingDataFormatter(simulationUpdate.getVirtualRAMUsageTimeline()));

        // Pages table update
        if(!otherMMUTable.getItems().isEmpty()){
            otherMMUTable.getItems().clear();
        }
        for (Page page: simulationUpdate.getAlgorithmStatusUpdate().getPages()) {
            otherMMUTable.getItems().add(page);
        }

        // RAM distribution update
        otherRAMDistribution.getChildren().clear();
        otherRAMDistribution.getChildren().addAll(simulationUtil.RAMUsageMappingFormatter(simulationUpdate.getRAMUsageMapping(), processColors));
    }

    public void updateOptimalSimulationData(SimulationUpdate simulationUpdate){

        // Percentage data calculus
        String RAMUsagePercentage = simulationUtil.percentageStringFormatter(simulationUpdate.getAlgorithmStatusUpdate().getRamUsage() / 400D);
        String VRAMUsagePercentage = simulationUtil.percentageStringFormatter(simulationUpdate.getAlgorithmStatusUpdate().getVRamUsage() / 400D);
        String fragmentationPercentage = simulationUtil.percentageStringFormatter(simulationUpdate.getAlgorithmStatusUpdate().getInternalFragmentationVolume() / 400D);
        String thrashingPercentage = simulationUtil.percentageStringFormatter((double) simulationUpdate.getAlgorithmStatusUpdate().getThrashingTime() / simulationUpdate.getAlgorithmStatusUpdate().getSimulationElapsedTime());

        optimalSimulationTime.setText(simulationUpdate.getAlgorithmStatusUpdate().getSimulationElapsedTime() + " s");

        optimalSimulatedProcesses.setText(String.valueOf(simulationUpdate.getAlgorithmStatusUpdate().getSimulatedProcesses()));

        optimalRAMUsageKB.setText(simulationUpdate.getAlgorithmStatusUpdate().getRamUsage() + " KB");
        optimalRAMUsagePercentage.setText(RAMUsagePercentage);

        optimalVirtualRAMUsageKB.setText(simulationUpdate.getAlgorithmStatusUpdate().getVRamUsage() + " KB");
        optimalVirtualRAMUsagePercentage.setText(VRAMUsagePercentage);

        optimalLoadedPages.setText(String.valueOf(simulationUpdate.getAlgorithmStatusUpdate().getLoadedPages()));
        optimalUnloadedPages.setText(String.valueOf(simulationUpdate.getAlgorithmStatusUpdate().getUnloadedPages()));

        optimalThrashingLevelSeconds.setText(simulationUpdate.getAlgorithmStatusUpdate().getThrashingTime() + " s");
        optimalThrashingLevelPercentage.setText(thrashingPercentage);

        optimalFragmentation.setText(simulationUpdate.getAlgorithmStatusUpdate().getInternalFragmentationVolume() + " KB");
        optimalFragmentationPercentage.setText(fragmentationPercentage);

        currentOperationLabel.setText(simulationUpdate.getCurrentOperation());

        // Thrashing level color formatting
        simulationUtil.thrashingColorFormatter(
                simulationUpdate.getAlgorithmStatusUpdate().getThrashingTime(),
                simulationUpdate.getAlgorithmStatusUpdate().getSimulationElapsedTime(),
                optimalThrashingLevelSeconds,
                optimalThrashingLevelSecondsTitle,
                optimalThrashingLevelPercentage,
                optimalThrashingLevelPercentageTitle);

        // Pages table update
        if(!optimalMMUTable.getItems().isEmpty()){
            optimalMMUTable.getItems().clear();
        }
        for (Page page: simulationUpdate.getAlgorithmStatusUpdate().getPages()) {
            optimalMMUTable.getItems().add(page);
        }

        // Chart data update
        optimalRAMChart.getData().clear();
        optimalRAMChart.getData().addAll(simulationUtil.plottingDataFormatter(simulationUpdate.getRAMUsageTimeline()));
        optimalVirtualRAMChart.getData().clear();
        optimalVirtualRAMChart.getData().addAll(simulationUtil.plottingDataFormatter(simulationUpdate.getVirtualRAMUsageTimeline()));

        // RAM distribution update
        optimalRAMDistribution.getChildren().clear();
        optimalRAMDistribution.getChildren().addAll(simulationUtil.RAMUsageMappingFormatter(simulationUpdate.getRAMUsageMapping(), processColors));

    }

    private void setMMUTableColumns(TableView<Page> table, Label simTimeLabel){
        TableColumn<Page,Integer> pageIdColumn = new TableColumn<>("Page ID");
        pageIdColumn.setCellValueFactory(new PropertyValueFactory<Page, Integer>("pageId"));

        TableColumn<Page,Integer> PIDColumn = new TableColumn<>("PID");
        PIDColumn.setCellValueFactory(new PropertyValueFactory<>("PID"));

        TableColumn<Page,String> loadedColumn = new TableColumn<>("Loaded");
        loadedColumn.setCellValueFactory(f -> new ReadOnlyStringWrapper(f.getValue().isLoaded() ? "Yes" : "No"));

        TableColumn<Page,Integer> lAddressColumn = new TableColumn<>("L-add");
        lAddressColumn.setCellValueFactory(new PropertyValueFactory<>("logicalAddress"));

        TableColumn<Page,String> mAddressColumn = new TableColumn<>("M-add");
        mAddressColumn.setCellValueFactory(f -> new ReadOnlyStringWrapper(f.getValue().getMemoryAddress() == -1 ? "None" : String.valueOf(f.getValue().getMemoryAddress())));

        TableColumn<Page,String> dAddressColumn = new TableColumn<>("D-add");
        dAddressColumn.setCellValueFactory(f -> new ReadOnlyStringWrapper(f.getValue().getDiskAddress() == -1 ? "None" : String.valueOf(f.getValue().getDiskAddress())));

        TableColumn<Page,String> loadTimeColumn = new TableColumn<>("L-Time");
        loadTimeColumn.setCellValueFactory(f -> {
            if (f.getValue().isLoaded()){
                return new ReadOnlyStringWrapper(Integer.parseInt(simTimeLabel.getText().replace(" s", "")) - f.getValue().getLoadedAt() + " s") ;
            }else{
                return new ReadOnlyStringWrapper("0 s");
            }
        });

        TableColumn<Page,String> markColumn = new TableColumn<>("Mark");
        markColumn.setCellValueFactory(new PropertyValueFactory<>("mark"));

        table.getColumns().addAll(
                pageIdColumn,
                PIDColumn,
                loadedColumn,
                lAddressColumn,
                mAddressColumn,
                dAddressColumn,
                loadTimeColumn,
                markColumn
        );

    }

    private void setRowStyleMMUTables(){
        optimalMMUTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Page page, boolean empty) {
                super.updateItem(page, empty);
                if (page == null) {
                    setStyle("");
                } else {
                    setStyle("-fx-background-color:" + processColors.get(page.getPID()) + ";");
                }
            }
        });

        otherMMUTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Page page, boolean empty) {
                super.updateItem(page, empty);
                if (page == null) {
                    setStyle("");
                } else {
                    setStyle("-fx-background-color:" + processColors.get(page.getPID()) + ";");
                }
            }
        });
    }

    private void initializeTables(){
        setMMUTableColumns(optimalMMUTable, optimalSimulationTime);
        setMMUTableColumns(otherMMUTable, otherSimulationTime);
        setRowStyleMMUTables();
    }

    private void initializeCharts(){
        configurePlotXAxis(optimalRAMXAxis);
        configurePlotXAxis(optimalVirtualRAMXAxis);
        configurePlotXAxis(otherRAMXAxis);
        configurePlotXAxis(otherVirtualRAMXAxis);
        optimalRAMChart.setAnimated(false);
        optimalVirtualRAMChart.setAnimated(false);
        otherRAMChart.setAnimated(false);
        otherVirtualRAMChart.setAnimated(false);
    }

    private void configurePlotXAxis(NumberAxis axis){
        axis.setAutoRanging(false);
        axis.setLowerBound(0);
        axis.setUpperBound(60);
        axis.setTickUnit(5);
        axis.setAnimated(false);
    }

    private void initializeRAMMapping(){

        ArrayList<Integer> emptyRAM = new ArrayList<>(Collections.nCopies(100, -1));

        optimalRAMDistribution.getChildren().addAll(simulationUtil.RAMUsageMappingFormatter(emptyRAM, processColors));
        otherRAMDistribution.getChildren().addAll(simulationUtil.RAMUsageMappingFormatter(emptyRAM, processColors));
    }

    private void initializeToggleButtons(){

        refreshRateToggleGroup = new ToggleGroup();

        oneXRadioButton.setSelected(true);
        oneXRadioButton.setToggleGroup(refreshRateToggleGroup);
        twoXRadioButton.setToggleGroup(refreshRateToggleGroup);
        threeXRadioButton.setToggleGroup(refreshRateToggleGroup);


        refreshRateToggleGroup.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            RadioButton selectedRefreshRate = (RadioButton) refreshRateToggleGroup.getSelectedToggle();
            switch (selectedRefreshRate.getText()){
                case "1X" -> Main.simulationController.selectRefreshRate(1000);
                case "2X" -> Main.simulationController.selectRefreshRate(500);
                case "4X" -> Main.simulationController.selectRefreshRate(250);
            }
        });

    }

    public void initializeSimulationDetails(String pagingAlgorithm, boolean isOperationsFileLoaded, int numberOfOperations, int numberOfProcesses, ArrayList<Integer> PIDs){
        this.numberOfProcesses = numberOfProcesses;
        processColors = simulationUtil.generateProcessesColors(PIDs);
        pagingAlgorithmLabel.setText(pagingAlgorithm);

        if(isOperationsFileLoaded){
            simulationSizeLabel.setText("Custom file simulation");
        }else{
            simulationSizeLabel.setText(simulationUtil.simulationSizeFormatter(numberOfOperations, numberOfProcesses));
        }
    }

    public TableView<Page> getOptimalMMUTable() {
        return optimalMMUTable;
    }

    public TableView<Page> getOtherMMUTable() {
        return otherMMUTable;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        isPaused = false;
        hasStarted = false;
        initializeToggleButtons();
        initializeCharts();
        initializeTables();
        initializeRAMMapping();
    }
}