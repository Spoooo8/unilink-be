package com.unilink.user_service.controller;

import com.unilink.common.dto.ApiResponse;
import com.unilink.user_service.dto.user.*;
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

    @GetMapping("/all")
    public List<UserResponse> getAllUsers(@RequestHeader("unilink-correlation-id") String correlationId,
            @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy,
                                          @RequestParam(defaultValue = "true") Boolean activated) {
        logger.info("in get all users");
        return usersService.getAllUsers();
    }

    @GetMapping("/users/{userId}")
    public UserResponse getUserById(@PathVariable("userId") Long userId) {
        logger.info("in get user by id");
        return usersService.getUserById(userId);
    }

    @PostMapping("/add")
    public ApiResponse addUser(@RequestHeader("unilink-correlation-id") String correlationId, @RequestBody UsersDto usersDto) {
        logger.info("in add user");
        return usersService.addUser(usersDto);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestHeader("unilink-correlation-id") String correlationId, @RequestBody RegisterRequest request) {
        usersService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestHeader("unilink-correlation-id") String correlationId, @RequestBody LoginRequest request) {
        ApiResponse response = usersService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/skill-score")
    public ResponseEntity<SkillScoreResponseDTO> getSkillScore(@RequestHeader("unilink-correlation-id") String correlationId) {
            return ResponseEntity.ok( usersService.getSkillScore());
    }

    @PutMapping("/complete")
    public ResponseEntity<String> completeUserProfile(@RequestHeader("unilink-correlation-id") String correlationId,
            @RequestBody CompleteProfileRequest request
    ) {
        usersService.completeProfile(request);
        return ResponseEntity.ok("Profile updated successfully.");
    }

    @GetMapping("/profile")
    public UserProfileResponse getUserProfile(@RequestHeader("unilink-correlation-id") String correlationId) {
        return usersService.getUserProfile();
    }
}
