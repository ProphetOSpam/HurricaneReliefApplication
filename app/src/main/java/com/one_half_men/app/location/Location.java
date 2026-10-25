package com.one_half_men.app.location;

import java.util.Optional;

import com.one_half_men.app.ToText;
import com.one_half_men.app.distance.Distance;

import io.avaje.jsonb.Json;

@Json
@Json.SubType(type = Address.class)
@Json.SubType(type = Coordinates.class)
public interface Location extends ToText {
    // TODO: actually do this
    public default Distance distanceTo(Location other) {
        return Distance.of(Distance.Unit.METERS, 0);
    }

    public Coordinates coordinates();

    public Optional<State> estimateState();
}
