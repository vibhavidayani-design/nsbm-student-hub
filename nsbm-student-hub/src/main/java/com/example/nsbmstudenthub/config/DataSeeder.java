package com.example.nsbmstudenthub.config;

import com.example.nsbmstudenthub.entity.AppUser;
import com.example.nsbmstudenthub.entity.Role;
import com.example.nsbmstudenthub.repository.RoleRepository;
import com.example.nsbmstudenthub.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner init(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            Role admin = roleRepo.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepo.save(Role.builder().name("ROLE_ADMIN").build()));

            Role user = roleRepo.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepo.save(Role.builder().name("ROLE_USER").build()));

            if (userRepo.findByUsername("admin").isEmpty()) {
                userRepo.save(AppUser.builder()
                        .username("admin")
                        .password(encoder.encode("admin123"))
                        .roles(Set.of(admin))
                        .build());
            }
        };
    }
}
