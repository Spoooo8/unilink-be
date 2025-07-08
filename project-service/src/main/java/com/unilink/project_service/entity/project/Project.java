package com.unilink.project_service.entity.project;

import com.unilink.common.entity.BaseEntity;

import com.unilink.project_service.utils.ComplexityLevel;
import com.unilink.project_service.utils.ProjectStatus;
import com.unilink.project_service.utils.ProjectVisibility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_project", schema = "projects")
public class Project extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String title;
    private String repoLink;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String imageUrl;
    private ComplexityLevel complexityLevel;
    private ProjectVisibility visibility;
    private LocalDate applicationDeadline;
    private Double progress;
    private Double rating;
    private ProjectStatus projectStatus;
    private ProjectVisibility projectVisibility;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SkillRequired> skillRequired;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    private Team team;


}