package com.unilink.team_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "team", schema = "team")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    private Integer id;

    private String name;

    private Integer projectId;
}
