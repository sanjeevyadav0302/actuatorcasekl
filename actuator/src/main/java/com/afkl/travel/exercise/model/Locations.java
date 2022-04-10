package com.afkl.travel.exercise.model;

import lombok.Data;

import java.util.Optional;

@Data
public class Locations {

    private String code;

    private String name;

    private Optional<LocationType> type;

    private Double latitude;

    private Double longitude;

    private String description;

    private String parentCode;

    private Optional<LocationType> parentType;
}
