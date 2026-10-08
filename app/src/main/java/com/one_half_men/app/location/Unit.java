package com.one_half_men.app.location;

public enum Unit {
    METERS(1),
    KILOMETERS(1000),
    FEET(.3048),
    MILES(1609.34);

    private double convert;

    private Unit(double convert) {
        this.convert = convert;
    }
}
