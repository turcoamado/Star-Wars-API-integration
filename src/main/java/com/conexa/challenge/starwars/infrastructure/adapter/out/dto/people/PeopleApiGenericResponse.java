package com.conexa.challenge.starwars.infrastructure.adapter.out.dto.people;

import lombok.Data;

@Data
public class PeopleApiGenericResponse {
    private String message;
    private PeopleApiDetailResponse result;
}
