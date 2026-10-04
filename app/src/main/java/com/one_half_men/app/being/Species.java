package com.one_half_men.app.being;

import lombok.Getter;
import lombok.Setter;

public class Species {
    private @Getter @Setter String value;

    public Species(String value) {
        this.value = value;
    }
}
