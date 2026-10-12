package com.one_half_men.app.being;

import java.util.Date;
import java.util.List;

import io.avaje.jsonb.Json;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Json
@EqualsAndHashCode(callSuper = true)
public class Person extends Being {
    private @Getter @Setter PhoneNumber phoneNumber;

    public Person(String firstName, String lastName, Date birthDate, List<SpecialNeed> specialNeeds, PhoneNumber phoneNumber) {
        super(firstName, lastName, birthDate, specialNeeds);
        this.phoneNumber = phoneNumber;
    }
}
