package com.conexa.challenge.starwars.infrastructure.adapter.out.dto.vehicle;

import lombok.Data;

@Data
public class VehicleApiGenericResponse {
    private String message;
    private VehicleApiDetailResponse result;
}
