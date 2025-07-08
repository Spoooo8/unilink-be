package com.unilink.user_service.entity.user;

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
@Table(name = "tbl_user_details", schema = "users")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Users user;

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
    private Boolean profileComplete;

    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserSkillMapping> userSkillMapping;

    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserCertifications> userCertifications;

    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserConnections> connectionsInitiated;

    @OneToMany(mappedBy = "connectedUserDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserConnections> connectionsReceived;

    @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ActivityLogs> activityLogs;

}
