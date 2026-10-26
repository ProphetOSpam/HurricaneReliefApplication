package com.one_half_men.app;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledIf;

import com.one_half_men.app.being.Animal;
import com.one_half_men.app.being.Person;
import com.one_half_men.app.being.PhoneNumber;
import com.one_half_men.app.being.SpecialNeed;
import com.one_half_men.app.being.Species;
import com.one_half_men.app.db.HurricaneDb;
import com.one_half_men.app.db.Key;
import com.one_half_men.app.db.RequestDb;
import com.one_half_men.app.db.ShelterDb;
import com.one_half_men.app.db.UserDb;
import com.one_half_men.app.error.AccountCreationError;
import com.one_half_men.app.error.AccountLoginError;
import com.one_half_men.app.error.PermissionsError;
import com.one_half_men.app.hurricane_application.HurricaneApplication;
import com.one_half_men.app.json.DataReader;
import com.one_half_men.app.json.DataWriter;
import com.one_half_men.app.location.Address;
import com.one_half_men.app.location.State;
import com.one_half_men.app.location.ZipCode;
import com.one_half_men.app.request.CloseReason;
import com.one_half_men.app.request.Request;
import com.one_half_men.app.request.RequestQuery;
import com.one_half_men.app.shelter.Resource;
import com.one_half_men.app.shelter.Resource.AnimalCapacity;
import com.one_half_men.app.shelter.Resource.HumanCapacity;
import com.one_half_men.app.shelter.Shelter;
import com.one_half_men.app.shelter.ShelterQuery;
import com.one_half_men.app.user.Specialty;
import com.one_half_men.app.user.User;

@TestMethodOrder(OrderAnnotation.class)
public class HurricaneScarlettScenariosTest {
    // if false then "reset" mode: this'll initialize with mock data and write
    // if true then "run" mode: this'll initialize from the file (do "reset" first)
    // and go through the scenario
    private static boolean isRun() {
        // modify for whatever during testing
        return true;
    }

    private static UserDb USER_DB;
    private static final File USER_DB_FILE = new File("../json_test/user_db.json");

    private static RequestDb REQUEST_DB;
    private static final File REQUEST_DB_FILE = new File("../json_test/request_db.json");

    private static ShelterDb SHELTER_DB;
    private static final File SHELTER_DB_FILE = new File("../json_test/shelter_db.json");

    private static HurricaneDb HURRICANE_DB;
    private static final File HURRICANE_DB_FILE = new File("../json_test/hurricane_db.json");

    private static HurricaneApplication DELL_APP;
    private static HurricaneApplication ROSA_APP;
    private static HurricaneApplication FELIX_APP;

    private static Request matchedRosaRequest;
    private static Key<Request> matchedRosaRequestKey;
    private static Shelter matchedRosaShelter;

    private static void initializeApps() {
        DELL_APP = new HurricaneApplication(
                USER_DB,
                REQUEST_DB,
                SHELTER_DB,
                HURRICANE_DB);

        ROSA_APP = new HurricaneApplication(
                USER_DB,
                REQUEST_DB,
                SHELTER_DB,
                HURRICANE_DB);

        FELIX_APP = new HurricaneApplication(
                USER_DB,
                REQUEST_DB,
                SHELTER_DB,
                HURRICANE_DB);
    }

    @BeforeAll
    static void initRun() throws AccountLoginError {
        if (!isRun())
            return;

        USER_DB = DataReader.readDatabase(UserDb.type(), UserDb::new, USER_DB_FILE);
        REQUEST_DB = DataReader.readDatabase(RequestDb.type(), RequestDb::new, REQUEST_DB_FILE);
        SHELTER_DB = DataReader.readDatabase(ShelterDb.type(), ShelterDb::new, SHELTER_DB_FILE);
        HURRICANE_DB = DataReader.readDatabase(HurricaneDb.type(), HurricaneDb::new, HURRICANE_DB_FILE);

        initializeApps();

        ROSA_APP.login("rosa", "password");
        FELIX_APP.login("felix", "password");
        // dell logs in
    }

    @BeforeAll
    static void initReset() throws AccountCreationError, AccountLoginError, IOException {
        if (isRun())
            return;

        USER_DB = new UserDb();
        REQUEST_DB = new RequestDb();
        SHELTER_DB = new ShelterDb();
        HURRICANE_DB = new HurricaneDb();

        initializeApps();

        final String ROSA_USER = "rosa";

        ROSA_APP.createAccount(
                ROSA_USER,
                "password");

        ROSA_APP.login(ROSA_USER, "password");
        User rosa = ROSA_APP.getLoggedInUser().get();

        rosa.getSpecialties().add(new Specialty.Transportation(4, Set.of(new Species("dog"))));
        rosa.setLocation(new Address(
                "8 Ropemakers Ln",
                "Charleston",
                State.SOUTH_CAROLINA,
                Optional.of(new ZipCode("29401"))));

        final String FELIX_USER = "felix";
        FELIX_APP.createAccount(
                FELIX_USER,
                "password");

        Key<User> felixKey = FELIX_APP.login(FELIX_USER, "password");

        Key<User> slumbumKey = FELIX_APP.createAccount("lordSlumbum", "password");

        User slumbum = USER_DB.get(slumbumKey);

        Key<Shelter> felixShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("Felix House")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of(new Species("dog"))),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(1),
                        new Resource.HumanCapacity(3)))
                .owner(felixKey)
                .location(new Address(
                        "40 Tradd St",
                        "Charleston",
                        State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29401"))))
                .build());

        FELIX_APP.getLoggedInUser().get().getOwnedShelters().add(felixShelterKey);

        Key<Shelter> noAnimalShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("No Animals")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of()),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(0),
                        new Resource.HumanCapacity(10)))
                .location(new Address("194 W Poplar St", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29403"))))
                .owner(slumbumKey).build());

        Key<Shelter> hilbertShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("The Grand Hilbert Hotel")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of()),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(10),
                        new Resource.HumanCapacity(100)))
                .location(new Address("226 Gordon St", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29403"))))
                .owner(slumbumKey).build());

        Key<Shelter> newYorkCityShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("New York City Alleyway")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of(new Species("rat"))),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(1000),
                        new Resource.HumanCapacity(10)))
                .location(new Address("1 Elmwood Ave", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29403"))))
                .owner(slumbumKey).build());

        Key<Shelter> noHumanShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("No Humans")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of(new Species("dog"), new Species("cat"))),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(100),
                        new Resource.HumanCapacity(0)))
                .location(new Address("12 Perry St", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29403"))))
                .owner(slumbumKey).build());

        Key<Shelter> geminiShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("Gemini Shelter")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of(new Species("cat"), new Species("dog"), new Species("rabbit"))),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(45),
                        new Resource.HumanCapacity(12)))
                .location(new Address("3 Murphy Ct", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29403"))))
                .owner(slumbumKey).build());

        Key<Shelter> anotherShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("Another Charleston Shelter")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of(new Species("rabbit"))),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(5),
                        new Resource.HumanCapacity(12)))
                .location(new Address("41 Smith St", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29401"))))
                .owner(slumbumKey).build());

        Key<Shelter> frenchShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("French Shelter")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of()),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(69),
                        new Resource.HumanCapacity(420)))
                .location(new Address("17 Chalmers St", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29401"))))
                .owner(slumbumKey).build());

        Key<Shelter> sjelterShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("The Sjelter")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of()),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(6),
                        new Resource.HumanCapacity(7)))
                .location(new Address("35 Poinsett St", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29403"))))
                .owner(slumbumKey).build());

        Key<Shelter> finalShelterKey = SHELTER_DB.create(Shelter.builder()
                .name("The Final Shelter")
                .resources(Set.of(
                        new Resource.AllowedPets(Set.of(new Species("dog"))),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(20),
                        new Resource.HumanCapacity(26)))
                .location(new Address("8 Murray Blvd", "Charleston", State.SOUTH_CAROLINA,
                        Optional.of(new ZipCode("29403"))))
                .owner(slumbumKey).build());

        REQUEST_DB.create(Request.builder()
                .author(slumbumKey)
                .owner(Optional.of(slumbumKey))
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
                .author(slumbumKey)
                .owner(Optional.of(slumbumKey))
                .date(LocalDate.now())
                .location(SHELTER_DB.get(hilbertShelterKey).getLocation())
                .description(
                        "My grand hotel is experiencing a major mathematical conundrum. Any mathematicians able to help me figure out how to house one more person?")
                .build());

        REQUEST_DB.create(Request.builder()
                .author(slumbumKey)
                .owner(Optional.of(slumbumKey))
                .date(LocalDate.now())
                .location(SHELTER_DB.get(finalShelterKey).getLocation())
                .requiredResources(Set.of(new Resource.Custom("Water")))
                .description(
                        "I'm quite parched. May one of you fine specimens aid me in obtaining liquid H2O?")
                .build());

        REQUEST_DB.create(Request.builder()
                .author(slumbumKey)
                .owner(Optional.of(slumbumKey))
                .date(LocalDate.now())
                .location(SHELTER_DB.get(sjelterShelterKey).getLocation())
                .requiredResources(Set.of(new Resource.Custom("Portable Generator")))
                .description(
                        "I need power for my phone to stay in contact with family.")
                .build());

        REQUEST_DB.create(Request.builder()
                .author(slumbumKey)
                .owner(Optional.of(slumbumKey))
                .date(LocalDate.now())
                .location(SHELTER_DB.get(frenchShelterKey).getLocation())
                .description(
                        "I need help cleaning up minor damage to my house.")
                .build());

        REQUEST_DB.create(Request.builder()
                .author(slumbumKey)
                .owner(Optional.of(slumbumKey))
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
    @EnabledIf("isRun")
    void createTheReliefRequest() throws AccountCreationError, AccountLoginError {
        String DELL_USER = "sophies_father_dell";

        Key<User> dellKey = DELL_APP.createAccount(
                DELL_USER,
                "password");

        DELL_APP.login(DELL_USER, "password");

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
                "Carter",
                LocalDate.of(1984, Month.APRIL, 22),
                new ArrayList<>(),
                new PhoneNumber("1", "864", "555-5555")));

        dell.getFamily().add(new Person(
                "Sophie",
                "Carter",
                LocalDate.of(2010, Month.DECEMBER, 25),
                List.of(new SpecialNeed("Asthmatic",
                        "Requires an inhaler at times, must be kept near their person")),
                new PhoneNumber("1", "864", "555-5554")));

        // Sophie's Mother
        dell.getFamily().add(new Person(
                "Carol",
                "Carter",
                LocalDate.of(1985, 03, 06),
                new ArrayList<>(),
                new PhoneNumber("1", "864", "555-5556")));

        dell.getFamily().add(new Animal(
                "Buddy",
                "Carter",
                LocalDate.of(2021, Month.JANUARY, 27),
                new ArrayList<>(),
                new Species("Miniature Schnauzer (Dog)")));

        REQUEST_DB.create(Request.builder()
                .author(dellKey)
                .owner(Optional.of(dellKey))
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
    @EnabledIf("isRun")
    void matchRosaWithTheRequest() {
        User rosa = ROSA_APP.getLoggedInUser().get();

        // Looking for available requests
        RequestQuery query = RequestQuery.builder()
                .participants(Optional.of(Set.of())) // No participants
                .requiredSpecialties(Optional.of(rosa.getSpecialties()))
                .build();

        ROSA_APP.searchRequest(query).stream().forEach(System.out::println);

        matchedRosaRequestKey = ROSA_APP.searchRequest(query).stream().findFirst().get();
        matchedRosaRequest = ROSA_APP.getRequest(matchedRosaRequestKey);
    }

    @Test
    @Order(2)
    @EnabledIf("isRun")
    void FindAShelterAndAssignTheRequest() {
        Key<User> rosaKey = ROSA_APP.getLoggedInUserKey().get();

        ShelterQuery query = ShelterQuery.builder()
                .location(Optional.of(matchedRosaRequest.getLocation()))
                .resources(Optional.of(Set.of(
                        new Resource.AllowedPets(Set.of(new Species("dog"))),
                        new Resource.MedicalStaff(),
                        new Resource.AnimalCapacity(1),
                        new Resource.HumanCapacity(3))))
                .build();

        System.out.println("Matched Shelters:");
        SortedSet<Key<Shelter>> shelters = (SortedSet<Key<Shelter>>) ROSA_APP.searchShelter(query);
        shelters.stream().map(key -> SHELTER_DB.get(key).getName()).forEach(System.out::println);

        matchedRosaShelter = shelters.stream().map(SHELTER_DB::get).findFirst().get();

        System.out.printf("Using %s because it is the closest\n", matchedRosaShelter.getName());

        ROSA_APP.addParticipantToRequest(matchedRosaRequestKey);
    }

    @Test
    @Order(3)
    @EnabledIf("isRun")
    void exportTheTripInformation() throws IOException {
        User rosa = ROSA_APP.getLoggedInUser().get();

        String out = PrintableUtils.fullRoute(ROSA_APP, ROSA_APP.getRequest(matchedRosaRequestKey), rosa.getLocation(),
                matchedRosaShelter);
        System.out.println(out);

        try (FileWriter writer = new FileWriter(new File("../json_test/print_out.txt"))) {
            writer.write(out);
        }
    }

    @Test
    @Order(4)
    @EnabledIf("isRun")
    void demonstrateRolePermisssions() throws PermissionsError {
        User dell = DELL_APP.getLoggedInUser().get();
        User rosa = ROSA_APP.getLoggedInUser().get();

        System.out.println("Rosa tries to close the request");

        // Rosa can't close Dell's request
        assertThrows(PermissionsError.class, () -> {
            ROSA_APP.editRequest(
                    ROSA_APP.getRequest(matchedRosaRequestKey),
                    request -> request.close(CloseReason
                            .resolved("Family has been checked into a shelter")));
        });

        System.err.printf(
                "User %s does not have permission to edit a request owned by %s\n",
                rosa.getUsername(),
                ROSA_APP.getUser(matchedRosaRequest.getOwner().get()).getUsername());

        System.out.println("Dell tries to close the request");

        // Dell *can* close their request
        DELL_APP.editRequest(
                matchedRosaRequest,
                request -> request.close(CloseReason.resolved("We've been checked into a shelter")));

        System.out.printf("User %s has successfully closed the request with the message: %s\n",
                dell.getUsername(),
                matchedRosaRequest.getCloseReason().get().getMessage());

        assertTrue(matchedRosaRequest.isClosed());
    }

    @Test
    @Order(5)
    @EnabledIf("isRun")
    void CheckTheFamilyIntoTheShelter() throws PermissionsError {
        System.out.println("\nFelix views his shelters");

        Shelter shelter = SHELTER_DB
                .get(FELIX_APP.getLoggedInUser().get().getOwnedShelters().stream().findFirst().get());

        System.out.printf("Shelter name: %s\n", shelter.getName());
        System.out.printf("Shelter location: %s\n", shelter.getLocation().toText());

        HumanCapacity humanCapacity = (HumanCapacity) shelter.getResources().stream()
                .filter(resource -> resource instanceof HumanCapacity).findFirst().get();

        System.out.printf("Total Capacity: %d\n", humanCapacity.getMax());
        System.out.printf("Current Capacity: %d\n", humanCapacity.getCurrent());
        System.out.printf("Remaining Capacity: %d\n", humanCapacity.remaining());

        AnimalCapacity animalCapacity = (AnimalCapacity) shelter.getResources().stream()
                .filter(resource -> resource instanceof AnimalCapacity).findFirst().get();

        System.out.printf("Total Pet Capacity: %d\n", animalCapacity.getMax());
        System.out.printf("Current Pet Capacity: %d\n", animalCapacity.getCurrent());
        System.out.printf("Remaining Pet Capacity: %d\n", animalCapacity.remaining());

        // -- Update shelters

        System.out.println("\nFelix updates his shelters\n");

        humanCapacity.change(3);
        animalCapacity.change(1);

        System.out.println("updated values");

        System.out.printf("Total Capacity: %d\n", humanCapacity.getMax());
        System.out.printf("Current Capacity: %d\n", humanCapacity.getCurrent());
        System.out.printf("Remaining Capacity: %d\n", humanCapacity.remaining());

        System.out.printf("Total Pet Capacity: %d\n", animalCapacity.getMax());
        System.out.printf("Current Pet Capacity: %d\n", animalCapacity.getCurrent());
        System.out.printf("Remaining Pet Capacity: %d\n", animalCapacity.remaining());
    }

    @AfterAll
    static void writeDatabases() {
        DataWriter.writeDatabase(UserDb.type(), USER_DB, USER_DB_FILE);
        DataWriter.writeDatabase(RequestDb.type(), REQUEST_DB, REQUEST_DB_FILE);
        DataWriter.writeDatabase(ShelterDb.type(), SHELTER_DB, SHELTER_DB_FILE);
        DataWriter.writeDatabase(HurricaneDb.type(), HURRICANE_DB, HURRICANE_DB_FILE);
    }
}
