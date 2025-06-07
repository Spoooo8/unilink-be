package com.unilink.team_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "team_members", schema = "team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamMember {

    @Id
    private Integer id;

    private String name;

    @Column(length = 1000)
    private String description;

    private Integer roleId;

    private Integer userId;

    private Timestamp lastActive;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    private String status;

    private Integer contributionLevel;
}
