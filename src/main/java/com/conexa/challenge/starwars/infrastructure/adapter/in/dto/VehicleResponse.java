package com.conexa.challenge.starwars.infrastructure.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VehicleResponse {
    private String uid;
    private String name;
    private String url;
    private String passengers;
    private String crew;
    private String model;
    private String manufacturer;
    private String vehicleClass;
    private List<BasicResponse> films;
}
