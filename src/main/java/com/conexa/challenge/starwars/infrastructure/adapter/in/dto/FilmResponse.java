package com.conexa.challenge.starwars.infrastructure.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FilmResponse {
    private String uid;
    private String title;
    private String url;
    private int episodeId;
    private String director;
    private String producer;
    private String releaseDate;
    private String openingCrawl;
    private List<BasicResponse> people;
    private List<String> planets;
    private List<BasicResponse> starships;
    private List<BasicResponse> vehicles;
    private List<String> species;
}
