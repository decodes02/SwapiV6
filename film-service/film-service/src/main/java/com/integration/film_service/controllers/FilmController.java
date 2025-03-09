package com.integration.film_service.controllers;

import com.integration.film_service.model.Film;
import com.integration.film_service.services.FilmService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    // Get all films
    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.fetchFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable("id") String id) {
        return filmService.fetchFilmById(id);
    }
}