package com.unilink.user_service.entity.user;

import com.unilink.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_users", schema = "users")
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    private String userName;
    private String password;
    private String aboutMe;
    private String profilePicture;
    private String statusMessage;
    private Double skillsScore;
    private String experienceLevel;
    private Integer projectCount;
    private Double projectRating;
    private String socialLinks;
    private String portfolioUrl;
    private String githubUrl;
    private LocalDateTime lastActive;
    private Boolean mentorStatus;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserSkillMapping> userSkillMapping;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserCertifications> userCertifications;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserConnections> connectionsInitiated;

    @OneToMany(mappedBy = "connectedUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserConnections> connectionsReceived;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ActivityLogs> activityLogs;


}

