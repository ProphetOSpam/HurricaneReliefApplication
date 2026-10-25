package com.one_half_men.app.location;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class MapToolsTest {
    @Test
    public void geocodeToCoordinates() throws IOException, InterruptedException {
        Address rutledge = new Address(
                "902 Sumter St", 
                "Columbia", 
                State.SOUTH_CAROLINA,
                Optional.of(new ZipCode("29208")));
        Coordinates rutledgeCoords = MapTools.estimateCoordinates(rutledge);
        State state = MapTools.estimateState(rutledgeCoords).get();
        assertEquals(state, State.SOUTH_CAROLINA);
    }
}
