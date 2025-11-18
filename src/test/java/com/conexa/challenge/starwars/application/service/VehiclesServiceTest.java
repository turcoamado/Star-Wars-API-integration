package com.conexa.challenge.starwars.application.service;

import com.conexa.challenge.starwars.application.port.out.StarWarsApiPort;
import com.conexa.challenge.starwars.application.service.lookup.BasicLookUpService;
import com.conexa.challenge.starwars.domain.model.Vehicle;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.BasicResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.VehicleResponse;
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
public class VehiclesServiceTest {

    @Mock
    private StarWarsApiPort starWarsApiPort;

    @Mock
    private BasicLookUpService lookup;

    @InjectMocks
    private VehiclesService vehiclesService;

    @Test
    public void listVehicles_shouldReturnPagedVehicles() {
        Pageable pageable = PageRequest.of(1, 2);

        Vehicle v1 = new Vehicle(
                "20", "Speeder", "/vehicles/20",
                "1", "1", "74-Z", "Imperial", "Speeder",
                List.of()
        );

        Vehicle v2 = new Vehicle(
                "21", "AT-AT", "/vehicles/21",
                "40", "4", "All Terrain", "Imperial", "Walker",
                List.of()
        );

        Page<Vehicle> page = new PageImpl<>(List.of(v1, v2));

        when(starWarsApiPort.listVehicles(1, 2)).thenReturn(page);

        Page<Vehicle> result = vehiclesService.listVehicles(pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().getFirst().getName()).isEqualTo("Speeder");
    }

    @Test
    public void findById_shouldReturnVehicleResponse() {
        Vehicle vehicle = new Vehicle(
                "30",
                "Snowspeeder",
                "/vehicles/30",
                "2",
                "2",
                "t-47",
                "Incom",
                "Air Speeder",
                List.of("/films/2", "/films/3")
        );

        when(starWarsApiPort.findVehicleById("30")).thenReturn(vehicle);
        when(lookup.filmBasic("2")).thenReturn(new BasicResponse("2", "Empire", "/films/2"));
        when(lookup.filmBasic("3")).thenReturn(new BasicResponse("3", "Jedi", "/films/3"));

        VehicleResponse response = vehiclesService.findById("30");

        assertThat(response.getName()).isEqualTo("Snowspeeder");
        assertThat(response.getFilms()).extracting("name")
                .containsExactly("Empire", "Jedi");
    }

    @Test
    public void findById_shouldHandleEmptyLists() {
        Vehicle vehicle = new Vehicle(
                "50",
                "Unknown Vehicle",
                "/vehicles/50",
                null, null, null, null, null,
                List.of()
        );

        when(starWarsApiPort.findVehicleById("50")).thenReturn(vehicle);

        VehicleResponse response = vehiclesService.findById("50");

        assertThat(response.getFilms()).isEmpty();
    }
}
