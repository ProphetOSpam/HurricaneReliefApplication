package com.one_half_men.app.location;

import com.one_half_men.app.distance.Distance;

public interface Location {
    public Distance distanceTo(Location other);
}
