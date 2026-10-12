package com.one_half_men.app.location;

import io.avaje.jsonb.Json;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Json
@EqualsAndHashCode
public class ZipCode {
    private @Getter @Setter String value;

    public ZipCode(String value) {
        this.value = value;
    }
}
