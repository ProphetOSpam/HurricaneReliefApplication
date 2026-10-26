package com.one_half_men.app.hurricane;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import com.one_half_men.annotations.Query;
import com.one_half_men.app.location.Location;
import com.one_half_men.app.location.State;

import io.avaje.jsonb.Json;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Json
@Query
@AllArgsConstructor
@Builder
public class Hurricane {
    private @Getter @Setter String name;
    private @Getter @Setter LocalDate formed;
    private @Getter @Setter Optional<LocalDate> dissipated;
    private @Getter Set<State> affectedAreas;

    public boolean isAffected(Location location) {
        Optional<State> state = location.estimateState();
        return state.isPresent() && affectedAreas.contains(state.get());
    }
}
