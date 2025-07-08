package com.unilink.user_service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private String name;
    private String aboutMe;
    private String designation;
    private Double skillsScore;
    private Integer projectCount;
    private String socialLinks;
    private String portfolioUrl;
    private String githubUrl;
    private Boolean mentorStatus;
    private List<SkillDto> skills;
}