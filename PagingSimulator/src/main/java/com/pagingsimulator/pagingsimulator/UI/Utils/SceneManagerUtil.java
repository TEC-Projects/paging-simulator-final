package com.pagingsimulator.pagingsimulator.UI.Utils;

import com.pagingsimulator.pagingsimulator.UI.Controller.ScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import com.pagingsimulator.pagingsimulator.Main;

import java.io.IOException;

public class SceneManagerUtil {

    public SceneManagerUtil() {
    }

    public void loadScene(Stage stage, String screenRoute) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(screenRoute));
        Parent root = loader.load();
        Main.UIMainMenuController = loader.getController();
        stage.setScene(new Scene(root, 1200, 600));

        stage.setTitle("Paging simulator");
        stage.getIcons().add(new Image(getClass().getResource("/com/pagingsimulator/pagingsimulator/img/isotype.png").toString()));
        stage.setResizable(false);

        stage.show();
    }

    public void navigate(ActionEvent event, String screenRoute) throws IOException {
        FXMLLoader viewHandler = new FXMLLoader(getClass().getResource(screenRoute));
        Parent root = viewHandler.load();
        Main.UISimulationController = viewHandler.getController();
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 1200, 600));
        window.show();
    }

}
