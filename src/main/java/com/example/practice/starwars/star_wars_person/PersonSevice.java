package com.example.practice.starwars.star_wars_person;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonSevice {

    private final PersonRepository personRepository;

    public void addPersonToDb(Person person) {
        personRepository.save(person);
    }

    public List<Person> getAllSavedPeople() {
        return personRepository.findAll();
    }

    private Person findById(String id) {
        return personRepository.findById(Long.valueOf(id)).orElseThrow(() -> new IllegalStateException(String.format("No person with id %s found", id)));
    }

     public Optional<Person> findByName(Person person){
        return personRepository.findByName(person.getName());
    }

    private int compareHeight(String id1, String id2) {
        return Integer.parseInt(id1) - Integer.parseInt(id2);
    }

    public String comparePeoplesHeight(String id1, String id2) {
        Person person1 = findById(id1);
        Person person2 = findById(id2);
        String result = String.format("%s and %s are of equal height", person1.getName(), person2.getName());
        int compareResult = compareHeight(id1, id2);
        if (compareResult != 0) {
            if (compareResult > 0) {
                result = String.format("%s is higher than %s", person1.getName(), person2.getName());
            } else {
                result = result = String.format("%s is lower than %s", person1.getName(), person2.getName());
            }
        }
        return result;
    }

    public String fight(Person person1, Person person2) {
        int power1 = person1.getPower();
        int power2 = person2.getPower();
        person1.setPower(power1+20);
        person2.setPower(power2-20);
        personRepository.save(person1);
        personRepository.save(person2);
        return String.format("%s has won the fight with %s. Current power: %d", person1.getName(), person2.getName(), person1.getPower());
    }

    public void updatePersonOwner(UserDetails appUser, Person person) {
        person.setUserEmail(appUser.getUsername());
        personRepository.save(person);
    }
}
