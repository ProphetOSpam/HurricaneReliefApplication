package com.one_half_men.app;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.one_half_men.app.error.AccountCreationError;
import com.one_half_men.app.error.AccountLoginError;
import com.one_half_men.app.hurricane_application.HurricaneApplication;

public class ApplicationLoginTest {
    private static HurricaneApplication app = HurricaneApplication.mockApplication();

    @BeforeAll
    static void setup() throws AccountCreationError {
        app.createAccount("Bob", "0123456789");
    }

    @Test
    void createAccount() throws AccountCreationError {
        app.createAccount("Alice", "0123456789");
    }

    @Test
    void failCreateAccount() {
        assertThrows(AccountCreationError.PasswordInvalid.class, () -> {
            app.createAccount("Clara", "");
        });

        assertThrows(AccountCreationError.UsernameInvalid.class, () -> {
            app.createAccount("", "0123456789");
        });

        assertThrows(AccountCreationError.UsernameTaken.class, () -> {
            app.createAccount("Bob", "0123456789");
        });
    }

    @Test
    void login() throws AccountLoginError {
        app.login("Bob", "0123456789");
    }

    @Test
    void loginWrongCredentials() {
        assertThrows(AccountLoginError.NoAccountFoundError.class, () -> app.login("", ""));

        assertThrows(AccountLoginError.WrongPasswordError.class, () -> app.login("Bob", ""));
    }
}
