package com.one_half_men.app.being;

import io.avaje.jsonb.Json;
import lombok.Data;
import lombok.NonNull;

@Json
@Data(staticConstructor = "of")
public class Species {
    @NonNull
    private String value;
}
