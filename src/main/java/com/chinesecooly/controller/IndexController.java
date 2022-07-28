package com.chinesecooly.controller;

import com.chinesecooly.model.Calculator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanStringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class IndexController implements Initializable {

    @FXML
    private Parent display;
    @FXML
    private Parent buttons;
    @FXML
    private DisplayController displayController;
    @FXML
    private ButtonsController buttonsController;
    private StringBuilder expr;
    private StringProperty disExpr;
    private JavaBeanStringProperty model;
    private Calculator calculator;

    public IndexController() {
        expr=new StringBuilder();
        disExpr=new SimpleStringProperty("0");
        calculator=Calculator.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            model = JavaBeanStringPropertyBuilder.create().bean(calculator).name("expression").build();
            buttonsController.getButtonInput().addListener((de,ov,nv)->{
                changeHandler(nv);
            });
            displayController.getTf1().textProperty().bind(model);
            displayController.getTf2().textProperty().bind(disExpr);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void changeHandler(String text){
        switch (text) {
            case "C":
                disExpr.set("0");
                expr.deleteCharAt(expr.length() - 1);
                break;
            case "CE":
                disExpr.set("0");
                expr.delete(0, expr.length());
                break;
            case "=":
                model.set(expr.toString());
                expr.delete(0, expr.length());
                Map<String, Object> result = calculator.calculate();
                String calculateResult = (String) result.get("calculateResult");
                expr.append(calculateResult);
                disExpr.set(calculateResult);
                break;
            case "+": case "-": case "*": case "/":
                expr.append(text);
                break;
            default:
                disExpr.set(text);
                expr.append(text);
        }
    }
}
