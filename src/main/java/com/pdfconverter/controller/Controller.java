package com.pdfconverter.controller;

import com.pdfconverter.exceptions.FileNotSelectedException;
import com.pdfconverter.exceptions.InvalidFormatException;
import com.pdfconverter.exceptions.ConversionFailedException;

import com.pdfconverter.service.PDFConverter;

import java.io.File;
import java.nio.file.*;

import javafx.fxml.FXML;
import javafx.animation.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.util.*;

public class Controller {
    private Stage stage;
    private File file;
    private File directory;

    @FXML
    private ComboBox<String> formats;
    @FXML
    private TextField fileInput;
    @FXML
    private TextField folderInput;

    public void initialize() {
        formats.getItems().addAll("PDF", "DOCX", "JPG", "PNG");

        String userHome = System.getProperty("user.home");
        directory = new File(userHome, File.separator + "Downloads");
        folderInput.setText(directory.toString());
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
            directory = selectedDirectory;
            folderInput.setText(selectedDirectory.getAbsolutePath());
            return;
        }

        showPopupNotification("Folder not found!", 2);
    }

    @FXML
    public void handleFileConversion() {
        try {
            if (file == null) {
                throw new FileNotSelectedException("No file selected for conversion.");
            }

            String format = formats.getSelectionModel().getSelectedItem();

            if (format == null) {
                throw new InvalidFormatException("No format selected for conversion.");
            }

            String formatLower = format.toLowerCase();
            String fileExtension = getExtension(file);

            if (fileExtension.equals(formatLower)) {
                showPopupNotification("You are already in the desired format!", 2);
                return;
            }

            convertFile(file, directory.toPath(), formatLower, fileExtension);

        } catch (FileNotSelectedException | InvalidFormatException e) {
            showPopupNotification(e.getMessage(), 4);
        }
    }

    private void convertFile(File file, Path directory, String format, String fileExtension) {
        try {
            if (fileExtension.equals("pdf")) {
                if (format.equals("png") || format.equals("jpg")) {
                    PDFConverter.toImage(file, directory, format);
                } else if (format.equals("docx")) {
                    PDFConverter.toDocx();
                }
                else {
                    showPopupNotification("File format not supported!", 2);
                }
            } else if (format.equals("docx")) {

            }

        } catch (ConversionFailedException e) {
            showPopupNotification(e.getMessage(), 4);
        }
    }

    private String getExtension(File file) {
        String fileName = file.getName();

        int lastIndex = fileName.lastIndexOf(".");

        if (lastIndex == -1) {
            return "";
        }

        return fileName.substring(lastIndex + 1);
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
}
