package com.conexa.challenge.starwars.infrastructure.adapter.out.dto.film;

import lombok.Data;

@Data
public class FilmApiGenericResponse {
    private String message;
    private FilmApiDetailResponse result;
}
