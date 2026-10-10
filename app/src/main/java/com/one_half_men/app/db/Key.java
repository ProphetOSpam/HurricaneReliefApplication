package com.one_half_men.app.db;

import java.util.UUID;

public class Key<T> {
    private final UUID uuid;

    private Key(UUID uuid) {
        this.uuid = uuid;
    }

    public static <T> Key<T> random() {
        return new Key<T>(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof Key<?> other)
                && uuid.equals(other.uuid);
    }
}
