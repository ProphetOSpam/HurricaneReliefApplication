package com.one_half_men.app.user;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Account {
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 48;

    private @Getter String username;
    private @Getter AuthenticationInfo auth;

    public static boolean isUsernameValid(String username) {
        return StandardCharsets.US_ASCII.newEncoder().canEncode(username);
    }

    public static boolean isPasswordValid(String password) {
        return password.length() >= MIN_PASSWORD_LENGTH
                && password.length() <= MAX_PASSWORD_LENGTH
                && StandardCharsets.US_ASCII.newEncoder().canEncode(password);
    }

    public boolean tryPassword(String password) {
        if (!isPasswordValid(password))
            return false;

        MessageDigest digest = AuthenticationInfo.digest;

        digest.update(auth.getSalt());
        digest.update(password.getBytes(StandardCharsets.US_ASCII));

        byte[] hash = digest.digest();

        return Arrays.equals(auth.getHash(), hash);
    }
}
