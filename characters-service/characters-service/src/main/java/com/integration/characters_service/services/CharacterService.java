package com.integration.characters_service.services;

import com.integration.characters_service.dtos.Film;
import com.integration.characters_service.model.Characters;
import com.integration.characters_service.Repository.CharacterRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CharacterService {

    private final String CHARACTER_JSON_URL = "https://swapi.dev/api/people";
    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    // Fetch all characters from DB
    public List<Characters> fetchCharacters() {
        return characterRepository.findAll();
    }

    // Fetch character by ID with MongoDB first strategy
    public Characters fetchCharacterById(String id) {
        // First check in MongoDB
        Optional<Characters> characterOptional = characterRepository.findById(id);
        if (characterOptional.isPresent()) {
            return characterOptional.get(); // Return if found in DB
        }

        // Not found in DB, fetch from SWAPI
        String url = CHARACTER_JSON_URL + "/" + id + "/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Characters> response = restTemplate.exchange(url, HttpMethod.GET, null, Characters.class);

        if (response != null && response.getBody() != null) {
            Characters character = response.getBody();

            // Set SWAPI ID as MongoDB ID
            character.setId(id);

            // Convert Film URLs to Film Titles
            List<String> filmNames = character.getFilms().stream()
                    .map(this::fetchFilmTitle)
                    .filter(name -> name != null && !name.equals("Unknown Film"))
                    .collect(Collectors.toList());

            character.setFilms(filmNames);

            // Save into MongoDB for future use
            characterRepository.save(character);

            return character;
        }

        return null; // If nothing found even from SWAPI
    }

    // Fetch Film Title from SWAPI
    private String fetchFilmTitle(String filmUrl) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Film film = restTemplate.getForObject(filmUrl, Film.class);

            if (film != null && film.getTitle() != null) {
                return film.getTitle(); // Return film title
            } else {
                System.err.println(" Film data not found for URL: " + filmUrl);
                return "Unknown Film";
            }
        } catch (Exception e) {
            System.err.println(" Error fetching film from URL: " + filmUrl + " | Error: " + e.getMessage());
            return "Unknown Film"; // Handle errors gracefully
        }
    }
}
