package com.unilink.quiz_service.controller.user;

import com.unilink.common.thread.ThreadLocal;
import com.unilink.quiz_service.dto.ApiResponse;
import com.unilink.quiz_service.dto.users.*;
import com.unilink.quiz_service.service.users.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
public class UsersController {

    Logger logger = LoggerFactory.getLogger(UsersController.class);


    @Autowired
    private UsersService usersService;

    @GetMapping("/all")
    public List<UserResponse> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy,
                                          @RequestParam(defaultValue = "true") Boolean activated) {
        logger.info("in get all users");
        return usersService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable("userId") Long userId) {
        logger.info("in get user by id");
        return usersService.getUserById(userId);
    }

    @PostMapping("/add")
    public ApiResponse addUser(@RequestBody UsersDto usersDto) {
        logger.info("in add user");
        return usersService.addUser(usersDto);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        usersService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        ApiResponse response = usersService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/skill-score")
    public ResponseEntity<SkillScoreResponseDTO> getSkillScore() {
            return ResponseEntity.ok( usersService.getSkillScore());
    }

    @PutMapping("/complete")
    public ResponseEntity<String> completeUserProfile(
            @RequestBody CompleteProfileRequest request
    ) {
        usersService.completeProfile(request);
        return ResponseEntity.ok("Profile updated successfully.");
    }

    @GetMapping("/profile")
    public UserProfileResponse getUserProfile() {
        return usersService.getUserProfile();
    }
}
