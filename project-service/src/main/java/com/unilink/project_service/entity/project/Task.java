package com.unilink.project_service.entity.project;

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
@Table(name = "tbl_task", schema = "projects")
class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate dueDate;
    private LocalDate completedAt;
    private Double estimatedHours;
    private Double actualHours;
    private double progress;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assignee_id")
    private TeamMember assignee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;
}