package com.conexa.challenge.starwars.application.port.in;

import com.conexa.challenge.starwars.domain.model.Starship;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.StarshipResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StarshipUseCase {
    Page<Starship> listStarships(Pageable pageable);
    StarshipResponse findById(String id);
}
