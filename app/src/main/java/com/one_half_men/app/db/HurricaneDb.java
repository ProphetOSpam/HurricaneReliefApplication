package com.one_half_men.app.db;

import java.io.File;

import com.one_half_men.app.hurricane.Hurricane;
import com.one_half_men.app.json.Constants;
import com.one_half_men.app.json.DataReader;
import com.one_half_men.app.json.JsonbInstance;

import io.avaje.jsonb.Json;
import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Types;
import lombok.EqualsAndHashCode;

@Json
@EqualsAndHashCode(callSuper = true)
public class HurricaneDb extends Database<Hurricane> {
	public HurricaneDb() {
		super();
	}

	public HurricaneDb(BaseDatabase<Hurricane> base) {
		super(base);
	}

	private static HurricaneDb instance;

	public static HurricaneDb getInstance() {
		if (instance == null) {
			instance = DataReader.readDatabase(type(), HurricaneDb::new, new File(Constants.SHELTER_DB));
		}

		return instance;
	}

	public static JsonType<BaseDatabase<Hurricane>> type() {
		return JsonbInstance.instance.type(Types.newParameterizedType(BaseDatabase.class, Hurricane.class));
	}
}
