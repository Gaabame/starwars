package com.example.practice.starwars.registration;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegistrationRequest {

    private String firstName;
    private String email;
    private String password;

}
