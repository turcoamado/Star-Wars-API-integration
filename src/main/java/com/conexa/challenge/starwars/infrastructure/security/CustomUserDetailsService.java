package com.conexa.challenge.starwars.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

//    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.equals("admin")) {
            return User.withUsername("admin")
//                    .password(passwordEncoder.encode("admin123"))
                    .password("{noop}admin123") // NO ENCODER
                    .roles("ADMIN")
                    .build();
        }
        if (username.equals("user")) {
            return User.withUsername("user")
//                    .password(passwordEncoder.encode("user123"))
                    .password("{noop}user123") // NO ENCODER
                    .roles("USER")
                    .build();
        }

        throw new UsernameNotFoundException("User not found");
    }
}
