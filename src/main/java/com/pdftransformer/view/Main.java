package com.pdftransformer.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/layout.fxml"));
        Scene scene = new Scene(root, 900, 700);

        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.getIcons().add(new Image("/images/pdf-icon.png"));
        stage.setTitle("PDF Transformer");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}