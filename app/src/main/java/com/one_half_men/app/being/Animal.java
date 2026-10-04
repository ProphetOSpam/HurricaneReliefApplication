package com.one_half_men.app.being;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Animal extends Being {
    private @Getter @Setter Species species;

    public Animal(String firstName, String lastName, Date birthDate, List<SpecialNeed> specialNeeds, Species species) {
        super(firstName, lastName, birthDate, specialNeeds);
        this.species = species;
    }
}
