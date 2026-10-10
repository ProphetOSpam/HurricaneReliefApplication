package com.one_half_men.app.db;

import java.util.Map;
import java.util.function.Consumer;

import lombok.AccessLevel;
import lombok.Getter;

public abstract class Database<V> {
    @Getter(AccessLevel.PACKAGE)
    protected final Map<Key<V>, V> entries;

    public Database(Map<Key<V>, V> entries) {
        this.entries = entries;
    }

    public Key<V> create(V element) {
        Key<V> key = Key.random();
        entries.put(key, element);

        return key;
    }

    public V get(Key<V> key) {
        return entries.get(key);
    }

    public V remove(Key<V> key) {
        return entries.remove(key);
    }

    // TODO: figure out how we wanna handle nulls
    public void edit(Key<V> key, Consumer<V> consumer) {
        V element = get(key);
        consumer.accept(element);
    }
}
