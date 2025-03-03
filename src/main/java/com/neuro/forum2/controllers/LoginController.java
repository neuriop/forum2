package com.neuro.forum2.controllers;

import com.neuro.forum2.services.LoginService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login2")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("")
    public String login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return "Login successful: " + authentication.getName();
        } catch (AuthenticationException e) {
            return "Login failed: " + e.getMessage();
        }
    }

    public static class LoginRequest{
        private String username;
        private String password;

        public LoginRequest(String password, String username) {
            this.password = password;
            this.username = username;
        }

        public LoginRequest() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
