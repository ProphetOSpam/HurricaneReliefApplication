package com.one_half_men.app.page;

import com.one_half_men.app.hurricane_application.HurricaneApplication;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Feed {
    private static final HurricaneApplication APP = HurricaneApplication.getInstance();

    @FXML
    private Label label;

    @FXML
    public void initialize() {
        String username = APP.getLoggedInUser().get().getUsername();
        label.setText(String.format("Username: %s", username));
    }
}
