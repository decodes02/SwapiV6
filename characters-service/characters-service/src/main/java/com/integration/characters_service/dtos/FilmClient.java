package com.integration.characters_service.dtos;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:9094",value = "film-service")
public interface FilmClient {
    @GetMapping("/people/{id}")
    Film getFilmById(@PathVariable("id") String id);
}
