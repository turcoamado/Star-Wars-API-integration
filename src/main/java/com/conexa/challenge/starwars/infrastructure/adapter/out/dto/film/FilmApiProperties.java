package com.conexa.challenge.starwars.infrastructure.adapter.out.dto.film;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class FilmApiProperties {
    private String title;

    @JsonProperty("episode_id")
    private int episodeId;

    private String director;
    private String producer;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("opening_crawl")
    private String openingCrawl;

    @JsonProperty("characters")
    private List<String> people;

    private List<String> planets;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> species;

    private String url;
}
