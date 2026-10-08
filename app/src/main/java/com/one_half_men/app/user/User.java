package com.one_half_men.app.user;

import java.util.Set;
import java.util.SortedSet;

import com.one_half_men.annotations.Query;
import com.one_half_men.app.being.Person;
import com.one_half_men.app.user.Role;
import com.one_half_men.app.location.Location;
import com.one_half_men.app.being.Being;
import com.one_half_men.app.request.Request;
import com.one_half_men.app.shelter.Shelter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Query
@Builder
public class User {
    private @Getter @Setter Person person;
    private @Getter @Setter Role role;
    private @Getter @Setter Location location;
    private @Getter SortedSet<Being> family;
    private @Getter Set<Request> ownedRequests;
    private @Getter Set<Request> participatingRequests;
    private @Getter Set<Shelter> ownedShelters;

    public User(Person person, Role role, Location location, SortedSet<Being> family, Set<Request> ownedRequests, Set<Request> participatingRequests, Set<Shelter> ownedShelters) {
        this.person = person;
        this.role = role;
        this.location = location;
        this.family = family;
        this.ownedRequests = ownedRequests;
        this.participatingRequests = participatingRequests;
        this.ownedRequests = ownedRequests;
    }
}
