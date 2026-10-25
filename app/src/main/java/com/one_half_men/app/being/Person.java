package com.one_half_men.app.being;

import java.time.LocalDate;
import java.util.List;

import io.avaje.jsonb.Json;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Json
@EqualsAndHashCode(callSuper = true)
public class Person extends Being {
    private PhoneNumber phoneNumber;

    public Person(
            String firstName,
            String lastName,
            LocalDate birthDate,
            List<SpecialNeed> specialNeeds,
            PhoneNumber phoneNumber) {
        super(firstName, lastName, birthDate, specialNeeds);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toText() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("- Name: %s %s\n", firstName, lastName));
        sb.append(String.format("- Date of Birth: %s\n", birthDate.toString()));
        sb.append(String.format("- Phone Number: %s\n", phoneNumber.toText()));

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
