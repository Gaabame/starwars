package com.example.practice.starwars.controller;

import com.example.practice.starwars.appuser.AppUser;
import com.example.practice.starwars.star_wars_person.Person;
import com.example.practice.starwars.star_wars_person.StarWarsService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin //do udostepniania swobodnego api innym portom z react localhost3000
public class StarWarsApiController {

    private final StarWarsService starWarsService;
    //private Authentication authentication;

    @GetMapping
    public String getWelcomeMessage(Authentication authentication){
        UserDetails currentyLoggedInUser = (UserDetails) authentication.getPrincipal();
        return String.format("Hello %s", currentyLoggedInUser.getUsername());
    }

    @GetMapping("/{number}")
    public Person getStarWarsPerson(@PathVariable String number){
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://swapi.dev/api/people/%s/?format=json", number);
        Person person = restTemplate.getForObject(url, Person.class);
        starWarsService.savePerson(person);
        return person;
    }

    @GetMapping("/save/{personId}")
    public String saveStarWarsPersonToUserList(Authentication authentication, @PathVariable String personId){
        UserDetails appUser = (UserDetails) authentication.getPrincipal();
        return starWarsService.savePersonToUserList(appUser, personId);
    }

    @GetMapping("/{id1}/{id2}")
    public String whichIsHigher(@PathVariable String id1, @PathVariable String id2, Authentication authentication){
        UserDetails appUser = (UserDetails) authentication.getPrincipal();
        return starWarsService.comparePeoplesHeight(appUser, id1, id2);
    }

    @GetMapping ("/all")
    public List<Person> getAllSavedPeople(){
        return starWarsService.getAllSavedPeople();
    }

    @GetMapping ("/myall")
    public List<Person> getAllSavedPeopleByUser(Authentication authentication){
        UserDetails appUser = (UserDetails) authentication.getPrincipal();
        return starWarsService.getAllSavedPeopleByUser(appUser);
    }

    @GetMapping ("/fight/{id1}/{id2}")
    public String fight(@PathVariable String id1, @PathVariable String id2, Authentication authentication) {
        UserDetails appUser = (UserDetails) authentication.getPrincipal();
        return starWarsService.fight(appUser, id1, id2);
    }

    //mogę tez wyświetlić wszystkie dane
//    @GetMapping(path = "str/{number}")
//    public String getAllInfoAboutPerson(@PathVariable String number) {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = String.format("https://swapi.dev/api/people/%s/?format=json", number);
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//        return response.getBody();
//    }
}
