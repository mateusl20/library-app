package com.challenge.library.service.impl;

import com.challenge.library.model.User;
import com.challenge.library.repository.LoanRepository;
import com.challenge.library.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllActiveUsers() {

        User user1 = new User();
        user1.setActive(true);
        User user2 = new User();
        user2.setActive(true);

        List<User> activeUsers = Arrays.asList(user1, user2);

        when(userRepository.findByActiveTrueOrderByCreationDate()).thenReturn(activeUsers);

        List<User> result = userService.getAllActiveUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findByActiveTrueOrderByCreationDate();
    }

    @Test
    void inactivateUser_successful() {

        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setActive(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loanRepository.findByUserIdAndReturnDateIsNull(userId)).thenReturn(Arrays.asList());

        userService.inactivateUser(userId);

        assertFalse(user.isActive());
        verify(userRepository, times(1)).findById(userId);
        verify(loanRepository, times(1)).findByUserIdAndReturnDateIsNull(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void inactivateUser_userNotFound() {

        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.inactivateUser(userId));

        verify(userRepository, times(1)).findById(userId);
        verify(loanRepository, never()).findByUserIdAndReturnDateIsNull(anyLong());
        verify(userRepository, never()).save(any());
    }

    @Test
    void inactivateUser_hasActiveLoans() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setActive(true);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(loanRepository.findByUserIdAndReturnDateIsNull(userId)).thenReturn(Arrays.asList(new com.challenge.library.model.Loan()));

        assertThrows(IllegalStateException.class, () -> userService.inactivateUser(userId));

        verify(userRepository, times(1)).findById(userId);
        verify(loanRepository, times(1)).findByUserIdAndReturnDateIsNull(userId);
        verify(userRepository, never()).save(any());
    }
}
