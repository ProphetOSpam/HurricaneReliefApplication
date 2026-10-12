package com.one_half_men.app.being;

import io.avaje.jsonb.Json;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Json
@EqualsAndHashCode
public class Species {
    private @Getter @Setter String value;

    public Species(String value) {
        this.value = value;
    }
}
