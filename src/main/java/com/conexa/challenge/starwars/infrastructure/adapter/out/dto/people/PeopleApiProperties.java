package com.conexa.challenge.starwars.infrastructure.adapter.out.dto.people;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PeopleApiProperties {
    private String name;
    private String gender;

    @JsonProperty("birth_year")
    private String birthYear;

    private List<String> vehicles;
    private List<String> starships;
    private List<String> films;
    private String url;
}
