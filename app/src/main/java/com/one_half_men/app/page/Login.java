package com.one_half_men.app.page;

import com.one_half_men.app.error.AccountCreationError;
import com.one_half_men.app.error.AccountLoginError;
import com.one_half_men.app.hurricane_application.HurricaneApplication;
import com.one_half_men.app.App;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Login {
    private static final HurricaneApplication APP = HurricaneApplication.getInstance();

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private void login() {
        try {
            APP.login(username.getText(), password.getText());
        } catch (AccountLoginError e) {
            System.err.println(e);
            return;
        }

        System.out.println("Logged in!");
        App.setRoot("Feed");
    }

    @FXML
    private void createAccount() {
        try {
            APP.createAccount(username.getText(), password.getText());
        } catch (AccountCreationError e) {
            System.err.println(e);
            return;
        }

        System.out.println("Created the account");
    }
}
