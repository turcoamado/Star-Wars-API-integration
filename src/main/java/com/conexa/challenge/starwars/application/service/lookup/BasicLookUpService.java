package com.conexa.challenge.starwars.application.service.lookup;

import com.conexa.challenge.starwars.application.port.out.StarWarsApiPort;
import com.conexa.challenge.starwars.domain.model.Film;
import com.conexa.challenge.starwars.domain.model.People;
import com.conexa.challenge.starwars.domain.model.Starship;
import com.conexa.challenge.starwars.domain.model.Vehicle;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasicLookUpService {

    private final StarWarsApiPort starWarsApiPort;

    public BasicResponse filmBasic(String id) {
        Film film = starWarsApiPort.findFilmById(id);
        return new BasicResponse(
                film.getUid(),
                film.getTitle(),
                film.getUrl()
        );
    }

    public BasicResponse peopleBasic(String id) {
        People people = starWarsApiPort.findPeopleById(id);
        return new BasicResponse(
                people.getUid(),
                people.getName(),
                people.getUrl()
        );
    }

    public BasicResponse vehicleBasic(String id) {
        Vehicle vehicle = starWarsApiPort.findVehicleById(id);
        return new BasicResponse(
                vehicle.getUid(),
                vehicle.getName(),
                vehicle.getUrl()
        );
    }

    public BasicResponse starshipBasic(String id) {
        Starship starship = starWarsApiPort.findStarshipById(id);
        return new BasicResponse(
                starship.getUid(),
                starship.getName(),
                starship.getUrl()
        );
    }
}
