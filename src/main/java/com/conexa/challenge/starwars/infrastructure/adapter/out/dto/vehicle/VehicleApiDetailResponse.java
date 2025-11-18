package com.conexa.challenge.starwars.infrastructure.adapter.out.dto.vehicle;

import lombok.Data;

@Data
public class VehicleApiDetailResponse {
    private String uid;
    private String description;
    private VehicleApiProperties properties;
}
