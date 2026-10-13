package com.one_half_men.app.db;

import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Types;

import com.one_half_men.app.json.JsonbInstance;
import com.one_half_men.app.user.User;

public class UserDb extends Database<User> {
    UserDb() {
        super();
    }

    UserDb(Database<User> db) {
        super(db.entries);
    }

    @Override
    protected JsonType<Database<User>> type() {
        return JsonbInstance.instance.type(Types.newParameterizedType(Database.class, User.class));
    }
}
