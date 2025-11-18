package com.conexa.challenge.starwars.infrastructure.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PeopleResponse {
    private String uid;
    private String name;
    private String url;
    private String gender;
    private String birthYear;
    private List<BasicResponse> films;
    private List<BasicResponse> vehicles;
    private List<BasicResponse> starships;
}
