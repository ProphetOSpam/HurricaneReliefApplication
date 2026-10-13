package com.one_half_men.app.db;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import com.one_half_men.app.being.Animal;
import com.one_half_men.app.being.Person;
import com.one_half_men.app.being.PhoneNumber;
import com.one_half_men.app.being.Species;
import com.one_half_men.app.json.DataReader;
import com.one_half_men.app.json.DataWriter;
import com.one_half_men.app.json.JsonbInstance;
import com.one_half_men.app.location.Coordinates;
import com.one_half_men.app.user.Role;
import com.one_half_men.app.user.User;

import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Jsonb;
import io.avaje.jsonb.Types;

public class DatabaseTest {
    @Test
    public void databaseRoundtripInMemory() throws IOException {
        UserDb db = new UserDb();

        User user = User.builder()
                .person(new Person("firstName", "lastName", Date.from(Instant.now()), List.of(),
                        new PhoneNumber("1", "234", "5678910")))
                .role(Role.CITIZEN)
                .location(new Coordinates(0.0, 0.0))
                .family(Set
                        .of(new Animal("Bob", "Barker", new Date(2012, 1, 1), List.of(), new Species("Dog"))))
                .build();

        db.create(user);

        JsonType<Database<User>> type = JsonbInstance.instance
                .type(Types.newParameterizedType(Database.class, User.class));

        // In memory testing
        {
            String str = type.toJson(db);
            UserDb db2 = new UserDb(type.fromJson(str));
            assertEquals(db, db2);
        }

        // Writing/Reading to file
        {

            DataWriter.writeDatabase(db);
            UserDb db3 = new UserDb(DataReader.readDatabase(db));
            assertEquals(db, db3);
        }
    }
}
