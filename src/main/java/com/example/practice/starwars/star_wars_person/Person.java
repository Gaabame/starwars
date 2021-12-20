package com.example.practice.starwars.star_wars_person;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    @JsonProperty ("name")
    private String name;
    @JsonProperty ("height")
    private String height;
    @JsonProperty ("eye_color")
    private String eyeColor;
    @JsonProperty ("birth_year")
    private String birthYear;
    private String userEmail = "free";
    private int power = 100;

}
