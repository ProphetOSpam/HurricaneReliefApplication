package com.one_half_men.app.page;

import com.one_half_men.app.error.AccountCreationError;
import com.one_half_men.app.error.AccountLoginError;
import com.one_half_men.app.hurricane_application.HurricaneApplication;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Login {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private void login() {
        var app = HurricaneApplication.getInstance();

        try {
            app.login(username.getText(), password.getText());
            System.out.println("Logged in!");
        } catch (AccountLoginError e) {
            System.err.println(e);
        }
    }

    @FXML
    private void createAccount() {
        var app = HurricaneApplication.getInstance();

        try {
            app.createAccount(username.getText(), password.getText());
            System.out.println("Created the account");
        } catch (AccountCreationError e) {
            System.err.println(e);
            return;
        }
    }
}
