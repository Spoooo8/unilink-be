package com.unilink.user_service.service.user;


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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsService {
    private final UsersRepository usersRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final UserContextId userContext;
    private final UserSkillMappingRepostiory userSkillMappingRepostiory;
    private final SkillRepository skillRepository;


    @Transactional
    public void editProfile( EditProfileRequest request) {
        Long userId = Long.valueOf(userContext.getUserId());
        UserDetails userDetails = userDetailsRepository.findByUserId(userId).orElseThrow(
                () -> new UnilinkResourceNotFoundException("user not found")
        );

        userDetails.setAboutMe(request.getAboutMe());
        userDetails.setPortfolioUrl(request.getPortfolioUrl());
        userDetails.setGithubUrl(request.getGithubUrl());
        userDetails.setSocialLinks(request.getSocialLink());
        userDetails.setStatusMessage(request.getDesignation());
        userDetails.setMentorStatus(request.getIsMentor());
        userDetailsRepository.save(userDetails);


        userSkillMappingRepostiory.deleteByUserDetailsId(userDetails.getId());

        List<UserSkillMapping> mappings = request.getSkillIds().stream().map(skillId-> {
            Skill skill = skillRepository.findById(Long.valueOf(skillId)).orElseThrow(() -> new UnilinkResourceNotFoundException("skill not found"));

            UserSkillMapping mapping = new UserSkillMapping();
            mapping.setUserDetails(userDetails);
            mapping.setSkill(skill);
            mapping.setIsPrimarySkill(false);
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
        UserDetails userDetails = userDetailsRepository.findByUserId(userId).orElseThrow(
                () -> new UnilinkResourceNotFoundException("no details found for thr user")
        );
        return new UserProfileResponse(
                user.getName(),
                userDetails.getAboutMe(),
                userDetails.getStatusMessage(),
                userDetails.getSkillsScore(),
                userDetails.getProjectCount(),
                userDetails.getSocialLinks(),
                userDetails.getPortfolioUrl(),
                userDetails.getGithubUrl(),
                userDetails.getMentorStatus(),
                userDetails.getUserSkillMapping().stream()
                        .map(mapping -> {
                            SkillDto dto = new SkillDto();
                            dto.setId(mapping.getSkill().getId());
                            dto.setName(mapping.getSkill().getName());
                            return dto;
                        })
                        .collect(Collectors.toList()));
    }


    public SkillScoreResponseDTO getSkillScore() {
        Optional<UserDetails> user = userDetailsRepository.findByUserId(Long.valueOf(userContext.getUserId()));
        return new SkillScoreResponseDTO(user.get().getSkillsScore());
    }

    public UserProfileResponse getUserProfileById(Long userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDetails userDetails = userDetailsRepository.findByUserId(userId).orElseThrow(
                () -> new UnilinkResourceNotFoundException("no details found for thr user")
        );
        return new UserProfileResponse(
                user.getName(),
                userDetails.getAboutMe(),
                userDetails.getStatusMessage(),
                userDetails.getSkillsScore(),
                userDetails.getProjectCount(),
                userDetails.getSocialLinks(),
                userDetails.getPortfolioUrl(),
                userDetails.getGithubUrl(),
                userDetails.getMentorStatus(),
                userDetails.getUserSkillMapping().stream()
                        .map(mapping -> {
                            SkillDto dto = new SkillDto();
                            dto.setId(mapping.getSkill().getId());
                            dto.setName(mapping.getSkill().getName());
                            return dto;
                        })
                        .collect(Collectors.toList()));
    }
}
