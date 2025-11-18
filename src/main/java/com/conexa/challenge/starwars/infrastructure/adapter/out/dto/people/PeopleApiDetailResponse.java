package com.conexa.challenge.starwars.infrastructure.adapter.out.dto.people;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PeopleApiDetailResponse {
    private String uid;
    private String description;

    @JsonProperty("properties")
    private PeopleApiProperties properties;
}
