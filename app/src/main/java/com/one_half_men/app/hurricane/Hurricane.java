package com.one_half_men.app.hurricane;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import com.one_half_men.app.location.Location;
import com.one_half_men.app.location.State;

import io.avaje.jsonb.Json;

import com.one_half_men.annotations.Query;

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
    private @Getter @Setter Date formed;
    private @Getter @Setter Optional<Date> dissipated;
    private @Getter Set<State> affectedAreas;

    public boolean isAffected(Location location) {
        return affectedAreas.contains(location.getState());
    }
}
