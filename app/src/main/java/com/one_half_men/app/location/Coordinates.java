package com.one_half_men.app.location;

import java.io.IOException;
import java.util.Optional;

import com.one_half_men.app.distance.Distance;

import lombok.AllArgsConstructor;
import lombok.Data;

import io.avaje.jsonb.Json;

@Json
@AllArgsConstructor
@Data
public class Coordinates implements Location {
    private double latitude;
    private double longitude;

    public Address estimateAddress() {
        return null;
    }

    @Override
    public Distance distanceTo(Location other) {
        return null;
    }

    @Override
    public Optional<State> estimateState() {
        try {
            return MapTools.estimateState(this);
        } catch (IOException | InterruptedException e) {
            return Optional.empty();
        }
    }
}
