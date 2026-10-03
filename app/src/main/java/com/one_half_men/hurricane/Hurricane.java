package com.one_half_men.hurricane;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import com.one_half_men.location.State;
import com.one_half_men.annotations.Query;
import lombok.Getter;
import lombok.Setter;

@Query
public class Hurricane {
    private @Getter @Setter String name;
    private @Getter @Setter Date formed;
    private @Getter @Setter Optional<Date> dissipated;
    private @Getter @Setter Set<State> affectedAreas;
}
