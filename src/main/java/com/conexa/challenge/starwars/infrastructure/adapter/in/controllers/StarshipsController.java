package com.conexa.challenge.starwars.infrastructure.adapter.in.controllers;

import com.conexa.challenge.starwars.application.port.in.StarshipUseCase;
import com.conexa.challenge.starwars.domain.model.Starship;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.BasicResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.StarshipResponse;
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
@RequestMapping("/starships")
@RequiredArgsConstructor
public class StarshipsController {

    private final StarshipUseCase starshipUseCase;

    @Operation(
            summary = "Get paginated list of starships",
            description = "Retrieves a paginated list of Star Wars starships from SWAPI. "
                    + "<br>Supports pagination via query parameters 'page' and 'size'.",
            tags = {"Starship"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of starships retrieved successfully",
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
                    description = "No people found",
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
        Page<Starship> starship = starshipUseCase.listStarships(pageable);

        Page<BasicResponse> response = starship.map(
                s -> new BasicResponse(
                        s.getUid(),
                        s.getName(),
                        s.getUrl()
                )
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get paginated list of starships",
            description = "Retrieves starships.",
            tags = {"Starship"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Starship retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StarshipResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination parameters",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No people found",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<StarshipResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(starshipUseCase.findById(id));
    }
}
