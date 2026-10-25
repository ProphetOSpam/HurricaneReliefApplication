package com.one_half_men.app.location;

import java.util.List;

/**
 * DrivingRoute
 */
public record DrivingRoute(Location start, Location end, List<Coordinates> points) {
}
