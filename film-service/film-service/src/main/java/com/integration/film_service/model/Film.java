package com.integration.film_service.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.List;

@Data
@Document(collection = "films") // Store in MongoDB Collection "films"
public class Film {

    @Id
    private String id; // MongoDB's Auto-Generated ID
    private String title;
    private String release_date;
    private String episode_id;
    private String opening_crawl;
    private String director;
    private String producer;
    private List<String> characters; // Only keeping characters

    public Film() {
    }
}
