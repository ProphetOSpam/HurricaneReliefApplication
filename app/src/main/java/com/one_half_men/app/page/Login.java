package com.one_half_men.app.page;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Login {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private void login() {
        System.out.println("Create");
        System.out.println(username.getText());
        System.out.println(password.getText());
    }

    @FXML
    private void createAccount() {
        System.out.println("Create");
        System.out.println(username.getText());
        System.out.println(password.getText());
    }
}
