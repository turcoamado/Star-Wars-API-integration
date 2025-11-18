package com.conexa.challenge.starwars.infrastructure.adapter.out.dto.starship;

import com.conexa.challenge.starwars.infrastructure.adapter.out.dto.StarshipApiDetailResponse;
import lombok.Data;

@Data
public class StarshipApiGenericResponse {
    private String message;
    private StarshipApiDetailResponse result;
}
