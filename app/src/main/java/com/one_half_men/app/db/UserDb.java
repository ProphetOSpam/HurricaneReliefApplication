package com.one_half_men.app.db;

import java.util.Map;
import java.util.Set;

import com.one_half_men.app.user.User;
import com.one_half_men.app.user.UserQuery;

public class UserDb extends Database<User> implements Searchable<User, UserQuery> {
	public UserDb(Map<Key<User>, User> entries) {
		super(entries);
	}

	@Override
	public Set<Key<User>> search(UserQuery query) {
		return Set.of();
	}
}
