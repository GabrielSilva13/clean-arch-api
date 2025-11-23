package com.gabriel.cleanarch.infrastructure.seed;

import com.gabriel.cleanarch.domain.user.Role;
import com.gabriel.cleanarch.domain.user.User;
import com.gabriel.cleanarch.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Seeds the database with an admin user if no user with the email "admin@example.com" exists.
     * The admin user has the email "admin@example.com", the password "admin123" and the role ADMIN.
     * @param args the command line arguments
     * @throws Exception if an error occurs while seeding the database
     */
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            User admin = User.builder()
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
        }
    }
}
