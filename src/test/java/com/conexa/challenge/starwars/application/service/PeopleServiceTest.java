package com.conexa.challenge.starwars.application.service;

import com.conexa.challenge.starwars.application.port.out.StarWarsApiPort;
import com.conexa.challenge.starwars.application.service.lookup.BasicLookUpService;
import com.conexa.challenge.starwars.domain.model.People;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.BasicResponse;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.PeopleResponse;
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
public class PeopleServiceTest {

    @Mock
    private StarWarsApiPort starWarsApiPort;

    @Mock
    private BasicLookUpService lookup;

    @InjectMocks
    private PeopleService peopleService;

    @Test
    public void listFilms_shouldReturnPagedFilms() {
        Pageable pageable = PageRequest.of(7, 2);
        People people1 = new People("13", "Chewbacca", "https://www.swapi.tech/api/people/13", null, null, List.of(), List.of(), List.of());
        People people2 = new People("14", "Han Solo", "https://www.swapi.tech/api/people/14", null, null, List.of(), List.of(), List.of());
        Page<People> page = new PageImpl<>(List.of(people1, people2));

        when(starWarsApiPort.listPeople(7, 2)).thenReturn(page);

        Page<People> result = peopleService.listPeople(pageable);

        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent().getFirst().getName()).isEqualTo("Chewbacca");
    }

    @Test
    public void findById_shouldReturnPeopleResponse() {
        People people = new People(
                "5",
                "Leia Organa",
                "/people/5",
                "female",
                "19BBY",
                List.of("/films/1", "/films/2", "/films/3", "/films/6"),
                List.of("/vehicles/30"),
                List.of("/starships/1")
        );

        when(starWarsApiPort.findPeopleById("5")).thenReturn(people);
        when(lookup.filmBasic("1")).thenReturn(new BasicResponse("1", "A New Hope", "/films/1"));
        when(lookup.filmBasic("2")).thenReturn(new BasicResponse("2", "The Empire Strikes Back", "/films/2"));
        when(lookup.filmBasic("3")).thenReturn(new BasicResponse("3", "Return of the Jedi", "/films/3"));
        when(lookup.filmBasic("6")).thenReturn(new BasicResponse("6", "Revenge of the Sith", "/films/6"));
        when(lookup.vehicleBasic("30")).thenReturn(new BasicResponse("30", "Imperial Speeder Bike", "/vehicle/30"));

        PeopleResponse response = peopleService.findById("5");

        assertThat(response.getName()).isEqualTo("Leia Organa");
        assertThat(response.getFilms()).extracting("name").containsExactly("A New Hope", "The Empire Strikes Back", "Return of the Jedi", "Revenge of the Sith");
        assertThat(response.getVehicles()).extracting("name").containsExactly("Imperial Speeder Bike");
    }

    @Test
    public void findById_shouldHandleEmptyLists() {
        People people = new People(
                "25",
                "Lando Calrissian",
                "/people/25",
                "male",
                "31BBY",
                List.of("/films/2", "/films/3"),
                List.of(),
                List.of("/starships/10")
        );

        when(starWarsApiPort.findPeopleById("25")).thenReturn(people);

        PeopleResponse response = peopleService.findById("25");

        assertThat(response.getVehicles()).isEmpty();
    }
}
