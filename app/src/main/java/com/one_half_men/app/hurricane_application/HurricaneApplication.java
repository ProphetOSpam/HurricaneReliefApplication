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

    @Getter
    private Optional<User> loggedInUser = Optional.empty();

    public static HurricaneApplication getInstance() {
        return INSTANCE;
    }

    // We cannot use the normal singleton instances if we want to test these, so the
    // instance is specified when calling the method

    public Key<User> createAccount(UserDb db, String username, String password) throws AccountCreationError {
        boolean usernameTaken = db.search(
                UserQuery.builder()
                        .username(Optional.of(username))
                        .build())
                .size() > 0;

        if (usernameTaken)
            throw new AccountCreationError.UsernameTaken();

        if (!Username.isValid(username))
            throw new AccountCreationError.UsernameInvalid();

        User.UserBuilder builder = User.builder().username(username);

        Optional<Authentication.Key> login = Authentication.Key.fromPassword(password);
        if (login.isEmpty())
            throw new AccountCreationError.PasswordInvalid();

        builder.login(login.get());

        return db.create(builder.build());
    }

    public User login(UserDb db, String username, String password) throws AccountLoginError {
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
        loggedInUser.get().getFamily().add(being);
    }

    public void editUser(User user, Consumer<User> consumer) throws PermissionsError {
        consumer.accept(user);
    }

    public User getUser(UserDb db, Key<User> key) {
        return db.get(key);
    }

    public Set<Key<User>> searchUser(UserDb db, UserQuery query) {
        return db.search(query);
    }

    public Key<Request> createRequest(RequestDb db, Request request) {
        return db.create(request);
    }

    public void editRequest(Request request, Consumer<Request> consumer) throws PermissionsError {
        if (request.getEditor() != loggedInUser.get()) {
            throw new PermissionsError();
        }

        consumer.accept(request);
    }

    public Request getRequest(RequestDb db, Key<Request> key) {
        return db.get(key);
    }

    public Set<Key<Request>> searchRequest(RequestDb db, RequestQuery query) {
        return db.search(query);
    }

    public void closeRequest(RequestDb db, Key<Request> key, CloseReason reason) {
        db.get(key).close(reason);
    }

    public void claimProxyRequest(RequestDb db, Key<Request> key) {
        db.get(key).claimProxy(loggedInUser.get());
    }

    public void addParticipantToRequest(RequestDb db, Key<Request> key) {
        db.get(key).addParticipant(loggedInUser.get());
    }

    public Key<Shelter> createShelter(ShelterDb db, Shelter shelter) {
        return db.create(shelter);
    }

    public void editShelter(ShelterDb db, Shelter shelter, Consumer<Shelter> consumer) throws PermissionsError {
        if (shelter.getOwner() != loggedInUser.get()) {
            throw new PermissionsError();
        }

        consumer.accept(shelter);
    }

    public Shelter getShelter(ShelterDb db, Key<Shelter> key) {
        return db.get(key);
    }

    public Set<Key<Shelter>> searchShelter(ShelterDb db, ShelterQuery query) {
        return db.search(query);
    }

    public void removeShelter(ShelterDb db, Key<Shelter> key) throws PermissionsError {
        Shelter shelter = getShelter(db, key);

        if (shelter.getOwner() != loggedInUser.get()) {
            throw new PermissionsError();
        }

        db.remove(key);
    }

    public Hurricane getHurricane(HurricaneDb db, Key<Hurricane> key) {
        return db.get(key);
    }

    public Set<Key<Hurricane>> searchHurricane(HurricaneDb db, HurricaneQuery query) {
        return db.search(query);
    }
}
