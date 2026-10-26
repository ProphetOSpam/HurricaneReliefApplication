package com.one_half_men.app.location;

import io.avaje.jsonb.Json;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

/**
 * Zip Code wrapper type
 */
@Data
public class ZipCode {
    @NonNull
    @Getter(onMethod_ = { @Json.Value })
    private String value;
}
