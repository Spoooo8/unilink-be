package com.unilink.quiz_service.dto.users;


import com.unilink.quiz_service.entity.users.UserCertifications;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {
    private Long id;
    private String name;
    private String email;
    private String aboutMe;
    private String profilePicture;
    private String statusMessage;
    private String experienceLevel;
    private String socialLinks;
    private String portfolioUrl;
    private String githubUrl;
    private Boolean mentorStatus;
    private List<UserCertifications> userCertifications;
}