package com.example.practice.starwars.controller;

import com.example.practice.starwars.registration.RegistrationRequest;
import com.example.practice.starwars.registration.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registration")
@AllArgsConstructor
public class AppUserRegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest registrationRequest){
        return registrationService.registerUser(registrationRequest);
    }

}
