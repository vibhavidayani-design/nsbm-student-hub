package com.example.nsbmstudenthub.controller;

import com.example.nsbmstudenthub.dto.RegisterRequest;
import com.example.nsbmstudenthub.entity.AppUser;
import com.example.nsbmstudenthub.entity.Role;
import com.example.nsbmstudenthub.repository.RoleRepository;
import com.example.nsbmstudenthub.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
    }

    @PostMapping("/register")
    public AppUser register(@RequestBody RegisterRequest req) {

        Set<Role> roleEntities = new HashSet<>();
        for (String roleName : req.roles()) {
            Role role = roleRepo.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roleEntities.add(role);
        }

        AppUser user = AppUser.builder()
                .username(req.username())
                .password(encoder.encode(req.password())) // ✅ encrypted
                .roles(roleEntities)
                .build();

        return userRepo.save(user);
    }
}

