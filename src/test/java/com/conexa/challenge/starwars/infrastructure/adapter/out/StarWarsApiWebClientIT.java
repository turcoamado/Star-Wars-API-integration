package com.conexa.challenge.starwars.infrastructure.adapter.out;

import com.conexa.challenge.starwars.domain.model.Film;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class StarWarsApiWebClientIT {
    private static MockWebServer mockWebServer;
    private static StarWarsApiWebClient starWarsApiWebClient;

    @BeforeAll
    static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        starWarsApiWebClient = new StarWarsApiWebClient(webClient);
    }

    @AfterAll
    static void teardown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void listFilms_shouldReturnFilmsFromApi() {
        String jsonResponse = """
        {
           "message": "ok",
           "result": [
             {
               "uid": "1",
               "properties": {
                 "title": "A New Hope",
                 "url": "https://swapi.tech/api/films/1"
               }
             },
             {
               "uid": "2",
               "properties": {
                 "title": "Empire",
                 "url": "https://swapi.tech/api/films/2"
               }
             }
           ]
         }
        """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        var page = starWarsApiWebClient.listFilms(0, 2);

        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getContent().getFirst().getTitle()).isEqualTo("A New Hope");
    }

    @Test
    void findFilmById_shouldReturnFilm() {
        String jsonResponse = """
        {
          "result": {
            "uid": "1",
            "properties": {
              "title": "A New Hope",
              "url": "/films/1",
              "episode_id": 4,
              "director": "George Lucas",
              "producer": "Lucasfilm",
              "release_date": "1977-05-25",
              "opening_crawl": "Opening crawl",
              "people": [],
              "planets": [],
              "starships": [],
              "vehicles": [],
              "species": []
            }
          }
        }
        """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        Film film = starWarsApiWebClient.findFilmById("1");

        assertThat(film.getTitle()).isEqualTo("A New Hope");
        assertThat(film.getEpisodeId()).isEqualTo(4);
        assertThat(film.getDirector()).isEqualTo("George Lucas");
    }

    @Test
    void listStarships_shouldReturnStarshipsFromApi() {
        String jsonResponse = """
        {
           "total_records": 2,
           "total_pages": 1,
           "previous": null,
           "next": null,
           "results": [
             {
               "uid": "1",
               "name": "Death Star",
               "url": "https://swapi.tech/api/starships/1"
             },
             {
               "uid": "2",
               "name": "Millennium Falcon",
               "url": "https://swapi.tech/api/starships/2"
             }
           ]
         }
        """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        var page = starWarsApiWebClient.listStarships(0, 2);

        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getContent().get(0).getName()).isEqualTo("Death Star");
        assertThat(page.getContent().get(1).getName()).isEqualTo("Millennium Falcon");
    }
}
