package com.conexa.challenge.starwars.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Starship {
    private String uid;
    private String name;
    private String url;
    private String passengers;
    private String crew;
    private String model;
    private String manufacturer;
    private String starshipClass;
    private String hyperdriveRating;
    private List<String> films;
}
