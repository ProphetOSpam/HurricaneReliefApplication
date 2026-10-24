package com.one_half_men.app;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.one_half_men.app.being.Animal;
import com.one_half_men.app.being.Person;
import com.one_half_men.app.being.PhoneNumber;
import com.one_half_men.app.being.SpecialNeed;
import com.one_half_men.app.being.Species;
import com.one_half_men.app.db.Key;
import com.one_half_men.app.db.RequestDb;
import com.one_half_men.app.db.UserDb;
import com.one_half_men.app.error.AccountCreationError;
import com.one_half_men.app.hurricane_application.HurricaneApplication;
import com.one_half_men.app.location.Address;
import com.one_half_men.app.location.State;
import com.one_half_men.app.location.ZipCode;
import com.one_half_men.app.request.Request;
import com.one_half_men.app.user.User;

@TestMethodOrder(OrderAnnotation.class)
public class HurricaneScarlettScenarios {
    private static final UserDb USER_DB = new UserDb();
    private static final RequestDb REQUEST_DB = new RequestDb();

    private static final String DELL_USER = "sophies_father_dell";
    private static Key<User> dellKey;

    private static final String ROSA_USER = "rosa";
    private static Key<User> rosaKey;

    @Test
    @Order(0)
    void createTheReliefRequest() throws AccountCreationError {
        dellKey = HurricaneApplication.getInstance().createAccount(
                DELL_USER,
                "password",
                USER_DB);

        // Sophie's Father
        User dell = USER_DB.get(dellKey);
        dell.setLocation(Address.builder()
                .streetAddress("8 St Michaels Alley")
                .city("Columbia")
                .state(State.SOUTH_CAROLINA)
                .zipCode(Optional.of(new ZipCode("29401")))
                .build());

        dell.setPerson(new Person(
                "Dell",
                "Conagher",
                LocalDate.of(1984, Month.APRIL, 22),
                new ArrayList<>(),
                new PhoneNumber("864", "555", "5555")));

        dell.getFamily().add(new Person(
                "Sophie",
                "Conagher",
                LocalDate.of(2010, Month.DECEMBER, 25),
                List.of(new SpecialNeed("Ashmatic",
                        "Requires an inhaler at times, must be kept near their person")),
                new PhoneNumber("864", "555", "5554")));

        // Sophie's Mother
        dell.getFamily().add(new Person(
                "Carol",
                "Conagher",
                LocalDate.of(1985, 03, 06),
                new ArrayList<>(),
                new PhoneNumber("864", "555", "5556")));

        dell.getFamily().add(new Animal(
                "Buddy",
                "Conagher",
                LocalDate.of(2021, Month.JANUARY, 27),
                new ArrayList<>(),
                new Species("Miniature Schnauzer (Dog)")));

        REQUEST_DB.create(Request.builder()
                .author(dell)
                .owner(Optional.of(dell))
                .beings(dell.getFamily())
                .date(LocalDate.now())
                .location(dell.getLocation())
                .description(
                        "Floodwater is rising around our home, and we cannot use our car. Need transportation for 2 adults, 1 child, and 1 (very kind) dog")
                .build());
    }

    @Test
    @Order(1)
    void matchRosaWithTheRequest() throws AccountCreationError {
    }
}
