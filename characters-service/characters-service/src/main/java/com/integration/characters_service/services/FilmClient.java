package com.integration.characters_service.services;

import com.integration.characters_service.dtos.Film;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient( url = "http://localhost:9095",value = "film-service")
public interface FilmClient {
    @GetMapping("/people/{id}")
    Film getFilmById(@PathVariable("id") String id);
}
