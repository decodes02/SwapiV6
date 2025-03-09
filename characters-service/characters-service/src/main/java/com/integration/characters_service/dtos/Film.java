package com.integration.characters_service.dtos;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
public class Film {
    private String title;
    private String release_date;

    private String episode_id;

    @Column(length = 50000)
    private String opening_crawl;

    private String director;

    private String producer;
    private List<String> characters;
}
