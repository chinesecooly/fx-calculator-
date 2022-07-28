package com.chinesecooly.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import lombok.Data;

@Data
public class MenuBarController {

    @FXML
    private ButtonBar buttonBar;
    private double initialX;
    private double initialY;

    @FXML
    private void closeHandler(){
        Platform.exit();
    }

    @FXML
    private void minimizeHandler(){
        buttonBar.getScene().getWindow().hide();
    }

    @FXML
    private void mousePressedHandler(MouseEvent me){
        if (me.getButton() != MouseButton.MIDDLE) {
            initialX = me.getSceneX();
            initialY = me.getSceneY();
        }
    }

    @FXML
    private void mouseDraggedHandler(MouseEvent me){
        if (me.getButton() != MouseButton.MIDDLE) {
            buttonBar.getScene().getWindow().setX(me.getScreenX() - initialX);
            buttonBar.getScene().getWindow().setY(me.getScreenY() - initialY);
        }
    }
}
