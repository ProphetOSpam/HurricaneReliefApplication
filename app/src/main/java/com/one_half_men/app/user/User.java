package com.one_half_men.app.user;

import java.util.HashSet;
import java.util.Set;

import com.one_half_men.annotations.Query;
import com.one_half_men.app.being.Being;
import com.one_half_men.app.being.Person;
import com.one_half_men.app.location.Location;
import com.one_half_men.app.request.Request;
import com.one_half_men.app.shelter.Shelter;

import io.avaje.jsonb.Json;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Json
@Query
@EqualsAndHashCode
@AllArgsConstructor
@Builder
@Getter
public class User {
    private String username;
    private Authentication.Key login;

    private @Setter Person person;
    private @Setter Role role;
    private @Setter Location location;
    private @Builder.Default Set<Specialty> specialties = new HashSet<>();
    private @Builder.Default Set<Being> family = new HashSet<>();
    private @Builder.Default Set<Request> ownedRequests = new HashSet<>();
    private @Builder.Default Set<Request> participatingRequests = new HashSet<>();
    private @Builder.Default Set<Shelter> ownedShelters = new HashSet<>();
}
