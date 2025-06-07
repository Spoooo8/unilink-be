package com.unilink.quiz_service.service.users;

import com.unilink.quiz_service.dto.ApiResponse;
import com.unilink.quiz_service.dto.users.UserSkillMappingDto;
import com.unilink.quiz_service.entity.users.Skill;
import com.unilink.quiz_service.entity.users.UserSkillMapping;
import com.unilink.quiz_service.entity.users.Users;
import com.unilink.quiz_service.exception.ResourceNotFoundException;
import com.unilink.quiz_service.repository.users.SkillRepository;
import com.unilink.quiz_service.repository.users.UserSkillMappingRepostiory;
import com.unilink.quiz_service.repository.users.UsersRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserSkillMappingService {
    Logger logger = LoggerFactory.getLogger(UserSkillMappingService.class);
    ApiResponse response = new ApiResponse();
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private UserSkillMappingRepostiory userSkillMappingRepostiory;
    @Autowired
    private ModelMapper modelMapper;

    public ApiResponse addUserSkillMapping(UserSkillMappingDto request) {
        Users existingUser = usersRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));

        List<Long> skillIds = request.getSkillIds();
        List<UserSkillMapping> userSkillMappings = new ArrayList<>();

        skillIds.forEach(id -> {
            Skill existingSkill = skillRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + id));

            UserSkillMapping userSkillMapping = new UserSkillMapping();
            userSkillMapping.setUser(existingUser);
            userSkillMapping.setSkill(existingSkill);
            userSkillMapping.setIsPrimarySkill(request.getIsPrimarySkill());
            userSkillMapping.setInterestLevel(request.getInterestLevel());
            userSkillMapping.setYearsOfExperience(request.getYearsOfExperience());
            userSkillMapping.setProficiencyLevel(request.getProficiencyLevel());
            userSkillMappings.add(userSkillMapping);
        });
        userSkillMappingRepostiory.saveAll(userSkillMappings);
        return response;
    }
}
