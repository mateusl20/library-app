package com.challenge.library;

import com.challenge.library.repository.BookRepository;
import com.challenge.library.repository.UserRepository;
import com.challenge.library.util.BookMocker;
import com.challenge.library.util.UserMocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;

	@Bean
	private static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.saveAll(UserMocker.mockUsers(passwordEncoder()));
		bookRepository.saveAll(BookMocker.mockBooks());
	}
}
