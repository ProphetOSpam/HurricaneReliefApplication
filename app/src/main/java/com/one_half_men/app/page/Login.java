package com.one_half_men.app.page;

import com.one_half_men.app.App;
import com.one_half_men.app.error.AccountCreationError;
import com.one_half_men.app.error.AccountLoginError;
import com.one_half_men.app.hurricane_application.HurricaneApplication;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Login {
    private static final HurricaneApplication APP = HurricaneApplication.getInstance();

    @FXML
    private MFXTextField username;

    @FXML
    private MFXPasswordField password;

    @FXML
    private void onPasswordFieldKeyPressed(KeyEvent key) {
        if (key.getCode() == KeyCode.ENTER)
            login();
    }

    @FXML
    private void login() {
        try {
            APP.login(username.getText(), password.getText());
            System.out.println(APP.getLoggedInUser().get().getUsername());
        } catch (AccountLoginError e) {
            System.err.println(e);
            return;
        }

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
