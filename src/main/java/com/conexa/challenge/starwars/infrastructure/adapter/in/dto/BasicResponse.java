package com.conexa.challenge.starwars.infrastructure.adapter.in.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicResponse {
    private String uid;
    private String name;
    private String url;
}
