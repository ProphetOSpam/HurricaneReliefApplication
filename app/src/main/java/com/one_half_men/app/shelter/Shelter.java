package com.one_half_men.app.shelter;

import java.util.HashSet;
import java.util.Set;

import com.one_half_men.annotations.Query;
import com.one_half_men.app.being.Species;
import com.one_half_men.app.location.Location;
import com.one_half_men.app.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Query
public class Shelter {
    private @Setter User owner;
    private @Setter String name;
    private @Setter Location location;
    private @Builder.Default Set<Resource> resources = new HashSet<>();
    private @Builder.Default Set<Accomodation> accomodations = new HashSet<>();
    private @Builder.Default Set<Species> allowedPets = new HashSet<>();
}
