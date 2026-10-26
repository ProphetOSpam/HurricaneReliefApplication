package com.one_half_men.app.location;

import java.util.Optional;

import com.one_half_men.app.ToText;

import io.avaje.jsonb.Json;

@Json
@Json.SubType(type = Address.class)
@Json.SubType(type = Coordinates.class)
public interface Location extends ToText {
    public Coordinates coordinates();

    public Optional<State> estimateState();
}
