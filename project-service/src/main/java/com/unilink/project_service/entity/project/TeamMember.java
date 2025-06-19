package com.unilink.project_service.entity.project;


import com.unilink.project_service.utils.TeamMemberRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_team_member", schema = "projects")
public  class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private LocalDate lastActive;
    private Integer contributionLevel;
    private TeamMemberRole teamMemberRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_role_id", referencedColumnName = "id", nullable = false)
    private MemberRole memberRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    private Team team;

}
