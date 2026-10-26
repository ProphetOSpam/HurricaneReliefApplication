// In it's own package to avoid exporting everything
package com.one_half_men.app.hurricane_application;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

import com.one_half_men.app.being.Being;
import com.one_half_men.app.db.HurricaneDb;
import com.one_half_men.app.db.Key;
import com.one_half_men.app.db.RequestDb;
import com.one_half_men.app.db.ShelterDb;
import com.one_half_men.app.db.UserDb;
import com.one_half_men.app.error.AccountCreationError;
import com.one_half_men.app.error.AccountLoginError;
import com.one_half_men.app.error.PermissionsError;
import com.one_half_men.app.hurricane.Hurricane;
import com.one_half_men.app.hurricane.HurricaneQuery;
import com.one_half_men.app.request.CloseReason;
import com.one_half_men.app.request.Request;
import com.one_half_men.app.request.RequestQuery;
import com.one_half_men.app.shelter.Shelter;
import com.one_half_men.app.shelter.ShelterQuery;
import com.one_half_men.app.user.Authentication;
import com.one_half_men.app.user.Authentication.Username;
import com.one_half_men.app.user.User;
import com.one_half_men.app.user.UserQuery;

import lombok.Getter;

public class HurricaneApplication {
    public static final Random RANDOM = new SecureRandom();

    private static final HurricaneApplication INSTANCE = new HurricaneApplication();

    private @Getter Optional<User> loggedInUser = Optional.empty();
    private @Getter Optional<Key<User>> loggedInUserKey = Optional.empty();

    private UserDb userDb;
    private RequestDb requestDb;
    private ShelterDb shelterDb;
    private HurricaneDb hurricaneDb;

    public static HurricaneApplication getInstance() {
        return INSTANCE;
    }

    public HurricaneApplication() {
        userDb = UserDb.getInstance();
        requestDb = RequestDb.getInstance();
        shelterDb = ShelterDb.getInstance();
        hurricaneDb = HurricaneDb.getInstance();
    }

    public HurricaneApplication(UserDb userDb, RequestDb requestDb, ShelterDb shelterDb, HurricaneDb hurricaneDb) {
        this.userDb = userDb;
        this.requestDb = requestDb;
        this.shelterDb = shelterDb;
        this.hurricaneDb = hurricaneDb;
    }

    public static HurricaneApplication mockApplication() {
        return new HurricaneApplication(
                new UserDb(),
                new RequestDb(),
                new ShelterDb(),
                new HurricaneDb());
    }

    // We cannot use the normal singleton instances if we want to test these, so the
    // instance is specified when calling the method

    public Key<User> createAccount(String username, String password) throws AccountCreationError {
        boolean usernameTaken = userDb.search(
                UserQuery.builder()
                        .username(Optional.of(username))
                        .build())
                .size() > 0;

        if (usernameTaken)
            throw new AccountCreationError.UsernameTaken();

        if (!Username.isValid(username))
            throw new AccountCreationError.UsernameInvalid();

        User.UserBuilder builder = User.builder().username(username);

        Optional<Authentication.AuthKey> login = Authentication.AuthKey.fromPassword(password);
        if (login.isEmpty())
            throw new AccountCreationError.PasswordInvalid();

        builder.login(login.get());

        return userDb.create(builder.build());
    }

    public Key<User> login(String username, String password) throws AccountLoginError {
        Set<Key<User>> results = userDb.search(
                UserQuery.builder()
                        .username(Optional.of(username))
                        .build());

        Optional<Key<User>> first = results.stream().findFirst();
        if (first.isEmpty())
            throw new AccountLoginError.NoAccountFoundError();

        Key<User> key = first.get();
        User user = userDb.get(key);

        boolean passwordCorrect = user.getLogin().checkPassword(password);

        if (!passwordCorrect)
            throw new AccountLoginError.WrongPasswordError();

        loggedInUser = Optional.of(user);
        loggedInUserKey = Optional.of(key);
        return key;
    }

    public void logout() {
        loggedInUser = Optional.empty();
        loggedInUserKey = Optional.empty();
    }

    public void addMember(Being being) {
        loggedInUser.get().getFamily().add(being);
    }

    public void editUser(User user, Consumer<User> consumer) throws PermissionsError {
        consumer.accept(user);
    }

    public User getUser(Key<User> key) {
        return userDb.get(key);
    }

    public Set<Key<User>> searchUser(UserQuery query) {
        return userDb.search(query);
    }

    public Key<Request> createRequest(Request request) {
        return requestDb.create(request);
    }

    public void editRequest(Request request, Consumer<Request> consumer) throws PermissionsError {
        if (!request.canEdit(loggedInUserKey.get())) {
            throw new PermissionsError();
        }

        consumer.accept(request);
    }

    public Request getRequest(Key<Request> key) {
        return requestDb.get(key);
    }

    public Set<Key<Request>> searchRequest(RequestQuery query) {
        return requestDb.search(query);
    }

    public void closeRequest(Key<Request> key, CloseReason reason) throws PermissionsError {
        editRequest(requestDb.get(key), request -> request.close(reason));
    }

    public void claimProxyRequest(Key<Request> key) {
        requestDb.get(key).claimProxy(loggedInUserKey.get());
        loggedInUser.get().getOwnedRequests().add(key);
    }

    public void addParticipantToRequest(Key<Request> key) {
        requestDb.get(key).addParticipant(loggedInUserKey.get());
        loggedInUser.get().getParticipatingRequests().add(key);
    }

    public Key<Shelter> createShelter(Shelter shelter) {
        return shelterDb.create(shelter);
    }

    public void editShelter(Shelter shelter, Consumer<Shelter> consumer) throws PermissionsError {
        if (userDb.get(shelter.getOwner()) != loggedInUser.get()) {
            throw new PermissionsError();
        }

        consumer.accept(shelter);
    }

    public Shelter getShelter(Key<Shelter> key) {
        return shelterDb.get(key);
    }

    public Set<Key<Shelter>> searchShelter(ShelterQuery query) {
        return shelterDb.search(query);
    }

    public void removeShelter(Key<Shelter> key) throws PermissionsError {
        Shelter shelter = getShelter(key);

        if (userDb.get(shelter.getOwner()) != loggedInUser.get()) {
            throw new PermissionsError();
        }

        shelterDb.remove(key);
    }

    public Hurricane getHurricane(Key<Hurricane> key) {
        return hurricaneDb.get(key);
    }

    public Set<Key<Hurricane>> searchHurricane(HurricaneQuery query) {
        return hurricaneDb.search(query);
    }
}
