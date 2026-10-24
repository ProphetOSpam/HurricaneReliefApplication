package com.one_half_men.app.location;

import java.util.Optional;

import com.one_half_men.app.distance.Distance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import io.avaje.jsonb.Json;

@Json
@AllArgsConstructor
@Builder
@Data
public class Address implements Location {
    private String streetAddress;
    private String city;
    private State state;
    private Optional<ZipCode> zipCode;

    public Coordinates estimateCoordinates() {
        return null;
    }

    @Override
    public Distance distanceTo(Location other) {
        return null;
    }

    @Override
    public State getState() {
        return state;
    }
}
