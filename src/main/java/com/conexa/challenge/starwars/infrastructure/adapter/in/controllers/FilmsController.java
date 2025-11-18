package com.conexa.challenge.starwars.infrastructure.adapter.in.controllers;

import com.conexa.challenge.starwars.application.port.in.FilmUseCase;
import com.conexa.challenge.starwars.domain.model.Film;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.BasicResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.FilmResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmsController {

    private final FilmUseCase filmUseCase;

    @Operation(
            summary = "Get paginated list of films",
            description = "Retrieves a paginated list of Star Wars films from SWAPI. "
                    + "<br>Supports pagination via query parameters 'page' and 'size'.",
            tags = {"Films"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of films retrieved successfully",
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
        Page<Film> film = filmUseCase.listFilms(pageable);

        Page<BasicResponse> response = film.map(
                f -> new BasicResponse(
                        f.getUid(),
                        f.getTitle(),
                        f.getUrl()
                )
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get film by ID",
            description = "Retrieves a film",
            tags = {"Films"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Film retrieved successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FilmResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No film found",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<FilmResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(filmUseCase.findById(id));
    }
}
