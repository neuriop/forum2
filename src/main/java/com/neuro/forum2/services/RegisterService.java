package com.neuro.forum2.services;

import com.neuro.forum2.models.User;
import com.neuro.forum2.repositories.UserRepositoryClass;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepositoryClass userRepository;

    public RegisterService(PasswordEncoder passwordEncoder, UserRepositoryClass userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveUser(user);
    }
}
