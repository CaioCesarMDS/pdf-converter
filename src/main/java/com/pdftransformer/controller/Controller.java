package com.pdftransformer.controller;

import javafx.fxml.FXML;

import java.io.File;

import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;

public class Controller {

    public ComboBox formats;
    private File file;

    @FXML
    private void searchFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file");

        file = fileChooser.showOpenDialog(null);

    }
}
