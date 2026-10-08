package com.one_half_men.app.location;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

public class Address implements Location {
    private @Getter @Setter String streetAddress;
    private @Getter @Setter String city;
    private @Getter @Setter State state;
    private @Getter @Setter Optional<ZipCode> zipCode;
    private @Getter @Setter String country;

    public Address(String streetAddress, String city, State state, String country) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public Coordinates estimateCoordinates() {
        return null;
    }

    @Override
    public Distance distanceto(Location other) {
        return null;
    }
}
