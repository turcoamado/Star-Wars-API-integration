package com.conexa.challenge.starwars.infrastructure.adapter.out.dto;

import com.conexa.challenge.starwars.infrastructure.adapter.out.dto.starship.StarshipApiProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StarshipApiDetailResponse {
    private String uid;
    private String description;

    @JsonProperty("properties")
    private StarshipApiProperties properties;
}
