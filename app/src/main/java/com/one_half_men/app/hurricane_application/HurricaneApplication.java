// In it's own package to avoid exporting everything
package com.one_half_men.app.hurricane_application;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

import com.one_half_men.app.being.Being;
import com.one_half_men.app.db.Key;
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
import com.one_half_men.app.user.User;
import com.one_half_men.app.user.UserQuery;

public class HurricaneApplication {
    public static final Random RANDOM = new SecureRandom();

    private static final HurricaneApplication INSTANCE = new HurricaneApplication();

    private Optional<User> loggedInUser = Optional.empty();

    public static HurricaneApplication getInstance() {
        return INSTANCE;
    }

    public Key<User> createAccount(String username, String password) throws AccountCreationError {
        UserDb db = new UserDb();

        boolean usernameTaken = db.search(
                UserQuery.builder()
                        .username(Optional.of(username))
                        .build())
                .size() > 0;

        if (usernameTaken)
            throw new AccountCreationError.UsernameTaken();

        User.UserBuilder builder = User.builder().username(username);

        Optional<Authentication.Key> login = Authentication.Key.fromPassword(password);
        if (login.isEmpty())
            throw new AccountCreationError.PasswordInvalid();

        builder.login(login.get());

        return db.create(builder.build());
    }

    public User login(String username, String password) throws AccountLoginError {
        UserDb db = new UserDb();

        Set<Key<User>> results = db.search(
                UserQuery.builder()
                        .username(Optional.of(username))
                        .build());

        Optional<Key<User>> first = results.stream().findFirst();
        if (first.isEmpty())
            throw new AccountLoginError.NoAccountFoundError();

        Key<User> key = first.get();
        User user = db.get(key);

        boolean passwordCorrect = user.getLogin().checkPassword(password);

        if (!passwordCorrect)
            throw new AccountLoginError.WrongPasswordError();

        loggedInUser = Optional.of(user);
        return user;
    }

    public void logout() {
        loggedInUser = Optional.empty();
    }

    public void addMember(Being being) {
    }

    public void editUser(Key<User> key, Consumer<User> consumer) throws PermissionsError {
    }

    public User getUser(Key<User> key) {
        return null;
    }

    public Set<Key<User>> searchUser(UserQuery query) {
        return null;
    }

    public Key<Request> createRequest(Request request) {
        return null;
    }

    public void editRequest(Key<Request> key, Consumer<Request> consumer) throws PermissionsError {
    }

    public Request getRequest(Key<Request> key) {
        return null;
    }

    public Set<Key<Request>> searchRequest(RequestQuery query) {
        return null;
    }

    public void closeRequest(Key<Request> key, CloseReason reason) {
    }

    public void claimProxyRequest(Key<Request> request) {
    }

    public void addParticipantToRequest(Key<Request> request) {
    }

    public Key<Shelter> createShelter(Shelter shelter) {
        return null;
    }

    public void editShelter(Key<Shelter> key, Consumer<Shelter> consumer) throws PermissionsError {
    }

    public Shelter getShelter(Key<Shelter> key) {
        return null;
    }

    public Set<Key<Shelter>> searchShelter(ShelterQuery query) {
        return null;
    }

    public void removeShelter(Key<Shelter> key) throws PermissionsError {
    }

    public Hurricane getHurricane(Key<Hurricane> key) {
        return null;
    }

    public Set<Key<Hurricane>> searchHurricane(HurricaneQuery query) {
        return null;
    }
}
