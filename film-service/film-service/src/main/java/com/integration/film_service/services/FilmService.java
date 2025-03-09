package com.integration.film_service.services;

import com.integration.film_service.model.Characters;
import com.integration.film_service.model.Film;
import com.integration.film_service.Repository.FilmRepository;
import org.springframework.core.ParameterizedTypeReference;
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
    private final String FILM_JSON_URL = "https://swapi.dev/api/films"; // API Endpoint

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public List<Film> fetchFilms() {
        List<Film> films = filmRepository.findAll();
        if (!films.isEmpty()) {
            return films; // Return from DB if available
        }

        // Fetch from API if not found in DB
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Film>> response = restTemplate.exchange(
                FILM_JSON_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Film>>() {}
        );

        films = response.getBody();
        filmRepository.saveAll(films); // Save to MongoDB
        return films;
    }

    public Film fetchFilmById(String id) {
        Optional<Film> filmOptional = filmRepository.findById(id);
        if (filmOptional.isPresent()) {
            return filmOptional.get(); // Return from MongoDB if found
        }

        // Fetch from API if not found in DB
        String url = FILM_JSON_URL + "/" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Film> response = restTemplate.exchange(url, HttpMethod.GET, null, Film.class);

        if (response != null && response.getBody() != null) {
            Film film = response.getBody();

            // Fetch character names instead of URLs
            List<String> charNames = film.getCharacters().stream()
                    .map(this::fetchCharacterName)  // Convert URLs to names
                    .filter(name -> name != null && !name.equals("Unknown Character")) // Remove bad data
                    .collect(Collectors.toList());

            film.setCharacters(charNames);
            filmRepository.save(film); // Save updated film in MongoDB
            return film;
        }
        return null;
    }


    private String fetchCharacterName(String characterUrl) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Characters character = restTemplate.getForObject(characterUrl, Characters.class);

            if (character != null && character.getName() != null) {
                return character.getName();  // Return only the name
            } else {
                System.err.println("Character data not found for URL: " + characterUrl);
                return "Unknown Character";
            }
        } catch (Exception e) {
            System.err.println("Error fetching character: " + e.getMessage());
            return "Unknown Character";
        }
    }

}
