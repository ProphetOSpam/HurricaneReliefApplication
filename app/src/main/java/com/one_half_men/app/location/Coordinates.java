package com.one_half_men.app.location;

import java.io.IOException;
import java.util.Optional;

import io.avaje.jsonb.Json;
import lombok.AllArgsConstructor;
import lombok.Data;

@Json
@AllArgsConstructor
@Data
public class Coordinates implements Location {
    private double latitude;
    private double longitude;

    @Override
    public Coordinates coordinates() {
        return this;
    }

    @Override
    public Optional<State> state() {
        try {
            return MapTools.estimateState(this);
        } catch (IOException | InterruptedException e) {
            return Optional.empty();
        }
    }
}
