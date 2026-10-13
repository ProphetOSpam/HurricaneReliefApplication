package com.one_half_men.app.db;

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
		return Set.of();
	}
}
