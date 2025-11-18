package com.conexa.challenge.starwars.infrastructure.adapter.out.dto.film;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FilmApiResponse {
    private String uid;

    @JsonProperty("_id")
    private String id;

    private String description;
    private FilmApiProperties properties;
}
