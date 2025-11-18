package com.conexa.challenge.starwars.application.port.out;

import com.conexa.challenge.starwars.domain.model.Film;
import com.conexa.challenge.starwars.domain.model.People;
import com.conexa.challenge.starwars.domain.model.Starship;
import com.conexa.challenge.starwars.domain.model.Vehicle;
import org.springframework.data.domain.Page;

public interface StarWarsApiPort {
    Page<People> listPeople(int page, int size);

    Page<Film> listFilms(int page, int size);

    Page<Starship> listStarships(int page, int size);

    Page<Vehicle> listVehicles(int page, int size);

    People findPeopleById(String id);

    Starship findStarshipById(String id);

    Vehicle findVehicleById(String id);

    Film findFilmById(String id);
}
