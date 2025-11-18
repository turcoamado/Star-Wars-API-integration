package com.conexa.challenge.starwars.application.service;

import com.conexa.challenge.starwars.application.port.out.StarWarsApiPort;
import com.conexa.challenge.starwars.application.service.lookup.BasicLookUpService;
import com.conexa.challenge.starwars.domain.model.Film;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.BasicResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.FilmResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilmServiceTest {

    @Mock
    private StarWarsApiPort starWarsApiPort;

    @Mock
    private BasicLookUpService lookup;

    @InjectMocks
    private FilmsService filmsService;

    @Test
    public void listFilms_shouldReturnPagedFilms() {
        Pageable pageable = PageRequest.of(0, 2);
        Film film1 = new Film("1", "A New Hope", "/films/1", 4, "George Lucas", "Lucasfilm", "1977-05-25", "Opening crawl", List.of(), List.of(), List.of(), List.of(), List.of());
        Film film2 = new Film("2", "Empire", "/films/2", 5, "Irvin Kershner", "Lucasfilm", "1980-05-17", "Opening crawl", List.of(), List.of(), List.of(), List.of(), List.of());
        Page<Film> page = new PageImpl<>(List.of(film1, film2));

        when(starWarsApiPort.listFilms(0, 2)).thenReturn(page);

        Page<Film> result = filmsService.listFilms(pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().getFirst().getTitle()).isEqualTo("A New Hope");
    }

    @Test
    public void findById_shouldReturnFilmResponse() {
        Film film = new Film(
                "1",
                "A New Hope",
                "/films/1",
                4,
                "George Lucas",
                "Lucasfilm",
                "1977-05-25",
                "Opening crawl",
                List.of("/people/1"),
                List.of("/planets/1"),
                List.of("/starships/1"),
                List.of("/vehicles/1"),
                List.of("/species/1")
        );

        when(starWarsApiPort.findFilmById("1")).thenReturn(film);
        when(lookup.peopleBasic("1")).thenReturn(new BasicResponse("1", "Luke", "/people/1"));
        when(lookup.vehicleBasic("1")).thenReturn(new BasicResponse("1", "Sand Crawler", "/vehicle/1"));
        when(lookup.starshipBasic("1")).thenReturn(new BasicResponse("1", "X-Wing","/starships/1"));

        FilmResponse response = filmsService.findById("1");

        assertThat(response.getTitle()).isEqualTo("A New Hope");
        assertThat(response.getPeople()).extracting("name").containsExactly("Luke");
        assertThat(response.getVehicles()).extracting("name").containsExactly("Sand Crawler");
        assertThat(response.getStarships()).extracting("name").containsExactly("X-Wing");
    }

    @Test
    public void findById_shouldHandleEmptyLists() {
        Film film = new Film(
                "2",
                "Empire",
                "/films/2",
                5,
                "Irvin Kershner",
                "Lucasfilm",
                "1980-05-17",
                "Opening crawl",
                List.of(),
                List.of(),
                List.of(),
                List.of(),
                List.of()
        );

        when(starWarsApiPort.findFilmById("2")).thenReturn(film);

        FilmResponse response = filmsService.findById("2");

        assertThat(response.getPeople()).isEmpty();
        assertThat(response.getVehicles()).isEmpty();
        assertThat(response.getStarships()).isEmpty();
    }

}
