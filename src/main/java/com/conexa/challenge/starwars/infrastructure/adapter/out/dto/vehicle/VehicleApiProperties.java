package com.conexa.challenge.starwars.infrastructure.adapter.out.dto.vehicle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class VehicleApiProperties {
    private String name;
    private String passengers;
    private String crew;
    private String model;
    private String manufacturer;

    @JsonProperty("vehicle_class")
    private String vehicleClass;

    private List<String> films; //Esta mal, tiene que ser <FilmApiResponse>
    private String url;
}
