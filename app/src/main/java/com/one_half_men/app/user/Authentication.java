package com.one_half_men.app.user;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;

import com.one_half_men.app.hurricane_application.HurricaneApplication;

import io.avaje.jsonb.Json;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Authentication {
    private static final Charset ASCII = StandardCharsets.US_ASCII;
    private static final CharsetEncoder ASCII_ENCODER = ASCII.newEncoder();

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Username {
        private static final int MIN_LENGTH = 2;
        private static final int MAX_LENGTH = 64;

        public static boolean isValid(String username) {
            return username.length() >= MIN_LENGTH
                    && username.length() <= MAX_LENGTH
                    && ASCII_ENCODER.canEncode(username);
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Password {
        private static final int MIN_LENGTH = 8;
        private static final int MAX_LENGTH = 48;

        public static boolean isValid(String password) {
            return password.length() >= MIN_LENGTH
                    && password.length() <= MAX_LENGTH
                    && ASCII_ENCODER.canEncode(password);
        }
    }

    // TODO: consider replacing with a more robust, secure solution
    @AllArgsConstructor
    @Getter
    @Json
    public static class AuthKey {
        /** 128-bit salt */
        private final byte[] salt;

        /** SHA-512 Hash of {Salt + Password} */
        private final byte[] hash;

        private static final MessageDigest DIGEST;

        static {
            try {
                DIGEST = MessageDigest.getInstance("SHA-512");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        };

        /**
         * Creates a new instance with a random salt.
         *
         * @param password The password to use for hashing
         *
         * @return New key, empty if passsword in invalid
         */
        public static Optional<Authentication.AuthKey> fromPassword(String password) {
            if (!Password.isValid(password))
                return Optional.empty();

            byte[] salt = new byte[16];
            HurricaneApplication.RANDOM.nextBytes(salt);

            DIGEST.update(salt);
            DIGEST.update(password.getBytes(ASCII));

            byte[] hash = DIGEST.digest();

            return Optional.of(new Authentication.AuthKey(salt, hash));
        }

        /**
         * Checks if the given password matches the key
         *
         * @param password The password to check
         */
        public boolean checkPassword(String password) {
            if (!Password.isValid(password))
                return false;

            MessageDigest digest = Authentication.AuthKey.DIGEST;

            digest.update(salt);
            digest.update(password.getBytes(ASCII));

            byte[] newHash = digest.digest();

            return Arrays.equals(hash, newHash);
        }
    }
}
