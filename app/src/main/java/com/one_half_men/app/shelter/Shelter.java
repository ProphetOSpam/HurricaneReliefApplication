package com.one_half_men.app.shelter;

import java.util.Set;

import com.one_half_men.annotations.Query;
import com.one_half_men.app.being.Species;
import com.one_half_men.app.location.Location;
import com.one_half_men.app.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Query
@Builder
public class Shelter {
    private @Getter @Setter User owner;
    private @Getter @Setter String name;
    private @Getter @Setter Location location;
    private @Getter @Setter Set<Resource> resources;
    private @Getter @Setter Set<Accomodation> accomodations;
    private @Getter @Setter Set<Species> allowedPets;
}
