package com.one_half_men.app.location;

import io.avaje.jsonb.Json;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Json
@EqualsAndHashCode
@Getter
@Setter
public class Distance {
    private double meters;

    public Distance(double meters) {
        this.meters = meters;
    }

    public double asMeters() {
        return 0;
    }

    public double asKilometers() {
        return 0;
    }

    public double asFeet() {
        return 0;
    }

    public double asMiles() {
        return 0;
    }
}
