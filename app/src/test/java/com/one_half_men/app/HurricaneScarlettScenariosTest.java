package com.one_half_men.app;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
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
import com.one_half_men.app.db.ShelterDb;
import com.one_half_men.app.db.UserDb;
import com.one_half_men.app.error.AccountCreationError;
import com.one_half_men.app.error.AccountLoginError;
import com.one_half_men.app.error.PermissionsError;
import com.one_half_men.app.hurricane_application.HurricaneApplication;
import com.one_half_men.app.location.Address;
import com.one_half_men.app.location.State;
import com.one_half_men.app.location.ZipCode;
import com.one_half_men.app.request.CloseReason;
import com.one_half_men.app.request.Request;
import com.one_half_men.app.request.RequestQuery;
import com.one_half_men.app.shelter.Resource;
import com.one_half_men.app.shelter.Shelter;
import com.one_half_men.app.shelter.ShelterQuery;
import com.one_half_men.app.user.Specialty;
import com.one_half_men.app.user.User;

@TestMethodOrder(OrderAnnotation.class)
public class HurricaneScarlettScenariosTest {
    private static final UserDb USER_DB = new UserDb();
    private static final RequestDb REQUEST_DB = new RequestDb();
    private static final ShelterDb SHELTER_DB = new ShelterDb();

    private static final HurricaneApplication DELL_APP = new HurricaneApplication();

    private static final HurricaneApplication ROSA_APP = new HurricaneApplication();
    private static final Address ROSA_ADDRESS = new Address("8 Ropemakers Ln", "Charleston", State.SOUTH_CAROLINA,
            Optional.of(new ZipCode("29401")));

    private static Request matchedRosaRequest;
    private static Shelter matchedRosaShelter;

    private static final HurricaneApplication FELIX_APP = new HurricaneApplication();
    private static final Address FELIX_SHELTER_ADDRESS = new Address("40 Tradd St", "Charleston", State.SOUTH_CAROLINA,
            Optional.of(new ZipCode("29401")));

    private static final String SLUMBUM_USER = "lordSlumbum";
    private static Key<User> slumbumKey;

    @BeforeAll
    static void init() throws AccountCreationError, AccountLoginError, IOException {
        final String ROSA_USER = "rosa";

        HurricaneApplication.getInstance().createAccount(
                USER_DB,
                ROSA_USER,
                "password");

        ROSA_APP.login(USER_DB, ROSA_USER, "password");
        User rosa = ROSA_APP.getLoggedInUser().get();

        rosa.getSpecialties().add(new Specialty.Transportation(4, Set.of(new Species("dog"))));
        rosa.setLocation(ROSA_ADDRESS);

        final String FELIX_USER = "felix";
        HurricaneApplication.getInstance().createAccount(
                USER_DB,
                FELIX_USER,
                "password");

        FELIX_APP.login(USER_DB, FELIX_USER, "password");
        User felix = FELIX_APP.getLoggedInUser().get();

        slumbumKey = HurricaneApplication.getInstance().createAccount(
                USER_DB,
                SLUMBUM_USER,
                "password");

        User slumbum = USER_DB.get(slumbumKey);

        Key<Shelter> felixShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("Felix House")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of(new Species("dog"))),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(1),
                        new Resource.HumanCapacity(3)))
                .owner(felix)
                .location(FELIX_SHELTER_ADDRESS)
                .build());

        Key<Shelter> noAnimalShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("No Animals")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of()),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(0),
                        new Resource.HumanCapacity(10)))
                .location(new Address("194 W Poplar St", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29403"))))
                .owner(slumbum).build());

        Key<Shelter> hilbertShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("The Grand Hilbert Hotel")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of()),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(100),
                        new Resource.HumanCapacity(100)))
                .location(new Address("226 Gordon St", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29403"))))
                .owner(slumbum).build());

        Key<Shelter> newYorkCityShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("New York City Alleyway")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of(new Species("rat"))),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(1000),
                        new Resource.HumanCapacity(10)))
                .location(new Address("1 Elmwood Ave", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29403"))))
                .owner(slumbum).build());

        Key<Shelter> noHumanShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("No Humans")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of(new Species("dog"), new Species("cat"))),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(100),
                        new Resource.HumanCapacity(0)))
                .location(new Address("12 Perry St", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29403"))))
                .owner(slumbum).build());

        Key<Shelter> geminiShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("Gemini Shelter")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of(new Species("cat"), new Species("dog"), new Species("rabbit"))),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(45),
                        new Resource.HumanCapacity(12)))
                .location(new Address("3 Murphy Ct", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29403"))))
                .owner(slumbum).build());

        Key<Shelter> anotherShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("Another Charleston Shelter")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of(new Species("rabbit"))),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(5),
                        new Resource.HumanCapacity(12)))
                .location(new Address("41 Smith St", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29401"))))
                .owner(slumbum).build());

        Key<Shelter> frenchShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("French Shelter")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of()),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(69),
                        new Resource.HumanCapacity(420)))
                .location(new Address("17 Chalmers St", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29401"))))
                .owner(slumbum).build());

        Key<Shelter> sjelterShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("The Sjelter")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of()),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(6),
                        new Resource.HumanCapacity(7)))
                .location(new Address("35 Poinsett St", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29403"))))
                .owner(slumbum).build());

        Key<Shelter> finalShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("The Final Shelter")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of(new Species("dog"))),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(20),
                        new Resource.HumanCapacity(26)))
                .location(new Address("8 Murray Blvd", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29403"))))
                .owner(slumbum).build());

        REQUEST_DB.create(Request.builder()
                .author(slumbum)
                .owner(Optional.of(slumbum))
                .beings(Set.of(
                        new Animal(
                                "Rabies",
                                "Ronnie",
                                LocalDate.of(2026, 10, 02),
                                List.of(new SpecialNeed("Rabies", "Don't get bitten")),
                                new Species("rat"))))
                .date(LocalDate.now())
                .location(SHELTER_DB.get(newYorkCityShelterKey).getLocation())
                .requiredResources(
                        Set.of(new Resource.MedicalStaff()))
                .description(
                        "My (very kind) rat has rabies, and I need help taking care of him.")
                .build());

        REQUEST_DB.create(Request.builder()
                .author(slumbum)
                .owner(Optional.of(slumbum))
                .date(LocalDate.now())
                .location(SHELTER_DB.get(hilbertShelterKey).getLocation())
                .description(
                        "My grand hotel is experiencing a major mathematical conundrum. Any mathematicians able to help me figure out how to house one more person?")
                .build());

        REQUEST_DB.create(Request.builder()
                .author(slumbum)
                .owner(Optional.of(slumbum))
                .date(LocalDate.now())
                .location(SHELTER_DB.get(finalShelterKey).getLocation())
                .requiredResources(Set.of(new Resource.Custom("Water")))
                .description(
                        "I'm quite parched. May one of you fine specimens aid me in obtaining liquid H2O?")
                .build());

        REQUEST_DB.create(Request.builder()
                .author(slumbum)
                .owner(Optional.of(slumbum))
                .date(LocalDate.now())
                .location(SHELTER_DB.get(sjelterShelterKey).getLocation())
                .requiredResources(Set.of(new Resource.Custom("Portable Generator")))
                .description(
                        "I need power for my phone to stay in contact with family.")
                .build());

        REQUEST_DB.create(Request.builder()
                .author(slumbum)
                .owner(Optional.of(slumbum))
                .date(LocalDate.now())
                .location(SHELTER_DB.get(frenchShelterKey).getLocation())
                .description(
                        "I need help cleaning up minor damage to my house.")
                .build());

        REQUEST_DB.create(Request.builder()
                .author(slumbum)
                .owner(Optional.of(slumbum))
                .date(LocalDate.now())
                .location(SHELTER_DB.get(geminiShelterKey).getLocation())
                .requiredResources(Set.of(new Resource.Custom("Food")))
                .description(
                        "I'm hungry!")
                .build());

        printDatabases();
    }

    private static void printDatabases() {
        System.out.printf("""
                User Database =  %s
                ---

                Request Database =  %s
                ---

                Shelter Database = %s
                ---\n""",
                UserDb.type().toJsonPretty(USER_DB.base),
                RequestDb.type().toJsonPretty(REQUEST_DB.base),
                ShelterDb.type().toJsonPretty(SHELTER_DB.base));
    }

    @Test
    @Order(0)
    void createTheReliefRequest() throws AccountCreationError, AccountLoginError {
        String DELL_USER = "sophies_father_dell";

        HurricaneApplication.getInstance().createAccount(
                USER_DB,
                DELL_USER,
                "password");

        DELL_APP.login(USER_DB, DELL_USER, "password");

        // Sophie's Father
        User dell = DELL_APP.getLoggedInUser().get();
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
                new PhoneNumber("1", "864", "555-5555")));

        dell.getFamily().add(new Person(
                "Sophie",
                "Conagher",
                LocalDate.of(2010, Month.DECEMBER, 25),
                List.of(new SpecialNeed("Asthmatic",
                        "Requires an inhaler at times, must be kept near their person")),
                new PhoneNumber("1", "864", "555-5554")));

        // Sophie's Mother
        dell.getFamily().add(new Person(
                "Carol",
                "Conagher",
                LocalDate.of(1985, 03, 06),
                new ArrayList<>(),
                new PhoneNumber("1", "864", "555-5556")));

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
                .requiredSpecialties(
                        Set.of(new Specialty.Transportation(4, Set.of(new Species("dog")))))
                .description(
                        "Floodwater is rising around our home, and we cannot use our car. Need transportation for 2 adults, 1 child, and 1 (very kind) dog")
                .build());
    }

    @Test
    @Order(1)
    void matchRosaWithTheRequest() {
        User rosa = ROSA_APP.getLoggedInUser().get();

        // Looking for available requests
        RequestQuery query = RequestQuery.builder()
                .participants(Optional.of(Set.of())) // No participants
                .requiredSpecialties(Optional.of(rosa.getSpecialties()))
                .build();

        matchedRosaRequest = ROSA_APP.searchRequest(REQUEST_DB, query).stream().map(REQUEST_DB::get).findFirst().get();
    }

    @Test
    @Order(2)
    void FindAShelterAndAssignTheRequest() {
        User rosa = ROSA_APP.getLoggedInUser().get();

        ShelterQuery query = ShelterQuery.builder()
                .resources(Optional.of(Set.of(
                        new Resource.AllowedPets(Set.of(new Species("dog"))),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(1),
                        new Resource.HumanCapacity(3))))
                .build();

        matchedRosaShelter = ROSA_APP.searchShelter(SHELTER_DB, query).stream().map(SHELTER_DB::get).findFirst().get();

        matchedRosaRequest.addParticipant(rosa);

        System.out.println(matchedRosaShelter);
    }

    @Test
    @Order(3)
    void exportTheTripInformation() throws IOException {
        User rosa = ROSA_APP.getLoggedInUser().get();

        String out = PrintableUtils.fullRoute(matchedRosaRequest, rosa.getLocation(), matchedRosaShelter);
        System.out.println(out);

        try (FileWriter writer = new FileWriter("out.txt")) {
            writer.write(out);
        }
    }

    @Test
    @Order(4)
    void demonstrateRolePermisssions() throws PermissionsError {
        User rosa = ROSA_APP.getLoggedInUser().get();

        // Rosa can't close Dell's request
        assertThrows(PermissionsError.class, () -> {
            ROSA_APP.editRequest(
                    matchedRosaRequest,
                    request -> request.close(CloseReason.resolved("Family has been checked into a shelter")));
        });

        System.err.printf(
                "User %s does not have permission to edit Request: %s",
                rosa.getUsername(),
                matchedRosaRequest.toString());

        // Dell *can* close their request
        DELL_APP.editRequest(
                matchedRosaRequest,
                request -> request.close(CloseReason.resolved("We've been checked into a shelter")));

        assertTrue(matchedRosaRequest.isClosed());
    }
}
