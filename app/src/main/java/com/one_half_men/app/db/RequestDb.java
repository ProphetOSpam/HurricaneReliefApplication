package com.one_half_men.app.db;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.one_half_men.app.json.Constants;
import com.one_half_men.app.json.DataReader;
import com.one_half_men.app.json.JsonbInstance;
import com.one_half_men.app.request.Request;
import com.one_half_men.app.request.RequestQuery;
import com.one_half_men.app.shelter.Resource;
import com.one_half_men.app.user.Specialty;

import io.avaje.jsonb.Json;
import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Types;
import lombok.EqualsAndHashCode;

@Json
@EqualsAndHashCode(callSuper = true)
public class RequestDb extends Database<Request> {
    public RequestDb() {
        super();
    }

    public RequestDb(BaseDatabase<Request> base) {
        super(base);
    }

    private static RequestDb instance;

    public static RequestDb getInstance() {
        if (instance == null) {
            instance = DataReader.readDatabase(type(), RequestDb::new, new File(Constants.SHELTER_DB));
        }

        return instance;
    }

    public static JsonType<BaseDatabase<Request>> type() {
        return JsonbInstance.instance.type(Types.newParameterizedType(BaseDatabase.class, Request.class));
    }

    public Set<Key<Request>> search(RequestQuery query) {
        Set<Key<Request>> result = new HashSet<Key<Request>>();

        for (Key<Request> key : base.entries.keySet()) {
            Request request = base.entries.get(key);

            if (query.matches(request)) {
                result.add(key);
                continue;
            }

            if (query.requiredResources.isPresent()) {
                boolean add = true;

                for (Resource resource : query.requiredResources.get()) {
                    if (resource instanceof Resource.Capacity capacity) {
                        if (capacity.remaining() <= ((Resource.Capacity) request.getRequiredResources().stream()
                                .filter(res -> capacity.getClass() == res.getClass()).findFirst().get()).remaining()) {
                            continue;
                        }
                    }

                    if (request.getRequiredResources().contains(resource)) {
                        continue;
                    }

                    add = false;
                    break;
                }

                if (add) {
                    result.add(key);
                }
            }

            if (query.requiredSpecialties.isPresent()) {
                if (request.getRequiredSpecialties().containsAll(query.requiredSpecialties.get())) {
                    result.add(key);
                }
            }
        }
        return result;
    }
}
