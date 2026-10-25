package com.one_half_men.app.location;

import java.io.IOException;
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

    public Coordinates estimateCoordinates() throws IOException, InterruptedException {
        return MapTools.estimateCoordinates(this);
    }

    @Override
    public Distance distanceTo(Location other) {
        return null;
    }

    @Override
    public Optional<State> estimateState() {
        return Optional.of(state);
    }

    public String toText() {
        return String.format("%s, %s, %s %s", streetAddress, city, state.getAbbreviation(), zipCode.get().getValue());
    }
}
