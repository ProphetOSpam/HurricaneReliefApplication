package com.one_half_men.app.user;

import java.util.HashSet;
import java.util.Set;

import com.one_half_men.app.being.Species;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

public sealed interface Specialty {
    @AllArgsConstructor
    @Data
    public static final class Transportation implements Specialty {
        int capacity;
        Set<Species> allowedPets = new HashSet<>();
    }

    @Data
    public static final class Unspecified implements Specialty {
        @NonNull
        String value;
    }
}
