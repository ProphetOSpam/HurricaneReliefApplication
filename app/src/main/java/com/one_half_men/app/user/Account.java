package com.one_half_men.app.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Account {
    private String username;
    private Authentication.Key login;
}
