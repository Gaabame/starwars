package com.example.practice.starwars.appuser;

import com.example.practice.starwars.registration.RegistrationRequest;
import com.example.practice.starwars.star_wars_person.Person;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private static final String USER_NOT_FOUND_MESSAGE = "No user with email: %s found";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException(String.format(USER_NOT_FOUND_MESSAGE, email)));
    }

    public String signUpNewUser(AppUser appUser) {
        if (appUserRepository.findByEmail(appUser.getEmail()).isPresent()) {
            throw new IllegalStateException(String.format("User with email %s already exists.", appUser.getEmail()));
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);
        return String.format("User with email: %s registered", appUser.getEmail());
    }

    public String addToStarWarsPeopleList(String email, Person person){
        AppUser appUser = getAppUser(email);
        appUser.getListOfStarWarsPeople().add(person);
       appUserRepository.save(appUser);
       return String.format("Person %s has been saved to user %s list.", person.getName(), appUser.getFirstName());
    }

    public List<Person> getAllSavedPeople (String email){
        AppUser appUser = getAppUser(email);
        return appUser.getListOfStarWarsPeople();
    }

    private AppUser getAppUser(String email) {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("No email found."));
    }

    public boolean checkIfStarWarsPersonExistsInUserList(UserDetails appUser, String id1, String id2) {
        List<Person> allSavedPeople = getAllSavedPeople(appUser.getUsername());
        List<Person> person1 = allSavedPeople.stream().filter(person -> person.getId().equals(Long.valueOf(id1))).collect(Collectors.toList());
        List<Person> person2 = allSavedPeople.stream().filter(person -> person.getId().equals(Long.valueOf(id2))).collect(Collectors.toList());
        return !person1.isEmpty() && !person2.isEmpty();
    }
}
