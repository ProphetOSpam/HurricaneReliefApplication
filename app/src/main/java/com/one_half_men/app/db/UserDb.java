package com.one_half_men.app.db;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.one_half_men.app.json.JsonbInstance;
import com.one_half_men.app.user.User;
import com.one_half_men.app.user.UserQuery;

import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Types;

public class UserDb extends Database<User> implements Searchable<User, UserQuery> {
    public UserDb() {
        super();
    }

    public UserDb(Map<Key<User>, User> entries) {
        super(entries);
    }

    UserDb(Database<User> db) {
        super(db.entries);
    }

    public Set<Key<User>> search(UserQuery query) {
        Set<Key<User>> result = new HashSet<Key<User>>();
        for (Key<User> key : entries.keySet()) {
            User user = entries.get(key);
            if (query.equals(user)) {
                result.add(key);
            }
        }
        return result;
    }

    @Override
    public JsonType<Database<User>> type() {
        return JsonbInstance.instance.type(Types.newParameterizedType(Database.class, User.class));
    }
}
