package com.challenge.library.security;

import com.challenge.library.model.User;
import com.challenge.library.model.enums.Profile;
import com.challenge.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(), // Store hashed passwords in the database!
                true, //enabled
                true, //accountNonExpired
                true, //credentialsNonExpired
                true, //accountNonLocked
                mapRolesToAuthorities(user.getProfile())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Profile profile) {
        return profile.getAuthorities().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
