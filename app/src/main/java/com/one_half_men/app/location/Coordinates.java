package com.one_half_men.app.location;

public class Coordinates implements Location {
    private double latitude;
    private double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Address estimateAddress() {
        return null;
    }

    @Override
    public Distance distanceto(Location other) {
        return null;
    }
}
