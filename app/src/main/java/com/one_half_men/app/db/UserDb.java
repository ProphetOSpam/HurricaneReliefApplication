package com.one_half_men.app.db;

import java.util.Map;

import com.one_half_men.app.user.User;

import io.avaje.jsonb.Json;

// @Json
public class UserDb extends Database<User> {
    public UserDb(Map<Key<User>, User> entries) {
        super(entries);
    }
}
