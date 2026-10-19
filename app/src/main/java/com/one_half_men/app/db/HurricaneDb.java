package com.one_half_men.app.db;

import java.io.File;
import java.util.Set;

import com.one_half_men.app.hurricane.Hurricane;
import com.one_half_men.app.hurricane.HurricaneQuery;
import com.one_half_men.app.json.Constants;
import com.one_half_men.app.json.DataReader;
import com.one_half_men.app.json.JsonbInstance;
import com.one_half_men.app.request.Request;

import io.avaje.jsonb.Json;
import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Types;
import lombok.EqualsAndHashCode;

@Json
@EqualsAndHashCode(callSuper = true)
public class HurricaneDb extends Database<Hurricane> implements Searchable<Hurricane, HurricaneQuery> {
	public HurricaneDb(BaseDatabase<Hurricane> base) {
		super(base);
	}

	public static final HurricaneDb instance = DataReader.readDatabase(type(), HurricaneDb::new,
			new File(Constants.SHELTER_DB));

	public static JsonType<BaseDatabase<Hurricane>> type() {
		return JsonbInstance.instance.type(Types.newParameterizedType(Database.class, Request.class));
	}

	@Override
	public Set<Key<Hurricane>> search(HurricaneQuery query) {
		return Set.of();
	}
}
