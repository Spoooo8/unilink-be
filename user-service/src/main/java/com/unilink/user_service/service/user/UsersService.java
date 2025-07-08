package com.unilink.user_service.service.user;


import com.unilink.common.dto.ApiResponse;
import com.unilink.common.exceptions.UnilinkBadRequestException;
import com.unilink.common.exceptions.UnilinkResourceNotFoundException;

import com.unilink.user_service.config.UserContextId;
import com.unilink.user_service.dto.user.*;
import com.unilink.user_service.entity.user.Skill;
import com.unilink.user_service.entity.user.UserDetails;
import com.unilink.user_service.entity.user.UserSkillMapping;
import com.unilink.user_service.entity.user.Users;
import com.unilink.user_service.repository.user.SkillRepository;
import com.unilink.user_service.repository.user.UserDetailsRepository;
import com.unilink.user_service.repository.user.UserSkillMappingRepostiory;
import com.unilink.user_service.repository.user.UsersRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersService {
    Logger logger = LoggerFactory.getLogger(UsersService.class);
    ApiResponse response = new ApiResponse();
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private UserSkillMappingRepostiory userSkillMappingRepostiory;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private UserContextId userContext;

    @Autowired
    private UserDetailsRepository userDetailsRepository;


    public void register(RegisterRequest request) {
        if (usersRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already in use");
        }

        if (usersRepository.existsByUserName(request.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        Users user = new Users();
        user.setEmail(request.getEmail());
        user.setUserName(request.getUsername());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        usersRepository.save(user);

        UserDetails newUserDetails = new UserDetails();
        newUserDetails.setUser(user);
        newUserDetails.setProjectCount(0);
        newUserDetails.setSkillsScore(0.0);
        newUserDetails.setProjectRating(0.0);
        newUserDetails.setProfileComplete(false);
        userDetailsRepository.save(newUserDetails);
    }

    public UserResponse getUserById(Long id) {
        logger.info("in get user by id: {}", id);
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new UnilinkResourceNotFoundException("User not found with id: " + id));
        UserDetails userDetails = userDetailsRepository.findByUserId(id).orElseThrow(()-> new UnilinkResourceNotFoundException("no user details found"));
        UserResponse userResponse = new UserResponse();
                    userResponse.setId(user.getId());
                    userResponse.setName(user.getName());
                   userResponse.setSkillScore(String.valueOf(userDetails.getSkillsScore()));
                   userResponse.setProjectRating(String.valueOf(userDetails.getProjectRating()));
                    return userResponse;

    }



    public UserInfoDTO getUseInfo() {
        Long userId = Long.valueOf(userContext.getUserId());
        logger.info("in get user by id: {}", userId);
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new UnilinkResourceNotFoundException("User not found with id: " + userId));
        UserInfoDTO userResponse = new UserInfoDTO();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setId(user.getId());
        userResponse.setUserName(user.getUserName());
        userResponse.setEmail(user.getEmail());
        return userResponse;

    }


}
