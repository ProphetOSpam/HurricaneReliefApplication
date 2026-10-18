package com.one_half_men.app.being;

import io.avaje.jsonb.Json;
import lombok.AllArgsConstructor;
import lombok.Data;

@Json
@AllArgsConstructor
@Data
public class Species {
    private String value;
}
