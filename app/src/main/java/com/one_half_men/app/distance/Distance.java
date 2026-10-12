package com.one_half_men.app.distance;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class Distance {
    // inspired in concept by
    // https://github.com/nholthaus/units/blob/bd88eb43036d1e47b3c983ee93019e759b433eeb/include/units/length.h#L61
    @AllArgsConstructor
    public static enum Unit {
        METERS(new Ratio(1, 1)),
        KILOMETERS(new Ratio(1000, 1)),
        FEET(new Ratio(381, 1250)),
        MILES(new Ratio(50, 80467)),
        ;

        public final Ratio ratio;

        public double fromMeters(double meters) {
            return meters * ratio.numerator() / ratio.denominator();
        }
    }

    private final double meters;

    public double asMeters() {
        return meters;
    }

    public double asUnit(Unit unit) {
        return unit.fromMeters(meters);
    }
}
