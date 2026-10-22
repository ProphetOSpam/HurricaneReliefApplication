package com.one_half_men.app.db;

import java.io.File;

import com.one_half_men.app.json.Constants;
import com.one_half_men.app.json.DataReader;
import com.one_half_men.app.json.JsonbInstance;
import com.one_half_men.app.user.User;

import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Types;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class UserDb extends Database<User> {
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
}