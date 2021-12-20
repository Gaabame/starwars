package com.example.practice.starwars.appuser;

import com.example.practice.starwars.star_wars_person.Person;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class AppUser implements UserDetails {

    @Id
    @SequenceGenerator(name = "user_sequence", allocationSize = 1, sequenceName = "user_sequence")
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    @Column (unique = true)
    private String firstName;
    private String email;
    private String password;
    @Enumerated (EnumType.STRING)
    private AppUserRole appUserRole;
    private Boolean isLocked = false;
    private Boolean isEnabled = true;
    @OneToMany
   // @JoinColumn(name = "person_id")
    private List<Person> listOfStarWarsPeople;

    public AppUser(String firstName, String email, String password, AppUserRole appUserRole) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.appUserRole = appUserRole;
        listOfStarWarsPeople = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
