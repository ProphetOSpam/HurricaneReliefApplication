package com.one_half_men.app.db;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.one_half_men.app.json.Constants;
import com.one_half_men.app.json.DataReader;
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
    public ShelterDb(BaseDatabase<Shelter> base) {
        super(base);
    }

    public static final ShelterDb instance = DataReader.readDatabase(type(), ShelterDb::new,
            new File(Constants.SHELTER_DB));

    public static JsonType<BaseDatabase<Shelter>> type() {
        return JsonbInstance.instance.type(Types.newParameterizedType(Database.class, Shelter.class));
    }

    @Override
    public Set<Key<Shelter>> search(ShelterQuery query) {
        Set<Key<Shelter>> result = new HashSet<Key<Shelter>>();
        for (Key<Shelter> key : base.entries.keySet()) {
            Shelter shelter = base.entries.get(key);
            if (query.equals(shelter)) {
                result.add(key);
            }
        }
        return result;
    }
}
