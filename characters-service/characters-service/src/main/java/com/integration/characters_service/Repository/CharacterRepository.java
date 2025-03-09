package com.integration.characters_service.Repository;

import com.integration.characters_service.model.Characters;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends MongoRepository<Characters, String> {
}
