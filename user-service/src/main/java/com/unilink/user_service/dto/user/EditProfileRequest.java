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
public class EditProfileRequest {
    private String aboutMe;
    private String portfolioUrl;
    private String designation;
    private List<Integer> skillIds;
    private String socialLink;
    private String githubUrl;
    private Boolean isMentor;

}