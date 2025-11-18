package com.conexa.challenge.starwars.application.port.in;

import com.conexa.challenge.starwars.domain.model.People;
import com.conexa.challenge.starwars.infrastructure.adapter.in.dto.PeopleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PeopleUseCase {
    Page<People> listPeople(Pageable pageable);
    PeopleResponse findById(String id);
    People findByName(String name, Pageable pageable);
}
