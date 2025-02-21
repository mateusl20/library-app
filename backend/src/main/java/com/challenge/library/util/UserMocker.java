package com.challenge.library.util;

import com.challenge.library.model.User;
import com.challenge.library.model.enums.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserMocker {


    public static Collection<User> mockUsers(PasswordEncoder passwordEncoder) {
        List<User> mockedUsers = new ArrayList<>(2);

        User admin = new User();
        admin.setName("Admin");
        admin.setEmail("admin@test.com");
        admin.setProfile(Profile.ADMIN);
        admin.setCreationDate(Instant.now());
        admin.setActive(true);
        admin.setPassword(passwordEncoder.encode("password"));
        mockedUsers.add(admin);

        User user = new User();
        user.setName("User");
        user.setEmail("user@test.com");
        user.setProfile(Profile.USER);
        user.setCreationDate(Instant.now());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode("password"));
        mockedUsers.add(user);

        return mockedUsers;
    }
}
