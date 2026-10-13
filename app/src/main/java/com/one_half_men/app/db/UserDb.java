package com.one_half_men.app.db;

import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Types;

import java.util.Set;

import com.one_half_men.app.json.JsonbInstance;
import com.one_half_men.app.user.User;
import com.one_half_men.app.user.UserQuery;

public class UserDb extends Database<User> implements Searchable<User, UserQuery> {
    UserDb() {
        super();
    }

    UserDb(Database<User> db) {
        super(db.entries);
    }

    @Override
	public Set<Key<User>> search(UserQuery query) {
		return Set.of();
	}

    @Override
    protected JsonType<Database<User>> type() {
        return JsonbInstance.instance.type(Types.newParameterizedType(Database.class, User.class));
    }
}