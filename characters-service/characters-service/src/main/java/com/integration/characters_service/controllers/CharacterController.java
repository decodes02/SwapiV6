package com.integration.characters_service.controllers;

import com.integration.characters_service.model.Characters;
import com.integration.characters_service.services.CharacterService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/people")
public class CharacterController {

    private final CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping
    public List<Characters> getAllCharacters() {
        return characterService.fetchCharacters(); // This returns List<Characters>
    }


    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Characters getCharacterById(@PathVariable("id") String id) {

        return characterService.fetchCharacterById(id);
    }
}
