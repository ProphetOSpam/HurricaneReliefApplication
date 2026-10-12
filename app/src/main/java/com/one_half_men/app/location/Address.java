package com.one_half_men.app.location;

import java.util.Optional;

import com.one_half_men.app.distance.Distance;

import lombok.AllArgsConstructor;
import lombok.Data;

import io.avaje.jsonb.Json;
import lombok.EqualsAndHashCode;

@Json
@EqualsAndHashCode
@AllArgsConstructor
@Data
public class Address implements Location {
    private String streetAddress;
    private String city;
    private State state;
    private Optional<ZipCode> zipCode;
    private String country;

    public Coordinates estimateCoordinates() {
        return null;
    }

    @Override
    public Distance distanceTo(Location other) {
        return null;
    }
}
