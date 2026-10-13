package com.one_half_men.app.being;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public abstract class Being {
    private String firstName;
    private String lastName;
    private Date birthDate;
    private @Setter List<SpecialNeed> specialNeeds;
}
