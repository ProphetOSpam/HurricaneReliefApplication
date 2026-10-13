package com.one_half_men.app.user;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.one_half_men.annotations.Query;
import com.one_half_men.app.being.Being;
import com.one_half_men.app.being.Person;
import com.one_half_men.app.location.Location;
import com.one_half_men.app.request.Request;
import com.one_half_men.app.shelter.Shelter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Query
public class User {
    private @Setter Person person;
    private @Setter Role role;
    private @Setter Location location;
    private @Builder.Default SortedSet<Being> family = new TreeSet<>();
    private @Builder.Default Set<Request> ownedRequests = new HashSet<>();
    private @Builder.Default Set<Request> participatingRequests = new HashSet<>();
    private @Builder.Default Set<Shelter> ownedShelters = new HashSet<>();
}
