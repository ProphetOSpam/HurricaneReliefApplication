package com.one_half_men.app.location;

import io.avaje.jsonb.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class ZipCode {
    @Getter(onMethod_ = { @Json.Value })
    private String value;
}
