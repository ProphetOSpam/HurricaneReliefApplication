package com.one_half_men.app.page;

import java.net.URL;
import java.util.ResourceBundle;

import com.one_half_men.app.hurricane_application.HurricaneApplication;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class Feed implements Initializable {
    private static final HurricaneApplication APP = HurricaneApplication.getInstance();

    @FXML
    private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label.setText(APP.getLoggedInUser().get().getUsername());
    }
}
