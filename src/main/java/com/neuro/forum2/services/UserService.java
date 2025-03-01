package com.neuro.forum2.services;

import com.neuro.forum2.models.User;
import com.neuro.forum2.repositories.UserRepositoryClass;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired private UserRepositoryClass userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

//    @PostConstruct
//    public void getUser(){
//        Optional<User>  user = userRepository.findByUsername("mark");
//        user.ifPresent(value -> System.out.println(value.getUsername()));
//    }
//
//    @PostConstruct
//    public void makeUser() throws Exception {
//        userRepository.saveUser(registerUser("john", "1234"));
//    }

    public User registerUser(String username, String rawPassword) throws Exception {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new Exception("Username already exists");
        }
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(username, encodedPassword);
        userRepository.saveUser(user);
        return user;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if(passwordEncoder.matches(rawPassword, user.getPassword())){
            return user;
        } else {
            throw new BadCredentialsException("Invalid credentials");
        }
    }
}
