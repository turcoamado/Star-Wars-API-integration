package com.conexa.challenge.starwars.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private String uid;
    private String name;
    private String url;
    private String passengers;
    private String crew;
    private String model;
    private String manufacturer;
    private String vehicleClass;
    private List<String> films;
}
