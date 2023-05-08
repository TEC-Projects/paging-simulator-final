package com.pagingsimulator.pagingsimulator.UI.Utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.Objects;

public class SnackBarUtil {

    public SnackBarUtil(){}

    public void showSnackBar(String message, String type, StackPane snackBarPane, Label snackBarLabel, boolean autoHide){

        switch (type) {
            case "warning" -> snackBarPane.getStyleClass().setAll("alert-warning", "alert");
            case "success" -> snackBarPane.getStyleClass().setAll("alert-success", "alert");
            case "danger" -> snackBarPane.getStyleClass().setAll("alert-danger", "alert");
            case "info" -> snackBarPane.getStyleClass().setAll("alert-info", "alert");
        }

        snackBarPane.setVisible(true);
        snackBarLabel.setText(message);
        if(autoHide){
            Timeline timer = new Timeline(
                    new KeyFrame(Duration.seconds(2), (ActionEvent aEvent)  -> hideSnackBar(snackBarPane))
            );
            timer.play();
        }
    }

    public void hideSnackBar(StackPane snackBarPane){
        snackBarPane.setVisible(false);
    }


}
