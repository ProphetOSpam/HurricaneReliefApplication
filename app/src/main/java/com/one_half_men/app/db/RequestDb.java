package com.one_half_men.app.db;

import java.util.Set;

import com.one_half_men.app.json.JsonbInstance;
import com.one_half_men.app.request.Request;
import com.one_half_men.app.request.RequestQuery;

import io.avaje.jsonb.Json;
import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Types;
import lombok.EqualsAndHashCode;

// needs `Request` to be implemented first
// tracking: https://github.com/ProphetOSpam/HurricaneReliefApplication/issues/11

@Json
@EqualsAndHashCode(callSuper = true)
public class RequestDb extends Database<Request> implements Searchable<Request, RequestQuery> {
    public static JsonType<BaseDatabase<Request>> type() {
        return JsonbInstance.instance.type(Types.newParameterizedType(Database.class, Request.class));
    }

    @Override
    public Set<Key<Request>> search(RequestQuery query) {
        return Set.of();
    }
}
