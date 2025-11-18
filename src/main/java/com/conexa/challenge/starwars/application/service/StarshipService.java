package com.conexa.challenge.starwars.application.service;

import com.conexa.challenge.starwars.application.port.in.StarshipUseCase;
import com.conexa.challenge.starwars.application.port.out.StarWarsApiPort;
import com.conexa.challenge.starwars.application.service.lookup.BasicLookUpService;
import com.conexa.challenge.starwars.domain.model.Starship;
import com.conexa.challenge.starwars.domain.model.util.ApiIdExtractor;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.BasicResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.StarshipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StarshipService implements StarshipUseCase {

    private final StarWarsApiPort starWarsApiPort;
    private final BasicLookUpService lookup;

    @Override
    public Page<Starship> listStarships(Pageable pageable) {
        return starWarsApiPort.listStarships(
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
    }

    @Override
    public StarshipResponse findById(String id) {
        Starship starship = starWarsApiPort.findStarshipById(id);

        List<BasicResponse> films = starship.getFilms().stream()
                .map(ApiIdExtractor::extractId)
                .map(lookup::filmBasic)
                .toList();

        return new StarshipResponse(
                starship.getUid(),
                starship.getName(),
                starship.getUrl(),
                starship.getPassengers(),
                starship.getCrew(),
                starship.getModel(),
                starship.getManufacturer(),
                starship.getStarshipClass(),
                starship.getHyperdriveRating(),
                films
        );
    }
}
