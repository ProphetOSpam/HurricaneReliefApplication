package com.one_half_men.app.location;

import io.avaje.jsonb.Json;
import lombok.AllArgsConstructor;
import lombok.Data;

@Json
@AllArgsConstructor
@Data
public class ZipCode {
    private String value;
}
