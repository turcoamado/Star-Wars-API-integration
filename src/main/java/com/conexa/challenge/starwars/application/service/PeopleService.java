package com.conexa.challenge.starwars.application.service;

import com.conexa.challenge.starwars.application.port.in.PeopleUseCase;
import com.conexa.challenge.starwars.application.port.out.StarWarsApiPort;

import com.conexa.challenge.starwars.application.service.lookup.BasicLookUpService;
import com.conexa.challenge.starwars.domain.model.People;
import com.conexa.challenge.starwars.domain.model.util.ApiIdExtractor;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.BasicResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.PeopleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PeopleService implements PeopleUseCase {

    private final StarWarsApiPort starWarsApiPort;
    private final BasicLookUpService lookup;

    @Override
    public Page<People> listPeople(Pageable pageable) {
        return starWarsApiPort.listPeople(
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
    }

    @Override
    public PeopleResponse findById(String id) {
        People people = starWarsApiPort.findPeopleById(id);

        List<BasicResponse> films = people.getFilms().stream()
                .map(ApiIdExtractor::extractId)
                .map(lookup::filmBasic)
                .toList();

        List<BasicResponse> vehicles = people.getVehicles().stream()
                .map(ApiIdExtractor::extractId)
                .map(lookup::vehicleBasic)
                .toList();

        List<BasicResponse> starships = people.getStarships().stream()
                .map(ApiIdExtractor::extractId)
                .map(lookup::starshipBasic)
                .toList();

        return new PeopleResponse(
                people.getUid(),
                people.getName(),
                people.getUrl(),
                people.getGender(),
                people.getBirthYear(),
                films,
                vehicles,
                starships
        );
    }

    @Override
    public People findByName(String name, Pageable pageable) {
        return null;
    }
}
