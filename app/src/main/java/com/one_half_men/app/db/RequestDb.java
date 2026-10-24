package com.one_half_men.app.db;

import java.io.File;

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
public class RequestDb extends Database<Request> {
    public RequestDb() {
        super();
    }

    public RequestDb(BaseDatabase<Request> base) {
        super(base);
    }

    public static final RequestDb instance = DataReader.readDatabase(type(), RequestDb::new,
            new File(Constants.SHELTER_DB));

    public static JsonType<BaseDatabase<Request>> type() {
        return JsonbInstance.instance.type(Types.newParameterizedType(Database.class, Request.class));
    }
}
