package com.one_half_men.app.user;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.one_half_men.app.hurricane_application.HurricaneApplication;

import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO: consider replacing with a more robust, secure solution
@AllArgsConstructor
@Getter
public class AuthenticationInfo {
    public static final MessageDigest digest;
    static {
        try {
            digest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    };

    /** 128-bit salt */
    private byte[] salt;

    /** SHA-512 Hash of {Salt + Password} */
    private byte[] hash;

    /**
     * Creates a new instance with a random salt,
     * does not do any password validation
     */
    public static AuthenticationInfo fromPassword(String password) {
        byte[] salt = new byte[16];
        HurricaneApplication.RANDOM.nextBytes(salt);

        digest.update(salt);
        digest.update(password.getBytes(StandardCharsets.US_ASCII));

        byte[] hash = digest.digest();

        return new AuthenticationInfo(salt, hash);
    }
}
