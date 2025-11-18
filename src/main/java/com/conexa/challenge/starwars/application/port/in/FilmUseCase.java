package com.conexa.challenge.starwars.application.port.in;

import com.conexa.challenge.starwars.domain.model.Film;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.FilmResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilmUseCase {
    Page<Film> listFilms(Pageable pageable);
    FilmResponse findById(String id);
}
