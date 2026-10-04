package com.one_half_men.app.being;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public abstract class Being {
    private @Getter @Setter String firstName;
    private @Getter @Setter String lastName;
    private @Getter @Setter Date birthDate;
    private @Getter @Setter List<SpecialNeed> specialNeeds;

    public Being(String firstName, String lastName, Date birthDate, List<SpecialNeed> specialNeeds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.specialNeeds = specialNeeds;
    }
}
