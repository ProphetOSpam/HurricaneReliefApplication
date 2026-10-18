package com.one_half_men.app.location;

import com.one_half_men.app.distance.Distance;

import io.avaje.jsonb.Json;

@Json
@Json.SubType(type = Address.class)
@Json.SubType(type = Coordinates.class)
public interface Location {
    public Distance distanceTo(Location other);
}
