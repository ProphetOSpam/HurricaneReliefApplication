package com.one_half_men.app.being;

import java.time.LocalDate;
import java.util.List;

import io.avaje.jsonb.Json;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Json
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Animal extends Being {
    private Species species;

    public Animal(String firstName, String lastName, LocalDate birthDate, List<SpecialNeed> specialNeeds, Species species) {
        super(firstName, lastName, birthDate, specialNeeds);
        this.species = species;
    }
}
