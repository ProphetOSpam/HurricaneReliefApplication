package com.one_half_men.app.db;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

import com.one_half_men.annotations.IQuery;
import com.one_half_men.app.json.Constants;
import com.one_half_men.app.json.DataReader;
import com.one_half_men.app.json.JsonbInstance;
import com.one_half_men.app.location.MapTools;
import com.one_half_men.app.shelter.Resource;
import com.one_half_men.app.shelter.Shelter;
import com.one_half_men.app.shelter.ShelterQuery;

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

    public Set<Key<Shelter>> search(ShelterQuery query) {
        // SortedSet<Key<Shelter>> result = new TreeSet<Key<Shelter>>(
        // (one, two) -> get(one).getLocation().distanceTo(query) >
        // get(two).getLocation().distanceTo(query));

        Function<Shelter, Double> drivingTime = shelter -> {
            try {
                return MapTools.getDrivingTime(query.location.get().coordinates(), shelter.getLocation().coordinates());
            } catch (IOException | InterruptedException e) {
                System.err.println(e);
            }

            return Double.MAX_VALUE;
        };

        SortedSet<Key<Shelter>> result = new TreeSet<Key<Shelter>>(
                (one, two) -> (int) (drivingTime.apply(get(one)) - drivingTime.apply(get(two))));

        for (Key<Shelter> key : base.entries.keySet()) {
            Shelter shelter = base.entries.get(key);

            if (query.matches(shelter)) {
                result.add(key);
                continue;
            }

            if (query.resources.isPresent()) {
                boolean add = true;

                for (Resource resource : query.resources.get()) {
                    if (resource instanceof Resource.Capacity capacity) {
                        if (capacity.remaining() <= ((Resource.Capacity) shelter.getResources().stream()
                                .filter(res -> capacity.getClass() == res.getClass()).findFirst().get()).remaining()) {
                            continue;
                        }
                    }

                    if (shelter.getResources().contains(resource)) {
                        continue;
                    }

                    add = false;
                    break;
                }

                if (add) {
                    result.add(key);
                }
            }
        }
        return result;
    }
}
