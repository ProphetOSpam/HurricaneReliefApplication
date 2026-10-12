package com.one_half_men.app.location;

import com.one_half_men.app.distance.Distance;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Coordinates implements Location {
    private double latitude;
    private double longitude;

    public Address estimateAddress() {
        return null;
    }

    @Override
    public Distance distanceTo(Location other) {
        return null;
    }
}
