package com.conexa.challenge.starwars.infrastructure.adapter.out.dto.film;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FilmListApiResponse {
    private String message;

    @JsonProperty("result")
    private List<FilmApiResponse> results;
}
