package com.unilink.user_service.service.user;


import com.unilink.common.dto.ApiResponse;
import com.unilink.common.exceptions.UnilinkResourceNotFoundException;
import com.unilink.user_service.dto.user.UserSkillMappingDto;
import com.unilink.user_service.entity.user.Skill;
import com.unilink.user_service.entity.user.UserSkillMapping;
import com.unilink.user_service.entity.user.Users;
import com.unilink.user_service.repository.user.SkillRepository;
import com.unilink.user_service.repository.user.UserSkillMappingRepostiory;
import com.unilink.user_service.repository.user.UsersRepository;
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
                .orElseThrow(() -> new UnilinkResourceNotFoundException("User not found with id: " + request.getUserId()));

        List<Long> skillIds = request.getSkillIds();
        List<UserSkillMapping> userSkillMappings = new ArrayList<>();

        skillIds.forEach(id -> {
            Skill existingSkill = skillRepository.findById(id)
                    .orElseThrow(() -> new UnilinkResourceNotFoundException("Skill not found with id: " + id));

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