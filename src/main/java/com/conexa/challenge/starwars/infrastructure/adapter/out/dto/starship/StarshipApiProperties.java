package com.conexa.challenge.starwars.infrastructure.adapter.out.dto.starship;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class StarshipApiProperties {
    private String name;
    private String passengers;
    private String crew;
    private String model;
    private String manufacturer;

    @JsonProperty("starship_class")
    private String starshipClass;

    @JsonProperty("hyperdrive_rating")
    private String hyperdriveRating;

    private List<String> films; //Esta mal, tiene que ser <FilmApiResponse>
    private String url;
}
