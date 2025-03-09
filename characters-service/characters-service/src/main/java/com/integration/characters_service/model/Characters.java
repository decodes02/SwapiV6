//package com.integration.characters_service.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.Entity;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@JsonIgnoreProperties(ignoreUnknown = true)
//@Getter
//@Setter
//@Entity
//public class Characters {
//    private String name;
//    private String height;
//    private String mass;
//    private String hair_color;
//    private String eye_color;
//    private String birth_year;
//    private  String gender;
//    private List<String> films;
//
//    public Characters() {
//    }
//
//    public Characters(String name, String height, String mass, String hair_color, String eye_color, String birth_year, String gender, List<String> films) {
//        this.name = name;
//        this.height = height;
//        this.mass = mass;
//        this.hair_color = hair_color;
//        this.eye_color = eye_color;
//        this.birth_year = birth_year;
//        this.gender = gender;
//        this.films = films;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getHeight() {
//        return height;
//    }
//
//    public void setHeight(String height) {
//        this.height = height;
//    }
//
//    public String getMass() {
//        return mass;
//    }
//
//    public void setMass(String mass) {
//        this.mass = mass;
//    }
//
//    public String getHair_color() {
//        return hair_color;
//    }
//
//    public void setHair_color(String hair_color) {
//        this.hair_color = hair_color;
//    }
//
//    public String getEye_color() {
//        return eye_color;
//    }
//
//    public void setEye_color(String eye_color) {
//        this.eye_color = eye_color;
//    }
//
//    public String getBirth_year() {
//        return birth_year;
//    }
//
//    public void setBirth_year(String birth_year) {
//        this.birth_year = birth_year;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public List<String> getFilms() {
//        return films;
//    }
//
//    public void setFilms(List<String> films) {
//        this.films = films;
//    }
//}

package com.integration.characters_service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.List;

@Data
@Document(collection = "characters") // Store in MongoDB Collection "characters"
@JsonIgnoreProperties(ignoreUnknown = true)
public class Characters {

    @Id
    private String id;  // MongoDB Auto-generated ID
    private String name;
    private String height;
    private String mass;
    private String hair_color;
    private String eye_color;
    private String birth_year;
    private String gender;
    private List<String> films;  // Store only film names

    public Characters() {
    }
}

