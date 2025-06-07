package com.unilink.quiz_service.service.users;

import com.unilink.common.thread.ThreadLocal;
import com.unilink.quiz_service.config.UserContext;
import com.unilink.quiz_service.dto.ApiResponse;
import com.unilink.quiz_service.dto.users.*;
import com.unilink.quiz_service.entity.users.Skill;
import com.unilink.quiz_service.entity.users.UserSkillMapping;
import com.unilink.quiz_service.entity.users.Users;
import com.unilink.quiz_service.exception.BadRequestException;
import com.unilink.quiz_service.exception.ResourceNotFoundException;
import com.unilink.quiz_service.repository.users.SkillRepository;
import com.unilink.quiz_service.repository.users.UserSkillMappingRepostiory;
import com.unilink.quiz_service.repository.users.UsersRepository;
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
    private UserContext userContext;



    public List<UserResponse> getAllUsers() {
        logger.info("in get all users");
            List<Users> users = usersRepository.findByIsDeactivated(false);
        return users.stream()
                .map(user -> {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setId(user.getId());
                    userResponse.setName(user.getName());
                    userResponse.setAboutMe(user.getAboutMe());
                    List<UserSkillMapping> userSkillMappings = userSkillMappingRepostiory.findByUserId(user.getId());
                    List<Skill> userSkills = userSkillMappings.stream().map(UserSkillMapping::getSkill).toList();
                    List<String> skills = userSkills.stream().map(
                            Skill::getName
                    ).toList();
                    userResponse.setSkills(skills);
                    return userResponse;
                })
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {
        logger.info("in get user by id: {}", id);
        Users user = usersRepository.findById(id)

                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        UserResponse userResponse = new UserResponse();
                    userResponse.setId(user.getId());
                    userResponse.setName(user.getName());
                    userResponse.setAboutMe(user.getAboutMe());
                    List<UserSkillMapping> userSkillMappings = userSkillMappingRepostiory.findByUserId(user.getId());
                    List<Skill> userSkills = userSkillMappings.stream().map(UserSkillMapping::getSkill).toList();
                    List<String> skills = userSkills.stream().map(
                          Skill::getName
                     ).toList();
                    userResponse.setSkills(skills);
                    return userResponse;

    }

    public ApiResponse addUser(UsersDto request) {
        logger.info("in add user");
        Users newUser = new Users();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setAboutMe(request.getAboutMe());
        newUser.setProfilePicture(request.getProfilePicture());
        newUser.setStatusMessage(request.getStatusMessage());
        newUser.setExperienceLevel(request.getExperienceLevel());
        newUser.setSocialLinks(request.getSocialLinks());
        newUser.setPortfolioUrl(request.getPortfolioUrl());
        newUser.setGithubUrl(request.getGithubUrl());
        newUser.setMentorStatus(request.getMentorStatus());
        newUser.setUserCertifications(request.getUserCertifications());
        usersRepository.save(newUser);
        return response;
    }

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
    }

    public ApiResponse login(LoginRequest request) {
        // Try finding by username first
        Optional<Users> optionalUser = usersRepository.findByUserName(request.getUsernameOrEmail());

        if (!optionalUser.isPresent()) {
            optionalUser = usersRepository.findByEmail(request.getUsernameOrEmail());
        }

            Users user = optionalUser.orElseThrow(() -> new BadRequestException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }
//        ThreadLocal.setUserId(user.getId().toString());
//        String UserId = ThreadLocal.getUserId();
        userContext.setUserId(user.getId().toString());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Login successful");
        return apiResponse;
    }


    public SkillScoreResponseDTO getSkillScore() {
        Optional<Users> user = usersRepository.findById(Long.valueOf(userContext.getUserId()));
        return new SkillScoreResponseDTO(user.get().getSkillsScore());
    }

    @Transactional
    public void completeProfile( CompleteProfileRequest request) {
        Long userId = Long.valueOf(userContext.getUserId());
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setAboutMe(request.getAboutMe());
        user.setPortfolioUrl(request.getPortfolioUrl());
        user.setGithubUrl(request.getGithubUrl());
        user.setSocialLinks(request.getSocialLink());
        user.setStatusMessage(request.getDesignation());
        usersRepository.save(user);

        // Clear existing skills
        userSkillMappingRepostiory.deleteByUserId(userId);

        // Add new skills
        List<UserSkillMapping> mappings = request.getSkills().stream().map(skillName -> {
            Skill skill = skillRepository.findByName(skillName)
                    .orElseGet(() -> {
                        Skill newSkill = new Skill();
                        newSkill.setName(skillName);
                        return skillRepository.save(newSkill);
                    });

            UserSkillMapping mapping = new UserSkillMapping();
            mapping.setUser(user);
            mapping.setSkill(skill);
            mapping.setIsPrimarySkill(false); // default
            mapping.setProficiencyLevel("Beginner");
            mapping.setYearsOfExperience(0.0);
            mapping.setInterestLevel("High");
            mapping.setEndorsementCount(0);
            return mapping;
        }).toList();

        userSkillMappingRepostiory.saveAll(mappings);
    }

    public UserProfileResponse getUserProfile() {
        Long userId = Long.valueOf(userContext.getUserId());
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileResponse(
                user.getName(),
                user.getAboutMe(),
                user.getStatusMessage(),
                user.getSkillsScore(),
                user.getProjectCount(),
                user.getSocialLinks(),
                user.getPortfolioUrl(),
                user.getGithubUrl(),
                user.getMentorStatus(),
                user.getUserSkillMapping().stream()
                        .map(mapping -> mapping.getSkill().getName())
                        .collect(Collectors.toList())
        );
    }
}
