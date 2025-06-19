package com.unilink.auth_server.controller;

import com.unilink.auth_server.entity.AuthProfile;
import com.unilink.auth_server.repository.AuthProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthProfileController {
    private final AuthProfileRepository customerRepository;
    private final PasswordEncoder passwordEncoder;


    // ========== Customer Registration ==========

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody AuthProfile authProfile) {
        try {
            authProfile.setPwd(passwordEncoder.encode(authProfile.getPwd()));

            if (authProfile.getAuthorities() != null) {
                authProfile.getAuthorities().forEach(auth -> auth.setAuthProfile(authProfile));
            }

            AuthProfile savedAuthProfile = customerRepository.save(authProfile);

            if (savedAuthProfile.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User registration failed");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occurred: " + ex.getMessage());
        }
    }
}
