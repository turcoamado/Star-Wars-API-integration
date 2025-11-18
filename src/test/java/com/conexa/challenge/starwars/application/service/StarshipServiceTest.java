package com.conexa.challenge.starwars.application.service;

import com.conexa.challenge.starwars.application.port.out.StarWarsApiPort;
import com.conexa.challenge.starwars.application.service.lookup.BasicLookUpService;
import com.conexa.challenge.starwars.domain.model.Starship;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.BasicResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.StarshipResponse;
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
public class StarshipServiceTest {

    @Mock
    private StarWarsApiPort starWarsApiPort;

    @Mock
    private BasicLookUpService lookup;

    @InjectMocks
    private StarshipService starshipService;

    @Test
    public void listStarships_shouldReturnPagedStarships() {
        Pageable pageable = PageRequest.of(1, 2);

        Starship s1 = new Starship(
                "9", "Death Star", "/starships/9",
                "843342", "342953", "DS-1", "Imperial", "Deep Space Mobile Battlestation",
                "4.0", List.of()
        );

        Starship s2 = new Starship(
                "10", "Millennium Falcon", "/starships/10",
                "6", "4", "YT-1300", "Corellian Engineering", "Light Freighter",
                "0.5", List.of()
        );

        Page<Starship> page = new PageImpl<>(List.of(s1, s2));

        when(starWarsApiPort.listStarships(1, 2)).thenReturn(page);

        Page<Starship> result = starshipService.listStarships(pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().getFirst().getName()).isEqualTo("Death Star");
    }

    @Test
    public void findById_shouldReturnStarshipResponse() {
        Starship starship = new Starship(
                "10",
                "Millennium Falcon",
                "/starships/10",
                "6",
                "4",
                "YT-1300",
                "Corellian Engineering",
                "Light Freighter",
                "0.5",
                List.of("/films/1", "/films/2")
        );

        when(starWarsApiPort.findStarshipById("10")).thenReturn(starship);
        when(lookup.filmBasic("1")).thenReturn(new BasicResponse("1", "A New Hope", "/films/1"));
        when(lookup.filmBasic("2")).thenReturn(new BasicResponse("2", "Empire", "/films/2"));

        StarshipResponse response = starshipService.findById("10");

        assertThat(response.getName()).isEqualTo("Millennium Falcon");
        assertThat(response.getFilms()).extracting("name").containsExactly("A New Hope", "Empire");
    }

    @Test
    public void findById_shouldHandleEmptyLists() {
        Starship starship = new Starship(
                "99",
                "Unknown Ship",
                "/starships/99",
                null, null, null, null, null, null,
                List.of()
        );

        when(starWarsApiPort.findStarshipById("99")).thenReturn(starship);

        StarshipResponse response = starshipService.findById("99");

        assertThat(response.getFilms()).isEmpty();
    }
}
