package com.afkl.travel.exercise.model;

import java.util.Arrays;
import java.util.Optional;

public enum LocationType {
    COUNTRY("country"),
    CITY("city"),
    AIRPORT("airport");

    private final String type;

    /**
     * @param type
     */
    private LocationType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static Optional<LocationType> fromType(String type) {
        return Arrays.stream(values())
                .filter(bl -> bl.type.equalsIgnoreCase(type))
                .findFirst();
    }
}
