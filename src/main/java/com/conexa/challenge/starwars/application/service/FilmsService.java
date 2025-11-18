package com.conexa.challenge.starwars.application.service;

import com.conexa.challenge.starwars.application.port.in.FilmUseCase;
import com.conexa.challenge.starwars.application.port.out.StarWarsApiPort;
import com.conexa.challenge.starwars.application.service.lookup.BasicLookUpService;
import com.conexa.challenge.starwars.domain.model.Film;
import com.conexa.challenge.starwars.domain.model.util.ApiIdExtractor;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.BasicResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.FilmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmsService implements FilmUseCase {

    private final StarWarsApiPort starWarsApiPort;
    private final BasicLookUpService lookup;

    @Override
    public Page<Film> listFilms(Pageable pageable) {
        return starWarsApiPort.listFilms(
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
    }

    @Override
    public FilmResponse findById(String id) {
        Film film = starWarsApiPort.findFilmById(id);

        List<BasicResponse> people = film.getPeople().stream()
                .map(ApiIdExtractor::extractId)
                .map(lookup::peopleBasic)
                .toList();

        List<BasicResponse> vehicles = film.getVehicles().stream()
                .map(ApiIdExtractor::extractId)
                .map(lookup::vehicleBasic)
                .toList();

        List<BasicResponse> starships = film.getStarships().stream()
                .map(ApiIdExtractor::extractId)
                .map(lookup::starshipBasic)
                .toList();

        return new FilmResponse(
                film.getUid(),
                film.getTitle(),
                film.getUrl(),
                film.getEpisodeId(),
                film.getDirector(),
                film.getProducer(),
                film.getReleaseDate(),
                film.getOpeningCrawl(),
                people,
                film.getPlanets(),
                starships,
                vehicles,
                film.getSpecies()
        );
    }
}
