package com.one_half_men.app.error;

/** Thrown when an issue occurs during the account creation progress */
public sealed class AccountCreationError extends Throwable {
    public static final class UsernameTaken extends AccountCreationError {}
    public static final class UsernameInvalid extends AccountCreationError {}
    public static final class PasswordInvalid extends AccountCreationError {}
}
