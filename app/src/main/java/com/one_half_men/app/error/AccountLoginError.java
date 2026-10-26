package com.one_half_men.app.error;

/** Thrown when an issue occurs during the log in progress */
public sealed class AccountLoginError extends Throwable {
    public static final class NoAccountFoundError extends AccountLoginError {}
    public static final class WrongPasswordError extends AccountLoginError {}
}
