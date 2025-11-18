package com.conexa.challenge.starwars.application.service;

import com.conexa.challenge.starwars.application.port.in.VehicleUseCase;
import com.conexa.challenge.starwars.application.port.out.StarWarsApiPort;
import com.conexa.challenge.starwars.application.service.lookup.BasicLookUpService;
import com.conexa.challenge.starwars.domain.model.Vehicle;
import com.conexa.challenge.starwars.domain.model.util.ApiIdExtractor;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.BasicResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.VehicleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiclesService implements VehicleUseCase {

    private final StarWarsApiPort starWarsApiPort;
    private final BasicLookUpService lookup;

    @Override
    public Page<Vehicle> listVehicles(Pageable pageable) {
        return starWarsApiPort.listVehicles(
                pageable.getPageNumber(),
                pageable.getPageSize()
        );
    }

    @Override
    public VehicleResponse findById(String id) {
        Vehicle vehicle = starWarsApiPort.findVehicleById(id);

        List<BasicResponse> films = vehicle.getFilms().stream()
                .map(ApiIdExtractor::extractId)
                .map(lookup::filmBasic)
                .toList();

        return new VehicleResponse(
                vehicle.getUid(),
                vehicle.getName(),
                vehicle.getUrl(),
                vehicle.getPassengers(),
                vehicle.getCrew(),
                vehicle.getModel(),
                vehicle.getManufacturer(),
                vehicle.getVehicleClass(),
                films
        );
    }
}
