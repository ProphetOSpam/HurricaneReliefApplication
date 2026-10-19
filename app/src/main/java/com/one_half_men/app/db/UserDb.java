package com.one_half_men.app.db;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import com.one_half_men.app.json.Constants;
import com.one_half_men.app.json.DataReader;
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

    public static final UserDb instance = DataReader.readDatabase(type(), UserDb::new, new File(Constants.USER_DB));

    public static JsonType<BaseDatabase<User>> type() {
        return JsonbInstance.instance.type(Types.newParameterizedType(BaseDatabase.class, User.class));
    }

    public Set<Key<User>> search(UserQuery query) {
        Set<Key<User>> result = new HashSet<Key<User>>();
        for (Key<User> key : base.entries.keySet()) {
            User user = base.entries.get(key);
            if (query.matches(user)) {
                result.add(key);
            }
        }
        return result;
    }

}
