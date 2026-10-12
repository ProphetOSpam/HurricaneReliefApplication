package com.one_half_men.app.user;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    CITIZEN("Citizen"),
    EMS("Emergency Services");

    public final String string;
}
