package com.one_half_men.app.db;

import java.io.File;
import java.util.Set;

import com.one_half_men.app.json.Constants;
import com.one_half_men.app.json.DataReader;
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
    public RequestDb(BaseDatabase<Request> base) {
        super(base);
    }

    public static final RequestDb instance = DataReader.readDatabase(type(), RequestDb::new,
            new File(Constants.SHELTER_DB));

    public static JsonType<BaseDatabase<Request>> type() {
        return JsonbInstance.instance.type(Types.newParameterizedType(Database.class, Request.class));
    }

    @Override
    public Set<Key<Request>> search(RequestQuery query) {
        return Set.of();
    }
}
// public class RequestDb extends Database<Request> implements Searchable<Request, RequestQuery> {
// 	public RequestDb(Map<Key<Request>, Request> entries) {
// 		super(entries);
// 	}
//
// 	@Override
// 	public Set<Key<Request>> search(RequestQuery query) {
// 		Set<Key<Request>> result = new HashSet<Key<Request>>();
//          for (Key<Request> key : entries.keySet()) {
//              Request request = entries.get(key);
//              if (query.equals(request)) {
//                  result.add(key);
//              }
//          }
//      return result;
// 	}
// }
