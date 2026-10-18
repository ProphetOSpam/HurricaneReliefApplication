package com.one_half_men.app.error;

public sealed class AccountCreationError extends Throwable {
    public static final class UsernameTaken extends AccountCreationError {}
    public static final class PasswordInvalid extends AccountCreationError {}
}
