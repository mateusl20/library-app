package com.challenge.library.repository;

import com.challenge.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByActiveTrueOrderByCreationDate();
    Optional<User> findByEmail(String email);
}
