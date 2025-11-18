package com.conexa.challenge.starwars.infrastructure.adapter.out.dto.starship;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StarshipListApiResponse {
    @JsonProperty("total_records")
    private int totalRecords;

    @JsonProperty("total_pages")
    private int totalPages;

    private String previous;
    private String next;

    @JsonProperty("results")
    private List<StarshipApiResponse> results;
}
