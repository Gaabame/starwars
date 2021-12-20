package com.example.practice.starwars.star_wars_person;

import com.example.practice.starwars.appuser.AppUser;
import com.example.practice.starwars.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StarWarsService {

    private final PersonSevice personSevice;
    private final AppUserService appUserService;

    public void savePerson(Person person) {
        boolean existsInDB = personSevice.findByName(person).isPresent();
        if (!existsInDB) {
            personSevice.addPersonToDb(person);
        }
    }

    public String comparePeoplesHeight(UserDetails appUser, String id1, String id2) {
        boolean exists = appUserService.checkIfStarWarsPersonExistsInUserList(appUser, id1, id2);
        if (!exists) {
            throw new IllegalStateException("Incorrect id.");
        }
        return personSevice.comparePeoplesHeight(id1, id2);
    }

    public List<Person> getAllSavedPeopleByUser(UserDetails appUser) {
        return appUserService.getAllSavedPeople(appUser.getUsername());
    }

    @Transactional
    public String savePersonToUserList(UserDetails appUser, String personId) {
        Person foundPerson = personSevice.getAllSavedPeople().stream().filter(person -> person.getId().equals(Long.valueOf(personId))).findFirst().orElseThrow(() -> new IllegalStateException("No person found."));
        personSevice.updatePersonOwner(appUser, foundPerson);
        return appUserService.addToStarWarsPeopleList(appUser.getUsername(), foundPerson);
    }

    public List<Person> getAllSavedPeople() {
        return personSevice.getAllSavedPeople();
    }

    public String fight(UserDetails appUser, String id1, String id2) {
        Person person1 = getAllSavedPeopleByUser(appUser).stream().filter(person -> person.getId().equals(Long.valueOf(id1))).findFirst().orElseThrow(() -> new IllegalStateException("No person with given id found"));
        Person person2 = getAllSavedPeopleButUsers(appUser).stream().filter(person -> person.getId().equals(Long.valueOf(id2))).findFirst().orElseThrow(() -> new IllegalStateException("No person with given id found"));
        return personSevice.fight(person1, person2);
    }

    private List<Person> getAllSavedPeopleButUsers(UserDetails appUser) {
        return personSevice.getAllSavedPeople().stream().filter(person -> !person.getUserEmail().equals(appUser.getUsername())).collect(Collectors.toList());
    }
}
