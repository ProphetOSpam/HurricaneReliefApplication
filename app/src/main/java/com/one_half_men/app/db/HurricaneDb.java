package com.one_half_men.app.db;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.one_half_men.app.hurricane.Hurricane;
import com.one_half_men.app.hurricane.HurricaneQuery;

public class HurricaneDb extends Database<Hurricane> implements Searchable<Hurricane, HurricaneQuery> {
	public HurricaneDb(Map<Key<Hurricane>, Hurricane> entries) {
		super(entries);
	}

	@Override
	public Set<Key<Hurricane>> search(HurricaneQuery query) {
		Set<Key<Hurricane>> result = new HashSet<Key<Hurricane>>();
        for (Key<Hurricane> key : entries.keySet()) {
            Hurricane hurricane = entries.get(key);
            if (query.equals(hurricane)) {
                result.add(key);
            }
        }
        return result;
	}
}
