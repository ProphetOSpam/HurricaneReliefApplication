package com.one_half_men.app.being;

import io.avaje.jsonb.Json;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Json
@EqualsAndHashCode
public class SpecialNeed {
    private @Getter @Setter String name;
    private @Getter @Setter String description;

    public SpecialNeed(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
