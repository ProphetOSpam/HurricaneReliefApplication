package com.one_half_men.app.controllers;

import java.io.IOException;

import com.one_half_men.app.App;

import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
