package com.one_half_men.app;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import com.one_half_men.app.being.Being;
import com.one_half_men.app.db.Key;
import com.one_half_men.app.error.AccountCreationError;
import com.one_half_men.app.error.AccountLoginError;
import com.one_half_men.app.error.PermissionsError;
import com.one_half_men.app.request.CloseReason;
import com.one_half_men.app.hurricane.Hurricane;
import com.one_half_men.app.hurricane.HurricaneQuery;
import com.one_half_men.app.request.Request;
import com.one_half_men.app.request.RequestQuery;
import com.one_half_men.app.shelter.Shelter;
import com.one_half_men.app.shelter.ShelterQuery;
import com.one_half_men.app.user.User;
import com.one_half_men.app.user.UserQuery;

public class HurricaneApplication {
    private Optional<User> loggedInUser;
    private static HurricaneApplication instance = new HurricaneApplication();

    public static HurricaneApplication getInstance() {
        return instance;
    }

    public Key<User> createAccount(String username, String password) throws AccountCreationError {
        return null;
    }

    public User login(String username, String password) throws AccountLoginError {
        return null;
    }

    public void logout() {
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
