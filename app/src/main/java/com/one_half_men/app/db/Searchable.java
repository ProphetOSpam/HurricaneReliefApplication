package com.one_half_men.app.db;

import java.util.Set;

public interface Searchable<V, Q> {
    public Set<Key<V>> search(Q query);
}
