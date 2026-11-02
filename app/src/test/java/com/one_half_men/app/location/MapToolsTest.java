package com.one_half_men.app.location;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * @author Jayce Bak
 */
public class MapToolsTest {
    @Test
    public void addressToCoordinates() throws IOException, InterruptedException {
        Address rutledge = new Address(
                "902 Sumter St", 
                "Columbia", 
                State.SOUTH_CAROLINA,
                Optional.of(new ZipCode("29208")));
        Coordinates rutledgeCoords = MapTools.estimateCoordinates(rutledge);
        State rutledgeState = MapTools.estimateState(rutledgeCoords).get();
        assertEquals(State.SOUTH_CAROLINA, rutledgeState);

        Address brown = new Address(
            "1001 Avenida De Las Americas", 
            "Houston", 
            State.TEXAS, 
            Optional.of(new ZipCode("77010")));
        Coordinates brownCoords = MapTools.estimateCoordinates(brown);
        State brownState = MapTools.estimateState(brownCoords).get();
        assertEquals(State.TEXAS, brownState);

        Address nonexistent = new Address(
            "1001 Avenida De Las Americas", 
            "Houston", 
            State.ALASKA, 
            Optional.of(new ZipCode("77010")));
        Coordinates nonexistentCoords = MapTools.estimateCoordinates(nonexistent);
        assertEquals(null, nonexistentCoords);

        Coordinates nullCoords = MapTools.estimateCoordinates(null);
        assertEquals(null, nullCoords);
    }

    @Test
    public void coordinatesToState() throws IOException, InterruptedException {
        Coordinates columbia = new Coordinates(34.00474300990445, -81.04420636514249);
        State columbiaState = MapTools.estimateState(columbia).get();
        assertEquals(State.SOUTH_CAROLINA, columbiaState);

        Coordinates nullIsland = new Coordinates(0, 0);
        State nullIslandState = MapTools.estimateState(nullIsland).get();
        assertEquals(null, nullIslandState);

        Coordinates canada = new Coordinates(45.42348689869027, -75.70829624398654);
        State canadaState = MapTools.estimateState(canada).get();
        assertEquals(null, canadaState);

        State nullState = MapTools.estimateState(null).get();
        assertEquals(null, nullState);
    }
}
