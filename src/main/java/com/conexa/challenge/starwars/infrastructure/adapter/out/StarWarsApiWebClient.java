package com.conexa.challenge.starwars.infrastructure.adapter.out;

import com.conexa.challenge.starwars.application.exception.EmptyListException;
import com.conexa.challenge.starwars.application.exception.ExternalServiceException;
import com.conexa.challenge.starwars.application.exception.ExternalServiceTimeoutException;
import com.conexa.challenge.starwars.application.exception.ResourceNotFoundException;
import com.conexa.challenge.starwars.application.port.out.StarWarsApiPort;
import com.conexa.challenge.starwars.domain.model.Film;
import com.conexa.challenge.starwars.domain.model.People;
import com.conexa.challenge.starwars.domain.model.Starship;
import com.conexa.challenge.starwars.domain.model.Vehicle;
import com.conexa.challenge.starwars.infrastructure.adapter.out.dto.StarshipApiDetailResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.out.dto.film.FilmApiDetailResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.out.dto.film.FilmApiGenericResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.out.dto.film.FilmApiProperties;
import com.conexa.challenge.starwars.infrastructure.adapter.out.dto.film.FilmListApiResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.out.dto.people.PeopleApiDetailResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.out.dto.people.PeopleApiGenericResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.out.dto.people.PeopleApiProperties;
import com.conexa.challenge.starwars.infrastructure.adapter.out.dto.people.PeopleListApiResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.out.dto.starship.StarshipApiGenericResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.out.dto.starship.StarshipApiProperties;
import com.conexa.challenge.starwars.infrastructure.adapter.out.dto.starship.StarshipListApiResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.out.dto.vehicle.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StarWarsApiWebClient implements StarWarsApiPort {

    private final WebClient webClient;

    @Override
    public Page<People> listPeople(int page, int size) {
        try {
            PeopleListApiResponse apiResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/people")
                            .queryParam("page", page)
                            .queryParam("limit", size)
                            .build())
                    .retrieve()
                    .bodyToMono(PeopleListApiResponse.class)
                    .block();

            if (apiResponse == null) {
                throw new ExternalServiceException("Invalid SWAPI response");
            }

            List<People> peopleList = apiResponse.getResults().stream()
                    .map(r -> new People(
                            r.getUid(),
                            r.getName(),
                            r.getUrl(),
                            null,
                            null,
                            null,
                            null,
                            null
                    ))
                    .toList();

            if (peopleList.isEmpty()) {
                throw new EmptyListException("No people found");
            }

            return new PageImpl<>(
                    peopleList,
                    PageRequest.of(page, size),
                    apiResponse.getTotalRecords()
            );
        } catch (WebClientResponseException e) {
            throw new ExternalServiceException("SWAPI returned an error: " + e.getRawStatusCode());
        } catch (WebClientRequestException e) {
            throw new ExternalServiceTimeoutException("SWAPI timeout");
        }
    }

    @Override
    public Page<Film> listFilms(int page, int size) {
        try {
            FilmListApiResponse apiResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/films")
                            .queryParam("page", page)
                            .queryParam("limit", size)
                            .build())
                    .retrieve()
                    .bodyToMono(FilmListApiResponse.class)
                    .block();

            if (apiResponse == null) {
                throw new ExternalServiceException("Invalid SWAPI response");
            }

            List<Film> filmList = apiResponse.getResults().stream()
                    .map(r -> new Film(
                            r.getUid(),
                            r.getProperties().getTitle(),
                            r.getProperties().getUrl(),
                            0,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                    ))
                    .toList();

            if (filmList.isEmpty()) {
                throw new EmptyListException("No films found");
            }

            int total = filmList.size();
            int from = page * size;
            int to = Math.min(from + size, total);

            List<Film> pagedFilms =
                    from > total ? List.of() : filmList.subList(from, to);


            return new PageImpl<>(
                    pagedFilms,
                    PageRequest.of(page, size),
                    total
            );

        } catch (WebClientResponseException e) {
            throw new ExternalServiceException("SWAPI returned an error: " + e.getRawStatusCode());
        } catch (WebClientRequestException e) {
            throw new ExternalServiceTimeoutException("SWAPI timeout");
        }
    }

    @Override
    public Page<Starship> listStarships(int page, int size) {
        try {
            StarshipListApiResponse apiResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/starships")
                            .queryParam("page", page)
                            .queryParam("limit", size)
                            .build())
                    .retrieve()
                    .bodyToMono(StarshipListApiResponse.class)
                    .block();

            if (apiResponse == null) {
                throw new ExternalServiceException("Invalid SWAPI response");
            }

            List<Starship> starshipList = apiResponse.getResults().stream()
                    .map(r-> new Starship(
                            r.getUid(),
                            r.getName(),
                            r.getUrl(),
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                    ))
                    .toList();

            if (starshipList.isEmpty()) {
                throw new EmptyListException("No starships found");
            }

            return new PageImpl<>(
                    starshipList,
                    PageRequest.of(page, size),
                    apiResponse.getTotalRecords()
            );

        } catch (WebClientResponseException e) {
            throw new ExternalServiceException("SWAPI returned an error: " + e.getRawStatusCode());
        } catch (WebClientRequestException e) {
            throw new ExternalServiceTimeoutException("SWAPI timeout");
        }
    }

    @Override
    public Page<Vehicle> listVehicles(int page, int size) {
        try {
            VehicleListApiResponse apiResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/vehicles")
                            .queryParam("page", page)
                            .queryParam("limit", size)
                            .build())
                    .retrieve()
                    .bodyToMono(VehicleListApiResponse.class)
                    .block();

            if (apiResponse == null) {
                throw new ExternalServiceException("Invalid SWAPI response");
            }

            List<Vehicle> vehicleList = apiResponse.getResults().stream()
                    .map(r-> new Vehicle(
                            r.getUid(),
                            r.getName(),
                            r.getUrl(),
                            null,
                            null,
                            null,
                            null,
                            null,
                            null
                    ))
                    .toList();

            if (vehicleList.isEmpty()) {
                throw new EmptyListException("No vehicles found");
            }

            return new PageImpl<>(
                    vehicleList,
                    PageRequest.of(page, size),
                    apiResponse.getTotalRecords()
            );

        } catch (WebClientResponseException e) {
            throw new ExternalServiceException("SWAPI returned an error: " + e.getRawStatusCode());
        } catch (WebClientRequestException e) {
            throw new ExternalServiceTimeoutException("SWAPI timeout");
        }
    }

    @Cacheable(value = "people", key = "#id")
    @Override
    public People findPeopleById(String id) {
        try {
            PeopleApiGenericResponse peopleApiGenericResponse = webClient.get()
                    .uri("/people/{id}", id)
                    .retrieve()
                    .bodyToMono(PeopleApiGenericResponse.class)
                    .block();

            if (peopleApiGenericResponse == null || peopleApiGenericResponse.getResult() == null) {
                throw new ResourceNotFoundException("People with id %s not found".formatted(id));
            }

            PeopleApiDetailResponse result = peopleApiGenericResponse.getResult();
            PeopleApiProperties properties = result.getProperties();

            return new People(
                    result.getUid(),
                    properties.getName(),
                    properties.getUrl(),
                    properties.getGender(),
                    properties.getBirthYear(),
                    properties.getFilms(),
                    properties.getVehicles(),
                    properties.getStarships()
            );
        } catch (WebClientResponseException.NotFound e) {
            throw new ResourceNotFoundException("People with id %s not found in SWAPI".formatted(id));

        } catch (WebClientResponseException e) {
            throw new ExternalServiceException("SWAPI error: " + e.getRawStatusCode());

        } catch (WebClientRequestException e) {
            throw new ExternalServiceTimeoutException("SWAPI connection timeout");
        }
    }

    @Cacheable(value = "starships", key = "#id")
    @Override
    public Starship findStarshipById(String id) {
        try {
            StarshipApiGenericResponse starshipApiGenericResponse = webClient.get()
                    .uri("/starships/{id}", id)
                    .retrieve()
                    .bodyToMono(StarshipApiGenericResponse.class)
                    .block();

            if (starshipApiGenericResponse == null || starshipApiGenericResponse.getResult() == null) {
                throw new ResourceNotFoundException("Starship with id %s not found".formatted(id));
            }

            StarshipApiDetailResponse result = starshipApiGenericResponse.getResult();
            StarshipApiProperties properties = result.getProperties();

            return new Starship(
                    result.getUid(),
                    properties.getName(),
                    properties.getPassengers(),
                    properties.getUrl(),
                    properties.getCrew(),
                    properties.getModel(),
                    properties.getManufacturer(),
                    properties.getStarshipClass(),
                    properties.getHyperdriveRating(),
                    properties.getFilms()
            );

        } catch (WebClientResponseException.NotFound e) {
            throw new ResourceNotFoundException("Starship with id %s not found".formatted(id));

        } catch (WebClientResponseException e) {
            throw new ExternalServiceException("SWAPI error: " + e.getRawStatusCode());

        } catch (WebClientRequestException e) {
            throw new ExternalServiceTimeoutException("SWAPI connection timeout");
        }
    }

    @Cacheable(value = "vehicles", key = "#id")
    @Override
    public Vehicle findVehicleById(String id) {
        try {
            VehicleApiGenericResponse vehicleApiGenericResponse = webClient.get()
                    .uri("/vehicles/{id}", id)
                    .retrieve()
                    .bodyToMono(VehicleApiGenericResponse.class)
                    .block();

            if (vehicleApiGenericResponse == null || vehicleApiGenericResponse.getResult() == null) {
                throw new ResourceNotFoundException("Vehicle with id %s not found".formatted(id));
            }

            VehicleApiDetailResponse result = vehicleApiGenericResponse.getResult();
            VehicleApiProperties properties = result.getProperties();

            return new Vehicle(
                    result.getUid(),
                    properties.getName(),
                    properties.getUrl(),
                    properties.getPassengers(),
                    properties.getCrew(),
                    properties.getModel(),
                    properties.getManufacturer(),
                    properties.getVehicleClass(),
                    properties.getFilms()
            );

        } catch (WebClientResponseException.NotFound e) {
            throw new ResourceNotFoundException("Vehicle with id %s not found".formatted(id));

        } catch (WebClientResponseException e) {
            throw new ExternalServiceException("SWAPI error: " + e.getRawStatusCode());

        } catch (WebClientRequestException e) {
            throw new ExternalServiceTimeoutException("SWAPI connection timeout");
        }
    }

    @Cacheable(value = "films", key = "#id")
    @Override
    public Film findFilmById(String id) {
        try {
            FilmApiGenericResponse filmApiGenericResponse = webClient.get()
                    .uri("/films/{id}", id)
                    .retrieve()
                    .bodyToMono(FilmApiGenericResponse.class)
                    .block();

            if (filmApiGenericResponse == null || filmApiGenericResponse.getResult() == null) {
                throw new ResourceNotFoundException("Film with id %s not found".formatted(id));
            }

            FilmApiDetailResponse result = filmApiGenericResponse.getResult();
            FilmApiProperties properties = result.getProperties();

            return new Film(
                    result.getUid(),
                    properties.getTitle(),
                    properties.getUrl(),
                    properties.getEpisodeId(),
                    properties.getDirector(),
                    properties.getProducer(),
                    properties.getReleaseDate(),
                    properties.getOpeningCrawl(),
                    properties.getPeople(),
                    properties.getPlanets(),
                    properties.getStarships(),
                    properties.getVehicles(),
                    properties.getSpecies()
            );

        } catch (WebClientResponseException.NotFound e) {
            throw new ResourceNotFoundException("Film with id %s not found".formatted(id));

        } catch (WebClientResponseException e) {
            throw new ExternalServiceException("SWAPI error: " + e.getRawStatusCode());

        } catch (WebClientRequestException e) {
            throw new ExternalServiceTimeoutException("SWAPI connection timeout");
        }
    }
}
