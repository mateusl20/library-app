package com.challenge.library.service.impl;

import com.challenge.library.model.User;
import com.challenge.library.repository.LoanRepository;
import com.challenge.library.repository.UserRepository;
import com.challenge.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    @Override
    public List<User> getAllActiveUsers() {
        return userRepository.findByActiveTrueOrderByCreationDate();
    }

    @Override
    public void inactivateUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getEmail().equals(getLoggedInUserEmail())) {
            throw new IllegalStateException("You cannot inactivate yourself!");
        }

        if (!loanRepository.findByUserIdAndReturnDateIsNull(userId).isEmpty()) {
            throw new IllegalStateException("User has active loans. Cannot inactivate.");
        }
        user.setActive(false);
        userRepository.save(user);
    }

    private String getLoggedInUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
