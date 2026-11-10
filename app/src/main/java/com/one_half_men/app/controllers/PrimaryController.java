package com.one_half_men.app.controllers;

import java.io.IOException;

import com.one_half_men.app.App;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
