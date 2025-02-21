package com.challenge.library.controller;

import com.challenge.library.model.User;
import com.challenge.library.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Tag(name = "Users", description = "Endpoints for managing users")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get all active users", description = "Return all active users sorted by creation date")
    @GetMapping
    @RolesAllowed("ADMIN")
    public ResponseEntity<List<User>> getAllActiveUsers() {
        return ResponseEntity.ok(userService.getAllActiveUsers());
    }

    @Operation(summary = "Inactivate a user", description = "Inactivate and active user")
    @PostMapping("/{userId}/inactivate")
    @RolesAllowed("ADMIN")
    public ResponseEntity<String> inactivateUser(@PathVariable Long userId) {
        try {
            userService.inactivateUser(userId);
            return ResponseEntity.ok("Operation performed successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while performing the operation");
        }
    }
}
