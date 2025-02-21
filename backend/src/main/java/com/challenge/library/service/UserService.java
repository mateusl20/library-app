package com.challenge.library.service;

import com.challenge.library.model.User;
import java.util.List;

public interface UserService {
    List<User> getAllActiveUsers();
    void inactivateUser(Long userId);
}
