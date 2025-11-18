package com.conexa.challenge.starwars.application.port.in;

import com.conexa.challenge.starwars.domain.model.Vehicle;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.VehicleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleUseCase {
    Page<Vehicle> listVehicles(Pageable pageable);
    VehicleResponse findById(String id);
}
