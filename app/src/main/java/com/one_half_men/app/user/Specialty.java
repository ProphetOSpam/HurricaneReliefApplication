package com.one_half_men.app.user;

import java.util.HashSet;
import java.util.Set;

import com.one_half_men.app.being.Species;

import io.avaje.jsonb.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Json
@Json.SubType(type = Specialty.Transportation.class)
@Json.SubType(type = Specialty.Unspecified.class)
public sealed interface Specialty {
    @Json
    @AllArgsConstructor
    @Data
    public static final class Transportation implements Specialty {
        int capacity;
        Set<Species> allowedPets = new HashSet<>();
    }

    @Json
    @Data
    public static final class Unspecified implements Specialty {
        @NonNull
        String value;
    }
}
