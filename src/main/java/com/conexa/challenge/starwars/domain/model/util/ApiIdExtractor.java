package com.conexa.challenge.starwars.domain.model.util;

public class ApiIdExtractor {
    public static String extractId(String url) {
        if (url == null) return null;
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }
}
