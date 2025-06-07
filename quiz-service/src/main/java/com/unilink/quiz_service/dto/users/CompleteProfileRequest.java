package com.unilink.quiz_service.dto.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompleteProfileRequest {
    private String aboutMe;
    private String portfolioUrl;
    private String designation; // maps to experienceLevel or statusMessage
    private List<String> skills;
    private String socialLink;
    private String githubUrl;
}