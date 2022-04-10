package com.afkl.travel.exercise.model;

import lombok.Data;

import java.util.Map;

@Data
public class Metrics {

    private Map<String,Integer> counter;

    private Map<String,Long> timer;
}
