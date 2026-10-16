package com.one_half_men.app.json;

import com.one_half_men.app.db.BaseDatabase;

import io.avaje.jsonb.Jsonb;

public class JsonbInstance {
    public static final Jsonb instance = Jsonb.builder()
            .add(BaseDatabase.BaseDatabaseJsonAdapter.FACTORY)
            .build();
}
