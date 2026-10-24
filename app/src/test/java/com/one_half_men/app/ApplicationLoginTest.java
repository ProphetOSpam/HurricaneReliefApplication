package com.one_half_men.app;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.one_half_men.app.db.UserDb;
import com.one_half_men.app.error.AccountCreationError;
import com.one_half_men.app.error.AccountLoginError;
import com.one_half_men.app.hurricane_application.HurricaneApplication;
import com.one_half_men.app.user.Authentication;
import com.one_half_men.app.user.User;

public class ApplicationLoginTest {
    static UserDb db = new UserDb();

    @BeforeAll
    static void setup() {
        db.create(User.builder()
                .username("Bob")
                .login(Authentication.Key.fromPassword("0123456789").get())
                .build());
    }

    @Test
    void createAccount() throws AccountCreationError {
        HurricaneApplication.getInstance().createAccount("Alice", "0123456789", db);
    }

    @Test
    void failCreateAccount() {
        assertThrows(AccountCreationError.PasswordInvalid.class, () -> {
            HurricaneApplication.getInstance().createAccount("Clara", "", db);
        });

        assertThrows(AccountCreationError.UsernameInvalid.class, () -> {
            HurricaneApplication.getInstance().createAccount("", "0123456789", db);
        });

        assertThrows(AccountCreationError.UsernameTaken.class, () -> {
            HurricaneApplication.getInstance().createAccount("Bob", "0123456789", db);
        });
    }

    @Test
    void login() throws AccountLoginError {
        HurricaneApplication.getInstance().login("Bob", "0123456789", db);
    }

    @Test
    void loginWrongCredentials() {
        assertThrows(AccountLoginError.NoAccountFoundError.class,
                () -> HurricaneApplication.getInstance().login("", "", db));

        assertThrows(AccountLoginError.WrongPasswordError.class,
                () -> HurricaneApplication.getInstance().login("Bob", "", db));
    }
}
