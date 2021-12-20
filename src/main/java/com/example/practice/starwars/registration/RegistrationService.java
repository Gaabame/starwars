package com.example.practice.starwars.registration;

import com.example.practice.starwars.appuser.AppUser;
import com.example.practice.starwars.appuser.AppUserRole;
import com.example.practice.starwars.appuser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String registerUser(RegistrationRequest registrationRequest) {
//TODO check if email is correct EMAIL VALIDATOR then register
        boolean isValidEmail = emailValidator.test(registrationRequest.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("Email not valid");
        }
        AppUser appUser = new AppUser(registrationRequest.getFirstName(), registrationRequest.getEmail(), registrationRequest.getPassword(), AppUserRole.USER);
        return appUserService.signUpNewUser(appUser);

    }
}
