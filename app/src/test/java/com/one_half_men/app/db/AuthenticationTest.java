package com.one_half_men.app.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.one_half_men.app.user.Authentication;

public class AuthenticationTest {
    @Test
    public void passwordIsValid() {
        String[] validPassswords = new String[] {
                "abcdefghi",
                "password",
                "BIhPaseord"
        };

        String[] invalidPassswords = new String[] {
                "short",
                "ÑÓT_ASCÍÍ",
                "LOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOONG"
        };

        for (String valid : validPassswords) {
            assertTrue(Authentication.Password.isValid(valid));
        }

        for (String invalid : invalidPassswords) {
            assertFalse(Authentication.Password.isValid(invalid));
        }
    }

    @Test
    public void keyCreationAndChecking() {
        String wrongPassword = "wrongPassword";
        String[] correctPasswords = new String[] { "abcdefghi", "password", "BIhPaseord" };

        for (String correctPassword : correctPasswords) {
            Authentication.AuthKey key = Authentication.AuthKey.fromPassword(correctPassword).get();
            assertFalse(key.checkPassword(wrongPassword));
            assertTrue(key.checkPassword(correctPassword));
        }
    }
}
