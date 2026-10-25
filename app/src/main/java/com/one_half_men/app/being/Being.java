package com.one_half_men.app.being;

import java.time.LocalDate;
import java.util.List;

import com.one_half_men.app.ToText;

import io.avaje.jsonb.Json;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Json
@Json.SubType(type = Animal.class)
@Json.SubType(type = Person.class)
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public abstract class Being implements ToText {
    protected String firstName;
    protected String lastName;
    protected LocalDate birthDate;
    protected @Setter List<SpecialNeed> specialNeeds;
}
