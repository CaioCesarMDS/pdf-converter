package com.pdfconverter.controller;

import com.pdfconverter.exceptions.UnsuccessfulConversion;
import javafx.fxml.FXML;
import javafx.animation.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.*;

import java.io.File;

public class Controller {
    @FXML
    private StackPane popupNotification;
    @FXML
    private ComboBox<String> formats;
    @FXML
    private TextField fileInput;
    @FXML
    private TextField folderInput;

    private File file;
    private Stage stage;

    public void initialize() {
        formats.getItems().addAll("PDF", "JPG", "PNG");
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void searchFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file");

        file = fileChooser.showOpenDialog(null);

        if (file != null) {
            fileInput.setText(file.getAbsolutePath());
            return;
        }

        showPopupNotification("File not found!", 2);
    }

    @FXML
    public void searchFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a folder");
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            folderInput.setText(selectedDirectory.getAbsolutePath());
            return;
        }

        showPopupNotification("Folder not found!", 2);
    }

    @FXML
    private void convertFile() throws UnsuccessfulConversion {
        String format = formats.getSelectionModel().getSelectedItem();

        String formatLower = format.toLowerCase();

        if (!getExtension(file).isEmpty() && !getExtension(file).equals(formatLower)) {
            try {
                switch (formatLower) {
                    case "pdf":

                        break;
                    case "jpg":

                        break;
                    case "png":
                }
            } catch (Exception e) {
                throw new UnsuccessfulConversion(e.getMessage());
            }
            return;
        }

        showPopupNotification("You are already in the desired format!", 2);
    }

    private void showPopupNotification(String message, int seconds) {
        Popup popup = new Popup();
        Label popupMessage = new Label(message);
        popup.getContent().add(popupMessage);
        popupMessage.getStyleClass().add("popup-notification");
        popup.setAutoHide(true);


        double x = stage.getX() + stage.getWidth() / 2 - 135;
        double y = stage.getY() + stage.getHeight() / 10;
        popup.show(stage, x, y);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(seconds), e -> popup.hide()));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private String getExtension(File file) {
        String fileName = file.getName();

        int lastIndex = fileName.lastIndexOf(".");

        if (lastIndex == -1) {
            return "";
        }

        return fileName.substring(lastIndex + 1);
    }
}
