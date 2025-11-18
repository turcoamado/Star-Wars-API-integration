package com.conexa.challenge.starwars.infrastructure.adapter.in.controllers;

import com.conexa.challenge.starwars.application.port.in.PeopleUseCase;
import com.conexa.challenge.starwars.domain.model.People;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.BasicResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.PeopleResponse;
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
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleController {

    private final PeopleUseCase peopleUseCase;

    @Operation(
            summary = "Get paginated list of people",
            description = "Retrieves a paginated list of Star Wars people from SWAPI. "
                    + "<br>Supports pagination via query parameters 'page' and 'size'.",
            tags = {"People"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of people retrieved successfully",
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
        Page<People> people = peopleUseCase.listPeople(pageable);

        Page<BasicResponse> response = people.map(
                p -> new BasicResponse(
                        p.getUid(),
                        p.getName(),
                        p.getUrl()
                )
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get character by ID",
            description = "Retrieves a character",
            tags = {"People"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Character retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PeopleResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No character found",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PeopleResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(peopleUseCase.findById(id));
    }
}
