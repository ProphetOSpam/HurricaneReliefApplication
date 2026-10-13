package com.one_half_men.app.db;

import java.util.Map;
import java.util.Set;

import com.one_half_men.app.shelter.Shelter;
import com.one_half_men.app.shelter.ShelterQuery;

public class ShelterDb extends Database<Shelter> implements Searchable<Shelter, ShelterQuery> {
	public ShelterDb(Map<Key<Shelter>, Shelter> entries) {
		super(entries);
	}

	@Override
	public Set<Key<Shelter>> search(ShelterQuery query) {
		return Set.of();
	}
}
