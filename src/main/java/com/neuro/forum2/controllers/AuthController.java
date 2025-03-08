package com.neuro.forum2.controllers;

import com.neuro.forum2.config.JwtUtil;
import com.neuro.forum2.models.User;
import com.neuro.forum2.repositories.UserRepositoryClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepositoryClass userRepository;

    public AuthController(JwtUtil jwtUtil, PasswordEncoder passwordEncoder, UserRepositoryClass userRepository) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User inputUser) {
        System.out.println(" in controller");
        System.out.println(inputUser.getUsername());
        System.out.println(inputUser.getPassword());
        Optional<User> user = userRepository.findByUsername(inputUser.getUsername());
        if (user.isPresent() ) {
            String token = jwtUtil.generateToken(inputUser.getUsername());
            return ResponseEntity.ok(token);
        }else {
            User newUser = new User();
            newUser.setUsername(inputUser.getUsername());
            newUser.setPassword(passwordEncoder.encode(inputUser.getUsername()));
            newUser.setRole("USER");
            userRepository.saveUser(newUser);
            String token = jwtUtil.generateToken(inputUser.getUsername());
            return ResponseEntity.ok(token);
        }
    }

}