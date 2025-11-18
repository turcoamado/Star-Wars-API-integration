package com.conexa.challenge.starwars.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Film {
    private String uid;
    private String title;
    private String url;
    private int episodeId;
    private String director;
    private String producer;
    private String releaseDate;
    private String openingCrawl;
    private List<String> people;
    private List<String> planets;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> species;
}
