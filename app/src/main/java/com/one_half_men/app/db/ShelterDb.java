package com.one_half_men.app.db;

import java.util.Set;

import com.one_half_men.app.json.JsonbInstance;
import com.one_half_men.app.shelter.Shelter;
import com.one_half_men.app.shelter.ShelterQuery;

import io.avaje.jsonb.Json;
import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Types;
import lombok.EqualsAndHashCode;

@Json
@EqualsAndHashCode(callSuper = true)
public class ShelterDb extends Database<Shelter> implements Searchable<Shelter, ShelterQuery> {
	// public ShelterDb(Map<Key<Shelter>, Shelter> entries) {
	// super(entries);
	// }

	public static JsonType<BaseDatabase<Shelter>> type() {
		return JsonbInstance.instance.type(Types.newParameterizedType(Database.class, Shelter.class));
	}

	@Override
	public Set<Key<Shelter>> search(ShelterQuery query) {
		return Set.of();
	}
}
