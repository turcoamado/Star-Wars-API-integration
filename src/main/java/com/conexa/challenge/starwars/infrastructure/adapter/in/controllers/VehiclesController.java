package com.conexa.challenge.starwars.infrastructure.adapter.in.controllers;

import com.conexa.challenge.starwars.application.port.in.VehicleUseCase;
import com.conexa.challenge.starwars.domain.model.Vehicle;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.BasicResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.VehicleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehiclesController {

    private final VehicleUseCase vehicleUseCase;

    @Operation(
            summary = "Get paginated list of vehicles",
            description = "Retrieves a paginated list of Star Wars vehicles from SWAPI. "
                    + "<br>Supports pagination via query parameters 'page' and 'size'.",
            tags = {"Vehicles"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of vehicles retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BasicResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination parameters",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No films found",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping
    public ResponseEntity<Page<BasicResponse>> list(Pageable pageable) {
        Page<Vehicle> vehicle = vehicleUseCase.listVehicles(pageable);

        Page<BasicResponse> response = vehicle.map(
                v -> new BasicResponse(
                        v.getUid(),
                        v.getName(),
                        v.getUrl()
                )
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get vehicle by ID",
            description = "Retrieves a vehicle",
            tags = {"Vehicles"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Vehicle retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VehicleResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No vehicle found",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(vehicleUseCase.findById(id));
    }
}
