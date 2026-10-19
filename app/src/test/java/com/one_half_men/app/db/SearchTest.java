package com.one_half_men.app.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.one_half_men.app.being.Animal;
import com.one_half_men.app.being.Person;
import com.one_half_men.app.being.PhoneNumber;
import com.one_half_men.app.being.Species;
import com.one_half_men.app.location.Coordinates;
import com.one_half_men.app.user.Role;
import com.one_half_men.app.user.User;
import com.one_half_men.app.user.UserQuery;

public class SearchTest {
    @Test
    public void databaseSearchViaQuery() {
        UserDb db = new UserDb();

        User user1 = User.builder()
                .person(new Person("firstName", "lastName", Date.from(Instant.now()), List.of(),
                        new PhoneNumber("1", "234", "5678910")))
                .role(Role.CITIZEN)
                .location(new Coordinates(0.0, 0.0))
                .family(Set
                        .of(new Animal("Bob", "Barker", new Date(2012, 1, 1), List.of(), new Species("Dog"))))
                .build();

        User user2 = User.builder()
                .person(new Person("secondName", "lastName", Date.from(Instant.now()), List.of(),
                        new PhoneNumber("1", "234", "8675309")))
                .role(Role.CITIZEN)
                .location(new Coordinates(25.0, 50.0))
                .build();

        Key<User> key1 = db.create(user1);
        Key<User> key2 = db.create(user2);

        {
            UserQuery userQ = UserQuery.builder()
                    .role(Optional.of(Role.CITIZEN))
                    .location(Optional.of(new Coordinates(25.0, 50.0)))
                    .build();

            Set<Key<User>> set = db.search(userQ);
            assertEquals(set, Set.of(key2));
        }

        {
            UserQuery userQ = UserQuery.builder()
                    .role(Optional.of(Role.CITIZEN))
                    .build();

            Set<Key<User>> set = db.search(userQ);
            assertEquals(set, Set.of(key1, key2));
        }
    }
}
