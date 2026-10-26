package com.one_half_men.app.shelter;

import java.util.Set;

import com.one_half_men.app.being.Species;

import io.avaje.jsonb.Json;
import lombok.Data;
import lombok.NonNull;

@Json
@Json.SubType(type = Resource.AllowedPets.class)
@Json.SubType(type = Resource.AnimalCapacity.class)
@Json.SubType(type = Resource.HumanCapacity.class)
@Json.SubType(type = Resource.MedicalStaff.class)
@Json.SubType(type = Resource.WheelchairAccessible.class)
@Json.SubType(type = Resource.Custom.class)
public sealed interface Resource {
    @Data
    public static final class AllowedPets implements Resource {
        @NonNull
        Set<Species> pets;
    }

    public static interface Remaining {
        public int remaining();
    }

    @Data
    public static final class AnimalCapacity implements Resource, Remaining {
        int current;
        int max;

        public AnimalCapacity(int max) {
            this.max = max;
        }

        @Override
        public int remaining() {
            return max - current;
        }
    }

    @Data
    public static final class HumanCapacity implements Resource, Remaining {
        int current;
        int max;

        public HumanCapacity(int max) {
            this.max = max;
        }

        @Override
        public int remaining() {
            return max - current;
        }
    }

    @Data
    public static final class MedicalStaff implements Resource {
    }

    @Data
    public static final class WheelchairAccessible implements Resource {
    }

    @Data
    public static final class Custom implements Resource {
        @NonNull
        String value;
    }
}
