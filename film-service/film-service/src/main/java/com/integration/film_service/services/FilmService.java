package com.integration.film_service.services;

import com.integration.film_service.model.Characters;
import com.integration.film_service.model.Film;
import com.integration.film_service.Repository.FilmRepository;
import com.integration.film_service.model.FilmResults;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final String FILM_JSON_URL = "https://swapi.dev/api/films";

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    // Fetch all films from MongoDB, if empty fetch from SWAPI and store
    public List<Film> fetchFilms() {
        List<Film> films = filmRepository.findAll();
        if (!films.isEmpty()) {
            return films; // Return from MongoDB if found
        }

        // Fetch from SWAPI if not in MongoDB
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<FilmResults> response = restTemplate.exchange(
                FILM_JSON_URL,
                HttpMethod.GET,
                null,
                FilmResults.class
        );

        if (response != null && response.getBody() != null && response.getBody().getResults() != null) {
            List<Film> filmList = response.getBody().getResults();

            // Assign IDs (since SWAPI does not give ID directly)
            for (int i = 0; i < filmList.size(); i++) {
                filmList.get(i).setId(String.valueOf(i + 1)); // Assuming sequential IDs
            }

            filmRepository.saveAll(filmList); // Store in MongoDB
            return filmList;
        }

        return List.of(); // Return empty if nothing found
    }

    // Fetch film by ID, check MongoDB first, if not found fetch, process and save
    public Film fetchFilmById(String id) {
        Optional<Film> filmOptional = filmRepository.findById(id);
        if (filmOptional.isPresent()) {
            return filmOptional.get(); // Return from MongoDB
        }

        // Fetch from SWAPI
        String url = FILM_JSON_URL + "/" + id + "/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Film> response = restTemplate.exchange(url, HttpMethod.GET, null, Film.class);

        if (response != null && response.getBody() != null) {
            Film film = response.getBody();

            // Set SWAPI ID
            film.setId(id);

            // Convert Character URLs to Character Names
            List<String> charNames = film.getCharacters().stream()
                    .map(this::fetchCharacterName)
                    .filter(name -> name != null && !name.equals("Unknown Character"))
                    .collect(Collectors.toList());

            film.setCharacters(charNames); // Set character names

            filmRepository.save(film); // Save to MongoDB
            return film;
        }

        return null; // If not found anywhere
    }

    // Helper function to get Character Name from SWAPI
    private String fetchCharacterName(String characterUrl) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Characters character = restTemplate.getForObject(characterUrl, Characters.class);
            if (character != null && character.getName() != null) {
                return character.getName(); // Return name
            } else {
                System.err.println("Character not found at URL: " + characterUrl);
                return "Unknown Character";
            }
        } catch (Exception e) {
            System.err.println("Error fetching character from URL: " + characterUrl + " Error: " + e.getMessage());
            return "Unknown Character";
        }
    }
}
