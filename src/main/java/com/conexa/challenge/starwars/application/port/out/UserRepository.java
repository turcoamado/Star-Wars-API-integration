package com.conexa.challenge.starwars.application.port.out;

import com.conexa.challenge.starwars.domain.model.User;

public interface UserRepository {
    User findByUsername(String username);
}
