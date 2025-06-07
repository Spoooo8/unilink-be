package com.unilink.project_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "project", name = "skill_required")
public class SkillRequired {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer skillId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private String proficiencyLevel;

    // Getters and setters...
}
