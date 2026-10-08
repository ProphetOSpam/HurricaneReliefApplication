package com.one_half_men.app.user;

public enum Role {
    CITIZEN("Citizen"),
    EMS("Emergency Services");

    private String string;

    private Role(String string) {
        this.string = string;
    }
}
