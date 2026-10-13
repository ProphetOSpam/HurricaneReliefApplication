package com.one_half_men.app.db;

import java.util.UUID;

import io.avaje.jsonb.Json;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Json
@EqualsAndHashCode
@AllArgsConstructor
public class Key<T> {
    private @Getter(AccessLevel.PACKAGE) final UUID uuid;

    public static <T> Key<T> random() {
        return new Key<T>(UUID.randomUUID());
    }

    public static <T> Key<T> fromString(String name) {
        return new Key<>(UUID.fromString(name));
    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}
