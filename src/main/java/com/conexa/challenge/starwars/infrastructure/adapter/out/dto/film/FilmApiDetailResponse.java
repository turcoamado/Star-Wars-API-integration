package com.conexa.challenge.starwars.infrastructure.adapter.out.dto.film;

import lombok.Data;

@Data
public class FilmApiDetailResponse {
    private String uid;
    private String description;
    private FilmApiProperties properties;
}
