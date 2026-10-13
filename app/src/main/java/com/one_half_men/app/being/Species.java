package com.one_half_men.app.being;

import io.avaje.jsonb.Json;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Json
@EqualsAndHashCode
@AllArgsConstructor
@Data
public class Species {
    private String value;
}
