package com.unilink.user_service.entity;

import com.unilink.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "users", name = "users")
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "about_me", length = 1000)
    private String aboutMe;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "status_message")
    private String statusMessage;

    private Integer skillsScore;
    private String experienceLevel;
    private Integer projectCount;
    private Integer projectRating;

    @Column(columnDefinition = "json")
    private String socialLinks;

    private String portfolioUrl;
    private String githubUrl;
    private LocalDateTime lastActive;
    private Boolean mentorStatus;

}
