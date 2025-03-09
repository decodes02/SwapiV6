package com.integration.film_service.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "characters-service", url = "http://localhost:9094")
public interface CharacterClient {
    @GetMapping("/characters/{id}")
    Character getCharacterById(@PathVariable("id") String id);
}