package com.one_half_men.app.db;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import com.one_half_men.annotations.IQuery;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class Database<V> implements Searchable<V, IQuery<V>> {
    public BaseDatabase<V> base;

    Database() {
        this.base = new BaseDatabase<>();
    }

    Database(BaseDatabase<V> base) {
        this.base = base;
    }

    public Key<V> create(V element) {
        Key<V> key = Key.random();
        base.entries.put(key, element);

        return key;
    }

    public V get(Key<V> key) {
        return (V) base.entries.get(key);
    }

    public V remove(Key<V> key) {
        return (V) base.entries.remove(key);
    }

    // TODO: figure out how we wanna handle nulls
    public void edit(Key<V> key, Consumer<V> consumer) {
        V element = get(key);
        consumer.accept(element);
    }

    @Override
    public Set<Key<V>> search(IQuery<V> query) {
        Set<Key<V>> result = new HashSet<Key<V>>();
        for (Key<V> key : base.entries.keySet()) {
            V obj = base.entries.get(key);

            if (query.matches(obj)) {
                result.add(key);
            }
        }
        return result;
    }
}
