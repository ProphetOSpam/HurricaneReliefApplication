package com.one_half_men.app.db;

import java.util.Set;

import com.one_half_men.app.json.JsonbInstance;
import com.one_half_men.app.user.User;
import com.one_half_men.app.user.UserQuery;

import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Types;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class UserDb extends Database<User> implements Searchable<User, UserQuery> {
    // Just for testing, remove later
    public UserDb() {
        super();
    }

    public UserDb(BaseDatabase<User> base) {
        super(base);
    }

    public static JsonType<BaseDatabase<User>> type() {
        return JsonbInstance.instance.type(Types.newParameterizedType(BaseDatabase.class, User.class));
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

}
