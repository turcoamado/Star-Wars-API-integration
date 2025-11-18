package com.conexa.challenge.starwars.infrastructure.adapter.out.dto.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VehicleListApiResponse {
    @JsonProperty("total_records")
    private int totalRecords;

    @JsonProperty("total_pages")
    private int totalPages;

    private String previous;
    private String next;

    @JsonProperty("results")
    private List<VehicleApiResponse> results;
}
