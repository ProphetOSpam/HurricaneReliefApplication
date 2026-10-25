package com.one_half_men.app.location;

import java.io.IOException;
import java.util.Optional;

import io.avaje.jsonb.Json;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Json
@AllArgsConstructor
@Builder
@Data
public class Address implements Location {
    private String streetAddress;
    private String city;
    private State state;
    private Optional<ZipCode> zipCode;

    @Override
    public Coordinates coordinates() {
        try {
            return MapTools.estimateCoordinates(this);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<State> state() {
        return Optional.of(state);
    }

    public String toText() {
        return String.format("%s, %s, %s %s", streetAddress, city, state.getAbbreviation(), zipCode.get().getValue());
    }
}
