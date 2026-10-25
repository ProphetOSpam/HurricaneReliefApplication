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

    public Animal(String firstName, String lastName, LocalDate birthDate, List<SpecialNeed> specialNeeds,
            Species species) {
        super(firstName, lastName, birthDate, specialNeeds);
        this.species = species;
    }

    @Override
    public String toText() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("- Species: %s\n", species.getValue()));
        sb.append(String.format("- Name: %s %s\n", firstName, lastName));
        sb.append(String.format("- Date of Birth: %s\n", birthDate.toString()));

        if (!specialNeeds.isEmpty()) {
            sb.append(String.format(
                    "- Special Needs:\n  - %s\n",
                    String.join(
                            "\n  - ",
                            specialNeeds.stream().map(SpecialNeed::toText).toList())));
        }

        return sb.toString();
    }
}
