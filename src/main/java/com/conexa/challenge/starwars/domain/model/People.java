package com.conexa.challenge.starwars.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class People {
    private String uid;
    private String name;
    private String url;
    private String gender;
    private String birthYear;
    private List<String> films;
    private List<String> vehicles;
    private List<String> starships;
}
