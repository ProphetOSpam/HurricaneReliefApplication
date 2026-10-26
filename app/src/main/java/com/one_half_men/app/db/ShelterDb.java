package com.one_half_men.app.db;

import java.io.File;

import com.one_half_men.app.json.Constants;
import com.one_half_men.app.json.DataReader;
import com.one_half_men.app.json.JsonbInstance;
import com.one_half_men.app.shelter.Shelter;

import io.avaje.jsonb.Json;
import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Types;
import lombok.EqualsAndHashCode;

@Json
@EqualsAndHashCode(callSuper = true)
public class ShelterDb extends Database<Shelter> {
    public ShelterDb() {
        super();
    }

    public ShelterDb(BaseDatabase<Shelter> base) {
        super(base);
    }

    private static ShelterDb instance;

    public static ShelterDb getInstance() {
        if (instance == null) {
            instance = DataReader.readDatabase(type(), ShelterDb::new, new File(Constants.SHELTER_DB));
        }

        return instance;
    }

    public static JsonType<BaseDatabase<Shelter>> type() {
        return JsonbInstance.instance.type(Types.newParameterizedType(BaseDatabase.class, Shelter.class));
    }
}
