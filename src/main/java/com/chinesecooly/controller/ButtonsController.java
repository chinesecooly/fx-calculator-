package com.chinesecooly.controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Data;
import java.net.URL;
import java.util.ResourceBundle;

@Data
public class ButtonsController implements Initializable {

    @FXML
    private VBox buttons;
    private StringProperty buttonInput;

    public ButtonsController() {
        buttonInput= new SimpleStringProperty();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Node child : buttons.getChildren()) {
            if (child instanceof HBox) {
                for (Node node : ((HBox) child).getChildren()) {
                    if (node instanceof Button) {
                        ((Button) node).setOnAction((event) -> {
                            buttonInput.set( ((Button) node).getText());
                        });
                    }
                }
            }
        }
    }
}
