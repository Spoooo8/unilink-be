package com.unilink.user_service.controller;

import com.unilink.common.dto.ApiResponse;
import com.unilink.user_service.dto.user.*;
import com.unilink.user_service.service.user.UserDetailsService;
import com.unilink.user_service.service.user.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UsersController {

    Logger logger = LoggerFactory.getLogger(UsersController.class);


    @Autowired
    private UsersService usersService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestHeader("unilink-correlation-id") String correlationId, @RequestBody RegisterRequest request) {
        usersService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/info")
    public UserInfoDTO getUserInfo() {
        logger.info("in get user by id");
        return usersService.getUseInfo();
    }

    @GetMapping("/users/{userId}")
    public UserResponse getUserById(@PathVariable("userId") Long userId) {
        logger.info("in get user by id");
        return usersService.getUserById(userId);
    }

    @GetMapping("/skill-score")
    public ResponseEntity<SkillScoreResponseDTO> getSkillScore(@RequestHeader("unilink-correlation-id") String correlationId) {
            return ResponseEntity.ok( userDetailsService.getSkillScore());
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editProfile(@RequestHeader("unilink-correlation-id") String correlationId,
            @RequestBody EditProfileRequest request
    ) {
        userDetailsService.editProfile(request);
        return ResponseEntity.ok("Profile updated successfully.");
    }

    @GetMapping("/profile")
    public UserProfileResponse getUserProfile(@RequestHeader("unilink-correlation-id") String correlationId) {
        return userDetailsService.getUserProfile();
    }

    @GetMapping("/profile/{userId}")
    public UserProfileResponse getUserProfileById(@PathVariable("userId") Long userId) {
        logger.info("in get user by id");
        return userDetailsService.getUserProfileById(userId);
    }


}
