package com.chinesecooly;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("index"));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.setHeight(490);
        stage.setWidth(330);
        Image image = new Image(loadResourceAsStream("img/calculator.png"));
        stage.getIcons().add(image);
        stage.show();
    }

    static Parent loadFXML(String fxml) throws IOException {
        String prefix="fxml/";
        String suffix=".fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(loadResourceAsURL(prefix+fxml+suffix));
        return fxmlLoader.load();
    }

    static InputStream loadResourceAsStream(String source) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(source);
    }

    static URL loadResourceAsURL(String source){
        return ClassLoader.getSystemClassLoader().getResource(source);
    }

    public static void main(String[] args) {
        launch();
    }

}